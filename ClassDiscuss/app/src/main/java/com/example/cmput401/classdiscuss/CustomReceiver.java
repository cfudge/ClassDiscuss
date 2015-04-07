package com.example.cmput401.classdiscuss;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.PushService;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;
import java.util.List;


/**
 * Created by Valerie on 2015-03-25.
 */
public class CustomReceiver extends BroadcastReceiver {
    private static final String TAG = "MyCustomReceiver";
    @Override
    public void onReceive(Context context, Intent intent) {
        //String name = intent.getAction();
        String name = "";
        String message = "";
       // Log.d(TAG, "got action name" + name );
        Notice notice = Notice.getInstance();
        try {
            JSONObject json = new JSONObject(intent.getExtras().getString("com.parse.Data"));
            //String name = json.getString(“alert”).toString();
            name = json.getString("username");
            message = json.getString("message");
            Log.d(TAG, "json: " + message );
        }
        catch (JSONException e){
                Log.d(TAG, "JSONException: " + e.getMessage());
            }
        //Notice notice = new Notice();
        notice.iconAppear();
        notice.setUsername(name);
        Intent cIntent = new Intent(context, MainActivity.class);
        PendingIntent contentIntent = PendingIntent.getActivity(context, 0, cIntent, 0);

        NotificationManager mNotifM = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        Toast.makeText(context, message, Toast.LENGTH_LONG).show();
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context).setSmallIcon(R.drawable.ic_nimicon)
                .setContentTitle(name)
                .setContentText(message)
                .setAutoCancel(true);

        mBuilder.setContentIntent(contentIntent);
        mNotifM.notify(5, mBuilder.build());
        /*Toast.makeText(context, "Push received!!!!.", Toast.LENGTH_LONG).show();
            if (intent == null)
            {
                Log.d(TAG, "Receiver intent null");
            }
            else
            {
                String action = intent.getAction();
                Log.d(TAG, "got action " + action );
                if (action.equals("com.example.cmput401.classdiscuss.UPDATE_STATUS"))
                {
                    String channel = intent.getExtras().getString("com.parse.Channel");
                    Log.d("channel", channel);
                }
            }
        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(context)
                        .setSmallIcon(R.drawable.ic_nimicon)
                        .setContentTitle("New Message")
                        .setContentText("Click to open!");
// Creates an explicit intent for an Activity in your app
        Intent resultIntent = new Intent(context, ConnectionsActivity.class);

// The stack builder object will contain an artificial back stack for the
// started Activity.
// This ensures that navigating backward from the Activity leads out of
// your application to the Home screen.
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
// Adds the back stack for the Intent (but not the Intent itself)
        stackBuilder.addParentStack(ConnectionsActivity.class);
// Adds the Intent that starts the Activity to the top of the stack
        stackBuilder.addNextIntent(resultIntent);
        PendingIntent resultPendingIntent =
                stackBuilder.getPendingIntent(
                        0,
                        PendingIntent.FLAG_UPDATE_CURRENT
                );
        mBuilder.setContentIntent(resultPendingIntent);
        NotificationManager mNotificationManager =
                (NotificationManager) Activity.getSystemService(Context.NOTIFICATION_SERVICE);
// mId allows you to update the notification later on.
        mNotificationManager.notify(mId, mBuilder.build());*/

    }

}
