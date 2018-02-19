package org.thelinuxmotion.apps.booktracker.persistence;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import org.thelinuxmotion.apps.booktracker.bookinfo.BookReadingDetails;

/**
 * Created by jweyr on 2/17/2018.
 */

@Database(entities = {BookReadingDetails.class}, version = 1)
public abstract class BookDetailsDatabase extends RoomDatabase {
    public abstract BookDetailsdao bookDetailsDao();


}
