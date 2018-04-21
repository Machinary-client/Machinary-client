package ru.spbstu.machinery.client;

import org.zeromq.ZMQ;

public class MachineController implements Runnable {

    private Connector machineConnector;
    private Controller controller;
    private boolean isInterrupt = false;

    MachineController(String address, FXMLController controller) {
        this.controller = controller;
        machineConnector = new MachineConnector(address, ZMQ.REQ);
    }

    public void run() {
        String string = "NULL";
        while (!Thread.interrupted() && !isInterrupt) {
            machineConnector.send(string);
            string = machineConnector.getReplyStr();
            System.out.println("Get string: " + string);
            Action action = controller.setMessage(string);
            System.out.println("Action in run: " + action);
            if (action.getActionType() == ActionType.FAIL) {
                string = "FAIL";
            }
            if (action.getActionType() == ActionType.CRUSH) {
                string = "CRUSH";
            }
            if (action.getActionType() == ActionType.EXIT) {
                isInterrupt = true;
                string = "FINISH";
                machineConnector.send(string);
                machineConnector.getReplyStr(); // FIXME: 02.04.2018 
            }
        }

        machineConnector.closeConnection();
        System.out.println("Log: connection was closed");
        System.exit(0);
    }

    /**
     * Special function for close Connection
     */
    public void finish() {
        isInterrupt = true;
    }
}
