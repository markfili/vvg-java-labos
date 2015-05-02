package hr.vvg.java.vjezbe.interfaces;

import hr.vvg.java.vjezbe.enumerations.PublicationType;

import java.math.BigDecimal;

/**
 *
 * Created by marko on 3/18/15.
 */
public interface ForSale {

    default BigDecimal calculatePrice(int pagesNumber, PublicationType typeOfPublication, double priceOfPublication) {
        BigDecimal costOfPublishingPaper = BigDecimal.valueOf(pagesNumber).multiply(BigDecimal.valueOf(priceOfPublication).setScale(2, BigDecimal.ROUND_CEILING));
        BigDecimal costOfPublishingElectronic = costOfPublishingPaper.divide(BigDecimal.valueOf(17), BigDecimal.ROUND_CEILING);

        switch (typeOfPublication) {
            case DIGITAL:
                return costOfPublishingElectronic;
            case PAPER:
                return costOfPublishingPaper;
        }
        return null;
    }
}
