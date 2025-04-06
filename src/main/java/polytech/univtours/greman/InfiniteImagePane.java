package polytech.univtours.greman;

import javafx.embed.swing.SwingFXUtils;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class InfiniteImagePane extends Pane {
    private double mouseX, mouseY; // Variables to store mouse coordinates
    private double scale = 1; // Initial scale for zooming
    private int hauteur = 0; // Initial height
    private int distance = 0; // Initial distance
    private int Ynitial = 0; // Initial Y position
    private int Xnitial = 0; // Initial X position

    public InfiniteImagePane() {
        Image image = new Image("file:src/main/resources/source.png");
        ImageView imageView = new ImageView(image);
        imageView.setPreserveRatio(true);
        imageView.fitWidthProperty().bind(widthProperty());
        imageView.fitHeightProperty().bind(heightProperty());
        getChildren().add(imageView);

        // Store initial positions
        Ynitial = (int)(getChildren().get(0).getTranslateY());
        Xnitial = (int)(getChildren().get(0).getTranslateX());

        // Handle dragging (view movement)
        setOnMousePressed(e -> {
            mouseX = e.getSceneX();
            mouseY = e.getSceneY();
        });

        setOnMouseDragged(e -> {
            double deltaX = e.getSceneX() - mouseX;
            double deltaY = e.getSceneY() - mouseY;
            setTranslateX(getTranslateX() + deltaX);
            setTranslateY(getTranslateY() + deltaY);
            mouseX = e.getSceneX();
            mouseY = e.getSceneY();
        });

        // Handle zooming (with the scroll wheel)
        addEventFilter(ScrollEvent.SCROLL, e -> {
            double zoomFactor = (e.getDeltaY() > 0) ? 1.1 : 0.9;
            scale *= zoomFactor;
            setScaleX(scale);
            setScaleY(scale);
        });
    }

    // Method to add an image to the pane
    public void _addImage(String element_name, String abreviation, Slider slider, String mode) {
        String QuelleImage = "";
        switch (mode) {
            case "R" -> QuelleImage = "resistance.png";
            case "L" -> QuelleImage = "bobine.png";
            case "C" -> QuelleImage = "condensateur.png";
            case "RL" -> {
                if (element_name.equals("resistance.png")) QuelleImage = "resistance_b.png";
                else QuelleImage = "bobine_b.png";
            }
            case "RC" -> {
                if (element_name.equals("resistance.png")) QuelleImage = "resistance.png";
                else QuelleImage = "condensateur.png";
            }
            default -> QuelleImage = "source.png";
        }

        // Load the selected image
        Image image = new Image("file:src/main/resources/" + QuelleImage);
        ImageView imageView = new ImageView(image);
        imageView.setPreserveRatio(true);
        imageView.fitWidthProperty().bind(widthProperty());
        imageView.fitHeightProperty().bind(heightProperty());

        // Create labels for the image
        Label label = new Label(abreviation);
        label.setStyle("-fx-font-weight: bold; -fx-padding: 2px;");
        label.setTranslateY(-10);

        Label valueLabel = new Label();
        valueLabel.setStyle("-fx-font-weight: bold; -fx-padding: 2px;");
        valueLabel.textProperty().bind(slider.valueProperty().asString("%.2e"));
        valueLabel.setTranslateY(10);

        // Create a stack pane to hold the image and labels
        StackPane stackPane = new StackPane();
        stackPane.getChildren().addAll(imageView, label, valueLabel);

        StackPane.setAlignment(label, Pos.CENTER);
        StackPane.setAlignment(valueLabel, Pos.CENTER);

        // Position the image based on the mode
        if (!getChildren().isEmpty()) {
            if (mode.equals("RL")) {
                if (element_name.equals("resistance.png")) {
                    _incrementDistance();
                    stackPane.setTranslateY(-75);
                    stackPane.setTranslateX(distance);
                } else if (element_name.equals("bobine.png")) {
                    stackPane.setTranslateY(75);
                    stackPane.setTranslateX(distance);
                }
            } else if (mode.equals("RC")) {
                if (element_name.equals("resistance.png")) {
                    _incrementHauteur();
                    _decrementDistance();
                    stackPane.setTranslateY(hauteur);
                    stackPane.setTranslateX(distance);
                    _incrementDistance();
                } else if (element_name.equals("condensateur.png")) {
                    stackPane.setTranslateY(hauteur);
                    stackPane.setTranslateX(distance);
                }
            }

            if (mode.equals("R")) {
                stackPane.setTranslateY(Ynitial);
                stackPane.setTranslateX(getChildren().get(0).getTranslateX() + getChildren().get(0).getLayoutBounds().getWidth() + distance);
                _incrementDistance();
            }
        }

        // Add the stack pane to the children
        getChildren().add(stackPane);
        stackPane.layout();

        // Save the current view as an image
        WritableImage writableImage = new WritableImage((int) (distance + getChildren().get(0).getLayoutBounds().getWidth() + 10), (int) (hauteur + getChildren().get(0).getLayoutBounds().getHeight() + 15));
        snapshot(null, writableImage);
        File file = new File("src/main/resources/image.png");
        try {
            ImageIO.write(SwingFXUtils.fromFXImage(writableImage, null), "png", file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Method to decrement the distance
    private void _decrementDistance() {
        distance -= (int) (getChildren().get(0).getTranslateX() + getChildren().get(0).getLayoutBounds().getWidth());
    }

    // Method to reset the pane
    public void _reset() {
        while (getChildren().size() > 1) {
            getChildren().remove(getChildren().size() - 1);
        }
        hauteur = 0;
        distance = 0;
        Xnitial = 0;
        Ynitial = 0;
    }

    // Method to calculate the average distance
    public int _moyennedistance() {
        int sum = (int) (getChildren().get(0).getTranslateX() + getChildren().get(getChildren().size() - 1).getTranslateX());
        return sum / 2;
    }

    // Method to increment the distance
    public void _incrementDistance() {
        distance += (int) (getChildren().get(0).getTranslateX() + getChildren().get(0).getLayoutBounds().getWidth());
    }

    // Method to increment the height
    public void _incrementHauteur() {
        hauteur += (int) (getChildren().get(0).getTranslateY() + getChildren().get(0).getLayoutBounds().getHeight());
    }
}