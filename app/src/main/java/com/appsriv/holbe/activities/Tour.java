package com.appsriv.holbe.activities;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.appsriv.holbe.CustomVIewPageAdapter;
import com.appsriv.holbe.Adatpters.CustomViewPager;
import com.appsriv.holbe.R;

public class Tour extends Activity {
    CustomViewPager viewPager;
    boolean check=true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        // hide statusbar of Android
        // could also be done later
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.tour);
        viewPager = (CustomViewPager) findViewById(R.id.viewPager);
        PagerAdapter adapter = new CustomVIewPageAdapter(Tour.this);
        viewPager.setAdapter(adapter);
        viewPager.setAllowedSwipeDirection(CustomViewPager.SwipeDirection.right);

        viewPager.setOnSwipeOutListener(new CustomViewPager.OnSwipeOutListener()
        { // the method never called
            @Override
            public void onSwipeOutAtStart()
            {
                Log.e("swipe Out At Start ", "swipe out");
            }

            @Override
            public void onSwipeOutAtEnd()
            {
                Log.e("swipe Out At End ", "swipe end");
                if(check)
                {
                    check=false;
                    finish();
                }
            }
        });


    }

    public void finish(View v){
        finish();
    }


}
