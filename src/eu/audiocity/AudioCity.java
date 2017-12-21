package eu.audiocity;

import eu.audiocity.ui.MainWindow;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class AudioCity extends Application {
    private static final int WINDOW_WIDTH = 1280;
    private static final int WINDOW_HEIGHT = 720;
    private static final int WINDOW_MIN_WIDTH = 1280;
    private static final int WINDOW_MIN_HEIGHT = 720;
    private static final String WINDOW_TITLE = "AudioCity";

    public static void main(String[] args) {
		Application.launch(args);
    }

    @Override
    public void start(Stage stage) {
        MainWindow root = new MainWindow(stage);
        Scene scene = new Scene(root, WINDOW_WIDTH, WINDOW_HEIGHT);

        stage.setScene(scene);
        stage.setMinWidth(WINDOW_MIN_WIDTH);
        stage.setMinHeight(WINDOW_MIN_HEIGHT);
        stage.setTitle(WINDOW_TITLE);
        stage.show();
    }
}
