package yorm.exception;

/**
 * Custom exception to be thrown in the Yorm application.
 */
public class YormException extends Exception {
    public YormException(String errorMessage) {
        super(errorMessage);
    }
}
