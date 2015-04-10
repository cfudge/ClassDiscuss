package com.example.cmput401.classdiscuss;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.PopupWindow;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.parse.ParseException;
import com.parse.ParseInstallation;
import com.parse.ParsePush;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.sql.Timestamp;
import java.util.ArrayList;

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
public class MapActivity extends sideBarMenuActivity {
    static final LatLng CAMPUS = new LatLng(53.5244, -113.5244);
    private GoogleMap mMap; // Might be null if Google Play services APK is not available.
    private ArrayList<CampusBuilding> buildings;
    private Location currentLocation;
    private Connections myConnections;
    OtherUsers users =  OtherUsers.getInstance();
    PopupWindow popup;

    ListView popupList;
    ParseUser currentUser = ParseUser.getCurrentUser();
    MyChannels myChannels = MyChannels.getInstance();
    ArrayList<OtherUserMapInfo> inPopUpBuilding = new ArrayList<OtherUserMapInfo>();

    ArrayList<String> commonChannels;

    ExpandableListView expandView;
    MutualClassAdapter expandAdapter;

    long time =  System.currentTimeMillis();
    Timestamp timeStamp =  new Timestamp(time);
    String tStamp = timeStamp.toString();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        myConnections = myConnections.getInstance();

        //update information
        ParseDatabase.getInstance().updateData();

        setUpMapIfNeeded();
    }
    @Override
    protected void onResume(){
        super.onResume();
        if(ParseUser.getCurrentUser() == null){
            MainActivity out = new MainActivity();
            out.signOutFromGplus();
            getApplicationContext().startActivity(new Intent(getApplicationContext(), MainActivity.class));
        }
        setUpMapIfNeeded();
        Bundle extras = getIntent().getExtras();
        if (mMap != null)
        {
            mMap.clear();
            placeBuildingMarkers();
            setPeopleInBuildings();
            showMeOnMap();
            if (extras != null)
            {
                String name = extras.getString("UserName");
                placePeopleMarker(name);
            }
        }
        Notice notice = Notice.getInstance();
        notice.setLive(true);
    }
    @Override
    protected void onPause() {
        super.onPause();
        Notice notice = Notice.getInstance();
        notice.setLive(false);
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
        updateUserLocation();
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
        setPeopleInBuildings();
        showMeOnMap();

        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                                          @Override
                                          public boolean onMarkerClick(Marker marker) {
                                              for (CampusBuilding build : buildings) {
                                                  if (build.getMarker().equals(marker)) {
                                                      inPopUpBuilding = build.getUsersInBuilding();
                                                      popupMenu();
                                                      break;
                                                  }
                                              }
                                              return true;
                                          }
                                      }
        );
    }

    private void updateUserLocation() {
        GPSLocation.initializeLocation(getApplicationContext());
        GPSLocation gpsLocation = GPSLocation.getInstance();
        currentLocation = gpsLocation.getLocation();
        gpsLocation.setMap(mMap);
    }

    private void placeBuildingMarkers() {

        buildings = new ArrayList<CampusBuilding>();
        Context context = getApplicationContext();

        CampusBuilding comSci = new CampusBuilding((new LatLng(53.526438, -113.527725)),
                (new LatLng(53.527078, -113.526202)), (new LatLng(53.526800, -113.527184)),
                mMap, context);
        buildings.add(comSci);

        CampusBuilding admin = new CampusBuilding((new LatLng(53.525146, -113.525982)),
                (new LatLng(53.525376, -113.525167)), (new LatLng(53.525337, -113.525724)),
                mMap, context);
        buildings.add(admin);

        CampusBuilding agri = new CampusBuilding((new LatLng(53.525852, -113.528651)),
                (new LatLng(53.526413, -113.527353)), (new LatLng(53.526107, -113.528040)),
                mMap, context);
        buildings.add(agri);

        CampusBuilding oldArts = new CampusBuilding((new LatLng(53.526330, -113.523265)),
                (new LatLng(53.527179, -113.522375)), (new LatLng(53.526732, -113.522364)),
                mMap, context);
        buildings.add(oldArts);

        CampusBuilding assiniboia = new CampusBuilding((new LatLng(53.527185, -113.526763)),
                (new LatLng(53.527791, -113.526345)), (new LatLng(53.527536, -113.526645)),
                mMap, context);
        buildings.add(assiniboia);

        CampusBuilding bioSci = new CampusBuilding((new LatLng(53.528657, -113.527388)),
                (new LatLng(53.529734, -113.524405)), (new LatLng(53.529283, -113.525529)),
                mMap, context);
        buildings.add(bioSci);

        CampusBuilding CCIS = new CampusBuilding((new LatLng(53.527962, -113.526894)),
                (new LatLng(53.528446, -113.524577)), (new LatLng(53.528217, -113.525757)),
                mMap, context);
        buildings.add(CCIS);

        CampusBuilding camLib = new CampusBuilding((new LatLng(53.526520, -113.524234)),
                (new LatLng(53.527094, -113.523333)), (new LatLng(53.526788, -113.523644)),
                mMap, context);
        buildings.add(camLib);

        CampusBuilding cab = new CampusBuilding((new LatLng(53.526233, -113.525105)),
                (new LatLng(53.526846, -113.524365)), (new LatLng(53.526559, -113.524815)),
                mMap, context);
        buildings.add(cab);

        CampusBuilding chem = new CampusBuilding((new LatLng(53.526852, -113.525319)),
                (new LatLng(53.528025, -113.524236)), (new LatLng(53.527400, -113.524547)),
                mMap, context);
        buildings.add(chem);

        CampusBuilding dentPharm = new CampusBuilding((new LatLng(53.525128, -113.524229)),
                (new LatLng(53.525759, -113.522619)), (new LatLng(53.525287, -113.523435)),
                mMap, context);
        buildings.add(dentPharm);

        CampusBuilding earthSci = new CampusBuilding((new LatLng(53.528055, -113.524475)),
                (new LatLng(53.528297, -113.522490)), (new LatLng(53.528183, -113.523445)),
                mMap, context);
        buildings.add(earthSci);

        CampusBuilding ECERF = new CampusBuilding((new LatLng(53.527067, -113.530365)),
                (new LatLng(53.527685, -113.528627)), (new LatLng(53.527456, -113.529743)),
                mMap, context);
        buildings.add(ECERF);

        CampusBuilding ECHA = new CampusBuilding((new LatLng(53.520695, -113.527091)),
                (new LatLng(53.522476, -113.525867)), (new LatLng(53.521480, -113.526554)),
                mMap, context);
        buildings.add(ECHA);

        CampusBuilding ed = new CampusBuilding((new LatLng(53.523200, -113.525402)),
                (new LatLng(53.524246, -113.522173)), (new LatLng(53.523832, -113.522881)),
                mMap, context);
        buildings.add(ed);

        CampusBuilding mechEng = new CampusBuilding((new LatLng(53.527564, -113.528445)),
                (new LatLng(53.527946, -113.527211)), (new LatLng(53.527762, -113.527919)),
                mMap, context);
        buildings.add(mechEng);

        CampusBuilding fineArts = new CampusBuilding((new LatLng(53.523960, -113.520586)),
                (new LatLng(53.524566, -113.518107)), (new LatLng(53.524318, -113.520049)),
                mMap, context);
        buildings.add(fineArts);

        CampusBuilding general = new CampusBuilding((new LatLng(53.525836, -113.529973)),
                (new LatLng(53.526174, -113.528976)), (new LatLng(53.526033, -113.529458)),
                mMap, context);
        buildings.add(general);

        CampusBuilding heritage = new CampusBuilding((new LatLng(53.522375, -113.523408)),
                (new LatLng(53.522719, -113.521809)), (new LatLng(53.522541, -113.522764)),
                mMap, context);
        buildings.add(heritage);

        CampusBuilding tory = new CampusBuilding((new LatLng(53.527641, -113.522448)),
                (new LatLng(53.528336, -113.521268)), (new LatLng(53.527845, -113.522094)),
                mMap, context);
        buildings.add(tory);

        CampusBuilding hub = new CampusBuilding((new LatLng(53.524917, -113.520989)),
                (new LatLng(53.527609, -113.520549)), (new LatLng(53.526729, -113.520763)),
                mMap, context);
        buildings.add(hub);

        CampusBuilding humanEco = new CampusBuilding((new LatLng(53.525060, -113.530423)),
                (new LatLng(53.525666, -113.530112)), (new LatLng(53.525328, -113.530273)),
                mMap, context);
        buildings.add(humanEco);

        CampusBuilding humanities = new CampusBuilding((new LatLng(53.526713, -113.520410)),
                (new LatLng(53.527383, -113.518629)), (new LatLng(53.527210, -113.519745)),
                mMap, context);
        buildings.add(humanities);

        CampusBuilding industrialDes = new CampusBuilding((new LatLng(53.525088, -113.528682)),
                (new LatLng(53.525490, -113.528306)), (new LatLng(53.525273, -113.528467)),
                mMap, context);
        buildings.add(industrialDes);

        CampusBuilding law = new CampusBuilding((new LatLng(53.523984, -113.519132)),
                (new LatLng(53.524599, -113.518064)), (new LatLng(53.524328, -113.518617)),
                mMap, context);
        buildings.add(law);

        CampusBuilding med = new CampusBuilding((new LatLng(53.521840, -113.525452)),
                (new LatLng(53.521840, -113.525452)), (new LatLng(53.522051, -113.524429)),
                mMap, context);
        buildings.add(med);

        CampusBuilding liKaShing = new CampusBuilding((new LatLng(53.522057, -113.521868)),
                (new LatLng(53.522688, -113.521106)), (new LatLng(53.522452, -113.521546)),
                mMap, context);
        buildings.add(liKaShing);

        CampusBuilding katz = new CampusBuilding((new LatLng(53.522209, -113.525259)),
                (new LatLng(53.522732, -113.523639)), (new LatLng(53.522510, -113.524615)),
                mMap, context);
        buildings.add(katz);

        CampusBuilding northPowerPlant = new CampusBuilding((new LatLng(53.525915, -113.523747)),
                (new LatLng(53.526177, -113.522889)), (new LatLng(53.526062, -113.523340)),
                mMap, context);
        buildings.add(northPowerPlant);

        CampusBuilding nref = new CampusBuilding((new LatLng(53.526314, -113.530332)),
                (new LatLng(53.526888, -113.528830)), (new LatLng(53.526556, -113.529463)),
                mMap, context);
        buildings.add(nref);

        CampusBuilding pembina = new CampusBuilding((new LatLng(53.525651, -113.526738)),
                (new LatLng(53.526231, -113.526320)), (new LatLng(53.525969, -113.526631)),
                mMap, context);
        buildings.add(pembina);

        CampusBuilding rutherford = new CampusBuilding((new LatLng(53.526542, -113.524117)),
                (new LatLng(53.527090, -113.523313)), (new LatLng(53.526810, -113.523624)),
                mMap, context);
        buildings.add(rutherford);

        CampusBuilding business = new CampusBuilding((new LatLng(53.527269, -113.522647)),
                (new LatLng(53.527505, -113.521167)), (new LatLng(53.527403, -113.521961)),
                mMap, context);
        buildings.add(business);

        CampusBuilding sab = new CampusBuilding((new LatLng(53.525187, -113.524965)),
                (new LatLng(53.526124, -113.524278)), (new LatLng(53.525678, -113.524782)),
                mMap, context);
        buildings.add(sab);

        CampusBuilding sub = new CampusBuilding((new LatLng(53.525021, -113.528001)),
                (new LatLng(53.525614, -113.526295)), (new LatLng(53.525378, -113.527454)),
                mMap, context);
        buildings.add(sub);

        CampusBuilding telus = new CampusBuilding((new LatLng(53.523177, -113.519329)),
                (new LatLng(53.523752, -113.517881)), (new LatLng(53.523413, -113.518599)),
                mMap, context);
        buildings.add(telus);

        CampusBuilding timms = new CampusBuilding((new LatLng(53.523213, -113.520599)),
                (new LatLng(53.523800, -113.519408)), (new LatLng(53.523532, -113.519955)),
                mMap, context);
        buildings.add(timms);

        CampusBuilding triffo = new CampusBuilding((new LatLng(53.526301, -113.524239)),
                (new LatLng(53.526460, -113.523037)), (new LatLng(53.526396, -113.523649)),
                mMap, context);
        buildings.add(triffo);

        CampusBuilding vanVilet = new CampusBuilding((new LatLng(53.523055, -113.528804)),
                (new LatLng(53.524688, -113.525789)), (new LatLng(53.523910, -113.527269)),
                mMap, context);
        buildings.add(vanVilet);

    }

    public void setPeopleInBuildings()
    {
        ArrayList<String> otherUsernames = users.getUsersList();
        ArrayList<String> allChannels = myChannels.getSubscribedList();
        ArrayList<String> activeChannels = new ArrayList<String>();

        for (String channel : allChannels)
        {
            if (myChannels.isChannelActive(channel))
            {
                activeChannels.add(channel);
            }
        }

        ArrayList<OtherUserMapInfo> usersToCheck = new ArrayList<OtherUserMapInfo>();

        //Get users in common channels and set a list to mark on the map
        for (int a=0; a<otherUsernames.size(); a++)
        {
            String otherName = otherUsernames.get(a);
            ArrayList<String> otherUserChannels = users.getChannelsListByUsername(otherName);
            commonChannels = new ArrayList<String>(otherUserChannels);

            commonChannels.retainAll(activeChannels);

            if (commonChannels.isEmpty())
            {
                continue;
            }

            OtherUserMapInfo mapUser = new OtherUserMapInfo((users.getUsersLatitudeByUserName(otherName)),
                    (users.getUsersLongitudeByUserName(otherName)), otherName);
            mapUser.addChannel(commonChannels);

            usersToCheck.add(mapUser);
        }

        for (int i=0; i < usersToCheck.size(); i++)
        {
            for (int a=0; a < buildings.size(); a++)
            {
                CampusBuilding building = buildings.get(a);
                if (building.getBounds().contains(usersToCheck.get(i).getLatLng()))
                {
                    building.setNumPeople(building.getNumPeople()+1);
                    building.addUser(usersToCheck.get(i));
                    break;
                }
            }
        }
    }

    public void popupMenu(){
        expandAdapter = new MutualClassAdapter(this, inPopUpBuilding,commonChannels);
        LayoutInflater inflater = (LayoutInflater) getBaseContext().getSystemService(LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.message_popup, null);
        popup = new PopupWindow(view, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);

        expandView = (ExpandableListView) view.findViewById(R.id.expandList);
        expandView.setAdapter(expandAdapter);

        popup.showAtLocation(view, Gravity.CENTER, 0,0);

        ImageButton closeButton = (ImageButton) view.findViewById(R.id.close_button);
        closeButton.setOnClickListener(new ImageButton.OnClickListener() {
            @Override
            public void onClick(View v) {
                popup.dismiss();
            }
        });

        final EditText enterMessage = (EditText) view.findViewById(R.id.enterMessage);

        enterMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                enterMessage.setText("");
            }
        });


        //Send button
        Button sendButton = (Button) view.findViewById(R.id.sendButton);
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            for (String receiver:myConnections.tempConnections) {
                Message message = new Message();
                message.setUserId(ParseUser.getCurrentUser().getUsername());
                message.setBody(enterMessage.getText().toString());
                message.setReceiver(receiver);
                message.saveInBackground(new SaveCallback() {
                @Override
                public void done(ParseException e) {}});
                ParseQuery userQuery = ParseUser.getQuery();
                userQuery.whereEqualTo("username", receiver);
                if (userQuery == null)
                    Log.d("user query is", "null");
                ParseQuery pushQuery = ParseInstallation.getQuery();
                pushQuery.whereMatchesQuery("user", userQuery);
                // Send push notification to query
                ParsePush push = new ParsePush();
                push.setQuery(pushQuery); // Set our Installation query
                push.setMessage("New Message!");
                push.sendInBackground();
            }
            myConnections.tempConnections.clear();
            Intent connectionsIntent = new Intent();
            connectionsIntent.setClass(getApplicationContext(), ConnectionsActivity.class);
            startActivity(connectionsIntent);

            for(int i = 0; i < myConnections.myConnections.size(); i++)
                myConnections.displayMessage.add(enterMessage.getText().toString());
                for(int j = 0; j < myConnections.displayMessage.size(); j++)
                 myConnections.messageTime.add(tStamp);

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_map, menu);
        return true;
    }


    public void placePeopleMarker(String name){
        double lat = users.getUsersLatitudeByUserName(name);
        double lon = users.getUsersLongitudeByUserName(name);

         if (checkIfOnCampus(lat, lon))
        {
            LatLng location = new LatLng(lat, lon);
            Marker marker = mMap.addMarker(

                new MarkerOptions().position(location)
            );
            drawNameOnMarker(name, marker);
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location,18));
            mMap.animateCamera(CameraUpdateFactory.zoomIn());
            mMap.animateCamera(CameraUpdateFactory.zoomTo(18), 2000, null);
        }

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

    public void drawNameOnMarker(String name, Marker marker)
    {
        Typeface typeface = Typeface.create("Helvetica",Typeface.NORMAL);
        Bitmap markerImage = BitmapFactory.decodeResource(getApplicationContext().getResources(), R.drawable.ic_person_marker).copy(Bitmap.Config.ARGB_8888, true);
        Canvas canvas = new Canvas(markerImage);
        Paint paint = new Paint();
        paint.setTextSize(16);
        paint.setTypeface(typeface);
        paint.setAntiAlias(true);
        paint.setTextAlign(Paint.Align.CENTER);
        int x = canvas.getWidth()/2;
        int y = (markerImage.getHeight()/2) + 5;
        canvas.drawText(name, x, y, paint); // paint defines the text color, stroke width, size
        BitmapDrawable draw = new BitmapDrawable(getApplicationContext().getResources(), markerImage);
        Bitmap drawBmp = draw.getBitmap();
        marker.setIcon(BitmapDescriptorFactory.fromBitmap(drawBmp));
    }

    public void showMeOnMap()
    {
        double lat = currentLocation.getLatitude();
        double lon = currentLocation.getLongitude();
        LatLng location = new LatLng(lat, lon);
        if (checkIfOnCampus(lat, lon)) {
            Marker marker = mMap.addMarker(
                    new MarkerOptions().position(location)
            );
            marker.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.ic_marker_you));
        }
    }

}
