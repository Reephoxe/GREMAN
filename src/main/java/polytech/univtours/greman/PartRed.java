package polytech.univtours.greman;

import javafx.animation.ScaleTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.util.Objects;
public class PartRed {

    public Button but_FullScreen;
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

}
