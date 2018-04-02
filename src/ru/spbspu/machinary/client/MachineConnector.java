package ru.spbspu.machinary.client;

import org.zeromq.ZMQ;

public class MachineConnector implements Connector {
    private ZMQ.Context context;
    private ZMQ.Socket socket;
    private String address;
    private boolean open = false;
    private boolean finish = false;

    MachineConnector(String address, int socketType) {
        this.address = address;
        openConnection(socketType);
    }

    @Override
    public void openConnection(int socketType) {
        if (open) {
            return;
        }
        open = true;
        finish = false;
        context = ZMQ.context(1);
        socket = context.socket(socketType);
        socket.connect(address);
    }

    @Override
    public void closeConnection() {
        System.out.println("try to close connection");
        if (open && !finish) {
            socket.close();
            context.term();
            finish = true;
            open = false;
        }
    }

    @Override
    public void send(String str) {
        socket.send(str, 0);
    }

    @Override
    public void send(byte[] data) {
        socket.send(data, 0);
    }

    @Override
    public byte[] getReply() {
        return socket.recv(0);
    }

    @Override
    public String getReplyStr() {
        return socket.recvStr(0);
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
