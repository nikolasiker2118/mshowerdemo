package com.example.kovac94.meteorshower.score;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.kovac94.meteorshower.R;

import java.util.ArrayList;

/**
 * Created by nikolan on 10/13/2016.
 */

public class ScoresAdapter extends ArrayAdapter<Scores> {
    private Context context;
    private int resource;
    private ArrayList <Scores> objects;

    //Adapter constructor
    public ScoresAdapter(Context context, int resource, ArrayList<Scores> objects) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
        this.objects = objects;
    }
    @Override
    //Method for getting data for adding rows in ListView
    public View getView(int position,
                        View convertView,
                        ViewGroup parent) {
        LayoutInflater inflater= ((Activity) context).getLayoutInflater();
        View row=inflater.inflate(resource,parent,false);
        TextView nick= (TextView) row.findViewById(R.id.textView4);
        TextView score=(TextView) row.findViewById(R.id.textView2);
        nick.setText(objects.get(position).nick);
        score.setText(Integer.toString(objects.get(position).score));
        return row;
    }
}
