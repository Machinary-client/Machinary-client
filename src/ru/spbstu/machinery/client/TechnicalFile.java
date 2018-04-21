package ru.spbstu.machinery.client;

import javafx.util.Pair;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;
import java.util.zip.DataFormatException;

public class TechnicalFile {

    private static final String UNKNOWN_COMMAND = "unknown_command";
    private static final String IMAGE_DELAY = "image_delay";
    private static final String VIDEO_DELAY = "video_delay";
    private static final String PROCESS_SWITCHER = "process_switcher";
    private static final String TECHNOLOGY_SWITCHER = "technology_switcher";

    private Pair<Boolean, Long> imageDelay = new Pair<>(false, 0L);
    private Pair<Boolean, Long> videoDelay = new Pair<>(false, 0L);

    private HashMap<String, Action> customerCommands = new HashMap<>();
    private Action unknownCommand;
    private Action technologySwitcher;
    private Action processSwitcher;

    /**
     *
     * @param path - path to technical file *.tech
     * @throws IOException
     * @throws DataFormatException throws if data has incorrect format
     */
    public TechnicalFile(String path) throws IOException, DataFormatException {
        Tokenizer tokenizer = new Tokenizer(new FileInputStream(new File(path)));

        tokenizer.setInputActions(new ArrayList<>(Arrays.asList("exit", "fail", "skip", "crush")));
        tokenizer.setSpecialCommands(new ArrayList<>(Arrays.asList(PROCESS_SWITCHER, TECHNOLOGY_SWITCHER,
                UNKNOWN_COMMAND, IMAGE_DELAY, VIDEO_DELAY)));

        tokenizer.setFileExtensions(new ArrayList<>(Arrays.asList(".tech", ".cfg",
                ".png", "jpeg", "jpg", ".mp4")));

        parseFile(tokenizer);
    }

    /**
     *
     * @return <code>Pair</>first - is parametr exists, second - value
     */
    public Pair<Boolean, Long> getImageDelay() {
        return imageDelay;
    }

    /**
     *
     * @return <code>Pair</>first - is parametr exists, second - value
     */
    public Pair<Boolean, Long> getVideoDelay() {
        return videoDelay;
    }

    /**
     *
     * @param command that should be execute
     * @return Action that must be execute
     */
    public Action getAction(String command) {
        Action action = customerCommands.get(command);
        if ((action == null) && (unknownCommand != null)) {
            action = unknownCommand;
        }
        return action;

    }

    /**
     *
     * @return Technology switcher as a String
     */
    public Action getTechnologySwitcher() {
        return technologySwitcher;
    }
    /**
     *
     * @return Process switcher as a String
     */
    public Action getProcessSwitcher() {
        return processSwitcher;
    }

    private void parseFile(Tokenizer tokenizer) throws IOException, DataFormatException {
        Token currentToken = null;
        Token prevToken = null;
        Token important = null;
        do {
            try {
                currentToken = tokenizer.next();
            } catch (DataFormatException invalidTypeForEncoding) {
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
                            unknownCommand = new Action(ActionType.valueOf(currentToken.value.toUpperCase()), new ArrayList<>());

                        }

                    } else if (important.type == TokenType.CUSTOMER_COMMAND) {
                        customerCommands.put(important.value,
                                new Action(ActionType.valueOf(currentToken.value.toUpperCase()), new ArrayList<>()));
                    }
                    important = null;
                    prevToken = null;

                    break;
                }
                case CUSTOMER_COMMAND: {
                    if ((important != null && important.type == TokenType.CUSTOMER_COMMAND) || prevToken != null &&
                            ((prevToken.type == TokenType.CUSTOMER_COMMAND))) {
                        throw new InvalidPropertiesFormatException(String.format("Invalid sequence of tokens: %s; %s",
                                prevToken, currentToken));

                    }
                    if (prevToken != null && prevToken.type == TokenType.ASSIGNER) {
                        if (important != null && important.type == TokenType.SPECIAL_COMMAND) {
                            if (important.value.equals(PROCESS_SWITCHER)) {
                                processSwitcher = new Action(ActionType.SWITCH_PROCESS,
                                        new ArrayList<>(Collections.singletonList(currentToken.value)));
                                important = null;
                                prevToken = null;
                                break;
                            } else if (important.value.equals(TECHNOLOGY_SWITCHER)) {
                                technologySwitcher = new Action(ActionType.SWITCH_TECHNOLOGY,
                                        new ArrayList<>(Collections.singletonList(currentToken.value)));
                                important = null;
                                prevToken = null;
                                break;
                            }
                        }

                    }
                    prevToken = currentToken;
                    important = currentToken;
                    break;
                }
                case PATH: {
                    if (important == null || prevToken == null || prevToken.type != TokenType.ASSIGNER ||
                            (important.type != TokenType.CUSTOMER_COMMAND && important.type != TokenType.SPECIAL_COMMAND)) {
                        throw new InvalidPropertiesFormatException(String.format("Invalid sequence of tokens: %s; %s; %s",
                                important, prevToken, currentToken));
                    }

                    if (important.type == TokenType.SPECIAL_COMMAND) {
                        if (important.value.equals(UNKNOWN_COMMAND)) {
                            unknownCommand = new Action(ActionType.EXECUTE, new ArrayList<>(Collections.singletonList(currentToken.value)));
                        }
                    } else if (important.type == TokenType.CUSTOMER_COMMAND) {
                        customerCommands.put(important.value, new Action(ActionType.EXECUTE,
                                new ArrayList<>(Collections.singletonList(currentToken.value))));
                    }

                    important = null;
                    prevToken = null;
                    break;
                }

                case NUMBER: {
                    if (prevToken == null || important == null || prevToken.type != TokenType.ASSIGNER || important.type != TokenType.SPECIAL_COMMAND) {
                        throw new InvalidPropertiesFormatException(String.format("Invalid sequence of tokens: %s; %s; %s",
                                important, prevToken, currentToken));
                    }

                    try {
                        switch (important.value) {
                            case IMAGE_DELAY:
                                imageDelay = new Pair<>(true, Long.parseLong(currentToken.value));
                                break;
                            case VIDEO_DELAY:
                                videoDelay = new Pair<>(true, Long.parseLong(currentToken.value));
                                break;
                            default:
                                throw new InvalidPropertiesFormatException(String.format("Invalid sequence of tokens: %s; %s; %s",
                                        important, prevToken, currentToken));
                        }
                    } catch (NumberFormatException err) {
                        throw new DataFormatException("Invalid NUMBER " + currentToken);
                    }
                    important = null;
                    prevToken = null;
                    break;
                }
                case UNKNOWN:
                    throw new DataFormatException("Unknown token: " + currentToken);
                case INVALID:
                    throw new DataFormatException("Invalid token: " + currentToken);
            }
        } while (tokenizer.hasNext());

    }


}
