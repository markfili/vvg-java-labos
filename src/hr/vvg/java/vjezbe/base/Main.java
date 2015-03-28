package hr.vvg.java.vjezbe.base;

import hr.vvg.java.vjezbe.entities.*;
import hr.vvg.java.vjezbe.utilities.HelpingHand;
import hr.vvg.java.vjezbe.utilities.Operations;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Created by marko on 3/12/15.
 * Main class
 */
public class Main {

    private static final int NUMBER_OF_BOOKS = 2;
    private static final int NUMBER_OF_MAGAZINES = 2;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Publication[] publicationArray = new Publication[4];
        List<Publication> publicationList = new ArrayList<>();
        String[] typesOfPublishing = Publication.getTypesOfPublishingArray();

        // TODO zakomentirati init testnih podataka
        Member member = initTestData(publicationList);


        // TODO otkomentirati kako bi se omogucio unos knjiga, casopisa i podataka o clanu
/*
        System.out.println("\t\t\tDobar dan!");
        System.out.println("****************************************\n\tUnesite podatke za dvije knjige!\n****************************************");

        for (int i = 1; i <= NUMBER_OF_BOOKS; i++) {
            System.out.println(String.format("Unesite podatke za %d. knjigu", i));
            Operations.bookDataInput(scanner, publicationList, typesOfPublishing);
        }

        for (int i = 1; i <= NUMBER_OF_MAGAZINES; i++) {
            System.out.println(String.format("Unesite podatke za %d. casopis", i));
            Operations.magazineDataInput(scanner, publicationList, typesOfPublishing);
        }

        System.out.println("\nPodaci clana");
        Member member = Operations.memberDataInput(scanner);
*/

        Publication.sortByPrice(publicationList);

        Publication chosenPublication = Operations.selectPubToLoan(scanner, publicationList);
        if (chosenPublication != null) {
            Operations.executeLoan(member, chosenPublication);
        } else {
            System.out.println("Ne moze, knjiznica ipak ne da knjigu.");
        }
    }


    private static Member initTestData(List<Publication> publicationList) {
        Book bookOne = new Book("Ko guske kroz maglu", "Hrvatski", new Publisher("Janko", "Hrvatska"), 1988, 1600, Book.TYPE_PAPER);
        Book bookTwo = new Book("Through thick fog till death", "Engleski", new Publisher("Bobetko", "England"), 1989, 302, Book.TYPE_ELECTRONIC);

        Magazine magazineOne = new Magazine("Top Fear", 11, 2011, 34, Magazine.TYPE_PAPER);
        Magazine magazineTwo = new Magazine("Madames in skirtes", 9, 1811, 24, Magazine.TYPE_ELECTRONIC);

        publicationList.add(bookOne);
        publicationList.add(magazineOne);
        publicationList.add(bookTwo);
        publicationList.add(magazineTwo);

        return new Member("Ivan", "Ivić", "2032323224");
    }
}
