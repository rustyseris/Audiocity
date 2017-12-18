package eu.vmaerten.audiocity.ui;

import eu.vmaerten.audiocity.soundtrack.Soundtrack;
import eu.vmaerten.audiocity.soundtrack.formats.WavSoundtrack;
import eu.vmaerten.audiocity.ui.Components.SoundtrackPane;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MainWindow extends Application {
    private static final long WINDOW_WIDTH = 1280;
    private static final long WINDOW_HEIGHT = 720;

    private List<SoundtrackPane> soundtracks;
    private VBox soundtracksContainer;
    private BorderPane root;
    private Stage mainStage;

    @Override
    public void start(Stage stage) throws Exception {
        this.mainStage = stage;
        this.root = new BorderPane();
        this.root.setPadding(new Insets(0, 0, 0, 0));
        Scene scene = new Scene(this.root, WINDOW_WIDTH, WINDOW_HEIGHT);

        this.setupMenu();
        this.setupSoundtracksPanel();

        stage.setMinWidth(720);
        stage.setMinHeight(360);
        stage.setTitle("Audiocity");
        stage.setScene(scene);
        stage.show();
    }

    private void setupSoundtracksPanel() throws Exception {
        ScrollPane soundtracks_container_scrollable = new ScrollPane();
        soundtracks_container_scrollable.setFitToHeight(true);
        soundtracks_container_scrollable.setFitToWidth(true);

        this.soundtracks = new ArrayList<>();
        this.soundtracksContainer = new VBox();
        this.soundtracksContainer.setSpacing(10);

        soundtracks_container_scrollable.setContent(this.soundtracksContainer);
        this.root.setCenter(soundtracks_container_scrollable);
    }

    private void setupMenu() {
        MenuBar menu = new MenuBar();
        menu.setBackground(new Background(new BackgroundFill(Color.DARKGREY, CornerRadii.EMPTY, Insets.EMPTY)));
        this.root.setTop(menu);

        Menu file = new Menu("File");
        MenuItem import_soundtrack = new MenuItem("Import soundtrack");
        import_soundtrack.setOnAction(actionEvent -> this.importSoundtrack());
        file.getItems().addAll(import_soundtrack);

        Menu edit = new Menu("Edit");
        menu.getMenus().addAll(file, edit);
    }

    private void importSoundtrack() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Import a soundtrack");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Audio Files", "*.wav", "*.ogg", "*.mp3"),
                new FileChooser.ExtensionFilter("All files", "*")
        );

        File selectedFile = fileChooser.showOpenDialog(this.mainStage);
        if(selectedFile != null) {
            String extension = this.getExtension(selectedFile);
            switch (extension) {
                case ".wav":
                    this.importWavSoundtrack(selectedFile.getPath());
                    break;
                default:
                    this.showAlert(Alert.AlertType.ERROR, "Unsupported file extension");
            }
        }
    }

    private void importWavSoundtrack(String path) {
        try {
            this.addSoundtrack(new WavSoundtrack(path));
        } catch (Exception e) {
            e.printStackTrace();
            this.showAlert(Alert.AlertType.ERROR, "An error occured when importing a WAV soundtrack. (more info in the console)");
        }
    }

    private void addSoundtrack(Soundtrack soundtrack) {
        SoundtrackPane pane = new SoundtrackPane(soundtrack);
        this.soundtracks.add(pane);
        this.soundtracksContainer.getChildren().add(pane);
    }

    private void showAlert(Alert.AlertType type, String message) {
        Alert alert = new Alert(type, message);
        alert.showAndWait();
    }

    private String getExtension(File file) {
        String fileName = file.getName();

        int extensionIndex = fileName.lastIndexOf('.');
        if(extensionIndex > -1) {
            return fileName.substring(extensionIndex);
        } else {
            return "";
        }
    }
}
