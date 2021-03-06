package edu.umich.kvmyrick.getfit;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.android.volley.Response;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.ExecutionException;

@SuppressWarnings("unused")
public class RegisterActivity extends AppCompatActivity {
    private Boolean UniqueID = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        final EditText etage = (EditText) findViewById(R.id.etage);
        final EditText etfName = (EditText) findViewById(R.id.etfName);
        final EditText etlName = (EditText) findViewById(R.id.etlName);
        final EditText etemail = (EditText) findViewById(R.id.etemail);
        final EditText etuserID = (EditText) findViewById(R.id.etuserID);
        final EditText etpassword = (EditText) findViewById(R.id.etpassword);
        final EditText etweight = (EditText) findViewById(R.id.etweight);
        final Button bRegister = (Button) findViewById(R.id.bRegister);
        final ImageView userID_Image = (ImageView) findViewById(R.id.UserID_Image);
        final EditText errorText = (EditText) findViewById(R.id.errorText);


        bRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PHPInterface php = new PHPInterface();
                //Call using: php.execute("RegisterUser", "userID", "FirstName", "LastName", "EMail", "Age", "Weight", "Password");
                if (!UniqueID){
                    Log.d("UniqueID", "false");
                    errorText.setText("This UserID is already taken. Please choose another.");
                    errorText.setEnabled(false);
                    errorText.setVisibility(View.VISIBLE);
                    return;
                }
                php.execute("RegisterUser", etuserID.getText(), etfName.getText(), etlName.getText(), etemail.getText(), etage.getText(), etweight.getText(), etpassword.getText());
                Object obj = null;
                boolean success = false;
                try {
                    obj = php.get();
                    JSONObject jsonResponse = null;
                    try {
                        jsonResponse = new JSONObject(obj.toString());
                        success = jsonResponse.getBoolean("success");
                        if (success) {
                            Log.d("Result1", "true");
                            Intent LogInIntent = new Intent(RegisterActivity.this, LoginActivity.class);
                            RegisterActivity.this.startActivity(LogInIntent);
                        }
                        else {
                            Log.d("Result1", "false");
                            errorText.setText("Please enter all information to register.");
                            errorText.setEnabled(false);
                            errorText.setVisibility(View.VISIBLE);
                        }
                    }
                    catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                catch (ExecutionException e) {
                    e.printStackTrace();
                }
                catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Log.d("Result1", obj.toString());

            }
        });

        etuserID.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { }

            @Override
            public void afterTextChanged(Editable s) {
                PHPInterface php = new PHPInterface();
                php.execute("UniqueUserID", s.toString());
                Object obj = null;
                try {
                    obj = php.get();
                    if (obj.toString().contains("true")) {
                        userID_Image.setImageResource(R.drawable.green_check);
                        UniqueID = true;
                    }
                    else {
                        userID_Image.setImageResource(R.drawable.red_x);
                        UniqueID = false;
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
            }
        });

    }

}



















