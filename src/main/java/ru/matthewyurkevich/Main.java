package ru.matthewyurkevich;

import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        try {
            AppConfig config = new CommandLineParser().parseArgs(args);
            FileProcessor processor = new FileProcessor(config);
            processor.processFiles();
            processor.printStatistics();
        } catch (IllegalArgumentException e) {
            System.err.println("Некорректный аргумент командной строки: " + e.getMessage());
        } catch (IOException e) {
            System.err.println("Ошибка при закрытии ресурсов: " + e.getMessage());
        }
    }
}