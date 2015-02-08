package com.example.cmput401.classdiscuss;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Button;
import android.view.View.OnClickListener;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;



public class ProfileActivity extends sideBarMenuActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile);

        final Profile myProfile = Profile.getInstance();

        TextView textUserName = (TextView) findViewById(R.id.textUserName);
        TextView textUserEmail = (TextView) findViewById(R.id.textUserEmail);

        textUserEmail.setText(myProfile.getUserEmail());
        textUserName.setText(myProfile.getUserName());

        setCheckPrivacyBox(myProfile);

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
