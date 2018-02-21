package org.thelinuxmotion.apps.booktracker.fragments;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import org.thelinuxmotion.apps.booktracker.BookDetailsActivity;
import org.thelinuxmotion.apps.booktracker.R;
import org.thelinuxmotion.apps.booktracker.adapters.BookAdapter;
import org.thelinuxmotion.apps.booktracker.bookinfo.Book;
import org.thelinuxmotion.apps.booktracker.persistence.AppDataBase;

import java.util.ArrayList;
import java.util.List;


/**
 * The main bookshelf. All book are loaded into the shelf to display
 * to the user. Receives the ISBN, completed pages number, and total
 * page number. Should validation a ISBN before intitating
 * a query to a ISBN database and adding the new book into the bookshelf.
 * <p>
 * Activities that contain this fragment must implement the
 * {@link BookShelfFragment.OnBookShelfInteractionListener} interface
 * to handle interaction events.
 * Use the {@link BookShelfFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BookShelfFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnBookShelfInteractionListener mListener;

    private GridView mBookShelfGrid;
    private BookAdapter mBookAdapter;

    private AppDataBase mDb;
    private BookClickedListener mBookClickedListener;

    public class BookClickedListener implements AdapterView.OnItemClickListener {


        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

            Intent intent = new Intent(adapterView.getContext().getApplicationContext(), BookDetailsActivity.class);
            //EditText editText = (EditText) findViewById(R.id.editText);
           // String message = editText.getText().toString();
            Book b = mBookAdapter.getItem(i);

            intent.putExtras(b.toIntent());

            startActivity(intent);


        }

    }

    public BookShelfFragment() {
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment BookShelf.
     */
    // TODO: Rename and change types and number of parameters
    public static BookShelfFragment newInstance(String param1, String param2) {
        BookShelfFragment fragment = new BookShelfFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }


        //TODO: Move DB access to a separate thread
        // Open the database
        mDb = Room.databaseBuilder(this.getActivity().getApplicationContext(),
                AppDataBase.class, "main_bookshelf").allowMainThreadQueries().build();
        // get all entries
        List<Book> entries = mDb.bookDao().getAll();
        ArrayList<Book> shelf = new ArrayList<>(entries.size());

        // Turn each DB object into the adapter object
        for (Book entry : entries) {
            shelf.add(entry);
        }
        // If this is the first time create the adapter
        if (mBookAdapter == null)
            mBookAdapter = new BookAdapter(this.getActivity().getApplicationContext(), shelf);
        // create the shelf
        //mBookAdapter.add(shelf);
        if (mBookClickedListener == null)
            mBookClickedListener = new BookClickedListener();



    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_book_shelf, container, false);
        mBookShelfGrid = v.findViewById(R.id.book_shelf_grid);
        mBookShelfGrid.setFocusable(false);
        mBookShelfGrid.setAdapter(mBookAdapter);
        mBookShelfGrid.setFocusableInTouchMode(false);
        mBookShelfGrid.setClickable(true);


        mBookShelfGrid.setOnItemClickListener(mBookClickedListener);


        return v;
    }


    @Override
    public void onResume() {
        super.onResume();

        // If we were not restarted load from the database


    }

    @Override
    public void onPause() {
        super.onPause();
        //TODO: Save the books to a persistent storage to save the shelf state

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnBookShelfInteractionListener) {
            mListener = (OnBookShelfInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public void addBooktoShelf(Book book) {
        Log.v("addBookToShelf", "Adding book to the list");
        //TODO: Move saving into a separate non UI thread
        mDb.bookDao().add(book);
        mBookAdapter.add(book);
    }


    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnBookShelfInteractionListener {
        // TODO: Update argument type and name
        void OnBookShelfInteraction(Uri uri);
    }
}
