package eu.audiocity.ui.soundtrack;

import eu.audiocity.soundtrack.Soundtrack;
import javafx.scene.layout.GridPane;
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
