package ru.spbspu.machinary.client;

import org.zeromq.ZMQ;

public class MachineConnector {
    private ZMQ.Context context;
    private ZMQ.Socket socket;

    public MachineConnector(String address) {
        context = ZMQ.context(1);
        socket = context.socket(ZMQ.REQ);
        socket.connect(address);//TODO: make like parametr//
    }

    public void sent(String str) {
        socket.send(str, 0);
    }

    public String getReply() {
        return socket.recvStr();
    }

    protected void finalize() {
        socket.close();
        context.term();
    }


}
