package edu.umich.kvmyrick.getfit;
import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;


public class PHPInterface extends AsyncTask {

    @SuppressWarnings("unused")
    private String url_test = "http://cis535-android-jkcaldwe.c9users.io/main.php?method=userLogIn";
    private String result = "";

    public PHPInterface(){
        //this.url_test = url;
    }

    @Override
    protected String doInBackground(Object[] params) { //
        //Main variables
        String link = "http://cis535-android-jkcaldwe.c9users.io/main.php"; //path to server
        String data = "";

        //First string will determine the method used in PHP
        Object method = params[0];
        //If the method is the userLogIn method - working
        //Call using: php.execute("userLogIn", "userID", "Password");
        if (method.toString() == "userLogIn") {
            link += "?method=" + method;
            String userID = params[1].toString();
            String password = params[2].toString();
            try {
                data  = URLEncoder.encode("UserID", "UTF-8") + "=" +
                        URLEncoder.encode(userID, "UTF-8");
                data += "&" + URLEncoder.encode("Password", "UTF-8") + "=" +
                        URLEncoder.encode(password, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        //if the method is UniqueUserID
        //call using: php.execute("UniqueUserID", "userID");
        else if(method.toString() == "UniqueUserID") {
            link += "?method=" + method;
            String userID = params[1].toString();
            try {
                data  = URLEncoder.encode("UserID", "UTF-8") + "=" +
                        URLEncoder.encode(userID, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        //if the method is RegisterUser - working
        //Call using: php.execute("RegisterUser", "userID", "FirstName", "LastName", "EMail", "Age", "Weight", "Password");
        else if(method.toString() == "RegisterUser") {
            link += "?method=" + method;
            String userID = params[1].toString();
            String fName = params[2].toString();
            String lName = params[3].toString();
            String eMail = params[4].toString();
            String age = params[5].toString();
            String weight = params[6].toString();
            String password = params[7].toString();
            try {
                data  = URLEncoder.encode("UserID", "UTF-8") + "=" +
                        URLEncoder.encode(userID, "UTF-8");
                data += "&" + URLEncoder.encode("FirstName", "UTF-8") + "=" +
                        URLEncoder.encode(fName, "UTF-8");
                data += "&" + URLEncoder.encode("LastName", "UTF-8") + "=" +
                        URLEncoder.encode(lName, "UTF-8");
                data += "&" + URLEncoder.encode("EMail", "UTF-8") + "=" +
                        URLEncoder.encode(eMail, "UTF-8");
                data += "&" + URLEncoder.encode("Age", "UTF-8") + "=" +
                        URLEncoder.encode(age, "UTF-8");
                data += "&" + URLEncoder.encode("Weight", "UTF-8") + "=" +
                        URLEncoder.encode(weight, "UTF-8");
                data += "&" + URLEncoder.encode("Password", "UTF-8") + "=" +
                        URLEncoder.encode(password, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        //if method is logActivity - working
        //Call using: php.execute("logActivity ", "userID", "exerciseID", "Date(YYYY-MM-DD)", "Weight", "Reps", "Sets");
        else if(method.toString() == "logActivity") {
            link += "?method=" + method;
            String userID = params[1].toString();
            String exerciseID = params[2].toString();
            String date = params[3].toString();
            String weight = params[4].toString();
            String reps = params[5].toString();
            String sets = params[6].toString();

            try {
                data  = URLEncoder.encode("UserID", "UTF-8") + "=" +
                        URLEncoder.encode(userID, "UTF-8");
                data += "&" + URLEncoder.encode("Exercise", "UTF-8") + "=" +
                        URLEncoder.encode(exerciseID, "UTF-8");
                data += "&" + URLEncoder.encode("Date", "UTF-8") + "=" +
                        URLEncoder.encode(date, "UTF-8");
                data += "&" + URLEncoder.encode("Weight", "UTF-8") + "=" +
                        URLEncoder.encode(weight, "UTF-8");
                data += "&" + URLEncoder.encode("Reps", "UTF-8") + "=" +
                        URLEncoder.encode(reps, "UTF-8");
                data += "&" + URLEncoder.encode("Sets", "UTF-8") + "=" +
                        URLEncoder.encode(sets, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        //when method is getActivityInfo - working
        //Call using: php.execute("getActivityInfo ", "userID", "Date(YYYY-MM-DD)");
        else if(method.toString() == "getActivityInfo") {
            link += "?method=" + method;
            String userID = params[1].toString();
            String date = params[2].toString();

            try {
                data  = URLEncoder.encode("UserID", "UTF-8") + "=" +
                        URLEncoder.encode(userID, "UTF-8");
                data += "&" + URLEncoder.encode("Date", "UTF-8") + "=" +
                        URLEncoder.encode(date, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }

        //main method to send data to server and get response
        try {
            URL url = new URL(link);
            URLConnection conn = url.openConnection();
            //new stuff
            conn.setDoOutput(true);

            OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
            wr.write(data);
            wr.flush();

            //working
            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line = "", res = "";
            while ((line = reader.readLine()) != null)
            {
                res += line;
            }

            Log.d("Debug", "output: " + res);
            result = res;
        } catch (MalformedURLException e) {
            e.printStackTrace();
            result = "fail";
        } catch (IOException e) {
            e.printStackTrace();
            result = "fail";
        }
        done(result);
        return result;
    }

    @SuppressWarnings("unused")
    protected void onPostExecute(String result) {
        Log.d("result", result);
    }

    protected void done(String result)
    {

    }
}
