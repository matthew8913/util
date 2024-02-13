package ru.cft.util;

/**
 * Enum с кодами ошибок.
 */
public enum ErrorCode {
    SAVE_PATH_NOT_EXIST("The save path does not exist!"),
    SINGLE_SOURCE_FILE_NOT_FOUND("A single source file was not found!"),
    SOURCE_FILES_NOT_FOUND("No source file was found!"),
    SAVE_PATH_NOT_SPECIFIED("The save path not specified!");

    /**
     * Сообщение для пользователя.
     */
    private final String message;

    /**
     * Конструктор.
     *
     * @param message Сообщение для пользователя.
     */
    ErrorCode(String message) {
        this.message = message;
    }

    /**
     * Геттер.
     *
     * @return Сообщение для пользователя.
     */
    public String getMessage() {
        return message;
    }
}