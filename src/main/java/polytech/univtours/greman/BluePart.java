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

import javax.swing.*;
import java.io.BufferedReader;
import java.io.FileReader;
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

    public void _TestCreationAvecCSV() {
        String csvFilePath = "C:\\!Polytech\\Cycle ingé\\Semestre 8\\Projet coo\\GREMAN\\RLC_data.csv";
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
                        _ajoutResistance_Bobine(value1, value3);
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

        for (int i = 0; i < elementList.size(); i++) {
            Node element = elementList.get(i);
            PauseTransition pause = new PauseTransition(Duration.millis(200 * i));
            pause.setOnFinished(event -> {
                try {
                    if (element instanceof ResistanceSideBar) {
                        Fullscreen._ajouterResistance((ResistanceSideBar) element);
                    } else if (element instanceof CondensateurSideBarController) {
                        Fullscreen._ajouterCondensateur((CondensateurSideBarController) element);
                    } else if (element instanceof BobineSideBar) {
                        Fullscreen._ajouterBobine((BobineSideBar) element);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            pause.play();
        }

        stage.showAndWait(); // Attendre la fermeture avant de continuer
        // On enlève les images déjà présentes
        for(int i = 0; i < elementList.size(); i++){
            infini._removeImage();
        }
        this.counterResistance = 0;
        this.counterBobine = 0;
        this.counterCondensateur = 0;
        // On met à jour la liste des éléments
        this.updateElementList(Fullscreen);
    }

    // Variante de la fonction _ajouterResistance pour récupérer une résistance déjà existante et la mettre dans le plein écran
    public void _ajouterResistance(ResistanceSideBar resistance) {
        elementList.add(resistance);
        sideBar.getChildren().add(resistance);
        infini._addImage("resistance.png", "R" + counterResistance,resistance.slider,"R");
        counterResistance++;
    }

    // Variante de la fonction _ajouterCondensateur pour récupérer un condensateur déjà existant et le mettre dans le plein écran
    public void _ajouterCondensateur(CondensateurSideBarController condensateur) throws IOException {
        elementList.add(condensateur);
        sideBar.getChildren().add(condensateur);
        infini._addImage("condensateur.png","C"+counterCondensateur, condensateur.slider,"C");
        counterCondensateur++;
    }

    // Variante de la fonction _ajouterBobine pour récupérer une bobine déjà existante et la mettre dans le plein écran
    public void _ajouterBobine(BobineSideBar bobine) throws IOException {
        elementList.add(bobine);
        sideBar.getChildren().add(bobine);
        infini._addImage("bobine.png","L"+counterBobine,bobine.slider,"L");
        counterBobine++;
    }

    public void updateElementList(BluePart Fullscreen) {
        // On enlève les éléments déjà présents pour éviter les doublons
        for (Node element : this.elementList) {
            this.sideBar.getChildren().remove(element);
        }
        this.elementList.clear();

        // On ajoute les éléments de la liste de l'écran plein, y compris ceux qui ont pu être ajoutés entre-temps
        for (int i = 0; i < Fullscreen.elementList.size(); i++) {
            Node element = Fullscreen.elementList.get(i);
            PauseTransition pause = new PauseTransition(Duration.millis(200 * i));
            pause.setOnFinished(event -> {
                try {
                    if (element instanceof ResistanceSideBar) {
                        this._ajouterResistance((ResistanceSideBar) element);
                    } else if (element instanceof CondensateurSideBarController) {
                        this._ajouterCondensateur((CondensateurSideBarController) element);
                    } else if (element instanceof BobineSideBar) {
                        this._ajouterBobine((BobineSideBar) element);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            pause.play();
        }
    }

    //endregion


}
