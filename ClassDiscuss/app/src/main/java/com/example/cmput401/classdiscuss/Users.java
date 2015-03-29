package com.example.cmput401.classdiscuss;

import com.parse.ParseUser;

import java.util.ArrayList;

/**
 *
 * @author Nhu Bui, Nancy Pham-Nguyen, Valerie Sawyer, Cole Fudge, Kelsey Wicentowich
 *
 */

public class Users{

    private static final Users usersInstance = new Users();
    ParseDatabase parseUsers = ParseDatabase.getInstance();

    ArrayList<String> users;

    private Users() {
        users = new ArrayList<String>();
    }

    public ArrayList<String> getUsers(){
        return users;
    }

    public void addNewUser(String name){
        boolean foundUser= false;
        for(int x =0; x < users.size(); x++){
            if(users.get(x).equals(name)){
                foundUser = true;
            }
        }
        if(ParseUser.getCurrentUser() != null){
            if(ParseUser.getCurrentUser().getUsername().toString().equals(name)){
                //don't add current user to list
                foundUser = true;
            }
        }

        //add new users
        if (!foundUser){
            users.add(name);
        }
    }

    public void updateUsersList(){
        parseUsers.setProfileDataLocally();
    }

    public void clearUsersList(){
        users.clear();
    }


    public static Users getInstance(){
        return usersInstance;
    }

}
