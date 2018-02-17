package org.thelinuxmotion.apps.booktracker.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.thelinuxmotion.apps.booktracker.R;
import org.thelinuxmotion.apps.booktracker.adapters.HeatMapGridAdapter;
import org.thelinuxmotion.apps.booktracker.bookinfo.Book;
import org.thelinuxmotion.apps.booktracker.persistence.AppDataBase;

/**
 * Created by jweyr on 2/8/2018.
 */

public class BookFragment extends Fragment {


    private Book mBook;
    private HeatMapGridAdapter mHeatMapAdapater;

    private AppDataBase mDb;

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
        // Set the Isbn

        Intent t = new Intent();
        t.putExtras(getArguments());
        // Intitalize the book
        mBook = new Book();
        // Now get the argument and set the as the book paramaters
        mBook.fromIntent(t);

        mHeatMapAdapater = new HeatMapGridAdapter(this.getContext(), 0);

        // Open the databse or create it for the adapater
       // mDb = Room.databaseBuilder(getContext(),
       //         AppDataBase.class, mIsbn).allowMainThreadQueries().build();
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
        View v = inflater.inflate(R.layout.bookfragment,container,false);
        TextView isbn = v.findViewById(R.id.display_book_name);
        isbn.setText(mBook.getIsbn_13());

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
