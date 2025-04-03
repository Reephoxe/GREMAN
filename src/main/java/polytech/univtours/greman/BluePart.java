package polytech.univtours.greman;
import javafx.animation.PauseTransition;
import javafx.application.HostServices;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
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
import javafx.scene.control.Hyperlink;
import javafx.scene.layout.VBox;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
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
    public Integer counterResistance, counterBobine, counterCondensateur;
    public List<Node> elementList;
    public TextField searchField;
    public InfiniteImagePane infini;
    public Button test_creation;
    public Button test_condensateur;
    public Lecture_Creation_Circuit _lecture_Circuit;
    public FileChooserController fileChooserController;

    private Stage stage;
    private Scene scene;
    private Parent root;


    public void initialize() throws IOException {
        sideBar.setPrefWidth(200);
        //scrollBar.valueProperty().addListener((observable, oldValue, newValue) -> {})
        but_closeFullSceen.setVisible(false);
        counterResistance = 0;
        counterBobine = 0;
        counterCondensateur = 0;
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

    // Bouton pour ouvrir la scène (fenêtre) de changement de fichier
    public void switchToFileChooserScene(ActionEvent event) throws IOException {

        FileChooserController fileChooserController = new FileChooserController();
        fileChooserController.selectFile(event);
        _CreationAvecCSV();

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
        for (Node element : elementList) {

            //si l'element et de type Resistance sidebar
            if(element instanceof ResistanceSideBar){
                // on récupère le nom de l'élément
                String labelText = ((ResistanceSideBar) element)._getName();
                // Si le nom de l'élément ne contient pas ce qui est écrit dans la sidebar
                if (!labelText.contains(searchText)) {
                    // L'élément devient invisible
                    element.setVisible(false);
                    // L'élément n'est plus pris en compte
                    element.setManaged(false);
                } else {
                    // L'élément devient visible
                    element.setVisible(true);
                    // L'élément est pris en compte
                    element.setManaged(true);
                }
            } else if (element instanceof BobineSideBar){
                // on récupère le nom de l'élément
                String labelText = ((BobineSideBar) element)._getName();
                // Si le nom de l'élément ne contient pas ce qui est écrit dans la sidebar
                if (!labelText.contains(searchText)) {
                    // L'élément devient invisible
                    element.setVisible(false);
                    // L'élément n'est plus pris en compte
                    element.setManaged(false);
                } else {
                    // L'élément devient visible
                    element.setVisible(true);
                    // L'élément est pris en compte
                    element.setManaged(true);
                }
            } else if (element instanceof CondensateurSideBarController) {
                // on récupère le nom de l'élément
                String labelText = ((CondensateurSideBarController) element)._getName();
                // Si le nom de l'élément ne contient pas ce qui est écrit dans la sidebar
                if (!labelText.contains(searchText)) {
                    // L'élément devient invisible
                    element.setVisible(false);
                    // L'élément n'est plus pris en compte
                    element.setManaged(false);
                } else {
                    // L'élément devient visible
                    element.setVisible(true);
                    // L'élément est pris en compte
                    element.setManaged(true);
                }
            }
        }
    }

    // region mode main
    public void _ajoutResistance_Bobine(double value1,double value2) throws IOException {
        _ajouterResistance("RL",value1);
        _ajouterBobine("RL",value2);
    }
    public void _ajoutBobine_Condensateur(double value1,double value2) throws IOException {
        _ajouterBobine("BC",value1);
        _ajouterCondensateur("BC",value2);
    }
    public void _ajoutResistance_Condensateur(double value1,double value2) throws IOException {
        _ajouterResistance("RC",value1);
        _ajouterCondensateur("RC",value2);
    }
    public void _ajouterResistance(String mode, double value) throws IOException {
        ResistanceSideBar resistance = new ResistanceSideBar("R" + counterResistance + ":", value);
        elementList.add(resistance);
        sideBar.getChildren().add(resistance);
        infini._addImage("resistance.png", "R" + counterResistance,resistance.slider,mode);
        counterResistance++;
    }
    public void _ajouterCondensateur(String mode,double value) throws IOException {
        CondensateurSideBarController condensateur = new CondensateurSideBarController("C" + counterCondensateur + ":", value);
        elementList.add(condensateur);
        sideBar.getChildren().add(condensateur);
        infini._addImage("condensateur.png","C"+counterCondensateur, condensateur.slider,mode);
        counterCondensateur++;
    }
    public void _ajouterBobine(String mode,double value) throws IOException {
        BobineSideBar bobine = new BobineSideBar("L" + counterBobine + ":",value);
        elementList.add(bobine);
        sideBar.getChildren().add(bobine);
        infini._addImage("bobine.png","L"+counterBobine,bobine.slider,mode);
        counterBobine++;
    }

    public void _CreationAvecCSV() {
        _reinitialisationcircuitslidebar();
        String csvFilePath = "RLC_data.csv";
        try (BufferedReader br = new BufferedReader(new FileReader(csvFilePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                if (values.length != 3) {
                    System.out.println("Invalid line: " + line);
                    continue; // Skip invalid lines
                }

                double value1 = Double.parseDouble(values[0]);
                double value2 = Double.parseDouble(values[1]);
                double value3 = Double.parseDouble(values[2]);

                // Determine which method to call based on the values
                switch (getComponentType(value1, value2, value3)) {
                    case "Resistance":
                        _ajouterResistance("R", value1);
                        break;
                    case "Bobine":
                        _ajouterBobine("L", value2);
                        break;
                    case "Condensateur":
                        _ajouterCondensateur("C", value3);
                        break;
                    case "Resistance_condensateur":
                        _ajoutResistance_Condensateur(value1, value3);
                        break;
                    case "Resistance_bobine":
                        _ajoutResistance_Bobine(value1, value2);
                        break;
                    case "Bobine_condensateur":
                        _ajoutBobine_Condensateur(value2, value3);
                        break;
                    // Add more cases as needed
                    default:
                        System.out.println("Unknown component type for values: " + line);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void _reinitialisationcircuitslidebar() {
        for (Node element : elementList) {
            sideBar.getChildren().remove(element);
        }
        elementList.clear();
        infini._reset();
        counterResistance = 0;
        counterBobine = 0;
        counterCondensateur = 0;
    }

    private String getComponentType(double value1, double value2, double value3) {
        if (value2 == 0 && value3 == 0) {
            return "Resistance";
        } else if (value1 == 0 && value3 == 0) {
            return "Bobine";
        }else if (value1 == 0 && value2 == 0) {
            return "Condesateur";
        }
        else if (value1 != 0 && value2 == 0 && value3 != 0) {
            return "Resistance_condensateur";
        } else if (value1 == 0 && value2 != 0 && value3 != 0) {
            return "Bobine_condensateur";
        } else if (value1 != 0 && value2 != 0 && value3 == 0) {
            return "Resistance_bobine";
        }
        // Add more conditions as needed
        return "Unknown";
    }
    //endregion

    //region mode fullscreen

    public void FullScreen() throws IOException {

    }


    @FXML
    private void showAboutPopup() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("About");
        alert.setHeaderText("About this project");

        Hyperlink githubLink = new Hyperlink("https://github.com/Reephoxe/GREMAN");
        githubLink.setOnAction(e -> {
            if (Desktop.isDesktopSupported()) {
                try {
                    Desktop.getDesktop().browse(new URI(githubLink.getText()));
                } catch (IOException | URISyntaxException ex) {
                    ex.printStackTrace();
                }
            }
        });

        VBox content = new VBox();
        content.getChildren().addAll(
                new Label("Ce projet a été créé suite à la collaboration entre Polytech Tours et le laboratoire GREMAN. " +
                        "Il a été développé dans le cadre du projet collectif de 4e année.\n\n" +
                        "Chef du projet:\n- Martin Violet \n\n " +
                        "Développeurs: \n- Lilian Ghesquiere \n- Romain Lafosse \n- Maxime Marecesche \n- Noé Mennerun " +
                        "\n- Floran Schmid \n- Martin Violet \n\n" +
                        "Représentant GREMAN: \n- Ismail Aouichak\n\n" +
                        "Professeurs référants: \n- Yannick Kergosien \n- Frédéric Rayar \n\n" +
                        "Algorithmes Octave: \n- Jihane Lazouzi\n\n"),
                new Label("GitHub: "), githubLink
        );

        alert.getDialogPane().setContent(content);
        alert.showAndWait();
    }

    //endregion


}
