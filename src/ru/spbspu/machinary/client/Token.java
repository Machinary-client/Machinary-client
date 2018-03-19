package ru.spbspu.machinary.client;

public class Token {
    TokenType type = TokenType.UNKNOWN;
    String value = "";
    /*
    boolean canBeAdd(char ch){
        if (type==TokenType.UNKNOWN){
            return true;
        }
        if (type==TokenType.SPECIAL_INPUT){

        }
    }
    */

    @Override
    public String toString() {
        return String.format("type: %s; value: %s", type, value);
    }
}

enum TokenType {
    UNKNOWN, SPECIAL_INPUT, INPUT_ACTION, SPECIAL_COMMAND, DELIMITER,              /* BRACKETS, */
    CUSTOMER_INPUT, PATH, CUSTOMER_COMMAND,
    INVALID
}

enum SpecialCommands {
    process_switcher, technology_switcher, image_delay, video_delay
}

