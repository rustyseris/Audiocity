package eu.vmaerten.audiocity.ui.components;

import eu.vmaerten.audiocity.soundtrack.Channel;
import javafx.scene.layout.AnchorPane;

class ChannelPane extends AnchorPane {
    ChannelPane(Channel channel) {
        ChannelCanvas canvas = new ChannelCanvas(channel);

        AnchorPane.setLeftAnchor(canvas, 0.0);
        AnchorPane.setRightAnchor(canvas, 0.0);
        AnchorPane.setTopAnchor(canvas, 0.0);
        AnchorPane.setBottomAnchor(canvas, 0.0);

        this.getChildren().add(canvas);
        this.getStyleClass().add("channel-pane");

        this.setPrefHeight(ChannelCanvas.CANVAS_HEIGHT);
        this.setPrefWidth(USE_COMPUTED_SIZE);
    }
}
