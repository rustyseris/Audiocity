package eu.audiocity.ui;

import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;

public class MainWindow extends BorderPane {
    private TopMenu topMenu;
    private SoundtracksPane soundtracksPane = new SoundtracksPane();
    private FileChooser fileChooser = new FileChooser();
    private Stage stage;

    public MainWindow(Stage stage) {
        super();
        this.stage = stage;
        this.topMenu = new TopMenu(this);
        this.topMenu.setId("top-menu");
        this.soundtracksPane.setId("soundtracks-container");

        this.setId("root");
        this.setTop(this.topMenu);
        this.setCenter(this.soundtracksPane);

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
}
