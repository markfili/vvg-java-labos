package hr.vvg.java.vjezbe.base;

import hr.vvg.java.vjezbe.entities.*;
import hr.vvg.java.vjezbe.enumerations.Language;
import hr.vvg.java.vjezbe.enumerations.PublicationType;
import hr.vvg.java.vjezbe.exceptions.NonaffordablePublishingException;
import hr.vvg.java.vjezbe.menu.Menu;
import hr.vvg.java.vjezbe.utilities.Operations;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Scanner;

/**
 * Created by marko on 3/12/15.
 * Main class
 */
public class Main {

    private static Logger logger;

    public static void main(String[] args) {

        logger = LoggerFactory.getLogger(Main.class);
        Scanner scanner = new Scanner(System.in);

        Library<Publication> library = new Library<>();
        // fetch publications from files
        Operations.getBooks(library);
        Operations.getMagazines(library);

        // member greeting
        System.out.println("\t\tDobar dan!");
        Operations.getMember().printData();

        System.out.println("Danas na meniju nudimo sljedece dostupne publikacije:");
        Operations.printPublicationList(library.getLibraryList());
        Menu.mainMenu(scanner, library);
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
        }

        return new Member("Ivan", "Ivić", "2032323224");
    }
}
