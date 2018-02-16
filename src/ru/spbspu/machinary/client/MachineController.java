package ru.spbspu.machinary.client;

public class MachineController extends Thread {

    private MachineConnector machineConnector;
    private static Controller controller = new Controller();
    private boolean isGood = true;


    public MachineController(String address) {
        machineConnector = new MachineConnector(address);
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
