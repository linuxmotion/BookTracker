package org.thelinuxmotion.apps.booktracker.fragments;


import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;


import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import com.google.android.gms.common.api.CommonStatusCodes;

import org.thelinuxmotion.apps.booktracker.R;
import org.thelinuxmotion.apps.booktracker.barcodereader.BarcodeCaptureActivity;

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

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private EditText mISBN;

    private OnAddBookDialogListener mListener;


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


    @NonNull
    @Override
    public Dialog onCreateDialog(@NonNull Bundle savedInstanceState) {


        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Get the layout inflater
        LayoutInflater inflater = getActivity().getLayoutInflater();

        final View fields = inflater.inflate(R.layout.fragment_add_book_dialog, null);
        mISBN = fields.findViewById(R.id.editISBN);
        fields.findViewById(R.id.scan_isbn_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(getActivity(), org.thelinuxmotion.apps.booktracker.barcodereader.BarcodeCameraActivity.class);
                startActivityForResult(intent, CAMERA_ISBN);
            }
        });

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


    private static int CAMERA_ISBN = 0;


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == CAMERA_ISBN) {
            // do some stuff with the retrning class being the camera sibn
            if ( (resultCode == CommonStatusCodes.SUCCESS)   ) {
                // If we have and error or succes still show the barcode
                // the next screen will determine if is valid or not
                String barcode = data.getStringExtra(BarcodeCaptureActivity.BarcodeObject);
                mISBN.setText(barcode);

            }else if ((resultCode == CommonStatusCodes.ERROR)){

                // If data is null we probably pressed the back button
                if(data != null){
                     String barcode = data.getStringExtra(BarcodeCaptureActivity.BarcodeObject);
                     mISBN.setText(barcode);
                }

            }


        }


    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     */
    public interface OnAddBookDialogListener {

        void onDialogPositiveClick(AddBookDialogFragment dialog);

        void onDialogNegativeClick(AddBookDialogFragment dialog);

    }
}
