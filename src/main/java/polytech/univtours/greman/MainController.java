package polytech.univtours.greman;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;
import javafx.scene.layout.HBox;

public class MainController {
    @FXML
    private HBox topBox;

    @FXML
    private HBox centerBox;

    @FXML
    private HBox bottomBox;

    @FXML
    public void initialize() {
    }

    private Stage stage;
    private Scene scene;
    private Parent root;

    // Bouton pour revenir vers la scène (fenêtre) d'accueil
    public void switchToHelloScene(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("hello-view.fxml"));
        root = fxmlLoader.load();
        stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root, 400, 300);
        String css = getClass().getResource("helloApplication.css").toExternalForm();
        scene.getStylesheets().add(css);
        stage.setScene(scene);
        stage.show();
    }
}