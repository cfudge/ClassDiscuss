package com.example.cmput401.classdiscuss;

import java.util.ArrayList;

/**
 * Created by CMDF_Alien on 3/25/2015.
 */
public class Profiles {
    private static Profiles instance;
    public ArrayList<Profile> profiles = new ArrayList<Profile>();
    public Profile thisUser;
    public Profile displayProfile;
    public String loginEmail;

    public static Profiles getInstance(){
        if(instance == null){
            instance = new Profiles();
        }
        return instance;
    }
}
