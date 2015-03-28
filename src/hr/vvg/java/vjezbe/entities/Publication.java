package hr.vvg.java.vjezbe.entities;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by marko on 3/18/15.
 */
public abstract class Publication implements ForSale {

    public static final String TYPE_ELECTRONIC = "ELECTRONIC";
    public static final String TYPE_PAPER = "PAPER";

    private String publicationTitle;
    private int yearPublished;
    private int numberOfPages;
    private String typeOfPublication;

    private BigDecimal priceOfPublication;

    /*
    enum PublicationType {
        VRSTA_PUBLIKACIJE_ELEKTRONICKA, VRSTA_PUBLIKACIJE_PAPIRNATA
    }
    */

    public Publication(String publicationTitle, int yearPublished, int numberOfPages, String typeOfPublication, double priceOfPublishing) {
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

    public String getTypeOfPublication() {
        return typeOfPublication;
    }

    public static String[] getTypesOfPublishingArray() {
        return new String[] {
                TYPE_ELECTRONIC, TYPE_PAPER
        };
    }

    public void printData() {
        System.out.println(this.getData());
    }

    public BigDecimal getPriceOfPublication() {
        return priceOfPublication;
    }

    public String getData() {
        return String.format("Naslov: %s\nGodina izdanja: %d\nBroj stranica: %d\nTip publikacije: %s\nCijena: %sHRK", getPublicationTitle(), getYearPublished(), getNumberOfPages(), getTypeOfPublication(), getPriceOfPublication().toPlainString());
    }

    public static void sortByPrice(List<Publication> publicationList) {
        publicationList.sort((pub1, pub2) -> (pub1.getPriceOfPublication().compareTo(pub2.getPriceOfPublication())));
    }
}
