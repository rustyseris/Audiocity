package eu.audiocity.ui.soundtrack;

import eu.audiocity.soundtrack.Channel;
import eu.audiocity.soundtrack.Soundtrack;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.List;

class ChannelsPane extends AnchorPane {
    private VBox inner_container;
    private List<ChannelPane> canvass;
    private Soundtrack soundtrack;

    ChannelsPane(Soundtrack soundtrack) {
        super();

        List<Channel> channels = soundtrack.getChannels();
        this.soundtrack = soundtrack;
        this.inner_container = new VBox();
        this.inner_container.getStyleClass().add("channels-container");

        this.canvass = new ArrayList<>(channels.size());

        AnchorPane.setLeftAnchor(this.inner_container, 0.0);
        AnchorPane.setRightAnchor(this.inner_container, 0.0);
        AnchorPane.setTopAnchor(this.inner_container, 0.0);
        AnchorPane.setBottomAnchor(this.inner_container, 0.0);

        this.getChildren().add(this.inner_container);
        this.getStyleClass().add("channels-pane");

        for (Channel channel : channels) {
            ChannelPane canvas = new ChannelPane(channel);
            this.canvass.add(canvas);
            this.inner_container.getChildren().add(canvas);
        }
    }

    public Soundtrack getSoundtrack() {
        return this.soundtrack;
    }
}
