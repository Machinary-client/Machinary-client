package ru.spbspu.machinary.client;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.InvalidPropertiesFormatException;
import java.util.Scanner;

/**
 * Special file for parsing configuration file, that have ip address and port to connect
 */

public class ConfigParser {
    private String ip;
    private String port;
    private final String PORT = "port";
    private final String IP = "ip";
    private final String ASSIGNER = ":";
    private File file;

    /**
     *
     * @param path - path to configuration file
     * @throws FileNotFoundException - throws if file isn't found
     * @throws InvalidPropertiesFormatException - throws if data in file has incorrect format
     */
    public ConfigParser(String path) throws FileNotFoundException, InvalidPropertiesFormatException {
        file = new File(path);
        parse();
    }

    /**
     *
     * @return IP address (IPv4) for connection as a String
     */
    public String getIp() {
        return ip;
    }

    /**
     *
     * @return port of host for connection as a String
     */
    public String getPort() {
        return port;
    }

    /**
     *
     * Parse configuration file and initialize variables like ip and port
     *
     * @throws FileNotFoundException - throws if file isn't found
     * @throws InvalidPropertiesFormatException - throws if data in file has incorrect format
     */
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
