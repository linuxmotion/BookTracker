package org.thelinuxmotion.apps.booktracker.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import androidx.annotation.NonNull;

import com.squareup.picasso.Picasso;

import org.thelinuxmotion.apps.booktracker.Isbndb.models.Book;
import org.thelinuxmotion.apps.booktracker.R;

import java.util.List;

/**
 * Created by jweyr on 1/31/2018.
 */

public class BookAdapter extends ArrayAdapter<Book> {

   // ArrayList<Book> mBookList;

    public BookAdapter(Context context, List<Book> books) {

        super(context, R.layout.bookshelf_adapter_layout, books);
    }


    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {

        ImageView imageView;
        if (convertView == null) {
            // if it's not recycled, initialize some attributes
            imageView = (ImageView) LayoutInflater.
                    from(this.getContext()).inflate(R.layout.bookshelf_adapter_layout, null);
            imageView.setLayoutParams(new GridView.LayoutParams(300, 400));

        } else {
            imageView = (ImageView) convertView;
        }

        //imageView.setImageResource(R.drawable.book_generic_213x);
        Picasso.get().load(this.getItem(position).mImage).into(imageView);
        return imageView;
    }

    public void add(List<Book> objects) {

        for (Book obj: objects) {
            add(obj);
        }
    }
}
