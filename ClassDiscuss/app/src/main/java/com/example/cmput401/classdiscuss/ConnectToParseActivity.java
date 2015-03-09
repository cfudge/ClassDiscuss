package com.example.cmput401.classdiscuss;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

/**
 * Created by nhu on 15-03-07.
 */

public class ConnectToParseActivity extends Activity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String App_ID = "7B1T7M2TH01J3jKhJjsYAHQKRYCXVvZKXMMwoQjG";
        String Client_ID= "Q8MOuQJaOeyaTeNC1MQxivTghm2bnZzrwvtp9oac";

        Parse.initialize(this, App_ID, Client_ID);


        //create new user
        Profile User = Profile.getInstance();
        ParseUser userParse = new ParseUser();
        userParse.setUsername(User.getUserName());
        userParse.setPassword(User.getUserName());
        userParse.setEmail(User.getUserEmail());

        // other fields can be set just like with ParseObject
        //user.put("phone", "650-555-0000");

        userParse.signUpInBackground(new SignUpCallback() {
            public void done(ParseException e) {
                if (e != null) {
                    //start myChannels activity
                    Intent myProfile = new Intent();
                    myProfile.setClass(getApplicationContext(), MyChannelsActivity.class);
                    startActivity(myProfile);

                } else {

                }
            }
        });


    }

}
