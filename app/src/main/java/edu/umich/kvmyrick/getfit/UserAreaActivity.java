package edu.umich.kvmyrick.getfit;

import android.content.Intent;
import android.icu.text.DateFormat;
import android.icu.text.SimpleDateFormat;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Calendar;
import java.util.Date;

import static edu.umich.kvmyrick.getfit.R.id.bNewExercise;
import static edu.umich.kvmyrick.getfit.R.id.bNewexercise;
import static edu.umich.kvmyrick.getfit.R.id.calendarView;

@SuppressWarnings("unused")
public class UserAreaActivity extends AppCompatActivity {
    int _day = 0;
    int _month = 0;
    int _year = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_area);

        final TextView WelcomeMsg = (TextView) findViewById(R.id.tvWelcomeMsg);
        final Button bHistory = (Button) findViewById(R.id.bHistory);
        final Button bNewExercise = (Button) findViewById(R.id.bNewExercise);
        final CalendarView calendar = (CalendarView) findViewById(R.id.calendarView);
        final EditText dateText = (EditText) findViewById(R.id.dateText);
        final SimpleDateFormat df = new SimpleDateFormat("dd");
        final SimpleDateFormat mf = new SimpleDateFormat("MM");
        final SimpleDateFormat yf = new SimpleDateFormat("yyyy");
        _day = Integer.parseInt(df.format(calendar.getDate()));
        _month = Integer.parseInt(mf.format(calendar.getDate()));
        _year = Integer.parseInt(yf.format(calendar.getDate()));

        calendar.setMaxDate(System.currentTimeMillis());
        WelcomeMsg.setText("Welcome " + getIntent().getStringExtra("EXTRA_FIRST_NAME"));
        dateText.setEnabled(false);

        bHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Log.d("Day", String.valueOf(currentDay));
                Intent HistoryIntent = new Intent(UserAreaActivity.this, HistoryActivity.class);
                HistoryIntent.putExtra("EXTRA_DAY", _day);
                HistoryIntent.putExtra("EXTRA_MONTH", _month);
                HistoryIntent.putExtra("EXTRA_YEAR", _year);
                HistoryIntent.putExtra("EXTRA_FIRST_NAME", getIntent().getStringExtra("EXTRA_FIRST_NAME"));
                HistoryIntent.putExtra("EXTRA_UID", getIntent().getStringExtra("EXTRA_UID"));
                UserAreaActivity.this.startActivity(HistoryIntent);
            }
        });

        bNewExercise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Log.d("Date", String.valueOf(_day));
                Intent NewActivityIntent = new Intent(UserAreaActivity.this, NewExerciseActivity.class);
                NewActivityIntent.putExtra("EXTRA_DAY", _day);
                NewActivityIntent.putExtra("EXTRA_MONTH", _month);
                NewActivityIntent.putExtra("EXTRA_YEAR", _year);
                NewActivityIntent.putExtra("EXTRA_FIRST_NAME", getIntent().getStringExtra("EXTRA_FIRST_NAME"));
                NewActivityIntent.putExtra("EXTRA_UID", getIntent().getStringExtra("EXTRA_UID"));
                UserAreaActivity.this.startActivity(NewActivityIntent);
            }
        });

        calendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                _day = dayOfMonth;
                _year = year;
                _month = month+1;
            }
        });
    }


}























