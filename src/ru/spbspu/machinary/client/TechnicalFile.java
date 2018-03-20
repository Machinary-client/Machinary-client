package ru.spbspu.machinary.client;

import com.sun.jdi.InvalidTypeException;

import java.beans.XMLDecoder;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Array;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class TechnicalFile {

    private HashMap<String, Action> customerCommands;
    private String processSwitcher;
    private String technologySwitcher;
    private long imageDelay;
    private long videoDelay;
    private InputAction unknownCommand;


    public TechnicalFile(String path) throws IOException, InvalidTypeException {
        Tokenizer tokenizer = new Tokenizer(new FileInputStream(new File(path)));
        tokenizer.setInputActions(new ArrayList<>(Arrays.asList("exit", "fail", "skip", "crush")));
        tokenizer.setSpecialCommands(new ArrayList<>(Arrays.asList("process_switcher", "technology_switcher", "unknown_command",
                "image_delay", "video_delay")));

        tokenizer.setFileExtensions(new ArrayList<>(Arrays.asList(".tech", ".cfg", ".txt",
                ".png", "jpeg", "jpg", ".mp4")));

        Token currentToken = null;
        Token prevToken = null;
        Token important = null;

        do {
            try {
                currentToken = tokenizer.next();
            } catch (InvalidTypeException invalidTypeForEncoding) {
                invalidTypeForEncoding.printStackTrace();
            }
            if (currentToken == null) {
                break;
            }

            switch (currentToken.type) {
                case SPECIAL_COMMAND:

                    break;

                case DELIMITER: {
                    important = prevToken;
                    prevToken = currentToken;

                    break;
                }

                case INPUT_ACTION:

                    break;
                case CUSTOMER_COMMAND:

                    break;

                case PATH:

                    break;

                case NUMBER:

                    break;

                case UNKNOWN:
                    throw new InvalidTypeException("Unknown token: " + currentToken);
                case INVALID:
                    throw new InvalidTypeException("Invalid token: " + currentToken);
            }
            System.out.println(currentToken);
        } while (tokenizer.hasNext());
    }

    public long getImageDelay() {
        return imageDelay;
    }

    public long getVideoDelay() {
        return videoDelay;
    }

    public Action getAction(String command) {
        return null; // TODO: 16.03.2018 FIXME

    }

    enum InputAction {
        EXIT, SKIP, CRASH, FAIL
    }
}
