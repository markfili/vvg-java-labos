package hr.vvg.java.vjezbe.utilities;

import hr.vvg.java.vjezbe.exceptions.ResultOutOfRange;

import java.io.IOException;
import java.util.Scanner;

/**
 * Contains helping methods for data manipulation and testing
 * Created by marko on 3/28/15.
 */
public class HelpingHand {

    public static String upperFirst(String s) {
        return s.substring(0, 1).toUpperCase() + s.substring(1, s.length()).toLowerCase();
    }

    public static void clearScreen() {
        try {
            Runtime.getRuntime().exec("clear");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static int checkIntInRange(Scanner scanner, int minValue, int maxValue) {
        int result = 0;
        boolean notOk = true;
        do {
            try {
                String entry = scanner.nextLine();
                // TODO handle cancellation
                if (!Literals.CONTINUE.equals(entry)) {
                    result = Integer.parseInt(entry);
                    checkIfInRange(result, minValue, maxValue);
                    notOk = false;
                }
            } catch (NumberFormatException|ResultOutOfRange ex) {
                System.out.println(ex.getMessage() + "Pokusajte ponovno.");
                notOk = true;
            }
        } while (notOk);

        return result;
    }

    private static void checkIfInRange(int result, int minValue, int maxValue) {
        if (result < minValue || result > maxValue) {
            throw new ResultOutOfRange("Uneseni broj je manji ili veći od ponuđenog intervala.");
        }
    }
}
