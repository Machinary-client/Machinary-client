package ru.spbspu.machinary.client;

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
            /*
            String input = machineConnector.getReplyStr();
            if (input == null) {
                System.out.println("input is null");
            }
            Action action = controller.setMessage(input);

            if (action.getActionType() == ActionType.FAIL) {
                input = "FAIL";
            }
            if (action.getActionType() == ActionType.CRUSH) {
                input = "CRUSH";
            }
            if (action.getActionType() == ActionType.EXIT) {
                isInterrupt = true;
                input = "FINISH";
            }
            machineConnector.send(input);
            */

            machineConnector.send(string);
            string = machineConnector.getReplyStr();
            Action action = controller.setMessage(string);

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
            }
        }
        machineConnector.closeConnection();
        System.exit(0);
    }

    public void finish() {
        isInterrupt = true;
    }
}
