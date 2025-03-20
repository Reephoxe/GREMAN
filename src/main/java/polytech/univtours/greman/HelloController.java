package polytech.univtours.greman;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;

public class HelloController {
    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    FileChooser fileChooser;

    // Fonction pour choisir un fichier
    public void selectFile(ActionEvent event) throws IOException {
        // Ouvre la pop up pour récupérer les infos nécessaires (nb bobines/condensateurs, pondérations)
        FXMLLoader loader = new FXMLLoader(getClass().getResource("input-dialog.fxml"));
        Parent dialogRoot = loader.load();
        InputDialogController inputDialogController = loader.getController();

        Stage dialogStage = new Stage();
        dialogStage.setTitle("Entrez les informations");
        dialogStage.initModality(Modality.WINDOW_MODAL);
        dialogStage.initOwner(stage);
        Scene dialogScene = new Scene(dialogRoot);
        // ------------ Ajoute le style CSS -------------
        String css = getClass().getResource("helloApplication.css").toExternalForm();
        dialogScene.getStylesheets().add(css);
        dialogStage.setScene(dialogScene);
        inputDialogController.setDialogStage(dialogStage);
        dialogStage.showAndWait();

        // Si l'utilisateur a cliqué sur OK, récupère les informations
        if (inputDialogController.isOkClicked()) {
            String nombreBobines = inputDialogController.getNombreBobines();
            String nombreCondensateurs = inputDialogController.getNombreCondensateurs();
            String ponderationBobines = inputDialogController.getPonderationBobines();
            String ponderationCondensateurs = inputDialogController.getPonderationCondensateurs();

            // Ouvre la fenêtre de gestionnaire de fichiers
            FileChooser fileChooser = new FileChooser();
            // Spécifie l'extension acceptée lors de la sélection de fichier
            FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("CSV files (*.csv, *.s1p, *.s2p)", "*.csv", "*.s1p", "*.s2p");
            fileChooser.getExtensionFilters().add(extFilter);
            // Enregistre dans file le chemin du fichier dans les dossiers
            File file = fileChooser.showOpenDialog(stage);
            System.out.println("File path: " + file);

            // Récupère les informations de la scène précédente et change de scène i.e. de fenêtre
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("main-view.fxml"));
            root = fxmlLoader.load();

            MainController mainController = fxmlLoader.getController();

            stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            // ------------ Ajoute le style CSS -------------
            scene.getStylesheets().add(css);
            // ----------------------------------------------
            stage.setScene(scene);
            // Permet de mettre en plein écran
            stage.setMaximized(true);
            // Centre la fenêtre au milieu de l'écran
            stage.centerOnScreen();
            stage.show();
        }
    }
}