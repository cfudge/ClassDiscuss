package com.example.cmput401.classdiscuss;

import android.test.ActivityInstrumentationTestCase2;
import android.test.suitebuilder.annotation.SmallTest;
import android.widget.ImageView;

/**
 * Created by CMDF_Alien on 4/9/2015.
 */
public class ChatActivityTest extends ActivityInstrumentationTestCase2<ChatActivity> {

    private ChatActivity mChatActivity;
    private MainActivity testMain;
    private ImageView postPicView;
    private Profiles profiles;

    public ChatActivityTest(){
        super(ChatActivity.class);
    }



    @Override
    protected void setUp() throws Exception {
        super.setUp();
        profiles = Profiles.getInstance();
        profiles.displayProfile = new Profile();
        profiles.loginEmail = "test@ualberta.ca";
        testMain = new MainActivity();
        testMain.initiateParse();
        testMain.connectToParse();
        profiles.displayProfile.getParseEntry("username", "test");
        setActivityInitialTouchMode(true);
        mChatActivity = getActivity();
        postPicView = (ImageView) mChatActivity.findViewById(R.id.postPicView);

        testPreconditions();
    }

    public void testPreconditions() {
        assertNotNull("mChatActivity is null", mChatActivity);
    }

    @SmallTest
    public void testPostPicView_visibility(){
        assertEquals("postPicView is visible without post pic added", postPicView.getVisibility(), ImageView.INVISIBLE);

    }

}
