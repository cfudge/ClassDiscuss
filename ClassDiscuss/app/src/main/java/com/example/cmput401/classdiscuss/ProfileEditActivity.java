package com.example.cmput401.classdiscuss;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;



public class ProfileEditActivity extends sideBarMenuActivity {

    //For supplying to the startActivityForResult method:
    private static final int SELECT_PICTURE = 1;
    final Profile myProfile = Profile.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.editprofile);

        Button buttonSave = (Button) findViewById(R.id.buttonSave);
        final EditText EditUserName = (EditText) findViewById(R.id.EditUserName);
        TextView textUserEmail = (TextView) findViewById(R.id.textUserEmail);
        final CheckBox boxPrivate = (CheckBox) findViewById(R.id.EditCheckPrivate);
        final CheckBox boxPublic = (CheckBox) findViewById(R.id.EditCheckPublic);
        final ImageView profilePicView = (ImageView) findViewById(R.id.imageUserProfile);

        //set user's information
        EditUserName.setText(myProfile.getUserName());
        textUserEmail.setText(myProfile.getUserEmail());
        if(myProfile.isEmailPrivate()){
            privateChecked();
        }
        else{
            publicChecked();
        }

        buttonSave.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                myProfile.setUserName(EditUserName.getText().toString());
                myProfile.setEmailPrivate(boxPrivate.isChecked());

                Intent Profile = new Intent();
                Profile.setClass(getApplicationContext(), ProfileActivity.class);
                startActivity(Profile);

            }
        });

        boxPrivate.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                String message1 = getResources().getString(R.string.textWarning);
                String message2 = getResources().getString(R.string.textWarningMessage);
                if(boxPrivate.isChecked()){
                    alertBox(message1, message2);
                }
            }
        });

        boxPublic.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                publicChecked();
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

    protected void alertBox(String title, String myMessage)
    {
        new AlertDialog.Builder(this)
                .setMessage(myMessage)
                .setTitle(title)
                .setCancelable(true)
                .setNeutralButton(android.R.string.ok,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton){
                                privateChecked();
                            }
                        })
                .setNegativeButton(android.R.string.cancel,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton){
                                publicChecked();
                            }
                        })
                .show();
    }

    private void privateChecked(){
        final Profile myProfile = Profile.getInstance();
        final EditText EditUserName = (EditText) findViewById(R.id.EditUserName);
        final CheckBox boxPrivate = (CheckBox) findViewById(R.id.EditCheckPrivate);
        final CheckBox boxPublic = (CheckBox) findViewById(R.id.EditCheckPublic);
        boxPrivate.setEnabled(false);
        boxPrivate.setChecked(true);
        boxPublic.setEnabled(true);
        boxPublic.setChecked(false);
        boxPublic.setSelected(true);
        EditUserName.setEnabled(false);
        EditUserName.setText(myProfile.getUsernameFromEmail());
    }

    private void publicChecked(){
        final EditText EditUserName = (EditText) findViewById(R.id.EditUserName);
        final CheckBox boxPrivate = (CheckBox) findViewById(R.id.EditCheckPrivate);
        final CheckBox boxPublic = (CheckBox) findViewById(R.id.EditCheckPublic);
        boxPrivate.setEnabled(true);
        boxPrivate.setChecked(false);
        boxPrivate.setSelected(true);
        boxPublic.setEnabled(false);
        boxPublic.setChecked(true);
        EditUserName.setEnabled(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        MenuInflater menuI = getMenuInflater();
        menuI.inflate(R.menu.menu_side_bar_menu, menu);

        return true;
    }


}