package hr.vvg.java.vjezbe.exceptions;

/**
 * Created by marko on 3/28/15.
 * checked exception
 */
public class DuplicatePublicationException extends Exception {

    public DuplicatePublicationException() {
        super();
    }

    public DuplicatePublicationException(String message) {
        super(message);
    }

    public DuplicatePublicationException(String message, Throwable cause) {
        super(message, cause);
    }

    public DuplicatePublicationException(Throwable cause) {
        super(cause);
    }
}
