package com.example.cmput401.classdiscuss;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;

import org.json.JSONException;

import java.util.List;

/**
 * Created by Valerie on 2015-03-25.
 */
public class CustomReceiver extends BroadcastReceiver {
    private static final String TAG = "MyCustomReceiver";
    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context, "Push received!!!!.", Toast.LENGTH_LONG).show();
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

    }
}
