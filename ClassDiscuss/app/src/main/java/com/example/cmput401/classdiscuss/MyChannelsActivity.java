package com.example.cmput401.classdiscuss;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;


public class MyChannelsActivity extends sideBarMenuActivity {

    ListView listView;
    private MyChannels myChannels;
   // public ArrayAdapter<String> channelAdapter;
   MyChannelsAdapter  channelAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_channel_screen);

        myChannels = myChannels.getInstance();
        channelAdapter = new MyChannelsAdapter(this, myChannels.getSubscribedList(), MyChannelsActivity.this);

        listView = (ListView) findViewById(R.id.listView);
        listView.setAdapter(channelAdapter);

        initializeChannels();

        //needs to wait for parse.com to query the subscribed channels
        try {
            Thread.sleep(1000);
        } catch(InterruptedException ex) {
            Thread.currentThread().interrupt();
        }

        Log.d("score", "update view in MyChannelsActivity");
        //update list
        channelAdapter.notifyDataSetChanged();


        //set add button listener
        Button addButton = (Button) findViewById(R.id.add_new_channel);
        addButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                Intent addChannels = new Intent();
                addChannels.setClass(getApplicationContext(), ChannelsAddActivity.class);
                startActivity(addChannels);
            }
        });
    }

    @Override
    protected void onStart(){
        super.onStart();

    }

    @Override
    protected void onResume(){
        super.onResume();
        channelAdapter.notifyDataSetChanged();
    }

    public void initializeChannels(){
        Parse.getInstance().Initiate();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_channel_screen, menu);
        return true;
    }


}
