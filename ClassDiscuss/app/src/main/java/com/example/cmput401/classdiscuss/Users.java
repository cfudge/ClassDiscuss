package com.example.cmput401.classdiscuss;

import android.graphics.Bitmap;

import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author Nhu Bui, Nancy Pham-Nguyen, Valerie Sawyer, Cole Fudge, Kelsey Wicentowich
 *
 */

public class Users{

    private static final Users usersInstance = new Users();
    ParseDatabase parseUsers = ParseDatabase.getInstance();

    private HashMap<String, Bitmap> usersInfo;
    ArrayList<Bitmap> profileImages;

    private Users() {
        usersInfo = new HashMap<String, Bitmap>();
    }

    public ArrayList<String> getUsersList(){
        ArrayList<String> users = new ArrayList<String>(usersInfo.keySet());
        return users;
    }
    public ArrayList<Bitmap> getUsersImageList(){
        ArrayList<Bitmap> usersImage = new ArrayList<>(usersInfo.values());
        return usersImage;
    }

    public void addNewUser(String name, Bitmap profilePic){
        boolean foundUser= false;
        if (usersInfo.get(name) !=null){
            foundUser= true;
        }
        if(ParseUser.getCurrentUser() != null){
            if(ParseUser.getCurrentUser().getUsername().toString().equals(name)){
                //don't add current user to list
                foundUser = true;
            }
        }
        //add new users
        if (!foundUser){
            usersInfo.put(name, profilePic );
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
