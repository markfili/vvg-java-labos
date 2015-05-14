package hr.vvg.java.vjezbe.utilities;

import hr.vvg.java.vjezbe.entities.*;
import hr.vvg.java.vjezbe.enumerations.Language;
import hr.vvg.java.vjezbe.enumerations.PublicationType;
import hr.vvg.java.vjezbe.exceptions.DuplicatePublicationException;
import hr.vvg.java.vjezbe.interfaces.ForLoan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * Contains operations which simulate a Library
 * Handles adding new Publications, Book and Magazine, new Member input, sorting Publication list,
 * printing out available Languages and PublicationTypes and selection of Publication to execute Loan
 * Created by marko on 3/28/15.
 */
public class Operations {

    private static Logger logger = LoggerFactory.getLogger(Operations.class);

    /**
     * prints a form for new Magazine entry
     *
     * @param scanner Scanner object
     */
    public static Magazine magazineDataInput(Scanner scanner) {
        System.out.print(Literals.PUB_TITLE);
        String title = scanner.nextLine();
        System.out.print(Literals.PUB_MONTH);
        int month = Integer.parseInt(scanner.nextLine());
        System.out.print(Literals.PUB_YEAR);
        int year = Integer.parseInt(scanner.nextLine());
        System.out.print(Literals.PUB_PAGES);
        int pagesNum = Integer.parseInt(scanner.nextLine());
        System.out.println(Literals.PUB_TYPE);
        PublicationType type = listTypesOfPublishing(scanner);
        return new Magazine(title, month, year, pagesNum, type);
    }

    /**
     * prints a form for new Member data entry
     *
     * @param scanner Scanner object
     * @return returns initialized Member
     */
    public static Optional<Member> memberDataInput(Scanner scanner) {
        System.out.println("Unesite podatke");
        System.out.print("Ime: ");
        String fname = scanner.nextLine();
        System.out.print("Prezime: ");
        String lname = scanner.nextLine();
        System.out.print("OIB: ");
        String oib = scanner.nextLine();

        Member member = new Member(fname, lname, oib);
        return Optional.of(member);
    }

    /**
     * prints a form for new Book data entry
     *
     * @param scanner Scanner object
     * @return Book to add to list
     */
    public static Book bookDataInput(Scanner scanner) {
        System.out.print(Literals.PUB_TITLE);
        String title = scanner.nextLine();
        Language language = chooseLanguage(scanner);
        System.out.print("Izdavac: ");
        String publisherName = scanner.nextLine();
        System.out.print("Drzava: ");
        String publisherCountry = scanner.nextLine();
        System.out.print(Literals.PUB_YEAR);
        int year = Integer.parseInt(scanner.nextLine());
        System.out.print(Literals.PUB_PAGES);
        int pagesNum = Integer.parseInt(scanner.nextLine());
        System.out.println(Literals.PUB_TYPE);
        PublicationType type = listTypesOfPublishing(scanner);

        Publisher publisher = new Publisher(publisherName, publisherCountry);
        return new Book(title, language, publisher, year, pagesNum, type);
    }


    /**
     * Sets the scene for selecting the publication of choice by printing every publication and checking if any publication
     * with the title containing the entered string exists and if it is loanable
     *
     * @param scanner
     * @param libraryList
     * @return Found, chosen publication to loan
     */
    public static <T extends Publication> Publication selectPubToLoan(Scanner scanner, List<T> libraryList) {
        Publication publicationToLoan = null;

        System.out.println(Literals.PUB_TO_LOAN);

        Predicate<T> filterAvailable = t -> t instanceof ForLoan && ((ForLoan) t).isAvailable();

        printPublicationList(filterPublications(libraryList, filterAvailable));
        List<Publication> filtered;

        do {
            System.out.println("Unesite naslov publikacije za posudbu: ");
            // string
            String selection = scanner.nextLine();
            // filtered = filterPublications(libraryList, pub -> pub.getPublicationTitle().contains(selection));
            filtered = libraryList.stream().filter(pub -> pub.getPublicationTitle().contains(selection)).collect(Collectors.toList());
            if (filtered.isEmpty()) {
                System.out.println("Nije pronadjena nijedna publikacija, pokusajte ponovno.");
            } else {
                System.out.println("Pronađene publikacije: ");
                printPublicationList(filtered);
                publicationToLoan = chooseFromFiltered(scanner, filtered).get();
            }
        } while (filtered.isEmpty() || publicationToLoan == null);

        return publicationToLoan;
    }

    /**
     * Handles choosing wanted publication title from the filtered list
     *
     * @param scanner
     * @param libraryList List to iterate through and print data
     * @return Chosen publication to loan
     */
    private static <T extends Publication> Optional<T> chooseFromFiltered(Scanner scanner, List<T> libraryList) {
        System.out.println("Unesite redni broj naslova (1, 2,...)");
        int title = HelpingHand.checkIntInRange(scanner, 1, libraryList.size()) - 1;
        if (libraryList.get(title) instanceof Book) {
            ((Book) libraryList.get(title)).borrow();
        }
        return Optional.of(libraryList.get(title));
    }

    private static <T extends Publication> List<T> filterPublications(List<T> publicationList, Predicate<T> filterBy) {
        return publicationList.stream().filter(filterBy).collect(Collectors.toList());
    }

    /**
     * Prints out Publications contained in list
     *
     * @param libraryList List to iterate through and print data
     */
    private static <T extends Publication> void printPublicationList(List<T> libraryList) {
        libraryList.stream().forEach(Publication::printData);
    }


    /**
     * Prints out available commands to control the menu when listing
     *
     * @param scanner
     * @param show    if true shows the menu options and waits for user interaction
     * @return false if menu no longer required
     */
    private static boolean printMenuCommands(Scanner scanner, boolean show) {
        boolean next;
        if (show) {
            do {
                System.out.println(Literals.MENU_COMMANDS);
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
        return show;
    }

    /**
     * sorts Publications by price, prints result list values and writes the most expensive Publication to log
     *
     * @param publicationList
     */
    public static <T extends Publication> void sortByPrice(List<T> publicationList) {
//        publicationList.sort((pub1, pub2) -> (pub1.getPriceOfPublication().compareTo(pub2.getPriceOfPublication())));
        Function<Publication, BigDecimal> byPrice = Publication::getPriceOfPublication;
        publicationList.stream().sorted(Comparator.comparing(byPrice));
        logger.info("Najskuplja publikacija\n" + publicationList.stream().findFirst().get().getData());
        logger.info("Najskuplja publikacije\n" + Optional.of(publicationList.get(publicationList.size() - 1)));
    }


    /**
     * Lists available enum types of publishing
     *
     * @param scanner
     * @return chosen type of publishing
     */
    private static PublicationType listTypesOfPublishing(Scanner scanner) {
        int numberOfTypes = PublicationType.values().length;
        PublicationType[] typesOfPublishing = PublicationType.values();
        for (int i = 0; i < numberOfTypes; i++) {
//            System.out.println(String.format("%d. %s", (i + 1), HelpingHand.upperFirst(typesOfPublishing[i])));
            System.out.println(String.format("%d. %s", typesOfPublishing[i].getId(), typesOfPublishing[i].getFriendlyName()));
        }
        System.out.print("Vrsta broj: ");
        int type = HelpingHand.checkIntInRange(scanner, 1, PublicationType.values().length) - 1;
        return typesOfPublishing[type];
    }


    /**
     * creates a loan after the member has chosen the publication
     *
     * @param member    member loaning the publication data
     * @param pubToLoan publication being loaned
     */
    public static <T extends Publication> void executeLoan(Member member, T pubToLoan) {
        Loan<T> loan = new Loan<T>(member, pubToLoan, LocalDateTime.now());
        loan.printData();
    }

    /**
     * checks if there's a duplicate publication on the list after a new publication has been entered
     * add publication to the list if there's no duplicate
     *
     * @param pub     publication to add to publicationList
     * @param library a library object containing a list of by now added publications
     * @return true if added false if duplicate found
     */
    public static <T extends Publication> boolean addPublication(T pub, Library<T> library) {
        try {
            checkForDuplicate(pub, library.getLibraryList());
            library.acquire(pub);
            return true;
        } catch (DuplicatePublicationException ex) {
            logger.error("Duplikat publikacije!" + pub.getData(), ex);
            System.out.println("Duplikat publikacije, pokusajte ponovno");
        }

        return false;
    }

    /**
     * Checks publicationList for duplicate Publications
     *
     * @param obj
     * @param publicationList
     * @throws DuplicatePublicationException if duplicate publication found
     */
    public static <T extends Publication> void checkForDuplicate(T obj, List<T> publicationList) throws DuplicatePublicationException {
        for (T pub : publicationList) {
            if (pub.equals(obj)) {
                logger.warn("Pronadjen duplikat!" + obj.getPublicationTitle());
                throw new DuplicatePublicationException("Vec postoji publikacija s tim podacima! " + obj.getPublicationTitle());
            }
        }
    }

    /**
     * lists possible languages to choose when creating new Publications
     *
     * @param scanner
     * @return returns the chosen Language
     */
    public static Language chooseLanguage(Scanner scanner) {
        System.out.println(Literals.LANGUAGE);

        // TODO Iterator preskace neparni ordinal
        // Iterator<Language> languageIterator = Language.languages().iterator();
        //  while (languageIterator.hasNext()) {
        for (Language lang : Language.values()) {
            System.out.println(String.format("%d. %s", lang.getId(), lang.getFriendlyName()));
        }
        int selection = HelpingHand.checkIntInRange(scanner, 1, Language.values().length) - 1;
        return Language.values()[selection];
    }
}
