package polytech.univtours.greman;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class PartGreen {

    public Button but_Fullscreen;

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
        stage.setTitle("Ajouter Projet/Tâche");
        stage.setScene(scene);
        stage.setMaximized(true);
        stage.showAndWait();  // Attendre la fermeture avant de continuer
    }
}
