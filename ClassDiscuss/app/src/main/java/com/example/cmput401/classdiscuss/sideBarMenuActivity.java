package com.example.cmput401.classdiscuss;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.MenuItem;

import com.parse.ParseUser;

/**
 * <p>
 * This class contains the implementation of side bar menu button.
 * </p>
 *
 * <p>
 * This button can be used to navigate through different activities within the app.
 * Since this class extends Activity, all activities that need to have the button
 * will extend this one, so that they will all have button and behave as activities.
 * </p>
 *
 * copyright 2015 Nhu Bui, Nancy Pham-Nguyen, Valerie Sawyer, Cole Fudge, Kelsey Wicentowich
 */
public class sideBarMenuActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch (item.getItemId()) {
            case R.id.action_profile:
                final ParseUser user = ParseUser.getCurrentUser();
                Profiles profiles = Profiles.getInstance();
                if (user != null){
                    Profile displayProfile = new Profile();
                    displayProfile.getParseEntry("email", profiles.loginEmail);
                    profiles.displayProfile = displayProfile;
                }
                else{
                    profiles.displayProfile = null;
                }
                getApplicationContext().startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
                break;
            case R.id.action_myChannels:
                getApplicationContext().startActivity(new Intent(getApplicationContext(), MyChannelsActivity.class));
                break;
            case R.id.action_myConnections:
                Intent activity = new Intent();
                activity.setClass(getApplicationContext(), ConnectionsActivity.class);
                startActivity(activity);
                break;
            /*case R.id.action_edit_profile:
                Intent edit = new Intent();
                edit.setClass(getApplicationContext(), ProfileEditActivity.class);
                startActivity(edit);
                break;*/
            case R.id.action_settings:
            case R.id.action_chat:
                Profiles.getInstance().displayProfile = new Profile();
                Profiles.getInstance().displayProfile.setParseEntry(ParseUser.getCurrentUser());
                Intent chatIntent = new Intent();
                chatIntent.setClass(getApplicationContext(), ChatActivity.class);
                startActivity(chatIntent);
                break;
            case R.id.action_users:
                Intent usersIntent = new Intent();
                usersIntent.setClass(getApplicationContext(), UserActivity.class);
                startActivity(usersIntent);
                break;
            case R.id.action_cancel_editProfile:
                Intent ProfileActivity = new Intent();
                ProfileActivity.setClass(getApplicationContext(), ProfileActivity.class);
                startActivity(ProfileActivity);
                break;
            // Clearly doesn't work right now.  the login activity has saved your
            // email address so if you log out it automatically signs you back in.
            case R.id.action_logout:
                MainActivity out = new MainActivity();
                out.signOutFromGplus();
                Intent login = new Intent();
                login.setClass(getApplicationContext(), MainActivity.class);
                startActivity(login);
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return true;
    }
}
