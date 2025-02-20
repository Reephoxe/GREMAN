module polytech.univtours.greman {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.jfree.jfreechart;
    requires java.desktop;
    requires javaoctave;


    opens polytech.univtours.greman to javafx.fxml;
    exports polytech.univtours.greman;
}