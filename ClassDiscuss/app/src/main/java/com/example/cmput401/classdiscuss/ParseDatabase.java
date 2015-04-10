package com.example.cmput401.classdiscuss;

import android.app.Activity;
import android.graphics.Bitmap;
import android.util.Log;

import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TreeMap;

/*
 * copyright 2015 Nhu Bui, Nancy Pham-Nguyen, Valerie Sawyer, Cole Fudge, Kelsey Wicentowich
 */
public class ParseDatabase extends Activity {
    private static final ParseDatabase parseInstance = new ParseDatabase();
    util Util = new util();
    //String ChannelsClass= "Channels";//classes in class
    String UserClass = "_User";//classes in class
    String myChannelObjectId;
    List channelList;
    Bitmap defaultProfilePic;

    private ParseDatabase() {
        this.myChannelObjectId = ""; //this is for the main user
        this.channelList = Collections.emptyList();
        this.defaultProfilePic = null;
    }
    public void setDefaultProfilePic(Bitmap pic){
        this.defaultProfilePic = pic;
    }

    public Bitmap getDefaultProfilePic(){
        return defaultProfilePic;
    }

    public static ParseDatabase getInstance() {
        return parseInstance;
    }

    public void updateData() {
        initiateChannels();
        pullParseAndSetOtherUsersData();
        if(ParseUser.getCurrentUser()!=null){
            pullConnectionsInfo(ParseUser.getCurrentUser().getUsername());
        }

    }
    public void updateDataInBackground(){
        initiateChannels();
        pullParseAndSetOtherUsersDatainBackground();
    }

    private void initiateChannels() {
        //create new channels to parse only if the user is new
        //get the object id now and save it for future use
        ParseQuery<ParseUser> query = ParseUser.getQuery();
        query.whereEqualTo("email", Profiles.getInstance().loginEmail);

        //check if email already existed in database
        query.getFirstInBackground(new GetCallback<ParseUser>() {
            public void done(ParseUser object, ParseException e) {
                if (object == null) {
                    //get the object id now and save it for future uses
                    setMyChannelObjectId(object.getObjectId());
                    object.put("channels", "");

                    object.saveInBackground();
                } else {
                    //already exists
                    //get the object id now and save it for future uses
                    setMyChannelObjectId(object.getObjectId());

                    //set the subscribed channels list locally
                    String channelList = object.getString("channels");
                    MyChannels subscriptionList = MyChannels.getInstance();
                    subscriptionList.initiateLocalChannelsList(channelList);

                    Log.d("score", "subscription list updated");
                }
            }
        });
    }

    /*private void createNewProfileToParse() {
        ParseQuery<ParseObject> query = ParseQuery.getQuery(ImageClass);
        query.whereEqualTo("username", OldProfile.getInstance().getUserName());

        //check if email already existed in database
        query.getFirstInBackground(new GetCallback<ParseObject>() {
            public void done(ParseObject object, ParseException e) {
                if (object == null) {
                    //make a new object for the user in the database
                    ParseObject Channels = new ParseObject(ImageClass);
                    Channels.put("Image", "");
                    Channels.put("username", OldProfile.getInstance().getUserName());
                    Channels.saveInBackground();

                } else { //success
                    //already existed
                }
            }
        });
    }*/

    public String getMyChannelObjectId() {
        return this.myChannelObjectId;
    }

    public void setMyChannelObjectId(String ID) {
        this.myChannelObjectId = ID;
    }

    /*public void UpdateChannelsToParse(final String item) {
        ParseQuery<ParseObject> query = ParseQuery.getQuery(ChannelsClass);

        // Retrieve the object by id
        query.getInBackground(getMyChannelObjectId(), new GetCallback<ParseObject>() {
            public void done(ParseObject ParseChannels, ParseException e) {
                if (e == null) {

                    ParseChannels.add("channels", item);
                    ParseChannels.saveInBackground();
                }
            }
        });
    }*/

    public void UpdateChannelsToParse(final String HashMapStrings) {
        ParseQuery<ParseUser> query = ParseUser.getQuery();
        // Retrieve the object by id
        query.getInBackground(getMyChannelObjectId(), new GetCallback<ParseUser>() {
            public void done(ParseUser ParseChannels, ParseException e) {
                if (e == null) {
                    MyChannels list = MyChannels.getInstance();
                    //we are just gonna update the string
                    ParseChannels.put("channels", HashMapStrings);
                    ParseChannels.saveInBackground();
                }
            }
        });
    }
    public void pullParseAndSetOtherUsersData() {
        ParseQuery<ParseUser> query = ParseUser.getQuery();
        List<ParseUser> objects = null;
        try {
            objects = query.find();
        } catch (ParseException e) {
            Log.e("Error", e.getMessage());
            e.printStackTrace();
        }

        updateOtherUsersData(objects);
        Log.d("score", "updated users info");
    }

    public void pullConnectionsInfo(String current_user) {
        ParseQuery<ParseObject> query1 = ParseQuery.getQuery("Message");
        query1.whereEqualTo("userId", current_user);

        ParseQuery<ParseObject> query2 = ParseQuery.getQuery("Message");
        query2.whereEqualTo("Receiver", current_user);

        List<ParseQuery<ParseObject>> allMatchesQueryList = new ArrayList<>();

        allMatchesQueryList.add(query1);
        allMatchesQueryList.add(query2);

        ParseQuery mainQuery = ParseQuery.or(allMatchesQueryList);

        mainQuery.orderByDescending("createdAt");
        List<ParseObject> objects = null;
        try {
            objects = mainQuery.find();
        } catch (ParseException e) {
            Log.e("Error", e.getMessage());
            e.printStackTrace();
        }

        // create map to store
        Map<String, String> ConnectionsMessage = new HashMap<>();
        Map<String, Date> ConnectionsDate = new HashMap<>();

        int messageSize = objects.size();
        for(int x =0; x < messageSize; x++  ){
            if(objects.get(x) != null){
                String otherUser = objects.get(x).getString("userId");

                if(otherUser!=null){
                    if(otherUser.equals(current_user)){
                        otherUser = objects.get(x).get("Receiver").toString();
                    }
                }
                Date timeStamp = objects.get(x).getCreatedAt();
                String message = objects.get(x).getString("body");
                if(otherUser!=null){
                    if(timeStamp !=null){
                        if(message == null){
                            message = "";
                        }
                        if(ConnectionsMessage.containsKey(otherUser)){
                            Date previousTimeStamp = ConnectionsDate.get(otherUser);
                            if(timeStamp.after(previousTimeStamp)){
                                ConnectionsDate.put(otherUser, timeStamp);
                                ConnectionsMessage.put(otherUser, message);
                            }
                        }
                        else{
                            ConnectionsDate.put(otherUser, timeStamp);
                            ConnectionsMessage.put(otherUser, message);
                        }
                    }
                }
            }
        }
        ValueComparator bvc =  new ValueComparator(ConnectionsDate);
        TreeMap<String,Date> sorted_map = new TreeMap<String,Date>(bvc);
        sorted_map.putAll(ConnectionsDate);

        Iterator<String> keySetIterator1 = sorted_map.keySet().iterator();

        //Log.e("score ", "seperator *********");

        Connections.getInstance().clear();
        while(keySetIterator1.hasNext()){
            String key = keySetIterator1.next();
            Date time = ConnectionsDate.get(key);
            //Timestamp conversion
            Calendar cal = Calendar.getInstance();

            // and get that as a Date
            Date today = cal.getTime();

            //formatting options
            SimpleDateFormat hour =
                    new SimpleDateFormat ("hh:mm a");
            SimpleDateFormat day =
                    new SimpleDateFormat ("MMMM dd, yyyy", Locale.CANADA);

            long dateDiffMS = today.getTime() - time.getTime();
            float dateDiffSecs = dateDiffMS/1000;
            float dateDiffMins = dateDiffSecs/60;
            float dateDiffHrs = dateDiffMins/60;
            float dateDiffDays = dateDiffHrs/24;
            String postTime = null;
            if((dateDiffDays < 1)){

                //post time if not today
                postTime = hour.format(time);
            }
            else{
                postTime = day.format(time);
            }


            //set the connections info here!!!!!!!!!!!!
            Connections.getInstance().displayMessage.add(ConnectionsMessage.get(key));
            Connections.getInstance().myConnections.add(key);
            Connections.getInstance().messageTime.add(postTime);
            //Log.e("score", "data saved " + key + " "+ ConnectionsMessage.get(key) + " " +postTime);

        }

    }

    //call this every time you update an image, so it updates the information
    public void pullParseAndSetOtherUsersDatainBackground() {
        ParseQuery<ParseUser> query = ParseUser.getQuery();
        query.findInBackground(new FindCallback<ParseUser>() {
            public void done(List<ParseUser> objects, ParseException e) {
                if (e == null) {
                    // The query was successful.
                    updateOtherUsersData(objects);
                    //Log.d("score", "updated users info");
                } else {
                    //failed
                }
            }
        });
    }

    public void setUsersImageToParse(String username, final File image){
        ParseQuery<ParseUser> query = ParseUser.getQuery();
        query.whereEqualTo("username", username);

        query.getFirstInBackground(new GetCallback<ParseUser>() {
            public void done(ParseUser UsersClass, ParseException e) {
                if (UsersClass == null) {
                    Log.d("score", "no success setUsersImageToParse");
                } else {
                    //success
                    UsersClass.put("ProfilePic", image);
                    UsersClass.saveInBackground();

                    pullParseAndSetOtherUsersDatainBackground();

                }
            }
        });
    }

    public void updateOtherUsersData(List<ParseUser> objects){
        OtherUsers usersList = OtherUsers.getInstance();
        usersList.clear();
        int userSize = objects.size();
        for(int x =0; x < userSize; x++  ){
            //set users list

            Bitmap picBitmap = null;
            double latitude=0.0;
            double longitude=0.0;

            ParseFile picFile = objects.get(x).getParseFile("ProfilePic");

            //image
            if(picFile == null && defaultProfilePic !=null){
                //add default profile pic if user has no profile pic
                ParseFile picParseFile = Util.convertBitmapToParseFile(defaultProfilePic);
                objects.get(x).put("ProfilePic", picParseFile);
                picBitmap = defaultProfilePic;
            }
            else{
                picBitmap = Util.convertFileToBitmap(picFile);
            }

            //location
            if(objects.get(x).get("Latitude") != null){
                latitude = objects.get(x).getDouble("Latitude");
            }
            if(objects.get(x).get("Longitude") != null){
                longitude = objects.get(x).getDouble("Longitude");
            }

            //channels
            String channels = "";
            if(objects.get(x).get("channels") != null){
                channels = objects.get(x).get("channels").toString();
            }

            String username = objects.get(x).getUsername();

            //add info to users list
            usersList.updateOtherUsersInfo(username, latitude,longitude, channels, picBitmap);

            //save default image
            objects.get(x).saveInBackground();

        }
    }

    class ValueComparator implements Comparator<String> {

        Map<String, Date> base;
        public ValueComparator(Map<String, Date> base) {
            this.base = base;
        }

        // Note: this comparator imposes orderings that are inconsistent with equals.
        public int compare(String a, String b) {
            if (base.get(a).after(base.get(b))) {
                return -1;
            } else {
                return 1;
            } // returning 0 would merge keys
        }
    }

}
