package com.example.cmput401.classdiscuss;

/*
 * copyright 2015 Nhu Bui, Nancy Pham-Nguyen, Valerie Sawyer, Cole Fudge, Kelsey Wicentowich
 */

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
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

        ImageView addImageView = (ImageView) channelAddView.findViewById(R.id.add_channel_button);

        if (myChannels.ifContains(getItem(position))){
            addImageView.setImageResource(R.drawable.ic_remove_channel);
            addImageView.getLayoutParams().height = 130;
        }



        addImageView.setOnClickListener(new View.OnClickListener() {

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
        if(myChannels.getSubscribedList().size() >= 5) {
            alert(position);
            return;
        }
        myChannels.addChannel(getItem(position));
        this.notifyDataSetChanged();

    }

    //Alert Dialog that pops up and asks the user if they want to delete the channel
    public void alert(final int position){
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setMessage(R.string.message_error).setTitle(R.string.error_title);

        builder.setPositiveButton(R.string.okay, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }

        });

        //Get alert dialog
        AlertDialog dialog = builder.create();
        dialog.show();

    }

}
