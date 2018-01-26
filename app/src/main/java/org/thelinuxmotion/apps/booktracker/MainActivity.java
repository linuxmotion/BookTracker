package org.thelinuxmotion.apps.booktracker;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ArrayList<Integer> isbn = new ArrayList<>(10);
        isbn.add(0);
        isbn.add(1);
        isbn.add(9);
        isbn.add(8);
        isbn.add(7);
        isbn.add(3);
        isbn.add(9);
        isbn.add(8);
        isbn.add(3);
        isbn.add(4);
        Book oneBook = new Book(0,0,isbn);

    }


}
