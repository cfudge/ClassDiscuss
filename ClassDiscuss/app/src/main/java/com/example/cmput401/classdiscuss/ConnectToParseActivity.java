package com.example.cmput401.classdiscuss;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

/*
 * copyright 2015 Nhu Bui, Nancy Pham-Nguyen, Valerie Sawyer, Cole Fudge, Kelsey Wicentowich
 */
public class ConnectToParseActivity extends Activity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String App_ID = "OuWwbxVpRVfWh0v3jHEvYeKuuNijBd6M1fVBlkWA";
        String Client_ID= "pYhzGaediLuDVUgQmkuMDkA1DUdKIBtzSziLBdnQ";

        Parse.initialize(this, App_ID, Client_ID);

        //create new user
        Profile User = Profile.getInstance();
        ParseUser userParse = new ParseUser();
        userParse.setUsername(User.getUserName());
        userParse.setPassword(User.getUserName());
        userParse.setEmail(User.getEmail());

        // other fields can be set just like with ParseObject
        userParse.put("Image", "null");

        userParse.signUpInBackground(new SignUpCallback() {
            public void done(ParseException e) {
                if (e != null) {
                    //error happen
                    //logout or something
                    //right now we are doing nothing, but if you log into nim without a uofa
                    //account, you will experience alot of crashes...
                    //only because no tables are created for you in parse
                } else {
                    //success
                }
                ParseDatabase.getInstance().Initiate();
            }
        });

        /*//needs to wait for parse.com to query the subscribed channels
        try {
            Thread.sleep(1000);
        } catch(InterruptedException ex) {
            Thread.currentThread().interrupt();
        }*/

        Intent mapIntent = new Intent();
        mapIntent.setClass(getApplicationContext(), MapActivity.class);
        startActivity(mapIntent);

    }

}
