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

        while (!Thread.interrupted() && !isInterrupt) {
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
        }
        machineConnector.closeConnection();
        System.exit(0);
    }

    public void finish() {
        isInterrupt = true;
    }
}
