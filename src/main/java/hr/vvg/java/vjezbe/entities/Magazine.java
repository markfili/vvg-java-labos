package hr.vvg.java.vjezbe.entities;

import hr.vvg.java.vjezbe.enumerations.PublicationType;
import org.slf4j.Logger;
import hr.vvg.java.vjezbe.exceptions.NonaffordablePublishingException;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;

/**
 * Created by marko on 3/19/15.
 *
 */
public class Magazine extends Publication {

    private Logger logger = LoggerFactory.getLogger(Magazine.class);

    private static final float PRICE_PER_COPY = 10;

    private int monthPublished;

    public Magazine(String publicationTitle, int monthPublished, int yearPublished, int numberOfPages, PublicationType typeOfPublication) {
        super(publicationTitle, yearPublished, numberOfPages, typeOfPublication, checkPricePerPage(numberOfPages));
        this.monthPublished = monthPublished;
        try {
            checkAffordability(super.getPriceOfPublication());
        } catch (NonaffordablePublishingException nex) {
            System.out.println("Neisplativo objavljivanje casopisa!");
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
        return String.format("Naslov: %s\nGodina izdanja: %d\nBroj stranica: %d\nTip publikacije: %s\nCijena: %sHRK\nMjesec izdanja: %d", getPublicationTitle(), getYearPublished(), getNumberOfPages(), getTypeOfPublication().getFriendlyName(), getPriceOfPublication().toPlainString(), getMonthPublished());
    }

    @Override
    public void checkAffordability(BigDecimal price) {
        if (BigDecimal.valueOf(0.1).compareTo(price) != -1) {
            logger.warn(String.format("casopis %s neisplativ", getPublicationTitle()));
            throw new NonaffordablePublishingException("Neisplativo izdavanje casopisa " + getPublicationTitle());
        }
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
