package polytech.univtours.greman;

import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

public class Lecture_Creation_Circuit {
    public List<String> composants = new java.util.ArrayList<String>();
    public Stage stage;

    public void _lectureCSV(){

        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("CSV files (*.csv, *.s1p, *.s2p)", "*.csv", "*.s1p", "*.s2p");
        fileChooser.getExtensionFilters().add(extFilter);
        File chemin = fileChooser.showOpenDialog(stage);
        System.out.println("File path: " + chemin);

        if (chemin != null) {
            try (BufferedReader br = new BufferedReader(new FileReader(chemin))) {
                String line;
                while ((line = br.readLine()) != null) {
                    String[] values = line.split(",");
                    if (values.length == 3) {
                        if (values[0].equals("1")) {
                            composants.add("Resistance");
                        }
                        if (values[1].equals("1")) {
                            composants.add("Bobine");
                        }
                        if (values[2].equals("1")) {
                            composants.add("Condensateur");
                        }
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public String toString(){
            return composants.toString();
    }

    public List<String> _getComposant(){
        return composants;
    }

}
