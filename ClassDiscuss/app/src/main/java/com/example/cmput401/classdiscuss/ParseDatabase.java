package com.example.cmput401.classdiscuss;

import android.app.Activity;
import android.graphics.Bitmap;
import android.util.Log;

import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.io.File;
import java.util.Collections;
import java.util.List;

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

    public void Initiate() {
        initiateChannels();
        setUsersDataLocally();
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

    //call this every time you update an image, so it updates the information
    public void setUsersDataLocally() {
        ParseQuery<ParseUser> query = ParseUser.getQuery();
        query.findInBackground(new FindCallback<ParseUser>() {
            public void done(List<ParseUser> objects, ParseException e) {
                    if (e == null) {
                        // The query was successful.
                        OtherUsers usersList = OtherUsers.getInstance();
                        usersList.clear();
                        int userSize = objects.size();
                        for(int x =0; x < userSize; x++  ){
                            //set users list
                            //image
                            ParseFile picFile = objects.get(x).getParseFile("ProfilePic");
                            Bitmap picBitmap = null;
                            if(picFile == null){
                                picBitmap = defaultProfilePic;
                            }
                            else{
                                picBitmap = Util.convertFileToBitmap(picFile);
                            }

                            //location
                            String latitude="";
                            String longitude="";
                            if(objects.get(x).get("Latitude") != null){
                                int intlatitude = objects.get(x).getInt("Latitude");
                                latitude = Integer.toString(intlatitude);
                            }
                            if(objects.get(x).get("Longitude") != null){
                                int intLatitude = objects.get(x).getInt("Longitude");
                                longitude = Integer.toString(intLatitude);
                            }

                            //channels
                            String channels = "";
                            if(objects.get(x).get("channels") != null){
                                channels = objects.get(x).get("channels").toString();
                            }

                            String username = objects.get(x).getUsername();

                            //add info to users list
                            usersList.UpdateUserLocationInfo(username, longitude, latitude, channels);
                            usersList.UpdateUserImageInfo(username, picBitmap);

                            //this will be added to users list next time.
                            if(picBitmap == null && defaultProfilePic !=null){
                                //add default profile pic if user has no profile pic
                                ParseFile picParseFile = Util.convertBitmapToParseFile(defaultProfilePic);
                                objects.get(x).put("ProfilePic", picParseFile);
                                objects.get(x).saveInBackground();
                            }
                        }
                        Log.d("score", "updated users info");
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

                    setUsersDataLocally();

                }
            }
        });
    }

}
