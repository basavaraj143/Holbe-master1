package com.appsriv.holbe.util;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;

import com.appsriv.holbe.R;
import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.Tracker;

/**
 * Created by Vamsi Tallapudi on 24-Oct-15.
 */
public class GoogleAnalyticsApplication extends Application {

    private Tracker mTracker;

    /**
     * Gets the default {@link Tracker} for this {@link Application}.
     * @return tracker
     */
    synchronized public Tracker getDefaultTracker() {
        if (mTracker == null) {
            GoogleAnalytics analytics = GoogleAnalytics.getInstance(this);
            // Setting mTracker to Analytics Tracker declared in our xml Folder
            mTracker = analytics.newTracker(R.xml.analytics_tracker);
        }
        return mTracker;
    }


    //volley

    private  static GoogleAnalyticsApplication sInstance;
    @Override
    public void onCreate(){
        super.onCreate();
        sInstance=this;
    }

    public static GoogleAnalyticsApplication getsInstance(){
        return sInstance;
    }

    public static Context getAppContext(){
        return sInstance.getApplicationContext();
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(newBase);
        MultiDex.install(this);
    }
}