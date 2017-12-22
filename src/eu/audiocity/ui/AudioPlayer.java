package eu.audiocity.ui;

import eu.audiocity.audio.AudioManager;
import eu.audiocity.soundtrack.Soundtrack;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.text.Text;
import javafx.util.Duration;

class AudioPlayer extends HBox {
    private MainWindow mainWindow;
    private Slider slider;
    private Text time;

    private static final int REFRESH_TIME = 100;

    public AudioPlayer(MainWindow mainWindow) {
        this.mainWindow = mainWindow;
        this.slider = new Slider(0, 0, 0);
        this.time = new Text("00:00s");

        Timeline followManager = new Timeline(new KeyFrame(Duration.millis(REFRESH_TIME), e -> {
            AudioManager manager = this.mainWindow.getAudioManager();
            this.slider.setMax(manager.getDuration().toMillis() / 1000);
            this.slider.setValueChanging(true);
            this.slider.setValue(manager.getCurrentTime().toMillis() / 1000);
            this.time.setText((long) this.slider.getValue() + ":" + (long) this.slider.getMax() + "s");
        }));
        followManager.setCycleCount(Timeline.INDEFINITE);
        followManager.play();

        this.slider.setOnMousePressed(e -> {
            this.mainWindow.getAudioManager().setCurrentTime(java.time.Duration.ofSeconds((long) this.slider.getValue()));
        });

        this.setupControlButtons();
        HBox.setHgrow(this.slider, Priority.ALWAYS);
        this.getChildren().addAll(this.slider, this.time);
    }

    private void setupControlButtons() {
        Button play = new Button("▶");
        Button pause = new Button("\u23F8");
        Button stop = new Button("\u23F9");
        this.getChildren().addAll(play, pause, stop);

        SimpleObjectProperty<Soundtrack> selectedSoundtrack = this.mainWindow.selectedSoundtrack();
        selectedSoundtrack.addListener(e -> {
            this.mainWindow.getAudioManager().stop();
        });

        play.setOnMouseClicked(e -> {
            this.mainWindow.getAudioManager().stop();
            try {
                this.mainWindow.getAudioManager().play(selectedSoundtrack.getValue());
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        });

        pause.setOnMouseClicked(e -> this.mainWindow.getAudioManager().pause());
        stop.setOnMouseClicked(e -> this.mainWindow.getAudioManager().stop());
    }
}
