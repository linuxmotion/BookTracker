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

    public ArrayList<Integer> getIsbn() {
       // if(!mIsbn.isEmpty())
            return mIsbn;


    }


    public boolean setIsbn(ISBN isbn) {

        if( checkISBN(isbn.getIsbn()) ){
            this.mIsbn = isbn.getIsbn();
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

    static boolean checkISBN(ArrayList<Integer> isbn){


        ArrayList<Integer> nisbn = new ArrayList<Integer>();
        // If the array is a 2007 or ealier array convert
        // it to the 13 digit isbn number
        if( (isbn.size() != 10) && (isbn.size() != 13) )
            return false;

        if (isbn.size() == 10){

            nisbn.add(9);
            nisbn.add(7);
            nisbn.add(8);

        }
        for(int i = 0; i < isbn.size(); i ++)
            nisbn.add(isbn.get(i));
        //If true return that the number is correct

        int sum1 = 0, sum3 = 0;

        for(int i = 0; i < 12; i++){
            sum1 += nisbn.get(i);
            sum3 += (3*nisbn.get(i+1));
            i++;
        }

        int tsum = sum1 + sum3;
        int msum = ((tsum)%10);
        int checkDigit = 10 - msum;
        // Check the last digit and the check digit
        if(checkDigit == nisbn.get(12))
            return true;
        // if the check digits dindt match
        // say so
        return false;


    }



}
