package polytech.univtours.greman;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class SharedData {
    // This class is used to share data between different controllers
    public static final StringProperty filePathProperty = new SimpleStringProperty();
}