package com.example.cmput401.classdiscuss;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.parse.LogInCallback;
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
        final Profiles profiles = Profiles.getInstance();
        final String loginUserName = profiles.loginEmail.replace("@ualberta.ca", "");
        Log.e("loginusername", loginUserName);

        String App_ID = "OuWwbxVpRVfWh0v3jHEvYeKuuNijBd6M1fVBlkWA";
        String Client_ID= "pYhzGaediLuDVUgQmkuMDkA1DUdKIBtzSziLBdnQ";

        Parse.initialize(this, App_ID, Client_ID);

        ParseUser.logInInBackground(loginUserName, loginUserName, new LogInCallback() {
            public void done(ParseUser user, ParseException e) {
                if (user == null) {
                    ParseUser userParse = new ParseUser();
                    userParse.setUsername(loginUserName);
                    userParse.setPassword(loginUserName);
                    userParse.setEmail(profiles.loginEmail);

                    userParse.signUpInBackground(new SignUpCallback() {
                        public void done(ParseException e) {
                            if (e != null) {
                                //old users /// hmmm wonder how we know if there is an error signing in?


                    /*//needs to wait for parse.com to query the subscribed channels
                    try {
                        Thread.sleep(1000);
                    } catch(InterruptedException ex) {
                        Thread.currentThread().interrupt();
                    }*/

                            } else {
                                //new users

                            }
                        }
                    });
                }
                ParseDatabase.getInstance().Initiate();
                if(ParseUser.getCurrentUser() == null){
                    Log.e("couldn't login", "Couldn't login");
                }
                startApp();
            }
        });
        if(ParseUser.getCurrentUser() == null){
            Log.e("couldn't login", "Couldn't login");
        }
    }

    public void startApp(){
        Intent mapIntent = new Intent();
        mapIntent.setClass(getApplicationContext(), MapActivity.class);
        startActivity(mapIntent);
    }

}
