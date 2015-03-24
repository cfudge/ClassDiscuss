package com.example.cmput401.classdiscuss;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.parse.ParseUser;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

/*
 * copyright 2015 Nhu Bui, Nancy Pham-Nguyen, Valerie Sawyer, Cole Fudge, Kelsey Wicentowich
 */
public class ProfileActivity extends sideBarMenuActivity{

    final Profile myProfile = Profile.getInstance();
    private MyChannels myChannels;
    List<ParseObject> ob;
    ListView listview;
    ArrayAdapter<String> adapter;
    ParseUser currentUser = ParseUser.getCurrentUser();
    boolean mutual = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile);


        TextView textUserName = (TextView) findViewById(R.id.textUserName);
        TextView textUserEmail = (TextView) findViewById(R.id.textUserEmail);
        TextView textChannelTitle = (TextView) findViewById(R.id.textChannelTitle);

        textUserEmail.setText(myProfile.getEmail());
        textUserName.setText(myProfile.getUserName());
        Log.d("currentuser", currentUser.getUsername().toString());

        ImageView profilePicView = (ImageView) findViewById(R.id.imageUserProfile);
        myChannels = myChannels.getInstance();


        if(myProfile.getProfilePicURI() != null){
            if(!myProfile.getProfilePicURI().toString().equals("null")) {
                if(myProfile.getProfilePicURI() !=null){
                    profilePicView.setImageURI(myProfile.getProfilePicURI());
                }
            }
        }
        ArrayList<String> channelsList = getChannels(myProfile.getEmail());
        listview = (ListView) findViewById(R.id.channelListview);
        adapter = new ArrayAdapter<String>(ProfileActivity.this,
                R.layout.channel_item);
        for(String channel : channelsList) {
            if(myChannels.ifContains(channel)) {
                mutual = true;
                adapter.add(channel);
            }
        }
        listview.setAdapter(adapter);
        if (currentUser != null) {
            if(!myProfile.getUserName().equals(currentUser.getUsername().toString())){
                if (mutual == true)
                    textChannelTitle.setText("Mutual courses with " + myProfile.getUserName() + ":");
                else
                    textChannelTitle.setText("You have no courses in common with " + myProfile.getUserName());
            }
        }

    }
    // TODO find a better place for this method (or maybe it's good where it is? who knows)-Valerie
    public ArrayList<String> getChannels(String email) {
        ArrayList<String> channelsList = new ArrayList<String>();
        ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("Channels");
        query.whereEqualTo("userID", email);
        try {
            ob = query.find();
        } catch (ParseException e) {
            Log.e("Error", e.getMessage());
            e.printStackTrace();
        }
        for (ParseObject channels : ob) {
            channelsList = (ArrayList<String>) channels.get("channels");
        }
        return channelsList;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        MenuInflater menuI = getMenuInflater();

        //display edit option for current user
        if (currentUser != null) {
            if(Profile.getInstance().getUserName().equals(currentUser.getUsername().toString())){
            //    menuI.inflate(R.menu.menu_profile, menu);
            }
        }
        return true;
    }

}
