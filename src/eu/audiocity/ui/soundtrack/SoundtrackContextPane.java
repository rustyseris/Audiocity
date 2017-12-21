package eu.audiocity.ui.soundtrack;

import eu.audiocity.soundtrack.Soundtrack;
import eu.audiocity.ui.MainWindow;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

class SoundtrackContextPane extends VBox {
    private Soundtrack soundtrack;
    private MainWindow mainWindow;

    SoundtrackContextPane(Soundtrack soundtrack, MainWindow mainWindow) {
        this.mainWindow = mainWindow;
        this.soundtrack = soundtrack;
        this.setPrefWidth(200);
        this.setMinHeight(200);

        HBox top = new HBox();
        Button closeSoundtrack = new Button("×");
        Text name = new Text(this.soundtrack.getName());
        top.getChildren().addAll(closeSoundtrack, name);

        Text duration = new Text("Duration: " + (soundtrack.getDuration().toMillis() / 1000) + "s");
        Text sampleRate = new Text("Sample Rate: " + soundtrack.getSampleRate() + "Hz");

        closeSoundtrack.getStyleClass().add("close-soundtrack");
        name.getStyleClass().add("soundtrack-title");
        this.getStyleClass().add("soundtrack-context-pane");

        this.getChildren().addAll(top, duration, sampleRate);
    }
}
