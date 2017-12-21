package eu.audiocity.ui.soundtrack;

import eu.audiocity.soundtrack.Soundtrack;
import javafx.geometry.Pos;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;

public class SoundtrackPane extends HBox {
    private ChannelsPane channelsPane;
    private Soundtrack soundtrack;
    private GridPane soundtrackInfo;

    public SoundtrackPane(Soundtrack soundtrack) {
        this.channelsPane = new ChannelsPane(soundtrack);
        this.soundtrackInfo = new SoundtrackInfoPane(soundtrack);
        this.soundtrack = soundtrack;

        HBox.setHgrow(this.channelsPane, Priority.ALWAYS);
        HBox.setHgrow(this.soundtrackInfo, Priority.NEVER);

        this.setAlignment(Pos.CENTER);

        this.getChildren().addAll(this.soundtrackInfo, this.channelsPane);
        this.getStyleClass().add("soundtrack-pane");
    }
}
