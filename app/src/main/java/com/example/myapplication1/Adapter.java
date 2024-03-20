package com.example.myapplication1;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class Adapter extends BaseAdapter implements Filterable {

    private Activity activity;
    private ArrayList<User> data;
    private ArrayList<User> databackup;
    private LayoutInflater inflater;
    public Adapter(Activity activity, ArrayList<User> data) {
        this.activity = activity;
        this.data = data;
        this.inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        if(v == null){
            v = inflater.inflate(R.layout.activity_details,null);
            ImageView image = v.findViewById(R.id.ivAva);
            TextView name = v.findViewById(R.id.txtName);
            name.setText(data.get(position).getName());
            TextView phone = v.findViewById(R.id.txtPhone);
            phone.setText(data.get(position).getPhone());
        }
        return v;
    }

    @Override
    public Filter getFilter() {
        Filter f = new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults fr = new FilterResults();
                if(databackup == null){
                    databackup = new ArrayList<>(data);
                }
                if(constraint==null || constraint.length() == 0){
                    fr.count = databackup.size();
                    fr.values = databackup;
                }
                else {
                    ArrayList<User> newdata = new ArrayList<>();
                    for(User c:databackup){
                        if(c.getName().toLowerCase().contains(
                                constraint.toString().toLowerCase()
                        )) newdata.add(c);
                    }
                    fr.count = newdata.size();
                    fr.values = newdata;
                }
                return fr;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                data = new ArrayList<User>();
                ArrayList<User>tmp = (ArrayList<User>)results.values;
                for(User c:tmp)
                    data.add(c);
                notifyDataSetChanged();
            }
        };
        return f;
    }
}
