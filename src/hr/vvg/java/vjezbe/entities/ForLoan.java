package hr.vvg.java.vjezbe.entities;

/**
 * Created by marko on 3/19/15.
 */
public interface ForLoan {
    void borrow();
    void giveBack();
    boolean checkAvailability();
}
