package com.example.cmput401.classdiscuss;

import android.widget.TextView;

import java.util.ArrayList;

/*
 * copyright 2015 Nhu Bui, Nancy Pham-Nguyen, Valerie Sawyer, Cole Fudge, Kelsey Wicentowich
 */

public class Connections {
    public ArrayList<String> myConnections;
    public ArrayList<String>displayMessage;

    private static final Connections connInstance = new Connections();

    public static Connections getInstance(){
        return connInstance;
    }

    private Connections() {
        myConnections = new ArrayList<String>();
        displayMessage = new ArrayList<String>();

        myConnections.add("John");
        myConnections.add("Joe");
        myConnections.add("Lisa");
        myConnections.add("Ashley");

        displayMessage.add("Hey, How are you?");
        displayMessage.add("Let's meet up to study");
        displayMessage.add("I'm so tired");
        displayMessage.add("See you later");

    }

}