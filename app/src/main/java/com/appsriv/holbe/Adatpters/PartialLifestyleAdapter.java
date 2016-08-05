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
public class PartialLifestyleAdapter extends BaseAdapter {
    EditText timeEditextvalue;
    ArrayList<Group> groups;
    Context context;
    int groupPosition , childPosition;
    private static LayoutInflater inflater=null;
    public PartialLifestyleAdapter(Context context, ArrayList<Group> groups, int groupPosition, int childPosition) {
        this.context=context;
        this.groups = groups;
        this.groupPosition=groupPosition;
        this.childPosition=childPosition;
    }

    @Override
    public int getCount() {
        return groups.get(groupPosition).getLife_Items().size();
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
            convertView = inflater.inflate(R.layout.partial_lifestyle_list_item, null);

        }
        TextView lifestyle_type = (TextView)convertView.findViewById(R.id.lifestyle_type);
        TextView time =(TextView)convertView.findViewById(R.id.lifestyle_time);
        timeEditextvalue =(EditText) convertView.findViewById(R.id.timeedittextvalue);
        lifestyle_type.setText(groups.get(groupPosition).getLife_Items().get(childPosition).getLifestyle_name());
        time.setText(groups.get(groupPosition).getLife_Items().get(position).getTime());
        return convertView;
    }
}
