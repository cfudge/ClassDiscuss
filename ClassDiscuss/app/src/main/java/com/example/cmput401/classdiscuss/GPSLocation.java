package com.example.cmput401.classdiscuss;

import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

/**
 * Created by Kelsey on 22/03/2015.
 * Get the location from the user's phone
 * initializeLocation() must be called before this may be used
 * Code taken from https://github.com/CMPUT301W14T02/Comput301Project/
 * blob/master/CMPUT301T02Project/src/ca/ualberta/cs/cmput301t02project/model/GPSLocation.java
 */

public class GPSLocation {
    private LocationManager locationManager;
    private static GPSLocation instance;
    private String provider;

    private GPSLocation(Context context){
        locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        Criteria criteria = new Criteria();
        provider = locationManager.getBestProvider(criteria, true);
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 100, 1, new LocationListener() {
            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {
            }

            @Override
            public void onProviderEnabled(String provider) {
            }

            @Override
            public void onProviderDisabled(String provider) {

            }

            @Override
            public void onLocationChanged(Location location) {

            }
        });
        }

    /* initialize a singleton to get the location */
    public static void initializeLocation(Context context) {
        if(instance == null) {
            instance = new GPSLocation(context);
        }
    }

    public static GPSLocation getInstance() {
        if(instance == null) {
            throw new IllegalAccessError("Location isn't initialized yet");
        }
        return instance;
    }

    /* Get the current location of the device */
    public Location getLocation() {
        Location location = locationManager.getLastKnownLocation(provider);
        if(location == null) {
            location = new Location("");
            location.setLatitude(0.0);
            location.setLongitude(0.0);
        }
        return location;
    }

}


