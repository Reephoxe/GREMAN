package polytech.univtours.greman;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class InputDialogController {
    @FXML
    private TextField nombreBobines;
    @FXML
    private TextField nombreCondensateurs;
    @FXML
    private TextField ponderationBobines;
    @FXML
    private TextField ponderationCondensateurs;

    private Stage dialogStage;
    private boolean okClicked = false;

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    public void isOkClicked(ActionEvent event) {
        dialogStage.close();
    }

    public String getNombreBobines() {
        return nombreBobines.getText();
    }

    public String getNombreCondensateurs() {
        return nombreCondensateurs.getText();
    }

    public String getPonderationBobines() {
        return ponderationBobines.getText();
    }

    public String getPonderationCondensateurs() {
        return ponderationCondensateurs.getText();
    }

    public boolean isOkClicked() {
        return okClicked;
    }
}