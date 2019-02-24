package org.thelinuxmotion.apps.booktracker.Isbndb.models;

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
    public final static String mBookTitleName = "title";

    @ColumnInfo(name = "BOOK_TITLE_LONG")
    public String mBookTitleLong;
    public final static String mBookTitleLongName = "title_long";

    @ColumnInfo(name = "ISBN_10")
    public String mIsbn_10;
    public final static String mIsbn10Name = "isbn";

    @ColumnInfo(name = "ISBN_13")
    public String mIsbn_13;
    public final static String mIsbn13Name = "isbn13";

    @ColumnInfo(name = "Dewey_Decimal ")
    public String mDeweyDecimal;
    public final static String mDeweyDecimalName = "dewey_decimal";

    @ColumnInfo(name = "Format")
    public String mFormat;
    public final static String mFormatName = "format";

    @ColumnInfo(name = "Image")
    public String mImage;
    public final static String mImageName = "image";

    @ColumnInfo(name = "MSRP")
    public String mMsrp;
    public final static String mMsrpName = "msrp";

    @ColumnInfo(name = "Binding")
    public String mBinding;
    public final static String mBindingName = "binding";

    @ColumnInfo(name = "Publisher")
    public String mPublisher;
    public final static String mPublisherName = "publisher";

    @ColumnInfo(name = "Language")
    public String mLanguage;
    public final static String mLanguageName = "language";

    @ColumnInfo(name = "Publish_date")
    public String mDatePublished;
    public final static String mDatePublishedName = "date_published";

    @ColumnInfo(name = "Edition")
    public String mEdition;
    public final static String mEditionName = "edition";

    @ColumnInfo(name = "Pages")
    public String mNumPages;
    public final static String mNumPagesName = "pages";

    @ColumnInfo(name = "Dimensions")
    public String mDimensions;
    public final static String mDimensionsName = "dimensions";

    @ColumnInfo(name = "Overview")
    public String mOverview;
    public final static String mOverviewName = "overview";

    @ColumnInfo(name = "Excerpt")
    public String mExcerpt;
    public final static String mExcerptName = "excerpt";

    @ColumnInfo(name = "Synopsys")
    public String mSynopsys;
    public final static String mSynopsysName = "synopsys";

    @ColumnInfo(name = "Authors")
    public String mAuthors;
    public final static String mAuthorName = "authors";

    @ColumnInfo(name = "Subjects")
    public String mSubjects;
    public final static String mSubjectsName = "subjects";

    @ColumnInfo(name = "Reviews")
    public String mReviews;
    public final static String mReviewsName = "reviews";

    @ColumnInfo(name = "Pages_Completed")
    public String mPagesCompleted;
    public final static String mPagesCompletedName = "Pages_completed";

    public Book() {

    }

    public Book(String isbn) {
        setISBN(isbn);
    }

    public boolean setISBN(@NonNull String isbn) {

        if (!ISBN.isValidISBN(mISBN))
            return false;

        mISBN = isbn;
        return true;
    }


    public Intent toIntent() {

        Intent b = new Intent();
        b.putExtra(mAuthorName, mAuthors);
        b.putExtra(mBookTitleName, mBookTitle);
        b.putExtra(mBindingName, mBinding);
        b.putExtra(mEditionName, mEdition);
        b.putExtra(mPublisherName, mPublisher);
        b.putExtra(mDatePublishedName, mDatePublished);
        b.putExtra(mIsbn13Name, mIsbn_13);
        b.putExtra(mIsbn13Name, mISBN);
        b.putExtra(mIsbn10Name, mIsbn_10);
        b.putExtra(mNumPagesName, mNumPages);
        b.putExtra(mPagesCompletedName, mPagesCompleted);
        b.putExtra(mSynopsysName, mSynopsys);
        b.putExtra(mBookTitleLongName, mBookTitleLong);
        b.putExtra(mDeweyDecimalName, mDeweyDecimal);
        b.putExtra(mSubjectsName, mSubjects);
        b.putExtra(mReviewsName, mReviews);
        b.putExtra(mOverviewName, mOverview);
        b.putExtra(mMsrpName, mMsrp);
        b.putExtra(mLanguageName, mLanguage);
        b.putExtra(mImageName, mImage);
        b.putExtra(mFormatName, mFormat);
        b.putExtra(mExcerptName, mExcerpt);
        b.putExtra(mEditionName, mEdition);
        b.putExtra(mDimensionsName, mDimensions);

        return b;

    }

    public void fromIntent(Intent i) {

        mAuthors = i.getStringExtra(mAuthorName);
        mBinding = i.getStringExtra(mBindingName);
        mEdition = i.getStringExtra(mEditionName);
        mPublisher = i.getStringExtra(mPublisherName);
        mDatePublished = i.getStringExtra(mDatePublishedName);
        mIsbn_13 = i.getStringExtra(mIsbn13Name);
        mIsbn_10 = i.getStringExtra(mIsbn10Name);
        mNumPages = i.getStringExtra(mNumPagesName);
        mPagesCompleted = i.getStringExtra(mPagesCompletedName);
        mBookTitle = i.getStringExtra(mBookTitleName);
        mSynopsys = i.getStringExtra(mSynopsysName);
        mBookTitleLong = i.getStringExtra(mBookTitleLongName);
        mDeweyDecimal = i.getStringExtra(mDeweyDecimalName);
        mSubjects = i.getStringExtra(mSubjectsName);
        mReviews = i.getStringExtra(mReviewsName);
        mOverview = i.getStringExtra(mOverviewName);
        mMsrp = i.getStringExtra(mMsrpName);
        mLanguage = i.getStringExtra(mLanguageName);
        mImage = i.getStringExtra(mImageName);
        mFormat = i.getStringExtra(mFormatName);
        mExcerpt = i.getStringExtra(mExcerptName);
        mEdition = i.getStringExtra(mEditionName);
        mDimensions = i.getStringExtra(mDimensionsName);


        mISBN = mIsbn_13;
    }

    public void LogBook(String callingClass) {


        Log.d(callingClass, mAuthors);
        Log.d(callingClass, mBinding);
        Log.d(callingClass, mEdition);
        Log.d(callingClass, mPublisher);
        Log.d(callingClass, mDatePublished);
        Log.d(callingClass, mIsbn_13);
        Log.d(callingClass, mIsbn_10);
        Log.d(callingClass, mNumPages);
        Log.d(callingClass, mPagesCompleted);
        Log.d(callingClass, mBookTitle);
        Log.d(callingClass, mSynopsys);
        Log.d(callingClass, mISBN);


    }
}


