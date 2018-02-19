package org.thelinuxmotion.apps.booktracker.fragments;

import android.arch.persistence.room.Room;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.thelinuxmotion.apps.booktracker.R;
import org.thelinuxmotion.apps.booktracker.adapters.HeatMapGridAdapter;
import org.thelinuxmotion.apps.booktracker.bookinfo.Book;
import org.thelinuxmotion.apps.booktracker.bookinfo.BookReadingDetails;
import org.thelinuxmotion.apps.booktracker.persistence.BookDetailsDatabase;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

/**
 * Created by jweyr on 2/8/2018.
 */

public class BookFragment extends Fragment {


    private Book mBook;
    private HeatMapGridAdapter mHeatMapAdapter;

    private BookDetailsDatabase mDb;

    public BookFragment() {

    }
    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param bookData the data the book in the database in an intent.
     * @return A new instance of fragment Book.
     */
    public static BookFragment newInstance(Intent bookData) {
        BookFragment fragment = new BookFragment();
        fragment.setArguments(bookData.getExtras());
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent t = new Intent();
        t.putExtras(getArguments());
        // Intitalize the book
        mBook = new Book();
        // Now get the argument and set the as the book paramaters
        mBook.fromIntent(t);

        mHeatMapAdapter = new HeatMapGridAdapter(this.getContext(), 0);
        ArrayList<BookReadingDetails> adapterDays = getDefaultAdapter();
        // Add a couple of test values
        adapterDays.get(2).pagesCompleted = 5;
        adapterDays.get(2).mTimeSpentReading = 12;

        adapterDays.get(5).pagesCompleted = 10;
        adapterDays.get(5).mTimeSpentReading = 25;

        adapterDays.get(7).pagesCompleted = 12;
        adapterDays.get(7).mTimeSpentReading = 30;

        // Pretty much everything below should be on a separate Non UI thread, with a UI callback to
        // update
        // Open the databse or create it for the adapater
        // Eventually we only want entries that are in the current month by default instead
        // of all the reading entries
         mDb = Room.databaseBuilder(getContext(),
                 BookDetailsDatabase.class, mBook.mISBN).allowMainThreadQueries().build();
        // We now have the list of book details
        List<BookReadingDetails> details = mDb.bookDetailsDao().getAll();
        // Find all book details from the same day and update the book entry for the adapter
        // do this for all the book entries in the current month
        // if we hae multiple book from the dame day add all the entries up and give a grad total
        // for that day, fine grain on touch will be implemented later

        mHeatMapAdapter.add(adapterDays);


    }

    @NonNull
    private static ArrayList<BookReadingDetails> getDefaultAdapter() {
        Calendar now = GregorianCalendar.getInstance();
        int daysMonth = now.getActualMaximum(Calendar.DAY_OF_MONTH);
        ArrayList<BookReadingDetails> adapterDays = new ArrayList<>();
        //Create each day in the months for the adapter
        for (int i = 1; i <= daysMonth; i++){
            BookReadingDetails readingDetails = new BookReadingDetails();
            readingDetails.mDay = i; // set each entries day
            readingDetails.mDateTime = 0;  // These are all just default
            readingDetails.mTimeSpentReading = 0;
            readingDetails.pagesCompleted = 0;
            adapterDays.add(readingDetails);

        }
        return adapterDays;
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        // Inflate the view
        View v = inflater.inflate(R.layout.bookfragment,container,false);
        //Set all the view's information
        {
            TextView bookname = v.findViewById(R.id.display_book_name);
            bookname.setText(mBook.getIsbn_13());

            TextView isbn = v.findViewById(R.id.display_isbn);
            isbn.setText(mBook.getIsbn_13());

            TextView pages = v.findViewById(R.id.display_pages);
            isbn.setText( "0/" +mBook.getTotalPages());

            GridView map = v.findViewById(R.id.heatmapgrid);
            map.setAdapter(mHeatMapAdapter);

            ProgressBar progress = v.findViewById(R.id.book_completion_progress_bar);
            // We should find the total amount of pages read, the total amount of pages
            // and interpolate into a range from 0 to 100
            progress.setProgress(50); // set to 50 to see the bar

        }




        return v;
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
    }
}
