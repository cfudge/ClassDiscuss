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
    Activity activity;

     CustomAdapter(Context context, ArrayList<String> listItems, Activity activity){
        super(context, R.layout.channel_list, listItems);
        this.context = context;
        this.listItems = listItems;
        this.activity = activity;

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

                alert(position);
                //test.setText(String.valueOf(position));


            }
        });



        listText.setText(classes);


        return customView;
    }




//Alert Dialog that pops up and asks the user if they want to delete the channel
    public void alert(final int position){
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setMessage(R.string.dialog_message).setTitle(R.string.dialog_title);

        builder.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                listItems.remove(position);
                notifyDataSetChanged();
            }

        });

        builder.setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        //Get alert dialog
        AlertDialog dialog = builder.create();
        dialog.show();

    }

}
