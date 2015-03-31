package com.example.cmput401.classdiscuss;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.parse.ParseUser;

import java.io.IOException;


/*
 * copyright 2015 Nhu Bui, Nancy Pham-Nguyen, Valerie Sawyer, Cole Fudge, Kelsey Wicentowich
 */
public class ProfileEditActivity extends sideBarMenuActivity {

    //For supplying to the startActivityForResult method:
    private static final int SELECT_PICTURE = 1;
    final Profiles profiles = Profiles.getInstance();
    ParseUser currentUser = ParseUser.getCurrentUser();
    Profile currentUserProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.editprofile);

        //topbar color
        android.support.v7.app.ActionBar actionBar =  getSupportActionBar();
        ColorDrawable colorDraw = new ColorDrawable(Color.parseColor("#9FBF8C"));
        actionBar.setBackgroundDrawable(colorDraw);
        
        currentUserProfile = new Profile();
        currentUserProfile.setParseEntry(currentUser);

        Button buttonSave = (Button) findViewById(R.id.buttonSave);
        final TextView EditUserName = (TextView) findViewById(R.id.EditUserName);
        TextView textUserEmail = (TextView) findViewById(R.id.textUserEmail);
        final ImageView profilePicView = (ImageView) findViewById(R.id.imageUserProfile);

        //set user's information
        EditUserName.setText(currentUser.getString("username"));
        textUserEmail.setText(currentUser.getString("email"));

        buttonSave.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                profiles.displayProfile.setParseEntry(currentUser);
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

        Bitmap profilePic = currentUserProfile.getPic();
        if(profilePic != null){//myProfile.getProfilePicURI() != null){
            profilePicView.setImageBitmap(profilePic);
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
                try {
                    currentUserProfile.setPic(MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImageUri));
                } catch (IOException e) {
                    e.printStackTrace();

                }
                //myProfile.setProfilePicURI(selectedImageUri);
                ImageView profilePicView = (ImageView) findViewById(R.id.imageUserProfile);
                //profilePicView.setImageURI(myProfile.getProfilePicURI());
                profilePicView.setImageBitmap(currentUserProfile.getPic());
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        MenuInflater menuI = getMenuInflater();
        menuI.inflate(R.menu.menu_editprofile, menu);
        return true;
    }


}
