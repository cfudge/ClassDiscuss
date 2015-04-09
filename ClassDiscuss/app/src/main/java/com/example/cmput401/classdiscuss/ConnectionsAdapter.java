package com.example.cmput401.classdiscuss;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/*
 * copyright 2015 Nhu Bui, Nancy Pham-Nguyen, Valerie Sawyer, Cole Fudge, Kelsey Wicentowich
 */
public class ConnectionsAdapter extends ArrayAdapter<String> {

    Context context;
    ArrayList<String> listItems;
    ArrayList<String> messages;
    Activity activity;
    Connections Conn;

    ConnectionsAdapter(Context context, ArrayList<String> listItems, ArrayList<String> messages, Activity activity) {
        super(context, R.layout.connections_list, listItems);
        this.context = context;
        this.listItems = listItems;
        this.messages = messages;
        this.activity = activity;
        Conn = Connections.getInstance();
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View customView = inflater.inflate(R.layout.connections_list, parent, false);

        String connections = Conn.myConnections.get(position);
        String message = Conn.displayMessage.get(position);
        String time = Conn.messageTime.get(position);
        TextView listText = (TextView) customView.findViewById(R.id.listText);
        TextView secondText = (TextView) customView.findViewById(R.id.secondText);
        TextView timeText = (TextView) customView.findViewById(R.id.timeText);
        ImageView ImageView = (ImageView) customView.findViewById(R.id.imageUserProfile);

       listText.setText(connections);
       secondText.setText(message);
       timeText.setText(time);
        ImageView.setImageBitmap(OtherUsers.getInstance().getUsersImageByUserName(connections));

        return customView;
    }
}