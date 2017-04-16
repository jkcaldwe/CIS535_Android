package edu.umich.kvmyrick.getfit;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.TextView;

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

        WelcomeMsg.setText("Welcome " + getIntent().getStringExtra("EXTRA_FIRST_NAME"));

        bHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Log.d("Date", String.valueOf(_day));
//                Intent UserAreaActivityIntent = new Intent(LoginActivity.this, UserAreaActivity.class);
//                UserAreaActivityIntent.putExtra("EXTRA_FIRST_NAME", jsonResponse.getString("FirstName"));
//                LoginActivity.this.startActivity(UserAreaActivityIntent);

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

                UserAreaActivity.this.startActivity(NewActivityIntent);
            }
        });

        calendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                _day = dayOfMonth;
                _year = year;
                _month = month;
            }
        });
    }


}























