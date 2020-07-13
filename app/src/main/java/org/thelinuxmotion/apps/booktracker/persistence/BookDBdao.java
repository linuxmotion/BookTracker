package org.thelinuxmotion.apps.booktracker.persistence;


import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import org.thelinuxmotion.apps.booktracker.Isbndb.models.Book;

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