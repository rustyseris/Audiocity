package eu.audiocity.ui.soundtrack.channels;

import eu.audiocity.soundtrack.Channel;
import javafx.scene.canvas.Canvas;
import javafx.scene.layout.AnchorPane;

class ChannelPane extends AnchorPane {
    private ChannelCanvas canvas;

    ChannelPane(Channel channel) {
        this.canvas = new ChannelCanvas(channel);

        AnchorPane.setLeftAnchor(canvas, 0.0);
        AnchorPane.setRightAnchor(canvas, 0.0);
        AnchorPane.setTopAnchor(canvas, 0.0);
        AnchorPane.setBottomAnchor(canvas, 0.0);

        this.getChildren().add(canvas);
        this.getStyleClass().add("channel-pane");

        this.setPrefHeight(ChannelCanvas.CANVAS_HEIGHT);
        this.setPrefWidth(USE_COMPUTED_SIZE);
    }

    public void redrawChannel() {
        this.canvas.draw();
    }
}
