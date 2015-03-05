package com.example.cmput401.classdiscuss;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;


public class MyChannelsActivity extends sideBarMenuActivity {

    ListView listView;
    private Channels channels;
   // public ArrayAdapter<String> channelAdapter;
   MyChannelsAdapter  channelAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_channel_screen);

        channels = Channels.getInstance();
        channelAdapter = new MyChannelsAdapter(this, channels.subscribedChannelList, MyChannelsActivity.this);

        listView = (ListView) findViewById(R.id.listView);
        listView.setAdapter(channelAdapter);
        addChannels();


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

    public void addChannels(){
        if(channels.subscribedChannelList.isEmpty()) {
            String campusSocial = "CampusSocial";
            String class1 = "CMPUT 101";
            channels.subscribedChannelList.add(0, class1);
            channels.subscribedChannelList.add(1, campusSocial);
            channelAdapter.notifyDataSetChanged();
        }
        Button addButton = (Button) findViewById(R.id.add_new_channel);



        addButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                Intent addChannels = new Intent();
                addChannels.setClass(getApplicationContext(), ChannelsAddActivity.class);
                startActivity(addChannels);
            }
        });
    }



    public void channelSelected(){

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_channel_screen, menu);
        return true;
    }


}
