package com.example.cmput401.classdiscuss;

import android.content.Context;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;

/**
 * Created by Nancy on 4/10/2015.
 */
public class ChannelsAddActivityTest extends ActivityInstrumentationTestCase2<ChannelsAddActivity> {

    private ListView listView;
    private ChannelsAddActivity channelsAddActivity;
    private Channels channels;
    private ChannelsAddAdapter channelsAddAdapter;
    private Context context;

    public ChannelsAddActivityTest(){
        super (ChannelsAddActivity.class);
    }

    public void setUp() throws Exception{

        setActivityInitialTouchMode(true);

        channels = channels.getInstance();

        context = this.getInstrumentation().getTargetContext().getApplicationContext();

        channelsAddActivity = getActivity();
        listView = (ListView) channelsAddActivity.findViewById(R.id.add_channel_list_view);

    }

    public void testListView(){

        assertNotNull("There is no list loaded", listView);
    }

    public void testAdapter(){
        channelsAddAdapter = (ChannelsAddAdapter) listView.getAdapter();
        String test = channelsAddAdapter.getItem(0);
        String test2 = channelsAddAdapter.getItem(1);
        assertEquals("There are no channels in the list", test, channels.availableChannelList.get(0));
        assertEquals("Channel doesn't exist", test2, channels.availableChannelList.get(1));
    }
}
