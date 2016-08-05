package com.appsriv.holbe.Adatpters;

import android.content.Context;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.appsriv.holbe.models.Group;
import com.appsriv.holbe.R;

import java.util.ArrayList;

public class PartialWorkoutListCustomAdapter extends BaseAdapter{
    String [] result;
    ArrayList<Group> groups;
    Context context;
    int groupPosition , childPosition;
    int point;
    private static LayoutInflater inflater=null;
    PartialInnerListviewCustomAdapter adapter;
    ArrayList<PartialInnerListviewCustomAdapter> adapters= new ArrayList<>();
    public PartialWorkoutListCustomAdapter(Context context, ArrayList<Group> groups, int groupPosition, int childPosition)
    {
        this.context=context;
        this.groups = groups;
        this.groupPosition=groupPosition;
        this.childPosition=childPosition;
        inflater = ( LayoutInflater )context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    @Override
    public int getCount()
    {
        return groups.get(groupPosition).getItems().size();
    }

    @Override
    public Object getItem(int position) {

        return position;
    }

    @Override
    public long getItemId(int position) {

        return position;
    }


    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {


        if (convertView==null)
        {
            convertView = inflater.inflate(R.layout.partial_layout_item, null);

        }
        int count=0;
        TextView workout_type = (TextView)convertView.findViewById(R.id.workout_type);
        TextView sets =(TextView)convertView.findViewById(R.id.sets);
        workout_type.setText(groups.get(groupPosition).getItems().get(childPosition).getWorkout_name());
        sets.setText(groups.get(groupPosition).getItems().get(position).getSets()+" Stes of 10")  ;
        ListView listView = (ListView)convertView.findViewById(R.id.list);
        if (groups.get(position).getItems().size()>position)
        {
             count = Integer.parseInt(groups.get(position).getItems().get(position).getSets());
        }
        adapter = new PartialInnerListviewCustomAdapter(context,groups,groupPosition,childPosition,count,position);
        adapters.add(adapter);
        listView.setAdapter(adapter);
        DisplayMetrics display = context.getResources().getDisplayMetrics();
        int density = display.densityDpi;
        point = 16 * groups.size();
        int pixels=(int) (point*density)/72;
        listView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, pixels));
        listView.requestLayout();
        return convertView;
    }

    public PartialInnerListviewCustomAdapter getRow(int index){
        if(adapters.get(index)==null){
            Log.d("adapters.get[index]","null");
            return null;
        }else{
        return adapters.get(index);
    }
    }



}