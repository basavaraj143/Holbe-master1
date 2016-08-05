package com.appsriv.holbe;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.SectionIndexer;
import android.widget.TextView;

import com.appsriv.holbe.models.Food;
import com.appsriv.holbe.models.Group;
import com.appsriv.holbe.models.LifeStyle;
import com.appsriv.holbe.models.Others;
import com.appsriv.holbe.models.Supplement;
import com.appsriv.holbe.models.Workout;

import java.util.ArrayList;


public class ExpandListAdapterForItems extends BaseExpandableListAdapter implements SectionIndexer, AbsListView.OnScrollListener {
    private boolean manualScroll;
    private Context context;
    private ArrayList<Group> groups;
    ExpandableListView expandableListView;
    int[] progressBarRes;
    int pos = 0;

    public ExpandListAdapterForItems(Context context, ArrayList<Group> groups, ExpandableListView expandableListView, int[] progressBarRes) {
        this.context = context;
        this.groups = groups;
        this.expandableListView = expandableListView;
        this.progressBarRes = progressBarRes;

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

        if (groupPosition == 0) {
            ArrayList<Workout> chList = groups.get(groupPosition).getItems();
            size = chList.size();
        } else if (groupPosition == 1) {
            ArrayList<Supplement> chList = groups.get(groupPosition).getSup_Items();
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
    public Object getChild(int groupPosition, int childPosition) {
        ArrayList<Workout> chList = groups.get(groupPosition).getItems();
        return chList.get(childPosition);
    }

    public Object getChild1(int groupPosition, int childPosition) {

        ArrayList<Supplement> chList = groups.get(groupPosition).getSup_Items();
        return chList.get(childPosition);
    }

    public Object getChild2(int groupPosition, int childPosition) {

        ArrayList<LifeStyle> chList = groups.get(groupPosition).getLife_Items();
        return chList.get(childPosition);
    }

    public Object getChild3(int groupPosition, int childPosition) {
        ArrayList<Food> chList = groups.get(groupPosition).getFood_Items();
        return chList.get(childPosition);
    }

    public Object getChild4(int groupPosition, int childPosition) {
        ArrayList<Others> chList = groups.get(groupPosition).getOther_Items();
        return chList.get(childPosition);
    }


    @Override
    public View getChildView(final int groupPosition, int childPosition, boolean isLastChild, View convertView, final ViewGroup parent) {
        Workout workout = null;
        Supplement supplement = null;
        LifeStyle style = null;
        Food food = null;
        Others others = null;
        if (groupPosition == 0) {
            supplement = (Supplement) getChild1(groupPosition, childPosition);
        } else if (groupPosition == 1) {
            workout = (Workout) getChild(groupPosition, childPosition);
        } else if (groupPosition == 2) {
            style = (LifeStyle) getChild2(groupPosition, childPosition);
        } else if (groupPosition == 3) {
            food = (Food) getChild3(groupPosition, childPosition);
        } else if (groupPosition == 4) {
            others = (Others) getChild4(groupPosition, childPosition);
        }


        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) context
                    .getSystemService(context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.child_item, null);
        }
        // pos=pos++;
//        TextView exc_name = (TextView) convertView.findViewById(R.id.exc_name);//workout_name
//        TextView rep = (TextView) convertView.findViewById(R.id.rep);//reps,repitition
//        TextView time = (TextView) convertView.findViewById(R.id.time);//time
//        TextView sets = (TextView) convertView.findViewById(R.id.sets);//sets,amount
//        TextView weight = (TextView) convertView.findViewById(R.id.weight);//weight
//        TextView rest = (TextView) convertView.findViewById(R.id.rest);//rest
//        TextView gap = (TextView) convertView.findViewById(R.id.gap);//gap
//        TextView frequency = (TextView) convertView.findViewById(R.id.frequency);//freq
        TextView compliance_percentage = (TextView) convertView.findViewById(R.id.compliance_percentage);
        TextView colour = (TextView) convertView.findViewById(R.id.colour);
        ProgressBar circle_progress_bar_front = (ProgressBar) convertView.findViewById(R.id.circle_progress_bar);
        ProgressBar circle_progress_bar_back = (ProgressBar) convertView.findViewById(R.id.circle_progress_bar_back);



//        if (groupPosition == 0) {
//
//            colour.setBackgroundColor(Color.parseColor(supplement.getColour()));
//            exc_name.setText(supplement.getSupplement_name());
//            rep.setText(supplement.getRepitition());
//            time.setText(supplement.getTime());
//            sets.setText(supplement.getAmount());
//            gap.setText(supplement.getGap());
//            frequency.setText(supplement.getFreequency());
//            compliance_percentage.setText(supplement.getCompliance());
//            circle_progress_bar_front.setBackgroundResource(supplement.getProgressBarRes());
//            circle_progress_bar_front.setProgressDrawable(context.getResources().getDrawable(supplement.getProgressBarRes()));
//            circle_progress_bar_front.setProgress(groups.get(0).getSup_Items().get(childPosition).getInt_compliance());
//            time.setText(supplement.getWhen_time());
//        }

//        if (groupPosition == 1) {
//
//            colour.setBackgroundColor(Color.parseColor(workout.getColour()));
//            exc_name.setText(workout.getWorkout_name());
//            rep.setText(workout.getReps());
//            time.setText(workout.getTime());
//            sets.setText(workout.getSets());
//            gap.setText(workout.getGap());
//            frequency.setText(workout.getFreequency());
//            weight.setText(workout.getWeight());
//            rest.setText(workout.getRest());
//            compliance_percentage.setText(workout.getCompliance());
//            circle_progress_bar_front.setBackgroundResource(workout.getProgressBarRes());
//            circle_progress_bar_front.setProgressDrawable(context.getResources().getDrawable(workout.getProgressBarRes()));
//            //circle_progress_bar_front.setProgress(workout.getInt_compliance());
//            circle_progress_bar_front.setProgress(groups.get(1).getItems().get(childPosition).getInt_compliance());
//        }
//        if (groupPosition == 2) {
//            colour.setBackgroundColor(Color.parseColor(style.getColour()));
//            exc_name.setText(style.getLifestyle_name());
//            rep.setText(style.getRepitition());
//            time.setText(style.getTime());
//            gap.setText(style.getGap());
//            frequency.setText(style.getFreequency());
//            compliance_percentage.setText(style.getCompliance());
//            circle_progress_bar_front.setBackgroundResource(style.getProgressBarRes());
//            circle_progress_bar_front.setProgressDrawable(context.getResources().getDrawable(style.getProgressBarRes()));
//            //circle_progress_bar_front.setProgress(style.getInt_compliance());
//            circle_progress_bar_front.setProgress(groups.get(2).getLife_Items().get(childPosition).getInt_compliance());
//        }
//        if (groupPosition == 3) {
//            colour.setBackgroundColor(Color.parseColor(food.getColour()));
//            exc_name.setText(food.getFood_name());
//            time.setText(food.getTime());
//            compliance_percentage.setText(food.getCompliance());
//            circle_progress_bar_front.setBackgroundResource(food.getProgressBarRes());
//            circle_progress_bar_front.setProgressDrawable(context.getResources().getDrawable(food.getProgressBarRes()));
//            //circle_progress_bar_front.setProgress(food.getInt_compliance());
//            circle_progress_bar_front.setProgress(groups.get(3).getFood_Items().get(childPosition).getInt_compliance());
//        }
//        if (groupPosition == 4) {
//            colour.setBackgroundColor(Color.parseColor(others.getColour()));
//            exc_name.setText(others.getOthers_name());
//            rep.setText(others.getDuration());
//            compliance_percentage.setText(others.getCompliance());
//            circle_progress_bar_front.setBackgroundResource(others.getProgressBarRes());
//            circle_progress_bar_front.setProgressDrawable(context.getResources().getDrawable(others.getProgressBarRes()));
//            //circle_progress_bar_front.setProgress(others.getInt_compliance());
//            circle_progress_bar_front.setProgress(groups.get(4).getOther_Items().get(childPosition).getInt_compliance());
//        }
//       /* circle_progress_bar_front.setBackgroundResource(workout.getProgressBarRes());
//        circle_progress_bar_front.setProgressDrawable(context.getResources().getDrawable(workout.getProgressBarRes()));
//        circle_progress_bar_front.setProgress(workout.getInt_compliance());


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
    public int getGroupCount() {
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
            convertView = inf.inflate(R.layout.group_item, null);
        }
        ExpandableListView mExpandableListView = (ExpandableListView) parent;
        mExpandableListView.expandGroup(groupPosition);
        ImageView icon = (ImageView) convertView.findViewById(R.id.icon);
        icon.setBackgroundResource(group.getIcon());
        TextView type = (TextView) convertView.findViewById(R.id.type);
        type.setText(group.getName());
        type.setTextColor(Color.parseColor(groups.get(groupPosition).getItems().get(groupPosition).getColour()));
        TextView compliance_percentage = (TextView) convertView.findViewById(R.id.compliance_percentage);
        if (groupPosition == 0) {
            compliance_percentage.setText("" + group.getWorkout_compliance() + "%");
        } else if (groupPosition == 1) {
            compliance_percentage.setText("" + group.getSupplement_compliance() + "%");
        } else if (groupPosition == 2) {
            compliance_percentage.setText("" + group.getLifestyle_compliance() + "%");
        } else if (groupPosition == 3) {
            compliance_percentage.setText("" + group.getFood_compliance() + "%");
        } else if (groupPosition == 4) {
            compliance_percentage.setText("" + group.getOthers_compliance() + "%");
        }

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



