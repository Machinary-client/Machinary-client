package ru.spbspu.machinary.client;

import org.zeromq.ZMQ;

public class MachineConnector extends Thread {
    private ZMQ.Context context;
    private ZMQ.Socket socket;
    boolean isGood = true;

    public MachineConnector() {
        context = ZMQ.context(1);
        socket = context.socket(ZMQ.REQ);
        socket.connect("tcp://localhost:5555");//TODO: make like parametr
    }

    @Override
    public void run() {

        int i = 0; //FIXME later
        while (isGood) {
            //TODO: <REMOVE IT>
            i++;
            if (i > 100) {
                isGood = false;
            }
            work(i);
        }

        //TODO: <REMOVE IT>
        socket.close();
        context.term();
    }


    private void work(int n) {
        String request = "Hello";
        System.out.println("Sending Hello " + n);
        socket.send(request.getBytes(), 0);
        byte[] reply = socket.recv(0);
        System.out.println("Received " + new String(reply));
    }


}
