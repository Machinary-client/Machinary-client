package ru.spbspu.machinary.client;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;

import javafx.scene.control.Label;
import javafx.scene.control.ListView;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.ZoomEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.util.Pair;

import java.io.File;
import java.util.HashMap;
import java.util.concurrent.CountDownLatch;


public class FXMLController implements Controller {
    @FXML
    private Label currentCommandLabel;

    @FXML
    private ListView<String> historyOfCommands;

    @FXML
    private GridPane pane;

    private ObservableList<String> commands = FXCollections.observableArrayList();

    private Analyze analyze;

    private final long STANDART_DELAY = 1000;

    private final long IMAGE_DELAY = 3000;

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
        });
        Executable show = analyze.getExec(str);
        show.exec(pane);

        System.out.println("ok");

    }


    class Analyze {
        private HashMap<String, File> mapImages = new HashMap<>();
        private HashMap<String, File> mapVideos = new HashMap<>();
        private boolean isBolt, isRing, isShovel;

        private String imagePath;
        private String videoPath;

        Analyze() {
            //TODO: read data
            mapImages.put("Stand by", new File("res/images/standby.jpeg"));


            //throw new InvalidPropertiesFormatException("unsupported format: ");
        }

        Executable getExec(String string) {
            //TODO:fixme


            if (string.equals("Stand by")) {
                imagePath = "res/images/standby.jpg";
                videoPath = "res/videos/1.mp4";
                System.out.println("stand by");
            } else if (string.equals("mscdocument Bolt;")) {
                isBolt = true;
                isRing = false;
                isShovel = false;
            } else if (string.equals("mscdocument Ring;")) {
                isBolt = false;
                isRing = true;
                isShovel = false;
            } else if (string.equals("mscdocument Shovel;")) {
                isBolt = false;
                isRing = false;
                isShovel = true;
            } else if (string.equals("Stop")) {
                //SIGNALS begin
                imagePath = "res/images/stop.png";
            } else if (string.equals("Exit")) {
                imagePath = "res/images/keepcalm.png";
                //SIGNALS end
            } else {
                if (isBolt) {
                    //BOLT begin
                    if (string.equals("TAT: out intialize() to SUT; /*1*/")) {
                        imagePath = "res/images/Bolt1.jpeg";
                    } else if (string.equals("TAT: out setPosition ( 0, 0, 0 ) to SUT; /*2*/")) {
                        //VideoPlay("/home/user/TestVideoPlayer/shipping1.wmv");
                        //image = new QImage(":/images/Bolt1.jpeg");
                        //VideoPlay("installation1.wmv");
                    } else if (string.equals("TAT: out setTool(_rook) to SUT; /*3*/")) {
                        imagePath = "res/images/Bolt2.jpeg";
                    } else if (string.equals("TAT: out moveTool ( 0, 0, 100, 0, 0 ) to SUT; /*4*/")) {
                        imagePath = "res/images/Bolt3.jpeg";
                    } else if (string.equals("TAT: out setTool(_rubber) to SUT; /*5*/")) {
                        imagePath = "res/images/Bolt4.jpeg";
                    } else if (string.equals("TAT: out moveTool ( 0, 0, 0, 0, 0 ) to SUT; /*6*/")) {
                        imagePath = "res/images/Bolt5.jpeg";
                    } else if (string.equals("TAT: out setTool(_blank) to SUT; /*7*/")) {
                        imagePath = "res/images/Bolt6.jpeg";
                    } else if (string.equals("TAT: out EndWork() to SUT; /*8*/")) {
                        //image = new QImage(":/images/Bolt6.jpeg");
                        //VideoPlay("shipping1.wmv");
                    }
                    //BOLT end
                } else if (isRing) {
                    //RING begin
                    if (string.equals("TAT: out initialize() to SUT; /*1*/")) {
                        imagePath = "res/images/Ring1.jpeg";
                    } else if (string.equals("TAT: out setPosition ( 50, 50, 100 ) to SUT; /*2*/")) {
                        //VideoPlay("/home/user/TestVideoPlayer/shipping1.wmv");
                        //image = new QImage(":/images/Ring1.jpeg");
                        //VideoPlay("installation2.wmv");
                    } else if (string.equals("TAT: out setTool(_spike) to SUT; /*3*/")) {
                        imagePath = "res/images/Ring2.jpeg";
                    } else if (string.equals("TAT: out moveTool ( 45, 45, 100, 0, 0 ) to SUT; /*4*/")) {
                        imagePath = "res/images/Ring3.jpeg";
                    } else if (string.equals("TAT: out setTool(_blank) to SUT; /*5*/")) {
                        imagePath = "res/images/Ring3.jpeg";
                    } else if (string.equals("TAT: out moveTool ( 50, 50, 110, 0, 0 ) to SUT; /*6*/")) {
                        imagePath = "res/images/Ring3.jpeg";
                    } else if (string.equals("TAT: out setTool(_needle) to SUT; /*7*/")) {
                        imagePath = "res/images/Ring4.jpeg";
                    } else if (string.equals("TAT: out moveTool ( 50, 50, 112, 0, 0 ) to SUT; /*8*/")) {
                        imagePath = "res/images/Ring5.jpeg";
                    } else if (string.equals("TAT: out setTool(_blank) to SUT; /*9*/")) {
                        imagePath = "res/images/Ring5.jpeg";
                    } else if (string.equals("TAT: out moveTool ( 60, 55, 110, 0, 0 ) to SUT; /*10*/")) {
                        imagePath = "res/images/Ring5.jpeg";
                    } else if (string.equals("TAT: out setTool(_press) to SUT; /*11*/")) {
                        imagePath = "res/images/Ring6.jpeg";
                    } else if (string.equals("TAT: out moveTool ( 60, 55, 0, 0, 0 ) to SUT; /*12*/")) {
                        imagePath = "res/images/Ring7.jpeg";
                    } else if (string.equals("TAT: out moveTool ( 0, 0, 50, 0, 0 ) to SUT; /*13*/")) {
                        imagePath = "res/images/Ring8.jpeg";
                    } else if (string.equals("TAT: out setTool(_blank) to SUT; /*14*/")) {
                        imagePath = "res/images/Ring8.jpeg";
                    } else if (string.equals("TAT: out EndWork() to SUT; /*15*/")) {
                        //image = new QImage(":/images/Ring8.jpeg");
                        //VideoPlay("shipping1.wmv");
                    }
                    //RING end
                } else if (isShovel) {
                    //SHOVEL begin input.erase(input.size() - 1)
                    if (string.equals("TAT: out initialize() to SUT; /*1*/ ")) {
                        imagePath = "res/images/Shovel1.jpeg";
                    } else if (string.equals("TAT: out setPosition ( 0, 0, 100 ) to SUT; /*2*/")) {
                        //VideoPlay("/home/user/TestVideoPlayer/shipping1.wmv");
                        //image = new QImage(":/images/Shovel2.jpeg");
                        //VideoPlay("installation3.wmv");
                    } else if (string.equals("TAT: out setTool(_spike) to SUT; /*3*/")) {
                        imagePath = "res/images/Shovel2.jpeg";
                    } else if (string.equals("TAT: out moveTool ( 0, 0, 75, 0, 0 ) to SUT; /*4*/")) {
                        imagePath = "res/images/Shovel3.jpeg";
                    } else if (string.equals("TAT: out moveTool ( 25, 25, 100, 0, 0 ) to SUT; /*5*/")) {
                        imagePath = "res/images/Shovel4.jpeg";
                    } else if (string.equals("TAT: out setTool(_blank) to SUT; /*6*/")) {
                        imagePath = "res/images/Shovel4.jpeg";
                    } else if (string.equals("TAT: out moveTool ( -10, 0, 75, 0, 0 ) to SUT; /*7*/")) {
                        imagePath = "res/images/Shovel5.jpeg";
                    } else if (string.equals("TAT: out setTool(_needle) to SUT;  /*8*/")) {
                        imagePath = "res/images/Shovel5.jpeg";
                    } else if (string.equals("TAT: out moveTool ( 0, 0, 50, 0, 0 ) to SUT; /*9*/")) {
                        imagePath = "res/images/Shovel6.jpeg";
                    } else if (string.equals("TAT: out moveTool ( -15, 0, 60, 0, 0 ) to SUT; /*10*/")) {
                        imagePath = "res/images/Shovel7.jpeg";
                    } else if (string.equals("TAT: out setTool(_blank) to SUT;  /*11*/")) {
                        imagePath = "res/images/Shovel7.jpeg";
                    } else if (string.equals("TAT: out moveTool ( 0, 0, 100, 0, 0 ) to SUT; /*12*/")) {
                        imagePath = "res/images/Shovel7.jpeg";
                    } else if (string.equals("TAT: out setTool(_spike) to SUT; /*13*/")) {
                        imagePath = "res/images/Shovel8.jpeg";
                    } else if (string.equals("TAT: out shave ( 25, 0, 2, 0, 1 ) to SUT; /*14*/")) {
                        imagePath = "res/images/Shovel9.jpeg";
                    } else if (string.equals("TAT: out shave ( 25, 0, 5, 0, 0 ) to SUT; /*15*/")) {
                        imagePath = "res/images/Shovel10.jpeg";
                    } else if (string.equals("TAT: out setTool(_blank) to SUT; /*переход  16*/")) {
                        imagePath = "res/images/Shovel10.jpeg";
                    } else if (string.equals("TAT: out moveTool ( 0, 0, 50, 0, 0 ) to SUT; /*17*/")) {
                        imagePath = "res/images/Shovel11.jpeg";
                    } else if (string.equals("TAT: out setTool(_drill) to SUT; /*18*/")) {
                        imagePath = "res/images/Shovel11.jpeg";
                    } else if (string.equals("TAT: out drill () to SUT; /*19*/")) {
                        imagePath = "res/images/Shovel12.jpeg";
                    } else if (string.equals("TAT: out EndWork() to SUT;  /*20*/")) {
                        //image = new QImage(":/images/Shovel12.jpeg");
                        //VideoPlay("shipping1.wmv");
                    }
                    //SHOVEL end
                }
            }

            if (imagePath != null && videoPath != null) {
                return pane1 -> {
                    showImage(pane1);
                    showVideo(pane1);
                };
            } else if (imagePath != null) {
                return this::showImage;
            } else if (videoPath != null) {

                return this::showVideo;
            }

            return pane -> {
            };
        }

        private void cleanZeroCell(GridPane pane1) {
            if (!pane1.getChildren().isEmpty()) {
                pane1.getChildren().remove(0);
            }
        }

        private void showImage(GridPane panel) {
            CountDownLatch latch = new CountDownLatch(1);
            Platform.runLater(() -> cleanZeroCell(panel));
            File fileImage = new File(imagePath);
            Image image = new Image(fileImage.toURI().toString());
            Platform.runLater(() -> {
                ImageView imageView = new ImageView(image);
                //TODO: fixme
                imageView.setFitWidth(panel.getWidth());
                imageView.setFitHeight(panel.getHeight());
                imageView.autosize();
                panel.add(imageView, 0, 0);
                latch.countDown();
            });
            try {
                latch.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            try {
                Thread.sleep(IMAGE_DELAY);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            latch.countDown();


        }

        private void showVideo(GridPane panel) {
            CountDownLatch latch = new CountDownLatch(1);
            Platform.runLater(() -> cleanZeroCell(panel));
            File fileVideo = new File(videoPath);
            Platform.runLater(() -> {
                Media media = new Media(fileVideo.toURI().toString());
                MediaPlayer mediaPlayer = new MediaPlayer(media);
                //TODO: tune media player
                MediaView mediaView = new MediaView(mediaPlayer);
                panel.add(mediaView, 0, 0);
                mediaPlayer.play();
                mediaPlayer.setOnEndOfMedia(latch::countDown);
            });
            try {
                latch.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

interface Executable {
    void exec(GridPane pane);
}
