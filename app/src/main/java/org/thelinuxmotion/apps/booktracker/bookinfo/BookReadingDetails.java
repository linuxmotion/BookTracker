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

    public BookReadingDetails(@NonNull long key) {
        mKey = key;
    }

    // This is the name of the key to search. it must be unique across all entries.
    @PrimaryKey
    public @NonNull
    long mKey;

    @ColumnInfo(name = "day")
    public int mDay;

    @ColumnInfo(name = "date")
    public long mDateTime;

    @ColumnInfo(name = "pages_completed")
    public int pagesCompleted;

    // The time that the book was started reading at.
    // it is in the format (hr*60 + min) and start from 0
    // it does not tak into account am or pm
    @ColumnInfo(name = "time_started_reading")
    public long mTimeStartedReading;
    // The time that the book was started reading at.
    // it is in the format (hr*60 + min) and start from 0
    // it does not tak into account am or pm
    @ColumnInfo(name = "time_stoped_reading")
    public long mTimeStopedReading;

    // How long we read the book, calulated value
    // it is in the format (hr*60 + min) and start from 0
    // stopped - started
    @ColumnInfo(name = "time_spent_reading")
    public long mTimeSpentReading;


}
