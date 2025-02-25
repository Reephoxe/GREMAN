package polytech.univtours.greman;

import javafx.geometry.Insets;
import javafx.scene.control.Slider;
import javafx.scene.layout.HBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

// Class of the elements of a resistance in the sidebar
public class ResistanceSideBar extends VBox {
    public ResistanceSideBar(String labelText) {
        // HBix that will contain the name of the resistance and its value
        HBox hbox = new HBox();
        // Name of the resistance
        Label label = new Label(labelText);
        // Value of the resistance
        TextField textField = new TextField("30");
        // Put the name and value into the HBox
        hbox.getChildren().addAll(label, textField);
        hbox.setSpacing(10);

        // Slider to change the value of the resistance
        Slider slider = new Slider(0, 1000, 30);
        // Listener on the slider to change the value when slider is moved
        slider.valueProperty().addListener((observable, oldValue, newValue) -> {
            textField.setText(newValue.toString());
        });
        // Listener on the value to change the slider by entering a certain value manually
        textField.setOnAction(event -> {
            try {
                float value = Float.parseFloat(textField.getText()); // Convert text to number
                if (value >= slider.getMin() && value <= slider.getMax()) {
                    slider.setValue(value); // Update slider position
                } else {
                    textField.setText(String.valueOf((float) slider.getValue())); // Reset to valid value
                }
            } catch (NumberFormatException e) {
                textField.setText(String.valueOf((float) slider.getValue())); // Reset to valid value
            }
        });
        this.getChildren().addAll(hbox, slider);
        this.setSpacing(5);
        VBox.setMargin(slider, new Insets(0, 20, 0, 10));
    }
}
