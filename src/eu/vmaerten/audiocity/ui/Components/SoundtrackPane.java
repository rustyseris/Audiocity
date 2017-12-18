package eu.vmaerten.audiocity.ui.Components;

import eu.vmaerten.audiocity.soundtrack.Soundtrack;
import javafx.scene.layout.AnchorPane;

public class SoundtrackPane extends AnchorPane {
    ChannelCanvas canvas;

    public SoundtrackPane(Soundtrack soundtrack) {
        this.canvas = new ChannelCanvas(soundtrack.getChannels().get(0));
        AnchorPane.setLeftAnchor(this.canvas, 30.0);
        AnchorPane.setRightAnchor(this.canvas, 30.0);
        AnchorPane.setTopAnchor(this.canvas, 15.0);
        AnchorPane.setBottomAnchor(this.canvas, 15.0);
        this.getChildren().add(this.canvas);
        this.setPrefHeight(150);
        this.setMinHeight(150);
    }
}
