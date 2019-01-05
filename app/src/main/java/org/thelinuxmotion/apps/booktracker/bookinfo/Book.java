package org.thelinuxmotion.apps.booktracker.bookinfo;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.util.Log;

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

    @ColumnInfo(name = "BOOK_TITLE")
    public String mBookTitle;
    final static String mBookTitleName = "BOOK_TITLE";


    @ColumnInfo(name = "ISBN_10")
    public String mIsbn_10;
    final static String mIsbn10Name = "ISBN_10";

    @ColumnInfo(name = "ISBN_13")
    public String mIsbn_13;
    final static public String mIsbn13Name = "ISBN_13";


    @ColumnInfo(name = "Author")
    public String mAuthor;
    final static public String mAuthorName = "Author";

    @ColumnInfo(name = "Edition")
    public String mEdition;
    final static public String mEditionName = "Edition";

    @ColumnInfo(name = "Binding")
    public String mBinding;
    final static public String mBindingName = "Binding";

    @ColumnInfo(name = "Publisher")
    public String mPublisher;
    public final static String mPublisherName = "Publisher";

    @ColumnInfo(name = "Publish_date")
    public String mDatePublished;
    public final static String mDatePublishedName = "PublisSh_date";

    @ColumnInfo(name = "Pages_Completed")
    public String mPagesCompleted;
    public final static String mPagesCompletedName = "Pages_completed";

    @ColumnInfo(name = "Total_pages")
    public String mTotalPages;
    public final static String mTotalPagesName = "Total_pages";

    @ColumnInfo(name = "Synopsys")
    public String mSynopsis;
    public final static String mSynopsisName = "Synopsys";
    
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

    public String getTotalPages() {return mTotalPages;  }

    public String getPAgesCompleted() {return mPagesCompleted;  }

    public Intent toIntent(){

        Intent b = new Intent();
        b.putExtra(mAuthorName,mAuthor);
        b.putExtra(mBookTitleName,mBookTitle);
        b.putExtra(mBindingName, mBinding);
        b.putExtra(mEditionName, mEdition);
        b.putExtra(mPublisherName, mPublisher);
        b.putExtra(mDatePublishedName, mDatePublished);
        b.putExtra(mIsbn13Name, mIsbn_13);
        //TODO: REmove during release, override the isbn 13 for now
        b.putExtra(mIsbn13Name, mISBN);
        //in the end this value will be assigned by a online db
        b.putExtra(mIsbn10Name,mIsbn_10);
        b.putExtra(mTotalPagesName, mTotalPages);
        b.putExtra(mPagesCompletedName, mPagesCompleted);
        b.putExtra(mSynopsisName,mSynopsis);
        return b;

    }

    public void fromIntent(Intent i){

        mAuthor = i.getStringExtra(mAuthorName);
        mBinding = i.getStringExtra(mBindingName);
        mEdition = i.getStringExtra(mEditionName);
        mPublisher = i.getStringExtra(mPublisherName);
        mDatePublished = i.getStringExtra(mDatePublishedName);
        mIsbn_13 = i.getStringExtra(mIsbn13Name);
        mIsbn_10 = i.getStringExtra(mIsbn10Name);
        mTotalPages = i.getStringExtra(mTotalPagesName);
        mPagesCompleted = i.getStringExtra(mPagesCompletedName);
        mBookTitle = i.getStringExtra(mBookTitleName);
        mSynopsis = i.getStringExtra(mSynopsisName);
        mISBN = mIsbn_13;
    }

    public void LogBook(String callingClass){


        Log.d(callingClass,mAuthor);
        Log.d(callingClass,mBinding);
        Log.d(callingClass,mEdition);
        Log.d(callingClass,mPublisher);
        Log.d(callingClass,mDatePublished);
        Log.d(callingClass,mIsbn_13);
        Log.d(callingClass,mIsbn_10);
        Log.d(callingClass,mTotalPages);
        Log.d(callingClass,mPagesCompleted);
        Log.d(callingClass,mBookTitle);
        Log.d(callingClass,mSynopsis);
        Log.d(callingClass,mISBN);


    }
}


