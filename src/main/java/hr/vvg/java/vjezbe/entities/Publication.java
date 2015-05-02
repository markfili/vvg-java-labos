package hr.vvg.java.vjezbe.entities;

import hr.vvg.java.vjezbe.enumerations.PublicationType;
import hr.vvg.java.vjezbe.interfaces.ForSale;

import java.math.BigDecimal;

/**
 * Created by marko on 3/18/15.
 */
public abstract class Publication implements ForSale {

    private String publicationTitle;
    private int yearPublished;
    private int numberOfPages;
    private PublicationType typeOfPublication;

    private BigDecimal priceOfPublication;


    public Publication(String publicationTitle, int yearPublished, int numberOfPages, PublicationType typeOfPublication, double priceOfPublishing) {
        this.publicationTitle = publicationTitle;
        this.yearPublished = yearPublished;
        this.numberOfPages = numberOfPages;
        this.typeOfPublication = typeOfPublication;
        this.priceOfPublication = calculatePrice(numberOfPages, typeOfPublication, priceOfPublishing);
    }

    public String getPublicationTitle() {
        return publicationTitle;
    }

    public int getYearPublished() {
        return yearPublished;
    }

    public int getNumberOfPages() {
        return numberOfPages;
    }

    public PublicationType getTypeOfPublication() {
        return typeOfPublication;
    }

    public abstract void printData();

    public abstract String getData();

    public abstract void checkAffordability(BigDecimal price);

    public BigDecimal getPriceOfPublication() {
        return priceOfPublication;
    }
}
