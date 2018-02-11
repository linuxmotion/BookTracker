package org.thelinuxmotion.apps.booktracker.persistence;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import org.thelinuxmotion.apps.booktracker.bookinfo.Book;

import java.util.List;

/**
 * Created by jweyr on 2/3/2018.
 */

@Dao
public interface BookDBdao {

    @Query("SELECT * FROM main_bookshelf")
    List<Book> getAll();

    @Insert
    void insertAll(Book... books);

    @Insert
    void add(Book book);

    @Delete
    void delete(Book user);
}