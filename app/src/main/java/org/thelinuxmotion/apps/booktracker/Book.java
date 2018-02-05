package org.thelinuxmotion.apps.booktracker;

import org.thelinuxmotion.apps.booktracker.persistence.BookDBEntry;

/**
 * Holds the information about a book. The isbn, pages read
 * and total pages the the book contains.
 * When updating the total number of pages read, the date
 * and time will be recorded into a persistent database
 * by the ISBN of the book
 */
public class Book {

    private String mISBN;

    private int mTotalNumPages;
    private int mPagesCompleted;


    public Book(int pages_total, int pages_completed, String isbn) {
        this.mTotalNumPages = pages_total;
        this.mPagesCompleted = pages_completed;
        this.mISBN = isbn;
    }

    public static Book getInstanceFromDBEntry(BookDBEntry entry){

        return new Book(entry.mTotalNumPages,
                        entry.mPagesCompleted,
                        entry.ISBN);
    }

    public void setPagesCompleted(int pagesCompleted) {

        this.mPagesCompleted = pagesCompleted;
    }

    public boolean setISBN(String isbn) {

        mISBN = isbn;
        return ISBN.isValidISBN(mISBN);
    }

    public void setTotalNumPages(int totalNumPages) {

        this.mTotalNumPages = totalNumPages;
    }

    public int getPagesCompleted() {

        return mPagesCompleted;
    }

    public int getTotalNumPages() {
        return mTotalNumPages;
    }


    public String getmISBN() {

        return mISBN;
    }


}
