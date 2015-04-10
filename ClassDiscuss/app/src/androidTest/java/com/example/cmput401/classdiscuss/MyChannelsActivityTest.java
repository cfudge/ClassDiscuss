package com.example.cmput401.classdiscuss;

import android.app.Instrumentation;
import android.content.Intent;
import android.test.ActivityInstrumentationTestCase2;
import android.test.TouchUtils;
import android.test.ViewAsserts;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;

/**
 * Created by Nancy on 4/9/2015.
 */
public class MyChannelsActivityTest extends ActivityInstrumentationTestCase2<MyChannelsActivity>{

    private Button addButton;
    private MyChannelsActivity myChannelsActivity;

    public MyChannelsActivityTest(){
        super (MyChannelsActivity.class);

    }

    public void setUp() throws Exception{

        setActivityInitialTouchMode(true);

        Intent intent = new Intent();
        intent.setClassName("com.example.cmput401.classdiscuss", "com.example.cmput401.classdiscuss.ChannelsAddActivity");
        setActivityIntent(intent);

        myChannelsActivity = getActivity();
        addButton = (Button) myChannelsActivity.findViewById(R.id.add_new_channel);

    }

    public void testAddButton(){

       assertEquals("Button label is incorrect", "Add", addButton.getText());
    }

    public void testIntentOnClick(){
        assertNotNull("Button cannot be null", addButton);


    }

    /*public void testAddButtonLayout(){
        final View decorView = myChannelsActivity.getWindow().getDecorView();

        ViewAsserts.assertOnScreen(decorView, addButton);

        final ViewGroup.LayoutParams layoutParams = addButton.getLayoutParams();
        assertNotNull(layoutParams);
        assertEquals(layoutParams.width, 90);
        assertEquals(layoutParams.height, 90);
    }*/
}
