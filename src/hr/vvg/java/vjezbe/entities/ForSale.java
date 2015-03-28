package hr.vvg.java.vjezbe.entities;

import java.math.BigDecimal;

/**
 * Created by marko on 3/18/15.
 */
public interface ForSale {

    default BigDecimal calculatePrice(int pagesNumber, String typeOfPublication, double priceOfPublication) {
        BigDecimal costOfPublishingPaper = BigDecimal.valueOf(pagesNumber).multiply(BigDecimal.valueOf(priceOfPublication).setScale(2, BigDecimal.ROUND_CEILING));
        BigDecimal costOfPublishingElectronic = costOfPublishingPaper.divide(BigDecimal.valueOf(17), BigDecimal.ROUND_CEILING);

        switch (typeOfPublication) {
            case Publication.TYPE_ELECTRONIC:
                return costOfPublishingElectronic;
            case Publication.TYPE_PAPER:
                return costOfPublishingPaper;
        }
        return null;
    }
}
