package com.example.cmput401.classdiscuss;

import android.content.Context;
import android.test.ActivityInstrumentationTestCase2;
import android.test.suitebuilder.annotation.SmallTest;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.parse.LogInCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseUser;

/**
 * Created by CMDF_Alien on 4/9/2015.
 */
public class ChatActivityTest extends ActivityInstrumentationTestCase2<ChatActivity> {
    public static final String YOUR_APPLICATION_ID = "OuWwbxVpRVfWh0v3jHEvYeKuuNijBd6M1fVBlkWA";
    public static final String YOUR_CLIENT_KEY = "pYhzGaediLuDVUgQmkuMDkA1DUdKIBtzSziLBdnQ";

    private ChatActivity mChatActivity;
    private ImageView postPicView;
    private EditText etMessage;
    private ImageButton btPicAdd;
    private Profiles profiles;
    private Context context;

    public ChatActivityTest(){
        super(ChatActivity.class);
    }



    @Override
    protected void setUp() throws Exception {
        super.setUp();
        context = this.getInstrumentation().getTargetContext().getApplicationContext();
        ParseObject.registerSubclass(Message.class);
        Parse.initialize(context, YOUR_APPLICATION_ID, YOUR_CLIENT_KEY);
        profiles = Profiles.getInstance();
        profiles.loginEmail = "cfudge@ualberta.ca";
        login();
        profiles.displayProfile = new Profile();
        profiles.displayProfile.getParseEntry("username", "sally");

        setActivityInitialTouchMode(true);

        mChatActivity = getActivity();
        postPicView = (ImageView) mChatActivity.findViewById(R.id.postPicView);
        etMessage = (EditText) mChatActivity.findViewById(R.id.etMessage);
        btPicAdd = (ImageButton) mChatActivity.findViewById(R.id.btPicAdd);


        testPreconditions();
    }

    public void testPreconditions() {
        assertNotNull("mChatActivity is null", mChatActivity);
    }

    @SmallTest
    public void testPostPicView_invisible(){
        assertEquals("postPicView is visible without post pic added", postPicView.getVisibility(), ImageView.INVISIBLE);
    }

    public void testetMessage_enterText() {
        getInstrumentation().runOnMainSync(new Runnable() {
            @Override
            public void run() {
                etMessage.requestFocus();
            }
        });

        getInstrumentation().waitForIdleSync();
        getInstrumentation().sendStringSync("Test");
        getInstrumentation().waitForIdleSync();

        String messageBody = etMessage.getText().toString();
        assertEquals("input text does not match displayed value", messageBody, "Test");
    }

    public void testbtPicAdd_pushButton(){
        
    }


    // Create an anonymous user using ParseAnonymousUtils and set sUserId
    private void login(){
        Profiles profiles = Profiles.getInstance();
        String loginUser = profiles.loginEmail.replace("@ualberta.ca", "");
        ParseUser.logInInBackground(loginUser, loginUser, new LogInCallback() {
            public void done(ParseUser user, ParseException e) {
            }
        });
    }
}
