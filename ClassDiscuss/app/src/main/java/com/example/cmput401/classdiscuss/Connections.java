package com.example.cmput401.classdiscuss;

import android.widget.TextView;

import com.parse.ParseQuery;

import java.sql.Connection;
import java.util.ArrayList;

/*
 * copyright 2015 Nhu Bui, Nancy Pham-Nguyen, Valerie Sawyer, Cole Fudge, Kelsey Wicentowich
 */

public class Connections {
    public ArrayList<String> myConnections;
    public ArrayList<String>displayMessage;
    public ArrayList<String>tempConnections;
    public ArrayList<String>allConnections;

    private static final Connections connInstance = new Connections();

    public static Connections getInstance(){
        return connInstance;
    }

    private Connections() {
        myConnections = new ArrayList<String>();
        displayMessage = new ArrayList<String>();
        tempConnections = new ArrayList<String>();

    }

}