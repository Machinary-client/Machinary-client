package ru.spbspu.machinary.client;

import javafx.beans.value.ObservableStringValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;

import javafx.scene.control.Label;
import javafx.scene.control.ListView;

import javafx.scene.control.MultipleSelectionModel;
import javafx.scene.layout.VBox;
import javafx.scene.media.MediaView;
import javafx.util.FXPermission;


public class Controller {
    @FXML
    private Label currentCommandLabel;
    @FXML
    private MediaView mediaView;
    @FXML
    private ListView<String> historyOfCommands;

    private boolean init = false;
    private ObservableList<String> commands = FXCollections.observableArrayList();

    public Controller() {
    }

    @FXML
    public void initialize() {
        historyOfCommands.setItems(commands);
    }

    @FXML
    public void setMessage(String str) {
        commands.add(str);
    }

}
