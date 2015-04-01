package com.example.cmput401.classdiscuss;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.parse.ParseClassName;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.io.ByteArrayOutputStream;
import java.util.Date;

/*
 * copyright 2015 Nhu Bui, Nancy Pham-Nguyen, Valerie Sawyer, Cole Fudge, Kelsey Wicentowich
 */
@ParseClassName("Message")
public class Message extends ParseObject {
    private Bitmap postPic = null;
    private Bitmap proPic = null;
    private boolean triedProFetch = false;
    private boolean triedPostPicFetch = false;
    private Profiles profiles = Profiles.getInstance();
    public String getUserId() {
        return getString("userId");
    }

    public String getBody() {
        return getString("body");
    }

    public void setUserId(String userId) {
        put("userId", userId);
    }

    public void setBody(String body) {
        put("body", body);
    }
    public void setReceiver(String receiver) { put("Receiver", receiver);}

    public Bitmap getPic() {
        if(triedPostPicFetch){
            return postPic;
        }
        triedPostPicFetch = true;
        ParseFile picFile = getParseFile("picture");
        if (picFile == null) {
            return null;
        }
        else {
            Bitmap result = bitmapFromPicFile(picFile, 300, 300);
            try {
                this.pin();
            } catch (ParseException e) {
                e.printStackTrace();
            }
            triedPostPicFetch = true;
            postPic = result;
            return result;
        }
    }

    public Bitmap getPosterPic(){
        if(triedProFetch){
            return proPic;
        }
        Bitmap result;
        triedProFetch = true;
        if(profiles.profilePics.containsKey(getUserId())){
            result = profiles.profilePics.get(getUserId());
            proPic = result;
            return result;
        }
        ParseFile picFile = null;
        ParseUser sender = null;
        ParseQuery query = ParseUser.getQuery();
        query.fromLocalDatastore();
        query.whereEqualTo("username", getUserId());
        try {
            sender = (ParseUser) query.find().get(0);
            sender.pin();
        } catch (Exception e) {
            //e.printStackTrace();
        }

        if(sender != null) {
            picFile = sender.getParseFile("ProfilePic");
        }
        if(picFile != null) {
            result = bitmapFromPicFile(picFile, 100, 100);
            profiles.profilePics.put(getUserId(), result);
            proPic = result;
            return result;
        }
        else{
            return null;
        }
    }

    private Bitmap bitmapFromPicFile(ParseFile picFile, int reqWidth, int reqHeight){
        try {
            byte[] image = picFile.getData();
            BitmapFactory.Options op = new BitmapFactory.Options();
            op.inJustDecodeBounds = true;
            BitmapFactory.decodeByteArray(image, 0, image.length, op);
            op.inJustDecodeBounds = false;
            op.inSampleSize = calculateInSampleSize(op, reqWidth, reqHeight);
            Bitmap pic = BitmapFactory.decodeByteArray(image, 0, image.length, op);
            ByteArrayOutputStream os = new ByteArrayOutputStream();
            pic.compress(Bitmap.CompressFormat.JPEG, 30, os);

            //compressing bitmap like here:
            //http://android.okhelp.cz/compressing-a-bitmap-to-jpg-format-android-example/
            image = os.toByteArray();
            op = new BitmapFactory.Options();
            op.inJustDecodeBounds = true;
            BitmapFactory.decodeByteArray(image, 0, image.length, op);
            op.inJustDecodeBounds = false;
            op.inSampleSize = calculateInSampleSize(op, reqWidth, reqHeight);
            pic = BitmapFactory.decodeByteArray(image, 0, image.length, op);
            return pic;
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }


    //from developer.android: http://developer.android.com/training/displaying-bitmaps/load-bitmap.html
    public static int calculateInSampleSize(
            BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
            // height and width larger than the requested height and width.
            while ((halfHeight / inSampleSize) > reqHeight
                    && (halfWidth / inSampleSize) > reqWidth) {
                inSampleSize *= 2;
            }
        }

        return inSampleSize;
    }

    public void setPic(Bitmap pic){
        // Convert it to byte
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        // Compress image to lower quality scale 1 - 100
        pic.compress(Bitmap.CompressFormat.PNG, 20, stream);
        byte[] image = stream.toByteArray();

        // Create the ParseFile
        ParseFile file = new ParseFile("comment_pic.png", image);

        put("picture", file);
    }

    public String getPostTime(){
        Date creationTime = getCreatedAt();
        return creationTime.toString();
    }

}