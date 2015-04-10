package com.example.cmput401.classdiscuss;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;


public class UsersAdapter extends ArrayAdapter<String> {
    Context c;
    private final Activity context;
    private final ArrayList<String> users;
    private final ArrayList<Bitmap> imageId;

    public UsersAdapter(Activity context,
                        ArrayList<String> users, ArrayList<Bitmap> imageId) {
        super(context, R.layout.activity_user_image, users);
        this.context = context;
        this.users = users;
        this.imageId = imageId;
    }
    @Override
    public View getView(final int position, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView= inflater.inflate(R.layout.activity_user_image, null, true);
        TextView txtTitle = (TextView) rowView.findViewById(R.id.usersName);
        ImageView imageView = (ImageView) rowView.findViewById(R.id.profileImage);
        txtTitle.setText(users.get(position));
        imageView.setImageBitmap(imageId.get(position));

        imageView.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

                // ListView Clicked item index
                int itemPosition = position;

                // ListView Clicked item value
                String itemValue = getItem(position);

                Log.v("score", itemValue + " username Image clicked");
                Profiles profiles = Profiles.getInstance();
                Profile displayProfile = new Profile();
                displayProfile.getParseEntry("username", itemValue);
                profiles.displayProfile = displayProfile;

                //start Profile activity
                context.startActivity(new Intent(context, ProfileActivity.class));
            }
        });
        return rowView;
    }

}
