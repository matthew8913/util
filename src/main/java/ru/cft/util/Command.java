package ru.cft.util;

/**
 * Enum с командами, используемыми в программе.
 */
public enum Command {
    S("-s"),
    F("-f"),
    O("-o"),
    A("-a"),
    P("-p");

    /**
     * Команда в виде строки.
     */
    private final String command;

    /**
     * Конструктор.
     *
     * @param command Команда.
     */
    Command(String command) {
        this.command = command;
    }

    /**
     * Интерпретирует строку в команду, если таковая существует.
     *
     * @param text Команда в виде строки.
     * @return Команда. Если ее не существует - возвращает null.
     */
    public static Command fromString(String text) {
        for (Command c : Command.values()) {
            if (c.command.equalsIgnoreCase(text)) {
                return c;
            }
        }
        return null;
    }
}