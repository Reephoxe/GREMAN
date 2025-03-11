package polytech.univtours.greman;

import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Slider;
import javafx.scene.layout.HBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

// Class of the elements of a resistance in the sidebar
public class ResistanceSideBar extends VBox {

    private HBox hBox;
    public Label name;

    public ResistanceSideBar(String labelText) {
        // HBox that will contain the name of the resistance and its value
        this.hBox = new HBox();
        // Name of the resistance
        name = new Label(labelText);
        // Value of the resistance
        TextField textField = new TextField("30");
        // Unit of the resistance
        Label unit = new Label("Ω");
        // Put the name and value into the HBox
        this.hBox.getChildren().addAll(name, textField, unit);
        // Padding to separate the name from the rest
        name.setPadding(new Insets(0, 7, 0, 0));
        unit.setPadding(new Insets(0, 0, 0, 3));
        name.setFont(new Font(17));
        unit.setFont(new Font(17));
        textField.setPrefWidth(130);


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
}
