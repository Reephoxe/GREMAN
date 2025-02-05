package polytech.univtours.greman;

import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.util.Objects;

public class BluePart {
    public AnchorPane topBox;
    public Button toggleButton;
    public ScrollPane scrollPane;
    public VBox sideBar;
    public Button but_FullSceen;

    public void initialize() {
        sideBar.setPrefWidth(200);
        //scrollBar.valueProperty().addListener((observable, oldValue, newValue) -> {})

    }
    public void initializeView(String MODE){

        if(Objects.equals(MODE, "FSM")){
            but_FullSceen.setVisible(false);
            but_FullSceen.isDisable();
        }
    }
    // Bouton pour afficher ou enlever la liste des éléments du circuit
    public void toggleSideBar(ActionEvent event) throws IOException {
        // Animations pour faire apparaître / disparaître en glissant
        TranslateTransition sideBarTransition = new TranslateTransition(Duration.millis(300), sideBar);
        TranslateTransition buttonTransition = new TranslateTransition(Duration.millis(300), toggleButton);
        TranslateTransition buttonFillsreen = new TranslateTransition(Duration.millis(300), but_FullSceen);
        TranslateTransition scrollPaneTransition = new TranslateTransition(Duration.millis(300), scrollPane);

        // Si les éléments sont cachés
        if (sideBar.getTranslateX() > (0)) {
            // Remettre les éléments à leur position d'origine
            sideBarTransition.setToX(0);
            buttonTransition.setToX(0);
            scrollPaneTransition.setToX(0);
            buttonFillsreen.setToX(0);
            toggleButton.setText(">>>");
            // Si les éléments sont visibles
        } else {
            // Déplacer les éléments hors de l'écran
            sideBarTransition.setToX(200);
            buttonTransition.setToX(200);
            scrollPaneTransition.setToX(200);
            buttonFillsreen.setToX(200);
            toggleButton.setText("<<<");
            System.out.println(sideBar.getHeight());
        }
        // Jouer les animations
        sideBarTransition.play();
        buttonTransition.play();
        scrollPaneTransition.play();
        buttonFillsreen.play();
    }

    public void FullScreen() throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("BluePart.fxml"));
        Parent root = loader.load();

        // Récupérer le contrôleur et initialiser la vue avec le mode souhaité
        BluePart Fullscreen = loader.getController();

        Fullscreen.initializeView("FSM");

        // Configurer la scène pour la fenêtre modale
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        String css = getClass().getResource("helloApplication.css").toExternalForm();
        scene.getStylesheets().add(css);
        stage.initModality(Modality.APPLICATION_MODAL);  // Fenêtre modale
        stage.setTitle("Ajouter Projet/Tâche");
        stage.setScene(scene);
        stage.setMaximized(true);
        stage.showAndWait();  // Attendre la fermeture avant de continuer
    }
}
