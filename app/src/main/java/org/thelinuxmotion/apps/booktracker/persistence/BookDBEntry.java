package org.thelinuxmotion.apps.booktracker.persistence;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import org.thelinuxmotion.apps.booktracker.Book;

/**
 * Created by jweyr on 2/4/2018.
 */
@Entity(tableName = "book")
public class BookDBEntry {

    @PrimaryKey
    @NonNull
    public String ISBN = "";

    @ColumnInfo(name = "mTotalNumPages")
    public int mTotalNumPages;

    @ColumnInfo(name = "mPagesCompleted")
    public int mPagesCompleted;

    @Ignore
    public static BookDBEntry getDBEntryInstanceFromBook(Book b){
        BookDBEntry entry = new BookDBEntry();
        entry.ISBN = b.getmISBN();
        entry.mTotalNumPages = b.getTotalNumPages();
        entry.mPagesCompleted = b.getPagesCompleted();
        return entry;
    }

}
