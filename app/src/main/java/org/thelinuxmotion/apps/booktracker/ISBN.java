package org.thelinuxmotion.apps.booktracker;

/**
 * Contains the data for a ISBN. Contains methods to validate ISBN's
 */

public class ISBN {

    public static boolean DEBUG = true;

    /**
     * @param isbn raw string of the isbn
     * @return whether the isbn that was passed into it is a
     * valid isbn.
     */
    public static boolean isValidISBN(String isbn) {

        //DEBUG SET TO TRUE ALWAYS
        if (DEBUG)
            return true;

        if (isbn.length() != 10 && isbn.length() != 13)
            return false;


        return ISBN.checkISBN(isbn);

    }


    private static boolean checkISBN(String isbn) {


        // If the array is a 2007 or ealier array convert
        // it to the 13 digit isbn number
        String nisbn = "";

        if (isbn.length() == 10) {

            nisbn += "9";
            nisbn += "7";
            nisbn += "8";

        }
        for (int i = 0; i < isbn.length(); i++)
            nisbn += isbn.charAt(i);
        //If true return that the number is correct

        int sum1 = 0, sum3 = 0;
        StringBuilder c = new StringBuilder();

        for (int i = 0; i < 12; i++) {

            c.append(nisbn.charAt(i));

            sum1 += Integer.parseInt(c.toString());

            c = new StringBuilder();
            c.append(nisbn.charAt(i + 1));

            sum3 += (3 * Integer.parseInt(c.toString()));

            i++;
        }

        int tsum = sum1 + sum3;
        int msum = ((tsum) % 10);
        int checkDigit = 10 - msum;

        c = new StringBuilder();
        c.append(nisbn.charAt(12));

        // Check the last digit and the check digit
        if (checkDigit == Integer.parseInt(c.toString()))
            return true;
        // if the check digits dindt match
        // say so
        return false;


    }


}
