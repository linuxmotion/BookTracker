package org.thelinuxmotion.apps.booktracker;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.EditText;

import org.thelinuxmotion.apps.booktracker.Isbndb.models.Book;
import org.thelinuxmotion.apps.booktracker.bookinfo.BookReadingDetails;
import org.thelinuxmotion.apps.booktracker.fragments.AddBookDetailsDialog;
import org.thelinuxmotion.apps.booktracker.fragments.BookDetailsFragment;

/**
 * Created by jweyr on 2/8/2018.
 */

public class BookDetailsActivity extends AppCompatActivity implements AddBookDetailsDialog.AddBookReadingDialogListener {

    private BookDetailsFragment mBookFragment;
    private Book mBook;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.book_activity_layout);


        // Initialize the book adapter
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
        if (ab != null)
            ab.setDisplayHomeAsUpEnabled(true);


    }


    @Override
    public void onDialogPositiveClick(AddBookDetailsDialog dialog) {

        BookReadingDetails details = new BookReadingDetails();
        details.mIsbn = mBookFragment.getBook().mISBN;
        EditText t = dialog.getDialog().findViewById(R.id.dialog_details_completed);
        details.pagesCompleted =
                Integer.parseInt(t.getText().toString());
        t = dialog.getDialog().findViewById(R.id.dialog_details_time_spent);
        details.mTimeSpentReading =
                Long.parseLong(t.getText().toString());


        t = dialog.getDialog().findViewById(R.id.dialog_details_date);//mm-dd-yyyy
        details.mDay =
                Integer.parseInt(t.getText().toString().split("-")[1]);

        t = dialog.getDialog().findViewById(R.id.dialog_details_time);//hh:mm
        details.mDateTime =
                Long.parseLong(t.getText().toString().split(":")[0]);

        Log.v("Dialog clicked",details.mDay + "Day of reading");
        mBookFragment.addDetails(details);


    }

    @Override
    public void onDialogNegativeClick(AddBookDetailsDialog dialog) {

    }
}
