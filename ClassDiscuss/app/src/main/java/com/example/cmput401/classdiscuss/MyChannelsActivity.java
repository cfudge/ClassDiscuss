package com.example.cmput401.classdiscuss;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.PopupWindow;

import com.parse.ParseUser;

/*
 * copyright 2015 Nhu Bui, Nancy Pham-Nguyen, Valerie Sawyer, Cole Fudge, Kelsey Wicentowich
 */
public class MyChannelsActivity extends sideBarMenuActivity {

    ListView listView;
    private MyChannels myChannels;
    MyChannelsAdapter  channelAdapter;

    private Connections myConnections;

    OtherUsers users =  OtherUsers.getInstance();
    PopupWindow popup;
    PopupListAdapter popupAdapter;
    ListView popupList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_channel_screen);

        myChannels = myChannels.getInstance();
        channelAdapter = new MyChannelsAdapter(this, myChannels.getSubscribedList(), MyChannelsActivity.this);

        listView = (ListView) findViewById(R.id.listView);
        listView.setAdapter(channelAdapter);

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


        myConnections = myConnections.getInstance();

    }

    @Override
    protected void onStart(){
        super.onStart();
        if(ParseUser.getCurrentUser() == null){
            getApplicationContext().startActivity(new Intent(getApplicationContext(), MainActivity.class));
        }
    }



    @Override
    protected void onResume(){
        super.onResume();
        channelAdapter.notifyDataSetChanged();
        Notice notice = Notice.getInstance();
        notice.setLive(true);
    }

    @Override
    protected void onPause() {
        super.onPause();
        Notice notice = Notice.getInstance();
        notice.setLive(false);
    }

    public void initializeChannels(){
        //initialized channels in ConnectToParseActivity
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_mychannels, menu);
        return true;
    }


}
