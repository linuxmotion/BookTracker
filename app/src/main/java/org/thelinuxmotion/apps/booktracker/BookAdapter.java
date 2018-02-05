package org.thelinuxmotion.apps.booktracker;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import java.util.List;

/**
 * Created by jweyr on 1/31/2018.
 */

public class BookAdapter extends ArrayAdapter<Book> {

   // ArrayList<Book> mBookList;

    public BookAdapter(Context context, List books) {
        this(context, R.id.editISBN, books);
    }

    public BookAdapter(Context context, int resource, List books) {
        super(context, resource, books);
    }




    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView;
        if (convertView == null) {
            // if it's not recycled, initialize some attributes
            imageView = new ImageView(parent.getContext());
            imageView.setLayoutParams(new GridView.LayoutParams(213, 300));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setPadding(8, 8, 8, 16);
        } else {
            imageView = (ImageView) convertView;
        }

        imageView.setImageResource(R.drawable.book_generic_213x);
        return imageView;
    }





    public void add(List<Book> objects) {
        for (Book obj: objects) {
            add(obj);
        }

    }
}
