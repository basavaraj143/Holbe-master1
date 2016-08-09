package com.appsriv.holbe.Adatpters;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AbsListView;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.NumberPicker;
import android.widget.SectionIndexer;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.appsriv.holbe.R;
import com.appsriv.holbe.models.Food;
import com.appsriv.holbe.models.Group;
import com.appsriv.holbe.models.LifeStyle;
import com.appsriv.holbe.models.Others;
import com.appsriv.holbe.models.Supplement;
import com.appsriv.holbe.models.Treatment;
import com.appsriv.holbe.models.Workout;
import com.appsriv.holbe.util.Util;
import com.appsriv.holbe.volley.VolleySingleton;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class ExpandListAdapterForComingUp extends BaseExpandableListAdapter implements SectionIndexer, AbsListView.OnScrollListener {
    private boolean manualScroll;
    private Context context;
    private ArrayList<Group> groups= new ArrayList<>();
    ExpandableListView expandableListView ;
    int[] progressBarRes;
    int pos=0;
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
    //int childPostion, groupPostion;
    int point;
    AlertDialog b;
    NumberPicker np;
    String num=null;
    CustomComingUpListAdapter comingUpListAdapter;
    public ExpandListAdapterForComingUp(Activity activity, String[] Items, String[] time, int [] icons, String[] excName, int [] background, String [] lineColour, ArrayList<Group> list,
                                        ExpandableListView expandableListView) {
        this.expandableListView = expandableListView;
        this.context = activity;
        this.Items = Items;
        this.time=time;
        this.icons=icons;
        this.excName=excName;
        this.background=background;
        this.lineColour = lineColour;
        this.groups = list;
        /*this.groupPostion = groupPostion;
        this.childPostion = childPostion;*/

    }


    @Override
    public Object[] getSections() {
        return new Object[0];
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        this.manualScroll = scrollState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL;


    }
    private int preLast;
    @Override
    public void onScroll(AbsListView view,
                         int firstVisibleItem,
                         int visibleItemCount,
                         int totalItemCount) {



    }

    @Override
    public int getPositionForSection(int section)
    {
        if (manualScroll) {
            return section;
        } else {
            return expandableListView.getFlatListPosition(
                    ExpandableListView.getPackedPositionForGroup(section));
        }
    }

    // Gets called when scrolling the list manually
    @Override
    public int getSectionForPosition(int position) {
        return ExpandableListView.getPackedPositionGroup(
                expandableListView.getExpandableListPosition(position));
    }


    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        int size = 0;


        if (groupPosition%5 == 0)
        {
            ArrayList<Supplement> chList = groups.get(groupPosition).getSup_Items();
            size = chList.size();

        } else if (groupPosition%5 == 1)
        {
            ArrayList<Workout> chList = groups.get(groupPosition).getItems();
            size = chList.size();


        } else if (groupPosition%5 == 2) {
            ArrayList<LifeStyle> chList = groups.get(groupPosition).getLife_Items();
            size = chList.size();
        } else if (groupPosition%5 == 3) {
            ArrayList<Food> chList = groups.get(groupPosition).getFood_Items();
            size = chList.size();
        } else if (groupPosition%5 == 4) {
            ArrayList<Others> chList = groups.get(groupPosition).getOther_Items();
            size = chList.size();
        }
       /* if (groupPosition%5==1)
        {
            return groups.get(groupPosition).getItems().size();
        }
        else {
            return 1;
        }*/
        return size;
    }

    @Override
    public int getChildType(int groupPosition, int childPosition) {
        return super.getChildType(groupPosition, childPosition);
    }

    @Override
    public boolean areAllItemsEnabled() {
        return super.areAllItemsEnabled();
    }


    @Override
    public Object getChild(int groupPosition, int childPosition)
    {
        ArrayList<Workout> chList = groups.get(groupPosition).getItems();
        return chList.get(childPosition);
    }

    public Object getChild1(int groupPosition, int childPosition)
    {

        ArrayList<Supplement> chList = groups.get(groupPosition).getSup_Items();
        return chList.get(childPosition);
    }

    public Object getChild5(int groupPosition, int childPosition)
    {

        ArrayList<Treatment> chList = groups.get(groupPosition).getTreatments();
        return chList.get(childPosition);
    }
    public Object getChild2(int groupPosition, int childPosition)
    {

        ArrayList<LifeStyle> chList = groups.get(groupPosition).getLife_Items();
        return chList.get(childPosition);
    }
    public Object getChild3(int groupPosition, int childPosition)
    {
        ArrayList<Food> chList = groups.get(groupPosition).getFood_Items();
        return chList.get(childPosition);
    }
    public Object getChild4(int groupPosition, int childPosition)
    {
        ArrayList<Others> chList = groups.get(groupPosition).getOther_Items();
        return chList.get(childPosition);
    }



    @Override
    public View getChildView(final int groupPosition, final int childPosition, boolean isLastChild, View convertView, final ViewGroup parent)
    {

        Log.i("check","cheild position "+childPosition+" group position " +groupPosition);
        Workout workout=null;
        Supplement supplement=null;
        LifeStyle style=null;
        Food food=null;
        Others others=null;
        Treatment treatment = null;

        int size=0;
         if ((groupPosition%5)==0)
        {
            //supplement = (Supplement) getChild1(groupPosition,childPosition);
           // size =  groups.get(groupPosition).getSup_Items().size();
            size =1;

        }
        else if ((groupPosition%5)==1)
        {
            //workout = (Workout) getChild(groupPosition, childPosition);
            size = groups.get(groupPosition).getItems().get(childPosition).getWorkout_name1().size();
            //size =1;

        }
        else if ((groupPosition%5)==2)
        {
            //style = (LifeStyle) getChild2(groupPosition,childPosition);
            //size = groups.get(groupPosition).getLife_Items().size();
            size =1;

        }
        else if ((groupPosition%5)==3)
        {
          //  food = (Food)getChild3(groupPosition,childPosition);
            //size = groups.get(groupPosition).getFood_Items().size();
            size =1;
        }
        else if ((groupPosition%5) == 4)
        {

                //others = (Others) getChild4(groupPosition, childPosition);
                //size = groups.get(groupPosition).getOther_Items().size();
            size =1;

        }


        if (groupPosition%5 == 1)
        {
            if (groups.get(groupPosition).getItems().get(childPosition).getCircuit_id()==null||groups.get(groupPosition).getItems().get(childPosition).getCircuit_id().equalsIgnoreCase("0"))
            {
                LayoutInflater infalInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
                final View dialogView = infalInflater.inflate(R.layout.latest_coming_childitem, null);

                DisplayMetrics display = context.getResources().getDisplayMetrics();
                int density = display.densityDpi;
                if (groupPosition%5==1) {
                    point = 43 * size;
                }
                else
                {
                    point = 20* size;
                }
                int pixels=(int) (point*density)/72;
                ListView listView = (ListView)convertView.findViewById(R.id.list);
               // comingUpListAdapter= new CustomComingUpListAdapter((Activity) context,groups,groupPosition,childPosition);
                listView.setAdapter( new CustomComingUpListAdapter((Activity) context,groups,groupPosition,childPosition));
                /*if (comingUpListAdapter!=null)
                {
                    comingUpListAdapter.notifyDataSetChanged();
                }*/
                listView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, pixels));
                listView.requestLayout();
            }
            else
            {
                LayoutInflater infalInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
                convertView = infalInflater.inflate(R.layout.latest_coming_childitem_second, null);
                DisplayMetrics display = context.getResources().getDisplayMetrics();
                int density = display.densityDpi;
                if (groupPosition%5 == 1)
                {
                    point = 43 * size;
                } else
                {
                    point = 20 * size;
                }
                int pixels = (int) (point * density) / 72;
                ListView listView = (ListView) convertView.findViewById(R.id.list);
               // comingUpListAdapter= new CustomComingUpListAdapter((Activity) context,groups,groupPosition,childPosition);
                listView.setAdapter(new CustomComingUpListAdapter((Activity) context,groups,groupPosition,childPosition));
               /* if (comingUpListAdapter!=null)
                {
                    comingUpListAdapter.notifyDataSetChanged();
                }*/
                listView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, pixels));
                listView.requestLayout();
                complete[groupPosition] = (ImageView) convertView.findViewById(R.id.completed);
                partial[groupPosition] = (ImageView) convertView.findViewById(R.id.partial);

                complete[groupPosition].setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final String URL = "http://192.185.26.69/~holbe/api/patient/updatecompliancenew.php";
                        volleypost(URL, groups.get(groupPosition).getOther_Items().get(childPosition).getTimings_id(), "1");
                    }
                });

                partial[groupPosition].setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v) {
                        showChangeLangDialog(groupPosition, childPosition);
                    }
                });
            }
        }
        else
        {
            LayoutInflater infalInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.latest_coming_childitem, null);
            DisplayMetrics display = context.getResources().getDisplayMetrics();
            int density = display.densityDpi;
            if (groupPosition%5==1) {
                point = 20 * size;
            }
            else
            {
                point = 70* size;
            }
            int pixels=(int) (point*density)/72;
           // comingUpListAdapter= new CustomComingUpListAdapter((Activity) context,groups,groupPosition,childPosition);
            ListView listView = (ListView)convertView.findViewById(R.id.list);
            listView.setAdapter(new CustomComingUpListAdapter((Activity) context,groups,groupPosition,childPosition));
           /* if (comingUpListAdapter!=null)
            {
                comingUpListAdapter.notifyDataSetChanged();
            }*/
            listView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, pixels));
            listView.requestLayout();
        }


        return convertView;
    }
    public void showChangeLangDialog(final int groupPosition , final int childPosition)
    {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(context);
        LayoutInflater infalInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        final View dialogView = infalInflater.inflate(R.layout.partial_layout, null);
        dialogBuilder.setView(dialogView);
        ImageView close = (ImageView)dialogView.findViewById(R.id.close);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                b.dismiss();
            }
        });
        //final ListView listView = (ListView)dialogView.findViewById(R.id.list);
        //final PartialWorkoutListCustomAdapter partialCustomAdapter = new PartialWorkoutListCustomAdapter(context,groups,groupPosition,childPosition);
       // listView.setAdapter(partialCustomAdapter);

        final ExpandableListView expandableListView = (ExpandableListView)dialogView.findViewById(R.id.list);
        int count = groups.get(groupPosition).getItems().get(childPosition).getSets1().size();
        PartialExpandListAdapterPopUp popUp = new PartialExpandListAdapterPopUp((Activity) context,Items,time,icons,excName,background,lineColour,groups,groupPosition,childPosition,expandableListView,count);
        expandableListView.setAdapter(popUp);

        dialogBuilder.setPositiveButton("Submit", new DialogInterface.OnClickListener()
        {
            public void onClick(DialogInterface dialog, int whichButton)
            {
                Log.i("list","values are "+PartialExpandListAdapterPopUp.arrayList.get("00"));
                Log.i("list","values are "+PartialExpandListAdapterPopUp.arrayList.get("01"));
                Log.i("list","values are "+PartialExpandListAdapterPopUp.arrayList.get("02"));

                Log.i("list","values are 1st"+PartialExpandListAdapterPopUp.arrayList.get("10"));
                Log.i("list","values are 1st"+PartialExpandListAdapterPopUp.arrayList.get("11"));
                Log.i("list","values are 1st "+PartialExpandListAdapterPopUp.arrayList.get("12"));
            }
        });
        dialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {

            }
        });
        b = dialogBuilder.create();
        b.show();
        b.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE  | WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
        b.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
        b.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);

    }

    private void volleypost(String url,final String id, final String completion)
    {
        RequestQueue requestQueue = VolleySingleton.getsInstance().getmRequestQueue();
        final ProgressDialog progressDialog = ProgressDialog.show(context, "Please wait", "Loading...");
        StringRequest jsonObjRequest = new StringRequest(Request.Method.POST,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Log.d("status",response);
                        if (progressDialog!=null&&progressDialog.isShowing())
                        {
                            progressDialog.dismiss();
                        }
                    }
                },
                new Response.ErrorListener()
                {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        VolleyLog.d("volley", "Error: " + error.getMessage());
                        error.printStackTrace();
                        if (progressDialog!=null&&progressDialog.isShowing())
                        {
                            progressDialog.dismiss();
                        }

                    }
                })

                {

                    @Override
                    public String getBodyContentType() {
                        return "application/x-www-form-urlencoded; charset=UTF-8";
                    }

                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> params = new HashMap<String, String>();
                        params.put("id", id);
                        params.put("completion", completion);
                        return params;
                    }

                };

        requestQueue.add(jsonObjRequest);


    }

    @Override
    public void onGroupCollapsed(int groupPosition) {
        super.onGroupCollapsed(groupPosition);

    }

    @Override
    public void onGroupExpanded(int groupPosition) {
        super.onGroupExpanded(groupPosition);
    }


    @Override
    public Object getGroup(int groupPosition) {
        return groups.get(groupPosition);
    }

    @Override
    public int getGroupCount()
    {
        return groups.size();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }


    @Override
    public int getChildTypeCount() {
        return super.getChildTypeCount();
    }


    @Override
    public View getGroupView(final int groupPosition, boolean isExpanded, View convertView, ViewGroup parent)
    {
        ExpandableListView mExpandableListView = (ExpandableListView) parent;
        mExpandableListView.expandGroup(groupPosition);
       if (convertView == null)
       {
            LayoutInflater inf = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            convertView = inf.inflate(R.layout.latest_comingup_group, null);
        }
        TextView date = (TextView)convertView.findViewById(R.id.date);
        if (groupPosition%5==0)
        {
            date.setVisibility(View.VISIBLE);
            if (Util.current_Date.equalsIgnoreCase(groups.get(groupPosition).getDate()))
            {
                date.setText("TODAY");

            }
            else {
                date.setText(groups.get(groupPosition).getDate());
            }
        }
        else
        {
            date.setVisibility(View.GONE);
        }

        ImageView icon = (ImageView)convertView.findViewById(R.id.icon);
        icon.setBackgroundResource(background[groupPosition%5]);
        TextView type = (TextView)convertView.findViewById(R.id.type);
        type.setText(Items[groupPosition%5]);
        return convertView;
    }


    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition)
    {
        return true;
    }


    public class AsyncHttpTask extends AsyncTask<String, Void, Integer>
    {

        ProgressDialog progressDialog;
        @Override
        protected void onPreExecute() {

            progressDialog = ProgressDialog.show(context,"Please wait","Loading...");
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
                urlConnection.setRequestMethod("POST");

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
        try {
            JSONObject object = new JSONObject(result);
            String statu= object.getString("status");
        }
        catch (JSONException e)
        {

            e.printStackTrace();
        }

    }

    private String stringconcat(String freq,String gap){
        String frequency = "once";
        switch(freq){
            case "1":frequency="Once";
                break;
            case "2":frequency="Twice";
                break;
            case "3":frequency="Thrice";
                break;
        }
        String result = frequency+" per "+gap;
        return result;
    }

}



