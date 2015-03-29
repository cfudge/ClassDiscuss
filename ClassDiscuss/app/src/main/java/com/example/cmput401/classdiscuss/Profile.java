package com.example.cmput401.classdiscuss;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by CMDF_Alien on 3/24/2015.
 */
/*Followed the tutorial here:
http://www.androidbegin.com/tutorial/android-parse-com-image-upload-tutorial/
*/

public class Profile{

    private ParseUser parseEntry;

    public String getEmail() {return parseEntry.getString("email");}

    public String getUserName() {return parseEntry.getString("username");}

    public void setUserName(String userName){
        parseEntry.put("username", userName);
        parseEntry.saveInBackground();
    }

    public ParseUser getParseEntry(String searchField, String searchValue) {
        if(parseEntry != null){
            return parseEntry;
        }
        if(searchValue == null){
            return null;
        }
        ParseQuery query = ParseUser.getQuery();
        query.whereEqualTo(searchField, searchValue);
        try {
            parseEntry = (ParseUser) query.find().get(0);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return parseEntry;
    }

    public void setParseEntry(ParseUser parseEntry){
        this.parseEntry = parseEntry;
    }

    public Bitmap getPic(){
        ParseFile picFile = parseEntry.getParseFile("ProfilePic");
        if(picFile == null){
            return null;
        }
        try {
            byte[] image = picFile.getData();
            Bitmap pic = BitmapFactory.decodeByteArray(image, 0, image.length);
            return pic;
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void setPic(Bitmap pic){
        // Convert it to byte
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        // Compress image to lower quality scale 1 - 100
        pic.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] image = stream.toByteArray();

        // Create the ParseFile
        ParseFile file = new ParseFile("userPic.png", image);

        parseEntry.put("ProfilePic", file);
        parseEntry.saveInBackground();

        //update users List
        Users.getInstance().updateUsersInfo();
    }

    public void setLocation(double lat, double lon){
        parseEntry.put("Latitude", lat);
        parseEntry.put("Longitude", lon);
        parseEntry.saveInBackground();
    }

    public ArrayList<String> getChannels() {
        //channels is in hashmap now, this needs to be updated
        List<ParseObject> ob = null;
        ArrayList<String> channelsList = new ArrayList<String>();
        ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("Channels");
        /*query.whereEqualTo("userID", getEmail());
        try {
            ob = query.find();
        } catch (ParseException e) {
            Log.e("Error", e.getMessage());
            e.printStackTrace();
        }
        for (ParseObject channels : ob) {
            channelsList = (ArrayList<String>) channels.get("channels");
        }*/
        return channelsList;
    }

}
