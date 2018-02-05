package org.thelinuxmotion.apps.booktracker.persistence;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

/**
 * Created by jweyr on 2/3/2018.
 */

@Dao
public interface BookDBdao {

    @Query("SELECT * FROM Book")
    List<BookDBEntry> getAll();

    @Insert
    void insertAll(BookDBEntry... books);

    @Insert
    void add(BookDBEntry book);

    @Delete
    void delete(BookDBEntry user);
}