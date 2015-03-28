package hr.vvg.java.vjezbe.entities;


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

    public Book(String title, String language, Publisher publisher, int yearPublished, int numberOfPages, String typeOfPublication) {
        super(title, yearPublished, numberOfPages, typeOfPublication, checkLanguageForPrice(language));
        this.language = language;
        this.publisher = publisher;
        this.available = true;
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
        super.printData();
        System.out.println(this.getData());
    }

    public String getData() {
        return String.format("Jezik: %s\nIzdavac: %s\nDrzava: %s", getLanguage(), getPublisher().getName(), getPublisher().getCountry());
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

}
