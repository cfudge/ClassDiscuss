package com.example.cmput401.classdiscuss;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.Button;
import android.location.Location;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.LatLngBounds;

import java.util.ArrayList;
import java.util.Vector;

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
    private Location currentLocation; //Will need to be stored with each user
    private ArrayList<CampusBuilding> buildings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        setUpMapIfNeeded();
        updateUserLocation();

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
        // Move the camera instantly to campus with a zoom of 20.
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(CAMPUS, 20));

        // Zoom in, animating the camera.
        mMap.animateCamera(CameraUpdateFactory.zoomTo(15), 2000, null);
        placeBuildingMarkers();
    }

    private void updateUserLocation() {
        GPSLocation.initializeLocation(getApplicationContext());
        currentLocation = GPSLocation.getInstance().getLocation();
    }

    private boolean checkIfOnCampus(double lat, double lng){
        LatLngBounds bounds = new LatLngBounds(new LatLng(53.517288, -113.533018),
                new LatLng(53.530606, -113.511475));
        if (bounds.contains(new LatLng(lat, lng)))
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    private void placeBuildingMarkers() {

        buildings = new ArrayList<CampusBuilding>();
        Marker comSciMarker = mMap.addMarker(
                new MarkerOptions().position(new LatLng(53.526800, -113.527184))
        );

        CampusBuilding comSci = new CampusBuilding((new LatLng(53.526428, -113.527560)),
                (new LatLng(53.527021, -113.526336)), comSciMarker);
        buildings.add(comSci);

        Marker adminMarker = mMap.addMarker(
                new MarkerOptions().position(new LatLng(53.525337, -113.525724))
        );

        CampusBuilding admin = new CampusBuilding((new LatLng(53.525146, -113.525982)),
                (new LatLng(53.525376, -113.525167)), adminMarker);
        buildings.add(admin);

        Marker agriMarker = mMap.addMarker(
                new MarkerOptions().position(new LatLng(53.526107, -113.528040))
        );

        CampusBuilding agri = new CampusBuilding((new LatLng(53.525852, -113.528651)),
                (new LatLng(53.526413, -113.527353)), agriMarker);
        buildings.add(agri);

        Marker artsMarker = mMap.addMarker(
                new MarkerOptions().position(new LatLng(53.526732, -113.522364))
        );

        CampusBuilding oldArts = new CampusBuilding((new LatLng(53.526330, -113.523265)),
                (new LatLng(53.527179, -113.522375)), artsMarker);
        buildings.add(oldArts);

        Marker assiniboiaMarker = mMap.addMarker(
                new MarkerOptions().position(new LatLng(53.527536, -113.526645))
        );

        CampusBuilding assiniboia = new CampusBuilding((new LatLng(53.527185, -113.526763)),
                (new LatLng(53.527791, -113.526345)), assiniboiaMarker);
        buildings.add(assiniboia);

        Marker bioSciMarker = mMap.addMarker(
                new MarkerOptions().position(new LatLng(53.529283, -113.525529))
        );

        CampusBuilding bioSci = new CampusBuilding((new LatLng(53.528657, -113.527388)),
                (new LatLng(53.529734, -113.524405)), bioSciMarker);
        buildings.add(bioSci);

        Marker CCISMarker = mMap.addMarker(
                new MarkerOptions().position(new LatLng(53.528217, -113.525757))
        );

        CampusBuilding CCIS = new CampusBuilding((new LatLng(53.527962, -113.526894)),
                (new LatLng(53.528446, -113.524577)), CCISMarker);
        buildings.add(CCIS);

        Marker camLibMarker = mMap.addMarker(
                new MarkerOptions().position(new LatLng(53.526788, -113.523644))
        );

        CampusBuilding camLib = new CampusBuilding((new LatLng(53.526520, -113.524234)),
                (new LatLng(53.527094, -113.523333)), camLibMarker);
        buildings.add(camLib);

        Marker cabMarker = mMap.addMarker(
                new MarkerOptions().position(new LatLng(53.526559, -113.524815))
        );

        CampusBuilding cab = new CampusBuilding((new LatLng(53.526233, -113.525105)),
                (new LatLng(53.526846, -113.524365)), cabMarker);
        buildings.add(cab);

        Marker chemMarker = mMap.addMarker(
                new MarkerOptions().position(new LatLng(53.527400, -113.524547))
        );

        CampusBuilding chem = new CampusBuilding((new LatLng(53.526852, -113.525319)),
                (new LatLng(53.528025, -113.524236)), chemMarker);
        buildings.add(chem);

        Marker dentPharmMarker = mMap.addMarker(
                new MarkerOptions().position(new LatLng(53.525287, -113.523435))
        );

        CampusBuilding dentPharm = new CampusBuilding((new LatLng(53.525128, -113.524229)),
                (new LatLng(53.525759, -113.522619)), dentPharmMarker);
        buildings.add(dentPharm);

        Marker earthSciMarker = mMap.addMarker(
                new MarkerOptions().position(new LatLng(53.528183, -113.523445))
        );

        CampusBuilding earthSci = new CampusBuilding((new LatLng(53.528055, -113.524475)),
                (new LatLng(53.528297, -113.522490)), earthSciMarker);
        buildings.add(earthSci);

        Marker ECERFMarker = mMap.addMarker(
                new MarkerOptions().position(new LatLng(53.527456, -113.529743))
        );

        CampusBuilding ECERF = new CampusBuilding((new LatLng(53.527067, -113.530365)),
                (new LatLng(53.527685, -113.528627)), ECERFMarker);
        buildings.add(ECERF);

        Marker ECHAMarker = mMap.addMarker(
                new MarkerOptions().position(new LatLng(53.521480, -113.526554))
        );

        CampusBuilding ECHA = new CampusBuilding((new LatLng(53.520695, -113.527091)),
                (new LatLng(53.522399, -113.526157)), ECHAMarker);
        buildings.add(ECHA);

        Marker edMarker = mMap.addMarker(
                new MarkerOptions().position(new LatLng(53.523832, -113.522881))
        );

        CampusBuilding ed = new CampusBuilding((new LatLng(53.523200, -113.525402)),
                (new LatLng(53.524246, -113.522173)), edMarker);
        buildings.add(ed);

        Marker mechEngMarker = mMap.addMarker(
                new MarkerOptions().position(new LatLng(53.527762, -113.527919))
        );

        CampusBuilding mechEng = new CampusBuilding((new LatLng(53.527564, -113.528445)),
                (new LatLng(53.527946, -113.527211)), mechEngMarker);
        buildings.add(mechEng);

        Marker fineArtsMarker = mMap.addMarker(
                new MarkerOptions().position(new LatLng(53.524318, -113.520049))
        );

        CampusBuilding fineArts = new CampusBuilding((new LatLng(53.523960, -113.520586)),
                (new LatLng(53.524566, -113.518107)), fineArtsMarker);
        buildings.add(fineArts);

        Marker generalMarker = mMap.addMarker(
                new MarkerOptions().position(new LatLng(53.526033, -113.529458))
        );

        CampusBuilding general = new CampusBuilding((new LatLng(53.525836, -113.529973)),
                (new LatLng(53.526174, -113.528976)), generalMarker);
        buildings.add(general);

        Marker heritageMarker = mMap.addMarker(
                new MarkerOptions().position(new LatLng(53.522541, -113.522764))
        );

        CampusBuilding heritage = new CampusBuilding((new LatLng(53.522375, -113.523408)),
                (new LatLng(53.522719, -113.521809)), heritageMarker);
        buildings.add(heritage);

        Marker toryMarker = mMap.addMarker(
                new MarkerOptions().position(new LatLng(53.527845, -113.522094))
        );

        CampusBuilding tory = new CampusBuilding((new LatLng(53.527641, -113.522448)),
                (new LatLng(53.528336, -113.521268)), toryMarker);
        buildings.add(tory);

        Marker hubMarker = mMap.addMarker(
                new MarkerOptions().position(new LatLng(53.526729, -113.520763))
        );

        CampusBuilding hub = new CampusBuilding((new LatLng(53.524917, -113.520989)),
                (new LatLng(53.527609, -113.520549)), hubMarker);
        buildings.add(hub);

        Marker humanEcoMarker = mMap.addMarker(
                new MarkerOptions().position(new LatLng(53.525328, -113.530273))
        );

        CampusBuilding humanEco = new CampusBuilding((new LatLng(53.525060, -113.530423)),
                (new LatLng(53.525666, -113.530112)), humanEcoMarker);
        buildings.add(humanEco);

        Marker humanitiesMarker = mMap.addMarker(
                new MarkerOptions().position(new LatLng(53.527210, -113.519745))
        );

        CampusBuilding humanities = new CampusBuilding((new LatLng(53.526713, -113.520410)),
                (new LatLng(53.527383, -113.518629)), humanitiesMarker);
        buildings.add(humanities);

        Marker industrialDesMarker = mMap.addMarker(
                new MarkerOptions().position(new LatLng(53.525273, -113.528467))
        );

        CampusBuilding industrialDes = new CampusBuilding((new LatLng(53.525088, -113.528682)),
                (new LatLng(53.525490, -113.528306)), industrialDesMarker);
        buildings.add(industrialDes);

        Marker lawMarker = mMap.addMarker(
                new MarkerOptions().position(new LatLng(53.524328, -113.518617))
        );

        CampusBuilding law = new CampusBuilding((new LatLng(53.523984, -113.519132)),
                (new LatLng(53.524599, -113.518064)), lawMarker);
        buildings.add(law);

        Marker medMarker = mMap.addMarker(
                new MarkerOptions().position(new LatLng(53.522051, -113.524429))
        );

        CampusBuilding med = new CampusBuilding((new LatLng(53.521840, -113.525452)),
                (new LatLng(53.521840, -113.525452)), lawMarker);
        buildings.add(med);

        Marker liKaShingMarker = mMap.addMarker(
                new MarkerOptions().position(new LatLng(53.522452, -113.521546))
        );

        CampusBuilding liKaShing = new CampusBuilding((new LatLng(53.522057, -113.521868)),
                (new LatLng(53.522688, -113.521106)), liKaShingMarker);
        buildings.add(liKaShing);

        Marker katzMarker = mMap.addMarker(
                new MarkerOptions().position(new LatLng(53.522510, -113.524615))
        );

        CampusBuilding katz = new CampusBuilding((new LatLng(53.522209, -113.525259)),
                (new LatLng(53.522732, -113.523639)), katzMarker);
        buildings.add(katz);

        Marker northPowerPlantMarker = mMap.addMarker(
                new MarkerOptions().position(new LatLng(53.526062, -113.523340))
        );

        CampusBuilding northPowerPlant = new CampusBuilding((new LatLng(53.525915, -113.523747)),
                (new LatLng(53.526177, -113.522889)), northPowerPlantMarker);
        buildings.add(northPowerPlant);

        Marker nrefMarker = mMap.addMarker(
                new MarkerOptions().position(new LatLng(53.526556, -113.529463))
        );

        CampusBuilding nref = new CampusBuilding((new LatLng(53.526314, -113.530332)),
                (new LatLng(53.526888, -113.528830)), nrefMarker);
        buildings.add(nref);

        Marker pembinaMarker = mMap.addMarker(
                new MarkerOptions().position(new LatLng(53.525969, -113.526631))
        );

        CampusBuilding pembina = new CampusBuilding((new LatLng(53.525651, -113.526738)),
                (new LatLng(53.526231, -113.526320)), pembinaMarker);
        buildings.add(pembina);

        Marker rutherfordMarker = mMap.addMarker(
                new MarkerOptions().position(new LatLng(53.526810, -113.523624))
        );

        CampusBuilding rutherford = new CampusBuilding((new LatLng(53.526542, -113.524117)),
                (new LatLng(53.527090, -113.523313)), rutherfordMarker);
        buildings.add(rutherford);

        Marker businessMarker = mMap.addMarker(
                new MarkerOptions().position(new LatLng(53.527403, -113.521961))
        );

        CampusBuilding business = new CampusBuilding((new LatLng(53.527269, -113.522647)),
                (new LatLng(53.527505, -113.521167)), businessMarker);
        buildings.add(business);

        Marker sabMarker = mMap.addMarker(
                new MarkerOptions().position(new LatLng(53.525678, -113.524782))
        );

        CampusBuilding sab = new CampusBuilding((new LatLng(53.525187, -113.524965)),
                (new LatLng(53.526124, -113.524278)), sabMarker);
        buildings.add(sab);

        Marker subMarker = mMap.addMarker(
                new MarkerOptions().position(new LatLng(53.525378, -113.527454))
        );

        CampusBuilding sub = new CampusBuilding((new LatLng(53.525021, -113.528001)),
                (new LatLng(53.525614, -113.526295)), subMarker);
        buildings.add(sub);

        Marker telusMarker = mMap.addMarker(
                new MarkerOptions().position(new LatLng(53.523413, -113.518599))
        );

        CampusBuilding telus = new CampusBuilding((new LatLng(53.523177, -113.519329)),
                (new LatLng(53.523752, -113.517881)), telusMarker);
        buildings.add(telus);

        Marker timmsMarker = mMap.addMarker(
                new MarkerOptions().position(new LatLng(53.523532, -113.519955))
        );

        CampusBuilding timms = new CampusBuilding((new LatLng(53.523213, -113.520599)),
                (new LatLng(53.523800, -113.519408)), timmsMarker);
        buildings.add(timms);

        Marker triffoMarker = mMap.addMarker(
                new MarkerOptions().position(new LatLng(53.526396, -113.523649))
        );

        CampusBuilding triffo = new CampusBuilding((new LatLng(53.526301, -113.524239)),
                (new LatLng(53.526460, -113.523037)), triffoMarker);
        buildings.add(triffo);

        Marker vanViletMarker = mMap.addMarker(
                new MarkerOptions().position(new LatLng(53.523910, -113.527269))
        );

        CampusBuilding vanVilet = new CampusBuilding((new LatLng(53.523055, -113.528804)),
                (new LatLng(53.524688, -113.525789)), vanViletMarker);
        buildings.add(vanVilet);

    }


}
