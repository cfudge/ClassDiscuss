package com.example.cmput401.classdiscuss;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.Toast;

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
                //ParsePush push = new ParsePush();


                //push.setMessage("this is my message");
               // push.sendInBackground();
                Intent addChannels = new Intent();
                addChannels.setClass(getApplicationContext(), ChannelsAddActivity.class);
                startActivity(addChannels);
            }
        });


        myConnections = myConnections.getInstance();
        popupMenu();

        //http://stackoverflow.com/questions/28081709/null-pointer-exception-at-actionbar
        android.support.v7.app.ActionBar actionBar =  getSupportActionBar();
        ColorDrawable colorDraw = new ColorDrawable(Color.parseColor("#9FBF8C"));
        actionBar.setBackgroundDrawable(colorDraw);
    }

    @Override
    protected void onStart(){
        super.onStart();
        if(ParseUser.getCurrentUser() == null){
            getApplicationContext().startActivity(new Intent(getApplicationContext(), MainActivity.class));
        }
    }

    //http://stackoverflow.com/questions/21329132/android-custom-dropdown-popup-menu
    //http://android-er.blogspot.ca/2012/03/example-of-using-popupwindow.html
    public void popupMenu(){
        //Button to test popup menu

        popupAdapter = new PopupListAdapter(this, users.getUsersList());

        final Button popupButton = (Button) findViewById(R.id.popup_button);
        popupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                LayoutInflater inflater = (LayoutInflater) getBaseContext().getSystemService(LAYOUT_INFLATER_SERVICE);
                View view = inflater.inflate(R.layout.message_popup, null);
                popup = new PopupWindow(view, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);

                popupList = (ListView) view.findViewById(R.id.popup_list_view);
                popupList.setAdapter(popupAdapter);

                popup.showAtLocation(view, Gravity.CENTER, 0,0);

                ImageButton closeButton = (ImageButton) view.findViewById(R.id.close_button);
                closeButton.setOnClickListener(new ImageButton.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        popup.dismiss();
                    }
                });

                final EditText enterMessage = (EditText) view.findViewById(R.id.enterMessage);
               enterMessage.setOnClickListener(new View.OnClickListener() {
                   @Override
                   public void onClick(View v) {
                       enterMessage.setText("");
                   }
               });

                //Send button
                Button sendButton = (Button) view.findViewById(R.id.sendButton);
                sendButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent connectionsIntent = new Intent();
                        connectionsIntent.setClass(getApplicationContext(), ConnectionsActivity.class);
                        startActivity(connectionsIntent);

                        myConnections.displayMessage.add(enterMessage.getText().toString());

                        Toast.makeText(MyChannelsActivity.this, enterMessage.getText().toString(),
                                Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }


    @Override
    protected void onResume(){
        super.onResume();
        channelAdapter.notifyDataSetChanged();
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
