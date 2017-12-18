package eu.vmaerten.audiocity.ui;

import eu.vmaerten.audiocity.soundtrack.Channel;
import eu.vmaerten.audiocity.soundtrack.formats.WavSoundtrack;
import eu.vmaerten.audiocity.ui.Components.SoundtrackPane;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.scene.control.ScrollPane;

import java.util.ArrayList;
import java.util.List;

public class MainWindow extends Application {
    private static final long WINDOW_WIDTH = 1280;
    private static final long WINDOW_HEIGHT = 720;

    @Override
    public void start(Stage stage) throws Exception {
        WavSoundtrack soundtrack = new WavSoundtrack("/home/xayah/Music/audio.wav");
        List<Channel> channels = soundtrack.getChannels();

        BorderPane root = new BorderPane();
        root.setPadding(new Insets(0, 0, 0, 0));
        Scene scene = new Scene(root, WINDOW_WIDTH, WINDOW_HEIGHT);

        HBox menu = new HBox();
        menu.setBackground(new Background(new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY)));
        menu.setPrefSize(200, 50);
        root.setTop(menu);

        ScrollPane soundtracks_container_scrollable = new ScrollPane();
        soundtracks_container_scrollable.setFitToHeight(true);
        soundtracks_container_scrollable.setFitToWidth(true);

        VBox soundtracks_container = new VBox();
        soundtracks_container.setSpacing(10);

        List<Node> soundtracks = new ArrayList<>();
        soundtracks.add(new SoundtrackPane(soundtrack));
        soundtracks.add(new SoundtrackPane(soundtrack));

        for (Node node : soundtracks) {
            soundtracks_container.getChildren().add(node);
        }

        soundtracks_container_scrollable.setContent(soundtracks_container);
        root.setCenter(soundtracks_container_scrollable);

        stage.setMinWidth(720);
        stage.setMinHeight(360);
        stage.setTitle("Audiocity");
        stage.setScene(scene);
        stage.show();
    }
}


//Col   umnConstraints root_column = new ColumnConstraints();
//        root_column.setPercentWidth(100);
//        root_column.setFillWidth(true);
//        root.getColumnConstraints().add(root_column);
//
//        RowConstraints menu = new RowConstraints();
//        menu.setPrefHeight(100);
//        menu.setMinHeight(100);
//        menu.setMaxHeight(100);
//        menu.setFillHeight(true);
//        root.getRowConstraints().add(menu);
//
//        RowConstraints soundtracks_row = new RowConstraints();
//        soundtracks_row.setFillHeight(true);
//        root.getRowConstraints().add(soundtracks_row);