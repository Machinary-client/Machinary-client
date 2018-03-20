package ru.spbspu.machinary.client;

import com.sun.jdi.InvalidTypeException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {
    private static MachineController machineController;

    public static void main(String[] args) {
        try {
            TechnicalProcess technicalProcess = new TechnicalProcess("firstProc");
        } catch (IOException | InvalidTypeException e) {
            System.err.println(e.getMessage());
            e.printStackTrace();

        }
        //launch(args);
        if (machineController!=null){
            machineController.finish();
        }
    }

    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("sample.fxml"));
        Parent root = loader.load();
        primaryStage.setTitle("Client");
        FXMLController controller = loader.getController();
        primaryStage.setScene(new Scene(root, 720, 640));
        machineController = new MachineController("tcp://localhost:5555", controller);
        Thread thread = new Thread(machineController);
        thread.start();
        primaryStage.show();
    }

}
