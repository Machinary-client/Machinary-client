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
    private static MachineController machineController;


    public static void main(String[] args) {
        launch(args);
    }

    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Client");
        primaryStage.setScene(new Scene(root, 720, 640));
        machineController = new MachineController("tcp://localhost:5555");
        machineController.start();
        primaryStage.show();
        System.out.println();
    }


}
