package ru.matthewyurkevich;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

public class FileProcessor {
    private final AppConfig config;
    private final StatisticsCollector statisticsCollector;
    private final FileWriterManager writerManager;

    public FileProcessor(AppConfig config) {
        this.config = config;
        this.statisticsCollector = new StatisticsCollector();
        this.writerManager = new FileWriterManager(config);
    }

    public void processFiles() {
        List<File> inputFiles = config.getInputFiles();
        for (File file : inputFiles) {
            try {
                processFile(file);
            } catch (IOException e) {
                System.err.println("Ошибка при обработке файла: " + file.getName() + ". Пропускаем файл.");
            }
        }
        try {
            writerManager.close();
        } catch (IOException e) {
            System.err.println("Ошибка при закрытии ресурсов: " + e.getMessage());
        }
    }

    private void processFile(File file) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                processLine(line);
            }
        }
    }

    private void processLine(String line) {
        try {
            if (isInteger(line)) {
                statisticsCollector.collectInteger(line);
                writerManager.writeToIntFile(line);
            } else if (isFloat(line)) {
                statisticsCollector.collectFloat(line);
                writerManager.writeToFloatFile(line);
            } else {
                statisticsCollector.collectString(line);
                writerManager.writeToStringFile(line);
            }
        } catch (Exception e) {
            System.err.println("Ошибка при обработке строки: " + line + ". Пропускаем строку.");
        }
    }

    private boolean isInteger(String line) {
        try {
            Integer.parseInt(line);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private boolean isFloat(String line) {
        try {
            Float.parseFloat(line);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public void printStatistics() {
        if (config.isFullStatistics()) {
            statisticsCollector.printFullStatistics();
        } else {
            statisticsCollector.printShortStatistics();
        }
    }
}