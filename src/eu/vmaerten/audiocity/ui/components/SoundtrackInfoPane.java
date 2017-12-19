package eu.vmaerten.audiocity.ui.components;

import eu.vmaerten.audiocity.soundtrack.Soundtrack;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

class SoundtrackInfoPane extends GridPane {
    private Soundtrack soundtrack;

    SoundtrackInfoPane(Soundtrack soundtrack) {
        this.soundtrack = soundtrack;
        this.setPrefWidth(200);
        this.setMinHeight(200);

        Text text = new Text(this.soundtrack.getName());
        this.add(text, 0, 1);

        this.getStyleClass().add("soundtrack-info-pane");
    }
}
