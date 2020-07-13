package org.thelinuxmotion.apps.booktracker.persistence;


import androidx.room.Database;
import androidx.room.RoomDatabase;

import org.thelinuxmotion.apps.booktracker.bookinfo.BookReadingDetails;

/**
 * Created by jweyr on 2/17/2018.
 */

@Database(entities = {BookReadingDetails.class}, version = 1)
public abstract class BookDetailsDatabase extends RoomDatabase {
    public abstract BookDetailsdao bookDetailsDao();


}
