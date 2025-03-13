package polytech.univtours.greman;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Executable {
    private static String scriptPath = "./Algorithmes";  // Utilisation de '/' pour compatibilité Windows & Linux

    public static void main(String[] args) {
        //Exemple d'utilisation
        String f = "1000";
        String Z = "50 + 10i";

        // Étape 1 - Estimation de base
        String[] result = ScriptTotal(f, Z, "4", "2", "7", "4.05");
        String R2 = result[0];
        String L2 = result[1];
        String C2 = result[2];

        if (R2.equals("error") || L2.equals("error") || C2.equals("error")) {
            System.err.println("Erreur lors de l'exécution de RCL_construct");
            return;
        }

        System.out.println("Résultats finaux :");
        System.out.println("R2 = " + R2);
        System.out.println("L2 = " + L2);
        System.out.println("C2 = " + C2);
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

        // Parcours chaque ligne pour trouver les valeurs numériques
        for (String line : lines) {
            line = line.trim();
            if (line.matches("[-+]?\\d*\\.?\\d+([eE][-+]?\\d+)?(\\s+[-+]?\\d*\\.?\\d+([eE][-+]?\\d+)?)*")) {
                String[] values = line.split("\\s+"); // Séparer par espaces
                for (int i = 0; i < nbOutput && i < values.length; i++) {
                    tmpOut[i] = values[i];
                }
                break; // On prend uniquement la première ligne valide trouvée
            }
        }

        // Si pas assez de valeurs, remplir avec "error"
        for (int i = 0; i < nbOutput; i++) {
            if (tmpOut[i] == null) {
                tmpOut[i] = "error";
            }
        }

        return tmpOut;
    }

    public static String[] ScriptTotal(String param_f, String param_Z_complex, String param_nbC, String param_nbL, String param_coefC, String param_coefL) {
        String command = String.format(
                "addpath('%s'); [R2, L2, C2] = ScriptTotal(%s, %s, %s, %s, %s, %s); disp([R2, L2, C2]);",
                scriptPath, param_f, param_Z_complex, param_nbC, param_nbL, param_coefC, param_coefL
        );

        return StringOutput(command, 3);
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

    public static String Estimated_Impedance(String param_R, String param_L, String param_C, String param_f) {
        String command = String.format(
                "addpath('%s'); Z = Estimated_Impedance(%s, %s, %s, %s); disp(Z);",
                scriptPath, param_R, param_L, param_C, param_f
        );

        return executeOctaveCommand(command);
    }

    private static String fmincon(String errorEq, String initialImp2) {
        String command = String.format(
                "addpath('%s'); x_AllExp = fmincon(E_AllExp, initial_Imp2, [] , [], [] , [] , lb2 , up2); disp(x_AllExp);",
                scriptPath, errorEq, initialImp2
        );

        return executeOctaveCommand(command);
    }*/
}

