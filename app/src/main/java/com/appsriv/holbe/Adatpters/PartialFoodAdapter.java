package com.appsriv.holbe.Adatpters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.TextView;

import com.appsriv.holbe.models.Group;
import com.appsriv.holbe.R;

import java.util.ArrayList;

/**
 * Created by AppsrivTech on 7/29/16.
 */
public class PartialFoodAdapter extends BaseAdapter {
    EditText amountEditextvalue;
    ArrayList<Group> groups;
    Context context;
    int groupPosition , childPosition;
    private static LayoutInflater inflater=null;
    public PartialFoodAdapter(Context context, ArrayList<Group> groups, int groupPosition, int childPosition) {
        this.context=context;
        this.groups = groups;
        this.groupPosition=groupPosition;
        this.childPosition=childPosition;
    }

    @Override
    public int getCount() {
        return groups.get(groupPosition).getFood_Items().size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        inflater = ( LayoutInflater )context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView==null)
        {
            convertView = inflater.inflate(R.layout.partial_food_list_item, null);

        }
        TextView food_type = (TextView)convertView.findViewById(R.id.food_type);
        TextView amount =(TextView)convertView.findViewById(R.id.amount);
        amountEditextvalue =(EditText) convertView.findViewById(R.id.amountedittextvalue);
        food_type.setText(groups.get(groupPosition).getFood_Items().get(childPosition).getFood_name());
        amount.setText(""+groups.get(groupPosition).getFood_Items().get(position).getFood_count());





        return convertView;
    }
}
