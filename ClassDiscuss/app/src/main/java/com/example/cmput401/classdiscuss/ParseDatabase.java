package com.example.cmput401.classdiscuss;

import android.app.Activity;
import android.util.Log;

import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/*
 * copyright 2015 Nhu Bui, Nancy Pham-Nguyen, Valerie Sawyer, Cole Fudge, Kelsey Wicentowich
 */
public class ParseDatabase extends Activity {
    private static final ParseDatabase parseInstance = new ParseDatabase();
    String ChannelsClass= "Channels";//classes in class
    String ImageClass= "Image";//classes in class
    String UserClass = "_User";//classes in class
    String myChannelObjectId;
    List channelList;

    private ParseDatabase() {
        this.myChannelObjectId = ""; //this is for the main user
        this.channelList = Collections.emptyList();
    }

    public static ParseDatabase getInstance() {
        return parseInstance;
    }

    public void Initiate() {
        createNewChannelsToParse();
        setDataLocally();
    }

    private void createNewChannelsToParse() {

            //get the object id now and save it for future use
            ParseQuery<ParseObject> query = ParseQuery.getQuery(ChannelsClass);
            query.whereEqualTo("userID", Profile.getInstance().getEmail());

            //check if userID already existed in database
            query.getFirstInBackground(new GetCallback<ParseObject>() {
                public void done(ParseObject object, ParseException e) {
                    if (object == null) {
                        //make a new object for the user in the database
                        ParseObject Channels = new ParseObject(ChannelsClass);
                        Channels.put("channels", Arrays.asList());
                        Channels.put("userID", Profile.getInstance().getEmail());
                        //get the object id now and save it for future uses
                        setMyChannelObjectId(Channels.getObjectId());

                        Channels.saveInBackground();
                    } else {
                        //already exists
                        //get the object id now and save it for future uses
                        setMyChannelObjectId(object.getObjectId());

                        //set the subscribed channels list locally
                        List channelList = object.getList("channels");
                        MyChannels subscriptionList = MyChannels.getInstance();
                        for (int x = 0; x < channelList.size(); x++) {
                            if (!subscriptionList.ifContains(channelList.get(x).toString())) {
                                subscriptionList.addChannel(channelList.get(x).toString());
                            }

                        }
                        Log.d("score", "subscription list updated");
                    }
                }
            });
    }

    /*private void createNewProfileToParse() {
        ParseQuery<ParseObject> query = ParseQuery.getQuery(ImageClass);
        query.whereEqualTo("username", Profile.getInstance().getUserName());

        //check if userID already existed in database
        query.getFirstInBackground(new GetCallback<ParseObject>() {
            public void done(ParseObject object, ParseException e) {
                if (object == null) {
                    //make a new object for the user in the database
                    ParseObject Channels = new ParseObject(ImageClass);
                    Channels.put("Image", "");
                    Channels.put("username", Profile.getInstance().getUserName());
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

    public void addChannelsToParse(final String item) {
        ParseQuery<ParseObject> query = ParseQuery.getQuery(ChannelsClass);

        // Retrieve the object by id
        query.getInBackground(getMyChannelObjectId(), new GetCallback<ParseObject>() {
            public void done(ParseObject ParseChannels, ParseException e) {
                if (e == null) {

                    ParseChannels.addUnique("channels", item);
                    ParseChannels.saveInBackground();
                }
            }
        });
    }

    public void deleteChannelFromParse() {
        ParseQuery<ParseObject> query = ParseQuery.getQuery(ChannelsClass);
        // Retrieve the object by id
        query.getInBackground(getMyChannelObjectId(), new GetCallback<ParseObject>() {
            public void done(ParseObject ParseChannels, ParseException e) {
                if (e == null) {
                    MyChannels list = MyChannels.getInstance();
                    //instead of deleting item of an array, we are just gonna update the
                    //array
                    ParseChannels.put("channels", list.getSubscribedList());
                    ParseChannels.saveInBackground();
                }
            }
        });
    }

    //call this every time you update an image, so it updates the information
    public void setDataLocally() {
        ParseQuery<ParseUser> query = ParseUser.getQuery();
        query.findInBackground(new FindCallback<ParseUser>() {
            public void done(List<ParseUser> objects, ParseException e) {
                    if (e == null) {
                        // The query was successful.
                        Users usersList = Users.getInstance();
                        usersList.clearUsersList();
                        int userSize = objects.size();
                        for(int x =0; x < userSize; x++  ){
                            //set users list
                            usersList.addNewUser(objects.get(x).get("username").toString());

                            //images purposes
                            String username = objects.get(x).get("username").toString();
                            String usersImage = objects.get(x).getString("Image");
                            Profile.getInstance().addToUserAndImagesTable(username, usersImage);
                        }
                    } else {
                        //failed
                    }
            }
        });
    }

    public void setUsersImageToParse(String username, final String image){
        ParseQuery<ParseUser> query = ParseUser.getQuery();
        query.whereEqualTo("username", username);

        query.getFirstInBackground(new GetCallback<ParseUser>() {
            public void done(ParseUser UsersClass, ParseException e) {
                if (UsersClass == null) {
                    Log.d("score", "no success setUsersImageToParse");
                } else {
                    //success
                    UsersClass.put("Image", image);
                    UsersClass.saveInBackground();

                    setDataLocally();

                }
            }
        });
    }

}
