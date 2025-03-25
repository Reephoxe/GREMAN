package polytech.univtours.greman;

import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;


public class InfiniteImagePane extends Pane {
    private double mouseX, mouseY;
    private double scale = 1;
    private int hauteur = 0;
    private int distance = 0;

    public InfiniteImagePane() {
        // Load the image (ensure the path is correct)
        Image image = new Image("file:src/main/resources/source.png");
        ImageView imageView = new ImageView(image);
        imageView.setPreserveRatio(true);
        imageView.fitWidthProperty().bind(widthProperty());
        imageView.fitHeightProperty().bind(heightProperty());
        getChildren().add(imageView);

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

    public void _addImage(String element_name, String abreviation, Slider slider,String mode) {
        Image image = new Image("file:src/main/resources/" + element_name);
        ImageView imageView = new ImageView(image);
        imageView.setPreserveRatio(true);
        imageView.fitWidthProperty().bind(widthProperty());
        imageView.fitHeightProperty().bind(heightProperty());

        Label label = new Label(abreviation);
        label.setStyle("-fx-font-weight: bold; -fx-padding: 2px;");
        label.setTranslateY(-10);

        Label valueLabel = new Label();
        valueLabel.setStyle("-fx-font-weight: bold; -fx-padding: 2px;");
        valueLabel.textProperty().bind(slider.valueProperty().asString("%.2f"));
        valueLabel.setTranslateY(10);


        StackPane stackPane = new StackPane();
        stackPane.getChildren().addAll(imageView, label, valueLabel);

        StackPane.setAlignment(label, Pos.CENTER);
        StackPane.setAlignment(valueLabel, Pos.CENTER);

        if (!getChildren().isEmpty()) {
            Node lastChild = getChildren().get(getChildren().size() - 1);
            if(mode.equals("RL")){
                if(element_name.equals("resistance.png")){
                    stackPane.setTranslateY(lastChild.getTranslateY() + lastChild.getLayoutBounds().getHeight() -350);
                    stackPane.setTranslateX(distance);
                }else if (element_name.equals("bobine.png")){
                    stackPane.setTranslateX(lastChild.getTranslateX() + lastChild.getLayoutBounds().getWidth()+800);
                    stackPane.setTranslateX(distance);
                    _incrementDistance();
                }
            }
            else if (mode.equals("RC")){
                int positionX = (int) (getChildren().get(0).getTranslateX() + getChildren().get(0).getLayoutBounds().getWidth()+distance);
                int positionY = (int) (getChildren().get(0).getTranslateY() + getChildren().get(0).getLayoutBounds().getHeight()+hauteur);
                if(element_name.equals("resistance.png")){
                    stackPane.setTranslateY(positionY);
                    stackPane.setTranslateX(positionX);
                    _incrementHauteur();
                    _incrementDistance();
                }else if (element_name.equals("condensateur.png")){
                    stackPane.setTranslateY(lastChild.getTranslateY() + lastChild.getLayoutBounds().getHeight());
                    stackPane.setTranslateX(positionX);
                    _decrementDistance();
                }
            }
            else if(mode.equals("R")){
                    stackPane.setTranslateY(getChildren().get(0).getTranslateY()+getChildren().get(0).getLayoutBounds().getHeight());
                    stackPane.setTranslateX(lastChild.getTranslateX() + lastChild.getLayoutBounds().getWidth());
                    _incrementDistance();
            }



        }
        getChildren().add(stackPane);
        stackPane.layout();
    }

    private void _decrementDistance() {
        distance -= (int) (getChildren().get(0).getTranslateX() + getChildren().get(0).getLayoutBounds().getWidth());
    }

    public void _removeImage() {
        if (!getChildren().isEmpty()) {
            getChildren().remove(getChildren().size() - 1);
        }
    }

    public int _moyennedistance(){
        int sum = (int) (getChildren().get(0).getTranslateX()+getChildren().get(getChildren().size()-1).getTranslateX());
        return sum/2;
    }

    public void _incrementDistance(){
        distance += (int) (getChildren().get(0).getTranslateX() + getChildren().get(0).getLayoutBounds().getWidth());
    }

    public void _incrementHauteur(){
        hauteur += (int) (getChildren().get(0).getTranslateY() + getChildren().get(0).getLayoutBounds().getHeight());
    }

}