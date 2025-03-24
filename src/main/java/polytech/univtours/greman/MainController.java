package polytech.univtours.greman;

import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import javafx.scene.layout.HBox;
import javafx.util.Duration;

public class MainController {

    public javafx.scene.control.SplitPane SplitPane;
    @FXML
    private AnchorPane topBox;

    @FXML
    private HBox centerBox;

    @FXML
    private HBox bottomBox;

    @FXML
    private VBox sideBar;

    @FXML
    private Button toggleButton;

    @FXML
    private ScrollPane scrollPane;

    private Stage stage;
    private Scene scene;
    private Parent root;
    private Lecture_Creation_Circuit _lecture_Circuit;
    @FXML
    private BluePart bluepartController;


    @FXML
    public void initialize() {
        // Ensure BluePart controller is initialized
        if (bluepartController != null) {
            bluepartController.setLecture_Creation_Circuit(_lecture_Circuit);
        }
    }

    public void setLecture_Creation_Circuit(Lecture_Creation_Circuit objet) {
        this._lecture_Circuit = objet;
        if (bluepartController != null) {
            bluepartController.setLecture_Creation_Circuit(objet);
        }
    }


    // Bouton pour ouvrir la scène (fenêtre) de changement de fichier
    public void switchToFileChooserScene(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("file-chooser-view.fxml"));
        root = fxmlLoader.load();
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root, 400, 300);
        String css = getClass().getResource("helloApplication.css").toExternalForm();
        scene.getStylesheets().add(css);
        stage.setScene(scene);
        // Permet de ne pas mettre en plein écran
        stage.setMaximized(false);
        // Centre la fenêtre au milieu de l'écran
        stage.centerOnScreen();
        stage.show();
    }

    public void switchToSaveFileScene(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("file-save-view.fxml"));
        root = fxmlLoader.load();
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root, 400, 300);
        String css = getClass().getResource("helloApplication.css").toExternalForm();
        scene.getStylesheets().add(css);
        stage.setScene(scene);
        // Permet de ne pas mettre en plein écran
        stage.setMaximized(false);
        // Centre la fenêtre au milieu de l'écran
        stage.centerOnScreen();
        stage.show();
    }

    // Bouton pour afficher ou enlever la liste des éléments du circuit
    public void toggleSideBar(ActionEvent event) throws IOException {
        // Animations pour faire apparaître / disparaître en glissant
        TranslateTransition sideBarTransition = new TranslateTransition(Duration.millis(300), sideBar);
        TranslateTransition buttonTransition = new TranslateTransition(Duration.millis(300), toggleButton);
        TranslateTransition scrollPaneTransition = new TranslateTransition(Duration.millis(300), scrollPane);

        // Si les éléments sont cachés
        if (sideBar.getTranslateX() > (0)) {
            // Remettre les éléments à leur position d'origine
            sideBarTransition.setToX(0);
            buttonTransition.setToX(0);
            scrollPaneTransition.setToX(0);
            toggleButton.setText(">>>");
        // Si les éléments sont visibles
        } else {
            // Déplacer les éléments hors de l'écran
            sideBarTransition.setToX(200);
            buttonTransition.setToX(200);
            scrollPaneTransition.setToX(200);
            toggleButton.setText("<<<");
            System.out.println(sideBar.getHeight());
        }
        // Jouer les animations
        sideBarTransition.play();
        buttonTransition.play();
        scrollPaneTransition.play();
    }

}