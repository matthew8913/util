package ru.cft.util;

/**
 * Исключение, отвечающее за ошибки пользовательского ввода.
 */
public class CommandException extends Exception {
    /**
     * Код ошибки.
     */
    private final ErrorCode errorCode;
    /**
     * Детали ошибки.
     */
    private final String errorDetail;

    /**
     * Конструктор. Используется, если у исключения есть детали.
     * @param errorCode Код ошибки.
     * @param errorDetail Детали ошибки.
     */
    public CommandException(ErrorCode errorCode, String errorDetail) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
        this.errorDetail = errorDetail;
    }

    /**
     * Конструктор.
     * @param errorCode Код ошибки.
     */
    public CommandException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
        this.errorDetail = null;
    }

    /**
     * Геттер.
     * @return Код ошибки.
     */
    public ErrorCode getErrorCode() {
        return errorCode;
    }

    /**
     * Геттер.
     * @return Детали ошибки.
     */
    public String getErrorDetail() {
        return errorDetail;
    }

    /**
     * Если у исключения есть детали - выводит сообщение с деталями, если нет, то, соответственно без них.
     * @return Сообщение об ошибке.
     */
    @Override
    public String getMessage() {
        if (errorDetail != null) {
            return errorCode.getMessage() + ": " + errorDetail;
        } else {
            return super.getMessage();
        }
    }
}
