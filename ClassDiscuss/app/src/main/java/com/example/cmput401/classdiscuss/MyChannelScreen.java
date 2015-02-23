package com.example.cmput401.classdiscuss;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.plus.Plus;


public class MyChannelScreen extends ActionBarActivity {

    ListView listView;
    private Singleton singleton;
   // public ArrayAdapter<String> channelAdapter;
    CustomAdapter channelAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_channel_screen);

        singleton = Singleton.getInstance();
        channelAdapter = new CustomAdapter(this, singleton.subscribedChannelList, MyChannelScreen.this);

        listView = (ListView) findViewById(R.id.listView);
        listView.setAdapter(channelAdapter);
        addChannels();

    }

    @Override
    protected void onStart(){
        super.onStart();


    }

    @Override
    protected void onResume(){
        super.onResume();
        channelAdapter.notifyDataSetChanged();
    }

    public void addChannels(){
        if(singleton.subscribedChannelList.isEmpty()) {
            String campusSocial = "CampusSocial";
            String class1 = "CMPUT 101";
            singleton.subscribedChannelList.add(0, class1);
            singleton.subscribedChannelList.add(1, campusSocial);
            channelAdapter.notifyDataSetChanged();
        }
        Button addButton = (Button) findViewById(R.id.add_new_channel);



        addButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                Intent addChannels = new Intent();
                addChannels.setClass(getApplicationContext(), AddChannelScreen.class);
                startActivity(addChannels);
            }
        });
    }



    public void channelSelected(){

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_channel_screen, menu);
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
        if (id == R.id.action_logout) {
           // MainActivity.isLoggedIn = false;
            MainActivity.loggedIn = false;
           // MainActivity.mGoogleApiClient.connect();
           // if (MainActivity.mGoogleApiClient.isConnected()) {
                //Toast.makeText(this, "In channel screen!", Toast.LENGTH_LONG).show();
               /* Plus.AccountApi.clearDefaultAccount(MainActivity.mGoogleApiClient);
                Plus.AccountApi.revokeAccessAndDisconnect(MainActivity.mGoogleApiClient)
                        .setResultCallback(new ResultCallback<Status>() {
                            @Override
                            public void onResult(Status arg0) {
                                Log.e("MyChannelScreen", "User access revoked!");
                              //  MainActivity.mGoogleApiClient.connect();

                            }

                        });
            }
                MainActivity.loggedIn = true;*/
                Intent ToChannelScreen = new Intent();
                ToChannelScreen.setClass(getApplicationContext(), MainActivity.class);
                startActivity(ToChannelScreen);


            return true;
        }


        return super.onOptionsItemSelected(item);
    }
}
