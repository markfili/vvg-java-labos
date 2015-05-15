package hr.vvg.java.vjezbe.entities;

import hr.vvg.java.vjezbe.enumerations.PublicationType;
import hr.vvg.java.vjezbe.exceptions.NonaffordablePublishingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;

/**
 * Magazine object class
 * <p>Creates a magazine object if affordable
 * <p>
 * Created by marko on 3/19/15.
 */
public class Magazine extends Publication {

    private static final float PRICE_PER_COPY = 10;
    private Logger logger = LoggerFactory.getLogger(Magazine.class);
    private int monthPublished;

    public Magazine(String publicationTitle, int monthPublished, int yearPublished, int numberOfPages, PublicationType typeOfPublication) {
        super(publicationTitle, yearPublished, numberOfPages, typeOfPublication, checkPricePerPage(numberOfPages));
        this.monthPublished = monthPublished;
        checkAffordability(super.getPriceOfPublication());
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
    public String fileData() {
        return String.format("%s\n%s\n%s\n%s\n%s\n", getPublicationTitle(), getMonthPublished(), getYearPublished(), getNumberOfPages(), getTypeOfPublication().getId());
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
