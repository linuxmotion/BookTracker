package org.thelinuxmotion.apps.booktracker;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.widget.Toast;

public class MainActivity extends FragmentActivity implements BookShelfFragment.OnFragmentInteractionListener, AddBookDialogFragment.OnAddBookDialogListener {
    BookShelfFragment mBookShelf;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mBookShelf = (BookShelfFragment) getSupportFragmentManager().findFragmentById(R.id.bookShelf);

    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public void onDialogPositiveClick(AddBookDialogFragment dialog) {

        boolean validISBN = ISBN.isValidISBN(dialog.getISBN().getText().toString());
        Context context = this.getApplicationContext();
        int duration = Toast.LENGTH_SHORT;
        Toast toast;
        mBookShelf.setIsValidISBN(validISBN);

        if (validISBN) {
            CharSequence text = "Valid ISBN";
            toast = Toast.makeText(context, text, duration);

        } else {
            CharSequence text = "Invalid ISBN";
            toast = Toast.makeText(context, text, duration);
        }
        toast.show();

    }


    @Override
    public void onDialogNegativeClick(AddBookDialogFragment dialog) {

    }
}