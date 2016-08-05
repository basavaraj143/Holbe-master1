package com.appsriv.holbe.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.appsriv.holbe.Adatpters.CustomListDrawerAdapter;
import com.appsriv.holbe.R;
import com.google.android.gms.analytics.Tracker;
import com.koushikdutta.urlimageviewhelper.UrlImageViewHelper;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class DashBordActivity extends AppCompatActivity {

        DrawerLayout drawerLayout;
        View drawerView;
       // TextView textPrompt, textPrompt2;
        ListView drawerList;
       // TextView textSelection;
    CustomListDrawerAdapter customListDrawerAdapter;

    TextView overalll_compliance,treatment_compliance,days_left,sup_compliance,work_compliance,life_compliance, food_compliance,other_compliance,this_week_compliance,last_week_compliance;
    int str_overalll_compliance, str_treatment_compliance, str_days_left, str_work_compliance, str_life_compliance,str_sup_compliance,
            str_food_compliance, str_other_compliance, str_this_week_compliance, str_last_week_compliance;
    ProgressBar supplement1, workout,lifestyle ,foodanddrink ,others, lastweek ,thisweek;
    Tracker mTracker;
        @Override
        protected void onCreate(Bundle savedInstanceState)
        {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.finaldrawer);
            drawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);
            drawerView = (View)findViewById(R.id.drawer);
            overalll_compliance = (TextView)findViewById(R.id.overalll_compliance);
            treatment_compliance = (TextView)findViewById(R.id.treatment_compliance);
            days_left = (TextView)findViewById(R.id.days_left);
            sup_compliance = (TextView)findViewById(R.id.sup_compliance);
            work_compliance = (TextView)findViewById(R.id.work_compliance);
            life_compliance = (TextView)findViewById(R.id.life_compliance);
            food_compliance = (TextView)findViewById(R.id.food_compliance);
            other_compliance = (TextView)findViewById(R.id.other_compliance);
            this_week_compliance = (TextView)findViewById(R.id.this_week_compliance);
            last_week_compliance = (TextView)findViewById(R.id.last_week_compliance);
            supplement1 = (ProgressBar)findViewById(R.id.supplement1);
            workout = (ProgressBar)findViewById(R.id.workout);
            lifestyle = (ProgressBar)findViewById(R.id.lifestyle);
            foodanddrink = (ProgressBar)findViewById(R.id.foodanddrink);
            others = (ProgressBar)findViewById(R.id.others);
            lastweek = (ProgressBar)findViewById(R.id.lastweek);
            thisweek = (ProgressBar)findViewById(R.id.thisweek);
            String url = "http://192.185.26.69/~holbe/api/patient/get_dashboard.php?id=1";
            new AsyncHttpTask6().execute(url);


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

  /*
   * In my trial experiment:
   * Without drawer_item OnTouchListener for the drawView to
   * consume the onTouch event, touching/clicking on
   * un-handled view on drawView will pass to the view
   * under it!
   * - Touching on the Android icon will
   * trigger the TextView("http://android-er.blogspot.com/")
   * to open the web.
   */

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
            customListDrawerAdapter = new CustomListDrawerAdapter(DashBordActivity.this,names,img,"#ABD14B");

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
                        startActivity(new Intent(DashBordActivity.this,DashBordActivity.class));


                    } else if (position==1)
                    {
                        selected_item.setBackgroundColor(Color.parseColor("#ABD14B"));
                        icon.setBackgroundResource(R.drawable.calendarblue);
                        startActivity(new Intent(DashBordActivity.this,LatestComingUp.class));


                    } else if (position==2)
                    {
                        icon.setBackgroundResource(R.drawable.userblue);
                        selected_item.setBackgroundColor(Color.parseColor("#ABD14B"));
                        startActivity(new Intent(DashBordActivity.this,ProfileActivity.class));


                    }
                    else if (position==3)
                    {
                        /*selected_item.setBackgroundColor(Color.parseColor("#ABD14B"));
                        icon.setBackgroundResource(R.drawable.comingupblue);
                        startActivity(new Intent(DashBordActivity.this,CominUpWithListview.class));*/

                    }

                    else if (position==5)
                    {
                        selected_item.setBackgroundColor(Color.parseColor("#ABD14B"));
                        icon.setBackgroundResource(R.drawable.settingsblue);
                        startActivity(new Intent(DashBordActivity.this,SettingActivity.class));


                    } else if (position==6)
                    {
                        selected_item.setBackgroundColor(Color.parseColor("#ABD14B"));
                        icon.setBackgroundResource(R.drawable.logoff);
                        startActivity(new Intent(DashBordActivity.this,Splash.class));

                    }
                }});
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
            public void onDrawerStateChanged(int newState)
            {
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

    public class AsyncHttpTask6 extends AsyncTask<String, Void, Integer>
    {

        ProgressDialog progressDialog;
        @Override
        protected void onPreExecute() {

            progressDialog = ProgressDialog.show(DashBordActivity.this,"Please wait","Loading...");
        }

        @Override
        protected Integer doInBackground(String... params)
        {
            Integer result = 0;
            HttpURLConnection urlConnection = null;

            try {
                /* forming th java.net.URL object */
                URL url6 = new URL(params[0]);

                urlConnection = (HttpURLConnection) url6.openConnection();

                /* for Get request */
                urlConnection.setRequestMethod("GET");

                int statusCode = urlConnection.getResponseCode();

                /* 200 represents HTTP OK */
                if (statusCode ==  200)
                {
                    System.out.println("Status code is:" + statusCode);
                    BufferedReader r = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                    StringBuilder response = new StringBuilder();
                    String line;
                    while ((line = r.readLine()) != null) {
                        response.append(line);
                    }
                    System.out.println("this is response" + response.toString());

                    parseResult(response.toString());



                    result = 1; // Successful

                }else{

                    result = 0; //"Failed to fetch data!"// ;
                    System.out.print("unable to fetch data");
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
            finally {
                if (urlConnection!=null)
                {

                }
            }

            return result; //"Failed to fetch data!";
        }

        @Override
        protected void onPostExecute(Integer result)
        {

            //mProgressDialog.dismiss();

            /* Download complete. Lets update UI */
            if (result == 1)
            {

                if (progressDialog!=null&&progressDialog.isShowing())
                {
                    progressDialog.dismiss();
                }
                overalll_compliance.setText(""+str_overalll_compliance+"%");
                treatment_compliance.setText(""+str_treatment_compliance+"");
                days_left.setText(""+str_days_left +" Days left");
                sup_compliance.setText(""+str_sup_compliance+"%");
                work_compliance.setText(""+str_work_compliance+"%");
                life_compliance.setText(""+str_life_compliance+"%");
                food_compliance.setText(""+str_food_compliance+"%");
                other_compliance.setText(""+str_other_compliance+"%");
                this_week_compliance.setText(""+str_this_week_compliance+"%");
                last_week_compliance.setText(""+str_last_week_compliance+"%");

                supplement1.setProgress(str_sup_compliance);
                workout.setProgress(str_work_compliance);
                lifestyle.setProgress(str_life_compliance);
                foodanddrink.setProgress(str_food_compliance);
                others.setProgress(str_other_compliance);
                lastweek.setProgress(str_last_week_compliance);
                thisweek.setProgress(str_this_week_compliance);

            } else
            {

                // Log.e(TAG, "Failed to fetch data!");
            }
        }
    }
    private void parseResult(String result)
    {

        try
        {

            JSONArray nearByBar = new JSONArray(result);

            if (nearByBar.length()!=0)
            {

                str_work_compliance=nearByBar.getJSONObject(0).getInt("workout_compliance");
                str_sup_compliance=nearByBar.getJSONObject(0).getInt("supplement_compliance");
                str_life_compliance= nearByBar.getJSONObject(0).getInt("lifestyle_compliance");
                str_food_compliance = nearByBar.getJSONObject(0).getInt("food_compliance");
                str_other_compliance = nearByBar.getJSONObject(0).getInt("others_compliance");
                str_overalll_compliance = nearByBar.getJSONObject(0).getInt("overall_compliance");
                str_this_week_compliance = nearByBar.getJSONObject(0).getInt("current_week_compliance");
                str_days_left =  nearByBar.getJSONObject(0).getInt("days_left");
                str_last_week_compliance = nearByBar.getJSONObject(0).getInt("last_week_compliance");
                str_treatment_compliance = nearByBar.getJSONObject(0).getInt("treatment_count");
            }
            else
            {
                //showAlertDialog(BeerScreen.this, "Alert", "No Data Found Please Try other place", false);

            }

        } catch (JSONException j)
        {
            j.printStackTrace();
        }

    }

}
