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

public class FileChooserController {
    public Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    FileChooser fileChooser;

    // Fonction pour choisir un fichier
    public String selectFile(ActionEvent event) throws IOException {

        String fileName = "";
        // Ouvre la fenêtre de gestionnaire de fichiers
        FileChooser fileChooser = new FileChooser();
        // Spécifie l'extension acceptée lors de la sélection de fichier
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("CSV files (*.csv, *.s1p, *.s2p)", "*.csv", "*.s1p", "*.s2p");
        fileChooser.getExtensionFilters().add(extFilter);
        // Enregistre dans file le chemin du fichier dans les dossiers
        File file = fileChooser.showOpenDialog(stage);
        System.out.println("File path: " + file);

        if (file != null) {
            fileName = file.getName();
            SharedData.filePathProperty.set(file.getAbsolutePath()); // NOTIFICATION GLOBALE ICI

            if (fileName.toLowerCase().endsWith(".s1p") || fileName.toLowerCase().endsWith(".s2p")) {
                double[][] resultat = Executable.executeFile(file.getPath(), "4", "2", "7", "4.05");
            }
        }
        return fileName;
    }

    public void backToMainScene(ActionEvent event) throws IOException {
        // Récupère les informations de la scène précédente et change de scène i.e. de fenêtre
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("main-view.fxml"));
        root = fxmlLoader.load();

        MainController mainController = fxmlLoader.getController();

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

    public void _retour(ActionEvent event) {
        Stage stage1 = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage1.close();
    }


}
