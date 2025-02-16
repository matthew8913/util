package ru.matthewyurkevich;

import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        try {
            AppConfig config = new CommandLineParser().parseArgs(args);
            FileProcessor processor = new FileProcessor(config);
            processor.processFiles();
            processor.printStatistics();
        } catch (Exception e) {
            System.err.println("Ошибка: " + e.getMessage());
        }
    }
}