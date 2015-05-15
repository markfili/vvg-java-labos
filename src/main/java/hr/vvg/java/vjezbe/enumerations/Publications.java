package hr.vvg.java.vjezbe.enumerations;

/**
 * Enumerations with types of publication
 * Created by marko on 5/3/15.
 */
public enum Publications {
    MAGAZINE(1, "Casopis"),
    BOOK(2, "Knjiga");

    private int id;
    private String friendlyName;

    Publications(int id, String friendlyName) {
        this.id = id;
        this.friendlyName = friendlyName;
    }

    public int getId() {
        return id;
    }

    public String getFriendlyName() {
        return friendlyName;
    }

    @Override
    public String toString() {
        return String.format("%d. %s", getId(), getFriendlyName());
    }
}
