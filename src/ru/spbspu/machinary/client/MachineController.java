package ru.spbspu.machinary.client;

import org.zeromq.ZMQ;

public class MachineController extends Thread {

    private MachineConnector machineConnector;
    private Controller controller;
    private boolean isGood = true;
    private ZMQ.Context context;
    private ZMQ.Socket socket;

    public MachineController(String address, Controller controller) {
        this.controller = controller;
        machineConnector = new MachineConnector(address);
    }

    public void run() {
        int count = 0;
        String input = "Hello";
        while (!Thread.currentThread().isInterrupted() && isGood) {
            //controller.setMessage("test" + count++);
            machineConnector.sent(input);
            controller.setMessage(input);
            input = machineConnector.getReply();

        }
    }

}
