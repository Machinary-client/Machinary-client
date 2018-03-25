package ru.spbspu.machinary.client;

import com.sun.jdi.InvalidTypeException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.InvalidPropertiesFormatException;

public class Main extends Application {
    private static MachineController machineController;
    private static ConfigParser parser;

    public static void main(String[] args) {
        try {
            parser = new ConfigParser("config.cfg");
        } catch (FileNotFoundException | InvalidPropertiesFormatException e) {
            e.printStackTrace();
        }
        try {
            new TechnicalFile("processes/firstProc/default.tech");
        } catch (IOException | InvalidTypeException e) {
            System.err.println("WTF: "+ e.getMessage());
            e.printStackTrace();
        }
        launch(args);
        if (machineController != null) {
            machineController.finish();
        }
    }

    public void start(Stage primaryStage) throws Exception {
        String ip = parser.getIp();
        String port = parser.getPort();
        if (ip == null) {
            ip = "localhost";
        }
        if (port == null) {
            port = "5555";
        }
        System.out.printf("IP: %s:%s\n", ip, port);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("sample.fxml"));
        Parent root = loader.load();
        primaryStage.setTitle("Client");
        FXMLController controller = loader.getController();
        primaryStage.setScene(new Scene(root, 1080, 720));
        machineController = new MachineController("tcp://" + ip + ":" + port, controller);
        Thread thread = new Thread(machineController);
        thread.start();
        primaryStage.show();
    }

}
