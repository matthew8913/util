package ru.matthewyurkevich;

import java.io.FileWriter;
import java.io.IOException;

public class FileWriterManager implements AutoCloseable {
    private final AppConfig config;
    private FileWriter intWriter;
    private FileWriter floatWriter;
    private FileWriter stringWriter;

    public FileWriterManager(AppConfig config) {
        this.config = config;
    }

    public void writeToIntFile(String str) {
        try {
            if (intWriter == null) {
                intWriter = new FileWriter(config.getOutputPath() + config.getPrefix() + "integers.txt", config.isAppendToFiles());
            }
            intWriter.write(str + "\n");
        } catch (IOException e) {
            System.err.println("Ошибка при создании/записи файла: " + e.getMessage());
        }

    }

    public void writeToFloatFile(String str) {
        try {
            if (floatWriter == null) {
                floatWriter = new FileWriter(config.getOutputPath() + config.getPrefix() + "floats.txt", config.isAppendToFiles());
            }
            floatWriter.write(str + "\n");
        } catch (IOException e) {
            System.err.println("Ошибка при создании/записи файла: " + e.getMessage());
        }
    }

    public void writeToStringFile(String str) {
        try {
            if (stringWriter == null) {
                stringWriter = new FileWriter(config.getOutputPath() + config.getPrefix() + "strings.txt", config.isAppendToFiles());
            }
            stringWriter.write(str + "\n");
        } catch (IOException e) {
            System.err.println("Ошибка при создании/записи файла: " + e.getMessage());
        }
    }


    @Override
    public void close() throws IOException {
        if (intWriter != null) {
            intWriter.close();
        }
        if (floatWriter != null) {
            floatWriter.close();
        }
        if (stringWriter != null) {
            stringWriter.close();
        }
    }
}