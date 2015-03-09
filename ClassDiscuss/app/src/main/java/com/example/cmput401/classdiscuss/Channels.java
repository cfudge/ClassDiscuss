package com.example.cmput401.classdiscuss;

import java.util.ArrayList;

/**
 * Created by CMDF_Alien on 2/12/2015.
 * Follows the design pattern laid out here:
 * http://www.javaworld.com/article/2073352/core-java/simply-singleton.html
 */
public class Channels {
    public ArrayList<String> subscribedChannelList;
    ArrayList<String> availableChannelList = new ArrayList<>();
    private static Channels instance = null;

    public ArrayList<String>displayMessage;


    protected Channels() {
        // Exists only to defeat instantiation.
        availableChannelList.add("CMPUT 101");
        availableChannelList.add("CMPUT 102");
        availableChannelList.add("CMPUT 103");
        availableChannelList.add("CMPUT 104");
        availableChannelList.add("CMPUT 105");
        availableChannelList.add("CMPUT 106");

        subscribedChannelList = new ArrayList<String>();
        displayMessage = new ArrayList<String>();
    }
    public static Channels getInstance() {
        if(instance == null) {
            instance = new Channels();
        }
        return instance;
    }
}