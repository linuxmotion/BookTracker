package org.linuxmotion.libraries.calendarlibrary;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;;

import androidx.appcompat.widget.LinearLayoutCompat;
import java.text.DateFormatSymbols;
import java.util.Calendar;

public class TLMCalendarView extends LinearLayoutCompat {

    private static int[] mDayIds;
    private Calendar mCalendar;
    View mMainView;
    LinearLayout[] mWeeks;
    private LayoutInflater mInflater;
    private ArrayAdapter mViewAdapter;

    public TLMCalendarView(Context context) {
        super(context, null,0);
    }

    public TLMCalendarView(Context context, AttributeSet attributes) {

        this(context, attributes,0);
    }

    public TLMCalendarView(Context context, AttributeSet attributes, int defstyleattr) {
        super(context, attributes,defstyleattr);

        mCalendar = Calendar.getInstance();

        mViewAdapter = new ArrayAdapter(context,R.layout.tlmcalendarday);

        initView(context);
        initDayIds();

    }

    private void initDayIds() {
        mDayIds = new int[7];
        mDayIds[0] = R.id.day1;
        mDayIds[1] = R.id.day2;
        mDayIds[2] = R.id.day3;
        mDayIds[3] = R.id.day4;
        mDayIds[4] = R.id.day5;
        mDayIds[5] = R.id.day6;
        mDayIds[6] = R.id.day7;
    }

    public void initView(Context context){

        mInflater =
                (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        mWeeks = new LinearLayout[6];

        if (mInflater != null){
            mMainView = mInflater.inflate(R.layout.tlmcalendarview,this);
        }





    }

    @Override
    protected void onFinishInflate(){
        super.onFinishInflate();

        mWeeks[0] = mMainView.findViewById(R.id.calendar_week_1);
        mWeeks[1] = mMainView.findViewById(R.id.calendar_week_2);
        mWeeks[2] = mMainView.findViewById(R.id.calendar_week_3);
        mWeeks[3] = mMainView.findViewById(R.id.calendar_week_4);
        mWeeks[4] = mMainView.findViewById(R.id.calendar_week_5);
        mWeeks[5] = mMainView.findViewById(R.id.calendar_week_6);


        // Inflate all the weeks
        for (int i = 0; i < 6; i++)
            mInflater.inflate(R.layout.tlmcalendarweek,mWeeks[i],true);

        initCalendarDays(0);

        Button previous = mMainView.findViewById(R.id.button_previous);
        previous.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                initCalendarDays(-1);
            }
        });
        Button next = mMainView.findViewById(R.id.button_next);
        next.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                initCalendarDays(1);
            }
        });


    }

    int mCurrentMonth = 0;
    private void initCalendarDays(int current) {


        mCurrentMonth += current;

        // Set the current month
        mCalendar.set(Calendar.MONTH, mCurrentMonth);
        mCurrentMonth = mCalendar.get(Calendar.MONTH);

        TextView t = ((TextView)mMainView.findViewById(R.id.text_view_month_name));
        t.setText(getMonth(mCurrentMonth));

        // Get the number of days in the current month
        int num_days = mCalendar.getActualMaximum(Calendar.DAY_OF_MONTH);

        while (mCalendar.get(Calendar.DATE) > 1) {
            mCalendar.add(Calendar.DATE, -1); // Substract 1 day until first day of month.
        }
        mCalendar.add(Calendar.DATE,-1);
        int last_months_num_days = mCalendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        //  Get which day the month starts
        int first_day_offset = mCalendar.get(Calendar.DAY_OF_WEEK);

        if (first_day_offset == 7)
            first_day_offset = 0;

        for (int i = 0 ; i < first_day_offset; i++ ) {
            LinearLayout day = mWeeks[0].findViewById(mDayIds[i]);
            ((TextView)day.findViewById(R.id.textView)).setText(""+(last_months_num_days-first_day_offset+i+1));
            ((TextView)day.findViewById(R.id.textView2)).setVisibility(INVISIBLE);
        }
        // For each week
        boolean used_offset = false;
        int day_number = 1;
        int next_month_day = 1;
        for(int i = 0; i < 6; i++){

            // for each day
            for(int j = 0; j < 7; j++){

                if(!used_offset && (i == 0)){
                    j = first_day_offset;
                    used_offset = true;
                }
                LinearLayout day = mWeeks[i].findViewById(mDayIds[j]);
                TextView header = day.findViewById(R.id.textView);
                TextView body = day.findViewById(R.id.textView2);
                // Get the day numbered j for the week numbered i
                if(day_number > num_days){
                    header.setText(""+ next_month_day++);
                    body.setVisibility(INVISIBLE);
                }
                else {
                    // Set the day number
                    header.setText("" + day_number);
                    body.setVisibility(VISIBLE);
                }

                day_number++;
            }

        }
    }

    public void setAllDaysBackground(int color){
        for(int i = 0; i < 6; i++){
            // for each day
            for(int j = 0; j < 7; j++){
                mWeeks[i].findViewById(mDayIds[j]).setBackgroundColor(color);
            }
        }
        // may need to invalidate view
    }

    public void setDayBackground(int day, int color){

        int week_num = (day/7);
        int day_of_week = day%7;// or is it 7-(day%7)

        mWeeks[week_num].findViewById(mDayIds[day_of_week]).setBackgroundColor(color);

        // May need to invalidate view
    }

    private String getMonth(int month) {

        String[] months = DateFormatSymbols.getInstance().getMonths();
        return months[month];
    }

}
