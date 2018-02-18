package ru.spbspu.machinary.client;

public class MachineController extends Thread {

    private MachineConnector machineConnector;
    private Controller controller;
    private boolean isGood = true;


    public MachineController(String address, Controller controller) {
        this.controller = controller;
        machineConnector = new MachineConnector(address);
    }

    public void run() {
        int count =0;
        String input = "Hello";
        while (!Thread.currentThread().isInterrupted() && isGood) {
            while (controller == null) {
                try {
                    wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            controller.setMessage("test" + count++);
            //machineConnector.sent(input);
            //controller.setMessage(input);
            //input = machineConnector.getReply();

        }
    }

}
