package ru.spbspu.machinary.client;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Action {
    private Actions action;
    private List<String> files;

    public Action(Actions action, List<String> files) {
        this.action = action;
        this.files = files;
    }

    public void addFiles(String... paths) {
        files.addAll(new ArrayList<>(Arrays.asList(paths)));
    }

    public Actions getAction() {
        return action;
    }

    public List<String> getFiles() {
        return files;
    }
}

enum Actions {
    EXECUTE, SKIP, EXIT, FAIL, CRUSH, SWITCH_TECHNOLOGY, SWITCH_PROCESS
}
