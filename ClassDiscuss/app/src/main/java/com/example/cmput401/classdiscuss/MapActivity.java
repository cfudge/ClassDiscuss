package com.example.cmput401.classdiscuss;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;

/*
 * copyright 2015 Nhu Bui, Nancy Pham-Nguyen, Valerie Sawyer, Cole Fudge, Kelsey Wicentowich
 */
/**
 * Referenced the code here:
 * http://www.vogella.com/tutorials/AndroidGoogleMaps/article.html#googlemaps_activity
 */

/**
 * Left/right swiping follows example here:
 * http://stackoverflow.com/questions/14965124/how-to-change-activity-with-left-right-swipe
 */
public class MapActivity extends FragmentActivity {
    static final LatLng CAMPUS = new LatLng(53.5244, -113.5244);
    private GoogleMap mMap; // Might be null if Google Play services APK is not available.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        setUpMapIfNeeded();

        //set channel button listener
        Button channelButton = (Button) findViewById(R.id.channel_map_btn);
        channelButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                Intent myChannels = new Intent();
                myChannels.setClass(getApplicationContext(), MyChannelsActivity.class);
                startActivity(myChannels);
            }
        });

        //set connections button listener
        Button connectionsButton = (Button) findViewById(R.id.connect_map_btn);
        connectionsButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                Intent myConnections = new Intent();
                myConnections.setClass(getApplicationContext(), ConnectionsActivity.class);
                startActivity(myConnections);
            }
        });
    }
    @Override
    protected void onResume(){
        super.onResume();
        setUpMapIfNeeded();
    }

    /**
     * Sets up the map if it is possible to do so (i.e., the Google Play services APK is correctly
     * installed) and the map has not already been instantiated.. This will ensure that we only ever
     * call {@link #setUpMap()} once when {@link #mMap} is not null.
     * <p/>
     * If it isn't installed {@link SupportMapFragment} (and
     * {@link com.google.android.gms.maps.MapView MapView}) will show a prompt for the user to
     * install/update the Google Play services APK on their device.
     * <p/>
     * A user can return to this FragmentActivity after following the prompt and correctly
     * installing/updating/enabling the Google Play services. Since the FragmentActivity may not
     * have been completely destroyed during this process (it is likely that it would only be
     * stopped or paused), {@link #onCreate(Bundle)} may not be called again so we should call this
     * method in {@link #onResume()} to guarantee that it will be called.
     */
    private void setUpMapIfNeeded() {
        // Do a null check to confirm that we have not already instantiated the map.
        if (mMap == null) {
            // Try to obtain the map from the SupportMapFragment.
            mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map))
                    .getMap();
            // Check if we were successful in obtaining the map.
            if (mMap != null) {
                setUpMap();
            }
        }
    }

    /**
     * This is where we can add markers or lines, add listeners or move the camera. In this case, we
     * just add a marker near Africa.
     * <p/>
     * This should only be called once and when we are sure that {@link #mMap} is not null.
     */
    private void setUpMap() {
        //mMap.addMarker(new MarkerOptions().position(new LatLng(53.523384, -113.525300)).title("Marker"));



        // Move the camera instantly to campus with a zoom of 20.
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(CAMPUS, 20));

        // Zoom in, animating the camera.
        mMap.animateCamera(CameraUpdateFactory.zoomTo(15), 2000, null);
    }

}
