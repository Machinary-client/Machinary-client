package ru.spbspu.machinary.client;

import org.zeromq.ZMQ;

public class MachineConnector {
    private ZMQ.Context context;
    private ZMQ.Socket socket;

    private boolean finish = false;

    MachineConnector(String address) {
        context = ZMQ.context(1);
        socket = context.socket(ZMQ.REQ);
        socket.connect(address);
    }

    public void sent(String str) {
        socket.send(str, 0);
    }

    public String getReply() {
        return socket.recvStr(0);
    }

    public void closeConnect() {
        socket.close();
        context.term();
        finish = true;
    }

    @Override
    @Deprecated
    protected void finalize() {
        if (!finish) {
            socket.close();
            context.term();
        }
    }


}
