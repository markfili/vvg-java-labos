package hr.vvg.java.vjezbe.entities;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Created by marko on 3/12/15.
 * Loan class
 */
public class Loan {
    private LocalDateTime localDateTime;

    private Publication pub;
    private Member member;

    public Loan(Member member, Publication pub, LocalDateTime localDateTime) {
        this.member = member;
        this.pub = pub;
        this.localDateTime = localDateTime;
    }

    public Member getMember() {
        return member;
    }

    public Publication getPub() {
        return pub;
    }

    public LocalDateTime getLocalDateTime() {
        return localDateTime;
    }

    public String loanDateToString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
        return this.localDateTime.format(formatter);
    }


    // TODO dohvaćanje getData metode podklase? sve tri metode ispod...
    public void printData() {
        System.out.println(getData());
    }

    private String getData() {
        return String.format("Stanje posudbe\n%s\n%s\nPosudjeno datuma: %s", returnSubclassData(), member.getData(), this.loanDateToString());
    }

    private String returnSubclassData() {
        if (pub instanceof Book) {
            return ((Book) pub).getData();
        } else if (pub instanceof Magazine) {
            return ((Magazine) pub).getData();
        }
        return "No info";
    }
}
