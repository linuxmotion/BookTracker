package org.thelinuxmotion.apps.booktracker.bookinfo;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

/**
 * Holds the information about a book. The isbn, pages read
 * and total pages the the book contains.
 * When updating the total number of pages read, the date
 * and time will be recorded into a persistent database
 * by the ISBN of the book
 */
@Entity(tableName = "main_bookshelf")
public class Book {


    @PrimaryKey
    @NonNull
    public String mISBN = "";

    @ColumnInfo(name = "ISBN_10")
    public String mIsbn_10;

    @ColumnInfo(name = "ISBN_13")
    public String mIsbn_13;

    @ColumnInfo(name = "Author")
    public String mAuthor;

    @ColumnInfo(name = "Edition")
    public String mEdition;

    @ColumnInfo(name = "Binding")
    public String mBinding;

    @ColumnInfo(name = "Publisher")
    public String mPublisher;

    @ColumnInfo(name = "Publish_date")
    public String mDatePublished;

    @ColumnInfo(name = "total_pages")
    public String mTotalPages;


    public Book() {

    }

    public boolean setISBN(@NonNull String isbn) {

        mISBN = isbn;
        return ISBN.isValidISBN(mISBN);
    }

    public String getmISBN() {

        return mISBN;
    }


    public String getIsbn_10() {
        return mIsbn_10;
    }

    public void setIsbn_10(String mIsbn_10) {
        this.mIsbn_10 = mIsbn_10;
    }

    public String getIsbn_13() {
        return mIsbn_13;
    }

    public void setIsbn_13(String mIsbn_13) {
        this.mIsbn_13 = mIsbn_13;
    }

    public String getAuthor() {
        return mAuthor;
    }

    public void setAuthor(String mAuthor) {
        this.mAuthor = mAuthor;
    }

    public String getEdition() {
        return mEdition;
    }

    public void setEdition(String mEdition) {
        this.mEdition = mEdition;
    }

    public String getBinding() {
        return mBinding;
    }

    public void setBinding(String mBinding) {
        this.mBinding = mBinding;
    }

    public String getPublisher() {
        return mPublisher;
    }

    public void setPublisher(String mPublisher) {
        this.mPublisher = mPublisher;
    }

    public String getDatePublished() {
        return mDatePublished;
    }

    public void setDatePublished(String mDatePublished) {
        this.mDatePublished = mDatePublished;
    }



}
