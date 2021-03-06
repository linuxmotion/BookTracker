package org.thelinuxmotion.apps.booktracker.fragments;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import org.thelinuxmotion.apps.booktracker.R;

import java.util.Calendar;

/**
 * Created by jweyr on 2/19/2018.
 */

public class AddBookDetailsDialog extends DialogFragment
        implements  DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener{

    private AddBookReadingDialogListener mListener;
    private EditText mDate;
    private EditText mTimeStart;

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

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {


        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Get the layout inflater
        LayoutInflater inflater = getActivity().getLayoutInflater();

        final View layout = inflater.inflate(R.layout.reading_details_dialog_fragment_layout, null);

        mDate = layout.findViewById(R.id.dialog_details_date);
        mDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // We should create a datepicker dialog that return the date
                Calendar c = Calendar.getInstance();
                DatePickerDialog dialog
                        = new DatePickerDialog(AddBookDetailsDialog.this.getContext(),
                        AddBookDetailsDialog.this,
                        c.get(Calendar.YEAR),
                        c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH));
                dialog.show();

            }
        });

        mTimeStart = layout.findViewById(R.id.dialog_details_time_start);
        mTimeStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar c = Calendar.getInstance();
                TimePickerDialog dialog
                        = new TimePickerDialog(AddBookDetailsDialog.this.getContext(),
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                mTimeStart.setText(hourOfDay + ":" + minute);
                            }
                        },
                        c.get(Calendar.HOUR),
                        c.get(Calendar.MINUTE),
                        false);
                dialog.show();
            }
        });


        final EditText end_time = layout.findViewById(R.id.dialog_details_time_end);
        end_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar c = Calendar.getInstance();
                TimePickerDialog dialog
                        = new TimePickerDialog(AddBookDetailsDialog.this.getContext(),
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                end_time.setText(hourOfDay + ":" + minute);
                            }
                        },
                        c.get(Calendar.HOUR),
                        c.get(Calendar.MINUTE),
                        false);
                dialog.show();
            }
        });


        EditText pages = layout.findViewById(R.id.dialog_details_pages_completed);


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


    @Override
    public void onDateSet(DatePicker datePicker, int year, int month, int day) {

        mDate.setText(month+ "-"+day + "-" + year);
    }

    @Override
    public void onTimeSet(TimePicker timePicker, int hour, int minute) {

        mTimeStart.setText(hour + ":" + minute + "");

    }


    public interface AddBookReadingDialogListener{


        void onDialogPositiveClick(AddBookDetailsDialog dialog);

        void onDialogNegativeClick(AddBookDetailsDialog dialog);


    }
}
