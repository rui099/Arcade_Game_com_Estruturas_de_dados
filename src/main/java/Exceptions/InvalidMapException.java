package Exceptions;

public class InvalidMapException extends Exception {

    public InvalidMapException() {
    }

    public InvalidMapException(String message) {
        super(message);
    }

    public InvalidMapException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidMapException(Throwable cause) {
        super(cause);
    }

    public InvalidMapException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
