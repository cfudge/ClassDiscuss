package com.example.cmput401.classdiscuss;

import android.app.ActionBar;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/*
 * copyright 2015 Nhu Bui, Nancy Pham-Nguyen, Valerie Sawyer, Cole Fudge, Kelsey Wicentowich
 */
public class ConnectionsActivity extends ActionBarActivity {

    ListView listView;
    private Connections myConnections;
    ConnectionsAdapter connectionsAdapter;


    ArrayList<Integer> messageTimes = new ArrayList<Integer>();

    long time =  System.currentTimeMillis();
    Timestamp timeStamp =  new Timestamp(time);
    String tStamp = timeStamp.toString();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connections_list);



        myConnections = myConnections.getInstance();
        connectionsAdapter = new ConnectionsAdapter(this, myConnections.myConnections, myConnections.displayMessage, ConnectionsActivity.this);

        listView = (ListView) findViewById(R.id.channel_list_view);
        listView.setAdapter(connectionsAdapter);


        //http://stackoverflow.com/questions/28081709/null-pointer-exception-at-actionbar
        android.support.v7.app.ActionBar actionBar =  getSupportActionBar();
        ColorDrawable colorDraw = new ColorDrawable(Color.parseColor("#6CCECB"));
        actionBar.setBackgroundDrawable(colorDraw);
    }

    @Override
    protected void onStart(){
        super.onStart();
        addConnections();
    }

    @Override
    protected void onResume(){
        super.onResume();
        connectionsAdapter.notifyDataSetChanged();
    }

    public void addConnections(){

       /* messageTimes.add(timeStamp.getHours());
        messageTimes.add(timeStamp.getMinutes());
        messageTimes.add(timeStamp.getSeconds());
        messageTimes.add(30);
        messageTimes.add(60);
        messageTimes.add(1);*/


        if(myConnections.myConnections.isEmpty()) {
            TextView noMessage = (TextView) findViewById(R.id.no_messages);
            noMessage.setText("NO MESSAGES");
        }
        if(myConnections.displayMessage.isEmpty()){
            TextView noMessage = (TextView) findViewById(R.id.no_messages);
            noMessage.setText("NO MESSAGES");
        }

        sortList();
        TextView noMessage = (TextView) findViewById(R.id.no_messages);
        noMessage.setText(myConnections.myConnections.size() + " Connections");

    }


    public void sortList(){
        Collections.sort(messageTimes, new Comparator<Integer>() {
            @Override
            public int compare(Integer lhs, Integer rhs) {
                return lhs.compareTo(rhs);
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_connections, menu);
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
