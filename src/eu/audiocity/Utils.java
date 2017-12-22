package eu.audiocity;

import javafx.scene.control.Alert;
import javafx.scene.control.TextInputDialog;
import javafx.stage.Stage;

import java.io.File;
import java.util.Optional;

public class Utils {
    public static String getExtension(File file) {
        String name = file.getName();
        return name.substring(name.lastIndexOf("."));
    }

    public static Optional alert(Alert.AlertType type, String message) {
        Alert alert = new Alert(type, message);
        return alert.showAndWait();
    }

    public static Optional<Double> askForDouble(String message) {
        TextInputDialog dialog = new TextInputDialog(message);
        Optional<Double> data = Optional.empty();
        Optional<String> s = dialog.showAndWait();
        if(s.isPresent()) {
            try {
                data = Optional.of(Double.parseDouble(s.get()));
            } catch(Exception ignored) {}
        }
        return data;
    }
}
