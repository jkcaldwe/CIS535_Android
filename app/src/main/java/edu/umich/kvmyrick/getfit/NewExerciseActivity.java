package edu.umich.kvmyrick.getfit;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

@SuppressWarnings("unused")
public class NewExerciseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_exercise);

        final TextView title = (TextView) findViewById(R.id.titleView);

        title.setText("Enter new exercise for " + getIntent().getIntExtra("EXTRA_MONTH", 0) + "/" + getIntent().getIntExtra("EXTRA_DAY", 0) + "/" + getIntent().getIntExtra("EXTRA_YEAR", 0));
    }
}
