package org.thelinuxmotion.apps.booktracker.bookinfo;

import java.util.ArrayList;

/**
 * Contains the data for a ISBN. Contains methods to validate ISBN's
 */

public class ISBN {

    public static boolean DEBUG = false;

    /**
     * @param isbn raw string of the isbn
     * @return whether the isbn that was passed into it is a
     * valid isbn.
     */
    public static boolean isValidISBN(String isbn) {


        if (DEBUG)
            return true;

        ArrayList<Integer> isbnl = convertToIntList(isbn);
        if (isbnl.size() == 10){
            return ISBN.checkISBN10(isbnl);
        }
        else if(isbnl.size() == 13){
            return ISBN.checkISBN13(isbnl);
        }
        return false;
    }

    private static ArrayList<Integer> convertToIntList(String isbn) {
        ArrayList<Integer> ls = new ArrayList<>();

        for (int i = 0; i < isbn.length(); i++){
            ls.add(Integer.parseInt(isbn.charAt(i) +"" ) );
        }
        return ls;
    }

    private static boolean checkISBN10(ArrayList<Integer> isbn) {

        final int ISBN10 = 10;
        int sum = 0;
        // We only nee the first nine digits
        for (int i = 1; i <= ISBN10; i++) {
            // get the ninth digit and count backwards
            // add this number to the sum
            sum = sum + (isbn.get(ISBN10-i)*i);
        }

        // Take the value mod 11 and see if it has a remainder
        int rem = sum % 11;
        if (rem == 0)
            return true;

        return false;
    }
    private static boolean checkISBN13(ArrayList<Integer> isbn) {

        final int ISBN13 = 13;
        boolean alternate = true;
        int multbyone = 0;
        int multbythree = 0;
        Integer digit = new Integer(0);
        for(int i = 1; i <= ISBN13; i++) {
            digit = isbn.get(i-1);
            if(alternate){
                // all of the odd values, 1,3,5,7,9,11
                multbyone += digit;
            }else{
                // all of the even values 2,4,6,8,10,12
                multbythree += digit;
            }
            alternate = !alternate;

        }
        // Multiply the even digits by three
        multbythree = multbythree*3;
        // add both sets of digits
        int genCheck = multbyone + multbythree;
        // take the mod 10 of the generated check value
        int mod = genCheck % 10;
        // if there is no remainder than it is a valid isbn
        if (mod == 0)
            return true;

        return  false;
    }

}
