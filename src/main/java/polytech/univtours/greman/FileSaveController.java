package polytech.univtours.greman;

import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.WritableImage;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class FileSaveController {
    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    private CheckBox csvRlcCheckBox;

    @FXML
    private CheckBox circuitCheckBox;

    @FXML
    private CheckBox courbeCheckBox;

    @FXML
    private CheckBox valeurCheckBox;

    @FXML
    private CheckBox ltspiceCheckBox;

    private LineChart<Number, Number> lineChart;

    public void setLineChart(LineChart<Number, Number> lineChart) {
        this.lineChart = lineChart;
    }

    public void backToMainScene(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("main-view.fxml"));
        root = fxmlLoader.load();

        MainController mainController = fxmlLoader.getController();

        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);

        String css = getClass().getResource("helloApplication.css").toExternalForm();
        scene.getStylesheets().add(css);
        stage.setScene(scene);
        stage.setMaximized(true);
        stage.centerOnScreen();
        stage.show();
    }

    public void saveToFile(ActionEvent event) throws IOException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialFileName("files.zip");
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("ZIP files (*.zip)", "*.zip");
        fileChooser.getExtensionFilters().add(extFilter);
        File file = fileChooser.showSaveDialog(new Stage());

        if (file != null) {
            try (ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(file))) {
                if (csvRlcCheckBox.isSelected()) { // Si la case est cochée
                    addFileToZip(zos, "rlc.csv", "RLC_data.csv");
                    // On récupère le fichier csv situé dans le dossier src/main/resources et on le met dans le fichier zip
                }
                if (circuitCheckBox.isSelected()) {
                    addFileToZip(zos, "circuit.png", "src/main/resources/image.png");
                }

            }

            // Afficher une pop-up de confirmation
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Confirmation");
            alert.setHeaderText(null);
            alert.setContentText("Le fichier ZIP a été sauvegardé avec succès !");
            alert.showAndWait();

            // Fermer la fenêtre de sauvegarde
            Stage saveStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            saveStage.close();
        }
    }

    private void addFileToZip(ZipOutputStream zos, String fileName, String filePath) throws IOException {
        zos.putNextEntry(new ZipEntry(fileName));
        if (!filePath.isEmpty()) {
            if (Files.exists(Paths.get(filePath))) {
                Files.copy(Paths.get(filePath), zos);
            } else {
                System.err.println("File not found: " + filePath);
            }
        }
        zos.closeEntry();
    }

    public void _retour(ActionEvent event) {
        Stage stage1 = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage1.close();
    }
}