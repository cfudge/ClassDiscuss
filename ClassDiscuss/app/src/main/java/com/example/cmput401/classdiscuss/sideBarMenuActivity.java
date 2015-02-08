package com.example.cmput401.classdiscuss;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

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
 * @author Nhu Bui (nbui)
 *
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
                Intent myProfile = new Intent();
                myProfile.setClass(getApplicationContext(), ProfileActivity.class);
                startActivity(myProfile);
                break;
            case R.id.action_myChannels:
                Intent myChannels = new Intent();
                myChannels.setClass(getApplicationContext(), MyChannelScreen.class);
                startActivity(myChannels);
                break;
            case R.id.action_edit_profile:
                Intent edit = new Intent();
                edit.setClass(getApplicationContext(), EditProfileActivity.class);
                startActivity(edit);
                break;
            case R.id.action_settings:
            case R.id.action_logout:
            default:
                return super.onOptionsItemSelected(item);
        }
        return true;
    }
}
