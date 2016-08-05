package com.appsriv.holbe.activities;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.appsriv.holbe.CominUpWithListview;
import com.appsriv.holbe.Adatpters.CustomListDrawerAdapter;
import com.appsriv.holbe.DrawerActivity;
import com.appsriv.holbe.util.GoogleAnalyticsApplication;
import com.appsriv.holbe.R;
import com.appsriv.holbe.fragments.TabFragment;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.koushikdutta.urlimageviewhelper.UrlImageViewHelper;

public class ProfileActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {


    FragmentManager mFragmentManager;
    FragmentTransaction mFragmentTransaction;
    Tracker mTracker;
    boolean flag= true;


    DrawerLayout drawerLayout;
    View drawerView;
    // TextView textPrompt, textPrompt2;
    ListView drawerList;
    // TextView textSelection;
    CustomListDrawerAdapter customListDrawerAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
       // requestWindowFeature(Window.FEATURE_NO_TITLE);
       // getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.treatment_drawer);
        /*Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        TextView edit = (TextView)toolbar.findViewById(R.id.edit);
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ProfileActivity.this,SettingActivity.class));
            }
        });*/

        drawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);
        drawerView = (View)findViewById(R.id.drawer);

       /* if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window w = getWindow(); // in Activity's onCreate() for instance
            w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }*/

        TextView edit = (TextView)findViewById(R.id.edit);
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ProfileActivity.this,SettingActivity.class));
            }
        });
        GoogleAnalyticsApplication application = (GoogleAnalyticsApplication) getApplicationContext();
        mTracker = application.getDefaultTracker();


        mTracker.send(new HitBuilders.EventBuilder()
                .setCategory("Profile screen")
                .setAction(" Profile screen")
                .setLabel("Profile screen")
                .build());


        ImageView prof_picture = (ImageView)findViewById(R.id.prof_picture);
        //Picasso.with(ProfileActivity.this).load("http://192.185.26.69/~holbe/api/patient/images/IMG_20160512_160617.jpg").into(prof_picture);
        UrlImageViewHelper.setUrlDrawable(prof_picture, Login.details.get("user_profile_picture"));
       // overridePendingTransition(R.anim.scale_from_corner, R.anim.scale_to_corner);
        ImageView buttonOpenDrawer = (ImageView)findViewById(R.id.id);
        buttonOpenDrawer.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View arg0) {
                drawerLayout.openDrawer(drawerView);
            }});

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
        customListDrawerAdapter = new CustomListDrawerAdapter(ProfileActivity.this,names,img,"#ABD14B");

        TextView prof_name = (TextView)findViewById(R.id.name);
        TextView city =(TextView)findViewById(R.id.city);
        ImageView prof_pic = (ImageView)findViewById(R.id.prof_pic);
        if (Login.details.size()!=0)
        {
            prof_name.setText(Login.details.get("userFirstName"));
            city.setText(Login.details.get("userCity"));
            UrlImageViewHelper.setUrlDrawable(prof_pic,Login.details.get("user_profile_picture"));
        }

        TextView name = (TextView)findViewById(R.id.prof_name);
        name.setText(Login.details.get("userFirstName"));
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
                    startActivity(new Intent(ProfileActivity.this,DashBordActivity.class));


                } else if (position==1)
                {
                    selected_item.setBackgroundColor(Color.parseColor("#ABD14B"));
                    icon.setBackgroundResource(R.drawable.calendarblue);
                    startActivity(new Intent(ProfileActivity.this,LatestComingUp.class));


                } else if (position==2)
                {
                    icon.setBackgroundResource(R.drawable.userblue);
                    selected_item.setBackgroundColor(Color.parseColor("#ABD14B"));
                    startActivity(new Intent(ProfileActivity.this,ProfileActivity.class));


                }
                else if (position==3)
                {
                   /* selected_item.setBackgroundColor(Color.parseColor("#ABD14B"));
                    icon.setBackgroundResource(R.drawable.comingupblue);
                    startActivity(new Intent(ProfileActivity.this,CominUpWithListview.class));*/

                }

                else if (position==5)
                {
                    selected_item.setBackgroundColor(Color.parseColor("#ABD14B"));
                    icon.setBackgroundResource(R.drawable.settingsblue);
                    startActivity(new Intent(ProfileActivity.this,SettingActivity.class));


                } else if (position==6)
                {
                    selected_item.setBackgroundColor(Color.parseColor("#ABD14B"));
                    icon.setBackgroundResource(R.drawable.logoff);
                    startActivity(new Intent(ProfileActivity.this,Splash.class));

                }
            }});

        final ImageView active = (ImageView)findViewById(R.id.active);

        active.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                if (flag)
                {
                    flag=false;
                    active.setBackgroundResource(R.drawable.activebtn);
                }
                else
                {
                    flag=true;
                    active.setBackgroundResource(R.drawable.inactivebutton);
                }
            }
        });


        mFragmentManager = getSupportFragmentManager();
        mFragmentTransaction = mFragmentManager.beginTransaction();
        mFragmentTransaction.replace(R.id.containerView,new TabFragment()).commit();

       /* DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);

       navigationView.setNavigationItemSelectedListener(this);
        View header=navigationView.getHeaderView(0);
*//*View view=navigationView.inflateHeaderView(R.layout.nav_header_main);*//*
        TextView prof_name = (TextView)header.findViewById(R.id.name);
        ImageView prof_pic = (ImageView)header.findViewById(R.id.prof_pic);
        TextView city =(TextView)header.findViewById(R.id.city);
        if (Login.details.size()!=0)
        {
            prof_name.setText(Login.details.get("userFirstName"));
            city.setText(Login.details.get("userCity"));
          //  Picasso.with(ProfileActivity.this).load("http://192.185.26.69/~holbe/api/patient/images/IMG_20160512_160617.jpg").into(prof_pic);
            UrlImageViewHelper.setUrlDrawable(prof_pic,Login.details.get("user_profile_picture"));
        }
        TextView name = (TextView)findViewById(R.id.name);
        name.setText(Login.details.get("userFirstName"));*/
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
    protected void onResume()
    {
        super.onResume();
        mTracker.setScreenName("Profile Screen ");
        mTracker.send(new HitBuilders.ScreenViewBuilder().build());
    }

    @Override
    protected void onStart() {
        super.onStart();
        mTracker.setScreenName("Profile Screen ");
        mTracker.send(new HitBuilders.ScreenViewBuilder().build());
    }
    @Override
    public void onBackPressed() {
       super.onBackPressed();
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item)
    {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.overview)
        {
            startActivity(new Intent(ProfileActivity.this,DashBordActivity.class));

        } else if (id == R.id.mytreatment)
        {
            startActivity(new Intent(ProfileActivity.this,DrawerActivity.class));

        } else if (id == R.id.profile)
        {
            startActivity(new Intent(ProfileActivity.this,ProfileActivity.class));

        }
        else if (id == R.id.comingup)
        {
            startActivity(new Intent(ProfileActivity.this,CominUpWithListview.class));
        }

        else if (id == R.id.setting)
        {
            startActivity(new Intent(ProfileActivity.this,SettingActivity.class));

        } else if (id == R.id.logout)
        {
            startActivity(new Intent(ProfileActivity.this,Splash.class));
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
