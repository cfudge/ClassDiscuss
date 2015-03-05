package com.example.cmput401.classdiscuss;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;


public class ChannelsAddActivity extends ActionBarActivity {

    ListView addChannelListView;
    ChannelsAddAdapter channelAdapter;
    Channels channels;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_channel_screen);
        channels = Channels.getInstance();
        channelAdapter = new ChannelsAddAdapter(this, channels.availableChannelList, ChannelsAddActivity.this);

        addChannelListView = (ListView) findViewById(R.id.add_channel_list_view);
        addChannelListView.setAdapter(channelAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_add_channel_screen, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
