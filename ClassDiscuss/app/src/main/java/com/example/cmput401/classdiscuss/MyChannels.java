package com.example.cmput401.classdiscuss;

import java.util.ArrayList;
import java.util.HashMap;

/*
 * copyright 2015 Nhu Bui, Nancy Pham-Nguyen, Valerie Sawyer, Cole Fudge, Kelsey Wicentowich
 */
public class MyChannels {
    String ACTIVE = "active";
    String UNACTIVE = "unActive";
    private static final MyChannels MyChannelsInstance = new MyChannels();

    public ArrayList<String> displayMessage;
    private HashMap<String, String> subscribedChannelList;

    private MyChannels() {
        this.displayMessage = new ArrayList<String>();
        this.subscribedChannelList = new HashMap<String, String>();
    }

    public static MyChannels getInstance(){
        return MyChannelsInstance;
    }

    public void addChannel(String item){
        if(!this.subscribedChannelList.containsKey(item)){
            this.subscribedChannelList.put(item, ACTIVE);
            updateChannelsToParse();
        }
    }

    public boolean isChannelActive(String item){
        if(this.subscribedChannelList.containsKey(item)){
            if(this.subscribedChannelList.get(item).contains(ACTIVE)){
                return true;
            }
        }
        return false;
    }

    private void updateChannelsToParse(){
        String HashMapStrings = util.mapToString(subscribedChannelList);
        ParseDatabase.getInstance().UpdateChannelsToParse(HashMapStrings);
    }

    public void initiateLocalChannelsList(String items){
        //the string has to be hashmap compatible
        this.subscribedChannelList.clear();
        this.subscribedChannelList = util.stringToMap(items);
    }

    public void unSubscribeToChannels(String Channel){
        if(this.subscribedChannelList.containsKey(Channel)){
            this.subscribedChannelList.put(Channel, UNACTIVE);
            updateChannelsToParse();
        }
    }

    public void subscribeToChannels(String Channel){
        if(this.subscribedChannelList.containsKey(Channel)){
            this.subscribedChannelList.put(Channel, ACTIVE);
            updateChannelsToParse();
        }
    }

    public void deleteChannel(String item){
        if(this.subscribedChannelList.containsKey(item)){
            this.subscribedChannelList.remove(item);
            updateChannelsToParse();
        }
    }

    public boolean ifContains(String item){
        return this.subscribedChannelList.containsKey(item);
    }

    public ArrayList getSubscribedList(){
        ArrayList<String> list = new ArrayList<String>(subscribedChannelList.keySet());
        return list;
    }
}