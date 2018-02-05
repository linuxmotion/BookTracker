package org.thelinuxmotion.apps.booktracker.persistence;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

/**
 * Created by jweyr on 2/3/2018.
 */

@Database(entities = {BookDBEntry.class}, version = 1)
public abstract class AppDataBase extends RoomDatabase {
    public abstract BookDBdao bookDao();
}