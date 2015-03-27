package com.example.cmput401.classdiscuss;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.LatLngBounds;

/**
 * Created by Kelsey on 25/03/2015.
 */
public class CampusBuilding {
    private Marker marker;
    private LatLngBounds bounds;
    private int numPeople;

    public CampusBuilding(LatLng southwest, LatLng northeast, Marker mark)
    {
        setNumPeople(0);
        marker = mark;
        bounds = new LatLngBounds(southwest,northeast);
    }

    public Marker getMarker() {
        return marker;
    }

    public LatLngBounds getBounds() {
        return bounds;
    }

    public int getNumPeople() {
        return numPeople;
    }

    public void setNumPeople(int numPeople) {
        this.numPeople = numPeople;
        /*
        if (getNumPeople() == 0)
        {
            marker.setVisible(false);
        }
        else
        {
            marker.setVisible(true);
        }*/
    }
}
