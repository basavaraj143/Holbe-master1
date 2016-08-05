package com.appsriv.holbe.Adatpters;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.appsriv.holbe.R;
import com.appsriv.holbe.models.Group;
import com.appsriv.holbe.volley.VolleySingleton;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CustomComingUpListAdapter extends BaseAdapter
{
    NumberPicker np;
    private Activity activity;
    private LayoutInflater inflater;
    ArrayList<Group> list;
    int groupPosition;
    int childPosition;
    TextView type,reps;
    ImageView []complete = new ImageView[1000];
    ImageView []partial = new ImageView[1000];
    String num=null;
    AlertDialog b;
    public CustomComingUpListAdapter(Activity activity, ArrayList<Group> list,int groupPosition,int childPosition) {
        this.activity = activity;
        this.list = list;
        this.groupPosition = groupPosition;
        this.childPosition = childPosition;

    }

    @Override
    public int getCount()
    {
        int size = 0;
        if ((groupPosition%5)==0)
        {

            size =  list.get(groupPosition).getSup_Items().size();
        }
        else if ((groupPosition%5)==1)
        {
            size = list.get(groupPosition).getItems().get(childPosition).getWorkout_name1().size();
        }
        else if ((groupPosition%5)==2)
        {
            size = list.get(groupPosition).getLife_Items().size();

        }
        else if ((groupPosition%5)==3)
        {
            size = list.get(groupPosition).getFood_Items().size();
        }
        else if ((groupPosition%5)==4)
        {
            size = list.get(groupPosition).getOther_Items().size();

        }


        return size;

    }

    @Override
    public Object getItem(int location) {
        return location;
    }

    @Override
    public long getItemId(int position)
    {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {

        LinearLayout layout=null;
        if (convertView == null)
        {
            inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            if ((groupPosition % 5)==1)
            {
                convertView = inflater.inflate(R.layout.latest_coming_list_items, null);
                type = (TextView) convertView.findViewById(R.id.type);
                reps = (TextView) convertView.findViewById(R.id.reps);
                layout = (LinearLayout) convertView.findViewById(R.id.layout);
            }
            else
            {
                convertView = inflater.inflate(R.layout.latest_coming_list_items_second, null);
                type = (TextView) convertView.findViewById(R.id.type);
                reps = (TextView) convertView.findViewById(R.id.reps);
                layout = (LinearLayout) convertView.findViewById(R.id.layout);
                complete[position]=(ImageView)convertView.findViewById(R.id.completed);
                partial[position]=(ImageView)convertView.findViewById(R.id.partial);
                /*completed[position].setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(activity,"completed ",Toast.LENGTH_LONG).show();
                    }
                });
*/


                complete[position].setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v)
                    {

                        if ((groupPosition % 5)==0)
                        {

                            final String URL = "http://192.185.26.69/~holbe/api/patient/updatecompliancenew.php";/*?id=" + list.get(groupPosition).getOther_Items().get(childPosition).getTimings_id() + "&completion=1";*/
                            volleypost(URL, list.get(groupPosition).getOther_Items().get(childPosition).getTimings_id(), "1");
                        }
                        else if ((groupPosition%5)==2)
                        {
                            final String URL = "http://192.185.26.69/~holbe/api/patient/updatecompliancenew.php";/*?id=" + list.get(groupPosition).getOther_Items().get(childPosition).getTimings_id() + "&completion=1";*/
                            volleypost(URL, list.get(groupPosition).getOther_Items().get(childPosition).getTimings_id(), "1");

                        }
                        else if (groupPosition==3)
                        {
                            final String URL = "http://192.185.26.69/~holbe/api/patient/updatecompliancenew.php";/*?id=" + list.get(groupPosition).getOther_Items().get(childPosition).getTimings_id() + "&completion=1";*/
                            volleypost(URL, list.get(groupPosition).getOther_Items().get(childPosition).getTimings_id(), "1");
                        }
                        else if ((groupPosition%5)==4)
                        {
                            final String URL = "http://192.185.26.69/~holbe/api/patient/updatecompliancenew.php";/*?id=" + list.get(groupPosition).getOther_Items().get(childPosition).getTimings_id() + "&completion=1";*/
                            volleypost(URL, list.get(groupPosition).getOther_Items().get(childPosition).getTimings_id(), "1");
                        }

                    }
                });
                partial[position].setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v)
                    {
                        if ((groupPosition%5)==0)
                        {

                            final String URL = "http://192.185.26.69/~holbe/api/patient/updatecompliancenew.php";/*?id=" + list.get(groupPosition).getOther_Items().get(childPosition).getTimings_id() + "&completion=1";*/
                            volleypost(URL, list.get(groupPosition).getOther_Items().get(childPosition).getTimings_id(), "0.5");
                        }
                        else if ((groupPosition%5)==2)
                        {
                            showChangeLangDialogForLifeStyle(groupPosition,childPosition);
                        }
                        else if ((groupPosition%5)==3)
                        {
                            showChangeLangDialogForFood(groupPosition,childPosition);
                        }
                        else if ((groupPosition%5)==4)
                        {
                            /*final String URL = "http://192.185.26.69/~holbe/api/patient/updatecompliancenew.php";*//*?id=" + list.get(groupPosition).getOther_Items().get(childPosition).getTimings_id() + "&completion=1";*//*

                            volleypost(URL, list.get(groupPosition).getOther_Items().get(childPosition).getTimings_id(), "0.5");
                            Toast.makeText(activity,"completed groupPosition==4",Toast.LENGTH_LONG).show();
*/
                            showChangeLangDialogForOthers(groupPosition,childPosition);
                        }
                    }
                });

            }
            if ((groupPosition%5)==0)
            {
                type.setText(list.get(groupPosition).getSup_Items().get(childPosition).getSupplement_name());
                reps.setText(list.get(groupPosition).getSup_Items().get(childPosition).getDosage_main_name());
            }
            else if ((groupPosition%5)==1)
            {
                layout.setVisibility(View.GONE);
                type.setText(list.get(groupPosition).getItems().get(childPosition).getWorkout_name1().get(position));
                reps.setText(list.get(groupPosition).getItems().get(childPosition).getSets1().get(position) +" Set of "+list.get(groupPosition).getItems().get(childPosition).getReps1().get(position)+" ");
            }
            else if ((groupPosition%5)==2)
            {
                type.setText(list.get(groupPosition).getLife_Items().get(childPosition).getLifestyle_name());
                reps.setText(""+list.get(groupPosition).getLife_Items().get(childPosition).getGap());
            }
            else if ((groupPosition%5)==3)
            {
                type.setText(list.get(groupPosition).getFood_Items().get(childPosition).getFood_name());
                reps.setText(""+list.get(groupPosition).getFood_Items().get(childPosition).getWhen());
            }
            else if ((groupPosition%5)==4)
            {
                type.setText(list.get(groupPosition).getOther_Items().get(childPosition).getOthers_name());
                reps.setText(""+list.get(groupPosition).getOther_Items().get(childPosition).getDuration());
            }


        }


        return convertView;
    }

    private void volleypost(String url,final String id, final String completion)
    {
        RequestQueue requestQueue = VolleySingleton.getsInstance().getmRequestQueue();
        final ProgressDialog progressDialog = ProgressDialog.show(activity, "Please wait", "Loading...");
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
            public String getBodyContentType()
            {
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

    private void volleyPostForLifeStyle(String url,final String id, final String time)
    {
        RequestQueue requestQueue = VolleySingleton.getsInstance().getmRequestQueue();
        final ProgressDialog progressDialog = ProgressDialog.show(activity, "Please wait", "Loading...");
        StringRequest jsonObjRequest = new StringRequest(Request.Method.POST,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Log.d("status",response);
                        Toast.makeText(activity,response,Toast.LENGTH_LONG).show();
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
            public String getBodyContentType()
            {
                return "application/x-www-form-urlencoded; charset=UTF-8";
            }

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("timings_id", id);
                params.put("time", time);
                return params;
            }

        };

        requestQueue.add(jsonObjRequest);

    }

    private void volleyPostForFood(String url,final String id, final String amount)
    {
        RequestQueue requestQueue = VolleySingleton.getsInstance().getmRequestQueue();
        final ProgressDialog progressDialog = ProgressDialog.show(activity, "Please wait", "Loading...");
        StringRequest jsonObjRequest = new StringRequest(Request.Method.POST,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Log.d("status",response);
                        Toast.makeText(activity,response,Toast.LENGTH_LONG).show();
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
            public String getBodyContentType()
            {
                return "application/x-www-form-urlencoded; charset=UTF-8";
            }

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("timings_id", id);
                params.put("amount", amount);
                return params;
            }

        };

        requestQueue.add(jsonObjRequest);

    }

    private void volleyPostForOthers(String url,final String id, final String completion)
    {
        RequestQueue requestQueue = VolleySingleton.getsInstance().getmRequestQueue();
        final ProgressDialog progressDialog = ProgressDialog.show(activity, "Please wait", "Loading...");
        StringRequest jsonObjRequest = new StringRequest(Request.Method.POST,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Log.d("status",response);
                        Toast.makeText(activity,response,Toast.LENGTH_LONG).show();
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
            public String getBodyContentType()
            {
                return "application/x-www-form-urlencoded; charset=UTF-8";
            }

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("timings_id", id);
                params.put("completion", completion);
                return params;
            }

        };

        requestQueue.add(jsonObjRequest);

    }
    public void showChangeLangDialogForFood(final int groupPosition , int childPosition) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(activity);

        LayoutInflater infalInflater = (LayoutInflater) activity.getSystemService(activity.LAYOUT_INFLATER_SERVICE);
        final View dialogView = infalInflater.inflate(R.layout.partial_food_drink, null);
        dialogBuilder.setView(dialogView);
        ImageView close = (ImageView)dialogView.findViewById(R.id.close);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                b.dismiss();
            }
        });
        /*final ListView listView = (ListView)dialogView.findViewById(R.id.list);
        final PartialFoodAdapter partialCustomAdapter = new PartialFoodAdapter(activity,list,groupPosition,childPosition);
        listView.setAdapter(partialCustomAdapter);
*/

        TextView food_type = (TextView)dialogView.findViewById(R.id.food_type);
        TextView amount =(TextView)dialogView.findViewById(R.id.amount);
        final EditText amountEditextvalue =(EditText) dialogView.findViewById(R.id.amountedittextvalue);
        food_type.setText(list.get(groupPosition).getFood_Items().get(childPosition).getFood_name());
        amount.setText(""+list.get(groupPosition).getFood_Items().get(0).getFood_count());

        dialogBuilder.setPositiveButton("Submit", new DialogInterface.OnClickListener()
        {
            public void onClick(DialogInterface dialog, int whichButton)
            {
                if (amountEditextvalue.getText().toString().isEmpty())
                {
                    Toast.makeText(activity,"Please Enter amount",Toast.LENGTH_LONG).show();
                }
                else
                {
                    final String URL = "http://192.185.26.69/~holbe/api/patient/test/updatefoodcompliance.php";
                    volleyPostForFood(URL,list.get(groupPosition).getFood_Items().get(0).getTimings_id(),amountEditextvalue.getText().toString());
                }
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
    public void showChangeLangDialogForLifeStyle(final int groupPosition , final int childPosition)
    {

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(activity);

        LayoutInflater infalInflater = (LayoutInflater) activity.getSystemService(activity.LAYOUT_INFLATER_SERVICE);
        final View dialogView = infalInflater.inflate(R.layout.partial_lifestyle, null);
        dialogBuilder.setView(dialogView);
        ImageView close = (ImageView)dialogView.findViewById(R.id.close);
        TextView lifestyle_type = (TextView)dialogView.findViewById(R.id.lifestyle_type);
        TextView time =(TextView)dialogView.findViewById(R.id.lifestyle_time);
        final EditText timeEditextvalue =(EditText) dialogView.findViewById(R.id.timeedittextvalue);
        lifestyle_type.setText(list.get(groupPosition).getLife_Items().get(childPosition).getLifestyle_name());
        time.setText(list.get(groupPosition).getLife_Items().get(0).getTime());

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                b.dismiss();
            }
        });
        /*final ListView listView = (ListView)dialogView.findViewById(R.id.list);
        final PartialLifestyleAdapter partialCustomAdapter = new PartialLifestyleAdapter(activity,list,groupPosition,childPosition);
        listView.setAdapter(partialCustomAdapter);*/

        dialogBuilder.setPositiveButton("Submit", new DialogInterface.OnClickListener()
        {
            public void onClick(DialogInterface dialog, int whichButton)
            {
                if (timeEditextvalue.getText().toString().isEmpty())
                {
                    Toast.makeText(activity,"Please Enter Time",Toast.LENGTH_LONG).show();
                }
                else {
                    final String URL = "http://192.185.26.69/~holbe/api/patient/test/updatelifestylecompliance.php";
                    volleyPostForLifeStyle(URL, list.get(groupPosition).getLife_Items().get(childPosition).getTimings_id(), timeEditextvalue.getText().toString());
                }
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

    public void showChangeLangDialogForOthers(final int groupPosition , final int childPosition)
    {

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(activity);
        LayoutInflater infalInflater = (LayoutInflater) activity.getSystemService(activity.LAYOUT_INFLATER_SERVICE);
        final View dialogView = infalInflater.inflate(R.layout.partial_others, null);
        dialogBuilder.setView(dialogView);
        ImageView close = (ImageView)dialogView.findViewById(R.id.close);
        np = (NumberPicker) dialogView.findViewById(R.id.numberPicker1);
        TextView food_type = (TextView)dialogView.findViewById(R.id.food_type);
        TextView amount =(TextView)dialogView.findViewById(R.id.amount);
        //final EditText amountEditextvalue =(EditText) dialogView.findViewById(R.id.amountedittextvalue);
        food_type.setText(list.get(groupPosition).getOther_Items().get(childPosition).getOthers_name());
        amount.setText(""+list.get(groupPosition).getOther_Items().get(0).getOthers_count());

        np.setMinValue(0);
        np.setMaxValue(10);
        np.setWrapSelectorWheel(false);
        np.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);

        np.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {

            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal)
            {
                num = String.valueOf(newVal);
            }
        });


        close.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                b.dismiss();
            }
        });
        dialogBuilder.setPositiveButton("Submit", new DialogInterface.OnClickListener()
        {
            public void onClick(DialogInterface dialog, int whichButton)
            {
                Log.i("num","number is "+num);
                final String URL = "http://192.185.26.69/~holbe/api/patient/test/updateotherscompliance.php";
                volleyPostForOthers(URL, list.get(groupPosition).getLife_Items().get(childPosition).getTimings_id(), num);

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
}