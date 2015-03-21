package com.example.cmput401.classdiscuss;

import android.app.Activity;
import android.util.Log;

import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/*
 * copyright 2015 Nhu Bui, Nancy Pham-Nguyen, Valerie Sawyer, Cole Fudge, Kelsey Wicentowich
 */
public class Parse extends Activity {
    private static final Parse parseInstance = new Parse();
    String ChannelsClass;
    String ObjectId;
    List channelList;
    boolean initializedDone;

    private Parse() {
        this.ChannelsClass = "Channels";
        this.ObjectId = "";
        this.channelList = Collections.emptyList();

    }

    public static Parse getInstance() {
        return parseInstance;
    }

    public void Initiate() {

        if (this.ObjectId.equals("")) {

            //get the object id now and save it for future use
            ParseQuery<ParseObject> query = ParseQuery.getQuery(this.ChannelsClass);
            query.whereEqualTo("userID", Profile.getInstance().getUserEmail());

            //check if userID already existed in database
            query.getFirstInBackground(new GetCallback<ParseObject>() {
                public void done(ParseObject object, ParseException e) {
                    if (object == null) {
                        createNewChannels();
                    } else { //success

                        //get the object id now and save it for future uses
                        setObjectId(object.getObjectId());

                        //set the subscribed channels list
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
    }

    private void createNewChannels() {
        //make a new object for the user in the database
        ParseObject Channels = new ParseObject(this.ChannelsClass);
        Channels.put("channels", Arrays.asList());
        Channels.put("userID", Profile.getInstance().getUserEmail());
        Channels.saveInBackground();

        //now object is created, we initiate again to get the objectID
        Initiate();
    }

    public String getObjectId() {
        return this.ObjectId;
    }

    public void setObjectId(String ID) {
        this.ObjectId = ID;
    }

    public void addChannels(final String item) {
        ParseQuery<ParseObject> query = ParseQuery.getQuery(this.ChannelsClass);

        // Retrieve the object by id
        query.getInBackground(getObjectId(), new GetCallback<ParseObject>() {
            public void done(ParseObject ParseChannels, ParseException e) {
                if (e == null) {

                    ParseChannels.addUnique("channels", item);
                    ParseChannels.saveInBackground();
                }
            }
        });


    }

    public void deleteChannel() {

        ParseQuery<ParseObject> query = ParseQuery.getQuery(this.ChannelsClass);
        Log.d("score", "did it go here- deleteChannel");
        // Retrieve the object by id
        query.getInBackground(getObjectId(), new GetCallback<ParseObject>() {
            public void done(ParseObject ParseChannels, ParseException e) {
                if (e == null) {
                    MyChannels list = MyChannels.getInstance();
                    //instead of deleting item of an array, we are just gonna update the
                    //array
                    //Log.d("score", "stuff in list from deletechannels"+  list.getSubscribedList());
                    ParseChannels.put("channels", list.getSubscribedList());
                    ParseChannels.saveInBackground();
                }
            }
        });
    }



}
