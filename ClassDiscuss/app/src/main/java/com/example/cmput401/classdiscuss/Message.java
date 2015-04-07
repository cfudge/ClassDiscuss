package com.example.cmput401.classdiscuss;

import android.graphics.Bitmap;

import com.parse.ParseClassName;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.io.ByteArrayOutputStream;
import java.util.Date;

/*
 * copyright 2015 Nhu Bui, Nancy Pham-Nguyen, Valerie Sawyer, Cole Fudge, Kelsey Wicentowich
 */
@ParseClassName("Message")
public class Message extends ParseObject {
    private Bitmap smallPostPic;
    private Bitmap postPic = null;
    private Bitmap proPic = null;
    private boolean triedProFetch = false;
    private boolean triedPostPicFetch = false;
    private Profiles profiles = Profiles.getInstance();
    public String getUserId() {
        return getString("userId");
    }

    public String getBody() {
        return getString("body");
    }

    public void setUserId(String userId) {
        put("userId", userId);
    }

    public void setBody(String body) {
        put("body", body);
    }
    public void setReceiver(String receiver) { put("Receiver", receiver);}

    public Bitmap getSmallPostPic() { return smallPostPic; }

    public Bitmap getPic() {
        if(triedPostPicFetch){
            return postPic;
        }
        triedPostPicFetch = true;
        ParseFile picFile = getParseFile("picture");
        if (picFile == null) {
            return null;
        }
        else {
            Bitmap result = ImageOperations.bitmapFromPicFile(picFile, 300, 300);
            try {
                this.pin();
            } catch (ParseException e) {
                e.printStackTrace();
            }
            triedPostPicFetch = true;
            postPic = result;
            return result;
        }
    }

    public Bitmap getPosterPic(){
        if(triedProFetch){
            return proPic;
        }
        Bitmap result;
        triedProFetch = true;
        if(profiles.profilePics.containsKey(getUserId())){
            result = profiles.profilePics.get(getUserId());
            proPic = result;
            return result;
        }
        ParseFile picFile = null;
        ParseUser sender = null;
        ParseQuery query = ParseUser.getQuery();
        query.whereEqualTo("username", getUserId());
        try {
            sender = (ParseUser) query.find().get(0);
            sender.pin();
        } catch (Exception e) {
            //e.printStackTrace();
        }

        if(sender != null) {
            picFile = sender.getParseFile("ProfilePic");
        }
        if(picFile != null) {
            result = ImageOperations.bitmapFromPicFile(picFile, 100, 100);
            profiles.profilePics.put(getUserId(), result);
            proPic = result;
            return result;
        }
        else{
            return null;
        }
    }

    public void setPic(Bitmap pic){
        // Convert it to byte
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        // Compress image to lower quality scale 1 - 100
        pic.compress(Bitmap.CompressFormat.PNG, 20, stream);
        byte[] image = stream.toByteArray();

        // Create the ParseFile
        ParseFile file = new ParseFile("comment_pic.png", image);
        smallPostPic = ImageOperations.bitmapFromPicFile(file, 50, 50);
        put("picture", file);

    }

    public Date getPostTime(){
        return getCreatedAt();
    }

}