package ru.spbspu.machinary.client;

import javafx.application.Platform;
import javafx.beans.value.ObservableStringValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;

import javafx.geometry.Rectangle2D;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;

import javafx.scene.control.MultipleSelectionModel;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;

import java.io.File;
import java.util.HashMap;
import java.util.Map;


public class FXMLController implements Controller {
    @FXML
    private Label currentCommandLabel;

    @FXML
    private ListView<String> historyOfCommands;

    @FXML
    private GridPane pane;

    private ObservableList<String> commands = FXCollections.observableArrayList();

    private Analyze analyze;

    @FXML
    public void initialize() {
        historyOfCommands.setItems(commands);
        analyze = new Analyze();
    }

    @Override
    public void setMessage(final String str) {

        Platform.runLater(() -> {
            currentCommandLabel.setText(str);
            commands.add(str);

            Executable show = analyze.getExec(str);
            show.exec(pane);
        });
    }

    interface Executable {
        void exec(GridPane pane);
    }

    static class Analyze {
        private HashMap<String, String> mapImages;
        private HashMap<String, String> mapVideos;

        Analyze() {
            //TODO: read data
        }

        Executable getExec(String string) {
            Executable for_ret = pane -> {
                Media media = new Media(new File("res/videos/1.mp4").toURI().toString());
                MediaPlayer player = new MediaPlayer(media);
                MediaView view = new MediaView(player);
                view.setViewport(new Rectangle2D(0,0,640,480));
                pane.add(view, 0, 0);
                player.play();
            };
            /*
            String imagePath = mapImages.get(string);
            String videoPath = mapImages.get(string);

            if ((imagePath != null) && (videoPath != null)) {
                return pane -> {

                };
            } else if (imagePath != null) {

            } else if (videoPath != null) {

            }
            */
            return for_ret;
        }
    }
}

