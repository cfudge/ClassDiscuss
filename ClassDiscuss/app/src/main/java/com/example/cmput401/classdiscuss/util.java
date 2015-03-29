package com.example.cmput401.classdiscuss;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.parse.ParseException;
import com.parse.ParseFile;

import java.io.ByteArrayOutputStream;


public class util {
    public Bitmap convertFileToBitmap(ParseFile picFile){
        if(picFile == null){
            return null;
        }
        try {
            byte[] image = picFile.getData();
            Bitmap pic = BitmapFactory.decodeByteArray(image, 0, image.length);
            return pic;
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }
    public ParseFile convertBitmapToParseFile( Bitmap bitmap){
        // Convert it to byte
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        // Compress image to lower quality scale 1 - 100
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] image = stream.toByteArray();

        // Create the ParseFile
        ParseFile file = new ParseFile("userPic.png", image);

        return file;
    }
}
