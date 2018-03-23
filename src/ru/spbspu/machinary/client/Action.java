package ru.spbspu.machinary.client;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Action {
    private ActionType actionType;
    private List<String> files;

    public Action(ActionType actionType, List<String> files) {
        this.actionType = actionType;
        this.files = files;
    }

    public void addFiles(String... paths) {
        files.addAll(new ArrayList<>(Arrays.asList(paths)));
    }

    public ActionType getActionType() {
        return actionType;
    }

    public List<String> getFilesPaths() {
        return files;
    }
}

enum ActionType {
    EXECUTE, SKIP, EXIT, FAIL, CRUSH, SWITCH_TECHNOLOGY, SWITCH_PROCESS
}
