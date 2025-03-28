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


//classe ne s'utilise plus

public class HelloController {
    /*private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    FileChooser fileChooser;

    // Fonction pour choisir un fichier
    public void selectFile(ActionEvent event) throws IOException {
        // Ouvre la pop up pour récupérer les infos nécessaires (nb bobines/condensateurs, pondérations)
        // Récupère les informations de la scène précédente et change de scène i.e. de fenêtre
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("input-dialog.fxml"));
        root = fxmlLoader.load();
        InputDialogController inputDialogController = fxmlLoader.getController();

        Stage dialogStage = new Stage();
        dialogStage.setTitle("Entrez les informations");
        dialogStage.initModality(Modality.WINDOW_MODAL);
        dialogStage.initOwner(stage);
        Scene dialogScene = new Scene(root);
        // ------------ Ajoute le style CSS -------------
        String css = getClass().getResource("helloApplication.css").toExternalForm();
        dialogScene.getStylesheets().add(css);
        dialogStage.setScene(dialogScene);
        inputDialogController.setDialogStage(dialogStage);
        dialogStage.showAndWait();

        Lecture_Creation_Circuit _lecture = new Lecture_Creation_Circuit();
        _lecture._lectureCSV();

        /*if (file != null) {
            String fileName = file.getName();
            if (fileName.endsWith(".s1p") || fileName.endsWith(".s2p")) {
                // Si le fichier est de type s1p ou s2p, appelle la méthode sparameter
                Executable.executeFile(file.getPath(), inputDialogController.getNombreCondensateurs().toString(), inputDialogController.getNombreBobines().toString() ,inputDialogController.getPonderationCondensateurs().toString() ,inputDialogController.getPonderationBobines().toString());
            }
        }

        // Récupère les informations de la scène précédente et change de scène i.e. de fenêtre
        fxmlLoader = new FXMLLoader(getClass().getResource("main-view.fxml"));
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

        System.out.println("Nb bobines: " + inputDialogController.getNombreBobines());
        System.out.println("Nb condensateurs: " + inputDialogController.getNombreCondensateurs());
        System.out.println("Pondération bobines: " + inputDialogController.getPonderationBobines());
        System.out.println("Pondération condensateurs: " + inputDialogController.getPonderationCondensateurs());*/

    }
