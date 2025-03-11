package polytech.univtours.greman;

import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.Pane;


public class InfiniteImagePane extends Pane {
    private double mouseX, mouseY;
    private double scale = 1;

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

    public void _addImage(String element_name) {
        Image image = new Image("file:src/main/resources/" + element_name);
        ImageView imageView = new ImageView(image);
        imageView.setPreserveRatio(true);
        imageView.fitWidthProperty().bind(widthProperty());
        imageView.fitHeightProperty().bind(heightProperty());

        if (!getChildren().isEmpty()) {
            Node lastChild = getChildren().get(getChildren().size() - 1);
            if (element_name.equals("condensateur.png")) {
                imageView.setTranslateY(lastChild.getTranslateY() + lastChild.getLayoutBounds().getHeight() - 75);
                imageView.setTranslateX(lastChild.getTranslateX());
            } else {
                imageView.setTranslateX(lastChild.getTranslateX() + lastChild.getLayoutBounds().getWidth());
            }
        }

        getChildren().addAll(imageView);
    }
}