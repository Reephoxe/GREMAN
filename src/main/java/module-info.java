module polytech.univtours.greman {
    requires javafx.controls;
    requires javafx.fxml;


    opens polytech.univtours.greman to javafx.fxml;
    exports polytech.univtours.greman;
}