package polytech.univtours.greman;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Executable {
    private static String scriptPath = "./Algorithmes";  // Utilisation de '/' pour compatibilité Windows & Linux

    public static void executeFile(String filePath, String param_nbC, String param_nbL, String param_CoefC, String param_CoefL) {
        try {
            double z0 = 50.0;
            List<SParameterParser.DataPoint> dataPoints = SParameterParser.parseFile(filePath, z0);

            List<String> freqList = new ArrayList<>();
            List<String> impList = new ArrayList<>();

            // Construire les tableaux f et Z au format Octave
            for (SParameterParser.DataPoint dp : dataPoints) {
                freqList.add(String.valueOf((int) dp.frequence)); // Conversion en entier (pas de ".0")
                impList.add(dp.impedanceReel + " + " + dp.impedanceImaginaire + "i");
            }

            // Convertir en tableaux de Strings
            String[] freqArray = freqList.toArray(new String[0]);
            String[] impArray = impList.toArray(new String[0]);

            // Appel unique de ScriptTotal avec les bons formats
            String[] resultat = ScriptTotal(freqArray, impArray, param_nbC, param_nbL, param_CoefC, param_CoefL);

            // Vérification et affichage des résultats
            if (resultat.length >= 3) {
                System.out.println("Résultats globaux :");
                System.out.println("R2 = " + resultat[0] + ", L2 = " + resultat[1] + ", C2 = " + resultat[2]);
            } else {
                System.out.println("Erreur : Résultats incomplets.");
            }
        } catch (IOException e) {
            System.err.println("Erreur de lecture du fichier : " + e.getMessage());
        }
    }

    private static String executeOctaveCommand(String command) {
        StringBuilder output = new StringBuilder();
        try {
            ProcessBuilder processBuilder = new ProcessBuilder("octave", "--no-gui", "--quiet", "--eval", command);
            processBuilder.redirectErrorStream(true);

            Process process = processBuilder.start();
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                output.append(line).append("\n");
                System.out.println("Octave : " + line);  // Affiche chaque ligne de sortie
            }

            int exitCode = process.waitFor();
            System.out.println("Le programme Octave a terminé avec le code de sortie : " + exitCode);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        return output.toString().trim();
    }

    public static String[] StringOutput(String command, int nbOutput) {
        String output = executeOctaveCommand(command);
        String[] lines = output.split("\n");  // Sépare les lignes

        String[] tmpOut = new String[nbOutput];
        Arrays.fill(tmpOut, "error");  // Remplissage par défaut

        // Parcours chaque ligne et stocke la dernière ligne contenant des valeurs
        for (String line : lines) {
            line = line.trim();
            if (line.matches("[-+]?\\d*\\.?\\d+([eE][-+]?\\d+)?(\\s+[-+]?\\d*\\.?\\d+([eE][-+]?\\d+)?)*")) {
                String[] values = line.split("\\s+"); // Séparer par espaces
                for (int i = 0; i < nbOutput && i < values.length; i++) {
                    tmpOut[i] = values[i];
                }
            }
        }

        return tmpOut;
    }


    public static String[] ScriptTotal(String[] param_f, String[] param_Z_complex, String param_nbC, String param_nbL, String param_coefC, String param_coefL) {
        String command = String.format(
                "addpath('%s'); [R2, L2, C2] = ScriptTotal(%s, %s, %s, %s, %s, %s); disp([R2, L2, C2]);",
                scriptPath, Arrays.toString(param_f), Arrays.toString(param_Z_complex), param_nbC, param_nbL, param_coefC, param_coefL
        );

        return StringOutput(command, 3);
    }

    public static double[] Estimated_Impedance(String param_R, String param_L, String param_C, String[] param_f) {
        // Crée la commande Octave à exécuter
        String command = String.format(
                "addpath('%s'); Z = Estimated_Impedance(%s, %s, %s, %s); disp(Z);",
                scriptPath, param_R, param_L, param_C, Arrays.toString(param_f)
        );

        // Exécute la commande Octave et récupère la sortie sous forme de chaîne
        String output = executeOctaveCommand(command);

        // Parse la sortie pour extraire les valeurs numériques
        String[] lines = output.split("\n");
        double[] result = new double[lines.length];

        // Remplir le tableau de doubles avec les valeurs extraites
        Arrays.fill(result, Double.NaN);  // Valeur par défaut si une erreur se produit

        for (String line : lines) {
            line = line.trim();
            if (line.matches("[-+]?\\d*\\.?\\d+([eE][-+]?\\d+)?")) {  // Correspond aux nombres décimaux
                String[] values = line.split("\\s+");
                for (int i = 0; i < values.length && i < result.length; i++) {
                    try {
                        result[i] = Double.parseDouble(values[i]);  // Conversion en double
                    } catch (NumberFormatException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        return result;  // Retourne le tableau de doubles
    }


    /*public static String[] Impedance_Estimation_Basic3(String param_f, String param_Z_complex, String param_nbC, String param_nbL, String param_coefC, String param_coefL) {
        String command = String.format(
                "addpath('%s'); [R, L, C] = Impedance_Estimation_Basic3(%s, %s, %s, %s, %s, %s); disp([R, L, C]);",
                scriptPath, param_f, param_Z_complex, param_nbC, param_nbL, param_coefC, param_coefL
        );

        return StringOutput(command, 3);
    }


    public static String[] Imp_Eq(String param_R, String param_L, String param_C, String param_f, String param_wich_Eq) {
        String command = String.format(
                "addpath('%s'); [Z_AllExp, initial_Imp2, initial_cst2] = Imp_Eq(%s, %s, %s, %s, %s); disp([Z_AllExp, initial_Imp2, initial_cst2]);",
                scriptPath, param_R, param_L, param_C, param_f, param_wich_Eq
        );

        return StringOutput(command, 3);
    }

    public static String ErrEq(String param_Z, String param_Z_equation) {
        String command = String.format(
                "addpath('%s'); E_AllExp = ErrEq(%s, %s); disp(E_AllExp);",
                scriptPath, param_Z, param_Z_equation
        );

        return executeOctaveCommand(command);
    }

    public static String[] RCL_construct(String param_x, String param_initial_cst, String param_Exp_or_NoExp, String param_R, String param_L, String param_C) {
        String command = String.format(
                "addpath('%s'); [R2, L2, C2] = RLC_construct(%s, %s, %s, %s, %s, %s); disp([R2, L2, C2]);",
                scriptPath, param_x, param_initial_cst, param_Exp_or_NoExp, param_R, param_L, param_C
        );

        return StringOutput(command, 3);
    }

    private static String fmincon(String errorEq, String initialImp2) {
        String command = String.format(
                "addpath('%s'); x_AllExp = fmincon(E_AllExp, initial_Imp2, [] , [], [] , [] , lb2 , up2); disp(x_AllExp);",
                scriptPath, errorEq, initialImp2
        );

        return executeOctaveCommand(command);
    }*/
}

