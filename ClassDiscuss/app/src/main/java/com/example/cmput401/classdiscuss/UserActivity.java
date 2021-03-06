package com.example.cmput401.classdiscuss;

import android.content.Intent;
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

        //populate the list// this won't take affect until next time activity starts
        ParseDatabase.getInstance().pullParseAndSetOtherUsersData();

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
    protected void onResume() {
        super.onResume();
        Notice notice = Notice.getInstance();
        notice.setLive(true);
    }
    @Override
    protected void onPause() {
        super.onPause();
        Notice notice = Notice.getInstance();
        notice.setLive(false);
    }
    @Override
    protected void onStart(){
        super.onStart();
        if(ParseUser.getCurrentUser().getUsername() == null){
            getApplicationContext().startActivity(new Intent(getApplicationContext(), MainActivity.class));
        }
    }
}