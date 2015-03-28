package hr.vvg.java.vjezbe.base;

import hr.vvg.java.vjezbe.entities.*;

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
    private static List<Publication> publicationList;
    private static String[] typesOfPublishing;


    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Publication[] publicationArray = new Publication[4];
        publicationList = new ArrayList<>();
        typesOfPublishing = Publication.getTypesOfPublishingArray();

        // TODO zakomentirati init testnih podataka
        Member member = initTestData();


        // TODO otkomentirati kako bi se omogucio unos knjiga, casopisa i podataka o clanu
        System.out.println("\t\t\tDobar dan!");
        /*
        System.out.println("****************************************\n\tUnesite podatke za dvije knjige!\n****************************************");

        for (int i = 1; i <= NUMBER_OF_BOOKS; i++) {
            System.out.println(String.format("Unesite podatke za %d. knjigu", i));
            bookDataInput(scanner);
        }

        for (int i = 1; i <= NUMBER_OF_MAGAZINES; i++) {
            System.out.println(String.format("Unesite podatke za %d. casopis", i));
            magazineDataInput(scanner);
        }

        System.out.println("\nPodaci clana");
        Member member = memberDataInput(scanner);
        */

        Publication.sortByPrice(publicationList);

        Publication chosenPublication = selectPubToLoan(scanner);
        if (chosenPublication != null) {
            executeLoan(member, chosenPublication);
        } else {
            System.out.println("Ne moze, knjiznica ipak ne da knjigu.");
        }
    }

    private static void magazineDataInput(Scanner scanner) {
        System.out.print("Naslov: ");
        String title = scanner.nextLine();
        System.out.print("Mjesec izdanja: ");
        int month = scanner.nextInt();
        System.out.print("Godina izdanja: ");
        int year = scanner.nextInt();
        System.out.print("Broj stranica: ");
        int pagesNum = scanner.nextInt();
        System.out.println("Vrsta publikacije: ");
        String type = listTypesOfPublishing(scanner);

        publicationList.add(new Magazine(title, (byte) month, year, pagesNum, type));

    }

    private static Member memberDataInput(Scanner scanner) {
        System.out.println("Unesite podatke");
        System.out.print("Ime: ");
        String fname = scanner.nextLine();
        System.out.print("Prezime: ");
        String lname = scanner.nextLine();
        System.out.print("OIB: ");
        String oib = scanner.nextLine();

        return new Member(fname, lname, oib);
    }

    private static void bookDataInput(Scanner scanner) {
        System.out.print("Naslov: ");
        String title = scanner.nextLine();
        System.out.print("Jezik: ");
        String language = scanner.nextLine();
        System.out.print("Izdavac: ");
        String publisherName = scanner.nextLine();
        System.out.print("Drzava: ");
        String publisherCountry = scanner.nextLine();
        System.out.print("Godina izdanja: ");
        int year = scanner.nextInt();
        System.out.print("Broj stranica: ");
        int pagesNum = scanner.nextInt();
        System.out.println("Vrsta publikacije: ");
        String type = listTypesOfPublishing(scanner);

        Publisher publisher = new Publisher(publisherName, publisherCountry);
        Book book = new Book(title, language, publisher, year, pagesNum, type);

        publicationList.add(book);

    }

    // TODO može li ovo malo jednostavnije/čišće?
    private static Publication selectPubToLoan(Scanner scanner) {
        boolean matchFound = false;

        System.out.println("Izaberite publikaciju po naslovu ili dijelu naslova");
        boolean show = true;
        boolean next;

        for (int i = 0; i < publicationList.size(); i++) {
            System.out.println(String.format("%d. izbor", i + 1));
            publicationList.get(i).printData();
            System.out.println();

            if (show && i < publicationList.size() - 1) {
                do {
                    System.out.println("Pritisnite Enter za nastavak ili unesite C za potpuni popis");
                    String listCtrl = scanner.nextLine();
                    if ("".equals(listCtrl)) {
                        next = false;
                    } else if ("c".equals(listCtrl.toLowerCase())) {
                        show = false;
                        next = false;
                    } else {
                        System.out.println("Netocan unos naredbe");
                        next = true;
                    }
                } while (next);
            }

        }

        System.out.println("Unesite redni broj publikacije: ");
        Publication publicationToLoan = null;
        do {

            int title = scanner.nextInt();

            if (title > 0 && title <= publicationList.size()) {

                publicationToLoan = publicationList.get(title - 1);
                // malo otisao predaleko...
                if (publicationToLoan instanceof Book) {
                    if (((Book) publicationToLoan).checkAvailability()) {
                        matchFound = true;
                        ((Book) publicationToLoan).setAvailable(false);
                    } else {
                        System.out.println("Knjiga trenutno nije dostupna. Izaberite nešto drugo.");
                    }
                } else {
                    matchFound = true;
                }

            } else {
                System.out.println("Pokusajte ponovo, unesite redni broj naslova.");
            }
        } while (!matchFound);

        return publicationToLoan;
    }

    private static void executeLoan(Member member, Publication pubToLoan) {
        Loan loan = new Loan(member, pubToLoan, LocalDateTime.now());
        loan.printData();
    }

    private static String listTypesOfPublishing(Scanner scanner) {
        int numberOfTypes = typesOfPublishing.length;
        for (int i = 0; i < numberOfTypes; i++) {
            System.out.println(String.format("%d. %s", (i + 1), upperFirst(typesOfPublishing[i])));
        }

        int type;
        do {
            System.out.print("Vrsta broj: ");
            type = scanner.nextInt();
        } while (type > numberOfTypes || type <= 0);

        return typesOfPublishing[type - 1];
    }



    private static String upperFirst(String s) {
        return s.substring(0, 1).toUpperCase() + s.substring(1, s.length()).toLowerCase();
    }

    private static Member initTestData() {
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
