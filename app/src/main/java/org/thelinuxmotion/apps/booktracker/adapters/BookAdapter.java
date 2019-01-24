package org.thelinuxmotion.apps.booktracker.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import org.thelinuxmotion.apps.booktracker.Isbndb.models.Book;
import org.thelinuxmotion.apps.booktracker.R;

import java.util.List;

/**
 * Created by jweyr on 1/31/2018.
 */

public class BookAdapter extends ArrayAdapter<Book> {

   // ArrayList<Book> mBookList;

    public BookAdapter(Context context, List<Book> books) {

        super(context, R.layout.fragment_add_book_dialog, books);
    }


    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {

        ImageView imageView;
        if (convertView == null) {
            // if it's not recycled, initialize some attributes
            imageView = new ImageView(parent.getContext());
            imageView.setLayoutParams(new GridView.LayoutParams(213, 300));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setPadding(8, 8, 8, 16);
            imageView.setFocusable(false);
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
