package com.example.cmput401.classdiscuss;

import java.util.ArrayList;

/*
 * copyright 2015 Nhu Bui, Nancy Pham-Nguyen, Valerie Sawyer, Cole Fudge, Kelsey Wicentowich
 */

public class Connections {
    public ArrayList<String> myConnections;
    public ArrayList<String>displayMessage;
    public ArrayList<String>tempConnections;
    public ArrayList<String>allConnections;
    public ArrayList<String>messageTime;

    private static final Connections connInstance = new Connections();

    public static Connections getInstance(){
        return connInstance;
    }

    private Connections() {
        myConnections = new ArrayList<String>();
        displayMessage = new ArrayList<String>();
        tempConnections = new ArrayList<String>();
        messageTime = new ArrayList<String>();

    }
    public void clear(){
        myConnections.clear();
        displayMessage.clear();
        messageTime.clear();
    }

}