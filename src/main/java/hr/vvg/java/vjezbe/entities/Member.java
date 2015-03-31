package hr.vvg.java.vjezbe.entities;

/**
 * Created by marko on 3/12/15.
 * Library member class
 */
public class Member {
    private String fname;
    private String lname;
    private String oib;

    public Member(String fname, String lname, String oib) {
        this.fname = fname;
        this.lname = lname;
        this.oib = oib;
    }

    public String getFname() {
        return fname;
    }

    public String getLname() {
        return lname;
    }

    public String getOib() {
        return oib;
    }

    public void printData() {
        System.out.println(getData());
    }

    public String getData() {
        return String.format("Clan: %s %s, %s", getFname(), getLname(), getOib());
    }

}
