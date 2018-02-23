package ru.spbspu.machinary.client;

public class MachineController implements Runnable {

    private Connector machineConnector;
    private Controller controller;
    private boolean isInterrupt = false;

    MachineController(String address, FXMLController controller) {
        this.controller = controller;
        machineConnector = new MachineConnector(address);
    }

    public void run() {
        String input = "Hello";
        while (!Thread.interrupted() && !isInterrupt) {
            machineConnector.send(input);
            controller.setMessage(input);
            input = machineConnector.getReplyStr();
        }
        machineConnector.closeConnection();
    }

    public void finish() { // FIXME: 23.02.2018  
        isInterrupt = true;
    }
}
