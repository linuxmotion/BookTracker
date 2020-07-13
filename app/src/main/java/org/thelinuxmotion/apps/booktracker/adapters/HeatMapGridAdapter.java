package org.thelinuxmotion.apps.booktracker.adapters;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import org.thelinuxmotion.apps.booktracker.R;
import org.thelinuxmotion.apps.booktracker.bookinfo.BookReadingDetails;

import java.util.List;

/**
 * Created by jweyr on 2/15/2018.
 */

public class HeatMapGridAdapter extends ArrayAdapter<BookReadingDetails> {

    static class HeatMapColors {
        static final int BLUE = Color.BLUE;
        static final int CYAN = Color.CYAN;
        static final int GREEN = Color.GREEN;
        static final int YELLOW = Color.YELLOW;
        static final int RED = Color.RED;
    }


    public HeatMapGridAdapter(@NonNull Context context, int resource) {
        super(context, resource);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        TextView textView;
        if (convertView == null) {
            // if it's not recycled, initialize some attributes
            textView = (LayoutInflater.
                    from(this.getContext()).inflate(R.layout.heatmap_adapter_layout, null)).
                    findViewById(R.id.heatmap__grid_item);

        } else {
            textView = (TextView) convertView;
        }

        BookReadingDetails readingDetails = getItem(position);

        if (readingDetails != null){

            textView.setText("" + readingDetails.mDay);
            int color = Color.GREEN;
            long timespent = readingDetails.mTimeSpentReading;
            // We should actually interpolater from 0 to 60
            // with 30 being the green time
            //TODO: Add color interpolation/changing
            if (timespent >= 30)
                color = HeatMapColors.GREEN;
            else if (timespent >= 10)
                color = HeatMapColors.CYAN;
            else if (timespent > 0)
                color = HeatMapColors.YELLOW;
            else
                color = HeatMapColors.BLUE;

            textView.setBackground(new ColorDrawable(color));
        }


        return textView;

    }


    @Override
    public void add(@Nullable BookReadingDetails object) {
        super.add(object);

    }

    public void add(List<BookReadingDetails> objects) {
        for (BookReadingDetails obj: objects) {
            add(obj);
        }

    }


}
