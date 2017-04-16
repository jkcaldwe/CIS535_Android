package edu.umich.kvmyrick.getfit;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

@SuppressWarnings("unused")
public class HistoryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);


    final Button bNewexercise = (Button) findViewById(R.id.bNewexercise);


        bNewexercise.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent UserAreaActivityIntent = new Intent(HistoryActivity.this, NewExerciseActivity.class);
                HistoryActivity.this.startActivity(UserAreaActivityIntent);

        }
    });
    }
}
