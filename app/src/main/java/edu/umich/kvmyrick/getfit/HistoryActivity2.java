package edu.umich.kvmyrick.getfit;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.w3c.dom.Text;

public class HistoryActivity2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history2);

        final EditText hist2mainText = (EditText) findViewById(R.id.hist2mainText);
        final TextView histWeightText = (TextView) findViewById(R.id.HistWeightText);
        final TextView histRepsText = (TextView) findViewById(R.id.HistRepsText);
        final TextView histSetsText = (TextView) findViewById(R.id.HistSetsText);
        final Button backButton = (Button) findViewById(R.id.hist2Back);
        final Button deleteButton = (Button) findViewById(R.id.hist2Delete);

        hist2mainText.setEnabled(false);
        hist2mainText.setText(getIntent().getStringExtra("EXTRA_EXERCISE_Name") + " completed on " + getIntent().getIntExtra("EXTRA_MONTH", 0) + "/" + getIntent().getIntExtra("EXTRA_DAY", 0) + "/" + getIntent().getIntExtra("EXTRA_YEAR", 0));

        histWeightText.setText(String.valueOf(getIntent().getIntExtra("EXTRA_WEIGHT", 0)));
        histRepsText.setText(String.valueOf(getIntent().getIntExtra("EXTRA_REPS", 0)));
        histSetsText.setText(String.valueOf(getIntent().getIntExtra("EXTRA_SETS", 0)));

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent History1Intent = new Intent(HistoryActivity2.this, HistoryActivity.class);
                History1Intent.putExtra("EXTRA_FIRST_NAME", getIntent().getStringExtra("EXTRA_FIRST_NAME"));
                History1Intent.putExtra("EXTRA_UID", getIntent().getStringExtra("EXTRA_UID"));
                History1Intent.putExtra("EXTRA_DAY", getIntent().getIntExtra("EXTRA_DAY", 0));
                History1Intent.putExtra("EXTRA_MONTH", getIntent().getIntExtra("EXTRA_MONTH", 0));
                History1Intent.putExtra("EXTRA_YEAR", getIntent().getIntExtra("EXTRA_YEAR", 0));
                HistoryActivity2.this.startActivity(History1Intent);
            }
        });
    }
}
