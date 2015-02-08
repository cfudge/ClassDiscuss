package com.example.cmput401.classdiscuss;

/**
 * Created by Nancy on 2/7/2015.
 */

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.preference.DialogPreference;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.view.LayoutInflater;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.TextView;
import android.view.View;
import android.content.Context;

import java.util.ArrayList;

//https://www.youtube.com/watch?v=nOdSARCVYic custom adapter tutorial
public class CustomAdapter extends ArrayAdapter<String>{

     Context context;
     ArrayList<String> listItems;

     CustomAdapter(Context context, ArrayList<String> listItems){
        super(context, R.layout.channel_list, listItems);
        this.context = context;
        this.listItems = listItems;

    }


    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View customView = inflater.inflate(R.layout.channel_list, parent, false);

        String classes = getItem(position);
        TextView listText = (TextView) customView.findViewById(R.id.listText);
        final TextView test = (TextView) customView.findViewById(R.id.test);
        Button deleteButton = (Button) customView.findViewById(R.id.deleteButton);


        deleteButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                
                listItems.remove(position);
                //test.setText(String.valueOf(position));
                notifyDataSetChanged();

            }
        });



        listText.setText(classes);


        return customView;
    }









}
