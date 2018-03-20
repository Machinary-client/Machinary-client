package ru.spbspu.machinary.client;

import com.sun.jdi.InvalidTypeException;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;

public class TechnicalFile {

    private static final String UNKNOWN_COMMAND = "unknown_command";
    private static final String IMAGE_DELAY = "image_delay";
    private static final String VIDEO_DELAY = "video_delay";
    private static final String PROCESS_SWITCHER = "process_switcher";
    private static final String TECHNOLOGY_SWITCHER = "technology_switcher";

    private HashMap<String, Action> customerCommands;
    private long imageDelay;
    private long videoDelay;
    private Action unknownCommand;


    public TechnicalFile(String path) throws IOException, InvalidTypeException {
        Tokenizer tokenizer = new Tokenizer(new FileInputStream(new File(path)));

        tokenizer.setInputActions(new ArrayList<>(Arrays.asList("exit", "fail", "skip", "crush")));
        tokenizer.setSpecialCommands(new ArrayList<>(Arrays.asList(PROCESS_SWITCHER, TECHNOLOGY_SWITCHER,
                UNKNOWN_COMMAND, IMAGE_DELAY, VIDEO_DELAY)));

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
                case SPECIAL_COMMAND: {

                    if (prevToken != null && ((prevToken.type == TokenType.SPECIAL_COMMAND) ||
                            (prevToken.type == TokenType.ASSIGNER) || (prevToken.type == TokenType.CUSTOMER_COMMAND))) {
                        throw new InvalidPropertiesFormatException(String.format("Invalid sequence of tokens: %s; %s",
                                prevToken, currentToken));

                    }
                    prevToken = currentToken;
                    important = currentToken;

                    break;
                }
                case ASSIGNER: {
                    if (prevToken == null || ((prevToken.type != TokenType.CUSTOMER_COMMAND) &&
                            (prevToken.type != TokenType.SPECIAL_COMMAND))) {
                        throw new InvalidPropertiesFormatException(String.format("Invalid sequence of tokens: %s; %s",
                                prevToken, currentToken));
                    }

                    important = prevToken;
                    prevToken = currentToken;

                    break;
                }

                case INPUT_ACTION: {

                    if (important == null || prevToken.type == null || prevToken.type != TokenType.ASSIGNER ||
                            ((important.type != TokenType.CUSTOMER_COMMAND) && (important.type != TokenType.SPECIAL_COMMAND))) {
                        throw new InvalidPropertiesFormatException(String.format("Invalid sequence of tokens: %s; %s; %s",
                                important, prevToken, currentToken));
                    }


                    if (important.type == TokenType.SPECIAL_COMMAND) {

                        if (important.value.equals(UNKNOWN_COMMAND)) {
                            unknownCommand = new Action(Actions.valueOf(currentToken.value.toUpperCase()), new ArrayList<>());
                        }

                    } else if (important.type == TokenType.CUSTOMER_COMMAND) {
                        customerCommands.put(important.value,
                                new Action(Actions.valueOf(currentToken.value.toUpperCase()), new ArrayList<>()));
                    }
                    prevToken = currentToken;
                    important = currentToken; // TODO: 20.03.2018 maybe change in latest versions


                    break;
                }
                case CUSTOMER_COMMAND:
                    if ((important != null && important.type == TokenType.CUSTOMER_COMMAND) || prevToken != null &&
                            ((prevToken.type == TokenType.CUSTOMER_COMMAND))) {
                        throw new InvalidPropertiesFormatException(String.format("Invalid sequence of tokens: %s; %s",
                                prevToken, currentToken));

                    }
                    if (prevToken != null && prevToken.type == TokenType.ASSIGNER) {

                        if (important != null && important.type == TokenType.SPECIAL_COMMAND) {
                            // TODO: 20.03.2018 process_switcher and technology_switcher
                            if (important.value.equals(PROCESS_SWITCHER)) {
                                customerCommands.put(currentToken.value, new Action(Actions.SWITCH_PROCESS,new ArrayList<>()));
                            } else if (important.value.equals(TECHNOLOGY_SWITCHER)) {
                                customerCommands.put(currentToken.value, new Action(Actions.SWITCH_TECHNOLOGY,new ArrayList<>()));
                            }
                        }

                    }
                    prevToken = currentToken;
                    important = currentToken;
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

}
