package com.example.cmput401.classdiscuss;

/**
 * Created by Nancy on 2/7/2015.
 */

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.preference.DialogPreference;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.view.LayoutInflater;
import android.widget.Button;
import android.widget.ImageButton;
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


        ImageButton deleteButton = (ImageButton) customView.findViewById(R.id.deleteButton);
        final Button statusButton = (Button) customView.findViewById(R.id.activeButton);
        final Drawable inactivePic = customView.getResources().getDrawable(R.drawable.ic_inactive);
        final Drawable activePic = customView.getResources().getDrawable(R.drawable.ic_active);
        statusButton.setTag("Active");
        statusButton.setText("0");


            deleteButton.setOnClickListener(new View.OnClickListener() {

                public void onClick(View v) {

                    alert(position);
                    //test.setText(String.valueOf(position));
                }
            });

        statusButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                if (statusButton.getTag().equals("Active")){
                    confirm(position, statusButton, inactivePic);
                    //test.setText(String.valueOf(position));
                }
                else {
                    statusButton.setText("0");
                    statusButton.setTag("Active");
                    statusButton.setBackgroundDrawable(activePic);
                }
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

    public void confirm(final int position, final Button button, final Drawable icon) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setMessage(R.string.deactivate_message).setTitle(R.string.confirm_deactivate_title);

        builder.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                button.setText("");
                button.setBackgroundDrawable(icon);
                button.setTag("Inactive");
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
