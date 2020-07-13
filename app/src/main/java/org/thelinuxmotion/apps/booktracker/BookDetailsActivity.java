package org.thelinuxmotion.apps.booktracker;

import android.icu.util.GregorianCalendar;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import org.thelinuxmotion.apps.booktracker.bookinfo.BookReadingDetails;
import org.thelinuxmotion.apps.booktracker.fragments.AddBookDetailsDialog;
import org.thelinuxmotion.apps.booktracker.fragments.BookDetailsFragment;

/**
 * Created by jweyr on 2/8/2018.
 */

public class BookDetailsActivity extends AppCompatActivity implements AddBookDetailsDialog.AddBookReadingDialogListener {

    private BookDetailsFragment mBookFragment;
    private long mCurrentNewId = 0; // we only support Num(long) entries per book
    //private Book mBook;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.book_activity_layout);


        // Initialize the book adapter
        Toolbar myToolbar = findViewById(R.id.my_toolbar);

//        setSupportActionBar(myToolbar);


        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        mBookFragment = BookDetailsFragment.newInstance(getIntent());
        fragmentTransaction.replace(R.id.book_activity_fragment, mBookFragment);
        fragmentTransaction.commit();

        // Get a support ActionBar corresponding to this toolbar
        //ActionBar ab = getSupportActionBar();
       // ab.setTitle(mBookFragment.getBook().mBookTitle);
        // Enable the Up button
      //  if (ab != null)
       //     ab.setDisplayHomeAsUpEnabled(true);


    }


    @Override
    public void onDialogPositiveClick(AddBookDetailsDialog dialog) {


        //Incrase the id number for each book entered
        BookReadingDetails details = new BookReadingDetails(mCurrentNewId++);

        //
        //
        EditText t = dialog.getDialog().findViewById(R.id.dialog_details_date);//mm-dd-yyyy
        String mdy = t.getText().toString();

        int month = Integer.parseInt(mdy.split("-")[0]);
        int day = Integer.parseInt(mdy.split("-")[1]);
        int year = Integer.parseInt(mdy.split("-")[2]);

        details.mDay = day;
        details.mDateTime = new GregorianCalendar(year, month, day).getTimeInMillis();

        //
        //
        t = dialog.getDialog().findViewById(R.id.dialog_details_time_start);//hh:mm
        String[] hrmin = t.getText().toString().split(":");
        details.mTimeStartedReading = Long.parseLong(hrmin[0]) * 60 + Long.parseLong(hrmin[1]);
        //long start =  Long.parseLong(hrmin[0]);
        //
        //
        t = dialog.getDialog().findViewById(R.id.dialog_details_time_end);//hh:mm
        hrmin = t.getText().toString().split(":");
        // Convert each hour into 60 min and to the minutes.
        details.mTimeStopedReading = Long.parseLong(hrmin[0]) * 60 + Long.parseLong(hrmin[1]);
        //long stoped =  Long.parseLong(hrmin[0]);
        //long total = stoped - start;

        details.mTimeSpentReading = details.mTimeStopedReading - details.mTimeStartedReading;
        //details.mKey =

        //
        t = dialog.getDialog().findViewById(R.id.dialog_details_pages_completed);
        details.pagesCompleted = Integer.parseInt(t.getText().toString());
        Log.v("Dialog clicked",details.mDay + "Day of reading");
        mBookFragment.addDetails(details);


    }

    @Override
    public void onDialogNegativeClick(AddBookDetailsDialog dialog) {

    }
}
