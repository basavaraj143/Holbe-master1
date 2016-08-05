package com.appsriv.holbe;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;

import com.appsriv.holbe.models.Food;
import com.appsriv.holbe.models.Group;
import com.appsriv.holbe.models.LifeStyle;
import com.appsriv.holbe.models.Others;
import com.appsriv.holbe.models.Supplement;
import com.appsriv.holbe.models.Workout;

import java.util.ArrayList;

public class ExpandListAdapter extends BaseExpandableListAdapter
{

    private Context context;
    private ArrayList<Group> groups;
    ExpandableListView expandableListView ;
    int[] progressBarRes;
    int pos=0;

    public ExpandListAdapter(Context context, ArrayList<Group> groups, ExpandableListView expandableListView ,int[] progressBarRes)
    {
        this.context = context;
        this.groups = groups;
        this.expandableListView = expandableListView;
        this.progressBarRes=progressBarRes;

    }

    @Override
    public Object getChild(int groupPosition, int childPosition)
    {
        /*if (groupPosition==0)
        {*/
            ArrayList<Workout> chList = groups.get(groupPosition).getItems();
            return chList.get(childPosition);
      /*  }*/
      /*   if (groupPosition==1)
        {
            ArrayList<Supplement> chList = groups.get(groupPosition).getSup_Items();
            return chList.get(childPosition);
        }*/
    }

    public Object getChild1(int groupPosition, int childPosition)
    {
        /*if (groupPosition==0)
        {*/
        ArrayList<Supplement> chList = groups.get(groupPosition).getSup_Items();
        return chList.get(childPosition);
      /*  }*/
      /*   if (groupPosition==1)
        {
            ArrayList<Supplement> chList = groups.get(groupPosition).getSup_Items();
            return chList.get(childPosition);
        }*/
    }
    public Object getChild2(int groupPosition, int childPosition)
    {
        /*if (groupPosition==0)
        {*/
        ArrayList<LifeStyle> chList = groups.get(groupPosition).getLife_Items();
        return chList.get(childPosition);
      /*  }*/
      /*   if (groupPosition==1)
        {
            ArrayList<Supplement> chList = groups.get(groupPosition).getSup_Items();
            return chList.get(childPosition);
        }*/
    }
    public Object getChild3(int groupPosition, int childPosition)
    {
        /*if (groupPosition==0)
        {*/
        ArrayList<Food> chList = groups.get(groupPosition).getFood_Items();
        return chList.get(childPosition);
      /*  }*/
      /*   if (groupPosition==1)
        {
            ArrayList<Supplement> chList = groups.get(groupPosition).getSup_Items();
            return chList.get(childPosition);
        }*/
    }
    public Object getChild4(int groupPosition, int childPosition)
    {
        /*if (groupPosition==0)
        {*/
        ArrayList<Others> chList = groups.get(groupPosition).getOther_Items();
        return chList.get(childPosition);
      /*  }*/
      /*   if (groupPosition==1)
        {
            ArrayList<Supplement> chList = groups.get(groupPosition).getSup_Items();
            return chList.get(childPosition);
        }*/
    }


    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

   @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent)
    {
//
//        Workout workout = (Workout) getChild(pos, childPosition);
//        Supplement supplement = (Supplement) getChild1(pos,childPosition);
//        LifeStyle style = (LifeStyle) getChild2(pos,childPosition);
//        Food food = (Food)getChild3(pos,childPosition);
//        Others others = (Others) getChild4(pos,childPosition);
//        if (convertView == null) {
//            LayoutInflater infalInflater = (LayoutInflater) context
//                    .getSystemService(context.LAYOUT_INFLATER_SERVICE);
//            convertView = infalInflater.inflate(R.layout.child_item, null);
//        }
//        pos=pos++;
////        TextView exc_name = (TextView)convertView.findViewById(R.id.exc_name);
////        TextView rep = (TextView)convertView.findViewById(R.id.rep);
//        TextView time = (TextView)convertView.findViewById(R.id.time);
//        TextView compliance_percentage =(TextView)convertView.findViewById(R.id.compliance_percentage);
//        TextView colour = (TextView)convertView.findViewById(R.id.colour);
//        ProgressBar circle_progress_bar_front =(ProgressBar)convertView.findViewById(R.id.circle_progress_bar);
//        ProgressBar circle_progress_bar_back =(ProgressBar)convertView.findViewById(R.id.circle_progress_bar_back);
//
//
//        if (groupPosition==0) {
//            colour.setBackgroundColor(Color.parseColor(workout.getColour()));
////            exc_name.setText(workout.getWorkout_name());
////            rep.setText(workout.getReps());
//            compliance_percentage.setText(workout.getCompliance());
//        }
//
////        if (groupPosition==1) {
////            colour.setBackgroundColor(Color.parseColor(supplement.getColour()));
////            exc_name.setText(workout.getWorkout_name());
////            rep.setText(workout.getReps());
////            compliance_percentage.setText(workout.getCompliance());
////        }
////        if (groupPosition==2) {
////            colour.setBackgroundColor(Color.parseColor(style.getColour()));
////            exc_name.setText(workout.getWorkout_name());
////            rep.setText(workout.getReps());
////            compliance_percentage.setText(workout.getCompliance());
////        }
////        if (groupPosition==3) {
////            colour.setBackgroundColor(Color.parseColor(food.getColour()));
////            exc_name.setText(workout.getWorkout_name());
////            rep.setText(workout.getReps());
////            compliance_percentage.setText(workout.getCompliance());
////        }
////        if (groupPosition==4) {
////            colour.setBackgroundColor(Color.parseColor(others.getColour()));
////            exc_name.setText(workout.getWorkout_name());
////            rep.setText(workout.getReps());
////            compliance_percentage.setText(workout.getCompliance());
////        }
////        circle_progress_bar_front.setBackgroundResource(workout.getProgressBarRes());
////        circle_progress_bar_front.setProgressDrawable(context.getResources().getDrawable(workout.getProgressBarRes()));
////        circle_progress_bar_front.setProgress(workout.getInt_compliance());
////
////
////
        return convertView;
    }

    @Override
    public int getChildrenCount(int groupPosition)
    {
        int size=0;

        if (groupPosition==0)
        {
            ArrayList<Workout> chList = groups.get(groupPosition).getItems();
            size = chList.size();
        }

        else if (groupPosition==1)
        {
            ArrayList<Supplement> chList = groups.get(groupPosition).getSup_Items();
            size = chList.size();
        }

        else if (groupPosition==2)
        {
            ArrayList<LifeStyle> chList = groups.get(groupPosition).getLife_Items();
            size = chList.size();
        }

        else if (groupPosition==3)
        {
            ArrayList<Food> chList = groups.get(groupPosition).getFood_Items();
            size = chList.size();
        }

        else if (groupPosition==4)
        {
            ArrayList<Others> chList = groups.get(groupPosition).getOther_Items();
            size = chList.size();
        }
        return size;
    }

    @Override
    public Object getGroup(int groupPosition) {
        return groups.get(groupPosition);
    }

    @Override
    public int getGroupCount()
    {
        //return groups.size();
        return 5;
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent)
    {
        Group group = (Group) getGroup(groupPosition);
        if (convertView == null) {
            LayoutInflater inf = (LayoutInflater) context
                    .getSystemService(context.LAYOUT_INFLATER_SERVICE);
            convertView = inf.inflate(R.layout.group_item, null);
        }
        ExpandableListView mExpandableListView = (ExpandableListView) parent;
        mExpandableListView.expandGroup(groupPosition);
        ImageView icon = (ImageView)convertView.findViewById(R.id.icon);
        icon.setBackgroundResource(group.getIcon());
        TextView type = (TextView)convertView.findViewById(R.id.type);
        type.setText(group.getName());
        type.setTextColor(Color.parseColor(groups.get(groupPosition).getItems().get(groupPosition).getColour()));
        TextView compliance_percentage = (TextView)convertView.findViewById(R.id.compliance_percentage);
        compliance_percentage.setText(group.getPercentage());
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

}