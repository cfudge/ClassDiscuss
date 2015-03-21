package com.example.cmput401.classdiscuss;

import java.util.ArrayList;

/*
 * copyright 2015 Nhu Bui, Nancy Pham-Nguyen, Valerie Sawyer, Cole Fudge, Kelsey Wicentowich
 */

public class Connections {
    private static Connections instance = null;
    public ArrayList<String> myConnections;
    public ArrayList<String>displayMessage;

    private static final Connections connInstance = new Connections();

    public static Connections getInstance(){
        return connInstance;
    }

    private Connections() {
        myConnections = new ArrayList<String>();
        displayMessage = new ArrayList<String>();

    }

}