package hr.vvg.java.vjezbe.enumerations;

import java.util.Arrays;
import java.util.List;

/**
 * Enumeration with available languages to choose for new Publications
 * Created by marko on 4/11/15.
 */
public enum Language {

    HRVATSKI(1, "Hrvatski"),
    ENGLESKI(2, "Engleski"),
    NJEMACKI(3, "Njemacki"),
    FRANCUSKI(4, "Francuski"),
    TALIJANSKI(5, "Talijanski"),
    RUSKI(6, "Ruski"),
    KINESKI(7, "Kineski");

    private int id;
    private String friendlyName;

    private Language(int id, String friendlyName) {
        this.id = id;
        this.friendlyName = friendlyName;
    }

    public static List<Language> languages() {
        return Arrays.asList(values());
    }

    public int getId() {
        return id;
    }

    public String getFriendlyName() {
        return this.friendlyName;
    }
}
