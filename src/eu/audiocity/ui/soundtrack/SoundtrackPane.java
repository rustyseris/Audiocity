package eu.audiocity.ui.soundtrack;

import eu.audiocity.Utils;
import eu.audiocity.soundtrack.Soundtrack;
import eu.audiocity.soundtrack.transform.*;
import eu.audiocity.ui.MainWindow;
import eu.audiocity.ui.soundtrack.channels.ChannelsPane;
import javafx.beans.property.SimpleObjectProperty;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;

public class SoundtrackPane extends HBox {
    private MainWindow mainWindow;
    private Soundtrack soundtrack;
    private SoundtrackContextPane soundtrackInfo;
    private ChannelsPane channelsPane;
    private ContextMenu contextMenu = new ContextMenu();

    private MenuItem duplicate = new MenuItem("Duplicate");
    private MenuItem scale = new MenuItem("Scale");
    private MenuItem shift = new MenuItem("Shift");
    private MenuItem smooth = new MenuItem("Smooth");
    private MenuItem merge = new MenuItem("Merge with selected soundtrack");
    private MenuItem slice = new MenuItem("Slice");

    public SoundtrackPane(Soundtrack soundtrack, MainWindow window) {
        this.mainWindow = window;
        this.channelsPane = new ChannelsPane(soundtrack);
        this.soundtrackInfo = new SoundtrackContextPane(soundtrack, window);
        this.soundtrack = soundtrack;

        HBox.setHgrow(this.channelsPane, Priority.ALWAYS);
        HBox.setHgrow(this.soundtrackInfo, Priority.NEVER);

        this.setAlignment(Pos.CENTER);

        this.getChildren().addAll(this.soundtrackInfo, this.channelsPane);
        this.getStyleClass().add("soundtrack-pane");

        SimpleObjectProperty<Soundtrack> selectedSoundtrack = this.mainWindow.selectedSoundtrack();
        this.setOnMouseClicked(e -> {
            if(e.getButton() == MouseButton.PRIMARY) {
                this.contextMenu.hide();
                selectedSoundtrack.setValue(this.soundtrack);
            }
        });

        selectedSoundtrack.addListener(e -> {
            if(this.soundtrack == selectedSoundtrack.getValue()) {
                this.getStyleClass().add("selected-soundtrack");
            } else {
                this.getStyleClass().remove("selected-soundtrack");
            }
        });

        this.setupContextMenu();
    }

    private void setupContextMenu() {
        this.contextMenu.getItems().addAll(this.duplicate, this.slice, this.scale, this.shift, this.smooth, this.merge);
        this.setOnContextMenuRequested(e -> {
            this.contextMenu.show(this, e.getScreenX(), e.getScreenY());
        });

        this.duplicate.setOnAction(e -> {
            this.mainWindow.getSoundtracksPane().addSountrack(this.soundtrack.copy());
        });

        this.scale.setOnAction(e -> {
            Utils.askForDouble("Scaling factor").ifPresent(scalingFactor -> {
                this.soundtrack.applyTransform(new Scale(scalingFactor));
                this.redrawChannels();
            });
        });

        this.shift.setOnAction(e -> {
            Utils.askForDouble("Time shift (in seconds)").ifPresent(timeShift -> {
                this.soundtrack.applyTransform(new Shift(timeShift));
                this.redrawChannels();
            });
        });

        this.smooth.setOnAction(e -> {
            Utils.askForDouble("Averaging window size (in number of samples)").ifPresent(windowSize -> {
                if(windowSize > 0) {
                    this.soundtrack.applyTransform(new Smooth((int) Math.floor(windowSize)));
                    this.redrawChannels();
                } else {
                    Utils.alert(Alert.AlertType.ERROR, "The averaging window size must be superior to 0");
                }
            });
        });

        this.merge.setOnAction(e -> {
            Soundtrack selectedSoundtrack = this.mainWindow.selectedSoundtrack().getValue();
            if(selectedSoundtrack != null) {
                this.soundtrack.applyTransform(new Merge(selectedSoundtrack));
                this.redrawChannels();
            }
        });

        this.slice.setOnAction(e -> {
            Utils.askForDouble("From time (in seconds)").ifPresent(fromTime -> {
                Utils.askForDouble("to time (in seconds)").ifPresent(toTime -> {
                    this.soundtrack.applyTransform(new Slice(fromTime, toTime));
                    this.redrawChannels();
                });
            });
        });
    }

    public void redrawChannels() {
        this.channelsPane.redrawChannels();
    }

    public Soundtrack getSoundtrack() {
        return this.soundtrack;
    }
}
