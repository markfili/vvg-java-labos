package hr.vvg.java.vjezbe.base;

import hr.vvg.java.vjezbe.entities.*;
import hr.vvg.java.vjezbe.enumerations.Language;
import hr.vvg.java.vjezbe.enumerations.PublicationType;
import hr.vvg.java.vjezbe.enumerations.Publications;
import hr.vvg.java.vjezbe.exceptions.NonaffordablePublishingException;
import hr.vvg.java.vjezbe.utilities.HelpingHand;
import hr.vvg.java.vjezbe.utilities.Literals;
import hr.vvg.java.vjezbe.utilities.Operations;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

/**
 * Created by marko on 3/12/15.
 * Main class
 */
public class Main {

    private static final int NUMBER_OF_BOOKS = 1;
    private static final int NUMBER_OF_MAGAZINES = 2;
    static Logger logger;

    public static void main(String[] args) {
        logger = LoggerFactory.getLogger(Main.class);
        Scanner scanner = new Scanner(System.in);

        Library<Publication> library = new Library<>();

        // init testnih podataka
        // Member member = initTestData(scanner, library);

        System.out.println("\t\t\tDobar dan!");

        // repeat main function until it gets no longer interesting
        String decision;
        do {
            boolean added = false;
            System.out.println("Izaberite publikaciju za unos: ");
            Arrays.asList(Publications.values()).stream().sorted().forEach(p -> System.out.println(p.toString()));

            Publications entering = Publications.values()[HelpingHand.checkIntInRange(scanner, 1, Publications.values().length) - 1];

            System.out.println(String.format("Unesite podatke za " + entering.getFriendlyName().toLowerCase()));
            try {
                switch (entering) {
                    case MAGAZINE:
                        added = Operations.addPublication(Operations.magazineDataInput(scanner), library);
                        break;

                    case BOOK:
                        added = Operations.addPublication(Operations.bookDataInput(scanner), library);
                        break;
                }
                if (!(added)) {
                    System.out.println("Greska, pokusajte ponovno.");
                }
            } catch (NonaffordablePublishingException nex) {
                System.out.println(nex.getMessage());
            }
            // TODO handle if decision not yes or no
            System.out.println("Dodati jos publikacija? da/ne");
            decision = scanner.nextLine().toLowerCase();
            if (Literals.CANCEL.equals(decision)) {

                System.out.println("\nPodaci clana");
                Member backupMember = new Member("Leonard", "Davinčić", "212333314");
                Member member = Operations.memberDataInput(scanner).orElse(backupMember);

                Operations.sortByPrice(library.getLibraryList());
                Operations.executeLoan(member, Operations.selectPubToLoan(scanner, library.getLibraryList()));

                System.out.printf("Zelite li unijeti nove publikacije i posuditi nesto drugo? (Da za ponavljanje, bilosto za kraj)");
                decision = scanner.nextLine().toLowerCase();
            }

        } while (Literals.CONFIRM.equals(decision));

    }


    private static Member initTestData(Scanner scanner, Library<Publication> library) {

        boolean exception = false;
        logger.warn("kreiranje testnih objekata!");
        Book bookOne = null;
        Book bookTwo = null;
        Magazine magazineOne = null;
        Magazine magazineTwo = null;
        Magazine magazineThree = null;

        try {
            magazineOne = new Magazine("Top Fear", 11, 2011, 345, PublicationType.DIGITAL);
            Operations.addPublication(magazineOne, library);
            magazineTwo = new Magazine("Madames in skirtes", 9, 1811, 2445, PublicationType.PAPER);
            Operations.addPublication(magazineTwo, library);
//            magazineThree = new Magazine("Madames in skirtes", 9, 1811, 24, Magazine.TYPE_ELECTRONIC);
//            Operations.addPublication(magazineThree, publicationList);
            bookOne = new Book("Ko guske kroz maglu", Language.ENGLESKI, new Publisher("Janko", "Hrvatska"), 1988, 16005, PublicationType.DIGITAL);
            Operations.addPublication(bookOne, library);
            bookTwo = new Book("Through thick fog till death", Operations.chooseLanguage(scanner), new Publisher("Bobetko", "England"), 1989, 3023, PublicationType.PAPER);
            Operations.addPublication(bookTwo, library);

        } catch (NonaffordablePublishingException ex) {
            ex.printStackTrace();
            exception = true;
        }

        return new Member("Ivan", "Ivić", "2032323224");
    }


}
