package exceptions;

public class MalformedQuestion extends Exception {
    public MalformedQuestion() {
    }

    public MalformedQuestion(String message) {
        super(message);
    }

    public MalformedQuestion(String message, Throwable cause) {
        super(message, cause);
    }

    public MalformedQuestion(Throwable cause) {
        super(cause);
    }

    public MalformedQuestion(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
