package eu.audiocity.ui;

import eu.audiocity.audio.AudioManager;
import eu.audiocity.soundtrack.Soundtrack;
import eu.audiocity.soundtrack.wav.WavSoundtrack;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;

public class MainWindow extends BorderPane {
    private TopMenu topMenu;
    private SoundtracksPane soundtracksPane;
    private AudioPlayer audioPlayer;
    private FileChooser fileChooser = new FileChooser();
    private Stage stage;
    private SimpleObjectProperty<Soundtrack> selectedSoundtrack;

    private AudioManager audioManager = new AudioManager();

    public MainWindow(Stage stage) {
        super();
        this.stage = stage;

        this.selectedSoundtrack = new SimpleObjectProperty<>(null);

        this.topMenu = new TopMenu(this);
        this.soundtracksPane = new SoundtracksPane(this);
        this.audioPlayer = new AudioPlayer(this);

        this.setId("root");
        this.topMenu.setId("top-menu");
        this.soundtracksPane.setId("soundtracks-container");
        this.audioPlayer.setId("audio-player");

        this.setTop(this.topMenu);
        this.setCenter(this.soundtracksPane);
        this.setBottom(this.audioPlayer);

        this.getStylesheets().add(this.getClass().getResource("themes/dark.css").toExternalForm());

        this.setupFileChooser();
    }

    void importSoundtrack() {
        File file = this.fileChooser.showOpenDialog(this.stage);
        if(file != null) {
            this.soundtracksPane.importSoundtrack(file);
        }
    }

    private void setupFileChooser() {
        this.fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Audio Files", "*.wav"),
                new FileChooser.ExtensionFilter("All Files", "*")
        );
        this.fileChooser.setTitle("Import Soundtrack");
        this.fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
    }

    public SoundtracksPane getSoundtracksPane() {
        return soundtracksPane;
    }

    public AudioManager getAudioManager() {
        return this.audioManager;
    }

    public SimpleObjectProperty<Soundtrack> selectedSoundtrack() {
        return this.selectedSoundtrack;
    }
}
