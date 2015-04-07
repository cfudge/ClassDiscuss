package com.example.cmput401.classdiscuss;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
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
        //topbar color
        android.support.v7.app.ActionBar actionBar =  getSupportActionBar();
        ColorDrawable colorDraw = new ColorDrawable(Color.parseColor("#9FBF8C"));
        actionBar.setBackgroundDrawable(colorDraw);
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
                Intent myProfile = new Intent();
                myProfile.setClass(getApplicationContext(), ProfileActivity.class);
                startActivity(myProfile);
                break;
            case R.id.action_myChannels:
                Intent channel = new Intent();
                channel.setClass(getApplicationContext(), MyChannelsActivity.class);
                startActivity(channel);
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
            case R.id.action_logout:
                //MainActivity out = new MainActivity();
               // out.signOutFromGplus();
                Intent login = new Intent();
                login.setClass(getApplicationContext(), MainActivity.class);
                startActivity(login);
                break;
            case R.id.action_chat:
                Intent connection = new Intent();
                connection.setClass(getApplicationContext(), ConnectionsActivity.class);
                startActivity(connection);
            default:
                return super.onOptionsItemSelected(item);
        }
        return true;
    }
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        Notice notice = Notice.getInstance();
        if (!notice.isIconThere())
        //    Log.d("here", "yes");
         //   MenuItem chat = menu.findItem(R.id.action_chat);
           // chat.isVisible();
        //}
        //else {
           menu.removeItem(R.id.action_chat);
       // }
        return true;
    }

}
