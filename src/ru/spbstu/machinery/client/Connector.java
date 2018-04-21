package ru.spbstu.machinery.client;

interface Connector {
    /**
     * Open (initialize) connection between client and server, must start in constructor
     */
    void openConnection(int socketType);

    /**
     * close connection between server and client, free all resources
     */
    void closeConnection();

    /**
     * Send string
     * @param string message for send
     */
    void send(String string);

    /**
     * Send any object by array of bytes
     * @param data bytes that will be sent
     */
    void send(byte[] data);

    /**
     * Return message
     * @return message like a string
     */
    String getReplyStr();

    /**
     * Return message
     * @return array of bytes that was sented from host
     */
    byte[] getReply();
}
