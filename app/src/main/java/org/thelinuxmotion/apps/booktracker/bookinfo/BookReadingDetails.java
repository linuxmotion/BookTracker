package org.thelinuxmotion.apps.booktracker.bookinfo;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

/**
 * Created by jweyr on 2/11/2018.
 */

@Entity(tableName = "book")
public class BookReadingDetails {

    // This is the name of the database
    @PrimaryKey
    public @NonNull String mIsbn;

    @ColumnInfo(name = "day")
    public int mDay;

    @ColumnInfo(name = "date")
    public long mDateTime;

    @ColumnInfo(name = "pages_completed")
    public int pagesCompleted;

    @ColumnInfo(name = "time_spent_reading")
    public long mTimeSpentReading;

}
