package com.example.cmput401.classdiscuss;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

/**
 * Created by Kelsey on 03/04/2015.
 */
public class OtherUserMapInfo {
    private LatLng latLng;
    private String username;
    private ArrayList<String> channels;

    public OtherUserMapInfo(double lat, double lon, String name)
    {
        latLng = new LatLng(lat, lon);
        username = name;
        channels = new ArrayList<String>();
    }

    public void addChannel(String channel)
    {
        channels.add(channel);
    }

    public void addChannel(ArrayList<String> channelList)
    {
        channels.addAll(channelList);
    }

    public ArrayList<String> getChannels()
    {
        return channels;
    }

    public LatLng getLatLng() {
        return latLng;
    }

    public String getUsername() {
        return username;
    }
}
