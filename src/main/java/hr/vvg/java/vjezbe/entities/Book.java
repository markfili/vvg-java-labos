package hr.vvg.java.vjezbe.entities;


import hr.vvg.java.vjezbe.exceptions.NonprofitablePublishingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;

/**
 * Created by marko on 3/12/15.
 * Book class
 */
public class Book extends Publication implements ForLoan {

    private static final double PRICE_PER_PAGE_CROATIAN = 0.5;
    private static final double PRICE_PER_PAGE_FOREIGN = 0.7;
    private static final String CROATIAN = "HRVATSKI";

    private String language;
    private boolean available;

    private Publisher publisher;

    public Book(String title, String language, Publisher publisher, int yearPublished, int numberOfPages, String typeOfPublication) throws NonprofitablePublishingException {
        super(title, yearPublished, numberOfPages, typeOfPublication, checkLanguageForPrice(language));
        this.language = language;
        this.publisher = publisher;
        this.available = true;
        try {
            if (BigDecimal.valueOf(100.00).compareTo(super.getPriceOfPublication()) != -1) {
                throw new NonprofitablePublishingException("Neisplativo izdavanje knjige " + getPublicationTitle());
            }
        } catch (NonprofitablePublishingException ex) {
            ex.printStackTrace();
        }

        Logger logger = LoggerFactory.getLogger(this.getClass());
        logger.info("Book created\n" + getData());
    }

    private static double checkLanguageForPrice(String language) {
        if (CROATIAN.equals(language.toUpperCase())) {
            return PRICE_PER_PAGE_CROATIAN;
        } else {
            return PRICE_PER_PAGE_FOREIGN;
        }
    }

    public String getLanguage() {
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
        return String.format("Naslov: %s\nGodina izdanja: %d\nBroj stranica: %d\nTip publikacije: %s\nCijena: %sHRK\nJezik knjige: %s\nIzdavač: %s\nDržava izdavača: %s", getPublicationTitle(), getYearPublished(), getNumberOfPages(), getTypeOfPublication(), getPriceOfPublication().toPlainString(), getLanguage(), getPublisher().getName(), getPublisher().getCountry());
    }

    @Override
    public void borrow() {
        this.available = false;
    }

    @Override
    public void giveBack() {
        this.available = true;
    }

    @Override
    public boolean checkAvailability() {
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
}
