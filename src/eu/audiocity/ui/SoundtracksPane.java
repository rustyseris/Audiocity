package eu.audiocity.ui;

import eu.audiocity.Utils;
import eu.audiocity.audio.AudioManager;
import eu.audiocity.soundtrack.Soundtrack;
import eu.audiocity.soundtrack.wav.WavSoundtrack;
import eu.audiocity.ui.soundtrack.SoundtrackPane;
import javafx.application.Platform;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;

import javax.sound.sampled.*;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class SoundtracksPane extends ScrollPane {
    private VBox container = new VBox();
    private List<SoundtrackPane> soundtrackPanes = new ArrayList<>();
    private MainWindow mainWindow;

    SoundtracksPane(MainWindow mainWindow) {
        super();
        this.container.setSpacing(5.0);
        this.setContent(this.container);
        this.setFitToWidth(true);

        this.getStyleClass().add("soundtracks-pane");

        this.mainWindow = mainWindow;
    }

    public void importSoundtrack(File file) {
        Soundtrack.Builder soundtrackTask = null;
        switch (Utils.getExtension(file)) {
            case ".wav":
                soundtrackTask = new WavSoundtrack.Builder(file);
                break;
        }

        if(soundtrackTask != null) {
            soundtrackTask.setOnSucceeded(e -> {
                Soundtrack soundtrack = (Soundtrack) e.getSource().getValue();
                this.addSountrack(soundtrack);
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

    public void addSountrack(Soundtrack soundtrack) {
        SoundtrackPane pane = new SoundtrackPane(soundtrack, this.mainWindow);
        this.container.getChildren().add(pane);
        this.soundtrackPanes.add(pane);

        if(this.mainWindow.selectedSoundtrack().getValue() == null) {
            this.mainWindow.selectedSoundtrack().setValue(soundtrack);
        }
    }

    public void removeSoundtrack(Soundtrack soundtrack) {
        for (SoundtrackPane pane : this.soundtrackPanes) {
            if(soundtrack == pane.getSoundtrack()){
                AudioManager manager = this.mainWindow.getAudioManager();
                SimpleObjectProperty<Soundtrack> selectedSoundtrack = this.mainWindow.selectedSoundtrack();
                if(selectedSoundtrack.getValue() == soundtrack) {
                    selectedSoundtrack.setValue(null);
                }
                this.container.getChildren().remove(pane);
                break;
            }
        }
    }
}
