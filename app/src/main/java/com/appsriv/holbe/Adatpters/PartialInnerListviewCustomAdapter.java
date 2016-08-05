package com.appsriv.holbe.Adatpters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.TextView;

import com.appsriv.holbe.R;
import com.appsriv.holbe.models.Group;

import java.util.ArrayList;

public class PartialInnerListviewCustomAdapter extends BaseAdapter{
    String [] result;
    ArrayList<Group> groups;
    Context context;
    int groupPosition , childPosition;
    private static LayoutInflater inflater=null;
    EditText setscount[] = new EditText[1000];
    int listCount;
    private String count;
    int previousindex;

    public PartialInnerListviewCustomAdapter(Context context, ArrayList<Group> groups, int groupPosition, int childPosition,int listCount,int previousindex)
    {
        this.context=context;
        this.groups = groups;
        this.groupPosition=groupPosition;
        this.childPosition=childPosition;
        this.listCount = listCount;
        this.previousindex=previousindex;
        inflater = ( LayoutInflater )context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    @Override
    public int getCount()
    {
        int count = 0;
        try {
            count = Integer.parseInt(groups.get(groupPosition).getItems().get(childPosition).getSets());
        }
        catch (NumberFormatException e)
        {
            e.printStackTrace();
        }
        return listCount;
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
            convertView = inflater.inflate(R.layout.situp_layout, null);
            TextView sets = (TextView)convertView.findViewById(R.id.set);
            sets.setText("SETS "+(position+1));
            Log.d("key inner", String.valueOf(position));
        }
        return convertView;
    }

    public ArrayList<String>  getValues(int pos)
    {
        ArrayList<String> list = new ArrayList<>();
        list.add(setscount[pos].getText().toString());

        return list;

    }


    public String getValue(int i) {
        if(setscount[i]==null){
            return null;
        }else{
            return count;
        }

    }
}