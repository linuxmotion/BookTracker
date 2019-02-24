package org.thelinuxmotion.apps.booktracker;

import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import org.thelinuxmotion.apps.booktracker.Isbndb.ISBNOnlineDatabase;
import org.thelinuxmotion.apps.booktracker.Isbndb.models.Book;
import org.thelinuxmotion.apps.booktracker.Isbndb.models.ISBN;
import org.thelinuxmotion.apps.booktracker.fragments.AddBookDialogFragment;
import org.thelinuxmotion.apps.booktracker.fragments.BookShelfFragment;

import java.util.ArrayList;

/**
 * Main activity. Handles the bookshelf fragment as well
 * as the book dialogs. The handling of the UI for a single
 * book and no the entire shelf should be done in its own
 * activity or fragment and dynamically envoked
 *
 */
public class MainActivity extends AppCompatActivity implements BookShelfFragment.OnBookShelfInteractionListener,
        AddBookDialogFragment.OnAddBookDialogListener, ISBNOnlineDatabase.ISBNDBCallbackInterface {


    private BookShelfFragment mBookShelf;
    ArrayList<String> mISBNList;
    String mRetryISBN;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mBookShelf = (BookShelfFragment) getSupportFragmentManager().findFragmentById(R.id.bookShelf);
        Toolbar myToolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        mISBNList = new ArrayList<>();


    }

    @Override
    public void onDialogPositiveClick(AddBookDialogFragment dialog) {


        String isbn = ((EditText) dialog.getDialog().findViewById(R.id.editISBN)).getText().toString();
        android.util.Log.v("ISBN: ", isbn);

        // We see that we have enterd a book twice, do not add it again
        for (String s : mISBNList) { // check all the books in the current shelf
            if (isbn.equals(s)) // if we find the book that matches the isbn do not continue
                return; // instead we should tell the user that the book has already been added

        }
        mRetryISBN = isbn; // Set the isbn to retry if the retireiving the book is unsuccessful
        //"9780134706054"
        boolean validISBN = ISBN.isValidISBN(isbn);

        if (validISBN) {
            mRetryISBN = isbn;
            retrieveBookFromOnlineDB(isbn);
            // add the filled book

        } else {
            CharSequence text = "Please enter a valid ISBN";
            Toast.makeText(this.getBaseContext(), text, Toast.LENGTH_SHORT).show();
        }
    }

    private void retrieveBookFromOnlineDB(String isbn) {

        ISBNOnlineDatabase ISBNDB = new ISBNOnlineDatabase();
        ISBNDB.getBookfromOnlineDB(isbn, this, this);
    }

    @Override
    public void onDialogNegativeClick(AddBookDialogFragment dialog) {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.appbarmenu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_add: {
                AddBookDialogFragment dialog = AddBookDialogFragment.newInstance("", "");
                dialog.show(getSupportFragmentManager(), "Add book");

            }
            break;
            default:
                break;
        }
        return true;
    }

    private int mRecursionLevel = 0;
    @Override
    public void OnReturnBook(Book book) {
        // A book retrived from the online db is return here.
        //Check to see if we recived a valid book

        if(book == null){

            if (mRecursionLevel >= 1) { // reset the recursion, end the chain
                mRetryISBN = "";
                mRecursionLevel = 0;
                return;
            }
            if (!mRetryISBN.equals("")) { // only recurse if there is a valid retry isbn
                mRecursionLevel++;
                retrieveBookFromOnlineDB(mRetryISBN);

            }
            return;
        }
        // We recived a valid book from the db
        // Add it to the book shelf
        book.LogBook( "Succesfully propagated up to " + this.getClass().toString());
        mBookShelf.addBooktoShelf(book);
    }


    @Override
    public void OnBookShelfInteraction(Uri uri) {

    }


}