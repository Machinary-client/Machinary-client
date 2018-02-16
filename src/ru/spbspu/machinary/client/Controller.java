package ru.spbspu.machinary.client;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.media.MediaView;
import javafx.stage.Stage;

public class Controller {
    @FXML
    private Label currentCommandLabel;
    @FXML
    private MediaView mediaView;
    @FXML
    private VBox historyOfCommands;

    public Controller(Parent root) {
        currentCommandLabel = (Label) root.lookup("#currentCommandLabel");
        currentCommandLabel.setText("Inizialization client");
        historyOfCommands = (VBox) root.lookup("#historyOfCommands");
    }

    @FXML
    public void inizialize() {

    }

    public synchronized void setMessage(String str) {
        currentCommandLabel.setText(str);
        Label label = new Label(str);
//        historyOfCommands.getChildren().add(label);
    }

}
