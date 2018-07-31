package org.thelinuxmotion.apps.booktracker;

import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import org.thelinuxmotion.apps.booktracker.bookinfo.Book;
import org.thelinuxmotion.apps.booktracker.bookinfo.ISBN;
import org.thelinuxmotion.apps.booktracker.fragments.AddBookDialogFragment;
import org.thelinuxmotion.apps.booktracker.fragments.BookShelfFragment;

/**
 * Main activity. Handles the bookshelf fragment as well
 * as the book dialogs. The handling of the UI for a single
 * book and no the entire shelf should be done in its own
 * activity or fragment and dynamically envoked
 *
 */
public class MainActivity extends AppCompatActivity implements BookShelfFragment.OnBookShelfInteractionListener, AddBookDialogFragment.OnAddBookDialogListener {
    BookShelfFragment mBookShelf;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mBookShelf = (BookShelfFragment) getSupportFragmentManager().findFragmentById(R.id.bookShelf);
        Toolbar myToolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

    }

    @Override
    public void onDialogPositiveClick(AddBookDialogFragment dialog) {


        String isbn = ((EditText) dialog.getDialog().findViewById(R.id.editISBN)).getText().toString();
        android.util.Log.v("ISBN: ", isbn);
        boolean validISBN = ISBN.isValidISBN(isbn);

        if (validISBN) {


            //TODO: Remove when done testing book adding system. We should never reach here with a empty isbn
            {
                if (isbn.isEmpty())//If we got here we are probably testing the book adding system
                    isbn = "123456789012";// this is a dummy value
            }

            Book b = getBookfromOnlineDB(isbn);
            // add the filled book
            mBookShelf.addBooktoShelf(b);


        } else {
            CharSequence text = "Please enter a valid ISBN";
            //toast = Toast.makeText(context, text, duration);
        }
        //toast.show();

    }

    /**
     * Set the paramaters of the book. Uses an online database given a valid
     * isbn
     * @param isbn Isbn to perform the search query against
     * @return
     */
    public Book getBookfromOnlineDB(String isbn) {
        //TODO: Query the book by ISBN from an online DB and fill in the info for the book
        // for now just create a book from the ISBN
         Book b = new Book();
         b.setISBN(isbn);
         b.mBookTitle = "Unknown title";
         b.mAuthor ="Unknown Author";
         b.mBinding = "Unknown binding";
         b.mEdition = "Unknown edition";
         b.mPublisher = "Unknown publisher";
         b.mPagesCompleted = "0";
         b.mTotalPages = "1000";

         return b;
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

    @Override
    public void OnBookShelfInteraction(Uri uri) {

    }
}