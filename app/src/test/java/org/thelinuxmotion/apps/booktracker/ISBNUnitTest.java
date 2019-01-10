package org.thelinuxmotion.apps.booktracker;

import org.junit.Test;
import org.thelinuxmotion.apps.booktracker.bookinfo.ISBN;

import static org.junit.Assert.assertEquals;

public class ISBNUnitTest {

    @Test
    public void Is_ISBN10Valid() {

        System.out.print("Starting the ISBN10 Validation tests");
        assertEquals(ISBN.isValidISBN("0134706056"), true);
        assertEquals(ISBN.isValidISBN("0134706055"), false);
        assertEquals(ISBN.isValidISBN("9780134706055"), false);

        System.out.print("All tests passed successfully.");
    }

    @Test
    public void Is_ISBN13Valid() {

        System.out.print("Starting the ISBN13 Validation tests");
        assertEquals(ISBN.isValidISBN("9780134706054"), true);
        assertEquals(ISBN.isValidISBN("0134706055"), false);

        System.out.print("All tests passed successfully.");
    }
}
