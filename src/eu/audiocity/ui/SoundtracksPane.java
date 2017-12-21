package eu.audiocity.ui;

import eu.audiocity.Utils;
import eu.audiocity.soundtrack.Soundtrack;
import eu.audiocity.soundtrack.wav.WavSoundtrack;
import eu.audiocity.ui.soundtrack.SoundtrackPane;
import javafx.concurrent.Task;
import javafx.scene.control.Alert;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;

import java.io.File;

class SoundtracksPane extends ScrollPane {
    private VBox container = new VBox();

    SoundtracksPane() {
        super();
        this.setFitToHeight(true);
        this.setFitToWidth(true);
        this.container.setSpacing(5.0);
        this.setContent(this.container);

        this.getStyleClass().add("soundtracks-pane");
    }

    void importSoundtrack(File file) {
        WavSoundtrack.Builder soundtrackTask = null;
        switch (Utils.getExtension(file)) {
            case ".wav":
                soundtrackTask = new WavSoundtrack.Builder(file);
                break;
        }

        if(soundtrackTask != null) {
            soundtrackTask.setOnSucceeded(e -> {
                System.out.println("Soundtrack loaded");
                this.container.getChildren().add(new SoundtrackPane((Soundtrack) e.getSource().getValue()));
            });


            soundtrackTask.setOnFailed(e -> {
                Throwable err = e.getSource().getException();
                err.printStackTrace();
                Utils.alert(Alert.AlertType.ERROR, "Error while importing soundtrack :c");
            });

            soundtrackTask.start();
        } else {
            Utils.alert(Alert.AlertType.ERROR, "Extension not supported");
        }
    }
}
