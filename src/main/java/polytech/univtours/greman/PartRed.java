package polytech.univtours.greman;

import javafx.animation.ScaleTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Objects;

import static polytech.univtours.greman.Parser.Parsers1p;
import static polytech.univtours.greman.Parser.Parsers2p;

public class PartRed {

    public Button but_FullScreen;
    // Trouver le Pane dans le FXML
    @FXML
    public Pane graphePane;
    // Définir les axes
    @FXML
    public NumberAxis xAxis = new NumberAxis();
    @FXML
    public NumberAxis yAxis = new NumberAxis();
    // Créer le graphique
    @FXML
    public LineChart<Number, Number> lineChart = new LineChart<>(xAxis, yAxis);
    public AnchorPane paneRed;

    public String Chemin_sxp="";

    public String getChemin_sxp() {
        return Chemin_sxp;
    }

    public void setChemin_sxp(String chemin_sxp) {
        Chemin_sxp = chemin_sxp;
    }

    public void init_courbe() throws FileNotFoundException {
        //File fichier = new File("C:/Users/sirra/IdeaProjects/Projet_Collectif_4A/src/main/java/polytech/univtours/greman/S1P/CHAFF HORS TENSION.S1P");
        //File fichier = new File("C:\\!Polytech\\Cycle ingé\\Semestre 8\\Projet coo\\GREMAN\\src\\main\\java\\polytech\\univtours\\greman\\S1P\\CHAFF HORS TENSION.S1P");
        File fichier = new File(getChemin_sxp());

        String typeDeFichier = "";
        String nomDuFichier = fichier.getName();

        //////////Définir le type de fichier/////////////
        int point = nomDuFichier.lastIndexOf('.');
        if (point > 0 && point < nomDuFichier.length() - 1) {
            typeDeFichier = nomDuFichier.substring(point + 1);
        }
        ////////////////////////////////////////////////


        LineChart.Series<Number, Number> series = new XYChart.Series<>(); //Points qui seront à afficher
        lineChart.setLegendVisible(false);
        ///////////Préparation des valeurs sur les axes /////////////////
        double xmin = 1000000000;
        double xmax = -1000000000;
        double ymin = 1000000000;
        double ymax = -1000000000;
        ////////////////////////////////////////////////////////////////

        if (typeDeFichier.equals("S1P")) {
            //TODO faire en sorte que le chemin soit le chemin qui est donné lors du chargement du fichier
            double[][] testS1P = Parsers1p(fichier);


            ///////////////////Set des valeurs d'abscisse et d'ordonnées////////////////////////
            for(int iboucle = 0 ; iboucle < testS1P.length ; iboucle++ ) {
                series.getData().add(new XYChart.Data<>(testS1P[iboucle][0], testS1P[iboucle][1]));
                if(testS1P[iboucle][0] < xmin) { xmin = testS1P[iboucle][0]; }
                if(testS1P[iboucle][0] > xmax) { xmax = testS1P[iboucle][0]; }
                if(testS1P[iboucle][1] < ymin) { ymin = testS1P[iboucle][1]; }
                if(testS1P[iboucle][1] > ymax) { ymax = testS1P[iboucle][1]; }

                xAxis.setAutoRanging(false);
                yAxis.setAutoRanging(false);
                xAxis.setLowerBound(xmin);
                xAxis.setUpperBound(xmax);
                yAxis.setLowerBound(ymin);
                yAxis.setUpperBound(ymax);
                xAxis.setTickUnit((xmax - xmin)/20);
                yAxis.setTickUnit((ymax - ymin)/20);
                ////////////////////////////////////////////////////////////////////////////////

                /////Juste pour vérifier que les valeurs sont bien celles de fichier, une fois terminé, peut être retiré/////
                System.out.println("S1P :");
                for (double[] row : testS1P) {
                    System.out.println("Module: " + row[0] + ", Argument: " + row[1]);
                }
                /////////////////////////////////////////////////////////////////////////////////////////////////////////////
            }
        } else if(typeDeFichier.equals("S2P")) {
            double[][] testS1P = Parsers2p(fichier);


            ///////////////////Set des valeurs d'abscisse et d'ordonnées////////////////////////
            // TODO mettre l'initialisation des des xmin, xmax, ymin, ymax
            xAxis.setAutoRanging(false);
            yAxis.setAutoRanging(false);
            xAxis.setLowerBound(xmin);
            xAxis.setUpperBound(xmax);
            yAxis.setLowerBound(ymin);
            yAxis.setUpperBound(ymax);
            xAxis.setTickUnit((xmax - xmin)/20);
            yAxis.setTickUnit((ymax - ymin)/20);
            ///////////////////////////////////////////////////////////////////////////////////

            /////Juste pour vérifier que les valeurs sont bien celles de fichier, une fois terminé, peut être retiré/////
            System.out.println("S2P :");
            for (double[] row : testS1P) {
                System.out.println("ModuleS11: " + row[0] + ", ModuleS12: " + row[1] + ", ModuleS21: " + row[2] + ", ModuleS22: " + row[3] + ", ArgumentS11: " + row[4] + ", ArgumentS12: " + row[5] + ", ArgumentS21: " + row[6] + ", ArgumentS22: " + row[7]);
            }
            ///////////////////////////////////////////////////////////////////////////////////////////////////////////
        }
        lineChart.getData().add(series);
    }

    public void initialize() throws FileNotFoundException {
        _recuperation();
    }

    public void initializeView(String MODE){

        if(Objects.equals(MODE, "FSM")){
            but_FullScreen.setVisible(false);
            but_FullScreen.isDisable();
        }
    }

    public void FullSrceen(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("PartRed.fxml"));
        Parent root = loader.load();

        // Récupérer le contrôleur et initialiser la vue avec le mode souhaité
        PartRed Fullscreen = loader.getController();

        Fullscreen.initializeView("FSM");

        // Configurer la scène pour la fenêtre modale
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        String css = getClass().getResource("helloApplication.css").toExternalForm();
        scene.getStylesheets().add(css);
        stage.initModality(Modality.APPLICATION_MODAL);  // Fenêtre modale
        stage.setTitle("RedPart");
        stage.setScene(scene);
        stage.setMaximized(true);

        transitionZoom(root);

        stage.showAndWait();  // Attendre la fermeture avant de continuer
    }
    public void transitionZoom(Parent parent) {
        ScaleTransition scaleTransition = new ScaleTransition(Duration.millis(500), parent);
        scaleTransition.setFromX(0.5);
        scaleTransition.setFromY(0.5);
        scaleTransition.setToX(1.0);
        scaleTransition.setToY(1.0);
        scaleTransition.play();
    }

    public void _eraze_graphique(){
        lineChart.getData().clear();
    }

    public void _recuperation(){
        SharedData.filePathProperty.addListener((observable, oldValue, newValue) -> {
            System.out.println(" Nouveau fichier détecté dans REDPART : " + newValue);
            setChemin_sxp(newValue);
            // ici tu pourrais relancer init_courbe() avec ce nouveau fichier
            try {
                _eraze_graphique();
                init_courbe(); // si tu le modifies pour prendre en compte Chemin_sxp
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

}
