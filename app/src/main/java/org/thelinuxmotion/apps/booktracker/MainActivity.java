package org.thelinuxmotion.apps.booktracker;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final CheckBox validISBN = (CheckBox) findViewById(R.id.validationBox);
        final EditText enterISBN = (EditText) findViewById(R.id.editText);
        Button   cancel    = (Button)   findViewById(R.id.cancelButton);
        Button   enter     = (Button)   findViewById(R.id.enterButton);

        enter.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                String isbn = enterISBN.getText().toString();
                StringBuilder sb;
                ArrayList<Integer> aisbn = new ArrayList<>();
                for(int i = 0; i < isbn.length(); i++){
                    sb = new StringBuilder();
                    // convert to int
                    //String ch;
                    sb.append(isbn.charAt(i));
                    aisbn.add(Integer.parseInt(sb.toString()));
                }

                if(ISBN.checkISBN(aisbn)) {
                    validISBN.setChecked(true);
                }else{
                    validISBN.setChecked(false);
                }

                //
                //ISBN bookISBN = new ISBN(aisbn);
                //Book book = new Book(0,100, bookISBN);

            }
         }


        );

        //findViewById(R.id.);
        ArrayList<Integer> isbn = new ArrayList<>(10);
        Book oneBook = new Book(0,0,isbn);

    }


}
