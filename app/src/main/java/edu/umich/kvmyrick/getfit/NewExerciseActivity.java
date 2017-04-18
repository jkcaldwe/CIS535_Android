package edu.umich.kvmyrick.getfit;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.ExecutionException;

@SuppressWarnings("unused")
public class NewExerciseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_exercise);

        final TextView title = (TextView) findViewById(R.id.titleView);
        final Spinner ExerciseDropDown = (Spinner) findViewById(R.id.exerciseDropDown);
        final TextView WeightText = (TextView) findViewById(R.id.weightText);
        final TextView RepsText = (TextView) findViewById(R.id.RepsText);
        final TextView SetsText = (TextView) findViewById(R.id.SetsText);
        final EditText messageText = (EditText) findViewById(R.id.messageText);
        final Button submitButton = (Button) findViewById(R.id.submit);
        final Button backButton = (Button) findViewById(R.id.back);

        title.setText("Enter new exercise for " + getIntent().getIntExtra("EXTRA_MONTH", 0) + "/" + getIntent().getIntExtra("EXTRA_DAY", 0) + "/" + getIntent().getIntExtra("EXTRA_YEAR", 0));
        String[] ExerciseArray = new String[]{"Bench Press", "Shoulder Press", "Bicep Curl", "Triceps Extension", "Squat", "Leg Press", "Leg Curl", "Leg Extension"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, ExerciseArray);
        ExerciseDropDown.setAdapter(adapter);
        //ExerciseDropDown

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Log.d("DropDown Index:", String.valueOf(ExerciseDropDown.getSelectedItemPosition()));
                //Log.d("UserID:", getIntent().getStringExtra("EXTRA_UID"));
                String exerciseID = "-1";
                switch (ExerciseDropDown.getSelectedItemPosition()) {
                    case 0: exerciseID = "1"; break;
                    case 1: exerciseID = "2"; break;
                    case 2: exerciseID = "3"; break;
                    case 3: exerciseID = "6"; break;
                    case 4: exerciseID = "9"; break;
                    case 5: exerciseID = "10"; break;
                    case 6: exerciseID = "11"; break;
                    case 7: exerciseID = "12"; break;
                    default: exerciseID = "1"; break;
                }

                //Call using: php.execute("logActivity ", "userID", "exerciseID", "Date(YYYY-MM-DD)", "Weight", "Reps", "Sets");
                if (WeightText.getText() != null && RepsText.getText() != null) {
                    String date = getIntent().getIntExtra("EXTRA_YEAR", 0) + "-" + getIntent().getIntExtra("EXTRA_MONTH", 0) + "-" + getIntent().getIntExtra("EXTRA_DAY", 0);
                    PHPInterface php = new PHPInterface();
                    php.execute("logActivity", getIntent().getStringExtra("EXTRA_UID"), exerciseID, date, WeightText.getText(), RepsText.getText(), SetsText.getText());
                    Object obj = null;
                    boolean success = false;
                    try {
                        obj = php.get();
                        JSONObject jsonResponse = null;
                        try {
                            jsonResponse = new JSONObject(obj.toString());
                            success = jsonResponse.getBoolean("success");
                            if (success) {
                                messageText.setEnabled(false);
                                messageText.setVisibility(View.VISIBLE);
                                messageText.setText("Exercise entered successfully.  You may enter another or move back to the main screen.");
                            } else {
                                messageText.setEnabled(false);
                                messageText.setVisibility(View.VISIBLE);
                                messageText.setText("Exercise entry failed.  Please make sure you have entered values for each entry.");
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    //Log.d("PHP Result", obj.toString());
                }
                else {
                    messageText.setEnabled(false);
                    messageText.setVisibility(View.VISIBLE);
                    messageText.setText("Exercise entry failed.  Please make sure you have entered values for each entry.");
                }
            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent UserAreaIntent = new Intent(NewExerciseActivity.this, UserAreaActivity.class);
                UserAreaIntent.putExtra("EXTRA_FIRST_NAME", getIntent().getStringExtra("EXTRA_FIRST_NAME"));
                UserAreaIntent.putExtra("EXTRA_UID", getIntent().getStringExtra("EXTRA_UID"));
                NewExerciseActivity.this.startActivity(UserAreaIntent);
            }
        });
    }
}
