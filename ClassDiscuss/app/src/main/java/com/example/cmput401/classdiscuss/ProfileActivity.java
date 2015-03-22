package com.example.cmput401.classdiscuss;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.widget.ImageView;
import android.widget.TextView;

/*
 * copyright 2015 Nhu Bui, Nancy Pham-Nguyen, Valerie Sawyer, Cole Fudge, Kelsey Wicentowich
 */
public class ProfileActivity extends sideBarMenuActivity{

    final Profile myProfile = Profile.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile);


        TextView textUserName = (TextView) findViewById(R.id.textUserName);
        TextView textUserEmail = (TextView) findViewById(R.id.textUserEmail);

        textUserEmail.setText(myProfile.getEmail());
        textUserName.setText(myProfile.getUserName());


        ImageView profilePicView = (ImageView) findViewById(R.id.imageUserProfile);

        if(myProfile.getProfilePicURI() != null) {
            profilePicView.setImageURI(myProfile.getProfilePicURI());
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        MenuInflater menuI = getMenuInflater();
        menuI.inflate(R.menu.menu_edit_profile, menu);

        return true;
    }

}
