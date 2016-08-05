package com.appsriv.holbe.activities;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.appsriv.holbe.Config;
import com.appsriv.holbe.R;
import com.appsriv.holbe.util.AndroidMultiPartEntity;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.content.ByteArrayBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class SignUpActivity extends Activity {

    EditText email,password,first_name,last_name;
    ImageView addPhoto;
    String emailId,pwd;
    String filePathFromApi="";
    String message;
    // Camera activity request codes
    private static final int CAMERA_CAPTURE_IMAGE_REQUEST_CODE = 100;
    private static final int CAMERA_CAPTURE_VIDEO_REQUEST_CODE = 200;
    public static final int MEDIA_TYPE_VIDEO = 2;

    public static final int MEDIA_TYPE_IMAGE = 1;
    private Uri fileUri; // file url to store image/video

    long totalSize = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        addPhoto = (ImageView)findViewById(R.id.logo);
        addPhoto.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // capture picture
                captureImage();
            }
        });
        // Checking camera availability
        if (!isDeviceSupportCamera())
        {
            Toast.makeText(getApplicationContext(), "Sorry! Your device doesn't support camera",
                    Toast.LENGTH_LONG).show();
            // will close the app if the device does't have camera
            finish();
        }







        TextView signin = (TextView)findViewById(R.id.signin);
        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignUpActivity.this,Login.class));
            }
        });

        email=(EditText) findViewById(R.id.email);
        password=(EditText) findViewById(R.id.password);
        first_name=(EditText) findViewById(R.id.first_name);
        last_name=(EditText) findViewById(R.id.last_name);
        Button login =(Button)findViewById(R.id.signup);

        //Sign up button click
        login.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                emailId=email.getText().toString();
                pwd =password.getText().toString();
                if (emailId.isEmpty()||pwd.isEmpty())
                {
                    Toast.makeText(SignUpActivity.this,"Please Enter Valid Credentials ", Toast.LENGTH_LONG).show();
                }
                else if (first_name.getText().toString().isEmpty())
                {
                    Toast.makeText(SignUpActivity.this,"Please Enter First Name ", Toast.LENGTH_LONG).show();
                }
                else if (last_name.getText().toString().isEmpty())
                {
                    Toast.makeText(SignUpActivity.this,"Please Enter Last Name ", Toast.LENGTH_LONG).show();
                }
                else if (Splash.isValidEmaillId(emailId))
                {


                    new SignUpHttpTask().execute(email.getText().toString(),password.getText().toString(),first_name.getText().toString(),last_name.getText().toString(),filePathFromApi);

                }
                else
                {
                    Toast.makeText(SignUpActivity.this,"Please Enter Valid Email ", Toast.LENGTH_LONG).show();
                }
            }
        });

    }
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        // save file url in bundle as it will be null on screen orientation
        // changes
        outState.putParcelable("file_uri", fileUri);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        // get the file url
        fileUri = savedInstanceState.getParcelable("file_uri");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        // if the result is capturing Image
        if (requestCode == CAMERA_CAPTURE_IMAGE_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {

                // successfully captured the image
                // launching upload activity
               // launchUploadActivity(true);
//                BitmapFactory.Options options = new BitmapFactory.Options();

                // down sizing image as it throws OutOfMemory Exception for larger
                // images
//                options.inSampleSize = 8;

//                final Bitmap bitmap = BitmapFactory.decodeFile(fileUri.getPath(), options);

//                addPhoto.setImageBitmap(bitmap);
                addPhoto.setImageBitmap(decodeSampledBitmapFromResource(fileUri.getPath(), 100, 100));
                new UploadFileToServer().execute();


            } else if (resultCode == RESULT_CANCELED) {

                // user cancelled Image capture
                Toast.makeText(getApplicationContext(),
                        "User cancelled image capture", Toast.LENGTH_SHORT)
                        .show();

            } else {
                // failed to capture image
                Toast.makeText(getApplicationContext(),
                        "Sorry! Failed to capture image", Toast.LENGTH_SHORT)
                        .show();
            }

        } else if (requestCode == CAMERA_CAPTURE_VIDEO_REQUEST_CODE)
        {
            if (resultCode == RESULT_OK) {

                // video successfully recorded
                // launching upload activity
               // launchUploadActivity(false);

            } else if (resultCode == RESULT_CANCELED) {

                // user cancelled recording
                Toast.makeText(getApplicationContext(),
                        "User cancelled video recording", Toast.LENGTH_SHORT).show();

            } else {
                // failed to record video
                Toast.makeText(getApplicationContext(), "Sorry! Failed to record video", Toast.LENGTH_SHORT).show();
            }
        }
    }


    private boolean isDeviceSupportCamera() {
        if (getApplicationContext().getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA))
        {
            // this device has a camera
            return true;
        } else {
            // no camera on this device
            return false;
        }
    }
    /**
     * Launching camera app to capture image
     */
    private void captureImage() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        fileUri = getOutputMediaFileUri(MEDIA_TYPE_IMAGE);

        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);

        // start the image capture Intent
        startActivityForResult(intent, CAMERA_CAPTURE_IMAGE_REQUEST_CODE);
    }

    public Uri getOutputMediaFileUri(int type) {
        return Uri.fromFile(getOutputMediaFile(type));
    }

    private static File getOutputMediaFile(int type) {

        // External sdcard location
        File mediaStorageDir = new File(
                Environment
                        .getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
                Config.IMAGE_DIRECTORY_NAME);

        // Create the storage directory if it does not exist
        if (!mediaStorageDir.exists())
        {
            if (!mediaStorageDir.mkdirs()) {
                Log.d("hole", "Oops! Failed create " +Config.IMAGE_DIRECTORY_NAME + " directory");
                return null;
            }
        }

        // Create a media file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss",
                Locale.getDefault()).format(new Date());
        File mediaFile;
        if (type == MEDIA_TYPE_IMAGE)
        {
            mediaFile = new File(mediaStorageDir.getPath() + File.separator
                    + "IMG_" + timeStamp + ".jpg");
        } else if (type == MEDIA_TYPE_VIDEO)
        {
            mediaFile = new File(mediaStorageDir.getPath() + File.separator
                    + "VID_" + timeStamp + ".mp4");
        } else {
            return null;
        }

        return mediaFile;
    }


    //resizing uploaded image to avoid out of memory exception

    public static int calculateInSampleSize(
            BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
            // height and width larger than the requested height and width.
            while ((halfHeight / inSampleSize) > reqHeight
                    && (halfWidth / inSampleSize) > reqWidth) {
                inSampleSize *= 2;
            }
        }

        return inSampleSize;
    }


    public static Bitmap decodeSampledBitmapFromResource(String filepath,
                                                         int reqWidth, int reqHeight) {

        // First decode with inJustDecodeBounds=true to check dimensions
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(filepath, options);

        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeFile(filepath, options);
    }



    /**
     * Uploading the file to server
     * */
    private class UploadFileToServer extends AsyncTask<Void, Integer, String>
    {
        ProgressDialog dialog;
        @Override
        protected void onPreExecute()
        {
            dialog = ProgressDialog.show(SignUpActivity.this,"","Uploading...");

            super.onPreExecute();
        }

        @Override
        protected void onProgressUpdate(Integer... progress)
        {
            // Making progress bar visible
         //   progressBar.setVisibility(View.VISIBLE);

            // updating progress bar value
            dialog.setProgress(progress[0]);

            // updating percentage value
         //   txtPercentage.setText(String.valueOf(progress[0]) + "%");
        }

        @Override
        protected String doInBackground(Void... params) {
            return uploadFile();
        }

        @SuppressWarnings("deprecation")
        private String uploadFile()
        {
            String responseString = null;

            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost(Config.FILE_UPLOAD_URL);

            try
            {
                AndroidMultiPartEntity entity = new AndroidMultiPartEntity(new AndroidMultiPartEntity.ProgressListener() {

                    @Override
                    public void transferred(long num) {
                        publishProgress((int) ((num / (float) totalSize) * 100));
                    }
                });
                Bitmap bitmap = decodeSampledBitmapFromResource(fileUri.getPath(),600,600);
                //Bitmap bitmap = getResizedBitmap(bmp,600);
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 70, baos);
                byte[] imageBytes = baos.toByteArray();
                File sourceFile = new File(fileUri.getPath());

                // Adding file data to http body
                entity.addPart("filename", new ByteArrayBody(imageBytes,"image/jpeg",fileUri.getPath()));

                totalSize = entity.getContentLength();
                httppost.setEntity(entity);

                // Making server call
                HttpResponse response = httpclient.execute(httppost);
                HttpEntity r_entity = response.getEntity();

                int statusCode = response.getStatusLine().getStatusCode();
                if (statusCode == 200)
                {
                    // Server response
                    responseString = EntityUtils.toString(r_entity);
                    parseResult(responseString);
                } else {
                    responseString = "Error occurred! Http Status Code: " + statusCode;
                }

            } catch (ClientProtocolException e)
            {
                responseString = e.toString();
            } catch (IOException e) {
                responseString = e.toString();
            }

            return responseString;

        }

        @Override
        protected void onPostExecute(String result)
        {
            super.onPostExecute(result);

            Log.e("holbe", "Response from server: " + result);

            if (dialog!=null&&dialog.isShowing())
            {
                dialog.dismiss();
            }
            // showing the server response in an alert dialog
            //showAlert(result);

        }


        public Bitmap getResizedBitmap(Bitmap image, int maxSize) {
            int width = image.getWidth();
            int height = image.getHeight();

            float bitmapRatio = (float)width / (float) height;
            if (bitmapRatio > 0)
            {
                width = maxSize;
                height = (int) (width / bitmapRatio);

                if (height>600)
                {
                    height=600;
                }

            } else
            {
                height = maxSize;
                width = (int) (height * bitmapRatio);
                if (width>600)
                {
                    width=600;
                }
            }
            return Bitmap.createScaledBitmap(image, width, height, true);
        }


        /*public Bitmap getResizedBitmap(Bitmap image, int maxSize) {
            int width = image.getWidth();
            int height = image.getHeight();

            float bitmapRatio = (float)width / (float) height;
            if (bitmapRatio > 0) {
                width = maxSize;
                height = (int) (width / bitmapRatio);
            } else {
                height = maxSize;
                width = (int) (height * bitmapRatio);
            }
            return Bitmap.createScaledBitmap(image, width, height, true);
        }*/

        private String parseResult(String result)
        {
            //{"file_name":"images.jpeg","message":"File uploaded successfully!","error":false,"file_path":"http://192.185.26.69/~holbe/api/patient/images/images.jpeg"}
            String status=null;
            try
            {
                JSONObject object = new JSONObject(result);
                filePathFromApi= object.getString("file_path");
               // Log.i("Holbe","FILE_PATH "+filePathFromApi);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return status;
        }
    }


    /**
     * Method to show alert dialog
     * */
    private void showAlert(String message)
    {
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(this);
        builder.setMessage(message).setTitle("Response from Servers")
                .setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // do nothing
                    }
                });
        android.app.AlertDialog alert = builder.create();
        alert.show();
    }

    private class SignUpHttpTask extends AsyncTask<String, Void, String>
    {
        HttpURLConnection urlConnection;
        StringBuilder sb = new StringBuilder();

        ProgressDialog progressDialog;

        @Override
        protected void onPreExecute()
        {
            progressDialog = ProgressDialog.show(SignUpActivity.this, "Please wait!", "Loading...");
        }

        @Override
        protected String doInBackground(String... params)
        {

            try
            {
                JSONObject object = new JSONObject();
                object.put("user_email_address",params[0]);
                object.put("password",params[1]);
                object.put("first_name",params[2]);
                object.put("last_name",params[3]);
                object.put("profilepic",params[4]);
                URL url = new URL(" http://192.185.26.69/~holbe/api/patient/createuser.php");
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setDoOutput(true);
                urlConnection.setRequestMethod("POST");
                urlConnection.setUseCaches(false);
                urlConnection.setConnectTimeout(10000);
                urlConnection.setReadTimeout(10000);
                urlConnection.setRequestProperty("Content-Type","application/json");
                //  urlConnection.setRequestProperty("Host", "android.schoolportal.gr");
                urlConnection.connect();
                OutputStreamWriter out = new OutputStreamWriter(urlConnection.getOutputStream());
                out.write(object.toString());
                out.close();

                int HttpResult =urlConnection.getResponseCode();
                if(HttpResult ==HttpURLConnection.HTTP_OK){
                    BufferedReader br = new BufferedReader(new InputStreamReader(
                            urlConnection.getInputStream(),"utf-8"));
                    String line = null;
                    while ((line = br.readLine()) != null) {
                        sb.append(line + "\n");
                    }
                    br.close();
                    JSONObject mainObject = new JSONObject(sb.toString());
                    message = mainObject.getString("message");
                    return line;

                }
                else
                {
                    System.out.println(urlConnection.getResponseMessage());
                }
            } catch (MalformedURLException e) {

                e.printStackTrace();
            }
            catch (IOException e) {

                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            } finally{
                if(urlConnection!=null)
                    urlConnection.disconnect();
            }

            return null;
        }

        @Override
        protected void onPostExecute(String s)
        {
            super.onPostExecute(s);
            if (progressDialog!=null&&progressDialog.isShowing())
            {
                progressDialog.dismiss();
            }
            Toast.makeText(SignUpActivity.this, message, Toast.LENGTH_LONG).show();
            startActivity(new Intent(SignUpActivity.this,Login.class));
        }
    }


}
