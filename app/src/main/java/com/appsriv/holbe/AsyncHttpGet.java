package com.appsriv.holbe;

import android.os.AsyncTask;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

/**
 * Created by amit on 11/26/15.
 */
public class AsyncHttpGet extends AsyncTask<String, String, String> {

    String str="";
    JSONObject data=new JSONObject();
    private OnTaskCompleted listener;
    private String identifier;

    private HashMap<String, String> mData = null;// post data

    /**
     * constructor
     */
    public AsyncHttpGet(String identifier1, OnTaskCompleted listener1, HashMap<String, String> data) {
        mData = data;
        listener=listener1;
        identifier=identifier1;
    }

    /**
     * background
     */
    @Override
    protected String doInBackground(String... params) {
        byte[] result = null;
        str = "";
        HttpClient client = new DefaultHttpClient();
        HttpGet post = new HttpGet(params[0]);// in this case, params[0] is URL
        try {
            // set up post data
            //StringEntity param = new StringEntity(data.toString());
            //post.setEntity(param);
            ArrayList<NameValuePair> nameValuePair = new ArrayList<NameValuePair>();
            Iterator<String> it = mData.keySet().iterator();
            while (it.hasNext()) {
                String key = it.next();
                nameValuePair.add(new BasicNameValuePair(key, mData.get(key)));
            }

            //post.setEntity(new UrlEncodedFormEntity(nameValuePair, "UTF-8"));
            post.setHeader("Content-Type","application/json");
            HttpResponse response = client.execute(post);
            StatusLine statusLine = response.getStatusLine();
            //Log.e("status", "" + statusLine.getStatusCode() + params[0]);
            if(statusLine.getStatusCode() == HttpURLConnection.HTTP_OK){
                result = EntityUtils.toByteArray(response.getEntity());
                str = new String(result, "UTF-8");
                //Log.e("res","res"+str);
            }
        }
        catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        catch (Exception e) {
        }
        return str;

    }





    /**
     * on getting result
     */
    @Override
    protected void onPostExecute(String result) {
        // something...
        listener.onTaskCompleted(identifier,result);

    }
}
