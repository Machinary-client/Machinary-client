package ru.spbspu.machinary.client;

import com.sun.jdi.InvalidTypeException;

import java.io.IOException;

interface Controller {
    /**
     * Send message to UI
     * @param string message that will be show on the screen
     */
    void setMessage(final String string);
}
