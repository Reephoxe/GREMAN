
package polytech.univtours.greman;

import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Lecture_Creation_Circuit {
    public List<String> composants = new ArrayList<>();
    public Stage stage;

    public void _lectureCSV() {
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("CSV files (*.csv, *.s1p, *.s2p)", "*.csv", "*.s1p", "*.s2p");
        fileChooser.getExtensionFilters().add(extFilter);
        File chemin = fileChooser.showOpenDialog(stage);
        System.out.println("File path: " + chemin);

        if (chemin != null) {
            try (BufferedReader br = new BufferedReader(new FileReader(chemin))) {
                String line;
                while ((line = br.readLine()) != null) {
                    composants.add(line);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        toString();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (String line : composants) {
            sb.append(line).append("\n");
        }
        return sb.toString();
    }

    public List<String> _getComposant() {
        return composants;
    }
}