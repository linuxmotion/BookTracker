package org.thelinuxmotion.apps.booktracker.adapters;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import org.thelinuxmotion.apps.booktracker.bookinfo.BookReadingDetails;

import java.util.List;

/**
 * Created by jweyr on 2/15/2018.
 */

public class HeatMapGridAdapter extends ArrayAdapter {

    static class HeatMapColors {
        static int BLUE = Color.BLUE;
        static int CYAN = Color.CYAN;
        static int GREEN = Color.GREEN;
        static int YELLOW = Color.YELLOW;
        static int RED = Color.RED;
    }


    public HeatMapGridAdapter(@NonNull Context context, int resource) {
        super(context, resource);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ImageView imageView;
        if (convertView == null) {
            // if it's not recycled, initialize some attributes
            imageView = new ImageView(parent.getContext());
            imageView.setLayoutParams(new GridView.LayoutParams(50, 50));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setPadding(8, 8, 8, 16);
            imageView.setFocusable(false);
        } else {
            imageView = (ImageView) convertView;
        }

        imageView.setBackgroundColor(HeatMapColors.BLUE);
        return imageView;

    }

    public void add(List<BookReadingDetails> objects) {
        for (BookReadingDetails obj: objects) {
            add(obj);
        }

    }


}
