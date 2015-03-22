package com.example.cmput401.classdiscuss;

import com.parse.Parse;

import java.util.ArrayList;

/*
 * copyright 2015 Nhu Bui, Nancy Pham-Nguyen, Valerie Sawyer, Cole Fudge, Kelsey Wicentowich
 */
public class MyChannels {

    private ArrayList<String> subscribedChannelList;
    private static final MyChannels MyChannelsInstance = new MyChannels();

    public ArrayList<String>displayMessage;


    private MyChannels() {
        this.subscribedChannelList = new ArrayList<String>();
        this.displayMessage = new ArrayList<String>();


    }
    public static MyChannels getInstance(){
        return MyChannelsInstance;
    }

    public void addChannel(String item){
        if(!ifContains(item)){
            this.subscribedChannelList.add(item);
            ParseDatabase.getInstance().addChannels(item);
        }

    }

    public void deleteChannel(){
        //MyChannelsAdapter deleted the item from subscribedChannelList
        //now we just need to update parse.com
        ParseDatabase.getInstance().deleteChannel();
    }

    public boolean ifContains(String item){
        return this.subscribedChannelList.contains(item);
    }

    public ArrayList getSubscribedList(){
        return this.subscribedChannelList;
    }
}