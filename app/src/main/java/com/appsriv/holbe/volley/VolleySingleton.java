package com.appsriv.holbe.volley;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.appsriv.holbe.util.GoogleAnalyticsApplication;

/**
 * Created by AppsrivTech on 5/27/16.
 */
public class VolleySingleton
{
    private static VolleySingleton sInstance=null;
    RequestQueue mRequestQueue;
    private VolleySingleton(){
        mRequestQueue = Volley.newRequestQueue(GoogleAnalyticsApplication.getAppContext());

    }

    public static VolleySingleton getsInstance(){
        if(sInstance==null)
        {
            sInstance=new VolleySingleton();
        }
        return sInstance;
    }

    public RequestQueue getmRequestQueue(){
        return mRequestQueue;
    }
}
