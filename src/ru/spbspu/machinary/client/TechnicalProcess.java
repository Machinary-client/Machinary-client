package ru.spbspu.machinary.client;


import com.sun.jdi.InvalidTypeException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class TechnicalProcess {

    private final String PATH = "processes/";

    private final String FILE_EXTENSION = ".tech";

    TechnicalFile common;
    TechnicalFile defaultFile;

    public TechnicalProcess() throws IOException, InvalidTypeException {
        common = readCommon();
    }

    public TechnicalProcess(String name) throws IOException, InvalidTypeException {
        this();
        defaultFile = new TechnicalFile(PATH + name + "/default" + FILE_EXTENSION);
    }

    private static TechnicalFile readCommon() throws IOException, InvalidTypeException {
        return new TechnicalFile("processes/common.cfg");
    }
}
