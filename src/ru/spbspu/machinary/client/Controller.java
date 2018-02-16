package ru.spbspu.machinary.client;

import javafx.beans.Observable;
import javafx.beans.value.ObservableStringValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
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
    @FXML
    private ObservableStringValue command;

    public Controller() {
    }

    @FXML
    public void initialize() {

    }

    public synchronized void setMessage(String str) {

    }

}
