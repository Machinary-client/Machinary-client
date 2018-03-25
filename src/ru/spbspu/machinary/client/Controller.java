package ru.spbspu.machinary.client;

interface Controller {
    /**
     * Send message to UI
     * @param string message that will be show on the screen
     */
    Action setMessage(final String string);
}
