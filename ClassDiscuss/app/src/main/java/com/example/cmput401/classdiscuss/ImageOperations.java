package com.example.cmput401.classdiscuss;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.parse.ParseException;
import com.parse.ParseFile;

import java.io.ByteArrayOutputStream;

/**
 * Created by CMDF_Alien on 4/4/2015.
 */
public class ImageOperations {

    public static Bitmap bitmapFromPicFile(ParseFile picFile, int reqWidth, int reqHeight){
        try {
            byte[] image = picFile.getData();
            return compressImage(image, reqWidth,reqHeight);
        }
        catch(ParseException e){
            e.printStackTrace();
            return null;
        }
    }

    public static Bitmap compressImage(byte[] image, int reqWidth, int reqHeight){
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
    public static byte[] getBytes(Bitmap bmp){
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.PNG, 100, stream);
        return stream.toByteArray();
    }
}
