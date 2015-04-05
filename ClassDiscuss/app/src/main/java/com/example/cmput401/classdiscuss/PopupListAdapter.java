package com.example.cmput401.classdiscuss;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by Nancy on 3/17/2015.
 */
public class PopupListAdapter extends ArrayAdapter<OtherUserMapInfo> {

    Context context;
    ArrayList<OtherUserMapInfo> listItems;
    Connections myConnections;




    PopupListAdapter(Context context, ArrayList<OtherUserMapInfo> listItems) {
        super(context, R.layout.popup_list, listItems);
        this.context = context;
        this.listItems = listItems;
        myConnections = Connections.getInstance();
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View customView = inflater.inflate(R.layout.popup_list, parent, false);

        String classes = getItem(position).getUsername();
        final TextView listText = (TextView) customView.findViewById(R.id.popupText);
        final CheckBox checkBox = (CheckBox) customView.findViewById(R.id.checkBox);

        listText.setText(classes);

        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if(isChecked){
                    Log.d("isChecked", "yes");
                    Toast.makeText(getContext(),listText.getText(),Toast.LENGTH_SHORT).show();

                   if(myConnections.myConnections.contains(listText.getText()));
                   else {
                       myConnections.myConnections.add(listText.getText().toString());
                   }
                    if(!myConnections.tempConnections.contains(listText.getText()))
                        myConnections.tempConnections.add(listText.getText().toString());

                }
                else {
                    Log.d("isChecked", "no");
                    myConnections.tempConnections.remove(listText.getText().toString());

                }
            }
        });
      return customView;
    }

}