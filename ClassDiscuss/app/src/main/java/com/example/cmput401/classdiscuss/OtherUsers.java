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

    private HashMap<String, List<Double>> usersLocationMap;
    private HashMap<String, Bitmap> profileImagesMap;
    private HashMap<String, String> usersChannelsMap;

    private OtherUsers() {
        usersLocationMap = new HashMap<>();
        profileImagesMap = new HashMap<>();
        usersChannelsMap = new HashMap<>();
    }

    public ArrayList<String> getUsersList(){
        if(profileImagesMap.containsKey(ParseUser.getCurrentUser().getUsername())){
            profileImagesMap.remove(ParseUser.getCurrentUser().getUsername());
        }
        ArrayList<String> users = new ArrayList<String>(profileImagesMap.keySet());
        return users;
    }

    public ArrayList<Bitmap> getUsersImageList(){
        if(profileImagesMap.containsKey(ParseUser.getCurrentUser().getUsername())){
            profileImagesMap.remove(ParseUser.getCurrentUser().getUsername());
        }
        ArrayList<Bitmap> usersImage = new ArrayList<>(profileImagesMap.values());
        return usersImage;
    }

    public double getUsersLatitudeByUserName(double username){
        double latitudeNum= 0;
        if(usersLocationMap.get(username)!=null){
            if(usersLocationMap.get(username).get(0) !=null) {
                latitudeNum = usersLocationMap.get(username).get(0).doubleValue();
            }
        }
        return latitudeNum;
    }

    public double getUsersLongitudeByUserName(String username){
        double LongitudeNum= 0;
        if(usersLocationMap.get(username)!=null){
            if(usersLocationMap.get(username).get(0) !=null) {
                LongitudeNum = usersLocationMap.get(username).get(0).doubleValue();
                Log.e("score", "Longitude = " + LongitudeNum + "n");
            }
        }
        return LongitudeNum;
    }

    public HashMap getUsersChannelsHashMap(String username){
        HashMap<String, String> subscribedChannelList = new HashMap<>();

        String channels = "";
        if(usersChannelsMap.get(username)!=null){
            channels = usersChannelsMap.get(username);
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

    public Bitmap getUsersImageByUserName(String username){
        return profileImagesMap.get(username);
    }

    public void updateOtherUsersInfo(String name, double latitude, double longitude, String Channels,Bitmap pic ){
        if(ParseUser.getCurrentUser() != null){
            if(!ParseUser.getCurrentUser().getUsername().toString().equals(name)){
                //don't add current user to list
                UpdateUsersLocationInfo(name, latitude, longitude);
                UpdateUsersChannelsInfo(name, Channels);
                UpdateUserImageInfo(name, pic);
            }
        }
    }

    private void UpdateUsersLocationInfo(String name, double longitude, double latitude){
        boolean foundUser= false;
        // create list one and store values
        ArrayList<Double> usersLocation = new ArrayList<Double>();
        usersLocation.add(longitude);
        usersLocation.add(latitude);

        //add/update new users
        Log.e("score", "Longitude string = " + longitude + "n");
        usersLocationMap.put(name, usersLocation );
    }

    private void UpdateUsersChannelsInfo(String name, String Channels ){
        //add/update new users
        usersChannelsMap.put(name, Channels );
    }

    private void UpdateUserImageInfo(String name, Bitmap pic){
        //add/update new users
        if (pic !=null){
            profileImagesMap.put(name, pic );
        }
    }

    public void clear(){
        usersLocationMap.clear();
        profileImagesMap.clear();
        usersChannelsMap.clear();
    }


    public static OtherUsers getInstance(){
        return usersInstance;
    }

}
