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
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.thelinuxmotion.apps.booktracker.Isbndb.models.Book;
import org.thelinuxmotion.apps.booktracker.R;
import org.thelinuxmotion.apps.booktracker.adapters.HeatMapGridAdapter;
import org.thelinuxmotion.apps.booktracker.bookinfo.BookReadingDetails;
import org.thelinuxmotion.apps.booktracker.persistence.BookDetailsDatabase;

import java.text.DateFormatSymbols;
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
    private ArrayList<BookReadingDetails> mAdapterDays;
    private BookDetailsDatabase mDb;
    // Maintain a reference to the month so that we can change it later on in the case
    // of viewing data not from the current month
    private TextView mMonth;

    public BookDetailsFragment() {

    }

    public void PreInitialize(Intent book) {

        // Intitalize the book
        mBook = new Book();

        // Now get the argument and set the as the book paramaters
        mBook.fromIntent(book);
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
        fragment.PreInitialize(bookData);

        return fragment;
    }

    @NonNull
    private ArrayList<BookReadingDetails> getDefaultAdapter() {
        Calendar now = GregorianCalendar.getInstance();
        int daysMonth = now.getActualMaximum(Calendar.DAY_OF_MONTH);
        Log.v(daysMonth + "", "Get Default Adapter");
        ArrayList<BookReadingDetails> adapterDays = new ArrayList<>();
        //Create each day in the months for the adapter
        for (int i = 1; i <= daysMonth; i++) {
            // Use the isbn for the book as a deafult value for the key
            // This is not saved in the database and is the key so that
            // we can check if the key is the isbn and take the appropriate action
            BookReadingDetails readingDetails = new BookReadingDetails(Long.parseLong(mBook.mISBN));


            readingDetails.mDay = i; // set each entries day
            readingDetails.mDateTime = 0;  // These are all just default
            readingDetails.mTimeStartedReading = 0;
            readingDetails.mTimeStopedReading = 0;
            readingDetails.pagesCompleted = 0;
            adapterDays.add(readingDetails);

        }
        Log.v("default values set :" + adapterDays.size(), "Get Default Adapter");
        return adapterDays;
    }

    public Book getBook() {
        return mBook;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // init heatmap
        InitializeHeatmapAdapter();


    }

    private void InitializeHeatmapAdapter() {
        mHeatMapAdapter = new HeatMapGridAdapter(this.getContext(), 0);
        mAdapterDays = getDefaultAdapter();

        // Pretty much everything below should be on a separate Non UI thread, with a UI callback to
        // update
        // Open the databse or create it for the adapater
        // Eventually we only want entries that are in the current month by default instead
        // of all the reading entries
        mDb = Room.databaseBuilder(getContext(),
                BookDetailsDatabase.class, mBook.mISBN).allowMainThreadQueries().build();
        // We now have the list of book details
        final List<BookReadingDetails> details = mDb.bookDetailsDao().getAll();
        Log.v(details.size() + "", "Initialize Heatmap details doa size");
        Collections.sort(details, new Comparator<BookReadingDetails>() {
            @Override
            public int compare(BookReadingDetails details1, BookReadingDetails details2) {


                //noinspection UseCompareMethod
                if (details1.mDay == details2.mDay)
                    return 0;
                if (details1.mDay < details2.mDay)
                    return -1;
                else
                    return 1;
            }
        });
        Log.v(details.size() + "", "Size after sort");


        // Find all book details from the same day and update the book entry for the adapter
        // do this for all the book entries in the current month
        // loop through the entire details list
        for (int i = 0, j = 1; i < details.size(); i++) {
            Log.v(i + "", "Iteration #");
            BookReadingDetails b = details.get(i);// get the details for each pos
            Log.v(b.mDay + "", "Current day");
            int pos = b.mDay - 1;

            BookReadingDetails bd = mAdapterDays.get(pos);
            bd.pagesCompleted += b.pagesCompleted;
            bd.mTimeSpentReading += b.mTimeSpentReading;

            mAdapterDays.set(pos, bd);
            mBook.mPagesCompleted = "" + (b.pagesCompleted + Integer.parseInt(mBook.mPagesCompleted));// Set the global UI pages completed
        }

        // if we hae multiple book from the dame day add all the entries up and give a grad total
        // for that day, fine grain on touch will be implemented later
        Log.v(mAdapterDays.size() + "", "Initilize Heatmap");

        mHeatMapAdapter.add(mAdapterDays);


    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable final Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        // Inflate the view
        View v = inflater.inflate(R.layout.book_details_fragment, container, false);
        //Set all the view's information
        {
            ImageView cover = v.findViewById(R.id.book_image);
            Picasso.get().load(mBook.mImage).into(cover);
            // Sets the book name
            TextView bookname = v.findViewById(R.id.display_book_name);
            bookname.setText(mBook.mBookTitle);

            // Set the isbn
            TextView isbn = v.findViewById(R.id.display_isbn);
            isbn.setText(mBook.mIsbn_13);


            // Set the gridview adapter
            GridView map = v.findViewById(R.id.heatmapgrid);
            map.setAdapter(mHeatMapAdapter);

            //Set the click handler for the entry button
            Button addEntry = v.findViewById(R.id.add_reading_entry);
            addEntry.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AddBookDetailsDialog dialog = AddBookDetailsDialog.newInstance();
                    dialog.show(getFragmentManager(), "Details Dialog");

                }
            });


            mMonth = v.findViewById(R.id.monthtext);
            int month = Calendar.getInstance().get(Calendar.MONTH);
            mMonth.setText(getMonth(month));

            //Set the pages completed and total
            TextView pages = v.findViewById(R.id.display_pages);
            // Create the string for the numerical pages
            ProgressBar pbar = v.findViewById(R.id.book_completion_progress_bar);
            try {
                int total = Integer.parseInt(mBook.mNumPages);
                int progress = Integer.parseInt(mBook.mPagesCompleted);

                // We should check to see if there is more completed pages than pages
                // that are able to be read, until then we just make that we have at least
                // one page that we can read

                if (total > 0) // we need to have more than no pages
                {
                    String pagesText = mBook.mPagesCompleted + "/" + mBook.mNumPages;
                    pages.setText(pagesText);
                    setProgressBar(pbar, progress, total); // we only set the progress bar if we have two valid
                    // integers. One for the progress and another for the total.
                }
            } catch (NumberFormatException e) {

                pbar.setVisibility(View.INVISIBLE);
                pages.setVisibility(View.INVISIBLE);
                // don't set the view
            }


        }


        return v;
    }

    private String getMonth(int month) {

        String[] months = DateFormatSymbols.getInstance().getMonths();
        return months[month];
    }

    private void setProgressBar(ProgressBar pbar, int progress, int total) {

        // map the range [0,1] to the interval [0,100]
        float t = (progress) / (float) (total);
        Float progressNum = (100) * (t);
        // We should find the total amount of pages read, the total amount of pages
        // and interpolate into a range from 0 to 100
        pbar.setProgress(progressNum.intValue()); // set the found integer percentage
    }

    /**
     * Add the details to the database for the current book
     * The method will automatically set the key
     * for the details even if it was set. The key is the next size, ie. size + 1
     *
     * @param details The book details to add
     */
    public void addDetails(BookReadingDetails details) {

        //details.mKey = Calendar.getInstance().getTime().getTime();
        // add the details to the database

        Log.v(details.mKey + "", "Add Details");
        Log.v(details.mDay + "", "Add Details");
        Log.v(details.mDateTime + "", "Add Details");
        Log.v(details.mTimeStartedReading + "", "Add Details");
        Log.v(details.mTimeStopedReading + "", "Add Details");
        Log.v(details.pagesCompleted + "", "Add Details");

        //TODO: Assign a key that will always be unique
        //in this case removing and entry and adding another one will assign the same key twice possibly
        //
        int size = mDb.bookDetailsDao().getAll().size();
        details.mKey = size + 1;

        mDb.bookDetailsDao().add(details);
        // update the adapter
        updateAdapter(details);
        // We also need to update the UI that we have completed more pages

    }

    // Update the gridview adapater so that it reflects when new information
    // is added
    private void updateAdapter(BookReadingDetails details) {

        int day = details.mDay;

        BookReadingDetails book = mAdapterDays.get(day - 1);//mHeatMapAdapter.getItem(day-1);
        //mHeatMapAdapter.remove(book);
        book.mTimeSpentReading += details.mTimeSpentReading;
        book.pagesCompleted += details.pagesCompleted;
        mAdapterDays.set(day - 1, book);
        //mHeatMapAdapter.insert(book,day-1);


        int progress = (details.pagesCompleted + Integer.parseInt(mBook.mPagesCompleted));
        mBook.mPagesCompleted = "" + progress;// Set the global UI pages completed
        //mBook.
        // Update the UI now
        // Create the string for the numerical pages
        String pagesText = mBook.mPagesCompleted + "/" + mBook.mNumPages;
        TextView t = this.getView().findViewById(R.id.display_pages);
        if (t == null) {
            Log.e(this.getClass().getName(), "Textview is an empty object");
            Log.d("updateAdapter()", "Make sure that the inflated view has an object with id R.id.display_pages");

            return;
        }

        t.setText(pagesText);

        //mAdapterDays.notify();
        mHeatMapAdapter.notifyDataSetChanged();
        try {
            setProgressBar((ProgressBar) getView().findViewById(R.id.book_completion_progress_bar), progress, Integer.parseInt(mBook.mNumPages));
        } catch (NumberFormatException e) {

            Toast.makeText(this.getContext(),
                    "Could not set the progress bar on update, " +
                            "please contact me at linuxmotion@gmail.com",
                    Toast.LENGTH_SHORT).show();


        }

    }
}
