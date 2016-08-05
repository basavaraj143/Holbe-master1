package com.appsriv.holbe.activities;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.appsriv.holbe.CominUpWithListview;
import com.appsriv.holbe.Config;
import com.appsriv.holbe.Adatpters.CustomListDrawerAdapter;
import com.appsriv.holbe.DrawerActivity;
import com.appsriv.holbe.util.GoogleAnalyticsApplication;
import com.appsriv.holbe.R;

import com.appsriv.holbe.util.AndroidMultiPartEntity;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.koushikdutta.urlimageviewhelper.UrlImageViewHelper;

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
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class SettingActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener
{
    EditText fname,lname,phone,email,dob,address;
    ImageView prof_pic;
    Tracker mTracker;
    private int year;
    private int month;
    private int day;
    //private static final int CAMERA_REQUEST = 1888;

    String filePathFromApi="";
    String message;
    // Camera activity request codes
    private static final int CAMERA_CAPTURE_IMAGE_REQUEST_CODE = 100;
    private static final int CAMERA_CAPTURE_VIDEO_REQUEST_CODE = 200;
    public static final int MEDIA_TYPE_VIDEO = 2;

    public static final int MEDIA_TYPE_IMAGE = 1;
    private Uri fileUri; // file url to store image/video

    long totalSize = 0;
    ImageView addPhoto;
    DrawerLayout drawerLayout;
    View drawerView;
    // TextView textPrompt, textPrompt2;
    ListView drawerList;
    // TextView textSelection;
    CustomListDrawerAdapter customListDrawerAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting_drawer);
        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);
       // DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        //ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
           ////     this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
      //  drawer.setDrawerListener(toggle);
       //toggle.syncState();
        final Calendar c = Calendar.getInstance();
        year = c.get(Calendar.YEAR);
        month = c.get(Calendar.MONTH);
        day = c.get(Calendar.DAY_OF_MONTH);


        GoogleAnalyticsApplication application = (GoogleAnalyticsApplication) getApplicationContext();
        mTracker = application.getDefaultTracker();


        mTracker.send(new HitBuilders.EventBuilder()
                .setCategory("Setting screen")
                .setAction(" Setting screen")
                .setLabel("Setting screen")
                .build());

        drawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);
        drawerView = (View)findViewById(R.id.drawer);
        TextView save = (TextView)findViewById(R.id.save);
        fname =(EditText)findViewById(R.id.fname);
        lname =(EditText)findViewById(R.id.lname);
        phone =(EditText)findViewById(R.id.phone);
        email =(EditText)findViewById(R.id.email);
        dob =(EditText)findViewById(R.id.dob);
        dob.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                DatePickerDialog dpd = new DatePickerDialog(SettingActivity.this,
                        new DatePickerDialog.OnDateSetListener()
                        {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth)
                            {
                                dob.setText(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);

                            }
                        }, year, month, day);
                dpd.show();
            }
        });
        address =(EditText)findViewById(R.id.address);
        prof_pic = (ImageView)findViewById(R.id.prof_picture);
        UrlImageViewHelper.setUrlDrawable(prof_pic, Login.details.get("user_profile_picture"));
        if (Login.details.size()!=0)
        {
            /*details.put("userId",userId);
                details.put("userFirstName",userFirstName);
                details.put("userCity",userCity);
                details.put("userPhoneNo",userPhoneNo);
                details.put("userAddress",userAddress);
                details.put("userDob",userDob);
                details.put("userEmailAddress",userEmailAddress);
                details.put("userLastName",userLastName);*/

            fname.setText(Login.details.get("userFirstName"));
            lname.setText(Login.details.get("userLastName"));
            phone.setText(Login.details.get("userPhoneNo"));
            email.setText(Login.details.get("userEmailAddress"));
            dob.setText(Login.details.get("userDob"));
            address.setText(Login.details.get("userAddress"));
        }

        addPhoto = (ImageView)findViewById(R.id.camera);
       // prof_pic = (ImageView)findViewById(R.id.prof_pic);
       addPhoto.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               captureImage();
           }
       });
        if (!isDeviceSupportCamera())
        {
            Toast.makeText(getApplicationContext(), "Sorry! Your device doesn't support camera",
                    Toast.LENGTH_LONG).show();
            // will close the app if the device does't have camera
            finish();
        }
        save.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if (fname.getText().toString().isEmpty())
                {
                    fname.setError("Please Enter Name");
                }
                else if (lname.getText().toString().isEmpty())
                {
                    lname.setError("Please Enter Last Name");
                }
                else if (phone.getText().toString().isEmpty())
                {
                    phone.setError("Please Enter Phone Number");
                }
                else if (email.getText().toString().isEmpty())
                {
                    email.setError("Please Enter Email");
                }
                else if (dob.getText().toString().isEmpty())
                {
                    dob.setError("Please Enter DOB");
                }
                else if (address.getText().toString().isEmpty())
                {
                    email.setError("Please Enter Address");
                }

                else
                {
                    String firstName = fname.getText().toString();
                    String lastName = lname.getText().toString();
                    String phoneNum = phone.getText().toString();
                    String emailId = email.getText().toString();
                    String DOB = dob.getText().toString();
                    String add = address.getText().toString();


                    new SettingTask().execute(firstName,lastName,phoneNum,add,Login.details.get("userId"),filePathFromApi);


                }

            }
        });


        ImageView buttonOpenDrawer = (ImageView)findViewById(R.id.id);
        buttonOpenDrawer.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View arg0) {
                drawerLayout.openDrawer(drawerView);
            }});

            /*Button buttonCloseDrawer = (Button)findViewById(R.id.closedrawer);
            buttonCloseDrawer.setOnClickListener(new View.OnClickListener(){

                @Override
                public void onClick(View arg0)
                {
                    drawerLayout.closeDrawers();
                }});
*/
        drawerLayout.setDrawerListener(myDrawerListener);


        drawerView.setOnTouchListener(new View.OnTouchListener()
        {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // TODO Auto-generated method stub
                return true;
            }
        });

        //textSelection = (TextView)findViewById(R.id.selection);
        drawerList = (ListView)findViewById(R.id.drawerlist);
        String names[]={"Overview","Daily Activities","Profile","","","Settings","Logout"};
        Integer[] img={R.drawable.overview,R.drawable.treatment,R.drawable.profile,0,0,R.drawable.settting,R.drawable.logout};
        customListDrawerAdapter = new CustomListDrawerAdapter(SettingActivity.this,names,img,"#ABD14B");

        TextView prof_name = (TextView)findViewById(R.id.name);
        TextView city =(TextView)findViewById(R.id.city);
        ImageView prof_pic = (ImageView)findViewById(R.id.prof_pic);
        if (Login.details.size()!=0)
        {
            prof_name.setText(Login.details.get("userFirstName"));
            city.setText(Login.details.get("userCity"));
            UrlImageViewHelper.setUrlDrawable(prof_pic,Login.details.get("user_profile_picture"));
        }


        //  arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, dayOfWeek);
        drawerList.setAdapter(customListDrawerAdapter);

        drawerList.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                TextView selected_item = (TextView) view.findViewById(R.id.selected_item);
                ImageView icon = (ImageView) view.findViewById(R.id.icon);

                if (position==0)
                {
                    selected_item.setBackgroundColor(Color.parseColor("#ABD14B"));
                    icon.setBackgroundResource(R.drawable.overview);
                    startActivity(new Intent(SettingActivity.this,DashBordActivity.class));


                } else if (position==1)
                {
                    selected_item.setBackgroundColor(Color.parseColor("#ABD14B"));
                    icon.setBackgroundResource(R.drawable.calendarblue);
                    startActivity(new Intent(SettingActivity.this,LatestComingUp.class));


                } else if (position==2)
                {
                    icon.setBackgroundResource(R.drawable.userblue);
                    selected_item.setBackgroundColor(Color.parseColor("#ABD14B"));
                    startActivity(new Intent(SettingActivity.this,ProfileActivity.class));


                }
                else if (position==3)
                {
                  /*  selected_item.setBackgroundColor(Color.parseColor("#ABD14B"));
                    icon.setBackgroundResource(R.drawable.comingupblue);
                    startActivity(new Intent(SettingActivity.this,CominUpWithListview.class));
*/
                }

                else if (position==5)
                {
                    selected_item.setBackgroundColor(Color.parseColor("#ABD14B"));
                    icon.setBackgroundResource(R.drawable.settingsblue);
                    startActivity(new Intent(SettingActivity.this,SettingActivity.class));


                } else if (position==6)
                {
                    selected_item.setBackgroundColor(Color.parseColor("#ABD14B"));
                    icon.setBackgroundResource(R.drawable.logoff);
                    startActivity(new Intent(SettingActivity.this,Splash.class));

                }
            }});

        /*NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View header=navigationView.getHeaderView(0);
        TextView prof_name = (TextView)header.findViewById(R.id.name);
        ImageView prof_pic = (ImageView)header.findViewById(R.id.prof_pic);
        TextView city =(TextView)header.findViewById(R.id.city);
        if (Login.details.size()!=0) {
            prof_name.setText(Login.details.get("userFirstName"));
            //Picasso.with(DrawerActivity.this).load("http://192.185.26.69/~holbe/api/patient/images/IMG_20160512_160617.jpg").into(prof_pic);
            UrlImageViewHelper.setUrlDrawable(prof_pic,Login.details.get("user_profile_picture"));
            // city.setText(Login.details.get("userCity"));
        }*/
    }

    DrawerLayout.DrawerListener myDrawerListener = new DrawerLayout.DrawerListener(){

        @Override
        public void onDrawerClosed(View drawerView) {
            // textPrompt.setText("onDrawerClosed");
        }

        @Override
        public void onDrawerOpened(View drawerView) {
            // textPrompt.setText("onDrawerOpened");
        }

        @Override
        public void onDrawerSlide(View drawerView, float slideOffset) {
            // textPrompt.setText("onDrawerSlide: " + String.format("%.2f", slideOffset));
        }

        @Override
        public void onDrawerStateChanged(int newState) {
            String state;
            switch(newState){
                case DrawerLayout.STATE_IDLE:
                    state = "STATE_IDLE";
                    break;
                case DrawerLayout.STATE_DRAGGING:
                    state = "STATE_DRAGGING";
                    break;
                case DrawerLayout.STATE_SETTLING:
                    state = "STATE_SETTLING";
                    break;
                default:
                    state = "unknown!";
            }

            // textPrompt2.setText(state);
        }
    };
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
                BitmapFactory.Options options = new BitmapFactory.Options();

                // down sizing image as it throws OutOfMemory Exception for larger
                // images
                options.inSampleSize = 8;

                final Bitmap bitmap = BitmapFactory.decodeFile(fileUri.getPath(), options);

                prof_pic.setImageBitmap(bitmap);
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
                //launchUploadActivity(false);

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

 /*   private void launchUploadActivity(boolean isImage){
        Intent i = new Intent(SettingActivity.this, UploadActivity.class);
        i.putExtra("filePath", fileUri.getPath());
        i.putExtra("isImage", isImage);
        startActivity(i);
    }*/

    private boolean isDeviceSupportCamera() {
        if (getApplicationContext().getPackageManager().hasSystemFeature(
                PackageManager.FEATURE_CAMERA)) {
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
                Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
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


    /**
     * Uploading the file to server
     * */
    private class UploadFileToServer extends AsyncTask<Void, Integer, String>
    {
        ProgressDialog dialog;
        @Override
        protected void onPreExecute()
        {
            dialog = ProgressDialog.show(SettingActivity.this,"","Uploading...");
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


                Bitmap bmp = BitmapFactory.decodeFile(fileUri.getPath());
                Bitmap bitmap = getResizedBitmap(bmp,600);
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


        private String parseResult(String result)
        {
            //{"file_name":"images.jpeg","message":"File uploaded successfully!","error":false,"file_path":"http://192.185.26.69/~holbe/api/patient/images/images.jpeg"}
            String status=null;
            try
            {
                JSONObject object = new JSONObject(result);
                filePathFromApi= object.getString("file_path");
                Login.details.put("user_profile_picture",filePathFromApi);
                // Log.i("Holbe","FILE_PATH "+filePathFromApi);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return status;
        }
    }


    @Override
    protected void onResume()
    {
        super.onResume();
        mTracker.setScreenName("Setting Screen ");
        mTracker.send(new HitBuilders.ScreenViewBuilder().build());
    }

    @Override
    protected void onStart() {
        super.onStart();
        mTracker.setScreenName("Setting Screen ");
        mTracker.send(new HitBuilders.ScreenViewBuilder().build());
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.setting, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //
        // noinspection SimplifiableIfStatement

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.overview)
        {
            startActivity(new Intent(SettingActivity.this,DashBordActivity.class));

        } else if (id == R.id.mytreatment)
        {
            startActivity(new Intent(SettingActivity.this,DrawerActivity.class));

        } else if (id == R.id.profile)
        {
            startActivity(new Intent(SettingActivity.this,ProfileActivity.class));

        }
        else if (id == R.id.comingup)
        {
            startActivity(new Intent(SettingActivity.this,CominUpWithListview.class));
        }

        else if (id == R.id.setting)
        {
            startActivity(new Intent(SettingActivity.this,SettingActivity.class));

        } else if (id == R.id.logout)
        {
            startActivity(new Intent(SettingActivity.this,Splash.class));
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }



    private class SettingTask extends AsyncTask<String, Void, String>
    {
        HttpURLConnection urlConnection;
        StringBuilder sb = new StringBuilder();

           /*
    fname
    lname
    phone
    address
    id
        */

        @Override
        protected String doInBackground(String... params)
        {
            try
            {
                JSONObject object = new JSONObject();
                object.put("fname",params[0]);
                object.put("lname",params[1]);
                object.put("phone",params[2]);
                object.put("address",params[3]);
                object.put("id",params[4]);
                object.put("pic",params[5]);
                URL url = new URL("http://192.185.26.69/~holbe/api/patient/updateuserdetails.php");
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setDoOutput(true);
                urlConnection.setRequestMethod("POST");
                urlConnection.setUseCaches(false);
                urlConnection.setConnectTimeout(20000);
                urlConnection.setReadTimeout(20000);
                urlConnection.setRequestProperty("Content-Type","application/json");
                //  urlConnection.setRequestProperty("Host", "android.schoolportal.gr");
                urlConnection.connect();
                OutputStreamWriter out = new OutputStreamWriter(urlConnection.getOutputStream());
                out.write(object.toString());
                out.close();

                int HttpResult =urlConnection.getResponseCode();
                if(HttpResult ==HttpURLConnection.HTTP_OK)
                {
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
            catch (IOException e)
            {
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
            Toast.makeText(SettingActivity.this, message, Toast.LENGTH_SHORT).show();
            startActivity(new Intent(SettingActivity.this,DrawerActivity.class));

        }
    }
}
