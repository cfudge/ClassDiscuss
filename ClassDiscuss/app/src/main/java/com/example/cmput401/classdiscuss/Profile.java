package com.example.cmput401.classdiscuss;

import android.net.Uri;

/*
 * copyright 2015 Nhu Bui, Nancy Pham-Nguyen, Valerie Sawyer, Cole Fudge, Kelsey Wicentowich
 */
public class Profile {
    private String name;

    private Uri profilePicURI;
    private static Profile instance = new Profile();

    public Profile() {
        this.name = "";

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
        return profilePicURI;
    }

    public void setProfilePicURI(Uri profilePicURI) {
        this.profilePicURI = profilePicURI;
    }

    public static Profile getInstance() {
        if(instance == null) {
            instance = new Profile();
        }
        return instance;
    }


}