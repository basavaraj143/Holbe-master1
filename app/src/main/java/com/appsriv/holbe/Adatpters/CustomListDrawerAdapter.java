package com.appsriv.holbe.Adatpters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.appsriv.holbe.R;

/**
 * Created by appsriv-02 on 17/5/16.
 */
public class CustomListDrawerAdapter extends BaseAdapter{
    private String[] names;
    private String[] desc;
    private Integer[] imageid;
    private Activity context;
    String colour;

    public CustomListDrawerAdapter(Activity context, String[] names, Integer[] imageid , String colour)
    {
        this.context = context;
        this.names = names;
        this.imageid = imageid;
        this.colour = colour;

    }

    @Override
    public int getCount() {
        return names.length;
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
    public View getView(int position, View convertView, ViewGroup parent)
    {
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.drawer_item, null, true);
        ImageView icon = (ImageView) listViewItem.findViewById(R.id.icon);
        TextView type = (TextView) listViewItem.findViewById(R.id.type);
        icon.setBackgroundResource(imageid[position]);
        type.setText(names[position]);
        return  listViewItem;
    }

}