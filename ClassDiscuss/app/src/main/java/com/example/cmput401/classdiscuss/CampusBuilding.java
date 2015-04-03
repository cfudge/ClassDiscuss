package com.example.cmput401.classdiscuss;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.LatLngBounds;

import java.util.ArrayList;


/**
 * Created by Kelsey on 25/03/2015.
 */
public class CampusBuilding {
    private LatLngBounds bounds;
    private int numPeople;
    private GoogleMap mMap;
    private Marker marker;
    private Context context;
    private ArrayList<OtherUserMapInfo> usersInBuilding;

    public CampusBuilding(LatLng southwest, LatLng northeast, LatLng center, GoogleMap map, Context c)
    {
        context = c;
        mMap = map;
        marker = map.addMarker(
                new MarkerOptions().position(center)
        );
        setNumPeople(0);
        bounds = new LatLngBounds(southwest,northeast);
        usersInBuilding = new ArrayList<OtherUserMapInfo>();
    }

    public LatLngBounds getBounds() {
        return bounds;
    }

    public int getNumPeople() {
        return numPeople;
    }

    public void setNumPeople(int numPeople) {
        this.numPeople = numPeople;
        if (numPeople == 0)
        {
            marker.setVisible(false);
        }
        else
        {
            marker.setVisible(true);
            drawMarkerNumber(String.valueOf(numPeople));
        }
    }

    public void drawMarkerNumber(String numStr)
    {
        Typeface typeface = Typeface.create("Helvetica",Typeface.NORMAL);
        Bitmap markerImage = BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_marker).copy(Bitmap.Config.ARGB_8888, true);
        Canvas canvas = new Canvas(markerImage);
        Paint paint = new Paint();
        paint.setTextSize(22);
        paint.setTypeface(typeface);
        paint.setAntiAlias(true);
        paint.setTextAlign(Paint.Align.CENTER);
        int x = canvas.getWidth()/2;
        int y = (markerImage.getHeight()/2) + 5;
        canvas.drawText(numStr, x, y, paint); // paint defines the text color, stroke width, size
        BitmapDrawable draw = new BitmapDrawable(context.getResources(), markerImage);
        Bitmap drawBmp = draw.getBitmap();
        marker.setIcon(BitmapDescriptorFactory.fromBitmap(drawBmp));
    }

    public void addUser(OtherUserMapInfo user)
    {
        usersInBuilding.add(user);
    }
}
