package org.thelinuxmotion.apps.booktracker;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.thelinuxmotion.apps.booktracker.persistence.AppDataBase;

/**
 * Created by jweyr on 2/8/2018.
 */

public class BookFragment extends Fragment {


    public static final String ARG_ISBN = "isbn";
    String mIsbn;

    private AppDataBase mDb;

    public BookFragment() {

    }
    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param isbn Isbn of the book in the database.
     * @return A new instance of fragment Book.
     */
    public static BookFragment newInstance(String isbn) {
        BookFragment fragment = new BookFragment();
        Bundle args = new Bundle();
        args.putString(ARG_ISBN, isbn);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Get the isbn to query the DB with
        mIsbn = getArguments().getString(ARG_ISBN);

        // Open the databse or create its
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
