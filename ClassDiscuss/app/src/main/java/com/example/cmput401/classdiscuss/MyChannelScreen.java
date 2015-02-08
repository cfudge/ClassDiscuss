package com.example.cmput401.classdiscuss;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;


public class MyChannelScreen extends ActionBarActivity {

    ListView listView;
    public ArrayList<String> channelList;
   // public ArrayAdapter<String> channelAdapter;
    CustomAdapter channelAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_channel_screen);

        channelList = new ArrayList<String>();
        channelAdapter = new CustomAdapter(this, channelList, MyChannelScreen.this);

        listView = (ListView) findViewById(R.id.listView);
        listView.setAdapter(channelAdapter);
    }

    @Override
    protected void onStart(){
        super.onStart();

        addChannels();
    }

    public void addChannels(){
        String campusSocial = "CampusSocial";
        String class1 = "CMPUT 101";
        channelList.add(0,class1);
        channelList.add(1, campusSocial);
        channelAdapter.notifyDataSetChanged();

        Button addButton = (Button) findViewById(R.id.add_new_channel);
        addButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

            }
        });
    }


    public void channelSelected(){

    }

    public void alert(){
       AlertDialog.Builder builder = new AlertDialog.Builder(MyChannelScreen.this);
        builder.setMessage(R.string.dialog_message).setTitle(R.string.dialog_title);

        builder.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

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


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_channel_screen, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
         if (id == R.id.action_edit) {
            //removeChannel();
            return true;
        }

        //noinspection SimplifiableIfStatement
        else if (id == R.id.action_settings) {
            return true;
        }


        return super.onOptionsItemSelected(item);
    }
}
