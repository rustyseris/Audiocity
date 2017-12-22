package eu.audiocity.ui.soundtrack.channels;

import eu.audiocity.soundtrack.Channel;
import eu.audiocity.soundtrack.Soundtrack;
import javafx.scene.chart.NumberAxis;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;

public class ChannelsPane extends AnchorPane {
    private VBox channelsContainer = new VBox();
    private VBox innerContainer = new VBox();
    private List<ChannelPane> canvass;
    private Soundtrack soundtrack;
    private NumberAxis timeAxis;

    public ChannelsPane(Soundtrack soundtrack) {
        super();

        List<Channel> channels = soundtrack.getChannels();
        this.soundtrack = soundtrack;
        this.channelsContainer.getStyleClass().add("channels-container");
        this.innerContainer.setFillWidth(true);

        this.canvass = new ArrayList<>(channels.size());

        AnchorPane.setLeftAnchor(this.innerContainer, 0.0);
        AnchorPane.setRightAnchor(this.innerContainer, 0.0);
        AnchorPane.setTopAnchor(this.innerContainer, 0.0);
        AnchorPane.setBottomAnchor(this.innerContainer, 0.0);

        for (Channel channel : channels) {
            ChannelPane canvas = new ChannelPane(channel);
            this.canvass.add(canvas);
            this.channelsContainer.getChildren().add(canvas);
        }

        this.timeAxis = new NumberAxis("time (s)", 0, this.soundtrack.getDuration().toMillis() / 1000, 2);
        this.timeAxis.setMinHeight(42);

        this.widthProperty().addListener(e -> {
            this.timeAxis.setTickUnit(Math.ceil(this.soundtrack.getDuration().toMillis() / 1000 / this.getWidth() * 40));
        });

        this.innerContainer.getChildren().addAll(this.channelsContainer, this.timeAxis);
        this.getChildren().add(this.innerContainer);
        this.timeAxis.setTickLabelFill(Color.WHITE);
        this.getStyleClass().add("channels-pane");
    }


    public Soundtrack getSoundtrack() {
        return this.soundtrack;
    }

    public void redrawChannels() {
        for (ChannelPane channelPane : this.canvass) {
            channelPane.redrawChannel();
        }
        this.timeAxis.setUpperBound(this.soundtrack.getDuration().toMillis() / 1000);
    }
}
