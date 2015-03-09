package com.example.cmput401.classdiscuss;

/**
 * Created by CMDF_Alien on 2/10/2015.
 */

import android.app.Activity;
import android.content.Context;
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
    Channels channels;

    ChannelsAddAdapter(Context context, ArrayList<String> listItems, Activity activity){
        super(context, R.layout.channel_list, listItems);
        this.context = context;
        this.listItems = listItems;
        this.activity = activity;
        channels = channels.getInstance();
    }


    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View channelAddView = inflater.inflate(R.layout.add_channel_list_element, parent, false);

        String channelName = getItem(position);
        TextView listText = (TextView) channelAddView.findViewById(R.id.add_channel_name);
        //final TextView name = (TextView) channelAddView.findViewById(R.id.add_channel_button);


        Button addButton = (Button) channelAddView.findViewById(R.id.add_channel_button);


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
        if(channels.subscribedChannelList.contains(listItems.get(position))) {
            Toast.makeText(getContext(), "Already subscribed.",
                    Toast.LENGTH_SHORT).show();
            return;
        }
        channels.subscribedChannelList.add(listItems.get(position));
    }

}
