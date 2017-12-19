package eu.vmaerten.audiocity.ui.Components;

import eu.vmaerten.audiocity.soundtrack.Soundtrack;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;

public class SoundtrackPane extends HBox {
    private ChannelsPane channelsPane;
    private Soundtrack soundtrack;
    private GridPane info = new GridPane();

    public SoundtrackPane(Soundtrack soundtrack) {
        this.channelsPane = new ChannelsPane(soundtrack);
        this.soundtrack = soundtrack;

        HBox.setHgrow(this.channelsPane, Priority.ALWAYS);
        HBox.setHgrow(this.info, Priority.NEVER);

        this.getChildren().addAll(this.info, this.channelsPane);
    }
}
