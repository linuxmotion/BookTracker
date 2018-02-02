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

/**
 * A dialog to prompt the user to enter an the isbn of a book, the
 * number of pages completed, and the number of pages that the book contains.
 * It will then pass the data to the {@link BookShelfFragment} through
 * the main activity if the user selects to enter the isbn.
 * <p>
 * <p>
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

    //private ArrayList<Book> mBooksList;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnAddBookDialogListener mListener;

    private EditText mEnterISBN;
    private EditText mEnterPagesCompleted;
    private EditText mEnterPagesTotal;


    public AddBookDialogFragment() {
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


        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Get the layout inflater
        LayoutInflater inflater = getActivity().getLayoutInflater();

        final View fields = inflater.inflate(R.layout.fragment_add_book_dialog, null);
        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        builder.setView(fields)
                // Add action buttons
                .setPositiveButton(R.string.enter, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        mListener.onDialogPositiveClick(AddBookDialogFragment.this);
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        mListener.onDialogNegativeClick(AddBookDialogFragment.this);
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


        mEnterISBN = (EditText) v.findViewById(R.id.editISBN);
        mEnterPagesCompleted = (EditText) v.findViewById(R.id.editPagesCompleted);
        mEnterPagesTotal = (EditText) v.findViewById(R.id.editPagesTotal);
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

    public static String getISBN(AddBookDialogFragment d) {
        return ((EditText) d.getView().findViewById(R.id.editISBN)).getText().toString();
    }

    public static String getEnterPagesCompleted(Dialog d) {
        return ((EditText) d.findViewById(R.id.editPagesCompleted)).getText().toString();
    }

    public static String getEnterPagesTotal(Dialog d) {
        return ((EditText) d.findViewById(R.id.editPagesTotal)).getText().toString();
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
