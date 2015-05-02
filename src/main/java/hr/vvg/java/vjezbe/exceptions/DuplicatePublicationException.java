package hr.vvg.java.vjezbe.exceptions;

/**
 * to be thrown if new Publication is equal to one already added on the Publication list
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
