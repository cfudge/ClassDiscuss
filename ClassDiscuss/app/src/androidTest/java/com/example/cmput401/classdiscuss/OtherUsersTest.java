package com.example.cmput401.classdiscuss;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.test.ActivityInstrumentationTestCase2;

import com.parse.LogInCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseUser;

import java.util.ArrayList;

/**
 * Created by nhu on 15-04-10.
 */
public class OtherUsersTest extends ActivityInstrumentationTestCase2<UserActivity> {
    private Context context;
    OtherUsers users = OtherUsers.getInstance();

    public OtherUsersTest(){
        super(UserActivity.class);
    }

    public void setUp() {
        context = this.getInstrumentation().getTargetContext().getApplicationContext();
        String YOUR_APPLICATION_ID = "OuWwbxVpRVfWh0v3jHEvYeKuuNijBd6M1fVBlkWA";
        String YOUR_CLIENT_KEY = "pYhzGaediLuDVUgQmkuMDkA1DUdKIBtzSziLBdnQ";
        Parse.initialize(context, YOUR_APPLICATION_ID, YOUR_CLIENT_KEY);
        String loginUser = "nbui";
        ParseUser.logInInBackground(loginUser, loginUser, new LogInCallback() {
            public void done(ParseUser user, ParseException e) {
            }
        });


        String name = "Dave";
        String name1 = "Sara";
        double latitude = 20;
        double longitude =10;
        double latitude1 = 21;
        double longitude1 =11;
        String Channels= "CMPUT101=active&CMPUT102=unActive";
        Bitmap pic = BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_noimage);
        users.updateOtherUsersInfo(name, latitude1, longitude1, Channels, pic);
        users.updateOtherUsersInfo(name1, latitude, longitude, Channels, pic);
    }

    public void test_getUsersList_Sara_Dave(){
        ArrayList<String> usersList = users.getUsersList();
        assertEquals(usersList.get(0),"Sara");
        assertEquals(usersList.get(1),"Dave");
    }


    public void test_getUsersLatitudeByUserName_20_21(){
        double expectedSaraLatitude = 20;
        double expectedDaveLatitude = 21;
        double saraLatitude = users.getUsersLatitudeByUserName("Sara");
        double DaveLatitude = users.getUsersLatitudeByUserName("Dave");
        assertEquals(saraLatitude, expectedSaraLatitude );
        assertEquals(DaveLatitude, expectedDaveLatitude );
    }

    public void test_getUsersLongitudeByUserName_10_11(){
        double expectedSaraLongitude = 10;
        double expectedDaveLongitude = 11;
        double saraLongitude = users.getUsersLongitudeByUserName("Sara");
        double DaveLongitude = users.getUsersLongitudeByUserName("Dave");
        assertEquals(saraLongitude, expectedSaraLongitude );
        assertEquals(DaveLongitude, expectedDaveLongitude );

    }

    public void test_getChannelsListByUsername_CMPUT101_CMPUT102(){
        ArrayList<String> usersChannels = users.getChannelsListByUsername("Sara");
        String channel1 = "CMPUT101";
        String channel2 = "CMPUT102";
        assertEquals(channel1, usersChannels.get(0));
        assertEquals(channel2, usersChannels.get(1));
    }


    public void test_getUsersActiveList_CMPUT101(){
        ArrayList<String> activeChannels = users.getUsersActiveList("Sara");
        String activeChannel = "CMPUT101";
        assertEquals(activeChannel, activeChannels.get(0));

    }

}
