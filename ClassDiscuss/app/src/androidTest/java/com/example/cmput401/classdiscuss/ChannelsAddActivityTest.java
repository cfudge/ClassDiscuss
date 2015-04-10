package com.example.cmput401.classdiscuss;

import android.test.ActivityInstrumentationTestCase2;
import android.widget.Button;
import android.widget.ImageButton;

/**
 * Created by Nancy on 4/10/2015.
 */
public class ChannelsAddActivityTest extends ActivityInstrumentationTestCase2<ChannelsAddActivity> {


    private ChannelsAddActivity channelsAddActivity;

    public ChannelsAddActivityTest(){
        super (ChannelsAddActivity.class);

    }

    public void setUp() throws Exception{

        setActivityInitialTouchMode(true);

        channelsAddActivity = getActivity();

    }
}
