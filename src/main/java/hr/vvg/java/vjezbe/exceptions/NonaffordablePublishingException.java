package hr.vvg.java.vjezbe.exceptions;

/**
 * to be thrown if the Publishing price is lower than the required one set for a specific Publication subclass
 * Created by marko on 3/28/15.
 * runtime exception - unchecked
 */
public class NonaffordablePublishingException extends RuntimeException {
    public NonaffordablePublishingException() {
        super();
    }

    public NonaffordablePublishingException(String message) {
        super(message);
    }

    public NonaffordablePublishingException(String message, Throwable cause) {
        super(message, cause);
    }

    public NonaffordablePublishingException(Throwable cause) {
        super(cause);
    }
}
