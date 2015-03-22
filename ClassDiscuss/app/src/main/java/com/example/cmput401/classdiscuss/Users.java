package com.example.cmput401.classdiscuss;
import java.util.ArrayList;

/**
 *
 * @author Nhu Bui, Nancy Pham-Nguyen, Valerie Sawyer, Cole Fudge, Kelsey Wicentowich
 *
 */

public class Users{

    private static final Users usersInstance = new Users();
    Parse parseUsers = Parse.getInstance();

    ArrayList<String> users;

    private Users() {
        users = new ArrayList<String>();
    }

    public ArrayList<String> getUsers(){
        return users;
    }

    public void addUser(String name){
        users.add(name);
    }

    public void updateUsersList(){
        /*Parse parseUsers = Parse.getInstance();
        int userSize = parseUsers.getAllUsers();
        int userSize = parseUsers.size();
        for(int x =0; x < userSize; x++  ){
            usersList.addUser(parseUsers.get(x).getUsername());
        }*/
    }


    public static Users getInstance(){
        return usersInstance;
    }

}
