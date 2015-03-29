package com.example.cmput401.classdiscuss;

import android.app.Activity;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;


public class UsersAdapter extends ArrayAdapter<String> {
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
    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView= inflater.inflate(R.layout.activity_user_image, null, true);
        TextView txtTitle = (TextView) rowView.findViewById(R.id.usersName);
        ImageView imageView = (ImageView) rowView.findViewById(R.id.profileImage);
        txtTitle.setText(users.get(position));
        imageView.setImageBitmap(imageId.get(position));
        return rowView;
    }

}
