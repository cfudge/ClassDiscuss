package com.example.cmput401.classdiscuss;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


/*
 * copyright 2015 Nhu Bui, Nancy Pham-Nguyen, Valerie Sawyer, Cole Fudge, Kelsey Wicentowich
 */
public class ProfileEditActivity extends sideBarMenuActivity {

    //For supplying to the startActivityForResult method:
    private static final int SELECT_PICTURE = 1;
    final Profile myProfile = Profile.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.editprofile);

        Button buttonSave = (Button) findViewById(R.id.buttonSave);
        final TextView EditUserName = (TextView) findViewById(R.id.EditUserName);
        TextView textUserEmail = (TextView) findViewById(R.id.textUserEmail);
        final ImageView profilePicView = (ImageView) findViewById(R.id.imageUserProfile);

        //set user's information
        EditUserName.setText(myProfile.getUserName());
        textUserEmail.setText(myProfile.getEmail());


        buttonSave.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent Profile = new Intent();
                Profile.setClass(getApplicationContext(), ProfileActivity.class);
                startActivity(Profile);

            }
        });

        /**
         * Referenced the code laid out here:
         * http://stackoverflow.com/questions/2169649/get-pick-an-image-from-androids-built-in-gallery-app-programmatically
         */
        profilePicView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent imageSelect = new Intent();
                imageSelect.setType("image/*");
                imageSelect.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(imageSelect, "Select Picture"), SELECT_PICTURE);
            }
        });

        if(myProfile.getProfilePicURI() != null){
            profilePicView.setImageURI(myProfile.getProfilePicURI());
        }

    }

    /**
     * Referenced the code laid out here:
     * http://stackoverflow.com/questions/2169649/get-pick-an-image-from-androids-built-in-gallery-app-programmatically
     */
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == SELECT_PICTURE) {
                Uri selectedImageUri = data.getData();
                myProfile.setProfilePicURI(selectedImageUri);
                ImageView profilePicView = (ImageView) findViewById(R.id.imageUserProfile);
                profilePicView.setImageURI(myProfile.getProfilePicURI());
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        MenuInflater menuI = getMenuInflater();
        menuI.inflate(R.menu.menu_mychannels, menu);

        return true;
    }


}
