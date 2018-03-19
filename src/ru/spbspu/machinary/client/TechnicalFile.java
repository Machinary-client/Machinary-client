package ru.spbspu.machinary.client;

import java.beans.XMLDecoder;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

public class TechnicalFile {

    private HashMap<String, Action> customerCommands;
    private String processSwitcher;
    private String technologySwitcher;
    private long imageDelay;
    private long videoDelay;
    private InputActions UnknownCommand;

    public TechnicalFile(String path) throws IOException {
        /*List<String> lines = Files.readAllLines(Paths.get(path));

        for (String line : lines) {
            if (line.isEmpty()) {
                continue;
            }

        }
*/
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
