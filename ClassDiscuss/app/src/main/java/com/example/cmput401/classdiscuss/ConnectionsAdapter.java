package com.example.cmput401.classdiscuss;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Nancy on 2/28/2015.
 */
public class ConnectionsAdapter extends ArrayAdapter<String> {

    Context context;
    ArrayList<String> listItems;
    ArrayList<String> messages;
    Activity activity;
    Singleton singleton;

    ConnectionsAdapter(Context context, ArrayList<String> listItems, ArrayList<String> messages, Activity activity) {
        super(context, R.layout.connections_list, listItems);
        this.context = context;
        this.listItems = listItems;
        this.messages = messages;
        this.activity = activity;
        singleton = Singleton.getInstance();
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View customView = inflater.inflate(R.layout.connections_list, parent, false);

        String connections = singleton.myConnections.get(position);
        String message = singleton.displayMessage.get(position);
        TextView listText = (TextView) customView.findViewById(R.id.listText);
        TextView secondText = (TextView) customView.findViewById(R.id.secondText);

       listText.setText(connections);
       secondText.setText(message);

        return customView;
    }
}