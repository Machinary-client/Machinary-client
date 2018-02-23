package ru.spbspu.machinary.client;

import javafx.application.Platform;
import org.zeromq.ZMQ;

public class MachineController extends Thread {

    private MachineConnector machineConnector;
    private Controller controller;
    private boolean isInterrupt = false;
    private ZMQ.Context context;
    private ZMQ.Socket socket;

    MachineController(String address, Controller controller) {
        this.controller = controller;
        machineConnector = new MachineConnector(address);
    }

    public void run() {
        String input = "Hello";
        while (!Thread.interrupted() && !isInterrupt) {

            machineConnector.sent(input);
            final String showStr = input;
            Platform.runLater(() -> controller.setMessage(showStr));
            input = machineConnector.getReply();
            System.out.println("work");
        }
        machineConnector.closeConnect();
    }

}
