package hr.vvg.java.vjezbe.entities;


import hr.vvg.java.vjezbe.enumerations.Language;
import hr.vvg.java.vjezbe.enumerations.PublicationType;
import hr.vvg.java.vjezbe.exceptions.NonaffordablePublishingException;
import hr.vvg.java.vjezbe.interfaces.ForLoan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;

import static hr.vvg.java.vjezbe.enumerations.Language.HRVATSKI;

/**
 * Book entity for creating new Books
 * Created by marko on 3/12/15.
 */
public class Book extends Publication implements ForLoan {

    private static final double PRICE_PER_PAGE_CROATIAN = 1.5;
    private static final double PRICE_PER_PAGE_FOREIGN = 1.7;
    private static final int BOTTOM_PRICE = 100;
    private Logger logger = LoggerFactory.getLogger(Book.class);
    private Language language;
    private boolean available;

    private Publisher publisher;

    public Book(String title, Language language, Publisher publisher, int yearPublished, int numberOfPages, PublicationType typeOfPublication) throws NonaffordablePublishingException {
        super(title, yearPublished, numberOfPages, typeOfPublication, checkPriceForLanguage(language));
        this.language = language;
        this.publisher = publisher;
        this.available = true;
        checkAffordability(super.getPriceOfPublication());
        logger.info("Book created\n" + getData());
    }

    private static double checkPriceForLanguage(Language language) {
        return language == HRVATSKI ? PRICE_PER_PAGE_CROATIAN : PRICE_PER_PAGE_FOREIGN;
    }

    public Language getLanguage() {
        return this.language;
    }

    public Publisher getPublisher() {
        return publisher;
    }

    @Override
    public void printData() {
        System.out.println(getData());
    }

    @Override
    public String getData() {
        return String.format("Naslov: %s\nGodina izdanja: %d\nBroj stranica: %d\nTip publikacije: %s\nCijena: %sHRK\nJezik knjige: %s\nIzdavač: %s\nDržava izdavača: %s\nDostupno za posudbu: %s", getPublicationTitle(), getYearPublished(), getNumberOfPages(), getTypeOfPublication().getFriendlyName(), getPriceOfPublication().toPlainString(), getLanguage().getFriendlyName(), getPublisher().getName(), getPublisher().getCountry(), isAvailable() ? "Da" : "Ne");
    }

    @Override
    public void borrow() {
        this.available = false;
        logger.info("knjiga %s posudjena", super.getPublicationTitle());
    }

    @Override
    public void giveBack() {
        this.available = true;
        logger.info("knjiga %s vracena", super.getPublicationTitle());
    }

    @Override
    public boolean isAvailable() {
        return this.available;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Book) {
            if (((Book) obj).getData().equals(getData())) {
                return true;
            }
        }
        return false;
    }

    /**
     * Overridden method for book publishing affordability
     *
     * @param price calculated price of publishing
     * @throws hr.vvg.java.vjezbe.exceptions.NonaffordablePublishingException thrown when calculated price is lower than BOTTOM_PRICE
     */
    @Override
    public void checkAffordability(BigDecimal price) throws NonaffordablePublishingException {
        if (BigDecimal.valueOf(BOTTOM_PRICE).compareTo(price) != -1) {
            logger.warn(String.format("knjiga %s neisplativa", super.getPublicationTitle()));
            throw new NonaffordablePublishingException("Neisplativo izdavanje knjige " + super.getPublicationTitle() + " za cijenu " + price);
        }
    }
}
