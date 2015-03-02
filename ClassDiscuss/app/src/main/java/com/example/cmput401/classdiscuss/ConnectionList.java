package com.example.cmput401.classdiscuss;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;


public class ConnectionList extends ActionBarActivity {

    ListView listView;
    private Singleton singleton;
    ConnectionsAdapter connectionsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connections_list);


        singleton = Singleton.getInstance();
        connectionsAdapter = new ConnectionsAdapter(this, singleton.myConnections, singleton.displayMessage, ConnectionList.this);

        listView = (ListView) findViewById(R.id.channel_list_view);
        listView.setAdapter(connectionsAdapter);

    }

    @Override
    protected void onStart(){
        super.onStart();
        addConnections();
    }

    public void addConnections(){
        singleton.myConnections.add("John");
        singleton.myConnections.add("Joe");
        singleton.displayMessage.add("Hey, How are you?");
        singleton.displayMessage.add("Let's meet up to study");
        connectionsAdapter.notifyDataSetChanged();
        if(singleton.myConnections.isEmpty()) {
            TextView noMessage = (TextView) findViewById(R.id.no_messages);
            noMessage.setText("NO MESSAGES");
        }
        if(singleton.displayMessage.isEmpty()){
            TextView noMessage = (TextView) findViewById(R.id.no_messages);
            noMessage.setText("NO MESSAGES");
        }
    }

    public void sortList(){

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_connections_list, menu);
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
