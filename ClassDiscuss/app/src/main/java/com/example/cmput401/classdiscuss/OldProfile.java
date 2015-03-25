package com.example.cmput401.classdiscuss;

import android.net.Uri;

import java.util.Enumeration;
import java.util.Hashtable;

/*
 * copyright 2015 Nhu Bui, Nancy Pham-Nguyen, Valerie Sawyer, Cole Fudge, Kelsey Wicentowich
 */
public class OldProfile {
    private String name;

    private Uri profilePicURI;
    private static OldProfile instance = new OldProfile();
    Hashtable userAndImagesTable;

    public OldProfile() {
        this.name = "";
        this.userAndImagesTable = new Hashtable();

    }

    public void addToUserAndImagesTable(String username, String usersImage){
        userAndImagesTable.put(username, usersImage);
    }

    public Hashtable getUserAndImagesTable(){
        return userAndImagesTable;
    }

    public String getUserName() {
        return this.name;
    }

    public void setUserName(String name){
        this.name = name;
    }

    public String getEmail(){
        String username = this.name;
        String email = username + "@ualberta.ca";
        return email;
    }

    public Uri getProfilePicURI() {
        Enumeration item = userAndImagesTable.keys();
        while (item.hasMoreElements()) {
            String username = (String) item.nextElement();
            if(this.name.equals(username)){
                //get values = ur string
                return Uri.parse(userAndImagesTable.get(username).toString());
            }
        }
        return profilePicURI;
    }

    public void setProfilePicURI(Uri profilePicURI) {
        ParseDatabase.getInstance().setUsersImageToParse(this.name, profilePicURI.toString());

        //need to save it in profile as it is faster than waiting for parse to update
        this.profilePicURI = profilePicURI;
    }

    public static OldProfile getInstance() {
        if(instance == null) {
            instance = new OldProfile();
        }
        return instance;
    }


}