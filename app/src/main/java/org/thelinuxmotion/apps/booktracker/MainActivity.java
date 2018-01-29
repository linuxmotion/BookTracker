package org.thelinuxmotion.apps.booktracker;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements BookShelfFragment.OnFragmentInteractionListener, AddBookDialogFragment.OnAddBookDialogListener {
    BookShelfFragment mBookShelf;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mBookShelf = (BookShelfFragment) getSupportFragmentManager().findFragmentById(R.id.bookShelf);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        //getSupportActionBar().hide();
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public void onDialogPositiveClick(AddBookDialogFragment dialog) {

        boolean validISBN = ISBN.isValidISBN(dialog.getISBN().getText().toString());
        Context context = this.getApplicationContext();
        int duration = Toast.LENGTH_SHORT;
        Toast toast;
        mBookShelf.setIsValidISBN(validISBN);

        if (validISBN) {
            CharSequence text = "Valid ISBN";
            toast = Toast.makeText(context, text, duration);

        } else {
            CharSequence text = "Invalid ISBN";
            toast = Toast.makeText(context, text, duration);
        }
        toast.show();

    }


    @Override
    public void onDialogNegativeClick(AddBookDialogFragment dialog) {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.appbarmenu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_add:
                Toast.makeText(this, "Add book from here", Toast.LENGTH_LONG).show();
                break;
            default:
                break;
        }
        return true;
    }
}