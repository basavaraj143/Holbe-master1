package com.appsriv.holbe;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.appsriv.holbe.models.Group;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class CustomListAdapter extends BaseAdapter {
    private Activity activity;
    private LayoutInflater inflater;
    private String[] Items;
    private String[] time;
    private int[] icons;
    private int[] background;
    private String[] excName;
    private String[] lineColour;
    private ArrayList<Group> list;
    private ImageView[] complete = new ImageView[1000];
    private ImageView[] partial = new ImageView[1000];
    int childPostion, groupPostion;

    public CustomListAdapter(Activity activity, String[] Items,String[] time,int [] icons,String[] excName,int [] background,String [] lineColour,ArrayList<Group> list,
                             int groupPostion, int childPostion)
    {
        this.activity = activity;
        this.Items = Items;
        this.time=time;
        this.icons=icons;
        this.excName=excName;
        this.background=background;
        this.lineColour = lineColour;
        this.list = list;
        this.groupPostion = groupPostion;
        this.childPostion = childPostion;
    }

    /*@Override
    public int getItemViewType(int position)
    {
        int size=0;
        if (position==0)
        {
            size= list.get(0).getItems().size();
        }
        else if (position == 1)
        {
            size= list.get(0).getSup_Items().size();
        }
        else if (position==2)
        {
            size=list.get(0).getLife_Items().size();
        }
        else if (position==3)
        {
            size= list.get(0).getFood_Items().size();
        }
        else if (position==4)
        {
            size=list.get(0).getOther_Items().size();
        }
        return size;
        //return super.getItemViewType(position);
    }

    @Override
    public int getViewTypeCount()
    {
        return 6;
    }
*/
   @Override
    public int getCount()
    {


        return list.get(0).getItems().size() + list.get(0).getSup_Items().size() + list.get(0).getLife_Items().size() + list.get(0).getFood_Items().size() + list.get(0).getOther_Items().size();

    }

    public int getCount(int position)

    {
        int size=0;
        if (position==0)
        {
            size = list.get(0).getItems().size();
        }
        else if (position==1)
        {
            size = list.get(1).getSup_Items().size();
        }
        else if (position==2)
        {
            size = list.get(2).getLife_Items().size();
        }
        else if (position==3)
        {
            size = list.get(3).getFood_Items().size();
        }
        else if (position==4)
        {
            size = list.get(3).getOther_Items().size();
        }

        return size;
    }

    @Override
    public Object getItem(int position)
    {
        int size=0;
        if (position==0)
        {
            size = list.get(0).getItems().size();
        }
        else if (position==1)
        {
            size = list.get(1).getSup_Items().size();
        }
        else if (position==2)
        {
            size = list.get(2).getLife_Items().size();
        }
        else if (position==3)
        {
            size = list.get(3).getFood_Items().size();
        }
        else if (position==4)
        {
            size = list.get(3).getOther_Items().size();
        }

        return size;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        getCount(position);

        if (inflater == null)
            inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null)
            convertView = inflater.inflate(R.layout.mylist_items, null);
        TextView type =(TextView) convertView.findViewById(R.id.type);
//        TextView repsandsitups = (TextView)convertView.findViewById(R.id.repsandsitups);
        TextView time1 =(TextView) convertView.findViewById(R.id.time);
        TextView name =(TextView) convertView.findViewById(R.id.name);
        complete[position]=(ImageView)convertView.findViewById(R.id.complete);
        partial[position]=(ImageView)convertView.findViewById(R.id.partial);

        if (position<=list.size())
        {
            name.setText(list.get(position).getItems().get(position).getWorkout_name());
//            repsandsitups.setText(list.get(position).getItems().get(position).getReps() + " Reps " + "of " + list.get(position).getItems().get(position).getSets() + " Situps");

        }
       // String s = list.get(0).getItems().get(0).getWorkout_name();


        ImageView icon1 =(ImageView)convertView.findViewById(R.id.icon);
        View line=(View)convertView.findViewById(R.id.line);
        line.setBackgroundColor(Color.parseColor(lineColour[position]));
        time1.setText(time[position]);
        icon1.setBackgroundResource(background[position]);
        //name.setText(excName[position]);
        type.setText(Items[position]);
        complete[position].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                final String url =  "http://192.185.26.69/~holbe/api/patient/update_compliance.php?completion=1&workout_id="+list.get(groupPostion).getItems().get(childPostion).getWorkout_mapping_id();
                new AsyncHttpTask().execute(url);
            }
        });

        partial[position].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                final String url =  "http://192.185.26.69/~holbe/api/patient/update_compliance.php?completion=0.5&workout_id="+list.get(groupPostion).getItems().get(childPostion).getWorkout_mapping_id();;
                new AsyncHttpTask().execute(url);

            }
        });


        return convertView;
    }


    public class AsyncHttpTask extends AsyncTask<String, Void, Integer>
    {

        ProgressDialog progressDialog;
        @Override
        protected void onPreExecute() {

            progressDialog = ProgressDialog.show(activity,"Please wait","Loading...");
        }

        @Override
        protected Integer doInBackground(String... params)
        {
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
                if (statusCode ==  200) {
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


                //  Log.d(TAG, e.getLocalizedMessage());
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


            if (result == 1)
            {
               if (progressDialog!=null&&progressDialog.isShowing())
               {
                   progressDialog.dismiss();
               }
            } else {

                // Log.e(TAG, "Failed to fetch data!");
            }
        }
    }
    private void parseResult(String result)
    {

        //{"status":200,"message":"Update successful"}
        try {
        JSONObject object = new JSONObject(result);
            String statu= object.getString("status");
        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }

    }



     /* if (position==0)
            {
                for (int i = 0 ; i<list.get(position).getItems().size(); i++) {*/

              /*      }
            }*/
           /* else if (position==1)
            {
                for (int i = 0 ; i<list.get(position).getSup_Items().size(); i++) {
                    name.setText(list.get(position).getSup_Items().get(i).getSupplement_name());
                  //  repsandsitups.setText(list.get(position).getItems().get(position).getReps() + " Reps " + "of " + list.get(position).getItems().get(position).getSets() + " Situps");
                }
            }
            else if (position ==2)
            {
                for (int i = 0 ; i<list.get(position).getLife_Items().size(); i++) {
                    name.setText(list.get(position).getLife_Items().get(i).getLifestyle_name());
                    //  repsandsitups.setText(list.get(position).getItems().get(position).getReps() + " Reps " + "of " + list.get(position).getItems().get(position).getSets() + " Situps");
                }
            }
            else if (position==3)
            {
                for (int i = 0 ; i<list.get(position).getFood_Items().size(); i++) {
                    name.setText(list.get(position).getFood_Items().get(i).getFood_name());
                    //  repsandsitups.setText(list.get(position).getItems().get(position).getReps() + " Reps " + "of " + list.get(position).getItems().get(position).getSets() + " Situps");
                }
            }
            if (position==4)
            {
                for (int i = 0 ; i<list.get(position).getOther_Items().size(); i++) {
                    name.setText(list.get(position).getOther_Items().get(i).getOthers_name());
                    //  repsandsitups.setText(list.get(position).getItems().get(position).getReps() + " Reps " + "of " + list.get(position).getItems().get(position).getSets() + " Situps");
                }
            }*/


}