package com.appsriv.holbe;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
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
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.appsriv.holbe.Adatpters.CustomListDrawerAdapter;
import com.appsriv.holbe.Adatpters.ExpandListAdapterForComingUpListview;
import com.appsriv.holbe.activities.DashBordActivity;
import com.appsriv.holbe.activities.Login;
import com.appsriv.holbe.activities.ProfileActivity;
import com.appsriv.holbe.activities.SettingActivity;
import com.appsriv.holbe.activities.Splash;
import com.appsriv.holbe.models.Food;
import com.appsriv.holbe.models.Group;
import com.appsriv.holbe.models.LifeStyle;
import com.appsriv.holbe.models.Others;
import com.appsriv.holbe.models.Supplement;
import com.appsriv.holbe.models.Workout;
import com.appsriv.holbe.util.GoogleAnalyticsApplication;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.koushikdutta.urlimageviewhelper.UrlImageViewHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class CominUpWithListview extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private CustomListAdapter adapter;
    private ExpandListAdapterForComingUpListview comingUpListview;
    String items[];
    int flag[];
    int background[];
    String lineColour[];
    String[] time;
    String[] typeExc;
    int childPostion, groupPostion;
    Tracker mTracker;
    DrawerLayout drawerLayout;
    View drawerView;
    // TextView textPrompt, textPrompt2;
    ListView drawerList;
    // TextView textSelection;
    CustomListDrawerAdapter customListDrawerAdapter;
    private ArrayList<Group> list;
    ExpandableListView listview;
    private int progressBarRes[];
    private String[] name;
    ArrayList<Workout> work_list = null;
    ArrayList<Supplement> sup_list = null;
    ArrayList<LifeStyle> life_list = null;
    ArrayList<Food> food_list = null;
    ArrayList<Others> other_list = null;
    String workout_count, supplement_count, lifestyle_count, food_count, others_count;
    int str_overalll_compliance;
    ImageView datepicker;
    private int year;
    private int month;
    private int day;
    TextView datechanged;
    TextView currentdate;
    public static ImageView previousdate;
    public static ImageView nextdate;
    public static Calendar c;
    public static String formattedDate;
    public static SimpleDateFormat df;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.comingup_drawer);
        Bundle bundle = getIntent().getExtras();
        if (bundle != null)
        {
            groupPostion = bundle.getInt("groupPosition");
            childPostion = bundle.getInt("childPosition");
        }

        GoogleAnalyticsApplication application = (GoogleAnalyticsApplication) getApplicationContext();
        mTracker = application.getDefaultTracker();


        mTracker.send(new HitBuilders.EventBuilder()
                .setCategory("Comingup screen")
                .setAction(" Comingup screen")
                .setLabel("Comingup screen")
                .build());

        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawerView = (View) findViewById(R.id.drawer);
        //ListView listView =(ListView)findViewById(R.id.listview);
        listview = (ExpandableListView) findViewById(R.id.exp_list);
        items = new String[]{"Supplement", "Workout", "Lifestyle", "Food & Drink", "Others"};
        time = new String[]{"1:30 PM", "3:45 PM", "5:15 PM", "6:30 PM", "8:00 PM"};

        lineColour = new String[]{"#ABD14B", "#3CC3AF", "#1AA2DF", "#AA68B4", "#BD345E"};

        flag = new int[]{R.drawable.supplements, R.drawable.workouts, R.drawable.lifestyles, R.drawable.foodadndrink,
                R.drawable.others};
        background = new int[]{R.drawable.circle_suppliments, R.drawable.circle_workout, R.drawable.circle_heart, R.drawable.circlefoodanddrink,
                R.drawable.circle_others};
        typeExc = new String[]{"SITUPS", "AVACADO SHAKE", "VITAMIN C", "VITAMIN A", "VITAMIN D"};
        //  adapter = new CustomListAdapter(CominUpWithListview.this, items,time,flag,typeExc,background,lineColour,SupplementFragment.list,groupPostion,childPostion);
//        comingUpListview = new ExpandListAdapterForComingUpListview(CominUpWithListview.this, items,time,flag,typeExc,background,lineColour,SupplementFragment.list,groupPostion,childPostion,listview);
        //  listview.setAdapter(comingUpListview);

        ImageView buttonOpenDrawer = (ImageView) findViewById(R.id.id);
        datechanged= (TextView) findViewById(R.id.datechanged);
        currentdate=(TextView) findViewById(R.id.currentdate);

        datepicker = (ImageView) findViewById(R.id.comingupdatepicker);
        c = Calendar.getInstance();

        year = c.get(Calendar.YEAR);
        month = c.get(Calendar.MONTH);
        day = c.get(Calendar.DAY_OF_MONTH);



        previousdate = (ImageView)findViewById(R.id.previousdatetreatment);
        nextdate = (ImageView)findViewById(R.id.nextdatetreatment);


        df = new SimpleDateFormat("EE-yyyy-MM-dd");

        formattedDate = df.format(c.getTime());
        currentdate.setText(formattedDate);
        datechanged.setText(formattedDate);

        buttonOpenDrawer.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                drawerLayout.openDrawer(drawerView);
            }
        });

        datepicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(CominUpWithListview.this);
                final DatePickerDialog dpd = new DatePickerDialog(CominUpWithListview.this,
                        new DatePickerDialog.OnDateSetListener()
                        {
                            @Override
                            public void onDateSet(DatePicker view1, int year, int monthOfYear, int dayOfMonth)
                            {

                                SimpleDateFormat sdf = new SimpleDateFormat("EE");
                                Date d = new Date(year, monthOfYear, dayOfMonth-1);
                                String dayOfTheWeek = sdf.format(d);
                                datechanged.setText(dayOfTheWeek +" "+year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);
                                currentdate.setText(dayOfTheWeek +" "+year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);
                                String url = "http://192.185.26.69/~holbe/api/patient/get_coming_up.php?id=" + Login.details.get("userId") + "&dateid=" + datechanged.getText().toString().substring(4); //Log.i("url", "url " +url);
                                Log.d("test",url);
                                new AsyncHttpTask().execute(url);
                            }
                        }, year, month, day);
                dpd.show();
            }
        });

        previousdate.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                c.add(Calendar.DATE, -1);
                formattedDate = df.format(c.getTime());
                datechanged.setText(formattedDate);
                currentdate.setText(formattedDate);
                String url = "http://192.185.26.69/~holbe/api/patient/get_coming_up.php?id=" + Login.details.get("userId") + "&dateid=" + datechanged.getText().toString().substring(4);//Log.i("url", "url " +url);
                Log.d("test",url);
                new AsyncHttpTask().execute(url);

            }
        });


        //nextdate onlick
       nextdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                c.add(Calendar.DATE, 1);
                formattedDate = df.format(c.getTime());

                datechanged.setText(formattedDate);
                currentdate.setText(formattedDate);
                String url = "http://192.185.26.69/~holbe/api/patient/get_coming_up.php?id=" + Login.details.get("userId") + "&dateid=" + datechanged.getText().toString().substring(4);
                //Log.i("url", "url " +url);
                Log.d("test",url);
                new AsyncHttpTask().execute(url);
            }
        });
        drawerLayout.setDrawerListener(myDrawerListener);

        drawerView.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event)
            {
                return true;
            }
        });
        drawerList = (ListView) findViewById(R.id.drawerlist);
        String names[] = {"Overview", "Daily Activities", "Profile",  "", "", "Settings", "Logout"};
        Integer[] img = {R.drawable.overview, R.drawable.treatment, R.drawable.profile, 0, 0, R.drawable.settting, R.drawable.logout};
        customListDrawerAdapter = new CustomListDrawerAdapter(CominUpWithListview.this, names, img, "#ABD14B");

        TextView prof_name = (TextView) findViewById(R.id.name);
        TextView city = (TextView) findViewById(R.id.city);
        ImageView prof_pic = (ImageView) findViewById(R.id.prof_pic);
        if (Login.details.size() != 0) {
            prof_name.setText(Login.details.get("userFirstName"));
            city.setText(Login.details.get("userCity"));
            UrlImageViewHelper.setUrlDrawable(prof_pic, Login.details.get("user_profile_picture"));
        }


        //  arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, dayOfWeek);
        drawerList.setAdapter(customListDrawerAdapter);

        drawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                TextView selected_item = (TextView) view.findViewById(R.id.selected_item);
                ImageView icon = (ImageView) view.findViewById(R.id.icon);

                if (position == 0) {
                    selected_item.setBackgroundColor(Color.parseColor("#ABD14B"));
                    icon.setBackgroundResource(R.drawable.overview);
                    startActivity(new Intent(CominUpWithListview.this, DashBordActivity.class));


                } else if (position == 1) {
                    selected_item.setBackgroundColor(Color.parseColor("#ABD14B"));
                    icon.setBackgroundResource(R.drawable.calendarblue);
                    startActivity(new Intent(CominUpWithListview.this, DrawerActivity.class));


                } else if (position == 2) {
                    icon.setBackgroundResource(R.drawable.userblue);
                    selected_item.setBackgroundColor(Color.parseColor("#ABD14B"));
                    startActivity(new Intent(CominUpWithListview.this, ProfileActivity.class));


                } else if (position == 3) {
                   /* selected_item.setBackgroundColor(Color.parseColor("#ABD14B"));
                    icon.setBackgroundResource(R.drawable.comingupblue);
                    startActivity(new Intent(CominUpWithListview.this, CominUpWithListview.class));*/

                } else if (position == 5) {
                    selected_item.setBackgroundColor(Color.parseColor("#ABD14B"));
                    icon.setBackgroundResource(R.drawable.settingsblue);
                    startActivity(new Intent(CominUpWithListview.this, SettingActivity.class));


                } else if (position == 6) {
                    selected_item.setBackgroundColor(Color.parseColor("#ABD14B"));
                    icon.setBackgroundResource(R.drawable.logoff);
                    startActivity(new Intent(CominUpWithListview.this, Splash.class));

                }
            }
        });

        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        // setSupportActionBar(toolbar);
        // toolbar.setBackgroundColor(Color.TRANSPARENT);


        // DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        // ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        // drawer.setDrawerListener(toggle);
        //toggle.syncState();

        //NavigationView navigationView = (NavigationView)findViewById(R.id.nav_view);
        //navigationView.setNavigationItemSelectedListener(this);
        // View header=navigationView.getHeaderView(0);

/*View view=navigationView.inflateHeaderView(R.layout.nav_header_main);*/
        // TextView prof_name = (TextView)header.findViewById(R.id.name);
       /* TextView city =(TextView)header.findViewById(R.id.city);
        if (Login.details.size()!=0)
        {
           // prof_name.setText(Login.details.get("userFirstName"));
           // city.setText(Login.details.get("userCity"));
            TextView name = (TextView)header.findViewById(R.id.name);
            ImageView prof_pic = (ImageView)header.findViewById(R.id.prof_pic);
            //Picasso.with(CominUpWithListview.this).load("http://192.185.26.69/~holbe/api/patient/images/IMG_20160512_160617.jpg").into(prof_pic);
            String  s = Login.details.get("user_profile_picture");
            //UrlImageViewHelper.setUrlDrawable(prof_pic,Login.details.get("user_profile_picture"));
            name.setText(Login.details.get("userFirstName"));
        }*/


        String url = "http://192.185.26.69/~holbe/api/patient/get_coming_up.php?id=" + Login.details.get("userId") + "&dateid=" + formattedDate.substring(4);
        //Log.i("url", "url " +url);


        new AsyncHttpTask().execute(url);
        Log.d("new url",url);

    }


    public class AsyncHttpTask extends AsyncTask<String, Void, Integer> {

        ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {

            progressDialog = ProgressDialog.show(CominUpWithListview.this, "Please wait", "Loading...");
        }

        @Override
        protected Integer doInBackground(String... params) {
            InputStream inputStream = null;
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
                if (statusCode == 200) {
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

                } else {

                    result = 0; //"Failed to fetch data!"// ;
                    System.out.print("unable to fetch data");
                }

            } catch (Exception e) {


                //  Log.d(TAG, e.getLocalizedMessage());
            } finally {
                if (urlConnection != null) {

                }
            }

            return result; //"Failed to fetch data!";
        }

        @Override
        protected void onPostExecute(Integer result) {

            //mProgressDialog.dismiss();

            /* Download complete. Lets update UI */
            if (progressDialog != null && progressDialog.isShowing()) {
                progressDialog.dismiss();
            }
            if (result == 1) {
                comingUpListview = new ExpandListAdapterForComingUpListview(CominUpWithListview.this, items, time, flag, typeExc, background, lineColour, list, groupPostion, childPostion, listview);
                listview.setAdapter(comingUpListview);

            } else {

                Log.e("fail", "Failed to fetch data!");
                //Toast.makeText(, "", Toast.LENGTH_SHORT).show();
            }
        }
    }


    DrawerLayout.DrawerListener myDrawerListener = new DrawerLayout.DrawerListener() {

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
            switch (newState) {
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
    protected void onResume() {
        super.onResume();
        mTracker.setScreenName("Coming Up Screen");
        mTracker.send(new HitBuilders.ScreenViewBuilder().build());
    }

    @Override
    protected void onStart() {
        super.onStart();
        mTracker.setScreenName("Coming Up Screen");
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
        getMenuInflater().inflate(R.menu.drawer, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();


        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.overview) {
            startActivity(new Intent(CominUpWithListview.this, DashBordActivity.class));

        } else if (id == R.id.mytreatment) {
            startActivity(new Intent(CominUpWithListview.this, DrawerActivity.class));

        } else if (id == R.id.profile) {
            startActivity(new Intent(CominUpWithListview.this, ProfileActivity.class));

        } else if (id == R.id.comingup) {
            startActivity(new Intent(CominUpWithListview.this, CominUpWithListview.class));
        } else if (id == R.id.setting) {
            startActivity(new Intent(CominUpWithListview.this, SettingActivity.class));

        } else if (id == R.id.logout) {
            startActivity(new Intent(CominUpWithListview.this, Splash.class));
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    private void parseResult(String result) {
        list = new ArrayList<>();
        flag = new int[]{R.drawable.circle_suppliments, R.drawable.circle_workout, R.drawable.circle_heart, R.drawable.circlefoodanddrink,
                R.drawable.circle_others};
        progressBarRes = new int[]
                {R.drawable.supplement_circle_progress_foregrnd, R.drawable.workout_circle_progress_foregrnd, R.drawable.lifestylr_circle_progress_foregrnd,
                        R.drawable.foodanddrinks_circle_progress_foregrnd,
                        R.drawable.others_circle_progress_foregrnd};

        name = new String[]{"Supplements", "Workout", "Lifestyle", "Food & Drinks", "Others"};

        lineColour = new String[]{"#ABD14B", "#3CC3AF", "#1AA2DF", "#AA68B4", "#BD345E"};

        try {

            JSONObject object = new JSONObject(result);


            if (object.length() != 0) {

                list = new ArrayList<>();
                work_list = new ArrayList<>();
                life_list = new ArrayList<>();
                sup_list = new ArrayList<>();
                food_list = new ArrayList<>();
                other_list = new ArrayList<>();

                for (int j = 0; j < 5; j++) {
                    Group gru = new Group();
                    gru.setName(name[j]);
                    gru.setIcon(flag[j]);
                    gru.setPercentage("91");
                    gru.setSectionName("section" + j);

                    work_list = new ArrayList<Workout>();

					/*	for (int i = 0; i < 1; i++)
						{
*/

                    //treatment count
//                    JSONArray array = object.getJSONArray("treatment_count");
//                    for (int k = 0; k < array.length(); k++) {
//
//                        workout_count = array.getJSONObject(k).getString("workout_count");
//                        supplement_count = array.getJSONObject(k).getString("supplement_count");
//                        lifestyle_count = array.getJSONObject(k).getString("lifestyle_count");
//                        food_count = array.getJSONObject(k).getString("food_count");
//                        others_count = array.getJSONObject(k).getString("others_count");
//                    }
//
//                    //over all compliance
//                    JSONArray overAllCompArr = object.getJSONArray("overall_compliance");
//                    str_overalll_compliance = overAllCompArr.getJSONObject(0).getInt("overall_compliance");

                    //workout

                    Workout work;
                    JSONArray workout = object.getJSONArray("workout");
                    for (int x = 0; x < workout.length(); x++) {
                        work = new Workout();
                        work.setTime(workout.getJSONObject(x).getString("time"));
                        work.setTimings_id(workout.getJSONObject(x).getString("timings_id"));
                        work.setWorkout_mapping_id(workout.getJSONObject(x).getString("workout_mapping_id"));
                        work.setReps(workout.getJSONObject(x).getString("reps"));
                        work.setSets(workout.getJSONObject(x).getString("sets"));
                        work.setWeight(workout.getJSONObject(x).getString("weight"));
                        work.setGap(workout.getJSONObject(x).getString("gap"));
                        work.setFreequency(workout.getJSONObject(x).getString("frequency"));
                        work.setRest(workout.getJSONObject(x).getString("rest"));
                        work.setWorkout_name(workout.getJSONObject(x).getString("workout_name"));
                        work.setCompliance(workout.getJSONObject(x).getString("compliance") + "%");
                        work.setInt_compliance(workout.getJSONObject(x).getInt("compliance"));
                        work.setProgressBarRes(progressBarRes[j]);
                        work.setColour(lineColour[j]);
                        work_list.add(work);
                    }

                    // supplement

                    Supplement supp = null;
                    sup_list = new ArrayList<>();
                    JSONArray supplement = object.getJSONArray("supplement");
                    for (int x = 0; x < supplement.length(); x++) {
                        supp = new Supplement();
                        supp.setTime(supplement.getJSONObject(x).getString("time"));
                        supp.setTimings_id(supplement.getJSONObject(x).getString("timings_id"));
                        supp.setSupplement_mapping_id(supplement.getJSONObject(x).getString("supplement_mapping_id"));
                        supp.setSupplement_name(supplement.getJSONObject(x).getString("supplement_name"));
                        supp.setAmount(supplement.getJSONObject(x).getString("amount"));
                        supp.setGap(supplement.getJSONObject(x).getString("gap"));
                        //supp.setRepitition(supplement.getJSONObject(x).getString("repitition"));
                        supp.setCompliance(supplement.getJSONObject(x).getString("compliance") + "%");
                       // supp.setWhen_time(supplement.getJSONObject(x).getString("when_time"));
                        supp.setInt_compliance(supplement.getJSONObject(x).getInt("compliance"));
                        supp.setFreequency(supplement.getJSONObject(x).getString("frequency"));
                        supp.setProgressBarRes(progressBarRes[j]);
                        supp.setColour(lineColour[j]);
                        sup_list.add(supp);
                    }

                    //Food

                    Food food1 = null;
                    food_list = new ArrayList<>();
                    JSONArray food = object.getJSONArray("food");
                    for (int x = 0; x < food.length(); x++) {
                        food1 = new Food();
                        food1.setTime(food.getJSONObject(x).getString("time"));
                        food1.setTimings_id(food.getJSONObject(x).getString("timings_id"));
                        food1.setFood_mapping_id(food.getJSONObject(x).getString("food_mapping_id"));
                        food1.setFood_name(food.getJSONObject(x).getString("food_name"));
                        food1.setWhen(food.getJSONObject(x).getString("when"));
                        food1.setCompliance(food.getJSONObject(x).getString("compliance") + "%");
                        food1.setInt_compliance(food.getJSONObject(x).getInt("compliance"));
                        food1.setProgressBarRes(progressBarRes[j]);
                        food1.setColour(lineColour[j]);
                        food_list.add(food1);
                    }

                    //lifestyle

                    life_list = new ArrayList<>();
                    LifeStyle style = null;
                    JSONArray lifestyle = object.getJSONArray("lifestyle");
                    for (int x = 0; x < lifestyle.length(); x++) {
                        style = new LifeStyle();
                        style.setTime(lifestyle.getJSONObject(x).getString("time"));
                        style.setTimings_id(lifestyle.getJSONObject(x).getString("timings_id"));
                        style.setLifestyle_mapping_id(lifestyle.getJSONObject(x).getString("lifestyle_mapping_id"));
                        style.setLifestyle_name(lifestyle.getJSONObject(x).getString("lifestyle_name"));
                        style.setTime(lifestyle.getJSONObject(x).getString("time"));
                       // style.setRepitition(lifestyle.getJSONObject(x).getString("repitition"));
                       // style.setWhen(lifestyle.getJSONObject(x).getString("when"));
                        style.setCompliance(lifestyle.getJSONObject(x).getString("compliance") + "%");
                        style.setInt_compliance(lifestyle.getJSONObject(x).getInt("compliance"));
                        style.setGap(lifestyle.getJSONObject(x).getString("gap"));
                        style.setFreequency(lifestyle.getJSONObject(x).getString("frequency"));
                        style.setProgressBarRes(progressBarRes[j]);
                        style.setColour(lineColour[j]);
                        life_list.add(style);
                    }

                    //others
                    Others other = null;
                    other_list = new ArrayList<>();
                    JSONArray others = object.getJSONArray("others");
                    for (int x = 0; x < others.length(); x++) {
                        other = new Others();
                        other.setTime(others.getJSONObject(x).getString("time"));
                        other.setTimings_id(others.getJSONObject(x).getString("timings_id"));
                        other.setOthers_mapping_id(others.getJSONObject(x).getString("others_mapping_id"));
                        other.setOthers_name(others.getJSONObject(x).getString("others_name"));
                        other.setDuration(others.getJSONObject(x).getString("duration"));
                        other.setCompliance(others.getJSONObject(x).getString("compliance") + "%");
                        other.setInt_compliance(others.getJSONObject(x).getInt("compliance"));
                        other.setProgressBarRes(progressBarRes[j]);
                        other.setColour(lineColour[j]);
                        other_list.add(other);
                    }


//                    JSONArray workout_compliance = object.getJSONArray("workout_compliance");
//                    gru.setWorkout_compliance(workout_compliance.getJSONObject(0).getInt("workout_compliance"));
//
//                    JSONArray supplement_compliance = object.getJSONArray("supplement_compliance");
//                    gru.setSupplement_compliance(supplement_compliance.getJSONObject(0).getInt("supplement_compliance"));
//
//                    JSONArray lifestyle_compliance = object.getJSONArray("lifestyle_compliance");
//                    gru.setLifestyle_compliance(lifestyle_compliance.getJSONObject(0).getInt("lifestyle_compliance"));
//
//                    JSONArray food_compliance = object.getJSONArray("food_compliance");
//                    gru.setFood_compliance(food_compliance.getJSONObject(0).getInt("food_compliance"));
//
//                    JSONArray others_compliance = object.getJSONArray("others_compliance");
//                    gru.setOthers_compliance(others_compliance.getJSONObject(0).getInt("others_compliance"));
						/*}*/

                    gru.setItems(work_list);
                    gru.setFood_Items(food_list);
                    gru.setLife_Items(life_list);
                    gru.setSup_Items(sup_list);
                    gru.setOther_Items(other_list);
                    list.add(gru);


                }


            } else {

            }

        } catch (JSONException j) {
            Log.d("test",result);

            j.printStackTrace();
        }

    }

}


