package ru.spbspu.machinary.client;

import javafx.application.Platform;
import javafx.beans.value.ObservableStringValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;

import javafx.scene.control.Label;
import javafx.scene.control.ListView;

import javafx.scene.control.MultipleSelectionModel;
import javafx.scene.layout.VBox;
import javafx.scene.media.MediaView;

import java.util.HashMap;


public class FXMLController implements Controller {
    @FXML
    private Label currentCommandLabel;
    @FXML
    private MediaView mediaView;
    @FXML
    private ListView<String> historyOfCommands;

    private ObservableList<String> commands = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        historyOfCommands.setItems(commands);
    }

    @Override
    public void setMessage(final String str) {
        Platform.runLater(() -> {
            currentCommandLabel.setText(str);
            commands.add(str);
        });
        // TODO: 23.02.2018 Make Analizator and return Media to show
    }

}
