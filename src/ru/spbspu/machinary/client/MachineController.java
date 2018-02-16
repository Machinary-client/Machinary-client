package ru.spbspu.machinary.client;

public class MachineController extends Thread {

    private MachineConnector machineConnector;
    private Controller controller;
    private boolean isGood = true;


    public MachineController(String address, Controller controller) {
        machineConnector = new MachineConnector(address);
        this.controller = controller;
    }

    public void run() {
        String input = "Hello";
        while (!Thread.currentThread().isInterrupted()&& isGood) {
            machineConnector.sent(input);
            //controller.setMessage(input);
            input = machineConnector.getReply();
        }
    }

}
