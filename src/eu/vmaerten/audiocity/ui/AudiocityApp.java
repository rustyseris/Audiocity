package eu.vmaerten.audiocity.ui;

import eu.vmaerten.audiocity.ui.components.Audiocity;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class AudiocityApp extends Application {
    private static final long WINDOW_WIDTH = 1280;
    private static final long WINDOW_HEIGHT = 720;

    @Override
    public void start(Stage stage) throws Exception {
        Audiocity audiocity = new Audiocity(stage);
        Scene scene = new Scene(audiocity, WINDOW_WIDTH, WINDOW_HEIGHT);

        stage.setMinWidth(720);
        stage.setMinHeight(360);
        stage.setTitle("Audiocity");
        stage.setScene(scene);
        stage.show();
    }
}
