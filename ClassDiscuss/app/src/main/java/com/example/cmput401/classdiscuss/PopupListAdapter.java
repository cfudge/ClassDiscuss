package com.example.cmput401.classdiscuss;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by Nancy on 3/17/2015.
 */
public class PopupListAdapter extends ArrayAdapter<String> {

    Context context;
    ArrayList<String> listItems;
   // Activity activity;


    //PopupListAdapter(Context context, ArrayList<String> listItems, Activity activity) {
    PopupListAdapter(Context context, ArrayList<String> listItems) {
        super(context, R.layout.popup_list, listItems);
        this.context = context;
        this.listItems = listItems;
       // this.activity = activity;

    }


    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View customView = inflater.inflate(R.layout.popup_list, parent, false);

        String classes = getItem(position);
        TextView listText = (TextView) customView.findViewById(R.id.popupText);


        listText.setText(classes);

        onCheckBoxClicked(customView);

        return customView;
    }

      public void onCheckBoxClicked(View view){
        CheckBox checkBox = (CheckBox) view.findViewById(R.id.checkBox);
        boolean checked = checkBox.isChecked();

        switch (view.getId()){
            case R.id.checkBox:
                if(checked){
                    Toast.makeText(getContext(), "Checked",
                            Toast.LENGTH_SHORT).show();
                }

        }
    }

}