package polytech.univtours.greman;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.Pane;

public class InfiniteImagePane extends Pane {
    private double mouseX, mouseY;
    private double scale = 1;

    public InfiniteImagePane() {
        // Chargez l'image (assurez-vous que le chemin soit correct)
        Image image = new Image("file:src\\main\\resources\\image.png");
        int tileSize = (int) image.getWidth();
        ImageView imageView = new ImageView(image);
        imageView.setPreserveRatio(true);
        getChildren().add(imageView);

        // Gestion du drag (déplacement de la vue)
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

        // Gestion du zoom (avec la molette)
        addEventFilter(ScrollEvent.SCROLL, e -> {
            double zoomFactor = (e.getDeltaY() > 0) ? 1.1 : 0.9;
            scale *= zoomFactor;
            setScaleX(scale);
            setScaleY(scale);
        });
    }
}
