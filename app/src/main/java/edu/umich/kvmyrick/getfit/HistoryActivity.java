package edu.umich.kvmyrick.getfit;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.ListViewCompat;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

@SuppressWarnings("unused")
public class HistoryActivity extends AppCompatActivity {

    ArrayList <String> exerciseNameList = new ArrayList<String>();
    JSONArray exercises;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        final Button backButton = (Button) findViewById(R.id.backButton);
        final TextView introText = (TextView) findViewById(R.id.introText);
        final ListView listView = (ListView) findViewById(R.id.listView);
        final EditText histWarnText = (EditText) findViewById(R.id.histWarnText);

        histWarnText.setVisibility(View.INVISIBLE);
        introText.setEnabled(false);
        introText.setText("Select an exercise completed on " + getIntent().getIntExtra("EXTRA_MONTH", 0) + "/" + getIntent().getIntExtra("EXTRA_DAY", 0) + "/" + getIntent().getIntExtra("EXTRA_YEAR", 0) + " to see details");

        //php.execute("getActivityInfo ", "userID", "Date(YYYY-MM-DD)");
        String date = getIntent().getIntExtra("EXTRA_YEAR", 0) + "-" + getIntent().getIntExtra("EXTRA_MONTH", 0) + "-" + getIntent().getIntExtra("EXTRA_DAY", 0);
        PHPInterface php = new PHPInterface();
        php.execute("getActivityInfo", getIntent().getStringExtra("EXTRA_UID"), date);
        Object obj = null;
        boolean success = false;
        try {
            obj = php.get();
            JSONObject jsonResponse = null;
            try {
                jsonResponse = new JSONObject(obj.toString());
                exercises = jsonResponse.getJSONArray("activity_info");
                Log.d("Array Length", String.valueOf(exercises.length()));
                if (exercises.length() !=0){
                    for(int i=0; i < exercises.length(); ++i) {
                        JSONObject tempObj = exercises.getJSONObject(i);
                        exerciseNameList.add(tempObj.getString("ExerciseName"));
                    }
                }
                else {
                    listView.setVisibility(View.INVISIBLE);
                    histWarnText.setEnabled(false);
                    histWarnText.setVisibility(View.VISIBLE);
                    histWarnText.setText("There are no activities entered for this date.  Please go back and enter an activity for this date.");
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Log.d("PHP Result", obj.toString());

        ArrayAdapter<String> adaptor;
        adaptor = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, exerciseNameList);
        listView.setAdapter(adaptor);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adaptor, View view, int position, long id) {
                Log.d("List Result", String.valueOf(position));
                try {
                    JSONObject tempObj = exercises.getJSONObject(position);
                    Intent History2Intent = new Intent(HistoryActivity.this, HistoryActivity2.class);
                    History2Intent.putExtra("EXTRA_FIRST_NAME", getIntent().getStringExtra("EXTRA_FIRST_NAME"));
                    History2Intent.putExtra("EXTRA_UID", getIntent().getStringExtra("EXTRA_UID"));
                    History2Intent.putExtra("EXTRA_DAY", getIntent().getIntExtra("EXTRA_DAY", 0));
                    History2Intent.putExtra("EXTRA_MONTH", getIntent().getIntExtra("EXTRA_MONTH", 0));
                    History2Intent.putExtra("EXTRA_YEAR", getIntent().getIntExtra("EXTRA_YEAR", 0));
                    History2Intent.putExtra("EXTRA_WEIGHT", tempObj.getInt("Weight"));
                    History2Intent.putExtra("EXTRA_REPS", tempObj.getInt("Reps"));
                    History2Intent.putExtra("EXTRA_SETS", tempObj.getInt("Sets"));
                    History2Intent.putExtra("EXTRA_EXERCISE_ID", tempObj.getInt("ExerciseID"));
                    History2Intent.putExtra("EXTRA_EXERCISE_Name", tempObj.getString("ExerciseName"));
                    HistoryActivity.this.startActivity(History2Intent);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });


        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent UserAreaActivityIntent = new Intent(HistoryActivity.this, UserAreaActivity.class);
                UserAreaActivityIntent.putExtra("EXTRA_FIRST_NAME", getIntent().getStringExtra("EXTRA_FIRST_NAME"));
                UserAreaActivityIntent.putExtra("EXTRA_UID", getIntent().getStringExtra("EXTRA_UID"));
                HistoryActivity.this.startActivity(UserAreaActivityIntent);
            }
        });
    }
}
