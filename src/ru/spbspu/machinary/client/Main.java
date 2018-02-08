package ru.spbspu.machinary.client;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.media.MediaView;
import javafx.stage.Stage;

public class Main extends Application {
    private Label currentCommandLabel;
    private VBox historyOfCommand;
    private MediaView mediaView;
    public static MachineConnector machineConnector;


    public static void main(String[] args) {
        machineConnector = new MachineConnector();
        machineConnector.start();

       // machineConnector.interrupt();

        launch(args);
        System.out.println("Test");
    }

    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Client");
        primaryStage.setScene(new Scene(root, 720, 640));
        primaryStage.show();
        inizializeElements(root);

    }

    private void inizializeElements(Parent root) {
        currentCommandLabel = (Label) root.lookup("#currentCommand");
        currentCommandLabel.setText("Inizialization client");
        historyOfCommand = (VBox) root.lookup("#historyOfCommand");
        mediaView = (MediaView) root.lookup("#mediaView");
    }

}
