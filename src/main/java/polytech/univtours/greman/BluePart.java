package polytech.univtours.greman;
import javafx.animation.PauseTransition;
import javafx.util.Duration;
import javafx.animation.FadeTransition;
import javafx.animation.PauseTransition;
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
    public Lecture_Creation_Circuit _lecture_Circuit;


    public void initialize() throws IOException {
        sideBar.setPrefWidth(200);
        //scrollBar.valueProperty().addListener((observable, oldValue, newValue) -> {})
        but_closeFullSceen.setVisible(false);
        counter = 0;
        elementList = new ArrayList<>();

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

        Stage popupStage = new Stage();
        Scene scene = new Scene(root, 600, 600);
        String css = getClass().getResource("helloApplication.css").toExternalForm();
        scene.getStylesheets().add(css);
        popupStage.setScene(scene);
        popupStage.initModality(Modality.APPLICATION_MODAL); // Make it a modal window
        popupStage.setTitle("File Chooser");
        popupStage.setMaximized(false); // Do not maximize
        popupStage.centerOnScreen(); // Center the window
        popupStage.showAndWait(); // Show the pop-up and wait for it to close
    }

    public void switchToSaveFileScene(ActionEvent event) throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("file-save-view.fxml"));
        Parent root = fxmlLoader.load();

        Stage popupStage = new Stage();
        Scene scene = new Scene(root, 600, 600);
        String css = getClass().getResource("helloApplication.css").toExternalForm();
        scene.getStylesheets().add(css);
        popupStage.setScene(scene);
        popupStage.initModality(Modality.APPLICATION_MODAL); // Make it a modal window
        popupStage.setTitle("Save File");
        popupStage.setMaximized(false); // Do not maximize
        popupStage.centerOnScreen(); // Center the window
        popupStage.showAndWait(); // Show the pop-up and wait for it to close
    }

    public void closeFullScreen(ActionEvent actionEvent) {
        initializeView("MAIN");
        Stage stage = (Stage) but_closeFullSceen.getScene().getWindow();
        stage.close();
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

    public void _CreationCircuit() throws IOException {
        List<String> listelement = List.of("Resistance","Resistance", "Condensateur", "Bobine");
        int delay = 200; // Delay in milliseconds

        for (int i = 0; i < listelement.size(); i++) {
            String element = listelement.get(i);
            PauseTransition pause = new PauseTransition(Duration.millis(delay * i));
            pause.setOnFinished(event -> {
                try {
                    switch (element) {
                        case "Resistance" -> _ajouterResistance();
                        case "Condensateur" -> _ajouterCondensateur();
                        case "Bobine" -> _ajouterBobine();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            pause.play();
        }
    }

    public void _TestCreationAvecCSV(){
        _lecture_Circuit._lectureCSV();
        int delay = 200; // Delay in milliseconds
        System.out.println(_lecture_Circuit.composants);

        for (int i = 0; i < _lecture_Circuit._getComposant().size(); i++) {
            String element = _lecture_Circuit._getComposant().get(i);
            PauseTransition pause = new PauseTransition(Duration.millis(delay * i));
            pause.setOnFinished(event -> {
                try {
                    switch (element) {
                        case "Resistance" -> _ajouterResistance();
                        case "Condensateur" -> _ajouterCondensateur();
                        case "Bobine" -> _ajouterBobine();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            pause.play();
        }


    }

    public void _ajouterResistance(){
        ResistanceSideBar resistance = new ResistanceSideBar("R" + counter + ":");
        elementList.add(resistance);
        sideBar.getChildren().add(resistance);
        infini._addImage("resistance.png", "R" + counter,resistance.slider);
        counter++;
    }

    public void _ajouterCondensateur() throws IOException {
        CondensateurSideBarController condensateur = new CondensateurSideBarController("C" + counter + ":");
        elementList.add(condensateur);
        sideBar.getChildren().add(condensateur);
        infini._addImage("condensateur.png","C"+counter, condensateur.slider);
        counter++;
    }

    public void _ajouterBobine() throws IOException {
        BobineSideBar bobine = new BobineSideBar("L" + counter + ":");
        elementList.add(bobine);
        sideBar.getChildren().add(bobine);
        infini._addImage("bobine.png","L"+counter,bobine.slider);
        counter++;
    }

    public void setLecture_Creation_Circuit(Lecture_Creation_Circuit lecture_Creation_Circuit) {
        this._lecture_Circuit = lecture_Creation_Circuit;
    }
}
