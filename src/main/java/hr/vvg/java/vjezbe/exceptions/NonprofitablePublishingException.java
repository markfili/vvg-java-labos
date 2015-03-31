package hr.vvg.java.vjezbe.exceptions;

/**
 * Created by marko on 3/28/15.
 * runtime exception - unchecked
 */
public class NonprofitablePublishingException extends RuntimeException {
    public NonprofitablePublishingException() {
        super();
    }

    public NonprofitablePublishingException(String message) {
        super(message);
    }

    public NonprofitablePublishingException(String message, Throwable cause) {
        super(message, cause);
    }

    public NonprofitablePublishingException(Throwable cause) {
        super(cause);
    }
}
