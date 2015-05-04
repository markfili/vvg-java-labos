package hr.vvg.java.vjezbe.interfaces;

/**
 * interface containing loan operations
 * Created by marko on 3/19/15.
 */
public interface ForLoan {
    void borrow();

    void giveBack();

    boolean isAvailable();
}
