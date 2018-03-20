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
        tokenizer.setSpecialCommands(new ArrayList<>(Arrays.asList("process_switcher", "technology_switcher",
                "image_delay", "video_delay")));

        tokenizer.setFileExtensions(new ArrayList<>(Arrays.asList(".tech", ".cfg", ".txt",
                ".png", "jpeg", "jpg", ".mp4")));

        Token token = null;
        do {
            try {
                token = tokenizer.next();
            } catch (InvalidTypeException invalidTypeForEncoding) {
                invalidTypeForEncoding.printStackTrace();
            }
            if (token == null) {
                break;
            }
            if (token.type == TokenType.INVALID) {
                throw new InvalidTypeException("Invalid token: " + token);
            }
            if (token.type == TokenType.UNKNOWN) {
                throw new InvalidTypeException("Unknown token: " + token);
            }
            System.out.println(token);
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
