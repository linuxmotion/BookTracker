package org.thelinuxmotion.apps.booktracker;

import java.util.ArrayList;

/**
 * Created by jweyr on 1/25/2018.
 */

public class Book {

    private int mTotalNumPages;
    private int mPagesCompleted;
    private ISBN mISBN;
    public Book(int totalNumPages, int pagesCompleted, ArrayList<Integer> isbn) {

       this(totalNumPages,pagesCompleted,new ISBN(isbn));
    }
    public Book(int totalNumPages, int pagesCompleted, ISBN isbn) {
        this.mTotalNumPages = totalNumPages;
        this.mPagesCompleted = pagesCompleted;
        this.mISBN = isbn;
    }

    public int getPagesCompleted() {
        return mPagesCompleted;
    }

    public void setPagesCompleted(int pagesCompleted) {
        this.mPagesCompleted = pagesCompleted;
    }

    public ISBN getISBN() {
        return mISBN;
    }
    public boolean setISBN(ArrayList isbn) {

        return mISBN.setIsbn(isbn);
    }
    public boolean setISBN(ISBN isbn) {

        return mISBN.setIsbn(isbn);
    }

    public int getTotalNumPages() {
        return mTotalNumPages;
    }

    public void setTotalNumPages(int totalNumPages) {
        this.mTotalNumPages = totalNumPages;
    }





}
