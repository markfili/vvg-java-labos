package hr.vvg.java.vjezbe.menu;

import hr.vvg.java.vjezbe.entities.Library;
import hr.vvg.java.vjezbe.entities.Publication;
import hr.vvg.java.vjezbe.enumerations.Publications;
import hr.vvg.java.vjezbe.exceptions.NonaffordablePublishingException;
import hr.vvg.java.vjezbe.utilities.HelpingHand;
import hr.vvg.java.vjezbe.utilities.Literals;
import hr.vvg.java.vjezbe.utilities.Operations;

import java.util.Arrays;
import java.util.Scanner;

/**
 * Container for displaying Menu and choosing steps through the Library
 * Created by marko on 5/15/15.
 */
public class Menu {

    public static void mainMenu(Scanner scanner, Library<Publication> library) {
        // repeat main function until it gets no longer interesting
        boolean notOk = true;
        do {

            System.out.println("Izaberite akciju");
            System.out.println("1. Unos novih publikacija");
            System.out.println("2. Posudba postojecih publikacija");
            System.out.print("Izbor: ");

            try {
                switch (Integer.valueOf(scanner.nextLine())) {
                    case 1:
                        newEntries(scanner, library);
                        notOk = false;
                        break;
                    case 2:
                        loaning(scanner, library);
                        notOk = false;
                        break;
                    default:
                        System.out.println("Nepravilan izbor, pokusajte ponovno.");
                }
            } catch (NumberFormatException ex) {
                System.out.println("Nije unesen broj, pokusajte ponovno.");
            }
        } while (notOk);

        String decision;
        do {
            // TODO handle if decision not yes or no
            System.out.println("Dodati jos publikacija? da/ne");
            decision = scanner.nextLine().toLowerCase();

            // if "yes" selected, enter new publication
            if (Literals.CONFIRM.equals(decision)) {
                Menu.newEntries(scanner, library);
            }

            // if "no" selected, execute loan
            if (Literals.CANCEL.equals(decision)) {
                Menu.loaning(scanner, library);
                System.out.printf("Zelite li unijeti nove publikacije i posuditi nesto drugo?");
                System.out.println("Da za ponavljanje, bilosto za kraj");
                decision = scanner.nextLine().toLowerCase();
            }
            // quit if "no" selected
        } while (Literals.CONFIRM.equals(decision));

        System.out.println("Hvala sto ste koristili eKnjiznicu.");
    }

    /**
     * Handles sorting by price and calling execution of loaning action
     *
     * @param scanner
     * @param library
     */
    public static void loaning(Scanner scanner, Library<Publication> library) {
        Operations.sortByPrice(library.getLibraryList());
        Operations.executeLoan(Operations.getMember(), Operations.selectPubToLoan(scanner, library.getLibraryList()));
    }

    /**
     * Handles menu options for adding new publications to library
     *
     * @param scanner
     * @param library
     */
    public static void newEntries(Scanner scanner, Library<Publication> library) {
        boolean added = false;
        do {
            System.out.println("Izaberite publikaciju za unos: ");
            Arrays.asList(Publications.values()).stream().sorted().forEach(p -> System.out.println(p.toString()));

            // get publication id from user entry
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
        } while (!added);

    }
}
