package polytech.univtours.greman;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.stage.Stage;

import java.io.IOException;

public class FileSaveController {
    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    private CheckBox circuitCheckBox;

    @FXML
    private CheckBox moduleCheckBox;
 //
    @FXML
    private CheckBox argumentCheckBox;

    public void backToMainScene(ActionEvent event) throws IOException {

        // Récupère les informations de la scène précédente et change de scène i.e. de fenêtre
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("main-view.fxml"));
        root = fxmlLoader.load();

        // Récupère le contrôleur de la vue
        MainController mainController = fxmlLoader.getController();

        // Récupère la scène et la fenêtre
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);

        // ------------ Ajoute le style CSS -------------
        String css = getClass().getResource("helloApplication.css").toExternalForm();
        scene.getStylesheets().add(css);
        // ----------------------------------------------
        stage.setScene(scene);

        // Permet de mettre en plein écran
        stage.setMaximized(true);

        // Centre la fenêtre au milieu de l'écran
        stage.centerOnScreen();
        stage.show();
    }

    public void circuitChecked(ActionEvent event) {
        if (circuitCheckBox.isSelected()) {
            circuitCheckBox.setSelected(true);
        } else {
            circuitCheckBox.setSelected(false);
        }

    }

    public void moduleChecked(ActionEvent event) {
        if (moduleCheckBox.isSelected()) {
            moduleCheckBox.setSelected(true);
        } else {
            moduleCheckBox.setSelected(false);
        }
    }

    public void argumentChecked(ActionEvent event) {
        if (argumentCheckBox.isSelected()) {
            argumentCheckBox.setSelected(true);
        } else {
            argumentCheckBox.setSelected(false);
        }
    }

    public void saveToFile(ActionEvent event) throws IOException {
        // Ecrire code pour sauvegarder des fichiers selon les checkbox cochées
    }
}
