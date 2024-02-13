package ru.cft.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ListIterator;
import java.util.Scanner;

public class Utility {
    /**
     * Содержит тип статистики.
     */
    private Command statisticsType;
    /**
     * Содержит путь сохранения файла.
     */
    private File savingPath;
    /**
     * Содержит префикс.
     */
    private String prefix;
    /**
     * Говорит, какой режим для выходных файлов используется.
     */
    private boolean appendingMode;
    /**
     * Сканер, используемый в любом вводе пользователем в консоли.
     */
    private final Scanner scanner;
    /**
     * Список, хранящий исходные файлы.
     */
    private final List<File> fileSources;
    /**
     * Список, хранящий все строки исходных файлов.
     */
    private final List<String> strings;
    /**
     * Список, хранящий все целые числа исходных файлов.
     */
    private final List<Integer> integers;
    /**
     * Список, хранящий все числа с плавающей запятой.
     */
    private final List<Float> floats;

    /**
     * Конструктор.
     */
    public Utility() {
        System.setOut(new PrintStream(System.out));
        statisticsType = null;
        prefix = "";
        appendingMode = false;
        savingPath = new File(System.getProperty("user.dir"));
        fileSources = new ArrayList<>();
        strings = new ArrayList<>();
        integers = new ArrayList<>();
        floats = new ArrayList<>();
        scanner = new Scanner(System.in, StandardCharsets.UTF_8);
    }

    /**
     * Метод запуска программы.
     */
    public void run() {
        input();
        this.scanner.close();
        executeCommand();
    }

    /**
     * Метод, выводящий инструкцию в консоль.
     */
    public void welcomeMessage() {
        String fileName = "/welcome_message.txt";

        try {
            InputStream inputStream = getClass().getResourceAsStream(fileName);
            if (inputStream != null) {
                String content = new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);
                System.out.println(content);
            } else {
                System.out.println("File not found: " + fileName);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Метод, отвечающий за ввод пользователя.
     */
    public void input() {
        System.out.println("Enter the command: ");
        String input;
        List<String> inputStrings;
        input = this.scanner.nextLine();
        inputStrings = Arrays.asList(input.split(" "));
        setInput(inputStrings);
    }

    /**
     * Метод, конфигурирующий программу по пользовательскому вводу.
     *
     * @param input Список строк(входная строка, разделенная пробелами).
     */
    public void setInput(List<String> input) {
        ListIterator<String> inputIterator = input.listIterator();

        //Проверка каждой команды
        boolean commandIsValid = true;
        while (commandIsValid) {
            try {
                commandIsValid = setCommands(inputIterator);
            } catch (CommandException e) {
                handleCommandException(e);
            }
        }

        //Проверка каждого исходного файла
        boolean sourceFileIsValid = true;
        while (sourceFileIsValid) {
            try {
                sourceFileIsValid = setFileSources(inputIterator);
            } catch (CommandException e) {
                handleCommandException(e);
            }
        }

        //Проверка наличия исходников в команде
        try {
            checkFiles();
        } catch (CommandException e) {
            handleCommandException(e);
        }

    }

    /**
     * Метод, проверяющий корректность команды и настраивающий программу по ней.
     *
     * @param iterator Итератор используемый для просмотра команд во введенной строке.
     * @return Корректность команды.
     * @throws CommandException Если допущена ошибка в веденной команде.
     */
    public boolean setCommands(ListIterator<String> iterator) throws CommandException {
        if (iterator.hasNext()) {
            String s = iterator.next();
            Command command = Command.fromString(s);
            if (command != null) {
                switch (command) {
                    case S, F -> {
                        statisticsType = command;
                        return true;
                    }
                    case O -> {
                        if (iterator.hasNext()) {
                            String savingPath = iterator.next();
                            File path = new File(savingPath);
                            if (path.exists() && path.isDirectory()) {
                                this.savingPath = path;
                                return true;
                            } else {
                                throw new CommandException(ErrorCode.SAVE_PATH_NOT_EXIST, savingPath);
                            }
                        } else {
                            throw new CommandException(ErrorCode.SAVE_PATH_NOT_SPECIFIED);
                        }
                    }
                    case A -> {
                        appendingMode = true;
                        return true;
                    }
                    case P -> {
                        if (iterator.hasNext()) {
                            prefix = iterator.next();
                        }
                        return true;
                    }
                }
            } else {
                //команды закончились
                iterator.previous();
                return false;
            }
        } else {
            return false;
        }

        return false;
    }

    /**
     * Метод, проверяющий корректность пути файла во введенной строке и устанавливающий его.
     *
     * @param iterator Итератор используемый для просмотра строк в команде.
     * @return Корректен/не корректен путь.
     * @throws CommandException Если файл некорректен.
     */
    public boolean setFileSources(ListIterator<String> iterator) throws CommandException {
        if (iterator.hasNext()) {
            String path = iterator.next();
            if (checkFile(path)) {
                fileSources.add(new File(path));
                return true;
            } else {
                throw new CommandException(ErrorCode.SINGLE_SOURCE_FILE_NOT_FOUND, path);
            }
        } else {
            return false;
        }
    }

    /**
     * Метод проверки наличия исходных файлов.
     *
     * @throws CommandException Если файлов не обнаружено.
     */
    public void checkFiles() throws CommandException {
        if (fileSources.isEmpty()) {
            throw new CommandException(ErrorCode.SOURCE_FILES_NOT_FOUND);
        }
    }


    /**
     * Обработчик исключительных ситуаций пользовательского ввода.
     *
     * @param e Исключение.
     */
    public void handleCommandException(CommandException e) {
        ErrorCode error = e.getErrorCode();
        switch (error) {
            case SAVE_PATH_NOT_EXIST -> {
                System.out.println(e.getMessage());
                if (askForCommandCorrection()) {
                    correctSavingPath();
                }
            }
            case SOURCE_FILES_NOT_FOUND -> {
                System.out.println(e.getMessage());
                System.out.println("If you decide not to enter the file paths, the program will stop.");
                if (askForCommandCorrection()) {
                    correctFileSources();
                } else {
                    System.exit(0);
                }
            }
            case SINGLE_SOURCE_FILE_NOT_FOUND -> {
                System.out.println(e.getMessage());
                if (askForCommandCorrection()) {
                    correctSingleSourceFile();
                }
            }
        }
    }

    /**
     * Метод, спрашивающий у пользователя, будет ли он корректировать значение.
     *
     * @return Будет/не будет.
     */
    public boolean askForCommandCorrection() {
        System.out.println("To correct press 1\n" +
                "To skip press 0");
        String s = this.scanner.nextLine();
        if (s.equals("0")) {
            return false;
        } else if (s.equals("1")) {
            return true;
        } else {
            System.out.println("Enter the correct value: ");
            return askForCommandCorrection();
        }

    }

    /**
     * Метод, проверяющий существования файла по строке.
     *
     * @param path Строка содержащая путь к файлу.
     * @return Существует ли такой файл.
     */
    public boolean checkFile(String path) {
        File file = new File(path);
        return file.exists() && !file.isDirectory();
    }

    /**
     * Метод, проверяющий существования директории по строке.
     *
     * @param path Строка содержащая путь.
     * @return Существует ли такая директория.
     */
    public boolean checkDirectory(String path) {
        File file = new File(path);
        return file.exists() && file.isDirectory();

    }

    /**
     * Метод для корректировки пути сохранения. Использует ввод с консоли. Валидация присутствует.
     */
    public void correctSavingPath() {
        String path;
        do {
            System.out.println("Enter the correct path or press enter(default path): ");
            path = this.scanner.nextLine();
            if (path.equals("")) return;
        } while (!checkDirectory(path));
        savingPath = new File(path);
    }

    /**
     * Метод, добавляющий один файл в список исходников. Использует ввод с консоли. Валидация присутствует.
     */
    public void correctSingleSourceFile() {
        String path;
        do {
            System.out.println("Enter the correct path or skip(enter):");
            path = this.scanner.nextLine();
        } while (!checkFile(path) && !path.isEmpty());
        if (!path.isEmpty()) fileSources.add(new File(path));
    }

    /**
     * Метод, добавляющий несколько файлов в список исходников. Использует ввод с консоли. Валидация присутствует.
     */
    public void correctFileSources() {
        System.out.println("Enter the file paths, enter an empty line if you are finished.");
        String path;
        do {
            System.out.println("Enter file path: ");
            path = this.scanner.nextLine();
            while (!checkFile(path) && !path.isEmpty()) {
                System.out.println("Previous path was incorrect, enter the correct path or empty line:");
                path = this.scanner.nextLine().trim();
            }
            if (path.isEmpty()) break;
            else fileSources.add(new File(path));
        } while (true);

    }

    /**
     * Метод, выполняющий основную логику программы.
     */
    public void executeCommand() {
        List<String> stringsList = new ArrayList<>();

        for (File file : fileSources) {
            try (Scanner scanner = new Scanner(new FileInputStream(file), StandardCharsets.UTF_8)) {
                while (scanner.hasNextLine()) {
                    stringsList.add(scanner.nextLine());
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        collectData(stringsList);
        if (statisticsType != null) {
            printStatistics(this.statisticsType);
        }
        createFiles();
    }

    /**
     * Метод вывода в консоль статистики по данным.
     *
     * @param statisticsType Тип статистики.
     */
    public void printStatistics(Command statisticsType) {
        String intStats = "";
        String floatStats = "";
        String stringStats = "";
        switch (statisticsType) {
            case S -> {
                intStats = "Amount: " + integers.size();
                floatStats = "Amount: " + floats.size();
                stringStats = "Amount: " + strings.size();
            }
            case F -> {
                intStats = intFullStats();
                floatStats = floatFullStats();
                stringStats = stringFullStats();
            }
        }
        System.out.println("IntStats:\n" + intStats);
        System.out.println("FloatStats:\n" + floatStats);
        System.out.println("StringStats:\n" + stringStats);
    }

    /**
     * Метод, собирающий статистику по целым числам.
     *
     * @return Строка, содержащая статистику.
     */
    public String intFullStats() {
        if (!integers.isEmpty()) {
            StringBuilder sb = new StringBuilder();
            int sum = 0;
            int max = Integer.MIN_VALUE;
            int min = Integer.MAX_VALUE;
            for (int i : integers) {
                sum += i;
                max = Math.max(i, max);
                min = Math.min(i, min);
            }
            int average = sum / integers.size();
            sb.append("min: ").append(min).append("\n")
                    .append("max: ").append(max).append("\n")
                    .append("sum: ").append(sum).append("\n")
                    .append("average: ").append(average).append("\n");
            return sb.toString();
        } else {
            return null;
        }
    }

    /**
     * Метод, собирающий статистику по числам с плавающей запятой.
     *
     * @return Строка, содержащая статистику.
     */
    public String floatFullStats() {
        if (!floats.isEmpty()) {
            StringBuilder sb = new StringBuilder();
            float sum = 0;
            float max = Integer.MIN_VALUE;
            float min = Integer.MAX_VALUE;
            for (float i : floats) {
                sum += i;
                max = Math.max(i, max);
                min = Math.min(i, min);
            }
            float average = sum / floats.size();
            sb.append("min: ").append(min).append("\n")
                    .append("max: ").append(max).append("\n")
                    .append("sum: ").append(sum).append("\n")
                    .append("average: ").append(average).append("\n");
            return sb.toString();
        } else {
            return null;
        }
    }

    /**
     * Метод, собирающий статистику по строкам.
     *
     * @return Строка, содержащая статистику.
     */
    public String stringFullStats() {
        if (!strings.isEmpty()) {
            StringBuilder sb = new StringBuilder();
            int max = Integer.MIN_VALUE;
            int min = Integer.MAX_VALUE;
            for (String s : strings) {
                int length = s.length();
                max = Math.max(length, max);
                min = Math.min(length, min);
            }
            sb.append("min: ").append(min).append("\n")
                    .append("max: ").append(max).append("\n");
            return sb.toString();
        } else {
            return null;
        }
    }


    /**
     * Метод, распределяющий данные по соответствующим спискам.
     *
     * @param stringsList Нераспределенный список.
     */
    public void collectData(List<String> stringsList) {
        for (String s : stringsList) {
            try {
                int i = Integer.parseInt(s);
                this.integers.add(i);
            } catch (NumberFormatException e1) {
                try {
                    float f = Float.parseFloat(s);
                    this.floats.add(f);
                } catch (NumberFormatException e2) {
                    this.strings.add(s);
                }
            }
        }
    }

    /**
     * Метод, организующий файлы для распределения данных.
     */
    public void createFiles() {
        if (!integers.isEmpty()) {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(savingPath + "/" + prefix + "integers.txt", appendingMode))) {
                for (int i : integers) {
                    writer.write(i + "\n");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (!floats.isEmpty()) {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(savingPath + "/" + prefix + "floats.txt", appendingMode))) {
                for (float f : floats) {
                    writer.write(f + "\n");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (!strings.isEmpty()) {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(savingPath + "/" + prefix + "strings.txt", appendingMode))) {
                for (String s : strings) {
                    writer.write(s + "\n");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}

