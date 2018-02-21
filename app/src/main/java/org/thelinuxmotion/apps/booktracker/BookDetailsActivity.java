package org.thelinuxmotion.apps.booktracker;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.EditText;

import org.thelinuxmotion.apps.booktracker.bookinfo.Book;
import org.thelinuxmotion.apps.booktracker.bookinfo.BookReadingDetails;
import org.thelinuxmotion.apps.booktracker.fragments.AddBookDetailsDialog;
import org.thelinuxmotion.apps.booktracker.fragments.BookDetailsFragment;

/**
 * Created by jweyr on 2/8/2018.
 */

public class BookDetailsActivity extends AppCompatActivity implements AddBookDetailsDialog.AddBookReadingDialogListener {

    private BookDetailsFragment mBookFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.book_activity_layout);


        Book b = new Book();
        b.fromIntent(getIntent());
        // Intialize the book adapater
        Toolbar myToolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);


        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        mBookFragment = BookDetailsFragment.newInstance(getIntent());
        fragmentTransaction.replace(R.id.book_activity_fragment, mBookFragment);
        fragmentTransaction.commit();

        // Get a support ActionBar corresponding to this toolbar
        ActionBar ab = getSupportActionBar();

        // Enable the Up button
        ab.setDisplayHomeAsUpEnabled(true);


    }

    @Override
    public void onDialogPositiveClick(AddBookDetailsDialog dialog) {

        BookReadingDetails details = new BookReadingDetails();
        EditText t = (EditText) dialog.getDialog().findViewById(R.id.dialog_details_completed);
        details.pagesCompleted =
                Integer.parseInt(t.getText().toString());
        t = (EditText) dialog.getDialog().findViewById(R.id.dialog_details_time_spent);
        details.mTimeSpentReading =
                Long.parseLong(t.getText().toString());

        t = (EditText) dialog.getDialog().findViewById(R.id.dialog_details_date);
        details.mDay =
                Integer.parseInt(t.getText().toString());

        t = (EditText) dialog.getDialog().findViewById(R.id.dialog_details_time);
        details.mDateTime =
                Long.parseLong(t.getText().toString());


        mBookFragment.addDetails(details);


    }

    @Override
    public void onDialogNegativeClick(AddBookDetailsDialog dialog) {

    }
}
