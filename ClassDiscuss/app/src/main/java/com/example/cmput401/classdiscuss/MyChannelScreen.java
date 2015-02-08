package com.example.cmput401.classdiscuss;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;


public class MyChannelScreen extends ActionBarActivity {

    ListView listView;
    public ArrayList<String> channelList;
    public ArrayAdapter<String> channelAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_channel_screen);

        channelList = new ArrayList<String>();
        channelAdapter = new ArrayAdapter<String>(this, R.layout.channel_list, channelList);

        listView = (ListView) findViewById(R.id.listView);
        listView.setAdapter(channelAdapter);
    }

    @Override
    protected void onStart(){
        super.onStart();

        Button addButton = (Button) findViewById(R.id.add_new_channel);
    }

    public void channelSelected(){

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
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
