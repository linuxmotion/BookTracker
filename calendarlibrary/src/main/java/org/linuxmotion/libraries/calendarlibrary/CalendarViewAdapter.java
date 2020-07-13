package org.linuxmotion.libraries.calendarlibrary;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class CalendarViewAdapter extends ArrayAdapter {


    public CalendarViewAdapter(@NonNull Context context, int resource) {
        super(context, resource);
    }



    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        return super.getView(position, convertView, parent);
    }
}
