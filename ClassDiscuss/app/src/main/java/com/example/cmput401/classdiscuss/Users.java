package com.example.cmput401.classdiscuss;

import android.graphics.Bitmap;
import android.util.Log;

import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Nhu Bui, Nancy Pham-Nguyen, Valerie Sawyer, Cole Fudge, Kelsey Wicentowich
 *
 */

public class Users{

    private static final Users usersInstance = new Users();
    ParseDatabase parseUsers = ParseDatabase.getInstance();

    private HashMap<String, List<String>> usersInfo;
    ArrayList<String> usersValues;
    ArrayList<Bitmap> profileImages;
    util Util = new util();

    private Users() {
        usersInfo = new HashMap<String, List<String>>();
        usersValues = new ArrayList<>();
    }

    public ArrayList<String> getUsersList(){
        ArrayList<String> users = new ArrayList<String>(usersInfo.keySet());
        return users;
    }

    public int getUsersLatitudeByUserName(String username){
        int latitudeNum= 0;
        String latitude = "0";
        if(usersInfo.get(username)!=null){
            if(usersInfo.get(username).get(2) !=null) {
                latitude = usersInfo.get(username).get(2).toString();
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
        if(usersInfo.get(username)!=null){
            if(usersInfo.get(username).get(1) !=null) {
                Longitude = usersInfo.get(username).get(1).toString();
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
        if(usersInfo.get(username)!=null){
            if(usersInfo.get(username).get(3) !=null) {
                channels = usersInfo.get(username).get(3).toString();
            }
        }

        try {
            subscribedChannelList = util.stringToMap(channels);
        } catch(NumberFormatException nfe) {
            System.out.println("Could not parse " + nfe);
        }
        return subscribedChannelList;
    }

    public ArrayList getUsersChannelsList(String username){
        HashMap<String, String> usersChannelMap = getUsersChannelsHashMap(username);
        ArrayList<String> usersChannel = new ArrayList<String>(usersChannelMap.keySet());
        return usersChannel;
    }

    public ArrayList<Bitmap> getUsersImageList(){
        ArrayList<Bitmap> usersImage = new ArrayList<>();
        // iterate and display values
        for (Map.Entry<String, List<String>> entry : usersInfo.entrySet()) {
            List<String> values = entry.getValue();
            Bitmap image = Util.StringToBitMap(values.get(0));
            usersImage.add(image);
        }
        Log.e("score", "getUsersLongitudeByUserName = " + getUsersLongitudeByUserName("kwicento") + "n");
        Log.e("score", "getUsersLatitudeByUserName = " + getUsersLatitudeByUserName("kwicento") + "n");
        return usersImage;
    }

    public Bitmap getUsersImageByUserName(String username){
        String image = usersInfo.get(username).get(0);
        Bitmap ProfilePic = Util.StringToBitMap(image);
        return ProfilePic;
    }

    public void addOrUpdateUser(String name, String BitmapPic, String longitude, String latitude, String Channels ){
        boolean foundUser= false;
        // create list one and store values
        List<String> usersDetailValues = new ArrayList<String>();
        usersDetailValues.add(BitmapPic);
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
            usersInfo.put(name, usersDetailValues );
        }
    }

    public void updateUsersInfo(){
        parseUsers.setUsersDataLocally();
    }

    public void clearUsersList(){
        usersInfo.clear();
    }


    public static Users getInstance(){
        return usersInstance;
    }

}
