package hr.vvg.java.vjezbe.entities;

/**
 * Created by marko on 3/12/15.
 * Book publisher class
 */
public class Publisher {
    private String name;
    private String country;

    public Publisher(String name, String country) {
        this.name = name;
        this.country = country;
    }

    public String getName() {
        return name;
    }

    public String getCountry() {
        return country;
    }

}
