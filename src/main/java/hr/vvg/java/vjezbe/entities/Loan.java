package hr.vvg.java.vjezbe.entities;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Created by marko on 3/12/15.
 * Loan class
 */
public class Loan<T extends Publication> {
    private LocalDateTime localDateTime;

    private T pub;
    private Member member;

    public Loan(Member member, T pub, LocalDateTime localDateTime) {
        this.member = member;
        this.pub = pub;
        this.localDateTime = localDateTime;
    }

    public Member getMember() {
        return member;
    }

    public T getPub() {
        return pub;
    }

    public LocalDateTime getLocalDateTime() {
        return localDateTime;
    }

    public String loanDateToString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");
        return this.localDateTime.format(formatter);
    }


    // TODO dohvaćanje getData metode podklase? sve tri metode ispod...
    public void printData() {
        System.out.println(getData());
    }

    private String getData() {
        return String.format("Stanje posudbe\n%s\n%s\nPosudjeno datuma: %s", getPub().getData(), member.getData(), this.loanDateToString());
    }

}
