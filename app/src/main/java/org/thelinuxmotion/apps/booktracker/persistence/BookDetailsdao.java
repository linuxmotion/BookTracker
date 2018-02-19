package org.thelinuxmotion.apps.booktracker.persistence;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import org.thelinuxmotion.apps.booktracker.bookinfo.BookReadingDetails;

import java.util.List;

/**
 * Created by jweyr on 2/3/2018.
 */

@Dao
public interface BookDetailsdao {

    @Query("SELECT * FROM book")
    List<BookReadingDetails> getAll();

    @Insert
    void insertAll(BookReadingDetails... details);

    @Insert
    void add(BookReadingDetails details);

    @Delete
    void delete(BookReadingDetails details);
}