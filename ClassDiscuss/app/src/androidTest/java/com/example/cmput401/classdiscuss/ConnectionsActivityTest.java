package com.example.cmput401.classdiscuss;

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


    public  ConnectionsActivityTest(){
        super(ConnectionsActivity.class);
    }

    public void setUp() throws Exception{

        setActivityInitialTouchMode(true);

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





}
