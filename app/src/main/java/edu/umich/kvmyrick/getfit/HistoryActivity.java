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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        final Button backButton = (Button) findViewById(R.id.backButton);
        final Button exerciseDetails = (Button) findViewById(R.id.viewDetailsButton);
        final TextView introText = (TextView) findViewById(R.id.introText);
        final ListView listView = (ListView) findViewById(R.id.listView);
//        final ScrollView ScrollList = (ScrollView) findViewById(R.id.scrollView2);
//        final LinearLayout layout = (LinearLayout) findViewById(R.id.linearLayout);

        introText.setEnabled(false);
        introText.setText("Select exercise completed on " + getIntent().getIntExtra("EXTRA_MONTH", 0) + "/" + getIntent().getIntExtra("EXTRA_DAY", 0) + "/" + getIntent().getIntExtra("EXTRA_YEAR", 0) + " to see exercise details");

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
                JSONArray exercises = jsonResponse.getJSONArray("activity_info");
                Log.d("Array Length", String.valueOf(exercises.length()));
//                if (success) {
//                    Log.d("sucess Result", "true");
//                } else {
//                    Log.d("sucess Result", "false");
//                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Log.d("PHP Result", obj.toString());

        ArrayList <String> myStringArray1 = new ArrayList<String>();
        myStringArray1.add("something");
        myStringArray1.add("next");
        ArrayAdapter<String> adaptor;
        adaptor = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, myStringArray1);
        listView.setAdapter(adaptor);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adaptor, View view, int position, long id) {
                Log.d("List Result", String.valueOf(position));
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
