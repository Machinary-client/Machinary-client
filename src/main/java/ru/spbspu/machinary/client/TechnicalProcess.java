package ru.spbspu.machinary.client;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.zip.DataFormatException;

public class TechnicalProcess {

    private static final String PATH = "processes/";
    private static final String FILE_EXTENSION = ".tech";
    private static final String DEFAULT_FILE_NAME = "default";

    private TechnicalFile common;
    private TechnicalFile defaultFile;
    private TechnicalFile currentFile;
    private String processName;

    private Action technologySwitcher;
    private Action processSwitcher;

    public TechnicalProcess() throws IOException, DataFormatException {
        common = readCommon();
        processSwitcher = common.getProcessSwitcher();
        if (processSwitcher == null) {
            throw new IOException("process switcher must be in common.cfg file");
        }
        technologySwitcher = common.getTechnologySwitcher();
    }

    public TechnicalProcess(String name) throws IOException, DataFormatException { // TODO: 23.03.2018 add nonnull
        this();
        defaultFile = new TechnicalFile(PATH + name + "/" + DEFAULT_FILE_NAME + FILE_EXTENSION);
        processName = name;
        Action switcher = defaultFile.getTechnologySwitcher();
        if ((switcher == null) && technologySwitcher == null) {
            throw new IOException("Technology switcher must be in default.tech on process: " + name + " or in config.cfg");
        }
        technologySwitcher = switcher;
    }

    public Action getAction(String command) throws IOException, DataFormatException { // TODO: 23.03.2018 add NonNull
        Action action = checkToTechnologyAndProcessSwitchers(command);
        if ((action != null) && action.getActionType() == ActionType.SWITCH_TECHNOLOGY) {
            switchTechnology(action.getFilesPaths().get(0));
            return action;
        } else if ((action != null) && action.getActionType() == ActionType.SWITCH_PROCESS) {
            return action;
        }
        if (currentFile != null) {
            action = currentFile.getAction(command);
        }

        if (defaultFile != null && (action == null)) {
            action = defaultFile.getAction(command);
        }

        if (action == null) {
            action = common.getAction(command);
        }

        if (action == null) {
            /*
              if we don't know wat to do with unknown_command it will be crush system, because it unexpected command
              */
            action = new Action(ActionType.FAIL, new ArrayList<>());
        }

        return action;
    }

    public long getImageDelay() {
        if (currentFile != null && currentFile.getImageDelay().getKey()) {
            return currentFile.getImageDelay().getValue();
        }
        if (defaultFile != null && defaultFile.getImageDelay().getKey()) {
            return defaultFile.getImageDelay().getValue();
        }
        if (common.getImageDelay().getKey()) {
            return common.getImageDelay().getValue();
        }
        return 0;
    }

    public long getVideoDelay() {
        if (currentFile != null && currentFile.getVideoDelay().getKey()) {
            return currentFile.getVideoDelay().getValue();
        }
        if (defaultFile != null && defaultFile.getVideoDelay().getKey()) {
            return defaultFile.getVideoDelay().getValue();
        }
        if (common.getVideoDelay().getKey()) {
            return common.getVideoDelay().getValue();
        }
        return 0;
    }

    private Action checkToTechnologyAndProcessSwitchers(String command) throws IOException {
        if (processSwitcher == null) {
            throw new IOException("process switcher must be in common.cfg file");
        }
        List<String> switchers = processSwitcher.getFilesPaths();
        if (switchers == null || switchers.isEmpty()) {
            throw new RuntimeException("Can't find process_switcher");
        }
        String switchProcess = switchers.get(0);
        if (command.startsWith(switchProcess)) {
            if (switchProcess.length() >= command.length()) {
                throw new IOException("command must have name of process");
            }
            String pName = command.substring(switchProcess.length() + 1);
            return new Action(ActionType.SWITCH_PROCESS, new ArrayList<>(Collections.singletonList(pName)));
        }
        if (technologySwitcher != null) {
            switchers = technologySwitcher.getFilesPaths();
            if (switchers == null || switchers.isEmpty()) {
                throw new RuntimeException("Can't find technology_switcher");
            }
            String tSwitcher = switchers.get(0);
            if (command.startsWith(tSwitcher)) {
                if (tSwitcher.length() >= command.length()) {
                    throw new IOException("command must have name of switcher");
                }
                String tName = command.substring(tSwitcher.length() + 1);
                return new Action(ActionType.SWITCH_TECHNOLOGY, new ArrayList<>(Collections.singletonList(tName)));
            }
        }
        return null;
    }

    private void switchTechnology(String techName) throws IOException, DataFormatException {

        if (processName == null) {
            throw new IOException("Can't find process name and path");
        }
        currentFile = new TechnicalFile(PATH + processName + "/" + techName + FILE_EXTENSION);
    }

    private static TechnicalFile readCommon() throws IOException, DataFormatException {
        return new TechnicalFile(PATH + "common.cfg");
    }
}
