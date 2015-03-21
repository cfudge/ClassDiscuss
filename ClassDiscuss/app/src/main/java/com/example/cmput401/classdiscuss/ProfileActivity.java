package com.example.cmput401.classdiscuss;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.widget.CheckBox;
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

        textUserEmail.setText(myProfile.getUserEmail());
        textUserName.setText(myProfile.getUserName());

        setCheckPrivacyBox(myProfile);

        ImageView profilePicView = (ImageView) findViewById(R.id.imageUserProfile);

        if(myProfile.getProfilePicURI() != null) {
            profilePicView.setImageURI(myProfile.getProfilePicURI());
        }
    }

    public void setCheckPrivacyBox(Profile myProfile){
        CheckBox boxPrivate = (CheckBox) findViewById(R.id.checkPrivate);
        CheckBox boxPublic = (CheckBox) findViewById(R.id.checkPublic);

        boxPrivate.setEnabled(false);
        boxPublic.setEnabled(false);

        if(myProfile.isEmailPrivate()){
            boxPrivate.setChecked(true);
            boxPublic.setChecked(false);
        }
        else{
            boxPrivate.setChecked(false);
            boxPublic.setChecked(true);
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
