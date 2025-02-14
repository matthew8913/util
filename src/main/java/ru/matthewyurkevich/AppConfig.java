package ru.matthewyurkevich;

import java.io.File;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class AppConfig {
    private final List<File> inputFiles;
    private boolean appendToFiles;
    private String outputPath;
    private String prefix;
    private boolean fullStatistics;

    public AppConfig() {
        this.appendToFiles = false;
        this.prefix = "";
        this.outputPath = Paths.get(".").toAbsolutePath().normalize() + "/";
        this.fullStatistics = false;
        this.inputFiles = new ArrayList<>();
    }

    public void addInputFile(File file) {
        this.inputFiles.add(file);
    }

    public boolean isAppendToFiles() {
        return appendToFiles;
    }

    public void setAppendToFiles(boolean appendToFiles) {
        this.appendToFiles = appendToFiles;
    }

    public String getOutputPath() {
        return outputPath;
    }

    public void setOutputPath(String outputPath) {
        this.outputPath = outputPath;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public boolean isFullStatistics() {
        return fullStatistics;
    }

    public void setFullStatistics(boolean fullStatistics) {
        this.fullStatistics = fullStatistics;
    }

    public List<File> getInputFiles() {
        return inputFiles;
    }
}