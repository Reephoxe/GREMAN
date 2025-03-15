package polytech.univtours.greman;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Parser {
    public static void main(String[] args) {
        //double[][] testS1P = Parsers2p(new File("C:/Users/sirra/IdeaProjects/Projet_Collectif_4A/src/main/java/polytech/univtours/greman/S2P/ZT OPEN OHM.S2P"));
        double [][] testS1P = Parsers1p(new File("C:/Users/sirra/IdeaProjects/Projet_Collectif_4A/src/main/java/polytech/univtours/greman/S1P/CHAFF HORS TENSION.S1P"));
        System.out.println("S1P :");
        for (double[] row : testS1P) {
            //System.out.println("ModuleS11: " + row[0] + ", ModuleS12: " + row[1] + ", ModuleS21: " + row[2] + ", ModuleS22: " + row[3] + ", ArgumentS11: " + row[4] + ", ArgumentS12: " + row[5] + ", ArgumentS21: " + row[6] + ", ArgumentS22: " + row[7]);
            System.out.println("frequence: " + row[0] + ", Module " + row[1] + ", Argument " + row[2]);
        }
    }
    public static double[][] Parsers1p(File cheminDuFichier) {
        List<double[]> listePoints = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(cheminDuFichier))) {
            String ligne;
            int numeroLigne = 0;

            while (numeroLigne < 5 &&(ligne = reader.readLine()) != null) {
                numeroLigne++;
            }

            while((ligne = reader.readLine()) != null) {
                String[] colonnes = ligne.split("\\s+");
                if(colonnes.length ==3) {
                    try {
                        double frequence = Double.parseDouble(colonnes[0]);
                        double module = Double.parseDouble(colonnes[1]);
                        double argument = Double.parseDouble(colonnes[2]);
                        listePoints.add(new double[]{frequence,module,argument});
                    } catch (NumberFormatException e) {}
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        double[][] points = new double[listePoints.size()][3];
        for ( int iboucle = 0; iboucle < listePoints.size(); iboucle++) {
            points[iboucle] = listePoints.get(iboucle);
        }
        return points;
    }

    public static double[][] Parsers2p(File cheminDuFichier) {
        List<double[]> listePoints = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(cheminDuFichier))) {
            String ligne;
            int numeroLigne = 0;

            while (numeroLigne < 5 &&(ligne = reader.readLine()) != null) {
                numeroLigne++;
            }

            while((ligne = reader.readLine()) != null) {
                String[] colonnes = ligne.split("\\s+");
                if(colonnes.length ==9) {
                    try {
                        double moduleS11 = Double.parseDouble(colonnes[1]);
                        double moduleS12 = Double.parseDouble(colonnes[2]);
                        double moduleS21 = Double.parseDouble(colonnes[3]);
                        double moduleS22 = Double.parseDouble(colonnes[4]);
                        double argumentS11 = Double.parseDouble(colonnes[5]);
                        double argumentS12 = Double.parseDouble(colonnes[6]);
                        double argumentS21 = Double.parseDouble(colonnes[7]);
                        double argumentS22 = Double.parseDouble(colonnes[8]);
                        listePoints.add(new double[]{moduleS11, moduleS12, moduleS21, moduleS22, argumentS11, argumentS12, argumentS21, argumentS22});
                    } catch (NumberFormatException e) {}
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        double[][] points = new double[listePoints.size()][8];
        for ( int iboucle = 0; iboucle < listePoints.size(); iboucle++) {
            points[iboucle] = listePoints.get(iboucle);
        }
        return points;
    }
}
