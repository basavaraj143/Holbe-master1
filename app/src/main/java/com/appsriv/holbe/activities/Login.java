package com.appsriv.holbe.activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.appsriv.holbe.Config;
import com.appsriv.holbe.OnTaskCompleted;
import com.appsriv.holbe.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;

public class Login extends Activity implements OnTaskCompleted {

    ProgressDialog pd;
    EditText email;
    EditText password;
    Config config;
    String emailId,pwd;
    public static HashMap<String,String> details = new HashMap<>();

    private static final String PREFS_NAME = "preferences";
    private static final String PREF_UNAME = "Username";
    private static final String PREF_PASSWORD = "Password";

    private final String DefaultUnameValue = "";
    private String UnameValue;

    private final String DefaultPasswordValue = "";
    private String PasswordValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        TextView txt=(TextView)findViewById(R.id.forgot);
        String styledText = "<u>FORGOT PASSWORD?</u>";
        txt.setText(Html.fromHtml(styledText), TextView.BufferType.SPANNABLE);

        email=(EditText) findViewById(R.id.email);
        password=(EditText) findViewById(R.id.password);
        Button login =(Button)findViewById(R.id.login);

        login.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
               emailId=email.getText().toString();
               pwd =password.getText().toString();
              if (email.getText().toString().isEmpty()||password.getText().toString().isEmpty())
              {
                  Toast.makeText(Login.this,"Please Enter Valid Credentials ", Toast.LENGTH_LONG).show();
              }
                else if (!Splash.isValidEmaillId(emailId))
              {
                  Toast.makeText(Login.this,"Please Enter Valid Email ", Toast.LENGTH_LONG).show();
              }
                else
              {
                  final String url = "http://192.185.26.69/~holbe/api/patient/login.php?emailaddress="+email.getText().toString()+"&"+"password="+password.getText().toString();
                  new AsyncHttpTask().execute(url);
              }
            }
        });
    }
    @Override
    public void onPause()
    {
        super.onPause();
        savePreferences();

    }
    @Override
    public void onResume() {
        super.onResume();
        loadPreferences();
    }
    private void savePreferences() {
        SharedPreferences settings = getSharedPreferences(PREFS_NAME,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();

        // Edit and commit
        UnameValue = email.getText().toString();
        PasswordValue = password.getText().toString();
        System.out.println("onPause save name: " + UnameValue);
        System.out.println("onPause save password: " + PasswordValue);
        editor.putString(PREF_UNAME, UnameValue);
        editor.putString(PREF_PASSWORD, PasswordValue);
        editor.commit();
    }

    private void loadPreferences(){

        SharedPreferences settings = getSharedPreferences(PREFS_NAME,
                Context.MODE_PRIVATE);

        // Get value
        UnameValue = settings.getString(PREF_UNAME, DefaultUnameValue);
        PasswordValue = settings.getString(PREF_PASSWORD, DefaultPasswordValue);
        email.setText(UnameValue);
        password.setText(PasswordValue);
        System.out.println("onResume load name: " + UnameValue);
        System.out.println("onResume load password: " + PasswordValue);
    }
    public void forgot(View v)
    {
        Intent i=new Intent(Login.this,ForgotPassword.class);
        startActivity(i);
    }
    public void alert(String title, String msg)
    {

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                Login.this);

        // set title
        alertDialogBuilder.setTitle(title);

        // set dialog message
        alertDialogBuilder
                .setMessage(msg)
                .setCancelable(false)
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // if this button is clicked, close
                        // current activity
                        //Donor.this.finish();
                        dialog.cancel();
                    }
                });


        // create alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();

        // show it
        alertDialog.show();
    }

    @Override
    public void onTaskCompleted(String stringi, String string)
    {
        pd.dismiss();
        if(string.equalsIgnoreCase("true"))
        {
            Toast.makeText(this,"Login Successfully", Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(this,"Invalid Login Details", Toast.LENGTH_SHORT).show();
        }
    }

   /* public void login(View v)
    {

        if(email.getText().toString().isEmpty())
        {
            alert("Alert","Please enter your email address");
        }
        else if(password.getText().toString().isEmpty()){
            alert("Alert","Please enter your password");
        }
        else
        {
        pd = new ProgressDialog(Login.this);
        pd.setMessage("Contacting to server...");
        pd.setCancelable(false);
        pd.show();

        HashMap<String, String> dat = new HashMap<String, String>();
        JSONObject data=new JSONObject();

        try
        {
            data.put("emailaddress", email.getText().toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try
        {
            data.put("password", password.getText().toString());
        } catch (JSONException e)
        {
            e.printStackTrace();
        }

        AsyncHttpGet asyncHttpGet = new AsyncHttpGet("login",this,dat);
            asyncHttpGet.data=data;
            asyncHttpGet.execute(config.login+"?emailaddress="+email.getText().toString()+"&password="+password.getText().toString());
    }
    }
*/



    class AsyncHttpTask extends AsyncTask<String, Void, Integer>
    {
        ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            progressDialog = ProgressDialog.show(Login.this, "Please wait!", "Loading...");
        }

        @Override
        protected Integer doInBackground(String... params) {
            //InputStream inputStream = null;
            Integer result = 0;
            HttpURLConnection urlConnection = null;

            try {

                URL url = new URL(params[0]);

                urlConnection = (HttpURLConnection) url.openConnection();


                urlConnection.setRequestMethod("GET");

                int statusCode = urlConnection.getResponseCode();


                if (statusCode == 200)
                {
                    BufferedReader r = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                    StringBuilder response = new StringBuilder();
                    String line;
                    while ((line = r.readLine()) != null) {
                        response.append(line);
                    }

                    String status = parseResult(response.toString());
                    if (status.equals("200"))
                    {
                        result = 1; // Successful
                    }
                    else
                    {
                        return 0;
                    }


                    //result = 1; // Successful
                } else
                {
                    result = 0; //"Failed to fetch data!";
                }

            } catch (Exception e)
            {
                // Log.d(TAG, e.getLocalizedMessage());
                e.printStackTrace();
            }

            return result; //"Failed to fetch data!";
        }

        @Override
        protected void onPostExecute(Integer result) {

            //setProgressBarIndeterminateVisibility(false);


            if (progressDialog!=null&&progressDialog.isShowing())
            {
                progressDialog.dismiss();
            }
            if (result == 1)
            {
                startActivity(new Intent(Login.this, LatestComingUp.class));

            }
            else
            {
                Toast.makeText(Login.this,"Please enter valid Email and Password", Toast.LENGTH_LONG).show();
            }

        }

        private String parseResult(String result)
        {
            String status=null;
            try {
                JSONObject object = new JSONObject(result);
                 status = object.getString("status");
                details = new HashMap<>();
                String userId = object.getJSONObject("0").getString("user_id");
                String userFirstName = object.getJSONObject("0").getString("user_first_name");
                String userCity = object.getJSONObject("0").getString("user_city");
                String userPhoneNo = object.getJSONObject("0").getString("user_phone_no");
                String userAddress = object.getJSONObject("0").getString("user_address");
                String userDob = object.getJSONObject("0").getString("user_dob");
                String userEmailAddress = object.getJSONObject("0").getString("user_email_address");
                String userLastName = object.getJSONObject("0").getString("user_last_name");
                String user_profile_picture = object.getJSONObject("0").getString("user_profile_picture");
                details.put("userId",userId);
                details.put("userFirstName",userFirstName);
                details.put("userCity",userCity);
                details.put("userPhoneNo",userPhoneNo);
                details.put("userAddress",userAddress);
                details.put("userDob",userDob);
                details.put("userEmailAddress",userEmailAddress);
                details.put("userLastName",userLastName);
                details.put("user_profile_picture",user_profile_picture);




            } catch (JSONException e) {
                e.printStackTrace();
            }
            return status;
        }
    }

}
