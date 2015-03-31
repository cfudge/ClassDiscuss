package com.example.cmput401.classdiscuss;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.parse.ParseUser;

import java.util.ArrayList;

/*
 * copyright 2015 Nhu Bui, Nancy Pham-Nguyen, Valerie Sawyer, Cole Fudge, Kelsey Wicentowich
 */
public class ProfileActivity extends sideBarMenuActivity{

    Profiles profiles = Profiles.getInstance();
    private MyChannels myChannels;
    ListView listview;
    ArrayAdapter<String> adapter;
    ParseUser currentUser = ParseUser.getCurrentUser();
    boolean mutual = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile);
        //Folowing https://www.parse.com/docs/android_guide#queries-basic
        //final ArrayList<User> foundUsers = new ArrayList<User>();
        Profile displayProfile = profiles.displayProfile;

        TextView textUserName = (TextView) findViewById(R.id.textUserName);
        TextView textUserEmail = (TextView) findViewById(R.id.textUserEmail);
        TextView textChannelTitle = (TextView) findViewById(R.id.textChannelTitle);
        ImageView profilePicView = (ImageView) findViewById(R.id.imageUserProfile);

        if(displayProfile.getPic() == null){
            Context context= this;
            Bitmap icon= BitmapFactory.decodeResource(context.getResources(),
                    R.drawable.ic_noimage);
            displayProfile.setPic(icon);
            profilePicView.setImageBitmap(icon);
        }
        else{
            profilePicView.setImageBitmap(displayProfile.getPic());
        }
        
        textUserName.setText(displayProfile.getUserName());
        textUserEmail.setText(displayProfile.getEmail());


        myChannels = myChannels.getInstance();
        ArrayList<String> channelsList = OtherUsers.getInstance().getChannelsListByUsername(displayProfile.getUserName());
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
            if(!profiles.displayProfile.getUserName().equals(currentUser.getUsername().toString())){
                if (mutual == true)
                    textChannelTitle.setText("Mutual courses with " + displayProfile.getUserName() + ":");
                else
                    textChannelTitle.setText("You have no courses in common with " + displayProfile.getUserName());
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        MenuInflater menuI = getMenuInflater();

        //display edit option for current user
        if (currentUser != null) {
            if(Profiles.getInstance().displayProfile.getUserName().equals(currentUser.getUsername().toString())){
                menuI.inflate(R.menu.menu_profile, menu);
            }
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch (item.getItemId()) {
            case R.id.action_edit_profile:
                Intent editProfile = new Intent();
                editProfile.setClass(getApplicationContext(), ProfileEditActivity.class);
                startActivity(editProfile);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
