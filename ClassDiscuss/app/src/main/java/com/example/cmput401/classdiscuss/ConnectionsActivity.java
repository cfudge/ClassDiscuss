package com.example.cmput401.classdiscuss;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
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

    Users users =  Users.getInstance();
    PopupWindow popup;
    PopupListAdapter popupAdapter;
    ListView popupList;


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

        popupMenu();

    }

    @Override
    protected void onStart(){
        super.onStart();
        addConnections();
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

    }


    public void sortList(){
        Collections.sort(messageTimes, new Comparator<Integer>() {
            @Override
            public int compare(Integer lhs, Integer rhs) {
                return lhs.compareTo(rhs);
            }
        });
        TextView noMessage = (TextView) findViewById(R.id.no_messages);
        noMessage.setText(messageTimes.toString());
    }

    //http://stackoverflow.com/questions/21329132/android-custom-dropdown-popup-menu
    //http://android-er.blogspot.ca/2012/03/example-of-using-popupwindow.html
    public void popupMenu(){
        //Button to test popup menu

        popupAdapter = new PopupListAdapter(this, users.getUsersList());

        final Button popupButton = (Button) findViewById(R.id.popup_button);
        popupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                LayoutInflater inflater = (LayoutInflater) getBaseContext().getSystemService(LAYOUT_INFLATER_SERVICE);
                View view = inflater.inflate(R.layout.message_popup, null);
                popup = new PopupWindow(view, LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, true);

                popupList = (ListView) view.findViewById(R.id.popup_list_view);
                popupList.setAdapter(popupAdapter);

                popup.showAtLocation(view, Gravity.CENTER, 0,0);

                ImageButton closeButton = (ImageButton) view.findViewById(R.id.close_button);
                closeButton.setOnClickListener(new ImageButton.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        popup.dismiss();
                    }
                });

                final EditText enterMessage = (EditText) view.findViewById(R.id.enterMessage);

                //Send button
                Button sendButton = (Button) view.findViewById(R.id.sendButton);
                sendButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                       // myConnections.displayMessage.add(enterMessage.getText().toString());

                        Toast.makeText(ConnectionsActivity.this, enterMessage.getText().toString(),
                                Toast.LENGTH_SHORT).show();
                    }
                });
           }
        });
    }

    //http://developer.android.com/guide/topics/ui/controls/checkbox.html
   public void onCheckBoxClicked(View view){
        CheckBox checkBox = (CheckBox) view.findViewById(R.id.checkBox);
        boolean checked = checkBox.isChecked();

        switch (view.getId()){
            case R.id.checkBox:
                if(checked){
                    Toast.makeText(this, "Checked",
                            Toast.LENGTH_SHORT).show();
                }

        }
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
