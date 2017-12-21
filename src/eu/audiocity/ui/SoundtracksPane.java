package eu.audiocity.ui;

import eu.audiocity.Utils;
import eu.audiocity.soundtrack.Soundtrack;
import eu.audiocity.soundtrack.wav.WavSoundtrack;
import eu.audiocity.ui.soundtrack.SoundtrackPane;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;

import javax.sound.sampled.*;
import java.io.File;

class SoundtracksPane extends ScrollPane {
    private VBox container = new VBox();
    private MainWindow mainWindow;

    SoundtracksPane(MainWindow mainWindow) {
        super();
        this.container.setSpacing(5.0);
        this.setContent(this.container);

        this.getStyleClass().add("soundtracks-pane");

        this.mainWindow = mainWindow;
    }

    void importSoundtrack(File file) {
        Soundtrack.Builder soundtrackTask = null;
        switch (Utils.getExtension(file)) {
            case ".wav":
                soundtrackTask = new WavSoundtrack.Builder(file);
                break;
        }

        if(soundtrackTask != null) {
            soundtrackTask.setOnSucceeded(e -> {
                System.out.println("Soundtrack loaded");
                Soundtrack soundtrack = (Soundtrack) e.getSource().getValue();
                this.container.getChildren().add(new SoundtrackPane(soundtrack, this.mainWindow));
                if(this.mainWindow.selectedSoundtrack().getValue() == null) {
                    this.mainWindow.selectedSoundtrack().setValue(soundtrack);
                }
            });

            soundtrackTask.setOnFailed(e -> {
                Throwable err = e.getSource().getException();
                err.printStackTrace();
                Utils.alert(Alert.AlertType.ERROR, "Error while importing soundtrack :c");
            });

            soundtrackTask.startTask();
        } else {
            Utils.alert(Alert.AlertType.ERROR, "Extension not supported");
        }
    }
}
