package eu.audiocity.ui.soundtrack;

import eu.audiocity.soundtrack.Soundtrack;
import eu.audiocity.ui.MainWindow;
import eu.audiocity.ui.soundtrack.channels.ChannelsPane;
import javafx.beans.property.SimpleObjectProperty;
import javafx.geometry.Pos;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;

public class SoundtrackPane extends HBox {
    private MainWindow mainWindow;
    private Soundtrack soundtrack;
    private SoundtrackContextPane soundtrackInfo;
    private ChannelsPane channelsPane;

    public SoundtrackPane(Soundtrack soundtrack, MainWindow window) {
        this.mainWindow = window;
        this.channelsPane = new ChannelsPane(soundtrack);
        this.soundtrackInfo = new SoundtrackContextPane(soundtrack, window);
        this.soundtrack = soundtrack;

        HBox.setHgrow(this.channelsPane, Priority.ALWAYS);
        HBox.setHgrow(this.soundtrackInfo, Priority.NEVER);

        this.setAlignment(Pos.CENTER);

        this.getChildren().addAll(this.soundtrackInfo, this.channelsPane);
        this.getStyleClass().add("soundtrack-pane");

        SimpleObjectProperty<Soundtrack> selectedSoundtrack = this.mainWindow.selectedSoundtrack();
        this.setOnMouseClicked(e -> selectedSoundtrack.setValue(this.soundtrack));
        selectedSoundtrack.addListener(e -> {
            if(this.soundtrack == selectedSoundtrack.getValue()) {
                this.getStyleClass().add("selected-soundtrack");
            } else {
                this.getStyleClass().remove("selected-soundtrack");
            }
        });
    }
}
