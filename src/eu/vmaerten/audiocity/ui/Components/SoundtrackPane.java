package eu.vmaerten.audiocity.ui.Components;

import eu.vmaerten.audiocity.soundtrack.Channel;
import eu.vmaerten.audiocity.soundtrack.Soundtrack;
import javafx.geometry.Insets;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;

public class SoundtrackPane extends AnchorPane {
    private VBox inner_container;
    private List<ChannelPane> canvass;
    private Soundtrack soundtrack;

    public SoundtrackPane(Soundtrack soundtrack) {
        super();

        List<Channel> channels = soundtrack.getChannels();
        this.soundtrack = soundtrack;
        this.inner_container = new VBox();
        this.canvass = new ArrayList<>(channels.size());
        this.inner_container.setSpacing(5);
        this.inner_container.setPadding(new Insets(10.0));
        this.inner_container.setBorder(new Border(
                new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(1))
        ));
        
        AnchorPane.setLeftAnchor(this.inner_container, 30.0);
        AnchorPane.setRightAnchor(this.inner_container, 30.0);
        AnchorPane.setTopAnchor(this.inner_container, 15.0);
        AnchorPane.setBottomAnchor(this.inner_container, 15.0);
        this.getChildren().add(this.inner_container);

        for (Channel channel : channels) {
            ChannelPane canvas = new ChannelPane(channel);
            canvas.setBorder(new Border(
                    new BorderStroke(Color.GREY, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(1))
            ));
            this.canvass.add(canvas);
            this.inner_container.getChildren().add(canvas);
        }

        this.setPrefHeight(USE_COMPUTED_SIZE);
    }

    public Soundtrack getSoundtrack() {
        return this.soundtrack;
    }
}
