package edu.umich.kvmyrick.getfit;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import static edu.umich.kvmyrick.getfit.R.id.bNewExercise;
import static edu.umich.kvmyrick.getfit.R.id.bNewexercise;

@SuppressWarnings("unused")
public class UserAreaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_area);

        final EditText etUsername = (EditText) findViewById(R.id.etuserID);
        final EditText etAge = (EditText) findViewById(R.id.etage);
        final TextView WelcomeMsg = (TextView) findViewById(R.id.tvWelcomeMsg);
        final Button bHistory = (Button) findViewById(R.id.bHistory);
        final Button bNewExercise = (Button) findViewById(bNewexercise);
    }

    public void HistoryActivity(View view) {
        Intent intent = new Intent(this, HistoryActivity.class);
        startActivity(intent);
    }

    public void NewExerciseActivity(View view) {
        Intent intent = new Intent(this, NewExerciseActivity.class);
        startActivity(intent);


    }
}























