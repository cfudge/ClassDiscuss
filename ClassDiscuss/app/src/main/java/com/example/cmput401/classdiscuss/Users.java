package com.example.cmput401.classdiscuss;

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
        if (!foundUser){
            users.add(name);
        }
    }

    public void updateUsersList(){
        parseUsers.setDataLocally();
    }


    public static Users getInstance(){
        return usersInstance;
    }

}
