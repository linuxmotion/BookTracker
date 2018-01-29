package org.thelinuxmotion.apps.booktracker;


import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import java.util.ArrayList;

/*
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link AddBookDialogFragment.OnAddBookDialogListener} interface
 * to handle interaction events.
 * Use the {@link AddBookDialogFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddBookDialogFragment extends DialogFragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private ArrayList<Book> mBooksList;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnAddBookDialogListener mListener;

    private EditText mEnterISBN;


    public AddBookDialogFragment() {
        mBooksList = new ArrayList<>();
        // Required empty public constructor
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
    public static AddBookDialogFragment newInstance(String param1, String param2) {
        AddBookDialogFragment fragment = new AddBookDialogFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {


        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Get the layout inflater
        LayoutInflater inflater = getActivity().getLayoutInflater();

        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        builder.setView(inflater.inflate(R.layout.fragment_add_book_dialog, null))
                // Add action buttons
                .setPositiveButton(R.string.enter, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        mListener.onDialogPositiveClick(AddBookDialogFragment.this);
                        // AddBookDialogFragment.this.getDialog().dismiss();
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        mListener.onDialogNegativeClick(AddBookDialogFragment.this);
                        //AddBookDialogFragment.this.getDialog().cancel();
                    }
                });

        return builder.create();


    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_add_book_dialog, container, false);

        // mValidISBN = (CheckBox) v.findViewById(R.id.validationBox);
        mEnterISBN = (EditText) v.findViewById(R.id.editText);
        // mCancelButton   = (Button)   v.findViewById(R.id.cancelButton);
        // mEnterButton     = (Button)   v.findViewById(R.id.enterButton);
        // mEnterButton.setOnClickListener(new EnterOnclickListener());

        return v;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnAddBookDialogListener) {
            mListener = (OnAddBookDialogListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnAddBookDialogListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public EditText getISBN() {
        return mEnterISBN;
    }

    public ArrayList<Book> getmBooksList() {
        return mBooksList;
    }

    public void setBooksList(ArrayList<Book> mBooksList) {
        this.mBooksList = mBooksList;
    }

    public void addBooktoList(ISBN isbn) {

        this.mBooksList.add(new Book(0, 0, isbn));
    }

    public void addBooktoList(Book book) {
        this.mBooksList.add(book);
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     */
    public interface OnAddBookDialogListener {
        // TODO: Update argument type and name

        void onDialogPositiveClick(AddBookDialogFragment dialog);

        void onDialogNegativeClick(AddBookDialogFragment dialog);

    }
}
