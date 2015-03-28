package hr.vvg.java.vjezbe.utilities;

/**
 * Created by marko on 3/28/15.
 */
public class HelpingHand {

    public static String upperFirst(String s) {
        return s.substring(0, 1).toUpperCase() + s.substring(1, s.length()).toLowerCase();
    }
}
