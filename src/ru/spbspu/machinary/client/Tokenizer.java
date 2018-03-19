package ru.spbspu.machinary.client;

import org.omg.IOP.CodecPackage.InvalidTypeForEncoding;

import java.io.*;
import java.util.Scanner;


public class Tokenizer {

    private final char DELIMITER = ':';

    private PushbackInputStream inputStream;

    public Tokenizer(File file) throws FileNotFoundException {
        inputStream = new PushbackInputStream(new FileInputStream(file));
    }

    boolean hasNext() throws IOException {
        return inputStream.available() > 0;
    }

    Token next() throws IOException, InvalidTypeForEncoding {
        Token token = new Token();
        int quotes = 0;
        boolean end = false;
        while (hasNext()) {
            char ch = (char) inputStream.read();

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
            if (!Character.isSpaceChar(ch)) {
                token.type = (token.type == TokenType.UNKNOWN && token.value.isEmpty() && (ch == '\"')) ?
                        TokenType.CUSTOMER_INPUT : TokenType.SPECIAL_INPUT;
            }

            if (token.type == TokenType.CUSTOMER_INPUT) {
                boolean checkQuotes = true;
                if (ch == '\\') {
                    checkQuotes = false;
                    ch = (char) inputStream.read();
                    if (ch != '\\' && ch != '#' && ch != '\'' && ch != '\"') {
                        throw new InvalidTypeForEncoding("Invalid token symbol " + token);
                    }
                }
                token.value += ch;

                if (ch == '\"' && (quotes != 0) && checkQuotes) {
                    checkToken(token);
                    return token;
                }
                if (token.value.isEmpty()) {
                    quotes = 1;
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

        }

        if (end) {
            //TODO: do something
        }

        return token;
    }

    private int skip() throws IOException {
        char ch = 0;
        while ((inputStream.available() > 0) && ch != '\n') {
            int i = inputStream.read();
            if (i < 0) {
                return -1;
            }
            ch = (char) i;
        }
        return ch;
    }

    private void checkToken(Token token) {

    }
}
