package com.appsriv.holbe.fragments;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.DatePicker;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.appsriv.holbe.Adatpters.ExpandListAdapterForComingUp;
import com.appsriv.holbe.R;
import com.appsriv.holbe.activities.LatestComingUp;
import com.appsriv.holbe.activities.Login;
import com.appsriv.holbe.interfaces.ActivityCommunicator;
import com.appsriv.holbe.interfaces.FragmentCommunicator;
import com.appsriv.holbe.interfaces.OnTaskCompleted;
import com.appsriv.holbe.models.Food;
import com.appsriv.holbe.models.Group;
import com.appsriv.holbe.models.LifeStyle;
import com.appsriv.holbe.models.Others;
import com.appsriv.holbe.models.Supplement;
import com.appsriv.holbe.models.Workout;
import com.appsriv.holbe.util.Util;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

public class FragmentForComingUp extends Fragment implements FragmentCommunicator , OnTaskCompleted
{
    public static ArrayList<Group> list;
    private int progressBarRes[];
    String name[];

    ArrayList<Workout> work_list = null;
    ArrayList<Supplement> sup_list = null;
    ArrayList<LifeStyle> life_list = null;
    ArrayList<Food> food_list = null;
    ArrayList<Others> other_list = null;

    String items[];
    int flag[];
    int background[];
    String lineColour[];
    String[] time;
    String[] typeExc;
    HashMap<String,ArrayList<Workout>> map;
    private ViewPager viewPager;
    String workout_count, supplement_count, lifestyle_count, food_count, others_count;
    View view;
    public  ExpandableListView ExpList;
    ExpandListAdapterForComingUp comingUpListview;
    int preLast;
    public static String formattedDate;
    public static SimpleDateFormat df;
    public static TextView date;
    public static int year;
    public static int month;
    public static int day;
    public static ImageView previousdate;
    public static ImageView nextdate;
    public static Calendar c;
    String URL;
    private String currentdate;
    private ArrayList<Group> masterlist;
    private ActivityCommunicator activityCommunicator;
    Context context;
    ArrayList<String> circuitId = new ArrayList<>();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        view = inflater.inflate(R.layout.fragment_latest_comingup, container, false);
        masterlist=new ArrayList<>();
        c = Calendar.getInstance();
        Util.dateList.clear();
        year = c.get(Calendar.YEAR);
        month = c.get(Calendar.MONTH);
        day = c.get(Calendar.DAY_OF_MONTH);
        df = new SimpleDateFormat("yyyy-MM-dd");
        formattedDate = df.format(c.getTime());
        currentdate=formattedDate;
        Log.d("date",formattedDate);
        ExpList = (ExpandableListView) view.findViewById(R.id.exp_list);
        ExpList.setFastScrollEnabled(true);
        ExpList.setFastScrollAlwaysVisible(true);
        URL="http://192.185.26.69/~holbe/api/patient/test/get_coming_up.php?id="+Login.details.get("userId")+"&dateid="+currentdate;
        Util.current_Date = currentdate;
        Util.date=currentdate;
        Util.dateList.add(currentdate);
        new AsyncHttpTask().execute(URL);
        ExpList.setOnScrollListener(new AbsListView.OnScrollListener()
        {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState)
            {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount)
            {
                final int lastItem = firstVisibleItem + visibleItemCount;
                if(lastItem == totalItemCount)
                {
                    if(preLast!=lastItem){ //to avoid multiple calls for last item
                        preLast = lastItem;
                        try
                        {
                            c.setTime(df.parse(currentdate));
                        } catch (ParseException e)
                        {
                            e.printStackTrace();
                        }
                        c.add(Calendar.DATE, 1);  // number of days to add
                        currentdate= df.format(c.getTime());  //  is now the new date
                        URL="http://192.185.26.69/~holbe/api/patient/test/get_coming_up.php?id="+Login.details.get("userId")+"&dateid="+currentdate;
                        Log.i("check" ,"In AsysncTask cal in on Scroll " +currentdate);
                        Util.date=currentdate;
                        Util.dateList.add(currentdate);
                        if (Util.flag)
                        {
                            new AsyncHttpTask().execute(URL);
                        }
                    }
                }
            }
        });
        int position = LatestComingUp.tabLayout.getSelectedTabPosition();

        Log.i("tabPos", "pos " + position);

        if (ExpList != null)
        {
            if (position == 0)
            {
                ExpList.setSelectedGroup(position);
            } else if (position == 1)
            {
                ExpList.setSelectedGroup(position);

            } else if (position == 2)
            {
                ExpList.setSelectedGroup(position);
            } else if (position == 3)
            {
                ExpList.setSelectedGroup(position);
            } else if (position == 4)
            {
                ExpList.setSelectedGroup(position);
            }
        }
       /* LatestComingUp.tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener()
        {
            @Override
            public void onTabSelected(TabLayout.Tab tab)
            {
                if (ExpList!=null)
                {
                    int position = tab.getPosition();

                    Log.i("tabPos", "pos " + position);

                    if (ExpList != null) {
                        if (position == 0) {
                           ExpList.setSelectedGroup(position);
                        } else if (position == 1) {
                            ExpList.setSelectedGroup(position);

                        } else if (position == 2) {
                            ExpList.setSelectedGroup(position);
                        } else if (position == 3) {
                            ExpList.setSelectedGroup(position);
                        } else if (position == 4) {
                            ExpList.setSelectedGroup(position);
                        }
                    }
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab)
            {
                *//*if (ExpList!=null)
                {
                    int position = tab.getPosition();

                    Log.i("tabPos", "pos " + position);

                    if (ExpList != null)
                    {
                        if (position == 0)
                        {
                            ExpList.setSelectedGroup(position);
                        } else if (position == 1)
                        {
                           ExpList.setSelectedGroup(position);

                        } else if (position == 2)
                        {
                            ExpList.setSelectedGroup(position);
                        } else if (position == 3)
                        {
                           ExpList.setSelectedGroup(position);
                        } else if (position == 4)
                        {
                           ExpList.setSelectedGroup(position);
                        }
                    }
                }*//*
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                int position = tab.getPosition();
                Log.i("tabPos" ,"pos " +position);

                if (ExpList!=null)
                {
                    if (position == 0)
                    {
                       ExpList.setSelectedGroup(position);

                    } else if (position == 1) {
                       ExpList.setSelectedGroup(position);

                    } else if (position == 2) {
                       ExpList.setSelectedGroup(position);
                    } else if (position == 3) {
                       ExpList.setSelectedGroup(position);
                    } else if (position == 4) {
                       ExpList.setSelectedGroup(position);
                    }
                }
            }
        });*/
        return view;
    }

    @Override
    public void onAttach(Activity activity){
        super.onAttach(activity);
        context = getActivity();
        activityCommunicator =(ActivityCommunicator)context;
        ((LatestComingUp)context).fragmentCommunicator = this;
        Log.i("check" ,"In onAttach ");
//        ExpList = (ExpandableListView) view.findViewById(R.id.exp_list);
    }
    @Override
    public void passDataToFragment(String Date)
    {
        URL = "http://192.185.26.69/~holbe/api/patient/test/get_coming_up.php?id="+ Login.details.get("userId")+"&dateid="+Date;
        new CalenderClickTask().execute(URL);
        //sendRequest(URL);
      //  Log.i("check" ,"In passDataToFragment " +masterlist.toString());
    }


    @Override
    public void onStart()
    {
        super.onStart();



    }

    @Override
    public void onResume() {
        super.onResume();
        LatestComingUp.date.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                DatePickerDialog dpd = new DatePickerDialog(getActivity(),
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
                                URL = "http://192.185.26.69/~holbe/api/patient/test/get_coming_up.php?id="+ Login.details.get("userId")+"&dateid="+year + "-" + (monthOfYear + 1) + "-" + dayOfMonth;
                                Util.date=currentdate;
                                Util.dateList.add(currentdate);
                                masterlist.clear();
                                if (Util.flag) {
                                    new CalenderClickTask().execute(URL);
                                }
                             //   Log.i("check" ,"In onDateSet " +masterlist.toString());
                             //   Log.i("check" ,"In onDateSet DATE  " +year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);
                                //new CalenderClickTask().execute(URL);
                            }
                        }, year, month, day);
                dpd.show();
            }
        });

    }


    @Override
    public void onTaskCompleted()
    {

    }

    public class CalenderClickTask extends AsyncTask<String, Void, ArrayList<Group>>
    {
       /* private OnTaskCompleted listener;
        public CalenderClickTask(OnTaskCompleted listener){
            this.listener=listener;
        }*/

        ProgressDialog progressDialog;

        @Override
        protected void onPreExecute()
        {
            progressDialog = ProgressDialog.show(getActivity(), "Please wait", "Loading...");
        }

        @Override
        protected ArrayList<Group> doInBackground(String... params) {
            InputStream inputStream = null;
            Integer result = 0;
            HttpURLConnection urlConnection = null;
            ArrayList<Group> templist=new ArrayList<>();

            try {
                /* forming th java.net.URL object */
                URL url6 = new URL(params[0]);

                urlConnection = (HttpURLConnection) url6.openConnection();

                /* for Get request */
                urlConnection.setRequestMethod("GET");

                int statusCode = urlConnection.getResponseCode();

                /* 200 represents HTTP OK */
                if (statusCode == 200)
                {
                    BufferedReader r = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                    StringBuilder response = new StringBuilder();
                    String line;
                    while ((line = r.readLine()) != null)
                    {
                        response.append(line);
                    }
                    templist=parseResult(response.toString());
                    result = 1; // Successful

                } else
                {
                    result = 0; //"Failed to fetch data!"// ;
                }

            } catch (Exception e)
            {
                e.printStackTrace();
            } finally {
                if (urlConnection != null) {

                }
            }

            return templist; //"Failed to fetch data!";
        }

        @Override
        protected void onPostExecute(final ArrayList<Group> resultlist)
        {

            //mProgressDialog.dismiss();

            /* Download complete. Lets update UI */
            masterlist.clear();
            masterlist = resultlist;
            comingUpListview.notifyDataSetChanged();
            Log.i("check" ,"In Calender Task  " +masterlist.toString());
            Log.i("check" ,"In Calender Task result list " +resultlist.toString());
            Util.flag = true;
            if (progressDialog != null && progressDialog.isShowing()) {
                progressDialog.dismiss();
            }
            if (resultlist != null)
            {
                LatestComingUp.top.setText(supplement_count);
                LatestComingUp.top1.setText(workout_count);
                LatestComingUp.top2.setText(lifestyle_count);
                LatestComingUp.top3.setText(food_count);
                LatestComingUp.top4.setText(others_count);
                // overalll_compliance.setText("" + str_overalll_compliance + "%");
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        typeExc = new String[]{"SITUPS", "AVACADO SHAKE", "VITAMIN C", "VITAMIN A", "VITAMIN D"};
                        comingUpListview = new ExpandListAdapterForComingUp(getActivity(), items, time, background, typeExc, background, lineColour, masterlist,  ExpList);
                        ExpList.setAdapter(comingUpListview);
                        ExpList.setVisibility(View.INVISIBLE);
                        comingUpListview.notifyDataSetChanged();
                        for (int i =0; i<masterlist.size(); i++)
                        {
                            for (int j =0; j<masterlist.get(i).getItems().size(); j++)
                            {
                               // Log.i("check" ,"Resuml for supplement name " +resultlist.get(i).getSup_Items().get(j).getSupplement_name());
                               // Log.i("check" ,"Resuml for lifestyle name " +resultlist.get(i).getLife_Items().get(j).getLifestyle_name());
                            }
                        }

                    }
                });


            } else
            {
            }
        }
    }
    public class AsyncHttpTask extends AsyncTask<String, Void, ArrayList<Group>>
    {

        ProgressDialog progressDialog;

        @Override
        protected void onPreExecute()
        {


            progressDialog = ProgressDialog.show(getActivity(), "Please wait", "Loading...");
        }

        @Override
        protected ArrayList<Group> doInBackground(String... params) {
            InputStream inputStream = null;
            Integer result = 0;
            HttpURLConnection urlConnection = null;
            ArrayList<Group> templist=new ArrayList<>();

            try {
                /* forming th java.net.URL object */
                URL url6 = new URL(params[0]);

                urlConnection = (HttpURLConnection) url6.openConnection();

                /* for Get request */
                urlConnection.setRequestMethod("GET");

                int statusCode = urlConnection.getResponseCode();

                /* 200 represents HTTP OK */
                if (statusCode == 200)
                {
                    BufferedReader r = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                    StringBuilder response = new StringBuilder();
                    String line;
                    while ((line = r.readLine()) != null)
                    {
                        response.append(line);
                    }
                    templist=parseResult(response.toString());
                    result = 1; // Successful

                } else
                {
                    result = 0; //"Failed to fetch data!"// ;
                }

            } catch (Exception e)
            {
                e.printStackTrace();
            } finally {
                if (urlConnection != null) {

                }
            }

            return templist; //"Failed to fetch data!";
        }

        @Override
        protected void onPostExecute(ArrayList<Group> resultlist)
        {

            //mProgressDialog.dismiss();

            /* Download complete. Lets update UI */
            if (progressDialog != null && progressDialog.isShowing()) {
                progressDialog.dismiss();
            }
            Log.i("check" ,"In AsysncTask " +masterlist.toString());
            if (resultlist != null) {
                LatestComingUp.top.setText(supplement_count);
                LatestComingUp.top1.setText(workout_count);
                LatestComingUp.top2.setText(lifestyle_count);
                LatestComingUp.top3.setText(food_count);
                LatestComingUp.top4.setText(others_count);
               // overalll_compliance.setText("" + str_overalll_compliance + "%");
                items = new String[]{"Supplement", "Workout", "Lifestyle", "Food & Drink", "Others"};
                time = new String[]{"1:30 PM", "3:45 PM", "5:15 PM", "6:30 PM", "8:00 PM"};

                lineColour = new String[]{"#ABD14B", "#3CC3AF", "#1AA2DF", "#AA68B4", "#BD345E"};

                flag = new int[]{R.drawable.supplements, R.drawable.workouts, R.drawable.lifestyles, R.drawable.foodadndrink,
                        R.drawable.others};
                background = new int[]{R.drawable.circle_suppliments, R.drawable.circle_workout, R.drawable.circle_heart, R.drawable.circlefoodanddrink,
                        R.drawable.circle_others};
                typeExc = new String[]{"SITUPS", "AVACADO SHAKE", "VITAMIN C", "VITAMIN A", "VITAMIN D"};
                for(int i=0;i<resultlist.size();i++)
                {
                    masterlist.add(resultlist.get(i));
                }
                Log.i("check" ,masterlist.toString());
                comingUpListview = new ExpandListAdapterForComingUp(getActivity(), items, time, background, typeExc, background, lineColour, masterlist,  ExpList);
                ExpList.setAdapter(comingUpListview);


                if (comingUpListview!=null)
                {
                    comingUpListview.notifyDataSetChanged();
                }


            } else
            {
            }
        }
    }

    private ArrayList<Group> parseResult(String result)
    {

        list=new ArrayList<>();
        flag = new int[]{R.drawable.circle_suppliments, R.drawable.circle_workout, R.drawable.circle_heart, R.drawable.circlefoodanddrink, R.drawable.circle_others};
        progressBarRes = new int[]
                {R.drawable.supplement_circle_progress_foregrnd, R.drawable.workout_circle_progress_foregrnd, R.drawable.lifestylr_circle_progress_foregrnd,
                        R.drawable.foodanddrinks_circle_progress_foregrnd,
                        R.drawable.others_circle_progress_foregrnd};

        name = new String[]{"Supplements", "Workout", "Lifestyle", "Food & Drinks", "Others"};

        lineColour = new String[]{"#ABD14B", "#3CC3AF", "#1AA2DF", "#AA68B4", "#BD345E"};

        try
        {

            JSONObject object = new JSONObject(result);

            if (object.length() != 0)
            {

                list.clear();
                work_list = new ArrayList<>();
                life_list = new ArrayList<>();
                sup_list = new ArrayList<>();
                food_list = new ArrayList<>();
                other_list = new ArrayList<>();
                map = new HashMap<>();
                circuitId = new ArrayList<>();
                ArrayList<Workout> dumy = new ArrayList<>();

                for (int j = 0; j < 5; j++)
                {
                    Group gru = new Group();
                    gru.setName(name[j]);
                    gru.setIcon(flag[j]);
                    gru.setPercentage("91");
                    gru.setSectionName("section" + j);

                    work_list.clear();
                    //treatment count
                   JSONObject jsonObject = object.getJSONObject("treatment_count");
                        workout_count = jsonObject.getJSONObject("").getString("workout_count");
                        supplement_count = jsonObject.getJSONObject("").getString("supplement_count");
                       lifestyle_count = jsonObject.getJSONObject("").getString("lifestyle_count");
                        food_count = jsonObject.getJSONObject("").getString("food_count");
                        others_count = jsonObject.getJSONObject("").getString("others_count");


                    //Util.dateList.add(currentdate);
                    //Log.i("date_size","Date size "+Util.dateList.size());
                   // Log.i("date_size","Date size "+Util.dateList);
                    //over all compliance
                    //JSONArray overAllCompArr = object.getJSONArray("overall_compliance");
                    //workout

                    Workout work;

                    int index= 0 ;
                    JSONArray workout = object.getJSONArray("workout");
                    ArrayList<String> sets=null;
                    map = new HashMap<>();
                    ArrayList<Workout> finalList=null;
                    for (int x = 0; x < workout.length(); x++)
                    {

                        boolean flag = true;
                        String s = workout.getJSONObject(x).getString("circuit_id");
                        for (int i=0; i<work_list.size(); i++)
                        {
                            if (work_list.get(i).getCircuit_id().equalsIgnoreCase(workout.getJSONObject(x).getString("circuit_id")))
                            {
                                flag = false;
                                index  = i;
                            }

                        }
                        if (flag==false)
                        {
                            work_list.get(index).getReps1().add(workout.getJSONObject(x).getString("reps"));
                            work_list.get(index).getSets1().add(workout.getJSONObject(x).getString("sets"));
                            work_list.get(index).getHasWeight1().add(workout.getJSONObject(x).getString("hasweight"));
                            work_list.get(index).getType1().add(workout.getJSONObject(x).getString("type"));
                            work_list.get(index).getTime1().add(workout.getJSONObject(x).getString("time"));
                            work_list.get(index).getTimings_id1().add(workout.getJSONObject(x).getString("timings_id"));
                            work_list.get(index).getWorkout_name1().add(workout.getJSONObject(x).getString("workout_name"));
                            work_list.get(index).getCompliance1().add(workout.getJSONObject(x).getString("compliance"));

                        }
                        else
                        {

                            work = new Workout();

                            work.getReps1().add(workout.getJSONObject(x).getString("reps"));
                            work.getSets1().add(workout.getJSONObject(x).getString("sets"));
                            work.getHasWeight1().add(workout.getJSONObject(x).getString("hasweight"));
                            work.getType1().add(workout.getJSONObject(x).getString("type"));
                            work.getTime1().add(workout.getJSONObject(x).getString("time"));
                            work.getTimings_id1().add(workout.getJSONObject(x).getString("timings_id"));
                            work.getWorkout_name1().add(workout.getJSONObject(x).getString("workout_name"));
                            work.getCompliance1().add(workout.getJSONObject(x).getString("compliance"));
                            work.setCircuit_id(workout.getJSONObject(x).getString("circuit_id"));


                           // work.setType(workout.getJSONObject(x).getString("type"));
                           /* work.setCircuit_id(workout.getJSONObject(x).getString("circuit_id"));
                            work.setTime(workout.getJSONObject(x).getString("time"));
                            work.setTimings_id(workout.getJSONObject(x).getString("timings_id"));
                            work.setWorkout_mapping_id(workout.getJSONObject(x).getString("workout_mapping_id"));
                            work.setReps(workout.getJSONObject(x).getString("reps"));
                            work.setSets(workout.getJSONObject(x).getString("sets"));
                            work.setWeight(workout.getJSONObject(x).getString("weight"));
                            work.setGap(workout.getJSONObject(x).getString("gap"));
                            work.setFreequency(workout.getJSONObject(x).getString("frequency"));
                            work.setRest(workout.getJSONObject(x).getString("rest"));
                            work.setWorkout_name(workout.getJSONObject(x).getString("workout_name"));
                            work.setCompliance(workout.getJSONObject(x).getString("compliance") + "%");
                            work.setInt_compliance(workout.getJSONObject(x).getInt("compliance"));
                            work.setHasweight(workout.getJSONObject(x).getString("hasweight"));
                            work.setProgressBarRes(progressBarRes[j]);
                            work.setColour(lineColour[j]);
                            work.setListHashMap(map);*/
                            work_list.add(work);
                        }


                    }

                    // supplement

                    Supplement supp = null;
                    sup_list.clear();
                    JSONArray supplement = object.getJSONArray("supplement");
                    for (int x = 0; x < supplement.length(); x++)
                    {
                        supp = new Supplement();

                        supp.setTime(supplement.getJSONObject(x).getString("time"));
                        supp.setTimings_id(supplement.getJSONObject(x).getString("timings_id"));
                        supp.setSupplement_mapping_id(supplement.getJSONObject(x).getString("supplement_mapping_id"));
                        supp.setSupplement_name(supplement.getJSONObject(x).getString("supplement_name"));
                        supp.setAmount(supplement.getJSONObject(x).getString("amount"));
                        supp.setGap(supplement.getJSONObject(x).getString("gap"));
                        supp.setRepitition(supplement.getJSONObject(x).getString("repitition"));
                        supp.setCompliance(supplement.getJSONObject(x).getString("compliance") + "%");
                        supp.setWhen_time(supplement.getJSONObject(x).getString("when_time"));
                        supp.setInt_compliance(supplement.getJSONObject(x).getInt("compliance"));
                        supp.setFreequency(supplement.getJSONObject(x).getString("frequency"));
                        supp.setType(supplement.getJSONObject(x).getString("type"));
                        supp.setCriteria_main_name(supplement.getJSONObject(x).getString("criteria_main_name"));
                        supp.setForm_main_name(supplement.getJSONObject(x).getString("form_main_name"));
                        supp.setDosage_main_name(supplement.getJSONObject(x).getString("dosage_main_name"));
/*
                        supp.getTime1().add(supplement.getJSONObject(x).getString("time"));
                        supp.getTimings_id1().add((supplement.getJSONObject(x).getString("timings_id")));
                        supp.getSupplement_mapping_id1().add(supplement.getJSONObject(x).getString("supplement_mapping_id"));
                        supp.getSupplement_name1().add(supplement.getJSONObject(x).getString("supplement_name"));
                        supp.getAmoun1t().add(supplement.getJSONObject(x).getString("amount"));
                        supp.getGap1().add(supplement.getJSONObject(x).getString("gap"));
                        supp.getRepitition1().add(supplement.getJSONObject(x).getString("repitition"));
                        supp.getCompliance1().add(supplement.getJSONObject(x).getString("compliance") + "%");
                        supp.getWhen_time1().add(supplement.getJSONObject(x).getString("when_time"));
                        supp.getCompliance1().add(supplement.getJSONObject(x).getString("compliance"));
                        supp.getFreequency1().add(supplement.getJSONObject(x).getString("frequency"));
                        supp.getType1().add(supplement.getJSONObject(x).getString("type"));
                        supp.getCriteria_main_name1().add(supplement.getJSONObject(x).getString("criteria_main_name"));
                        supp.getForm_main_name1().add(supplement.getJSONObject(x).getString("form_main_name"));
                        supp.getDosage_main_name1().add(supplement.getJSONObject(x).getString("dosage_main_name"));*/

                        supp.setProgressBarRes(progressBarRes[j]);
                        supp.setColour(lineColour[j]);
                        sup_list.add(supp);
                    }

                    //Food
                    Food food1 = null;
                    food_list.clear();
                    JSONArray food = object.getJSONArray("food");
                    for (int x = 0; x < food.length(); x++) {
                        food1 = new Food();
                        food1.setType(food.getJSONObject(x).getString("type"));
                        food1.setTime(food.getJSONObject(x).getString("time"));
                        food1.setTimings_id(food.getJSONObject(x).getString("timings_id"));
                        food1.setFood_mapping_id(food.getJSONObject(x).getString("food_mapping_id"));
                        food1.setFood_name(food.getJSONObject(x).getString("food_name"));
                        food1.setWhen(food.getJSONObject(x).getString("when"));
                        food1.setCompliance(food.getJSONObject(x).getString("compliance") + "%");
                        food1.setInt_compliance(food.getJSONObject(x).getInt("compliance"));
                        food1.setProgressBarRes(progressBarRes[j]);
                        food1.setColour(lineColour[j]);
                        food_list.add(food1);
                    }

                    //lifestyle

                    life_list.clear();
                    LifeStyle style = null;
                    JSONArray lifestyle = object.getJSONArray("lifestyle");
                    for (int x = 0; x < lifestyle.length(); x++) {
                        style = new LifeStyle();
                        style.setType(lifestyle.getJSONObject(x).getString("type"));
                        style.setTime(lifestyle.getJSONObject(x).getString("time"));
                        style.setTimings_id(lifestyle.getJSONObject(x).getString("timings_id"));
                        style.setLifestyle_mapping_id(lifestyle.getJSONObject(x).getString("lifestyle_mapping_id"));
                        style.setLifestyle_name(lifestyle.getJSONObject(x).getString("lifestyle_name"));
                        style.setTime(lifestyle.getJSONObject(x).getString("time"));
                        //style.setRepitition(lifestyle.getJSONObject(x).getString("repitition"));
                        //style.setWhen(lifestyle.getJSONObject(x).getString("when"));
                        style.setCompliance(lifestyle.getJSONObject(x).getString("compliance") + "%");
                        style.setInt_compliance(lifestyle.getJSONObject(x).getInt("compliance"));
                        style.setGap(lifestyle.getJSONObject(x).getString("gap"));
                        style.setFreequency(lifestyle.getJSONObject(x).getString("frequency"));
                        style.setProgressBarRes(progressBarRes[j]);
                        style.setColour(lineColour[j]);
                        life_list.add(style);
                    }

                    //others
                    Others other = null;
                    other_list.clear();
                    JSONArray others = object.getJSONArray("others");
                    for (int x = 0; x < others.length(); x++) {
                        other = new Others();
                        other.setTime(others.getJSONObject(x).getString("time"));
                        other.setTimings_id(others.getJSONObject(x).getString("timings_id"));
                        other.setOthers_mapping_id(others.getJSONObject(x).getString("others_mapping_id"));
                        other.setOthers_name(others.getJSONObject(x).getString("others_name"));
                        other.setDuration(others.getJSONObject(x).getString("duration"));
                        other.setCompliance(others.getJSONObject(x).getString("compliance") + "%");
                        other.setInt_compliance(others.getJSONObject(x).getInt("compliance"));
                        other.setProgressBarRes(progressBarRes[j]);
                        other.setColour(lineColour[j]);
                        other_list.add(other);
                    }


//                    JSONArray workout_compliance = object.getJSONArray("workout_compliance");
//                    gru.setWorkout_compliance(workout_compliance.getJSONObject(0).getInt("workout_compliance"));
//
//                    JSONArray supplement_compliance = object.getJSONArray("supplement_compliance");
//                    gru.setSupplement_compliance(supplement_compliance.getJSONObject(0).getInt("supplement_compliance"));
//
//                    JSONArray lifestyle_compliance = object.getJSONArray("lifestyle_compliance");
//                    gru.setLifestyle_compliance(lifestyle_compliance.getJSONObject(0).getInt("lifestyle_compliance"));
//
//                    JSONArray food_compliance = object.getJSONArray("food_compliance");
//                    gru.setFood_compliance(food_compliance.getJSONObject(0).getInt("food_compliance"));
//
//                    JSONArray others_compliance = object.getJSONArray("others_compliance");
//                    gru.setOthers_compliance(others_compliance.getJSONObject(0).getInt("others_compliance"));
						/*}*/

                    gru.setDate(currentdate);
                    gru.setItems(work_list);
                    gru.setFood_Items(food_list);
                    gru.setLife_Items(life_list);
                    gru.setSup_Items(sup_list);
                    gru.setOther_Items(other_list);
                    list.add(gru);


                }

                return list;


            } else {
                return null;
            }

        } catch (JSONException j)
        {
            j.printStackTrace();
            return null;
        }

    }

    private void sendRequest(String JSON_URL)
    {

        StringRequest stringRequest = new StringRequest(JSON_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response)

                    {
                       ArrayList<Group> list = new ArrayList<>();
                        masterlist= parseResult(response);
                        comingUpListview = new ExpandListAdapterForComingUp(getActivity(), items, time, background, typeExc, background, lineColour, list,  ExpList);
                        ExpList.setAdapter(comingUpListview);
                        if (comingUpListview!=null)
                        {
                            comingUpListview.notifyDataSetChanged();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getActivity(),error.getMessage(),Toast.LENGTH_LONG).show();
                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(stringRequest);
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

