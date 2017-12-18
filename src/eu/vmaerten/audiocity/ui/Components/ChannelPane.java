package eu.vmaerten.audiocity.ui.Components;

import eu.vmaerten.audiocity.soundtrack.Channel;
import javafx.scene.layout.AnchorPane;

public class ChannelPane extends AnchorPane {
    private ChannelCanvas canvas;

    public ChannelPane(Channel channel) {
        this.canvas = new ChannelCanvas(channel);

        AnchorPane.setLeftAnchor(this.canvas, 0.0);
        AnchorPane.setRightAnchor(this.canvas, 0.0);
        AnchorPane.setTopAnchor(this.canvas, 0.0);
        AnchorPane.setBottomAnchor(this.canvas, 0.0);

        this.getChildren().add(this.canvas);

        this.setPrefHeight(100);
        this.setPrefWidth(USE_COMPUTED_SIZE);
    }
}
