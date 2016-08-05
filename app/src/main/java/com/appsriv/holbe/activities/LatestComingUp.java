package com.appsriv.holbe.activities;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;

import com.appsriv.holbe.ExpandListAdapter;
import com.appsriv.holbe.R;
import com.appsriv.holbe.fragments.FragmentForComingUp;
import com.appsriv.holbe.interfaces.ActivityCommunicator;
import com.appsriv.holbe.interfaces.FragmentCommunicator;
import com.appsriv.holbe.models.Group;
import com.appsriv.holbe.models.Workout;
import com.appsriv.holbe.util.GoogleAnalyticsApplication;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.koushikdutta.urlimageviewhelper.UrlImageViewHelper;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class LatestComingUp extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener , ActivityCommunicator
{
    private TabLayout tabLayout;
    private ExpandListAdapter ExpAdapter;
    private ArrayList<Group> ExpListItems;
    private ExpandableListView ExpandList;
    ArrayList<Group> list;
    private int flag[];
    public static TextView prof_name;
    String name[];
    String lineColour[];
    ArrayList<Workout> ch_list = null;
    private ViewPager viewPager;
    public static TextView top,top1,top2,top3,top4;
    Tracker mTracker;
    public static String formattedDate;
    public static SimpleDateFormat df;
    public static ImageView date;
    public static int year;
    public static int month;
    public static int day;
    public static ImageView previousdate;
    public static ImageView nextdate;
    public static Calendar c;
    FragmentForComingUp fragmentForComingUp;
    //interface through which communication is made to fragment
    public FragmentCommunicator fragmentCommunicator;

    ViewPagerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawer);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        tabLayout = (TabLayout) findViewById(R.id.tabs);
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);
        tabLayout.setupWithViewPager(viewPager);
        setupTabIcons();
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        c = Calendar.getInstance();

        year = c.get(Calendar.YEAR);
        month = c.get(Calendar.MONTH);
        day = c.get(Calendar.DAY_OF_MONTH);

        fragmentForComingUp = new FragmentForComingUp();

       // fragmentCommunicator = (FragmentCommunicator)getBaseContext();
        date = (ImageView) toolbar.findViewById(R.id.date);
      /*  previousdate = (ImageView)toolbar.findViewById(R.id.previousdate);
        nextdate = (ImageView)toolbar.findViewById(R.id.nextdate);*/


        df = new SimpleDateFormat("EE-yyyy-MM-dd");

        formattedDate = df.format(c.getTime());

        String ct = DateFormat.getDateInstance().format(new Date());
       // date.setText(formattedDate);


        //date picker when we click on date
        /*date.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                DatePickerDialog dpd = new DatePickerDialog(LatestComingUp.this,
                        new DatePickerDialog.OnDateSetListener()
                        {

                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth)
                            {

                                SimpleDateFormat sdf = new SimpleDateFormat("EE");
                                Date d = new Date(year, monthOfYear, dayOfMonth-1);
                                String dayOfTheWeek = sdf.format(d);
                                String data = year + "-" + (monthOfYear + 1) + "-" + dayOfMonth;
                                //Log.d("data " ,"date " +data);
                                fragmentCommunicator.passDataToFragment(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);
                                Util.flag = false;
                                adapter.notifyDataSetChanged();



                            }
                        }, year, month, day);
                dpd.show();
            }
        });*/
      /*  //Previous date onclick
        previousdate.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                c.add(Calendar.DATE, -1);
                formattedDate = df.format(c.getTime());

                Log.v("PREVIOUS DATE : ", formattedDate);
                date.setText(formattedDate);
            }
        });


        //nextdate onlick
        nextdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                c.add(Calendar.DATE, 1);
                formattedDate = df.format(c.getTime());

                Log.v("NEXT DATE : ", formattedDate);
                date.setText(formattedDate);
            }
        });

*/
      /*  String str[] ={"Basu","Basavaraj","BasavaraKing"};
        ListView listView =(ListView)drawer.findViewById(R.id.list);
        listView.setAdapter(new ArrayAdapter<String>(DrawerActivity.this,android.R.layout.simple_dropdown_item_1line,str));
*/
        GoogleAnalyticsApplication application = (GoogleAnalyticsApplication) getApplicationContext();
        mTracker = application.getDefaultTracker();


        mTracker.send(new HitBuilders.EventBuilder()
                .setCategory("Treatment screen")
                .setAction(" Treatment screen")
                .setLabel("Treatment screen")
                .build());

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        View header=navigationView.getHeaderView(0);

/*View view=navigationView.inflateHeaderView(R.layout.nav_header_main);*/
        prof_name = (TextView)header.findViewById(R.id.name);
        ImageView prof_pic = (ImageView)header.findViewById(R.id.prof_pic);
        TextView city =(TextView)header.findViewById(R.id.city);
        if (Login.details.size()!=0) {
            prof_name.setText(Login.details.get("userFirstName"));
            //Picasso.with(DrawerActivity.this).load("http://192.185.26.69/~holbe/api/patient/images/IMG_20160512_160617.jpg").into(prof_pic);
            UrlImageViewHelper.setUrlDrawable(prof_pic,Login.details.get("user_profile_picture"));
           // city.setText(Login.details.get("userCity"));
        }
    }


    @Override
    protected void onResume()
    {
        super.onResume();
        mTracker.setScreenName("Treatment Screen ");
        mTracker.send(new HitBuilders.ScreenViewBuilder().build());
    }

    @Override
    protected void onStart() {
        super.onStart();
        mTracker.setScreenName("Treatment Screen ");
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
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();


        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item)
    {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.overview)
        {
           startActivity(new Intent(LatestComingUp.this,DashBordActivity.class));

        } else if (id == R.id.mytreatment)
        {
            startActivity(new Intent(LatestComingUp.this,LatestComingUp.class));

        } else if (id == R.id.profile)
        {
            startActivity(new Intent(LatestComingUp.this,ProfileActivity.class));

        }
        else if (id == R.id.comingup)
        {
            //startActivity(new Intent(DrawerActivity.this,CominUpWithListview.class));
        }

        else if (id == R.id.setting)
        {
            startActivity(new Intent(LatestComingUp.this,SettingActivity.class));

        } else if (id == R.id.logout)
        {
            startActivity(new Intent(LatestComingUp.this,Splash.class));
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    public void reloadFragment() {
        adapter.notifyDataSetChanged();
    }

    private void setupViewPager(ViewPager viewPager)
    {
        adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFrag(new FragmentForComingUp(), "ONE");
        adapter.addFrag(new FragmentForComingUp(), "TWO");
        adapter.addFrag(new FragmentForComingUp(), "THREE");
        adapter.addFrag(new FragmentForComingUp(), "Four");
        adapter.addFrag(new FragmentForComingUp(), "Five");
        viewPager.setAdapter(adapter);
    }

    @Override
    public void passDataToActivity(ArrayList<Group> list)
    {

    }

    class ViewPagerAdapter extends FragmentStatePagerAdapter
    {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager)
        {
            super(manager);
        }


        @Override
        public Fragment getItem(int position) {
            Log.d("clciked","ck");
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }



        public void addFrag(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
        @Override
        public int getItemPosition(Object object) {
            // POSITION_NONE makes it possible to reload the PagerAdapter
            return POSITION_NONE;
        }


    }

    private void setupTabIcons() {

        View layoutInflater = LayoutInflater.from(LatestComingUp.this).inflate(R.layout.custom_tab_layout,null);
        top = (TextView)layoutInflater.findViewById(R.id.top);
        TextView bottom = (TextView)layoutInflater.findViewById(R.id.bottom);
        ImageView icon =(ImageView)layoutInflater.findViewById(R.id.icon);
        icon.setBackgroundResource(R.drawable.supplements);
        bottom.setText("Supplements");
        top.setText("");
        top.setBackgroundResource(R.drawable.supplement_circle);
        layoutInflater.setBackgroundColor(Color.parseColor("#ABD14B"));
        tabLayout.getTabAt(0).setCustomView(layoutInflater);

        View layoutInflater1 =LayoutInflater.from(LatestComingUp.this).inflate(R.layout.custom_tab_layout,null);
        top1 = (TextView)layoutInflater1.findViewById(R.id.top);
        TextView bottom1 = (TextView)layoutInflater1.findViewById(R.id.bottom);
        ImageView icon1 =(ImageView)layoutInflater1.findViewById(R.id.icon);
        icon1.setBackgroundResource(R.drawable.workouts);
        bottom1.setText("Workouts");
        bottom1.setTextSize(11);
        top1.setText("");
        top1.setBackgroundResource(R.drawable.workout_circle);
        layoutInflater1.setBackgroundColor(Color.parseColor("#3CC3AF"));
        tabLayout.getTabAt(1).setCustomView(layoutInflater1);

        View layoutInflater2 =LayoutInflater.from(LatestComingUp.this).inflate(R.layout.custom_tab_layout,null);
        top2 = (TextView)layoutInflater2.findViewById(R.id.top);
        TextView bottom2 = (TextView)layoutInflater2.findViewById(R.id.bottom);
        ImageView icon2 =(ImageView)layoutInflater2.findViewById(R.id.icon);
        icon2.setBackgroundResource(R.drawable.lifestyles);
        bottom2.setText("Lifestyles");
        top2.setText("");
        bottom2.setTextSize(11);
        top2.setBackgroundResource(R.drawable.lifestyle_circle);
        layoutInflater2.setBackgroundColor(Color.parseColor("#1AA2DF"));
        tabLayout.getTabAt(2).setCustomView(layoutInflater2);

        View layoutInflater3 =LayoutInflater.from(LatestComingUp.this).inflate(R.layout.custom_tab_layout,null);
        top3 = (TextView)layoutInflater3.findViewById(R.id.top);
        TextView bottom3 = (TextView)layoutInflater3.findViewById(R.id.bottom);
        ImageView icon3 =(ImageView)layoutInflater3.findViewById(R.id.icon);
        icon3.setBackgroundResource(R.drawable.foodadndrink);
        bottom3.setText("Food & Drinks");
        bottom3.setTextSize(11);
        top3.setText("");
        top3.setBackgroundResource(R.drawable.foodanddrinks_circle);
        layoutInflater3.setBackgroundColor(Color.parseColor("#AA68B4"));
        tabLayout.getTabAt(3).setCustomView(layoutInflater3);

        View layoutInflater4 =LayoutInflater.from(LatestComingUp.this).inflate(R.layout.custom_tab_layout,null);
        top4 = (TextView)layoutInflater4.findViewById(R.id.top);
        TextView bottom4 = (TextView)layoutInflater4.findViewById(R.id.bottom);
        ImageView icon4 =(ImageView)layoutInflater4.findViewById(R.id.icon);
        icon4.setBackgroundResource(R.drawable.foodadndrink);
        bottom4.setText("Others");
        bottom4.setTextSize(11);
        top4.setText("");
        top4.setBackgroundResource(R.drawable.circle);
        layoutInflater4.setBackgroundColor(Color.parseColor("#BD345E"));
        tabLayout.getTabAt(4).setCustomView(layoutInflater4);


    }

}
