package org.thelinuxmotion.apps.booktracker;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jweyr on 1/25/2018.
 */

public class ISBN {

    public ArrayList<Integer> mIsbn;

    public ISBN(ArrayList<Integer> mIsbn) {
        this.mIsbn = mIsbn;
    }

    public ArrayList<Integer> getmIsbn() {
        return mIsbn;
    }


    public boolean setIsbn(ISBN isbn) {

        if( checkISBN(isbn.getmIsbn()) ){
            this.mIsbn = isbn.getmIsbn();
            return true;
        }
        return false;
    }
    public boolean setIsbn(ArrayList<Integer> mIsbn) {
        if(checkISBN(mIsbn)){
            this.mIsbn = mIsbn;
            return true;
        }
        return false;
    }

    boolean checkISBN(ArrayList<Integer> mIsbn){


        ArrayList<Integer> isbn = new ArrayList<Integer>();
        // If the array is a 2007 or ealier array convert
        // it to the 13 digit isbn number
        if (mIsbn.size() == 10){

            isbn.add(9);
            isbn.add(7);
            isbn.add(8);

        }
        //If true return that the number is correct

        int sum1 = 0, sum3 = 0;

        for(int i = 0; i < 6; i++){
            sum1 += isbn.get(i);
            sum3 += (3*isbn.get(i));
        }

        int checkDigit = 10 - ((sum1 + sum3)%10);
        // Check the last digit and the check digit
        if(checkDigit == isbn.get(13))
            return true;
        // if the check digits dindt match
        // say so
        return false;


    }



}
