package com.example.cmput401.classdiscuss;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;


/*
 * copyright 2015 Nhu Bui, Nancy Pham-Nguyen, Valerie Sawyer, Cole Fudge, Kelsey Wicentowich
 */
public class UserActivity extends sideBarMenuActivity {
    ListView listView ;
    Users users = Users.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        //populate the list
        users.updateUsersList();


        // Get ListView object from xml
        listView = (ListView) findViewById(R.id.listUsers);


        // Define a new Adapter
        // First parameter - Context
        // Second parameter - Layout for the row
        // Third parameter - ID of the TextView to which the data is written
        // Forth - the Array of data

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1, users.getUsers());


        // Assign adapter to ListView
        listView.setAdapter(adapter);

        // ListView Item Click Listener
        listView.setOnItemClickListener(new OnItemClickListener() {

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
                intent.setClass(getApplicationContext(), ProfileActivity.class);
                startActivity(intent);
            }
        });

    }


}