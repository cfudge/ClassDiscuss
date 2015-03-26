package com.example.cmput401.classdiscuss;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.parse.ParseClassName;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;

import java.io.ByteArrayOutputStream;
import java.util.Date;

/*
 * copyright 2015 Nhu Bui, Nancy Pham-Nguyen, Valerie Sawyer, Cole Fudge, Kelsey Wicentowich
 */
@ParseClassName("Message")
public class Message extends ParseObject {
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

    public Bitmap getPic(){
        ParseFile picFile = getParseFile("picture");
        if(picFile == null){
            return null;
        }
        try {
            byte[] image = picFile.getData();
            BitmapFactory.Options op = new BitmapFactory.Options();
            op.inSampleSize = 8;
            Bitmap pic = BitmapFactory.decodeByteArray(image, 0, image.length, op);
            return pic;
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void setPic(Bitmap pic){
        // Convert it to byte
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        // Compress image to lower quality scale 1 - 100
        pic.compress(Bitmap.CompressFormat.PNG, 100, stream);
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