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
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.scene.layout.Pane;
import java.io.IOException;
import java.util.Objects;

public class PartGreen {

    public Button but_Fullscreen;

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

    public void init_courbe(){
        // Définir les séries de données
        XYChart.Series<Number, Number> series = new XYChart.Series<>();
        series.setName("Nom de la courbe frérot ");

        series.getData().add(new XYChart.Data<>(1, 23));
        series.getData().add(new XYChart.Data<>(2, 14));
        series.getData().add(new XYChart.Data<>(3, 15));
        series.getData().add(new XYChart.Data<>(4, 24));
        series.getData().add(new XYChart.Data<>(5, 34));
        series.getData().add(new XYChart.Data<>(6, 36));
        series.getData().add(new XYChart.Data<>(7, 22));
        series.getData().add(new XYChart.Data<>(8, 45));
        series.getData().add(new XYChart.Data<>(9, 43));
        series.getData().add(new XYChart.Data<>(10, 17));
        series.getData().add(new XYChart.Data<>(11, 29));
        series.getData().add(new XYChart.Data<>(12, 25));

        lineChart.getData().add(series);

        // Ajouter le graphique dans le Pane
        //graphePane.getChildren().add(lineChart);
    }

    public void initialize() {
        init_courbe();
    }

    public void initializeView(String MODE){

            if(Objects.equals(MODE, "FSM")){
                but_Fullscreen.setVisible(false);
                but_Fullscreen.isDisable();
            }
    }

    public void FullScreen(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("PartGreen.fxml"));
        Parent root = loader.load();

        // Récupérer le contrôleur et initialiser la vue avec le mode souhaité
        PartGreen Fullscreen = loader.getController();

        Fullscreen.initializeView("FSM");

        // Configurer la scène pour la fenêtre modale
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        String css = getClass().getResource("helloApplication.css").toExternalForm();
        scene.getStylesheets().add(css);
        stage.initModality(Modality.APPLICATION_MODAL);  // Fenêtre modale
        stage.setTitle("GreenPart");
        stage.setScene(scene);
        stage.setMaximized(true);

        // Redimensionner le graphique en fonction de la taille de la fenêtre
        Fullscreen.lineChart.prefWidthProperty().bind(scene.widthProperty());
        Fullscreen.lineChart.prefHeightProperty().bind(scene.heightProperty());

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
}