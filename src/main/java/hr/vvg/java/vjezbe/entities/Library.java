package hr.vvg.java.vjezbe.entities;

import hr.vvg.java.vjezbe.interfaces.ForLoan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * Library object class
 * <p>Represents a library and contains a list of available Publications.</p>
 * Created by marko on 5/3/15.
 */
public class Library<T extends Publication> {

    private static Logger logger = LoggerFactory.getLogger(Library.class);

    private List<T> libraryList;

    public Library() {
        this.libraryList = new ArrayList<>();
    }

    public void acquire(T publication) {
        libraryList.add(publication);
    }

    public List<T> getLibraryList() {
        return libraryList;
    }

    public void available() {
        logger.info("Logging available books");
        List<Publication> availables = libraryList.stream().filter(new Predicate<T>() {
            @Override
            public boolean test(T t) {
                return t instanceof ForLoan && ((ForLoan) t).isAvailable();
            }
        }).collect(Collectors.toList());
        System.out.println("\nSve publikacije koje je moguce posuditi:");
        availables.forEach(Publication::printData);
        System.out.println();
        availables.forEach(p -> logger.info("dostupno:\n" + p.getData()));
    }
}
