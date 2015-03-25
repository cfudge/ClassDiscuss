package com.example.cmput401.classdiscuss;

/*
 * copyright 2015 Nhu Bui, Nancy Pham-Nguyen, Valerie Sawyer, Cole Fudge, Kelsey Wicentowich
 */

import android.app.Activity;
import android.content.Context;
import android.graphics.PorterDuff;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class ChannelsAddAdapter extends ArrayAdapter<String>{

    Context context;
    ArrayList<String> listItems;
    Activity activity;
    MyChannels myChannels;

    ChannelsAddAdapter(Context context, ArrayList<String> listItems, Activity activity){
        super(context, R.layout.channel_list, listItems);
        this.context = context;
        this.listItems = listItems;
        this.activity = activity;
        myChannels = MyChannels.getInstance();
    }


    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View channelAddView = inflater.inflate(R.layout.add_channel_list_element, parent, false);

        String channelName = getItem(position);
        TextView listText = (TextView) channelAddView.findViewById(R.id.add_channel_name);
        //final TextView name = (TextView) channelAddView.findViewById(R.id.add_channel_button);

        Button addButton = (Button) channelAddView.findViewById(R.id.add_channel_button);

        /*Changing button to red if already added, based on:
         *http://stackoverflow.com/questions/1521640/standard-android-button-with-a-different-color/1726352#1726352
         */
        if (myChannels.ifContains(getItem(position)))
            addButton.getBackground().setColorFilter(0xFFFF0000, PorterDuff.Mode.MULTIPLY);
        else
            addButton.getBackground().setColorFilter(null);



        addButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

                add(position);
                //test.setText(String.valueOf(position));
            }
        });

        listText.setText(channelName);
        return channelAddView;
    }


    public void add(final int position){
        if(myChannels.ifContains(getItem(position))) {
            Toast.makeText(getContext(), "Already subscribed.",
                    Toast.LENGTH_SHORT).show();
            return;
        }

        myChannels.addChannel(getItem(position));
        this.notifyDataSetChanged();

    }

}
