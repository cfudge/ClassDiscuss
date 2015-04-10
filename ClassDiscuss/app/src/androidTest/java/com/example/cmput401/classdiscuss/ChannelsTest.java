package com.example.cmput401.classdiscuss;

import android.content.Context;
import android.test.ActivityInstrumentationTestCase2;

import com.parse.LogInCallback;
import com.parse.Parse;
import com.parse.ParseUser;

import java.text.ParseException;
import java.util.ArrayList;

/**
 * Created by Valerie on 2015-04-10.
 */
public class ChannelsTest extends ActivityInstrumentationTestCase2 {
    private Context context;
    MyChannels channels = MyChannels.getInstance();

    public ChannelsTest() {
        super(UserActivity.class);
    }

    public void setUp() {
        context = this.getInstrumentation().getTargetContext().getApplicationContext();
        String YOUR_APPLICATION_ID = "OuWwbxVpRVfWh0v3jHEvYeKuuNijBd6M1fVBlkWA";
        String YOUR_CLIENT_KEY = "pYhzGaediLuDVUgQmkuMDkA1DUdKIBtzSziLBdnQ";
        Parse.initialize(context, YOUR_APPLICATION_ID, YOUR_CLIENT_KEY);
        String loginUser = "vsawyer1";
        ParseUser.logInInBackground(loginUser, loginUser, new LogInCallback() {
            @Override
            public void done(ParseUser parseUser, com.parse.ParseException e) {

            }
        });
        channels.addChannel("CMPUT 101");
        channels.addChannel("CMPUT 102");

    }
    public void testIfActive() {
        boolean check = channels.isChannelActive("CMPUT 101");
        assertEquals(true, check);
    }
    public void testIfContains() {
        boolean check = channels.ifContains("CMPUT 101");
        assertEquals(true, check);
        check = channels.ifContains("Ring Theory");
        assertEquals(false, check);
    }

    public void testGetList() {
        ArrayList list = channels.getSubscribedList();
        assertEquals(list.get(0), "CMPUT 101");
        assertEquals(list.get(1), "CMPUT 102");
    }

    public void testDelete() {
        channels.deleteChannel("CMPUT 101");
        boolean check = channels.ifContains("CMPUT 101");
        assertEquals(false, check);
    }

    public void testUnsubscribe() {
        channels.unSubscribeToChannels("CMPUT 101");
        boolean check = channels.isChannelActive("CMPUT 101");
        assertEquals(false, check);
    }
}



