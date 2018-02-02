package org.thelinuxmotion.apps.booktracker;

import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity implements BookShelfFragment.OnBookShelfInteractionListener, AddBookDialogFragment.OnAddBookDialogListener {
    BookShelfFragment mBookShelf;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mBookShelf = (BookShelfFragment) getSupportFragmentManager().findFragmentById(R.id.bookShelf);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
    }

    @Override
    public void onDialogPositiveClick(AddBookDialogFragment dialog) {


        String isbn = ((EditText) dialog.getDialog().findViewById(R.id.editISBN)).getText().toString();
        android.util.Log.v("ISBN: ", isbn);
        boolean validISBN = ISBN.isValidISBN(isbn);

        if (validISBN) {

            String completed =
                    ((EditText) dialog.getDialog().findViewById(R.id.editPagesCompleted)).getText().toString();
            String total =
                    ((EditText) dialog.getDialog().findViewById(R.id.editPagesTotal)).getText().toString();

            //TODO: Remove when done testing book adding system. We should never reach here with a empty isbn
            {
                if (isbn.isEmpty())//If we got here we are probably testing the book adding system
                    isbn = "123456789012";// this is a dummy value
            }

            // If the fields are blank just set them to zero
            if (completed.isEmpty())
                completed = "0";
            if (total.isEmpty())
                total = "0";
            int t = Integer.parseInt(total);
            int c = Integer.parseInt(completed);
            Book b = new Book(t, c, isbn);
            mBookShelf.addBooktoShelf(b);

        } else {
            CharSequence text = "Please enter a valid ISBN";
            //toast = Toast.makeText(context, text, duration);
        }
        //toast.show();

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