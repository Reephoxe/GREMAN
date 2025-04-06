package polytech.univtours.greman;

import java.io.*;
import java.util.*;

public class SParameterParser {
    public static class DataPoint {
        double frequence;
        double impedanceReel;
        double impedanceImaginaire;

        // Constructeur pour initialiser les valeurs de DataPoint
        public DataPoint(double frequence, double reel, double imaginaire, double z0) {
            this.frequence = frequence;
            Complex s11 = new Complex(reel, imaginaire);
            Complex impedance = calculateImpedance(s11, z0);
            this.impedanceReel = impedance.reel;
            this.impedanceImaginaire = impedance.imaginaire;
        }

        // Méthode pour calculer l'impédance à partir de s11 et z0
        private Complex calculateImpedance(Complex s11, double z0) {
            Complex un = new Complex(1.0, 0.0);
            Complex numerateur = un.addition(s11);
            Complex denominateur = un.soustraction(s11);
            return numerateur.division(denominateur).multiplication(z0);
        }
    }

    static class Complex {
        private double reel, imaginaire;

        // Constructeur pour initialiser les valeurs de Complex
        public Complex(double reel, double imaginaire) {
            this.reel = reel;
            this.imaginaire = imaginaire;
        }

        // Méthode pour additionner deux nombres complexes
        public Complex addition(Complex autre) {
            return new Complex(this.reel + autre.reel, this.imaginaire + autre.imaginaire);
        }

        // Méthode pour soustraire deux nombres complexes
        public Complex soustraction(Complex autre) {
            return new Complex(this.reel - autre.reel, this.imaginaire - autre.imaginaire);
        }

        // Méthode pour multiplier un nombre complexe par un scalaire
        public Complex multiplication(double scalaire) {
            return new Complex(this.reel * scalaire, this.imaginaire * scalaire);
        }

        // Méthode pour diviser deux nombres complexes
        public Complex division(Complex autre) {
            double denominateur = autre.reel * autre.reel + autre.imaginaire * autre.imaginaire;
            double partieReel = (this.reel * autre.reel + this.imaginaire * autre.imaginaire) / denominateur;
            double partieImaginaire = (this.imaginaire * autre.reel - this.reel * autre.imaginaire) / denominateur;
            return new Complex(partieReel, partieImaginaire);
        }
    }

    // Méthode pour analyser un fichier et retourner une liste de DataPoint
    public static List<DataPoint> parseFile(String filePath, double z0) throws IOException {
        List<DataPoint> dataPoints = new ArrayList<>();
        boolean isS2P = filePath.endsWith(".s2p");

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String ligne;
            while ((ligne = br.readLine()) != null) {
                // Ignorer les commentaires et métadonnées
                if (ligne.startsWith("#") || ligne.startsWith("!") || ligne.trim().isEmpty()) {
                    continue;
                }

                String[] parts = ligne.trim().split("\\s+");
                if (parts.length >= (isS2P ? 9 : 3)) {
                    try {
                        double frequence = Double.parseDouble(parts[0]);
                        double reel = Double.parseDouble(parts[1]);
                        double imaginaire = Double.parseDouble(parts[2]);
                        dataPoints.add(new DataPoint(frequence, reel, imaginaire, z0));
                    } catch (NumberFormatException e) {
                        System.err.println("Ignoré : " + ligne); // Debug : afficher les lignes ignorées
                    }
                }
            }
        }
        return dataPoints;
    }
}