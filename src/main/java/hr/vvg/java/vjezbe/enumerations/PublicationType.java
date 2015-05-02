package hr.vvg.java.vjezbe.enumerations;

/**
 * Enumeration containing available Publication types for new Publications
 * Created by marko on 4/11/15.
 */
public enum PublicationType {
    DIGITAL(1, "Elektronicka"), PAPER(2, "Papirnata");

    private int id;
    private String friendlyName;


    private PublicationType(int id, String friendlyName) {
        this.id = id;
        this.friendlyName = friendlyName;
    }

    public int getId() {
        return id;
    }

    public String getFriendlyName() {
        return friendlyName;
    }
}
