package ru.matthewyurkevich;

import java.io.File;

public class CommandLineParser {
    public AppConfig parseArgs(String[] args) throws IllegalArgumentException {
        AppConfig config = new AppConfig();
        for (int i = 0; i < args.length; i++) {
            String arg = args[i];
            switch (arg) {
                case "-o" -> setOutputPath(config, args[++i]);
                case "-a" -> config.setAppendToFiles(true);
                case "-p" -> config.setPrefix(args[++i]);
                case "-s" -> config.setFullStatistics(false);
                case "-f" -> config.setFullStatistics(true);
                default -> addInputFile(config, arg);
            }
        }
        return config;
    }

    private void setOutputPath(AppConfig config, String path) {
        File dir = new File(path);
        if (dir.exists() && dir.isDirectory()) {
            config.setOutputPath(dir.getAbsolutePath());
        } else {
            throw new IllegalArgumentException("Некорректный путь для выходных данных: " + path);
        }
    }

    private void addInputFile(AppConfig config, String filePath) {
        File file = new File(filePath);
        if (file.exists()) {
            config.addInputFile(file);
        } else {
            throw new IllegalArgumentException("Файл входных данных не найден: " + filePath);
        }
    }
}