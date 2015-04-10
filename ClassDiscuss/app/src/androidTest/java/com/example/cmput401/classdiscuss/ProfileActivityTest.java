package com.example.cmput401.classdiscuss;

import android.content.Context;
        import android.test.ActivityInstrumentationTestCase2;
        import android.test.suitebuilder.annotation.SmallTest;
        import android.widget.EditText;
        import android.widget.ImageButton;
        import android.widget.ImageView;
import android.widget.TextView;

import com.parse.LogInCallback;
        import com.parse.Parse;
        import com.parse.ParseException;
        import com.parse.ParseObject;
        import com.parse.ParseUser;

/**
 * Created by Valerie on 2015-04-09.
 */
public class ProfileActivityTest extends ActivityInstrumentationTestCase2<ProfileActivity> {
    public static final String YOUR_APPLICATION_ID = "OuWwbxVpRVfWh0v3jHEvYeKuuNijBd6M1fVBlkWA";
    public static final String YOUR_CLIENT_KEY = "pYhzGaediLuDVUgQmkuMDkA1DUdKIBtzSziLBdnQ";

    private Context context;
    private Profiles profiles;
    private ProfileActivity mProfileActivity;
    public ProfileActivityTest(){
        super(ProfileActivity.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        context = this.getInstrumentation().getTargetContext().getApplicationContext();
        ParseObject.registerSubclass(Message.class);
        Parse.initialize(context, YOUR_APPLICATION_ID, YOUR_CLIENT_KEY);
        profiles = Profiles.getInstance();
        profiles.loginEmail = "test@ualberta.ca";
        login();
        profiles.displayProfile = new Profile();
        profiles.displayProfile.getParseEntry("username", "Nancy");

        setActivityInitialTouchMode(true);

        mProfileActivity = getActivity();


        testPreconditions();
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
    public void testPreconditions() {
        assertNotNull("mProfileActivity is null", mProfileActivity);
    }

    public void testUsernameAndEmail() {
        TextView textUserName = (TextView) mProfileActivity.findViewById(R.id.textUserName);
        TextView textUserEmail = (TextView) mProfileActivity.findViewById(R.id.textUserEmail);
        String name = textUserName.getText().toString();
        String email = textUserEmail.getText().toString();
        assertEquals(name, "Nancy");
        assertEquals(email, "test@ualberta.ca");
    }
}
