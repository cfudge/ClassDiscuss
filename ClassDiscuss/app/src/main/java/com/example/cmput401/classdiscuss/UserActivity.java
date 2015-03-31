package com.example.cmput401.classdiscuss;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.parse.ParseUser;


/*
 * copyright 2015 Nhu Bui, Nancy Pham-Nguyen, Valerie Sawyer, Cole Fudge, Kelsey Wicentowich
 */
public class UserActivity extends sideBarMenuActivity {
    ListView listView ;
    OtherUsers users = OtherUsers.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        //topbar color
        android.support.v7.app.ActionBar actionBar =  getSupportActionBar();
        ColorDrawable colorDraw = new ColorDrawable(Color.parseColor("#9FBF8C"));
        actionBar.setBackgroundDrawable(colorDraw);

        //populate the list// this won't take affect until next time activity starts
        ParseDatabase.getInstance().setUsersDataLocally();

        UsersAdapter adapter = new
                UsersAdapter(UserActivity.this, users.getUsersList(), users.getUsersImageList());

        // Get ListView object from xml
        listView=(ListView)findViewById(R.id.listUsers);

        // Assign adapter to ListView
        listView.setAdapter(adapter);

        // ListView Item Click Listener
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                // ListView Clicked item index
                int itemPosition = position;

                // ListView Clicked item value
                String  itemValue = (String) listView.getItemAtPosition(position);
                Profiles profiles = Profiles.getInstance();
                Profile displayProfile = new Profile();
                displayProfile.getParseEntry("username", itemValue);
                profiles.displayProfile = displayProfile;

                Intent intent = new Intent();
                //intent.setClass(getApplicationContext(), ProfileActivity.class);
                intent.setClass(getApplicationContext(), ChatActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onStart(){
        super.onStart();
        if(ParseUser.getCurrentUser().getUsername() == null){
            getApplicationContext().startActivity(new Intent(getApplicationContext(), MainActivity.class));
        }
    }
}