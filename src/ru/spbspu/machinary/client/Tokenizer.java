package ru.spbspu.machinary.client;

import com.sun.jdi.InvalidTypeException;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class Tokenizer {

    private final char DELIMITER = ':';
    private List<String> specialCommands = new ArrayList<>();
    private List<String> inputActions = new ArrayList<>();
    private List<String> fileExtensions = new ArrayList<>();
    private PushbackInputStream inputStream;

    public Tokenizer(InputStream input) {
        inputStream = new PushbackInputStream(input);
    }

    public void setInputActions(List<String> inputActions) {
        this.inputActions = inputActions;
    }

    public void setSpecialCommands(List<String> commands) {
        specialCommands = commands;
    }

    public void setFileExtensions(List<String> extensions) {
        fileExtensions = extensions;
    }

    boolean hasNext() throws IOException {
        return inputStream.available() > 0;
    }

    Token next() throws IOException, InvalidTypeException {
        Token token = new Token();
        int quotes = 0;
        boolean end = false;

        while (hasNext()) {
            char ch = (char) inputStream.read();
            //System.out.println("p1: ch = " + ch + "  hasNext = " + hasNext());

            if (ch == '#') { //skip comments
                int i = skip();
                if (i == -1) {
                    end = true;
                    break;
                }
                ch = (char) i;
            }

            if (!token.value.isEmpty() && (ch == '\n')) {
                checkToken(token);
                return token;
            }


            if ((token.type == TokenType.UNKNOWN) && (ch == DELIMITER)) {
                token.value += ch;
                token.type = TokenType.DELIMITER;
                return token;
            }
            if (!Character.isWhitespace(ch) && (token.type == TokenType.UNKNOWN)) {
                token.type = (token.type == TokenType.UNKNOWN && token.value.isEmpty() && (ch == '\"')) ?
                        TokenType.CUSTOMER_INPUT : TokenType.SPECIAL_INPUT;
            }

            if (token.type == TokenType.CUSTOMER_INPUT) {
                boolean checkQuotes = true;
                if (token.value.isEmpty()) {
                    checkQuotes = false;
                    quotes = 1;
                }
                if (ch == '\\') {
                    checkQuotes = false;
                    ch = (char) inputStream.read();
                    if (ch != '\\' && ch != '#' && ch != '\'' && ch != '\"') {
                        throw new InvalidTypeException("Invalid token symbol " + token);
                    }
                }
                token.value += ch;

                if (ch == '\"' && (quotes != 0) && checkQuotes) {
                    checkToken(token);
                    return token;
                }

            }

            if (token.type == TokenType.SPECIAL_INPUT) {
                if ((ch == DELIMITER) || (ch == ' ')) {
                    inputStream.unread(ch);
                    checkToken(token);
                    return token;
                }
                token.value += ch;
            }
            //System.out.println(token);
        }

        if (end) {
            if (token.value.isEmpty()) {
                return null;
            }
            checkToken(token);
        }

        return token;
    }

    private int skip() throws IOException {
        char ch = 0;
        while ((inputStream.available() >= 0) && ch != '\n') {
            int i = inputStream.read();
            if (i < 0) {
                return -1;
            }
            ch = (char) i;
        }
        return ch;
    }

    private void checkToken(Token token) {
        switch (token.type) {
            case CUSTOMER_INPUT: {
                if ((token.value.length() <= 1) || token.value.charAt(token.value.length() - 1) != '\"') {
                    token.type = TokenType.INVALID;
                    return;
                }
                token.value = token.value.substring(1, token.value.length() - 1);
                token.type = isPath(token) ? TokenType.PATH : TokenType.CUSTOMER_COMMAND;
                if (isNumber(token.value)) {
                    token.type = TokenType.NUMBER;
                }
                break;
            }
            case SPECIAL_INPUT: {
                if ((token.value.indexOf('\"') != -1) || (token.value.indexOf('\'') != -1)) {
                    token.type = TokenType.INVALID;
                    return;
                }
                if (specialCommands.contains(token.value)) {
                    token.type = TokenType.SPECIAL_COMMAND;
                    return;
                }
                if (inputActions.contains(token.value)) {
                    token.type = TokenType.INPUT_ACTION;
                    return;
                }
                token.type = TokenType.UNKNOWN;
                break;
            }
        }
    }

    private boolean isNumber(String value) {
        for (char ch : value.toCharArray()) {
            if (!Character.isDigit(ch)) {
                return false;
            }
        }
        return true;
    }

    private boolean isPath(Token token) {
        for (String string : fileExtensions) {
            if (token.value.endsWith(string)) {
                return true;
            }
        }
        return false;
    }
}
