package polytech.univtours.greman;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Executable {
    private static String scriptPath = "./Algorithmes";  // Utilisation de '/' pour compatibilité Windows & Linux

    public static double[][] executeFile(String filePath, String param_nbC, String param_nbL, String param_CoefC, String param_CoefL) {
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
            double[][] resultat = ScriptTotal(freqArray, impArray, param_nbC, param_nbL, param_CoefC, param_CoefL);

            // Vérification et affichage des résultats
            System.out.println("Résultats globaux :");
            for (double[] row : resultat) {
                System.out.println(Arrays.toString(row));
            }
            return resultat;
        } catch (IOException e) {
            System.err.println("Erreur de lecture du fichier : " + e.getMessage());
        }
        return new double[0][0];
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

    public static double[][] StringOutput(String command, int nbOutput) {
        String output = executeOctaveCommand(command);
        List<double[]> results = new ArrayList<>();

        // Mise à jour de la regex
        Pattern pattern = Pattern.compile("([-+]?[0-9]*\\.?[0-9]+(?:[eE][-+]?[0-9]+)?)\\s*([+-])?\\s*([0-9]*\\.?[0-9]+(?:[eE][-+]?[0-9]+)?)?\\s*i");
        Matcher matcher = pattern.matcher(output);

        while (matcher.find()) {
            // Partie réelle
            double real = Double.parseDouble(matcher.group(1));

            // Partie imaginaire : vérifier si elle est présente et bien formatée
            String imagString = matcher.group(3); // Partie imaginaire
            if (imagString == null || imagString.trim().isEmpty()) {
                imagString = "0"; // Si aucune partie imaginaire, on met 0
            }

            // Si la partie imaginaire est uniquement "+i" ou "-i", on lui attribue une valeur de 1 ou -1
            if (imagString.trim().equals("+")) {
                imagString = "1";
            } else if (imagString.trim().equals("-")) {
                imagString = "-1";
            }

            // Si un signe est présent pour la partie imaginaire, on le garde
            if (matcher.group(2) != null) {
                imagString = matcher.group(2) + imagString;
            }

            // Conversion de la partie imaginaire en double
            double imag = Double.parseDouble(imagString);

            results.add(new double[]{real, imag});
        }

        return results.toArray(new double[0][0]);
    }


    public static double[][] ScriptTotal(String[] param_f, String[] param_Z_complex, String param_nbC, String param_nbL, String param_coefC, String param_coefL) {
        String command = String.format(
                "addpath('%s'); Z2 = ScriptTotal(%s, %s, %s, %s, %s, %s); disp(Z2);",
                scriptPath, Arrays.toString(param_f), Arrays.toString(param_Z_complex), param_nbC, param_nbL, param_coefC, param_coefL
        );

        return StringOutput(command, 3);
    }
}