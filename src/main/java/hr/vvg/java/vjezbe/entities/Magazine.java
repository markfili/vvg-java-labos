package hr.vvg.java.vjezbe.entities;

import hr.vvg.java.vjezbe.exceptions.NonprofitablePublishingException;

import java.math.BigDecimal;

/**
 * Created by marko on 3/19/15.
 */
public class Magazine extends Publication {

    private static final float PRICE_PER_COPY = 10;

    private int monthPublished;

    public Magazine(String publicationTitle, int monthPublished, int yearPublished, int numberOfPages, String typeOfPublication) {
        super(publicationTitle, yearPublished, numberOfPages, typeOfPublication, checkPricePerPage(numberOfPages));
        this.monthPublished = monthPublished;

        try {
            if (BigDecimal.valueOf(0.1).compareTo(super.getPriceOfPublication()) != -1) {
                throw new NonprofitablePublishingException("Neisplativo izdavanje casopisa " + getPublicationTitle());
            }
        } catch (NonprofitablePublishingException ex) {
            ex.printStackTrace();
        }
    }

    private static double checkPricePerPage(int pagesNum) {
        return (float) pagesNum / PRICE_PER_COPY;
    }

    public int getMonthPublished() {
        return monthPublished;
    }

    @Override
    public void printData() {
        System.out.println(getData());
    }

    @Override
    public String getData() {
        return String.format("Naslov: %s\nGodina izdanja: %d\nBroj stranica: %d\nTip publikacije: %s\nCijena: %sHRK\nMjesec izdanja: %d", getPublicationTitle(), getYearPublished(), getNumberOfPages(), getTypeOfPublication(), getPriceOfPublication().toPlainString(), getMonthPublished());
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Magazine) {
            if (((Magazine) obj).getMonthPublished() == getMonthPublished() && ((Magazine) obj).getData().equals(getData())) {
                return true;
            }
        }
        return false;
    }
}
