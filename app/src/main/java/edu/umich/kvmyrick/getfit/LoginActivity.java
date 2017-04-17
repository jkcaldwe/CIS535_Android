package edu.umich.kvmyrick.getfit;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.Response;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.ExecutionException;


@SuppressWarnings("unused")
public class LoginActivity extends AppCompatActivity {
    @SuppressWarnings("unused")
    private static final String[] DUMMY_CREDENTIALS = new String[]{
            "foo@example.com:hello", "bar@example.com:world"
    };

    @SuppressWarnings("unused")
    private LoginActivity mAuthTask = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        final EditText etUsername = (EditText) findViewById(R.id.etuserID);
        final EditText etPassword = (EditText) findViewById(R.id.etpassword);
        final EditText loginWarning = (EditText) findViewById(R.id.loginWarning);
        final Button bLogin = (Button) findViewById(R.id.bLogin);
        final TextView registerLink = (TextView) findViewById(R.id.tvRegisterhere);
        final Button bInfo = (Button) findViewById(R.id.bInfo);


        {
            bInfo.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    startActivity(new Intent(LoginActivity.this, InfoActivity.class));
                }
            });
            registerLink.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent registerIntent = new Intent(LoginActivity.this, RegisterActivity.class);
                    LoginActivity.this.startActivity(registerIntent);
                }
            });

            bLogin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View v) {
                    PHPInterface php = new PHPInterface();
                    php.execute("userLogIn", etUsername.getText(), etPassword.getText());
                    Object obj = null;
                    boolean success = false;
                        try {
                            obj = php.get();
                            if (obj.toString()=="fail") {
                                loginWarning.setText("Failed to connect to server.");
                                loginWarning.setEnabled(false);
                                loginWarning.setVisibility(View.VISIBLE);
                                return;
                            }
                            JSONObject jsonResponse = null;
                                try {
                                    jsonResponse = new JSONObject(obj.toString());
                                    success = jsonResponse.getBoolean("success");
                                    if (success) {
                                        Log.d("LogIn Result", "true");
                                        Intent UserAreaActivityIntent = new Intent(LoginActivity.this, UserAreaActivity.class);
                                        UserAreaActivityIntent.putExtra("EXTRA_FIRST_NAME", jsonResponse.getString("FirstName"));
                                        UserAreaActivityIntent.putExtra("EXTRA_UID", jsonResponse.getString("UserID"));
                                        LoginActivity.this.startActivity(UserAreaActivityIntent);
                                    }
                                    else {
                                        Log.d("LogIn Result", "false");
                                        loginWarning.setText("Login Failed.  Please check your username and password and try again.");
                                        loginWarning.setEnabled(false);
                                        loginWarning.setVisibility(View.VISIBLE);
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
                    //Log.d("PHP Result", obj.toString());
                }
            });
        }
    }
}
















