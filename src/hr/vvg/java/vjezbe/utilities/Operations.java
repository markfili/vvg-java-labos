package hr.vvg.java.vjezbe.utilities;

import hr.vvg.java.vjezbe.entities.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Scanner;

/**
 * Created by marko on 3/28/15.
 */
public class Operations {

    public static void magazineDataInput(Scanner scanner, List<Publication> publicationList, String[] typesOfPublishing) {
        System.out.print("Naslov: ");
        String title = scanner.nextLine();
        System.out.print("Mjesec izdanja: ");
        int month = scanner.nextInt();
        System.out.print("Godina izdanja: ");
        int year = scanner.nextInt();
        System.out.print("Broj stranica: ");
        int pagesNum = scanner.nextInt();
        System.out.println("Vrsta publikacije: ");
        String type = listTypesOfPublishing(scanner, typesOfPublishing);

        publicationList.add(new Magazine(title, (byte) month, year, pagesNum, type));

    }

    public static Member memberDataInput(Scanner scanner) {
        System.out.println("Unesite podatke");
        System.out.print("Ime: ");
        String fname = scanner.nextLine();
        System.out.print("Prezime: ");
        String lname = scanner.nextLine();
        System.out.print("OIB: ");
        String oib = scanner.nextLine();

        return new Member(fname, lname, oib);
    }

    public static void bookDataInput(Scanner scanner, List<Publication> publicationList, String[] typesOfPublishing) {
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
        String type = listTypesOfPublishing(scanner, typesOfPublishing);

        Publisher publisher = new Publisher(publisherName, publisherCountry);
        Book book = new Book(title, language, publisher, year, pagesNum, type);

        publicationList.add(book);

    }




    public static Publication selectPubToLoan(Scanner scanner, List<Publication> publicationList) {
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

                if (publicationToLoan instanceof Book) {
                    if (((Book) publicationToLoan).checkAvailability()) {
                        matchFound = true;
                        ((Book) publicationToLoan).borrow();
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



    private static String listTypesOfPublishing(Scanner scanner, String[] typesOfPublishing) {
        int numberOfTypes = typesOfPublishing.length;
        for (int i = 0; i < numberOfTypes; i++) {
            System.out.println(String.format("%d. %s", (i + 1), HelpingHand.upperFirst(typesOfPublishing[i])));
        }

        int type;
        do {
            System.out.print("Vrsta broj: ");
            type = scanner.nextInt();
        } while (type > numberOfTypes || type <= 0);

        return typesOfPublishing[type - 1];
    }


    public static void executeLoan(Member member, Publication pubToLoan) {
        Loan loan = new Loan(member, pubToLoan, LocalDateTime.now());
        loan.printData();
    }
}
