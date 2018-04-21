package ru.spbstu.machinery.client;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * class Action describe special action, that should be execute
 */
public class Action {
    private ActionType actionType;
    private List<String> files;

    public Action(ActionType actionType, List<String> files) {
        this.actionType = actionType;
        this.files = files;
    }

    /**
     * @param paths - paths to files that should be execute by this command if action type is EXECUTE, SWITCH_TECHNOLOGY or SWITCH_PROCESS
     */
    public void addFiles(String... paths) {
        files.addAll(new ArrayList<>(Arrays.asList(paths)));
    }

    /**
     *
     * @return type of action that should be execute
     */
    public ActionType getActionType() {
        return actionType;
    }

    /**
     *
     * @return List of file paths like a List of Strings
     */
    public List<String> getFilesPaths() {
        return files;
    }

    @Override
    public String toString() {
        return "Type: " + actionType + " paths: " + files;
    }
}

enum ActionType {
    EXECUTE, SKIP, EXIT, FAIL, CRUSH, SWITCH_TECHNOLOGY, SWITCH_PROCESS
}
