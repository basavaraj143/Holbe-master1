package com.appsriv.holbe;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.widget.ImageView;

import com.appsriv.holbe.imageutils.FileCache;
import com.appsriv.holbe.imageutils.MemoryCache;
import com.appsriv.holbe.imageutils.Utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.WeakHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by amit on 12/10/15.
 */
public class ImageLoaderProfile {

    MemoryCache memoryCache=new MemoryCache();
    FileCache fileCache;
    private Map<ImageView, String> imageViews= Collections.synchronizedMap(new WeakHashMap<ImageView, String>());
    ExecutorService executorService;
    int stub_id=0;

    public ImageLoaderProfile(Context context){
        fileCache=new FileCache(context);
        executorService= Executors.newFixedThreadPool(5);

        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> taskInfo = am.getRunningTasks(1);
        Log.e("topActivity", "CURRENT Activity ::" + taskInfo.get(0).topActivity.getClassName());
        if((!taskInfo.get(0).topActivity.getClassName().equalsIgnoreCase("com.dignitas.myish.UserProfile"))&&
                (!taskInfo.get(0).topActivity.getClassName().equalsIgnoreCase("com.dignitas.myish.UserProfileTwo"))&&
                (!taskInfo.get(0).topActivity.getClassName().equalsIgnoreCase("com.dignitas.myish.UserProfileThree"))&&
                (!taskInfo.get(0).topActivity.getClassName().equalsIgnoreCase("com.dignitas.myish.SearchUsers"))&&
                (!taskInfo.get(0).topActivity.getClassName().equalsIgnoreCase("com.dignitas.myish.viewPost"))&&
                (!taskInfo.get(0).topActivity.getClassName().equalsIgnoreCase("com.dignitas.myish.Welcome"))&&
                (!taskInfo.get(0).topActivity.getClassName().equalsIgnoreCase("com.dignitas.myish.Tour"))&&
                (!taskInfo.get(0).topActivity.getClassName().equalsIgnoreCase("com.dignitas.myish.Search")))
                {
            //stub_id=R.drawable.profile;
                    stub_id=0;
        }
    }


    public void DisplayImage(final String url, final ImageView imageView)
    {
        new Thread() {

            public void run() {
                //your "file checking code" goes here like this
                //write your results to log cat, since you cant do Toast from threads without handlers also...

                try {
                    HttpURLConnection.setFollowRedirects(false);
                    // note : you may also need
                    //HttpURLConnection.setInstanceFollowRedirects(false)

                    HttpURLConnection con =  (HttpURLConnection) new URL(url).openConnection();
                    con.setRequestMethod("HEAD");
                    if( (con.getResponseCode() == HttpURLConnection.HTTP_OK) ) {
                        //Log.e("FILE_EXISTS", "true");
                        imageViews.put(imageView, url);
                        Bitmap bitmap=memoryCache.get(url);
                        if(bitmap!=null) {
                            Log.e("Bitmap", "check");
                            imageView.setImageBitmap(bitmap);
                        }
                        else
                        {
                            queuePhoto(url, imageView);
                            //imageView.setImageResource(stub_id);
                        }
                    }
                    else
                        Log.e("FILE_EXISTS", "false");
                }
                catch (Exception e) {
                    e.printStackTrace();
                    Log.e("FILE_EXISTS", "false");;
                }
            }
        }.start();

    }

    private void queuePhoto(String url, ImageView imageView)
    {
        PhotoToLoad p=new PhotoToLoad(url, imageView);
        executorService.submit(new PhotosLoader(p));
    }

    private Bitmap getBitmap(String url)
    {
        File f=fileCache.getFile(url);

        //from SD cache
        Bitmap b = decodeFile(f);
        if(b!=null)
            return b;

        //from web
        try {
            Bitmap bitmap=null;
            URL imageUrl = new URL(url);
            HttpURLConnection conn = (HttpURLConnection)imageUrl.openConnection();
            conn.setConnectTimeout(30000);
            conn.setReadTimeout(30000);
            conn.setInstanceFollowRedirects(true);
            InputStream is=conn.getInputStream();
            OutputStream os = new FileOutputStream(f);
            Utils.CopyStream(is, os);
            os.close();
            bitmap = decodeFile(f);
            return bitmap;
        } catch (Throwable ex){
            ex.printStackTrace();
            if(ex instanceof OutOfMemoryError)
                memoryCache.clear();
            return null;
        }
    }

    //decodes image and scales it to reduce memory consumption
    private Bitmap decodeFile(File f){
        try {
            //decode image size
            BitmapFactory.Options o = new BitmapFactory.Options();
            o.inJustDecodeBounds = true;
            BitmapFactory.decodeStream(new FileInputStream(f),null,o);

            //Find the correct scale value. It should be the power of 2.
            final int REQUIRED_SIZE=200;
            int width_tmp=o.outWidth, height_tmp=o.outHeight;
            int scale=1;
            while(true){
                if(width_tmp/2<REQUIRED_SIZE || height_tmp/2<REQUIRED_SIZE)
                    break;
                width_tmp/=2;
                height_tmp/=2;
                scale*=2;
            }

            //decode with inSampleSize
            BitmapFactory.Options o2 = new BitmapFactory.Options();
            o2.inSampleSize=scale;
            return BitmapFactory.decodeStream(new FileInputStream(f), null, o2);
        } catch (FileNotFoundException e) {}
        return null;
    }

    //Task for the queue
    private class PhotoToLoad
    {
        public String url;
        public ImageView imageView;
        public PhotoToLoad(String u, ImageView i){
            url=u;
            imageView=i;
        }
    }

    class PhotosLoader implements Runnable {
        PhotoToLoad photoToLoad;
        PhotosLoader(PhotoToLoad photoToLoad){
            this.photoToLoad=photoToLoad;
        }

        @Override
        public void run() {
            if(imageViewReused(photoToLoad))
                return;
            Bitmap bmp=getBitmap(photoToLoad.url);
            memoryCache.put(photoToLoad.url, bmp);
            if(imageViewReused(photoToLoad))
                return;
            BitmapDisplayer bd=new BitmapDisplayer(bmp, photoToLoad);
            Activity a=(Activity)photoToLoad.imageView.getContext();
            a.runOnUiThread(bd);
        }
    }

    boolean imageViewReused(PhotoToLoad photoToLoad){
        String tag=imageViews.get(photoToLoad.imageView);
        if(tag==null || !tag.equals(photoToLoad.url))
            return true;
        return false;
    }


    //Used to display bitmap in the UI thread
    class BitmapDisplayer implements Runnable
    {
        Bitmap bitmap;
        PhotoToLoad photoToLoad;
        public BitmapDisplayer(Bitmap b, PhotoToLoad p){bitmap=b;photoToLoad=p;}
        public void run()
        {
            if(imageViewReused(photoToLoad))
                return;
            if(bitmap!=null)
                photoToLoad.imageView.setImageBitmap(bitmap);
            else
                photoToLoad.imageView.setImageResource(stub_id);
        }
    }

    public void clearCache() {
        memoryCache.clear();
        fileCache.clear();
    }

}
