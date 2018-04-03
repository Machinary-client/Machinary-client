package ru.spbspu.machinary.client;

public class Token {
    TokenType type = TokenType.UNKNOWN;
    String value = "";

    @Override
    public String toString() {
        return String.format("type: %s; value: \"%s\"", type, value);
    }
}

enum TokenType {
    UNKNOWN, SPECIAL_INPUT, INPUT_ACTION, SPECIAL_COMMAND, ASSIGNER,              /* BRACKETS, */
    CUSTOMER_INPUT, PATH, CUSTOMER_COMMAND, NUMBER,
    INVALID
}


