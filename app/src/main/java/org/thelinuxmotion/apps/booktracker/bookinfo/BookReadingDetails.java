package org.thelinuxmotion.apps.booktracker.bookinfo;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;

import java.util.Date;

/**
 * Created by jweyr on 2/11/2018.
 */

@Entity
public class BookReadingDetails {

    // This is the name of the database
    String mIsbn;

    @ColumnInfo(name = "date")
    Date mDate;

    @ColumnInfo(name = "pages_completed")
    int pagesCompleted;

    @ColumnInfo(name = "time_spent_reading")
    long mTimeSpentReading;

}
