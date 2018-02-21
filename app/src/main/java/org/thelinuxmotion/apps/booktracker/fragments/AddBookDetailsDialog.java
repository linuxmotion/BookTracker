package org.thelinuxmotion.apps.booktracker.fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import org.thelinuxmotion.apps.booktracker.R;

/**
 * Created by jweyr on 2/19/2018.
 */

public class AddBookDetailsDialog extends DialogFragment {

    private AddBookReadingDialogListener mListener;

    public AddBookDetailsDialog() {
        super();
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment BookShelf.
     */
    // TODO: Rename and change types and number of parameters
    public static AddBookDetailsDialog newInstance() {
        AddBookDetailsDialog fragment = new AddBookDetailsDialog();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onAttach(Context context) {

        super.onAttach(context);
        if (context instanceof AddBookDetailsDialog.AddBookReadingDialogListener) {
            mListener = (AddBookReadingDialogListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement " + AddBookDetailsDialog.AddBookReadingDialogListener.class.getName());
        }
    }

    @Override
    public void onDetach() {

        super.onDetach();
        mListener = null;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {


        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Get the layout inflater
        LayoutInflater inflater = getActivity().getLayoutInflater();

        final View layout = inflater.inflate(R.layout.reading_details_dialog_fragment_layout, null);
        EditText date = layout.findViewById(R.id.dialog_details_date);
        EditText time = layout.findViewById(R.id.dialog_details_time);
        EditText pages = layout.findViewById(R.id.dialog_details_completed);
        EditText spent = layout.findViewById(R.id.dialog_details_time_spent);
        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        builder.setView(layout)
                // Add action buttons
                .setPositiveButton(R.string.enter, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        mListener.onDialogPositiveClick(AddBookDetailsDialog.this);
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        mListener.onDialogNegativeClick(AddBookDetailsDialog.this);
                    }
                });

        return builder.create();


    }



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       // View layout = inflater.inflate(R.layout.reading_details_dialog_fragment_layout,container,false);


        return super.onCreateView(inflater, container, savedInstanceState);
    }

    public interface AddBookReadingDialogListener{


        void onDialogPositiveClick(AddBookDetailsDialog dialog);

        void onDialogNegativeClick(AddBookDetailsDialog dialog);


    }
}
