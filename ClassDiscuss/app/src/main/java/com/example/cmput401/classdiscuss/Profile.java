package com.example.cmput401.classdiscuss;

import android.net.Uri;

/**
 * Created by nhu on 15-02-07.
 */
public class Profile {
    private String name;
    private String email;
    private boolean emailPrivate;

    private Uri profilePicURI;
    private static final Profile profileInstance = new Profile();

    public static Profile getInstance(){
        return profileInstance;
    }


    private Profile() {
        this.name = "";
        this.email = "";
        this.emailPrivate = false;
    }

    public String getUserName() {
        return this.name;
    }

    public String getUserEmail() {

        return this.email;
    }

    public boolean isEmailPrivate() {
        return this.emailPrivate;
    }

    public boolean isEmailPublic() {
        if(this.emailPrivate){
            return false;
        }
        return true;
    }

    public void setUserName(String newName) {
        this.name = newName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setEmailPrivate(boolean privacy){
        this.emailPrivate = privacy;
    }

    public Uri getProfilePicURI() {
        return profilePicURI;
    }

    public void setProfilePicURI(Uri profilePicURI) {
        this.profilePicURI = profilePicURI;
    }

    public String getUsernameFromEmail(){
        String email = getUserEmail();
        String username = email.split("@")[0];
        return username;
    }

}