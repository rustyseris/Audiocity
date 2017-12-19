package eu.vmaerten.audiocity.ui.components;

import eu.vmaerten.audiocity.soundtrack.Soundtrack;
import eu.vmaerten.audiocity.soundtrack.formats.WavSoundtrack;
import eu.vmaerten.audiocity.ui.AudiocityApp;
import javafx.concurrent.Task;
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
    private FileChooser fileChooser = new FileChooser();

    public Audiocity(Stage stage) {
        super();
        this.stage = stage;
        this.setId("root");

        this.getStylesheets().add(AudiocityApp.class.getResource("themes/dark.css").toExternalForm());

        this.setupMenu();
        this.setupSoundtracksPanel();
        this.setupFileChooser();

        this.doImportSoundtrack(new File("/home/xayah/Music/audio.wav"));
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

    private void setupFileChooser() {
        this.fileChooser = new FileChooser();
        this.fileChooser.setTitle("Import a soundtrack");
        this.fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Audio Files", "*.wav", "*.ogg", "*.mp3"),
                new FileChooser.ExtensionFilter("All files", "*")
        );
    }

    private void importSoundtrack() {
        File selectedFile = this.fileChooser.showOpenDialog(this.stage);
        if(selectedFile != null) {
            this.doImportSoundtrack(selectedFile);
        }
    }

    private void doImportSoundtrack(File file) {
        System.out.println("doImportSoundtrack");

        Task<Soundtrack> soundtrackLoader = new SoundtrackLoader(file);

        soundtrackLoader.setOnSucceeded(e -> {
            Soundtrack soundtrack = soundtrackLoader.getValue();
            if(soundtrack != null){
                this.addSoundtrack(soundtrack);
            } else {
                Audiocity.showAlert(Alert.AlertType.ERROR, "Unsupported file extension");
            }
        });

        soundtrackLoader.setOnFailed(e -> {
            Audiocity.showAlert(Alert.AlertType.ERROR, soundtrackLoader.getException().getMessage());
        });

        Thread thread = new Thread(soundtrackLoader);
        thread.setDaemon(true);
        thread.start();
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

    private class SoundtrackLoader extends Task<Soundtrack> {
        private File file;
        SoundtrackLoader(File file) {
            this.file = file;
        }

        @Override
        protected Soundtrack call() throws Exception {
            System.out.println("SoundtrackLoader.call()");

            Soundtrack soundtrack = null;
            String extension = Audiocity.getExtension(this.file);
            switch (extension) {
                case ".wav":
                    soundtrack = new WavSoundtrack(this.file.getPath());
                    break;
            }

            return soundtrack;
        }
    }
}
