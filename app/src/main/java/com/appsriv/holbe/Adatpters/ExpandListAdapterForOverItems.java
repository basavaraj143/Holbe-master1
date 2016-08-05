package com.appsriv.holbe.Adatpters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ProgressBar;
import android.widget.SectionIndexer;
import android.widget.TextView;

import com.appsriv.holbe.R;
import com.appsriv.holbe.models.Food;
import com.appsriv.holbe.models.Group;
import com.appsriv.holbe.models.LifeStyle;
import com.appsriv.holbe.models.Others;
import com.appsriv.holbe.models.Supplement;
import com.appsriv.holbe.models.Treatment;
import com.appsriv.holbe.models.Workout;

import java.util.ArrayList;


public class ExpandListAdapterForOverItems extends BaseExpandableListAdapter implements SectionIndexer, AbsListView.OnScrollListener {
    private boolean manualScroll;
    private Context context;
    private ArrayList<Group> groups;
    ExpandableListView expandableListView ;
    int[] progressBarRes;
    int pos=0;

    public ExpandListAdapterForOverItems(Context context, ArrayList<Group> groups, ExpandableListView expandableListView , int[] progressBarRes) {
        this.context = context;
        this.groups = groups;
        this.expandableListView = expandableListView;
        this.progressBarRes=progressBarRes;

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
    public int getChildrenCount(int groupPosition) {
        int size = 0;

        if (groupPosition==0)
        {
            ArrayList<Treatment> chList = groups.get(groupPosition).getTreatments();
            size = chList.size();
        }
        if (groupPosition == 1)
        {
            ArrayList<Workout> chList = groups.get(groupPosition).getItems();
            size = chList.size();
        } else if (groupPosition == 2)
        {
            ArrayList<Supplement> chList = groups.get(groupPosition).getSup_Items();
            size = chList.size();
        } else if (groupPosition == 3) {
            ArrayList<LifeStyle> chList = groups.get(groupPosition).getLife_Items();
            size = chList.size();
        } else if (groupPosition == 4) {
            ArrayList<Food> chList = groups.get(groupPosition).getFood_Items();
            size = chList.size();
        } else if (groupPosition == 5) {
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
    public View getChildView(final int groupPosition, int childPosition, boolean isLastChild, View convertView, final ViewGroup parent)
    {
        Workout workout=null;
        Supplement supplement=null;
        LifeStyle style=null;
        Food food=null;
        Others others=null;
        Treatment treatment = null;

        if (groupPosition==0)
        {
            treatment = (Treatment)getChild5(groupPosition,childPosition);
        }
        else if (groupPosition==1)
        {
            supplement = (Supplement) getChild1(groupPosition,childPosition);
        }
        else if (groupPosition==2)
        {
            workout = (Workout) getChild(groupPosition, childPosition);
        }
        else if (groupPosition==3)
        {
            style = (LifeStyle) getChild2(groupPosition,childPosition);
        }
        else if (groupPosition==4)
        {
            food = (Food)getChild3(groupPosition,childPosition);
        }
        else if (groupPosition==5)
        {
            others = (Others) getChild4(groupPosition,childPosition);
        }





        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) context
                    .getSystemService(context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.overview_group_item, null);
        }

       TextView count = (TextView)convertView.findViewById(R.id.count);
        TextView completd = (TextView)convertView.findViewById(R.id.completed);
        TextView late = (TextView)convertView.findViewById(R.id.late);
        TextView missed =(TextView)convertView.findViewById(R.id.missed);
        TextView type =(TextView)convertView.findViewById(R.id.type);
        ProgressBar circle_progress_bar_front =(ProgressBar)convertView.findViewById(R.id.circle_progress_bar);
        ProgressBar circle_progress_bar_back =(ProgressBar)convertView.findViewById(R.id.circle_progress_bar_back);


        if (groupPosition==0)
        {
            count.setText(""+treatment.getTreatment_count());
            completd.setText(""+treatment.getTreatment_completed());
            late.setText(""+treatment.getTreatment_late());
            missed.setText(""+treatment.getTreatment_missed());
            type.setText("OVERALL STATS");
            circle_progress_bar_back.setProgress(treatment.getTreatment_count());
        }

        if (groupPosition==1)
        {
            count.setText(""+supplement.getSupplement_count());
            completd.setText(""+supplement.getSupplement_completed());
            late.setText(""+supplement.getSupplement_late());
            missed.setText(""+supplement.getSupplement_missed());
            type.setText("SUPPLEMENTS STATS");
            circle_progress_bar_back.setProgress(supplement.getSupplement_count());

        }
        if (groupPosition==2)
        {

            count.setText(""+workout.getWorkout_count());
            completd.setText(""+workout.getWorkout_completed());
            late.setText(""+workout.getWorkout_late());
            missed.setText(""+workout.getWorkout_missed());
            type.setText("WORKOUT STATS");
            circle_progress_bar_back.setProgress(workout.getWorkout_count());
        }
        if (groupPosition==3) {
            count.setText(""+style.getLifestyle_count());
            completd.setText(""+style.getLifestyle_completed());
            late.setText(""+style.getLifestyle_late());
            missed.setText(""+style.getLifestyle_missed());
            type.setText("LIFESTYLE STATS");
            circle_progress_bar_back.setProgress(style.getLifestyle_count());
        }
        if (groupPosition==4)
        {
            count.setText(""+food.getFood_count());
            completd.setText(""+food.getFood_completed());
            late.setText(""+food.getFood_late());
            missed.setText(""+food.getFood_missed());
            type.setText("FOOD STATS");
            circle_progress_bar_back.setProgress(food.getFood_count());
        }
        if (groupPosition==5)
        {
            count.setText(""+others.getOthers_count());
            completd.setText(""+others.getOthers_completed());
            late.setText(""+others.getOthers_late());
            missed.setText(""+others.getOthers_missed());
            type.setText("OTHER STATS");
            //circle_progress_bar_front.setBackgroundResource(others.getProgressBarRes());
           /// circle_progress_bar_front.setProgressDrawable(context.getResources().getDrawable(others.getProgressBarRes()));
            circle_progress_bar_back.setProgress(others.getOthers_count());
        }

        return convertView;
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
        if (convertView == null) {
            LayoutInflater inf = (LayoutInflater) context
                    .getSystemService(context.LAYOUT_INFLATER_SERVICE);
            convertView = inf.inflate(R.layout.overview_group_item_dummy, null);
        }
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
}



