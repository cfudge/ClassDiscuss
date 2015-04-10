package com.example.cmput401.classdiscuss;

import android.content.Context;
import android.content.Intent;
import android.test.ActivityInstrumentationTestCase2;
import android.test.ViewAsserts;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

/**
 * Created by Nancy on 4/10/2015.
 */
public class ConnectionsActivityTest extends ActivityInstrumentationTestCase2<ConnectionsActivity> {

    private TextView textView;
    private ConnectionsActivity connectionsActivity;
    private ListView listView;

    private Connections connections;
    private Context context;
    private ConnectionsAdapter connectionsAdapter;


    public  ConnectionsActivityTest(){
        super(ConnectionsActivity.class);
    }

    public void setUp() throws Exception{

        setActivityInitialTouchMode(true);

        //http://stackoverflow.com/questions/4320099/how-do-i-write-an-android-junit-test-when-my-activity-relies-on-extras-passed-th
        Intent intent = new Intent();
        intent.setClassName("com.example.cmput401.classdiscuss", "com.example.cmput401.classdiscuss.ChatActivity");
        setActivityIntent(intent);


        connections = connections.getInstance();
        context = this.getInstrumentation().getTargetContext().getApplicationContext();
        connectionsActivity = getActivity();

        textView= (TextView) connectionsActivity.findViewById(R.id.no_messages);
        listView = (ListView) connectionsActivity.findViewById(R.id.channel_list_view);

    }

    public void testTextViewLayout(){
        final View decorView = connectionsActivity.getWindow().getDecorView();

        ViewAsserts.assertOnScreen(decorView, textView);

        final ViewGroup.LayoutParams layoutParams = textView.getLayoutParams();
        assertNotNull(layoutParams);
        assertEquals(layoutParams.width, layoutParams.WRAP_CONTENT);
        assertEquals(layoutParams.height, layoutParams.WRAP_CONTENT);
    }

    public void testListView(){

        assertNotNull("There is no list loaded", listView);
    }


    public void testText(){
        assertEquals("The texts do not match", connections.myConnections.size() + " Connections" , textView.getText().toString());
    }

    public void testAdapter(){
        connectionsAdapter = (ConnectionsAdapter) listView.getAdapter();
        String test = connectionsAdapter.getItem(0);
        String test2 = connectionsAdapter.getItem(1);
       
        assertEquals("There are no connections in the list", test, connections.myConnections.get(0));
        assertEquals("Connection doesn't exist", test2, connections.myConnections.get(1));

    }

}
