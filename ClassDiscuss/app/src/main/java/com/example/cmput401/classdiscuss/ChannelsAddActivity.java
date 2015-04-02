package com.example.cmput401.classdiscuss;

import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.view.Menu;
import android.widget.ListView;
import android.widget.SearchView;

import java.util.ArrayList;

/*
 * copyright 2015 Nhu Bui, Nancy Pham-Nguyen, Valerie Sawyer, Cole Fudge, Kelsey Wicentowich
 */
public class ChannelsAddActivity extends sideBarMenuActivity {

    ListView addChannelListView;
    ChannelsAddAdapter channelAdapter;
    Channels channels;

    ArrayList<String> searchedChannels;
    SearchView searchView;

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

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        //Current activity is searchable
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setIconifiedByDefault(true); //Iconify search bar by default

        search();
        return true;
    }

    public void search(){
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
               return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if(newText.isEmpty()){
                    addChannelListView.clearTextFilter();
                    return true;
                }
                else {

                    addChannelListView.setFilterText(newText);
                    addChannelListView.setTextFilterEnabled(true);
                    addChannelListView.setAdapter(channelAdapter);
                    return false;
                }
            }
        });
    }


}
