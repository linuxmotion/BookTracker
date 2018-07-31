package org.thelinuxmotion.apps.booktracker.fragments;

import android.arch.persistence.room.Room;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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
import java.util.Collections;
import java.util.Comparator;
import java.util.GregorianCalendar;
import java.util.List;

/**
 * Created by jweyr on 2/8/2018.
 */

public class BookDetailsFragment extends Fragment {


    private Book mBook;
    private HeatMapGridAdapter mHeatMapAdapter;

    private BookDetailsDatabase mDb;

    public BookDetailsFragment() {

    }
    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param bookData the data the book in the database in an intent.
     * @return A new instance of fragment Book.
     */
    public static BookDetailsFragment newInstance(Intent bookData) {
        BookDetailsFragment fragment = new BookDetailsFragment();
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

        // init heatmap
        InitializeHeatmapAdapter();


    }

    private void InitializeHeatmapAdapter() {
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
        final List<BookReadingDetails> details = mDb.bookDetailsDao().getAll();
        Collections.sort(details, new Comparator<BookReadingDetails>() {
            @Override
            public int compare(BookReadingDetails details1, BookReadingDetails details2) {

                if (details1.mDay == details2.mDay)
                    return 0;
                if(details1.mDay < details2.mDay)
                    return -1;
                else
                    return 1 ;
            }
        });

        // Find all book details from the same day and update the book entry for the adapter
        // do this for all the book entries in the current month
        // loop through the entire details list
        for(int i = 0,j = 1; i < details.size(); i++){
            BookReadingDetails b = details.get(i);// get the details for each pos
            if(b.mDay > j) // if the current entries day is greater than the last entry
                j++; // we have gone to the next day

            adapterDays.get(j-1).pagesCompleted += b.pagesCompleted;
            adapterDays.get(j-1).mTimeSpentReading += b.mTimeSpentReading;
        }

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
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable final Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        // Inflate the view
        View v = inflater.inflate(R.layout.book_details_fragment,container,false);
        //Set all the view's information
        {
            // Sets the book name
            TextView bookname = v.findViewById(R.id.display_book_name);
            bookname.setText(mBook.mBookTitle);

            // Set the isbn
            TextView isbn = v.findViewById(R.id.display_isbn);
            isbn.setText(mBook.getIsbn_13());

            //Set the pages completed and total
            TextView pages = v.findViewById(R.id.display_pages);
            // Create the string for the numerical pages
            String pagesText = mBook.mPagesCompleted + "/" + mBook.getTotalPages();
            pages.setText(pagesText);

            // Set the gridview adapter
            GridView map = v.findViewById(R.id.heatmapgrid);
            map.setAdapter(mHeatMapAdapter);

            //Set the click handler for the entry button
            Button addEntry = v.findViewById(R.id.add_reading_entry);
            addEntry.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                   AddBookDetailsDialog dialog = AddBookDetailsDialog.newInstance();
                   dialog.show(getFragmentManager(),"Details Dialog");

                }
            });

            /*map.setOnItemClickListener(new GridView.OnItemClickListener(){
;

                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                }
            });*/

            setProgressBar(v);

        }



        return v;
    }

    private void setProgressBar(View v) {
        // Get a reference to the progress bar
        ProgressBar progress = v.findViewById(R.id.book_completion_progress_bar);

        // Parse the pages completed and total
        int comp = 0;
        int tot = 1;// fallback values
        try{
            comp = Integer.parseInt(mBook.mPagesCompleted);
            tot = Integer.parseInt(mBook.mTotalPages);
        }
        catch(NumberFormatException e){
            Log.e("Progress bar","Could not parse the pages");
            Log.e("Progress bar",e.getMessage());

        }

        // map the range [0,1] to the interval [0,100]
        int t = comp/tot;
        int progressNum = (100)*(t);
        // We should find the total amount of pages read, the total amount of pages
        // and interpolate into a range from 0 to 100
        progress.setProgress(progressNum); // set to 50 to see the bar
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    public void addDetails(BookReadingDetails details) {

            details.mIsbn = Calendar.getInstance().getTime().toString();
            // add the details to the database
            mDb.bookDetailsDao().add(details);
            // update the adapter
            updateAdapter(details);
            // We also need to update the UI that we have completed more pages

            // Create the string for the numerical pages
           // String pagesText = mBook.mPagesCompleted + "/" + mBook.getTotalPages();
            TextView t = this.getView().findViewById(R.id.display_pages);
            //t.setText(pagesText);




    }

    // Update the gridview adapater so that it reflects when new information
    // is added
    private void updateAdapter(BookReadingDetails details) {

        int day = details.mDay;
        BookReadingDetails book = mHeatMapAdapter.getItem(day-1);
        mHeatMapAdapter.remove(book);
        book.mTimeSpentReading += details.mTimeSpentReading;
        book.pagesCompleted += details.pagesCompleted;
        mHeatMapAdapter.insert(book,day-1);
        // Update the UI now
        mHeatMapAdapter.notifyDataSetChanged();


    }
}
