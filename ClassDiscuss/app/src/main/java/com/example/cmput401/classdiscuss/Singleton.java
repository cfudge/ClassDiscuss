package com.example.cmput401.classdiscuss;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by CMDF_Alien on 2/12/2015.
 * Follows the design pattern laid out here:
 * http://www.javaworld.com/article/2073352/core-java/simply-singleton.html
 */
public class Singleton {
    public ArrayList<String> subscribedChannelList;
    ArrayList<String> availableChannelList = new ArrayList<>(Arrays.asList("CMPUT 101", "CampusSocial"));
    private static Singleton instance = null;

    public ArrayList<String> myConnections;
    public ArrayList<String>displayMessage;


    protected Singleton() {
        // Exists only to defeat instantiation.
        subscribedChannelList = new ArrayList<String>();
        myConnections = new ArrayList<String>();
        displayMessage = new ArrayList<String>();
    }
    public static Singleton getInstance() {
        if(instance == null) {
            instance = new Singleton();
        }
        return instance;
    }
}