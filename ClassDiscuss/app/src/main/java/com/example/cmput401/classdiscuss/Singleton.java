package com.example.cmput401.classdiscuss;

import java.util.ArrayList;

/**
 * Created by CMDF_Alien on 2/12/2015.
 * Follows the design pattern laid out here:
 * http://www.javaworld.com/article/2073352/core-java/simply-singleton.html
 */
public class Singleton {
    public ArrayList<String> subscribedChannelList;
    public ArrayList<String> availableChannelList;
    private static Singleton instance = null;
    protected Singleton() {
        // Exists only to defeat instantiation.
        subscribedChannelList = new ArrayList<String>();
    }
    public static Singleton getInstance() {
        if(instance == null) {
            instance = new Singleton();
        }
        return instance;
    }
}