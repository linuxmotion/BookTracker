package org.thelinuxmotion.apps.booktracker;

import org.junit.Test;
import org.thelinuxmotion.apps.booktracker.Isbndb.models.ISBN;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class ISBNUnitTest {

    @Test
    public void Is_ISBN10Valid() {

        System.out.print("Starting the ISBN10 Validation tests");
        assertTrue(ISBN.isValidISBN("0134706056"));
        assertFalse(ISBN.isValidISBN("0134706055"));
        assertFalse(ISBN.isValidISBN("9780134706055"));

        System.out.print("All tests passed successfully.");
    }

    @Test
    public void Is_ISBN13Valid() {

        System.out.print("Starting the ISBN13 Validation tests");
        assertTrue(ISBN.isValidISBN("9780134706054"));
        assertFalse(ISBN.isValidISBN("0134706055"));

        System.out.print("All tests passed successfully.");
    }
}
