package com.example.cmput401.classdiscuss;

import android.app.Application;

import com.parse.Parse;
import com.parse.ParseObject;

/**
 * Created by Valerie on 2015-03-06.
 */
public class ChatApplication extends Application {
    public static final String YOUR_APPLICATION_ID = "OuWwbxVpRVfWh0v3jHEvYeKuuNijBd6M1fVBlkWA";
    public static final String YOUR_CLIENT_KEY = "pYhzGaediLuDVUgQmkuMDkA1DUdKIBtzSziLBdnQ";
    @Override
    public void onCreate() {
        super.onCreate();
        Parse.enableLocalDatastore(this);
        // Register your parse models here
        ParseObject.registerSubclass(Message.class);
        Parse.initialize(this, YOUR_APPLICATION_ID, YOUR_CLIENT_KEY);
    }
}