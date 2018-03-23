package ru.spbspu.machinary.client;

import javax.swing.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.InvalidPropertiesFormatException;
import java.util.Scanner;

public class ConfigParser {
    private String ip;
    private String port;
    private final String PORT = "port";
    private final String IP = "ip";
    private final String ASSIGNER = ":";
    private File file;

    public ConfigParser(String path) throws FileNotFoundException, InvalidPropertiesFormatException {
        file = new File(path);
        parse();
    }

    public String getIp() {
        return ip;
    }

    public String getPort() {
        return port;
    }

    private void parse() throws FileNotFoundException, InvalidPropertiesFormatException {
        Scanner scanner = new Scanner(file);
        while (scanner.hasNext()) {
            String str = scanner.nextLine().toLowerCase();
            if (str.startsWith(IP)) {
                String strWithoutPrefix = str.substring(IP.length());
                if (strWithoutPrefix.length() < 8) {
                    throw new InvalidPropertiesFormatException("You must write ip: <your IPv4>");
                }
                int index = strWithoutPrefix.indexOf(ASSIGNER);
                if (index < 0 || index >= strWithoutPrefix.length()) {
                    throw new InvalidPropertiesFormatException("You must write ip: <your IPv4>");
                }
                ip = strWithoutPrefix.substring(index + 1).trim();
            }
            if (str.startsWith(PORT)) {
                String strWithoutPrefix = str.substring(PORT.length());
                if (strWithoutPrefix.length() < 2) {
                    throw new InvalidPropertiesFormatException("You must write port: <port>");
                }
                int index = strWithoutPrefix.indexOf(ASSIGNER);
                if (index < 0 || index >= strWithoutPrefix.length()) {
                    throw new InvalidPropertiesFormatException("You must write port: <port>");
                }
                port = strWithoutPrefix.substring(index + 1).trim();
            }
        }
    }
}
