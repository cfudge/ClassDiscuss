package com.example.cmput401.classdiscuss;

import android.graphics.Bitmap;
import android.util.Log;

import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

/**
 *
 * @author Nhu Bui, Nancy Pham-Nguyen, Valerie Sawyer, Cole Fudge, Kelsey Wicentowich
 *
 */

public class OtherUsers{

    private static final OtherUsers usersInstance = new OtherUsers();
    ParseDatabase parseUsers = ParseDatabase.getInstance();

    private HashMap<String, List<String>> usersInfoMap;
    ArrayList<String> usersValues;
    HashMap<String, Bitmap> profileImagesMap;
    util Util = new util();

    private OtherUsers() {
        usersInfoMap = new HashMap<String, List<String>>();
        usersValues = new ArrayList<>();
        profileImagesMap = new HashMap<>();
    }

    public ArrayList<String> getUsersList(){
        ArrayList<String> users = new ArrayList<String>(profileImagesMap.keySet());
        return users;
    }

    public int getUsersLatitudeByUserName(String username){
        int latitudeNum= 0;
        String latitude = "0";
        if(usersInfoMap.get(username)!=null){
            if(usersInfoMap.get(username).get(1) !=null) {
                latitude = usersInfoMap.get(username).get(1).toString();
            }
        }

        try {
            latitudeNum = Integer.parseInt(latitude);
        } catch(NumberFormatException nfe) {
            System.out.println("Could not parse " + nfe);
        }
        return latitudeNum;
    }

    public int getUsersLongitudeByUserName(String username){
        int LongitudeNum= 0;
        String Longitude = "0";
        if(usersInfoMap.get(username)!=null){
            if(usersInfoMap.get(username).get(0) !=null) {
                Longitude = usersInfoMap.get(username).get(0).toString();
                Log.e("score", "Longitude = " + Longitude + "n");
            }
        }

        try {
            LongitudeNum = Integer.parseInt(Longitude);
        } catch(NumberFormatException nfe) {
            System.out.println("Could not parse " + nfe);
        }
        return LongitudeNum;
    }

    public HashMap getUsersChannelsHashMap(String username){
        HashMap<String, String> subscribedChannelList = new HashMap<String, String>();

        String channels = "";
        if(usersInfoMap.get(username)!=null){
            if(usersInfoMap.get(username).get(2) !=null) {
                channels = usersInfoMap.get(username).get(2).toString();
            }
        }

        try {
            subscribedChannelList = util.stringToMap(channels);
        } catch(NumberFormatException nfe) {
            System.out.println("Could not parse " + nfe);
        }
        return subscribedChannelList;
    }

    public ArrayList getChannelsListByUsername(String username){
        HashMap<String, String> usersChannelMap = getUsersChannelsHashMap(username);
        ArrayList<String> usersChannelList = new ArrayList<String>(usersChannelMap.keySet());
        return usersChannelList;
    }

    public ArrayList getUsersActiveList(String username){
        HashMap<String, String> usersChannelMap = getUsersChannelsHashMap(username);
        ArrayList<String> usersActiveChannel = new ArrayList<String>();

        Iterator<String> keySetIterator = usersChannelMap.keySet().iterator();

        while(keySetIterator.hasNext()){
            String key = keySetIterator.next();
            if(usersChannelMap.get(key).contains("active")){
                usersActiveChannel.add(usersChannelMap.get(key));
            };
        }

        return usersActiveChannel;
    }

    public ArrayList<Bitmap> getUsersImageList(){
        ArrayList<Bitmap> usersImage = new ArrayList<>(profileImagesMap.values());
        return usersImage;

    }

    public Bitmap getUsersImageByUserName(String username){
        return profileImagesMap.get(username);
    }

    public void UpdateUserLocationInfo(String name, String longitude, String latitude, String Channels ){
        boolean foundUser= false;
        // create list one and store values
        List<String> usersDetailValues = new ArrayList<String>();
        usersDetailValues.add(longitude);
        usersDetailValues.add(latitude);
        usersDetailValues.add(Channels);
        if(ParseUser.getCurrentUser() != null){
            if(ParseUser.getCurrentUser().getUsername().toString().equals(name)){
                //don't add current user to list
                foundUser = true;
            }
        }
        //add/update new users
        if (!foundUser){
            Log.e("score", "Longitude string = " + longitude + "n");
            usersInfoMap.put(name, usersDetailValues );
        }
    }

    public void UpdateUserImageInfo(String name, Bitmap pic){
        boolean foundUser= false;

        if(ParseUser.getCurrentUser() != null){
            if(ParseUser.getCurrentUser().getUsername().toString().equals(name)){
                //don't add current user to list
                foundUser = true;
            }
        }
        //add/update new users
        if (!foundUser && pic !=null){
            profileImagesMap.put(name, pic );
        }
    }

    public void updateUsersInfo(){
        parseUsers.setUsersDataLocally();
    }

    public void clearUsersInfoMap(){
        usersInfoMap.clear();
    }


    public static OtherUsers getInstance(){
        return usersInstance;
    }

}
