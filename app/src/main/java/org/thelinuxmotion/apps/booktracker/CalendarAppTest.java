package org.thelinuxmotion.apps.booktracker;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

public class CalendarAppTest extends AppCompatActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("CalendarTest","Calendar test");
        setContentView(R.layout.app_calendar);


        return;
    }
}
