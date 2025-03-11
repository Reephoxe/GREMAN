package polytech.univtours.greman;

import javafx.animation.FadeTransition;
import javafx.animation.ScaleTransition;
import javafx.animation.TranslateTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class BluePart {
    public AnchorPane topBox;
    public Button toggleButton;
    public ScrollPane scrollPane;
    public VBox sideBar;
    public Button but_FullSceen;
    public Button but_Fichier;
    public Button but_sauvegarder;
    public Button but_closeFullSceen;
    public Button addLabelButton;
    public Integer counter;
    public List<Node> elementList;
    public TextField searchField;
    public InfiniteImagePane infini;
    public Button test_creation;
    public Button test_condensateur;
    public List<String> Composants = new ArrayList<>();

    public void initialize() throws IOException {
        sideBar.setPrefWidth(200);
        //scrollBar.valueProperty().addListener((observable, oldValue, newValue) -> {})
        but_closeFullSceen.setVisible(false);
        counter = 0;
        elementList = new ArrayList<>();

        //test ajout element de base
        Composants.add("Resistance");
        Composants.add("Condensateur");
        Composants.add("Resistance");
        Composants.add("Resistance");

    }

    public void initializeView(String MODE){

        if(Objects.equals(MODE, "FSM")){
            but_FullSceen.setVisible(false);
            but_closeFullSceen.setVisible(true);
        }

        if (Objects.equals(MODE, "MAIN")) {
            but_FullSceen.setVisible(true);
            but_closeFullSceen.setVisible(false);
        }
    }
    // Bouton pour afficher ou enlever la liste des éléments du circuit
    public void toggleSideBar(ActionEvent event) throws IOException {
        // Animations pour faire apparaître / disparaître en glissant
        TranslateTransition sideBarTransition = new TranslateTransition(Duration.millis(300), sideBar);
        TranslateTransition buttonTransition = new TranslateTransition(Duration.millis(300), toggleButton);
        TranslateTransition buttonFillsreen = new TranslateTransition(Duration.millis(300), but_FullSceen);
        TranslateTransition buttoncloseFillsreen = new TranslateTransition(Duration.millis(300), but_closeFullSceen);
        TranslateTransition scrollPaneTransition = new TranslateTransition(Duration.millis(300), scrollPane);

        // Si les éléments sont cachés
        if (sideBar.getTranslateX() > (0)) {
            // Remettre les éléments à leur position d'origine
            sideBarTransition.setToX(0);
            buttonTransition.setToX(0);
            scrollPaneTransition.setToX(0);
            buttonFillsreen.setToX(0);
            buttoncloseFillsreen.setToX(0);
            toggleButton.setText(">>>");
            // Si les éléments sont visibles
        } else {
            // Déplacer les éléments hors de l'écran
            sideBarTransition.setToX(200);
            buttonTransition.setToX(200);
            scrollPaneTransition.setToX(200);
            buttonFillsreen.setToX(200);
            buttoncloseFillsreen.setToX(200);
            toggleButton.setText("<<<");
            System.out.println(sideBar.getHeight());
        }
        // Jouer les animations
        sideBarTransition.play();
        buttonTransition.play();
        scrollPaneTransition.play();
        buttonFillsreen.play();
        buttoncloseFillsreen.play();
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
        stage.setTitle("BluePart");
        stage.setScene(scene);
        stage.setMaximized(true);
        //transitionZoom(root);

        stage.showAndWait();  // Attendre la fermeture avant de continuer
    }
    // Bouton pour ouvrir la scène (fenêtre) de changement de fichier
    public void switchToFileChooserScene(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("file-chooser-view.fxml"));
        Parent root = fxmlLoader.load();

        FileChooserController fileChooserController = fxmlLoader.getController();

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 400, 300);
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

        // Récupère le fichier FXML
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("file-save-view.fxml"));
        Parent root = fxmlLoader.load();

        // Récupère le contrôleur de la vue
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 400, 300);

        // Ajoute le style CSS
        String css = getClass().getResource("helloApplication.css").toExternalForm();
        scene.getStylesheets().add(css);
        stage.setScene(scene);

        // Permet de ne pas mettre en plein écran
        stage.setMaximized(false);

        // Centre la fenêtre au milieu de l'écran
        stage.centerOnScreen();
        stage.show();
    }

    public void transitionZoom(Parent parent) {
        ScaleTransition scaleTransition = new ScaleTransition(Duration.millis(500), parent);
        scaleTransition.setFromY(0.0);
        scaleTransition.setToY(1.0);
        scaleTransition.setFromX(1.0); // Keep the X-axis scale unchanged
        scaleTransition.setToX(1.0);   // Keep the X-axis scale unchanged
        scaleTransition.play();
    }

    public void closeFullScreen(ActionEvent actionEvent) {
        initializeView("MAIN");
        Stage stage = (Stage) but_closeFullSceen.getScene().getWindow();
        stage.close();
    }

    public void addLabel(ActionEvent actionEvent) {
        ResistanceSideBar resistanceSideBar = new ResistanceSideBar("R" + counter + ":");
        elementList.add(resistanceSideBar);
        counter++;
        sideBar.getChildren().add(resistanceSideBar);
        infini._addImage("resistance.png");
    }

    public void searchElement(ActionEvent actionEvent) {
        // On récupère le texte entré dans la barre de recherche
        String searchText = searchField.getText();
        // Pour chaque élément présent dans la sidebar
        for (Node resistanceSideBar : elementList) {

            //si l'element et de type Resistance sidebar
            if(resistanceSideBar instanceof ResistanceSideBar){
                // on récupère le nom de l'élément
                String labelText = ((ResistanceSideBar) resistanceSideBar)._getName();
                // Si le nom de l'élément ne contient pas ce qui est écrit dans la sidebar
                if (!labelText.contains(searchText)) {
                    // L'élément devient invisible
                    resistanceSideBar.setVisible(false);
                    // L'élément n'est plus pris en compte
                    resistanceSideBar.setManaged(false);
                } else {
                    // L'élément devient visible
                    resistanceSideBar.setVisible(true);
                    // L'élément est pris en compte
                    resistanceSideBar.setManaged(true);
                }
            }
        }
    }


    public void _boiteDialogueAjoutElement(){
        // Create a new dialog
        Dialog<String> dialog = new Dialog<>();
        dialog.setTitle("Select Element");

        // Set the button types
        ButtonType addButtonType = new ButtonType("Add", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(addButtonType, ButtonType.CANCEL);

        // Create the ComboBox and populate it with options
        ComboBox<String> comboBox = new ComboBox<>();
        comboBox.getItems().addAll("Resistance", "Condensateur", "Bobine");

        // Create a layout for the dialog
        VBox vbox = new VBox(comboBox);
        vbox.setSpacing(10);
        dialog.getDialogPane().setContent(vbox);

        // Convert the result to the selected item when the add button is clicked
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == addButtonType) {
                return comboBox.getSelectionModel().getSelectedItem();
            }
            return null;
        });

        // Show the dialog and wait for the result
        dialog.showAndWait().ifPresent(selectedItem -> {
            String imagePath = "";
            switch (selectedItem) {
                case "Resistance":
                    imagePath = "resistance.png";
                    break;
                case "Condensateur":
                    imagePath = "condensateur.png";
                    break;
                case "Bobine":
                    imagePath = "bobine.png";
                    break;
            }

            // Add the selected image to the InfiniteImagePane
            //infini._addImage(imagePath);
        });
    }

    public void _CreationCircuit() throws IOException {

        for(String element : Composants){
            if (element.equals("Resistance")){
                _ajouterResistance();
            }
            else if (element.equals("Condensateur")) {
                _ajouterCondensateur();
            }
            else if (element.equals("Bobine")) {
                //_ajouterBobine();
            }
        }

    }

    public void _ajouterResistance(){
        ResistanceSideBar resistance = new ResistanceSideBar("R" + counter + ":");
        elementList.add(resistance);
        sideBar.getChildren().add(resistance);
        infini._addImage("resistance.png");
        counter++;
    }

    public void _ajouterCondensateur() throws IOException {
        CondensateurSideBarController condensateur = new CondensateurSideBarController("C" + counter + ":");
        elementList.add(condensateur);
        sideBar.getChildren().add(condensateur);
        infini._addImage("condensateur.png");
        counter++;
    }
}
