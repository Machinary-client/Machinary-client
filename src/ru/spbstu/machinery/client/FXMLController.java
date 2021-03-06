package ru.spbstu.machinery.client;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.zip.DataFormatException;

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
    public Action setMessage(final String str) {
        System.out.println("Log: " + str);
        Platform.runLater(() -> {
            currentCommandLabel.setText(str);
            commands.add(str);
        });
        Executable show = null;
        try {
            show = analyze.getExec(str);
        } catch (IOException | DataFormatException e) {
            System.err.println(e.getMessage());
            e.printStackTrace();
        }
        if (show == null) {
            return analyze.getActionOfLastCommand();
        }
        show.exec(pane);
        return analyze.getActionOfLastCommand();
    }


    private class Analyze {

        private long imageDelay = 0;
        private long videoDelay = 0;
        private Action action;
        private TechnicalProcess process;


        Analyze() {
            try {
                process = new TechnicalProcess();
            } catch (IOException | DataFormatException e) {
                System.err.println("Problem with Technical process file");
                e.printStackTrace();
            }
        }

        Action getActionOfLastCommand() {
            return action;
        }

        /**
         *
         * @param command that should be execute by program
         * @return executable function that display image and videos
         * @throws IOException
         * @throws DataFormatException
         */
        Executable getExec(String command) throws IOException, DataFormatException {
            imageDelay = process.getImageDelay();
            videoDelay = process.getVideoDelay();

            action = process.getAction(command);
            System.out.println(action);
            switch (action.getActionType()) {
                case EXIT:
                    return pane1 -> exitFromProgram(pane1, action);
                case FAIL:
                    return pane1 -> failToExecuteCommand(pane1, action);
                case SKIP:
                    return panel -> {
                    };
                case CRUSH:
                    return pane1 -> crushProcess(pane1, action);
                case EXECUTE:
                    System.out.println("Execute");
                    return pane1 -> execute(pane1, action);
                case SWITCH_PROCESS:
                    List<String> strings = action.getFilesPaths();
                    if (strings == null || strings.isEmpty()) {
                        throw new RuntimeException("Can't find process name");
                    }
                    String name = strings.get(0);
                    if (name == null || name.isEmpty()) {
                        throw new RuntimeException("Process must have name");
                    }
                    process = new TechnicalProcess(name);
                    return pane1 -> {
                    };

                case SWITCH_TECHNOLOGY:
                    return pane1 -> {
                    };

            }

            throw new RuntimeException("action must have some type");

        }

        private void cleanZeroCell(GridPane pane1) {
            pane1.getChildren().removeAll(pane1.getChildren());
        }

        private void showImage(GridPane panel, String imagePath) {
            CountDownLatch latch = new CountDownLatch(1);
            Platform.runLater(() -> cleanZeroCell(panel));
            File fileImage = new File(imagePath);
            Image image = new Image(fileImage.toURI().toString());
            Platform.runLater(() -> {
                ImageView imageView = new ImageView(image);
                //It have two different variants for stretching images
                /*
                double h = image.getHeight();
                double w = image.getWidth();
                double width = panel.getWidth();
                double height = panel.getHeight();

                double imageWidth = width;
                double imageHeight = h / w * width;
                if (imageHeight > height) {
                    imageHeight = height;
                    imageWidth = w / h * imageHeight;
                }

                imageView.setFitWidth(imageWidth);
                imageView.setFitHeight(imageHeight);
                */
                imageView.fitWidthProperty().bind(panel.widthProperty());
                imageView.fitHeightProperty().bind(panel.heightProperty());

                panel.add(imageView, 0, 0);
                latch.countDown();
            });
            try {
                latch.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            try {
                Thread.sleep(imageDelay);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }

        private void showVideo(GridPane panel, String videoPath) {
            CountDownLatch latch = new CountDownLatch(1);
            Platform.runLater(() -> cleanZeroCell(panel));
            File fileVideo = new File(videoPath);
            Platform.runLater(() -> {
                Media media = new Media(fileVideo.toURI().toString());
                MediaPlayer mediaPlayer = new MediaPlayer(media);
                MediaView mediaView = new MediaView(mediaPlayer);
                mediaView.fitWidthProperty().bind(panel.widthProperty());
                mediaView.fitHeightProperty().bind(panel.heightProperty());
                panel.add(mediaView, 0, 0);

                mediaPlayer.play();
                mediaPlayer.setOnEndOfMedia(latch::countDown);
            });
            try {
                latch.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            try {
                Thread.sleep(videoDelay);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        private void exitFromProgram(GridPane pane, Action action) {
            Platform.exit();
        }

        private void execute(GridPane pane, Action action) {
            List<String> paths = action.getFilesPaths();
            System.out.println(paths);

            for (String path : paths) {
                if (path.endsWith(".jpg") || path.endsWith(".png") || path.endsWith(".jpeg")) {
                    System.out.println("must show image");
                    showImage(pane, path);
                }
                if (path.endsWith(".mp4")) {
                    System.out.println("must show video");
                    showVideo(pane, path);
                }
            }
        }

        private void failToExecuteCommand(GridPane pane, Action action) {

        }

        private void crushProcess(GridPane pane, Action action) {

        }
    }


}

/**
 * Special interface for creating execution functions
 */
interface Executable {
    void exec(GridPane pane);
}
