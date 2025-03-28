package polytech.univtours.greman;

import javafx.geometry.Insets;
import javafx.scene.control.Slider;
import javafx.scene.layout.HBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

// Class of the elements of a coil in the sidebar
public class BobineSideBar extends VBox {

    private HBox hBox;
    public Slider slider;
    public Label name;
    public TextField textField;

    public BobineSideBar(String labelText, double valeur) {
        // HBox that will contain the name of the coil and its value
        this.hBox = new HBox();
        // Name of the ccoil
        name = new Label(labelText);
        // Value of the coil
        textField = new TextField(String.valueOf(valeur));
        // Unit of the coil
        Label unit = new Label("H");
        // Put the name and value into the HBox
        this.hBox.getChildren().addAll(name, textField, unit);
        // Padding to separate the name from the rest
        name.setPadding(new Insets(0, 7, 0, 0));
        unit.setPadding(new Insets(0, 0, 0, 3));
        name.setFont(new Font(17));
        unit.setFont(new Font(17));
        textField.setPrefWidth(130);

        // Slider to change the value of the resistance
        slider = new Slider(0, +10000000, valeur);
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
        this.getChildren().addAll(this.hBox, slider);
        this.setSpacing(5);
        VBox.setMargin(slider, new Insets(0, 20, 0, 10));
    }

    // Getter to get the HBox child Label, and get the name of the label
    public String getHBoxLabelName() {
        Label label = (Label)hBox.getChildren().getFirst();
        return label.getText();
    }

    public String _getName(){
        return name.getText();
    }

    public double _getSliderValue() {
        return slider.getValue();
    }

    public void _setSliderValue(double value) {
        slider.setValue(value);
    }
}
