package com.appsriv.holbe.StickyHeader;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.appsriv.holbe.models.Food;
import com.appsriv.holbe.models.Group;
import com.appsriv.holbe.models.LifeStyle;
import com.appsriv.holbe.models.Others;
import com.appsriv.holbe.R;
import com.appsriv.holbe.models.Supplement;
import com.appsriv.holbe.fragments.SupplementFragment;
import com.appsriv.holbe.models.Workout;

import java.util.ArrayList;

public class DemoActivity extends Activity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        HeaderListView list = new HeaderListView(this);

        list.setAdapter(new SectionAdapter()
        {

            @Override
            public int numberOfSections()
            {
                return 5;
            }

            @Override
            public int numberOfRows(int section)
            {
                //return 5;

                int size = 0;

                if (section == 0)
                {
                    ArrayList<Workout> chList = SupplementFragment.list.get(section).getItems();
                    size = chList.size();
                } else if (section == 1)
                {
                    ArrayList<Supplement> chList = SupplementFragment.list.get(section).getSup_Items();
                    size = chList.size();
                } else if (section == 2) {
                    ArrayList<LifeStyle> chList = SupplementFragment.list.get(section).getLife_Items();
                    size = chList.size();
                } else if (section == 3) {
                    ArrayList<Food> chList = SupplementFragment.list.get(section).getFood_Items();
                    size = chList.size();
                } else if (section == 4) {
                    ArrayList<Others> chList = SupplementFragment.list.get(section).getOther_Items();
                    size = chList.size();
                }
                return size;

            }

            @Override
            public Object getRowItem(int section, int row)
            {
                return null;
            }

            @Override
            public boolean hasSectionHeaderView(int section) {
                return true;
            }



            public Object getChild(int groupPosition, int childPosition)
            {
                ArrayList<Workout> chList = SupplementFragment.list.get(groupPosition).getItems();
                return chList.get(childPosition);
            }

            public Object getChild1(int groupPosition, int childPosition)
            {

                ArrayList<Supplement> chList = SupplementFragment.list.get(groupPosition).getSup_Items();
                return chList.get(childPosition);
            }
            public Object getChild2(int groupPosition, int childPosition)
            {

                ArrayList<LifeStyle> chList = SupplementFragment.list.get(groupPosition).getLife_Items();
                return chList.get(childPosition);
            }
            public Object getChild3(int groupPosition, int childPosition)
            {
                ArrayList<Food> chList = SupplementFragment.list.get(groupPosition).getFood_Items();
                return chList.get(childPosition);
            }
            public Object getChild4(int groupPosition, int childPosition)
            {
                ArrayList<Others> chList = SupplementFragment.list.get(groupPosition).getOther_Items();
                return chList.get(childPosition);
            }
            public Object getGroup(int groupPosition) {
                return SupplementFragment.list.get(groupPosition);
            }


            @Override
            public View getRowView(int section, int row, View convertView, ViewGroup parent)
            {
                Workout workout=null;
                Supplement supplement=null;
                LifeStyle style=null;
                Food food=null;
                Others others=null;

                if (section==0)
                {
                    supplement = (Supplement) getChild1(section,row);
                }
                else if (section==1)
                {
                    workout = (Workout) getChild(section, row);
                }
                else if (section==2)
                {
                    style = (LifeStyle) getChild2(section,row);
                }
                else if (section==3)
                {
                    food = (Food)getChild3(section,row);
                }
                else if (section==4)
                {
                    others = (Others) getChild4(section,row);
                }


                if (convertView == null)
                {
                 //   convertView = (TextView) getLayoutInflater().inflate(getResources().getLayout(android.R.layout.simple_list_item_1), null);
                    LayoutInflater infalInflater = (LayoutInflater) DemoActivity.this.getSystemService(DemoActivity.this.LAYOUT_INFLATER_SERVICE);
                    convertView = infalInflater.inflate(R.layout.child_item, null);
                }
//                TextView exc_name = (TextView)convertView.findViewById(R.id.exc_name);
//                TextView rep = (TextView)convertView.findViewById(R.id.rep);
                TextView time = (TextView)convertView.findViewById(R.id.time);
                TextView compliance_percentage =(TextView)convertView.findViewById(R.id.compliance_percentage);
                TextView colour = (TextView)convertView.findViewById(R.id.colour);
                ProgressBar circle_progress_bar_front =(ProgressBar)convertView.findViewById(R.id.circle_progress_bar);
                ProgressBar circle_progress_bar_back =(ProgressBar)convertView.findViewById(R.id.circle_progress_bar_back);
                //((TextView) convertView).setText("Section " + section + " Row " + row);


//
//                if (section==0)
//                {
//
//                    colour.setBackgroundColor(Color.parseColor(supplement.getColour()));
//                    exc_name.setText(supplement.getSupplement_name());
//                    rep.setText(supplement.getAmount());
//                    compliance_percentage.setText(supplement.getCompliance());
//                    circle_progress_bar_front.setBackgroundResource(supplement.getProgressBarRes());
//                    circle_progress_bar_front.setProgressDrawable(DemoActivity.this.getResources().getDrawable(supplement.getProgressBarRes()));
//                    circle_progress_bar_front.setProgress(SupplementFragment.list.get(0).getSup_Items().get(row).getInt_compliance());
//                    time.setText(supplement.getWhen_time());
//                }
//
//                if (section==1)
//                {
//
//                    colour.setBackgroundColor(Color.parseColor(workout.getColour()));
//                    exc_name.setText(workout.getWorkout_name());
//                    rep.setText(workout.getReps());
//                    compliance_percentage.setText(workout.getCompliance());
//                    circle_progress_bar_front.setBackgroundResource(workout.getProgressBarRes());
//                    circle_progress_bar_front.setProgressDrawable(DemoActivity.this.getResources().getDrawable(workout.getProgressBarRes()));
//                    //circle_progress_bar_front.setProgress(workout.getInt_compliance());
//                    circle_progress_bar_front.setProgress(SupplementFragment.list.get(1).getItems().get(row).getInt_compliance());
//                }
//                if (section==2) {
//                    colour.setBackgroundColor(Color.parseColor(style.getColour()));
//                    exc_name.setText(style.getLifestyle_name());
//                    rep.setText(style.getRepitition());
//                    compliance_percentage.setText(style.getCompliance());
//                    circle_progress_bar_front.setBackgroundResource(style.getProgressBarRes());
//                    circle_progress_bar_front.setProgressDrawable(DemoActivity.this.getResources().getDrawable(style.getProgressBarRes()));
//                    //circle_progress_bar_front.setProgress(style.getInt_compliance());
//                    circle_progress_bar_front.setProgress(SupplementFragment.list.get(2).getLife_Items().get(row).getInt_compliance());
//                }
//                if (section==3) {
//                    colour.setBackgroundColor(Color.parseColor(food.getColour()));
//                    exc_name.setText(food.getFood_name());
//                    rep.setText(food.getWhen());
//                    compliance_percentage.setText(food.getCompliance());
//                    circle_progress_bar_front.setBackgroundResource(food.getProgressBarRes());
//                    circle_progress_bar_front.setProgressDrawable(DemoActivity.this.getResources().getDrawable(food.getProgressBarRes()));
//                    //circle_progress_bar_front.setProgress(food.getInt_compliance());
//                    circle_progress_bar_front.setProgress(SupplementFragment.list.get(3).getFood_Items().get(row).getInt_compliance());
//                }
//                if (section==4) {
//                    colour.setBackgroundColor(Color.parseColor(others.getColour()));
//                    exc_name.setText(others.getOthers_name());
//                    rep.setText(others.getDuration());
//                    compliance_percentage.setText(others.getCompliance());
//                    circle_progress_bar_front.setBackgroundResource(others.getProgressBarRes());
//                    circle_progress_bar_front.setProgressDrawable(DemoActivity.this.getResources().getDrawable(others.getProgressBarRes()));
//                    //circle_progress_bar_front.setProgress(others.getInt_compliance());
//                    circle_progress_bar_front.setProgress(SupplementFragment.list.get(4).getOther_Items().get(row).getInt_compliance());
//                }


                return convertView;
            }

            @Override
            public int getSectionHeaderViewTypeCount() {
                return 2;
            }

            @Override
            public int getSectionHeaderItemViewType(int section) {
                return section % 2;
            }

            @Override
            public View getSectionHeaderView(int section, View convertView, ViewGroup parent)
            {
                Group group = (Group) getGroup(section);
                if (convertView == null)
                {
                    if (getSectionHeaderItemViewType(section) == 0)
                    {
                        LayoutInflater inf = (LayoutInflater) DemoActivity.this.getSystemService(DemoActivity.this.LAYOUT_INFLATER_SERVICE);
                        convertView = inf.inflate(R.layout.group_item, null);
                    } else
                    {
                        LayoutInflater inf = (LayoutInflater) DemoActivity.this.getSystemService(DemoActivity.this.LAYOUT_INFLATER_SERVICE);
                        convertView = inf.inflate(R.layout.group_item, null);
                    }
                }

                if (getSectionHeaderItemViewType(section) == 0)
                {

                    ImageView icon = (ImageView)convertView.findViewById(R.id.icon);
                    icon.setBackgroundResource(group.getIcon());
                    TextView type = (TextView)convertView.findViewById(R.id.type);
                    type.setText(group.getName());
                    type.setTextColor(Color.parseColor(SupplementFragment.list.get(section).getItems().get(section).getColour()));
                    TextView compliance_percentage = (TextView)convertView.findViewById(R.id.compliance_percentage);
                    if (section==0)
                    {
                        compliance_percentage.setText(""+group.getWorkout_compliance()+"%");
                    }
                    else if (section==1)
                    {
                        compliance_percentage.setText(""+group.getSupplement_compliance()+"%");
                    }
                    else if (section==2)
                    {
                        compliance_percentage.setText(""+group.getLifestyle_compliance()+"%");
                    }
                    else if (section==3)
                    {
                        compliance_percentage.setText(""+group.getFood_compliance()+"%");
                    }
                    else if (section==4)
                    {
                        compliance_percentage.setText(""+group.getOthers_compliance()+"%");
                    }

                } else
                {
                    ImageView icon = (ImageView)convertView.findViewById(R.id.icon);
                    icon.setBackgroundResource(group.getIcon());
                    TextView type = (TextView)convertView.findViewById(R.id.type);
                    type.setText(group.getName());
                    type.setTextColor(Color.parseColor(SupplementFragment.list.get(section).getItems().get(section).getColour()));
                    TextView compliance_percentage = (TextView)convertView.findViewById(R.id.compliance_percentage);
                    if (section==0)
                    {
                        compliance_percentage.setText(""+group.getWorkout_compliance()+"%");
                    }
                    else if (section==1)
                    {
                        compliance_percentage.setText(""+group.getSupplement_compliance()+"%");
                    }
                    else if (section==2)
                    {
                        compliance_percentage.setText(""+group.getLifestyle_compliance()+"%");
                    }
                    else if (section==3)
                    {
                        compliance_percentage.setText(""+group.getFood_compliance()+"%");
                    }
                    else if (section==4)
                    {
                        compliance_percentage.setText(""+group.getOthers_compliance()+"%");
                    }
                }

                switch (section)
                {
                case 0:
                    convertView.setBackgroundColor(Color.parseColor("#ffffff"));
                    break;
                case 1:
                    convertView.setBackgroundColor(Color.parseColor("#ffffff"));
                    break;
                case 2:
                     convertView.setBackgroundColor(Color.parseColor("#ffffff"));
                    break;
                case 3:
                    convertView.setBackgroundColor(Color.parseColor("#ffffff"));
                    break;
                case 4:
                        convertView.setBackgroundColor(Color.parseColor("#ffffff"));
                        break;
                }
                return convertView;
            }

            @Override
            public void onRowItemClick(AdapterView<?> parent, View view, int section, int row, long id)
            {
                super.onRowItemClick(parent, view, section, row, id);
                Toast.makeText(DemoActivity.this, "Section " + section + " Row " + row, Toast.LENGTH_SHORT).show();
            }
        });
        setContentView(list);
    }
}
