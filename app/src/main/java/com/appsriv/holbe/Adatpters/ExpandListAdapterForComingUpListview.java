package com.appsriv.holbe.Adatpters;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.SectionIndexer;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.appsriv.holbe.models.Food;
import com.appsriv.holbe.models.Group;
import com.appsriv.holbe.models.LifeStyle;
import com.appsriv.holbe.models.Others;
import com.appsriv.holbe.R;
import com.appsriv.holbe.models.Supplement;
import com.appsriv.holbe.models.Treatment;
import com.appsriv.holbe.models.Workout;
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


public class ExpandListAdapterForComingUpListview extends BaseExpandableListAdapter implements SectionIndexer, AbsListView.OnScrollListener {
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


    public ExpandListAdapterForComingUpListview(Activity activity, String[] Items,String[] time,int [] icons,String[] excName,int [] background,String [] lineColour,ArrayList<Group> list,
                                                int groupPostion, int childPostion,ExpandableListView expandableListView) {
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

    @Override
    public void onScroll(AbsListView view,
                         int firstVisibleItem,
                         int visibleItemCount,
                         int totalItemCount) {
    }

    @Override
    public int getPositionForSection(int section) {
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
    public int getChildrenCount(int groupPosition)
    {
        int size = 0;


        if (groupPosition == 0)
        {
            ArrayList<Supplement> chList = groups.get(groupPosition).getSup_Items();
            size = chList.size();

        } else if (groupPosition == 1)
        {
            ArrayList<Workout> chList = groups.get(groupPosition).getItems();
            size = chList.size();


        } else if (groupPosition == 2) {
            ArrayList<LifeStyle> chList = groups.get(groupPosition).getLife_Items();
            size = chList.size();
        } else if (groupPosition == 3) {
            ArrayList<Food> chList = groups.get(groupPosition).getFood_Items();
            size = chList.size();
        } else if (groupPosition == 4) {
            ArrayList<Others> chList = groups.get(groupPosition).getOther_Items();
            size = chList.size();
        }
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
        Workout workout=null;
        Supplement supplement=null;
        LifeStyle style=null;
        Food food=null;
        Others others=null;
        Treatment treatment = null;

         if (groupPosition==0)
        {
            supplement = (Supplement) getChild1(groupPosition,childPosition);
        }
        else if (groupPosition==1)
        {
            workout = (Workout) getChild(groupPosition, childPosition);
        }
        else if (groupPosition==2)
        {
            style = (LifeStyle) getChild2(groupPosition,childPosition);
        }
        else if (groupPosition==3)
        {
            food = (Food)getChild3(groupPosition,childPosition);
        }
        else if (groupPosition==4)
        {
            others = (Others) getChild4(groupPosition,childPosition);
        }


//        if (convertView == null)//gives wrong group position while scrolling and group position 1 repeats mixing up the data hence commented
//        {
            LayoutInflater infalInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.mylist_items, null);
//        }

        TextView type =(TextView) convertView.findViewById(R.id.type);
        TextView exc_name = (TextView) convertView.findViewById(R.id.exc_name_exp);
        TextView rep = (TextView) convertView.findViewById(R.id.rep_exp);//reps,amount,when for food,gap n freq for lifestyle
        TextView gap = (TextView) convertView.findViewById(R.id.gap_exp);//gap n freequency
        TextView time = (TextView) convertView.findViewById(R.id.timenew_exp);//time header
        TextView sets = (TextView) convertView.findViewById(R.id.sets_exp);//sets only in workout
        TextView weight = (TextView) convertView.findViewById(R.id.weight_exp);//weight only in workout
        TextView rest = (TextView) convertView.findViewById(R.id.rest_exp);//rest only in workout


        complete[childPosition]=(ImageView)convertView.findViewById(R.id.complete);
        partial[childPosition]=(ImageView)convertView.findViewById(R.id.partial);



        ImageView icon1 =(ImageView)convertView.findViewById(R.id.icon);
        View line=(View)convertView.findViewById(R.id.line);
        line.setBackgroundColor(Color.parseColor(lineColour[groupPosition]));
        //time1.setText(time[groupPosition]);
        icon1.setBackgroundResource(background[groupPosition]);
        //name.setText(excName[position]);
        type.setText(Items[groupPosition]);

        if (groupPosition==0)
        {
            exc_name.setText(supplement.getSupplement_name());
            time.setText(supplement.getTime());
            rep.setText("amount: "+supplement.getAmount());
            gap.setText("gap: "+stringconcat(supplement.getFreequency(),supplement.getGap()));

            if (groups.get(groupPosition).getSup_Items().get(childPosition).getInt_compliance()==50)
            {
                complete[childPosition].setBackgroundResource(R.drawable.uncomplete_grey);
                partial[childPosition].setBackgroundResource(R.drawable.partialgreen);
            }
            else if (groups.get(groupPosition).getSup_Items().get(childPosition).getInt_compliance()==100)
            {
                complete[childPosition].setBackgroundResource(R.drawable.complete_green);
                partial[childPosition].setBackgroundResource(R.drawable.partialgray);
            }
            else if (groups.get(groupPosition).getSup_Items().get(childPosition).getInt_compliance()==0)
            {
                complete[childPosition].setBackgroundResource(R.drawable.uncomplete_grey);
                partial[childPosition].setBackgroundResource(R.drawable.partialgray);
            }

        }
        else if (groupPosition==1)
        {
            exc_name.setText(workout.getWorkout_name());
            time.setText(workout.getTime());
            rep.setText("reps: "+workout.getReps());
            sets.setText("sets: "+workout.getSets());
            gap.setText("gap: " + stringconcat(workout.getFreequency(),workout.getGap()));
            weight.setText("weight: "+workout.getWeight());
            rest.setText("rest: "+workout.getRest());

            if (groups.get(groupPosition).getItems().get(childPosition).getInt_compliance()==50)
            {
                complete[childPosition].setBackgroundResource(R.drawable.uncomplete_grey);
                partial[childPosition].setBackgroundResource(R.drawable.partialgreen);
            }
            else if (groups.get(groupPosition).getItems().get(childPosition).getInt_compliance()==100)
            {
                complete[childPosition].setBackgroundResource(R.drawable.complete_green);
                partial[childPosition].setBackgroundResource(R.drawable.partialgray);
            }
            else if (groups.get(groupPosition).getItems().get(childPosition).getInt_compliance()==0)
            {
                complete[childPosition].setBackgroundResource(R.drawable.uncomplete_grey);
                partial[childPosition].setBackgroundResource(R.drawable.partialgray);
            }

        }
        else if (groupPosition==2)
        {
            exc_name.setText(style.getLifestyle_name());
            time.setText(style.getTime()+" minutes");
            rep.setText("gap: "+stringconcat(style.getFreequency(),style.getGap()));


            if (groups.get(groupPosition).getLife_Items().get(childPosition).getInt_compliance()==50)
            {
                complete[childPosition].setBackgroundResource(R.drawable.uncomplete_grey);
                partial[childPosition].setBackgroundResource(R.drawable.partialgreen);
            }
            else if (groups.get(groupPosition).getLife_Items().get(childPosition).getInt_compliance()==100)
            {
                complete[childPosition].setBackgroundResource(R.drawable.complete_green);
                partial[childPosition].setBackgroundResource(R.drawable.partialgray);
            }
            else if (groups.get(groupPosition).getLife_Items().get(childPosition).getInt_compliance()==0)
            {
                complete[childPosition].setBackgroundResource(R.drawable.uncomplete_grey);
                partial[childPosition].setBackgroundResource(R.drawable.partialgray);
            }


        }
        else if (groupPosition==3)
        {
            exc_name.setText(food.getFood_name());
            rep.setText("when: "+food.getWhen());
            time.setText(food.getTime());

            if (groups.get(groupPosition).getFood_Items().get(childPosition).getInt_compliance()==50)
            {
                complete[childPosition].setBackgroundResource(R.drawable.uncomplete_grey);
                partial[childPosition].setBackgroundResource(R.drawable.partialgreen);
            }
            else if (groups.get(groupPosition).getFood_Items().get(childPosition).getInt_compliance()==100)
            {
                complete[childPosition].setBackgroundResource(R.drawable.complete_green);
                partial[childPosition].setBackgroundResource(R.drawable.partialgray);
            }
            else if (groups.get(groupPosition).getFood_Items().get(childPosition).getInt_compliance()==0)
            {
                complete[childPosition].setBackgroundResource(R.drawable.uncomplete_grey);
                partial[childPosition].setBackgroundResource(R.drawable.partialgray);
            }



        }
        else if (groupPosition==4)
        {
            exc_name.setText(others.getOthers_name());
            rep.setText("duration: "+others.getDuration());


            if (groups.get(groupPosition).getOther_Items().get(childPosition).getInt_compliance()==50)
            {
                complete[childPosition].setBackgroundResource(R.drawable.uncomplete_grey);
                partial[childPosition].setBackgroundResource(R.drawable.partialgreen);
            }
            else if (groups.get(groupPosition).getOther_Items().get(childPosition).getInt_compliance()==100)
            {
                complete[childPosition].setBackgroundResource(R.drawable.complete_green);
                partial[childPosition].setBackgroundResource(R.drawable.partialgray);
            }
            else if (groups.get(groupPosition).getOther_Items().get(childPosition).getInt_compliance()==0)
            {
                complete[childPosition].setBackgroundResource(R.drawable.uncomplete_grey);
                partial[childPosition].setBackgroundResource(R.drawable.partialgray);
            }

        }

        complete[childPosition].setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {



                if (groupPosition==0)
                {

                    if(groups.get(groupPosition).getSup_Items().get(childPosition).getInt_compliance()==100){
                        complete[childPosition].setBackgroundResource(R.drawable.uncomplete_grey);
                        partial[childPosition].setBackgroundResource(R.drawable.partialgray);
                        final String URL = "http://192.185.26.69/~holbe/api/patient/updatecompliancenew.php?id="+groups.get(groupPosition).getSup_Items().get(childPosition).getTimings_id()+"&completion=0";
                        //final String url =  "http://192.185.26.69/~holbe/api/patient/updatecompliance.php?completion=1&id="+groups.get(groupPosition).getSup_Items().get(childPosition).getSupplement_mapping_id()+"&treatment=supplement";
                        //  new AsyncHttpTask().execute(URL);
                        volleypost(URL,groups.get(groupPosition).getSup_Items().get(childPosition).getTimings_id(),"0");
                        groups.get(groupPosition).getSup_Items().get(childPosition).setInt_compliance(0);
                        notifyDataSetChanged();
                    }
                    else {
                        complete[childPosition].setBackgroundResource(R.drawable.complete_green);
                        partial[childPosition].setBackgroundResource(R.drawable.partialgray);
                        final String URL = "http://192.185.26.69/~holbe/api/patient/updatecompliancenew.php?id=" + groups.get(groupPosition).getSup_Items().get(childPosition).getTimings_id() + "&completion=1";
                        //final String url =  "http://192.185.26.69/~holbe/api/patient/updatecompliance.php?completion=1&id="+groups.get(groupPosition).getSup_Items().get(childPosition).getSupplement_mapping_id()+"&treatment=supplement";
                        //  new AsyncHttpTask().execute(URL);
                        volleypost(URL, groups.get(groupPosition).getSup_Items().get(childPosition).getTimings_id(), "1");
                        groups.get(groupPosition).getSup_Items().get(childPosition).setInt_compliance(100);
                        notifyDataSetChanged();
                    }
                }
                else if (groupPosition==1)
                {

                    if(groups.get(groupPosition).getItems().get(childPosition).getInt_compliance()==100)
                    {
                        complete[childPosition].setBackgroundResource(R.drawable.uncomplete_grey);
                        partial[childPosition].setBackgroundResource(R.drawable.partialgray);
                        final String URL = "http://192.185.26.69/~holbe/api/patient/updatecompliancenew.php?id="+groups.get(groupPosition).getItems().get(childPosition).getTimings_id()+"&completion=0";
                        //final String url =  "http://192.185.26.69/~holbe/api/patient/updatecompliance.php?completion=1&id="+groups.get(groupPosition).getSup_Items().get(childPosition).getSupplement_mapping_id()+"&treatment=supplement";
                        //  new AsyncHttpTask().execute(URL);
                        volleypost(URL,groups.get(groupPosition).getItems().get(childPosition).getTimings_id(),"0");
                        groups.get(groupPosition).getItems().get(childPosition).setInt_compliance(0);
                        notifyDataSetChanged();
                    }
                    else {
                        complete[childPosition].setBackgroundResource(R.drawable.complete_green);
                        partial[childPosition].setBackgroundResource(R.drawable.partialgray);
                        final String URL = "http://192.185.26.69/~holbe/api/patient/updatecompliancenew.php?id=" + groups.get(groupPosition).getItems().get(childPosition).getTimings_id() + "&completion=1";
                        //final String url =  "http://192.185.26.69/~holbe/api/patient/updatecompliance.php?completion=1&id="+groups.get(groupPosition).getItems().get(childPosition).getWorkout_mapping_id()+"&treatment=workout";
                        //new AsyncHttpTask().execute(URL);
                        volleypost(URL, groups.get(groupPosition).getItems().get(childPosition).getTimings_id(), "1");
                        groups.get(groupPosition).getItems().get(childPosition).setInt_compliance(100);
                        notifyDataSetChanged();
                    }

                }
                else if (groupPosition==2)
                {

                    if(groups.get(groupPosition).getLife_Items().get(childPosition).getInt_compliance()==100){
                        complete[childPosition].setBackgroundResource(R.drawable.uncomplete_grey);
                        partial[childPosition].setBackgroundResource(R.drawable.partialgray);
                        final String URL = "http://192.185.26.69/~holbe/api/patient/updatecompliancenew.php?id="+groups.get(groupPosition).getLife_Items().get(childPosition).getTimings_id()+"&completion=0";
                        //final String url =  "http://192.185.26.69/~holbe/api/patient/updatecompliance.php?completion=1&id="+groups.get(groupPosition).getSup_Items().get(childPosition).getSupplement_mapping_id()+"&treatment=supplement";
                        //  new AsyncHttpTask().execute(URL);
                        volleypost(URL,groups.get(groupPosition).getLife_Items().get(childPosition).getTimings_id(),"0");
                        groups.get(groupPosition).getLife_Items().get(childPosition).setInt_compliance(0);
                        notifyDataSetChanged();
                    }
                    else {
                        complete[childPosition].setBackgroundResource(R.drawable.complete_green);
                        partial[childPosition].setBackgroundResource(R.drawable.partialgray);
                        final String URL = "http://192.185.26.69/~holbe/api/patient/updatecompliancenew.php?id=" + groups.get(groupPosition).getLife_Items().get(childPosition).getTimings_id() + "&completion=1";
                        // final String url =  "http://192.185.26.69/~holbe/api/patient/updatecompliance.php?completion=1&id="+groups.get(groupPosition).getLife_Items().get(childPosition).getLifestyle_mapping_id()+"&treatment=lifestyle";
                        // new AsyncHttpTask().execute(URL);
                        volleypost(URL, groups.get(groupPosition).getLife_Items().get(childPosition).getTimings_id(), "1");
                        groups.get(groupPosition).getLife_Items().get(childPosition).setInt_compliance(100);
                        notifyDataSetChanged();
                    }

                }
                else if (groupPosition==3)
                {

                    if(groups.get(groupPosition).getFood_Items().get(childPosition).getInt_compliance()==100){
                        complete[childPosition].setBackgroundResource(R.drawable.uncomplete_grey);
                        partial[childPosition].setBackgroundResource(R.drawable.partialgray);
                        final String URL = "http://192.185.26.69/~holbe/api/patient/updatecompliancenew.php?id="+groups.get(groupPosition).getFood_Items().get(childPosition).getTimings_id()+"&completion=0";
                        //final String url =  "http://192.185.26.69/~holbe/api/patient/updatecompliance.php?completion=1&id="+groups.get(groupPosition).getSup_Items().get(childPosition).getSupplement_mapping_id()+"&treatment=supplement";
                        //  new AsyncHttpTask().execute(URL);
                        volleypost(URL,groups.get(groupPosition).getFood_Items().get(childPosition).getTimings_id(),"0");
                        groups.get(groupPosition).getFood_Items().get(childPosition).setInt_compliance(0);
                        notifyDataSetChanged();
                    }
                    else {
                        complete[childPosition].setBackgroundResource(R.drawable.complete_green);
                        partial[childPosition].setBackgroundResource(R.drawable.partialgray);
                        final String URL = "http://192.185.26.69/~holbe/api/patient/updatecompliancenew.php?id=" + groups.get(groupPosition).getFood_Items().get(childPosition).getTimings_id() + "&completion=1";
                        //final String url =  "http://192.185.26.69/~holbe/api/patient/updatecompliance.php?completion=1&id="+groups.get(groupPosition).getFood_Items().get(childPosition).getFood_mapping_id()+"&treatment=food";
                        // new AsyncHttpTask().execute(URL);
                        volleypost(URL, groups.get(groupPosition).getFood_Items().get(childPosition).getTimings_id(), "1");
                        groups.get(groupPosition).getFood_Items().get(childPosition).setInt_compliance(100);
                        notifyDataSetChanged();
                    }

                }
                else if (groupPosition==4)
                {
                    if(groups.get(groupPosition).getOther_Items().get(childPosition).getInt_compliance()==100){
                        complete[childPosition].setBackgroundResource(R.drawable.uncomplete_grey);
                        partial[childPosition].setBackgroundResource(R.drawable.partialgray);
                        final String URL = "http://192.185.26.69/~holbe/api/patient/updatecompliancenew.php?id="+groups.get(groupPosition).getOther_Items().get(childPosition).getTimings_id()+"&completion=0";
                        //final String url =  "http://192.185.26.69/~holbe/api/patient/updatecompliance.php?completion=1&id="+groups.get(groupPosition).getSup_Items().get(childPosition).getSupplement_mapping_id()+"&treatment=supplement";
                        //  new AsyncHttpTask().execute(URL);
                        volleypost(URL,groups.get(groupPosition).getOther_Items().get(childPosition).getTimings_id(),"0");
                        groups.get(groupPosition).getOther_Items().get(childPosition).setInt_compliance(0);
                        notifyDataSetChanged();
                    }
                    else {
                        complete[childPosition].setBackgroundResource(R.drawable.complete_green);
                        partial[childPosition].setBackgroundResource(R.drawable.partialgray);
                        final String URL = "http://192.185.26.69/~holbe/api/patient/updatecompliancenew.php?id=" + groups.get(groupPosition).getOther_Items().get(childPosition).getTimings_id() + "&completion=1";
                        // final String url =  "http://192.185.26.69/~holbe/api/patient/updatecompliance.php?completion=1&id="+groups.get(groupPosition).getOther_Items().get(childPosition).getOthers_mapping_id()+"&treatment=others";
                        // new AsyncHttpTask().execute(URL);
                        volleypost(URL, groups.get(groupPosition).getOther_Items().get(childPosition).getTimings_id(), "1");
                        groups.get(groupPosition).getOther_Items().get(childPosition).setInt_compliance(100);
                        notifyDataSetChanged();
                    }

                }

            }
        });

        partial[childPosition].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                if (groupPosition==0)
                {
                    complete[childPosition].setBackgroundResource(R.drawable.uncomplete_grey);
                    partial[childPosition].setBackgroundResource(R.drawable.partialgreen);
                    final String URL = "http://192.185.26.69/~holbe/api/patient/updatecompliancenew.php?id="+groups.get(groupPosition).getSup_Items().get(childPosition).getTimings_id()+"&completion=0.5";
                   // final String url =  "http://192.185.26.69/~holbe/api/patient/updatecompliance.php?completion=0.5&id="+groups.get(groupPosition).getSup_Items().get(childPosition).getSupplement_mapping_id()+"&treatment=supplement";
                    //new AsyncHttpTask().execute(URL);
                    volleypost(URL, groups.get(groupPosition).getSup_Items().get(childPosition).getTimings_id(), "0.5");
                    groups.get(groupPosition).getSup_Items().get(childPosition).setInt_compliance(50);
                    notifyDataSetChanged();

                }
                else if (groupPosition==1)
                {
                    complete[childPosition].setBackgroundResource(R.drawable.uncomplete_grey);
                    partial[childPosition].setBackgroundResource(R.drawable.partialgreen);
                    final String URL = "http://192.185.26.69/~holbe/api/patient/updatecompliancenew.php?id="+groups.get(groupPosition).getItems().get(childPosition).getTimings_id()+"&completion=0.5";
                   // final String url =  "http://192.185.26.69/~holbe/api/patient/updatecompliance.php?completion=0.5&id="+groups.get(groupPosition).getItems().get(childPosition).getWorkout_mapping_id()+"&treatment=workout";
                   // new AsyncHttpTask().execute(URL);
                    volleypost(URL, groups.get(groupPosition).getItems().get(childPosition).getTimings_id(), "0.5");
                    groups.get(groupPosition).getItems().get(childPosition).setInt_compliance(50);
                    notifyDataSetChanged();

                }
                else if (groupPosition==2)
                {
                    complete[childPosition].setBackgroundResource(R.drawable.uncomplete_grey);
                    partial[childPosition].setBackgroundResource(R.drawable.partialgreen);
                    final String URL = "http://192.185.26.69/~holbe/api/patient/updatecompliancenew.php?id="+groups.get(groupPosition).getLife_Items().get(childPosition).getTimings_id()+"&completion=0.5";
                   // final String url =  "http://192.185.26.69/~holbe/api/patient/updatecompliance.php?completion=0.5&id="+groups.get(groupPosition).getLife_Items().get(childPosition).getLifestyle_mapping_id()+"&treatment=lifestyle";
                   // new AsyncHttpTask().execute(URL);
                    volleypost(URL, groups.get(groupPosition).getLife_Items().get(childPosition).getTimings_id(), "0.5");
                    groups.get(groupPosition).getLife_Items().get(childPosition).setInt_compliance(50);
                    notifyDataSetChanged();


                }
                else if (groupPosition==3)
                {
                    complete[childPosition].setBackgroundResource(R.drawable.uncomplete_grey);
                    partial[childPosition].setBackgroundResource(R.drawable.partialgreen);
                    // http://192.185.26.69/~holbe/api/patient/updatecompliance.php?completion=0.1&id=1&treatment=workout
                    final String URL = "http://192.185.26.69/~holbe/api/patient/updatecompliancenew.php?id="+groups.get(groupPosition).getFood_Items().get(childPosition).getTimings_id()+"&completion=0.5";
                   // final String url =  "http://192.185.26.69/~holbe/api/patient/updatecompliance.php?completion=0.5&id="+groups.get(groupPosition).getFood_Items().get(childPosition).getFood_mapping_id()+"&treatment=food";
                   // new AsyncHttpTask().execute(URL);
                    volleypost(URL, groups.get(groupPosition).getFood_Items().get(childPosition).getTimings_id(), "0.5");
                    groups.get(groupPosition).getFood_Items().get(childPosition).setInt_compliance(50);
                    notifyDataSetChanged();


                }
                else if (groupPosition==4)
                {
                    complete[childPosition].setBackgroundResource(R.drawable.uncomplete_grey);
                    partial[childPosition].setBackgroundResource(R.drawable.partialgreen);
                    final String URL = "http://192.185.26.69/~holbe/api/patient/updatecompliancenew.php?id="+groups.get(groupPosition).getOther_Items().get(childPosition).getTimings_id()+"&completion=0.5";
                    //final String url =  "http://192.185.26.69/~holbe/api/patient/updatecompliance.php?completion=0.5&id="+groups.get(groupPosition).getOther_Items().get(childPosition).getOthers_mapping_id()+"&treatment=others";
                    //new AsyncHttpTask().execute(URL);
                    volleypost(URL, groups.get(groupPosition).getOther_Items().get(childPosition).getTimings_id(), "0.5");
                    groups.get(groupPosition).getOther_Items().get(childPosition).setInt_compliance(50);
                    notifyDataSetChanged();


                }

            }
        });


        return convertView;
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
    public View getGroupView(final int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {


        Group group = (Group) getGroup(groupPosition);
//        if (convertView == null) {  //gives wrong group position while scrolling and group position 1 repeats mixing up the data hence commented
            LayoutInflater inf = (LayoutInflater) context
                    .getSystemService(context.LAYOUT_INFLATER_SERVICE);
            convertView = inf.inflate(R.layout.overview_group_item_dummy, null);
//        }
        ExpandableListView mExpandableListView = (ExpandableListView) parent;
        mExpandableListView.expandGroup(groupPosition);
        if (isExpanded)
        {

        }
        /*ImageView icon = (ImageView)convertView.findViewById(R.id.icon);
        icon.setBackgroundResource(group.getIcon());
        TextView type = (TextView)convertView.findViewById(R.id.type);
        type.setText(group.getName());
        type.setTextColor(Color.parseColor(groups.get(groupPosition).getItems().get(groupPosition).getColour()));
        TextView compliance_percentage = (TextView)convertView.findViewById(R.id.compliance_percentage);
        if (groupPosition==0)
        {
            compliance_percentage.setText(""+group.getWorkout_compliance());
        }
        else if (groupPosition==1)
        {
            compliance_percentage.setText(""+group.getSupplement_compliance());
        }
        else if (groupPosition==2)
        {
            compliance_percentage.setText(""+group.getLifestyle_compliance());
        }
        else if (groupPosition==3)
        {
            compliance_percentage.setText(""+group.getFood_compliance());
        }
        else if (groupPosition==4)
        {
            compliance_percentage.setText(""+group.getOthers_compliance());
        }
*/
        return convertView;
    }


    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
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



