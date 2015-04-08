package com.example.cmput401.classdiscuss;

import java.util.ArrayList;

/*
 * copyright 2015 Nhu Bui, Nancy Pham-Nguyen, Valerie Sawyer, Cole Fudge, Kelsey Wicentowich
 */

/**
 * Created by CMDF_Alien on 2/12/2015.
 * Follows the design pattern laid out here:
 * http://www.javaworld.com/article/2073352/core-java/simply-singleton.html
 */
public class Channels {
    ArrayList<String> availableChannelList = new ArrayList<>();
    private static Channels instance = null;
    public ArrayList<String> displayMessage;

    protected Channels() {
        availableChannelList.add("CMPUT 101");
        availableChannelList.add("CMPUT 102");
        availableChannelList.add("CMPUT 103");
        availableChannelList.add("CMPUT 104");
        availableChannelList.add("CMPUT 105");
        availableChannelList.add("CMPUT 106");
        availableChannelList.add("CMPUT 325");
        availableChannelList.add("CMPUT 401");
        availableChannelList.add("CMPUT 402");
        availableChannelList.add("CMPUT 410");
        availableChannelList.add("CMPUT 414");
        availableChannelList.add("MATH 114");
        availableChannelList.add("MATH 115");
        availableChannelList.add("MUSIC 103");
        availableChannelList.add("STS 350");
        availableChannelList.add("STS 450");
        availableChannelList.add("EAS 100");

        //displayMessage = new ArrayList<String>();
    }
    public static Channels getInstance() {
        if(instance == null) {
            instance = new Channels();
        }
        return instance;
    }
}