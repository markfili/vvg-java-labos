package hr.vvg.java.vjezbe.exceptions;

/**
 * RuntimeException to be thrown if a value is outside of specified range.
 * Created by marko on 5/3/15.
 */
public class ResultOutOfRange extends RuntimeException {
    public ResultOutOfRange(String s) {
    }

    public ResultOutOfRange() {
        super();
    }

    public ResultOutOfRange(String message, Throwable cause) {
        super(message, cause);
    }

    public ResultOutOfRange(Throwable cause) {
        super(cause);
    }

}
