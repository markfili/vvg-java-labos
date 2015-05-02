package hr.vvg.java.vjezbe.utilities;

import hr.vvg.java.vjezbe.entities.Publication;

import java.math.BigDecimal;
import java.util.Comparator;

/**
 * Created by marko on 4/20/15.
 * <p>
 * Class PriceComparator
 * used to compare Publications by their publishing price
 */
public class PriceComparator implements Comparator<Publication> {
    /**
     * @param pub1 first publication to compare
     * @param pub2 second publication to compare
     * @return result of BigDecimal's compareTo method
     */
    @Override
    public int compare(Publication pub1, Publication pub2) {

        BigDecimal pubPrice1 = pub1.getPriceOfPublication();
        BigDecimal pubPrice2 = pub2.getPriceOfPublication();

        return (pubPrice1.compareTo(pubPrice2));
    }
}
