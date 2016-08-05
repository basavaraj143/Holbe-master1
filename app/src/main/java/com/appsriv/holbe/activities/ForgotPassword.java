package com.appsriv.holbe.activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.Toast;

import com.appsriv.holbe.Config;
import com.appsriv.holbe.OnTaskCompleted;
import com.appsriv.holbe.R;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class ForgotPassword extends Activity implements OnTaskCompleted {

    ProgressDialog pd;
    EditText email;
    Config config;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forgot);

        email=(EditText) findViewById(R.id.email);
    }
    public void alert(String title, String msg){

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                ForgotPassword.this);

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
    public void onTaskCompleted(String stringi, String string) {
        pd.dismiss();
        if(string.equalsIgnoreCase("true"))
        {
            Toast.makeText(this,"Your password has been sent on your email id", Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(this,"Email id not exist in our database", Toast.LENGTH_SHORT).show();
        }
    }

    public void submit(View v)
    {

        if(email.getText().toString().isEmpty())
        {
            alert("Alert","Please enter your email address");
        }
        else
        {
      //  pd = new ProgressDialog(ForgotPassword.this);
       /// pd.setMessage("Contacting to server...");
      //  pd.setCancelable(false);
      //  pd.show();

    //    HashMap<String, String> dat = new HashMap<String, String>();
      //  JSONObject data=new JSONObject();

       // try {
       //     data.put("emailaddress", email.getText().toString());
      ///  } catch (JSONException e) {
        //    e.printStackTrace();
      //  }

     /*   AsyncHttpGet asyncHttpGet = new AsyncHttpGet("login",this,dat);
            asyncHttpGet.data=data;
            asyncHttpGet.execute(config.forgot+"?emailaddress="+email.getText().toString());*/
            final String url = " http://192.185.26.69/~holbe/api/patient/forgotpassword.php?user_email_address="+email.getText().toString();
            new AsyncHttpTask().execute(url);
    }
    }


    class AsyncHttpTask extends AsyncTask<String, Void, Integer>
    {
        ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            progressDialog = ProgressDialog.show(ForgotPassword.this, "Please wait!", "Loading...");
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

                        result = 1; // Successful
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

                Toast.makeText(ForgotPassword.this,"Password has sent Your Registered Email", Toast.LENGTH_LONG).show();
            }
            else
            {

            }

        }

        private String parseResult(String result)
        {



            return result;
        }
    }

}
