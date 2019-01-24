package org.thelinuxmotion.apps.booktracker.persistence;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import org.thelinuxmotion.apps.booktracker.Isbndb.models.Book;

/**
 * Created by jweyr on 2/3/2018.
 */

@Database(entities = {Book.class}, version = 1)
public abstract class AppDataBase extends RoomDatabase {
    public abstract BookDBdao bookDao();
}