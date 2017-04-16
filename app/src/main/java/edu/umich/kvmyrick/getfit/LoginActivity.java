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
                            JSONObject jsonResponse = null;
                                try {
                                    jsonResponse = new JSONObject(obj.toString());
                                    success = jsonResponse.getBoolean("success");
                                if (success) {
                                    Log.d("Result1", "true");
                                    Intent UserAreaActivityIntent = new Intent(LoginActivity.this, UserAreaActivity.class);
                                    LoginActivity.this.startActivity(UserAreaActivityIntent);
                                }
                                else {
                                    Log.d("Result1", "false");
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
                    Log.d("Result1", obj.toString());

                }
            });


            Response.Listener<String> responseListener = new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        JSONObject jsonResponse = new JSONObject(response);
                        boolean success = jsonResponse.getBoolean("success");

                        if (success) {
                            Intent UserAreaActivityIntent = new Intent(LoginActivity.this, UserAreaActivity.class);
                            LoginActivity.this.startActivity(UserAreaActivityIntent);
                        }
                        else {
                            AlertDialog alertDialog = new AlertDialog.Builder(LoginActivity.this).create();
                            alertDialog.setTitle("Login Failed");
                            alertDialog.setMessage("Username or Password is inccorect.");
                            alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "Retry",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.dismiss();
                                        }
                                    });
                            alertDialog.show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }

                ;
            };
        }
    }
}















