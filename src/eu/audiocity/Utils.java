package eu.audiocity;

import javafx.scene.control.Alert;

import java.io.File;

public class Utils {
    public static String getExtension(File file) {
        String name = file.getName();
        return name.substring(name.lastIndexOf("."));
    }

    public static void alert(Alert.AlertType type, String message) {
        Alert alert = new Alert(type, message);
        alert.showAndWait();
    }
}
