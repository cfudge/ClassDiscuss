package com.example.cmput401.classdiscuss;

import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.parse.ParseUser;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


/*
 * copyright 2015 Nhu Bui, Nancy Pham-Nguyen, Valerie Sawyer, Cole Fudge, Kelsey Wicentowich
 */
public class ProfileEditActivity extends sideBarMenuActivity {

    //For supplying to the startActivityForResult method:
    private static final int SELECT_PICTURE = 1;
    final Profiles profiles = Profiles.getInstance();
    ParseUser currentUser = ParseUser.getCurrentUser();
    Profile currentUserProfile;
    private Uri outputFileUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.editprofile);
        
        currentUserProfile = new Profile();
        currentUserProfile.setParseEntry(currentUser);

        Button buttonSave = (Button) findViewById(R.id.buttonSave);
        final TextView EditUserName = (TextView) findViewById(R.id.EditUserName);
        TextView textUserEmail = (TextView) findViewById(R.id.textUserEmail);
        final ImageView profilePicView = (ImageView) findViewById(R.id.imageUserProfile);
        final ImageButton picButton = (ImageButton) findViewById(R.id.btProPicAdd);

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
        picButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                //following the code here: http://stackoverflow.com/questions/4455558/allow-user-to-select-camera-or-gallery-for-image

                // Determine Uri of camera image to save.
                final File root = new File(Environment.getExternalStorageDirectory() + File.separator + "NIMPics" + File.separator);
                root.mkdirs();
                final String fname = "nimPic.JPG";
                final File sdImageMainDirectory = new File(root, fname);
                outputFileUri = Uri.fromFile(sdImageMainDirectory);

                final List<Intent> cameraIntents = new ArrayList<Intent>();
                final Intent getPic = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                final PackageManager packageManager = getPackageManager();
                final List<ResolveInfo> listCam = packageManager.queryIntentActivities(getPic, 0);
                for(ResolveInfo res: listCam){
                    final String packageName = res.activityInfo.packageName;
                    final Intent intent = new Intent(getPic);
                    intent.setComponent(new ComponentName(packageName, res.activityInfo.name));
                    intent.setPackage(packageName);
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, outputFileUri);
                    cameraIntents.add(intent);
                }

                final Intent galleryIntent = new Intent();
                galleryIntent.setType("image/*");
                galleryIntent.setAction(Intent.ACTION_GET_CONTENT);

                final Intent chooserIntent = Intent.createChooser(galleryIntent, "Select Source");

                chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, cameraIntents.toArray(new Parcelable[cameraIntents.size()]));

                startActivityForResult(chooserIntent, SELECT_PICTURE);
            }
        });

        Bitmap profilePic = currentUserProfile.getPic();
        if(profilePic != null){//myProfile.getProfilePicURI() != null){
            profilePicView.setImageBitmap(profilePic);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        Notice notice = Notice.getInstance();
        notice.setLive(true);
    }

    @Override
    protected void onPause() {
        super.onPause();
        Notice notice = Notice.getInstance();
        notice.setLive(false);
    }


    /**
     * Referenced the code laid out here:
     * http://stackoverflow.com/questions/2169649/get-pick-an-image-from-androids-built-in-gallery-app-programmatically
     */
    public void onActivityResult(final int requestCode, final int resultCode, final Intent data) {

        new Thread(new Runnable() {
            public void run() {
                if (resultCode == RESULT_OK) {
                    if (requestCode == SELECT_PICTURE) {
                        //following the code here: http://stackoverflow.com/questions/4455558/allow-user-to-select-camera-or-gallery-for-image
                        final boolean isCamera;
                        if(data == null){
                            //if the camera was used, the image was saved to storage instead, so no data.
                            isCamera = true;
                        }
                        else{
                            final String action = data.getAction();
                            if (action == null){
                                //if the camera intent was picked, then the action is
                                //MediaStore.ACTION_IMAGE_CAPTURE, and thus cannot be null
                                isCamera = false;
                            }
                            else{
                                isCamera = action.equals(MediaStore.ACTION_IMAGE_CAPTURE);
                            }
                        }
                        Uri selectedImageUri;
                        if(isCamera){
                            selectedImageUri = outputFileUri;
                        }else{
                            selectedImageUri = data == null ? null : data.getData();
                        }
                        try {
                            final ImageView profilePicView = (ImageView) findViewById(R.id.imageUserProfile);
                            Bitmap newProPic = MediaStore.Images.Media.getBitmap(getContentResolver(), selectedImageUri);
                            newProPic = ImageOperations.compressImage(ImageOperations.getBytes(newProPic), 70, 70);
                            currentUserProfile.setPic(newProPic);
                            profilePicView.post(new Runnable() {
                                public void run(){
                                    //can't set imageview outside UI thread:
                                    profilePicView.setImageBitmap(currentUserProfile.getPic());
                                }
                            });
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }).start();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        MenuInflater menuI = getMenuInflater();
        menuI.inflate(R.menu.menu_editprofile, menu);
        return true;
    }


}
