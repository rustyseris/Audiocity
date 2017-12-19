package eu.vmaerten.audiocity.ui.components;

import eu.vmaerten.audiocity.soundtrack.Soundtrack;
import eu.vmaerten.audiocity.soundtrack.formats.WavSoundtrack;
import eu.vmaerten.audiocity.ui.AudiocityApp;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Audiocity extends BorderPane {
    private List<SoundtrackPane> soundtracks;
    private VBox soundtracksContainer;
    private Stage stage;

    public Audiocity(Stage stage) {
        super();
        this.stage = stage;
        this.setId("root");

        this.getStylesheets().add(AudiocityApp.class.getResource("themes/dark.css").toExternalForm());

        this.setupMenu();
        this.setupSoundtracksPanel();

        this.importWavSoundtrack("/home/xayah/Music/audio.wav");
    }

    private void setupMenu() {
        MenuBar menu = new MenuBar();
        menu.setBackground(new Background(new BackgroundFill(Color.DARKGREY, CornerRadii.EMPTY, Insets.EMPTY)));
        this.setTop(menu);

        Menu file = new Menu("File");
        MenuItem import_soundtrack = new MenuItem("Import soundtrack");
        import_soundtrack.setOnAction(actionEvent -> this.importSoundtrack());
        file.getItems().addAll(import_soundtrack);

        Menu edit = new Menu("Edit");
        menu.getMenus().addAll(file, edit);
    }

    private void setupSoundtracksPanel() {
        ScrollPane soundtracks_container_scrollable = new ScrollPane();
        soundtracks_container_scrollable.setFitToHeight(true);
        soundtracks_container_scrollable.setFitToWidth(true);

        this.soundtracks = new ArrayList<>();
        this.soundtracksContainer = new VBox();
        this.soundtracksContainer.setSpacing(10);

        this.soundtracksContainer.setId("soundtrack-container");

        soundtracks_container_scrollable.setContent(this.soundtracksContainer);
        this.setCenter(soundtracks_container_scrollable);
    }

    private void importSoundtrack() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Import a soundtrack");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Audio Files", "*.wav", "*.ogg", "*.mp3"),
                new FileChooser.ExtensionFilter("All files", "*")
        );

        File selectedFile = fileChooser.showOpenDialog(this.stage);
        if(selectedFile != null) {
            String extension = Audiocity.getExtension(selectedFile);
            switch (extension) {
                case ".wav":
                    this.importWavSoundtrack(selectedFile.getPath());
                    break;
                default:
                    Audiocity.showAlert(Alert.AlertType.ERROR, "Unsupported file extension");
            }
        }
    }

    private void importWavSoundtrack(String path) {
        try {
            this.addSoundtrack(new WavSoundtrack(path));
        } catch (Exception e) {
            e.printStackTrace();
            Audiocity.showAlert(Alert.AlertType.ERROR, "An error occured when importing a WAV soundtrack. (more info in the console)");
        }
    }

    private void addSoundtrack(Soundtrack soundtrack) {
        SoundtrackPane pane = new SoundtrackPane(soundtrack);
        this.soundtracks.add(pane);
        this.soundtracksContainer.getChildren().add(pane);
    }

    private static String getExtension(File file) {
        String fileName = file.getName();

        int extensionIndex = fileName.lastIndexOf('.');
        if(extensionIndex > -1) {
            return fileName.substring(extensionIndex);
        } else {
            return "";
        }
    }

    private static void showAlert(Alert.AlertType type, String message) {
        Alert alert = new Alert(type, message);
        alert.showAndWait();
    }
}
