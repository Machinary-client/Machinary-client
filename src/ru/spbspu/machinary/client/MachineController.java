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
        String input = "Stand by";
        while (!Thread.interrupted() && !isInterrupt) {
            // FIXME: 25.03.2018
            machineConnector.send(input);
            Action action = controller.setMessage(input);
            input = machineConnector.getReplyStr();
        }
        machineConnector.closeConnection();
    }

    public void finish() {
        isInterrupt = true;
    }
}
