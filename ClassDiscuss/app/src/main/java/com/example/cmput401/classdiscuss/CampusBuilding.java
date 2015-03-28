package com.example.cmput401.classdiscuss;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.LatLngBounds;

/**
 * Created by Kelsey on 25/03/2015.
 */
public class CampusBuilding {
    private LatLngBounds bounds;
    private int numPeople;
    private GoogleMap mMap;
    private Marker marker;

    public CampusBuilding(LatLng southwest, LatLng northeast, LatLng center, GoogleMap map)
    {
        mMap = map;
        marker = map.addMarker(
                new MarkerOptions().position(center)
        );
        setNumPeople(0);
        bounds = new LatLngBounds(southwest,northeast);
    }

    public LatLngBounds getBounds() {
        return bounds;
    }

    public int getNumPeople() {
        return numPeople;
    }

    public void setNumPeople(int numPeople) {
        this.numPeople = numPeople;
        if (numPeople == 0)
        {
            marker.setVisible(false);
        }
        else
        {
            marker.setVisible(true);
            marker.setTitle(String.valueOf(numPeople));
            marker.showInfoWindow();
        }
    }
}
