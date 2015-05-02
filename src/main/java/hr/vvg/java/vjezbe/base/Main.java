package hr.vvg.java.vjezbe.base;

import hr.vvg.java.vjezbe.entities.*;
import hr.vvg.java.vjezbe.enumerations.Language;
import hr.vvg.java.vjezbe.enumerations.PublicationType;
import hr.vvg.java.vjezbe.exceptions.NonaffordablePublishingException;
import hr.vvg.java.vjezbe.utilities.Operations;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
    static Logger logger;

    public static void main(String[] args) {
        logger = LoggerFactory.getLogger(Main.class);
        Scanner scanner = new Scanner(System.in);

        List<Publication> publicationList = new ArrayList<>();

        // TODO zakomentirati init testnih podataka
//        Member member = initTestData(scanner, publicationList);

        // TODO otkomentirati kako bi se omogucio unos knjiga, casopisa i podataka o clanu
        System.out.println("\t\t\tDobar dan!");

        // repeat main function until it gets no longer interesting
        do {
            System.out.printf("****************************************\n\tUnesite podatke za %d knjige i %d casopisa!\n****************************************\n", NUMBER_OF_BOOKS, NUMBER_OF_MAGAZINES);

            int i = 1;
            boolean added;
            do {
                added = false;
                System.out.println(String.format("Unesite podatke za %d. knjigu", i));
                try {
                    if (!(added = Operations.addPublication(Operations.bookDataInput(scanner), publicationList))) {
                        System.out.println("Greška, ponovno unesite: ");
                    } else {
                        i++;
                    }
                } catch (NonaffordablePublishingException nex) {
                    System.out.println(nex.getMessage());
                }
            } while (i <= NUMBER_OF_BOOKS || !added);

            i = 1;
            do {
                System.out.println(String.format("Unesite podatke za %d. casopis", i));
                try {
                    if (!(added = Operations.addPublication(Operations.magazineDataInput(scanner), publicationList))) {
                        System.out.println("Greska, ponovno uesite: ");
                    } else {
                        i++;
                    }
                } catch (NonaffordablePublishingException nex) {
                    System.out.println(nex.getMessage());
                }
            } while (i <= NUMBER_OF_MAGAZINES || !added);

            System.out.println("\nPodaci clana");
            Member backupMember = new Member("Leonard", "Davinčić", "212333314");
            Member member = Operations.memberDataInput(scanner).orElse(backupMember);

            Operations.sortByPrice(publicationList);
            Operations.executeLoan(member, Operations.selectPubToLoan(scanner, publicationList));

            System.out.printf("Zelite li ponoviti unos knjiga i odabir posudbe? (Da za ponavljanje, bilosto za kraj)");
        } while ("da".equals(scanner.nextLine().toLowerCase()));

    }


    private static Member initTestData(Scanner scanner, List<Publication> publicationList) {

        boolean exception = false;
        logger.warn("kreiranje testnih objekata!");
        Book bookOne = null;
        Book bookTwo = null;
        Magazine magazineOne = null;
        Magazine magazineTwo = null;
        Magazine magazineThree = null;

        try {
            magazineOne = new Magazine("Top Fear", 11, 2011, 345, PublicationType.DIGITAL);
            Operations.addPublication(magazineOne, publicationList);
            magazineTwo = new Magazine("Madames in skirtes", 9, 1811, 2445, PublicationType.PAPER);
            Operations.addPublication(magazineTwo, publicationList);
//            magazineThree = new Magazine("Madames in skirtes", 9, 1811, 24, Magazine.TYPE_ELECTRONIC);
//            Operations.addPublication(magazineThree, publicationList);
            bookOne = new Book("Ko guske kroz maglu", Language.ENGLESKI, new Publisher("Janko", "Hrvatska"), 1988, 16005, PublicationType.DIGITAL);
            Operations.addPublication(bookOne, publicationList);
            bookTwo = new Book("Through thick fog till death", Operations.chooseLanguage(scanner), new Publisher("Bobetko", "England"), 1989, 3023, PublicationType.PAPER);
            Operations.addPublication(bookTwo, publicationList);

        } catch (NonaffordablePublishingException ex) {
            ex.printStackTrace();
            exception = true;
        }

        return new Member("Ivan", "Ivić", "2032323224");
    }


}
