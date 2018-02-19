package org.thelinuxmotion.apps.booktracker;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import org.thelinuxmotion.apps.booktracker.bookinfo.Book;
import org.thelinuxmotion.apps.booktracker.fragments.BookFragment;

/**
 * Created by jweyr on 2/8/2018.
 */

public class BookActivity extends AppCompatActivity
{

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.book_activity_layout);
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            Book b = new Book();
            b.fromIntent(getIntent());
            // Intialize the book adapater
            BookFragment fragment = BookFragment.newInstance(getIntent());
            fragmentTransaction.replace(R.id.book_activity_fragment, fragment);
            fragmentTransaction.commit();

            Toolbar myToolbar = findViewById(R.id.my_toolbar);
            setSupportActionBar(myToolbar);
            // Get a support ActionBar corresponding to this toolbar
            ActionBar ab = getSupportActionBar();

            // Enable the Up button
            ab.setDisplayHomeAsUpEnabled(true);


        }

}
