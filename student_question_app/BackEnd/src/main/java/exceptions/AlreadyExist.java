package exceptions;

public class AlreadyExist extends Exception{
    public AlreadyExist() {
        super();
    }

    public AlreadyExist(String message) {
        super(message);
    }

    public AlreadyExist(String message, Throwable cause) {
        super(message, cause);
    }

    public AlreadyExist(Throwable cause) {
        super(cause);
    }

    protected AlreadyExist(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
