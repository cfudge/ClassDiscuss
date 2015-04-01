package com.example.cmput401.classdiscuss;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParsePush;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
/*
 * copyright 2015 Nhu Bui, Nancy Pham-Nguyen, Valerie Sawyer, Cole Fudge, Kelsey Wicentowich
 */
public class ChatActivity extends ActionBarActivity {
    private static final String TAG = ChatActivity.class.getName();
    private static String sUserId;

    private EditText etMessage;
    private Button btSend;
    private ImageButton btPicAdd;

    private static final int SELECT_PICTURE = 1;

    private ListView lvChat;
    private ArrayList<Message> mMessages;
    private ChatListAdapter mAdapter;
    IntentFilter filter1;
    private static final int MAX_CHAT_MESSAGES_TO_SHOW = 50;
    private Message message = new Message();

    Profiles profiles = Profiles.getInstance();

    // Create a handler which can run code periodically
    private Handler handler = new Handler();

    // Defines a runnable which is run every 10000ms
    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            refreshMessages();
            handler.postDelayed(this, 10000);
        }
    };
    private void refreshMessages() {
        receiveMessage();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        //topbar color
        android.support.v7.app.ActionBar actionBar =  getSupportActionBar();
        ColorDrawable colorDraw = new ColorDrawable(Color.parseColor("#9FBF8C"));
        actionBar.setBackgroundDrawable(colorDraw);

        // User login
        if (ParseUser.getCurrentUser() != null) { // start with existing user
            refreshMessages();
            //filter1 = new IntentFilter("android.bluetooth.BluetoothDevice.ACTION_ACL_CONNECTED");

            //registerReceiver(myReceiver, filter1);
            startWithCurrentUser();
        } else { // If not logged in, login as a new anonymous user
            LoginActivity logout = new LoginActivity();
            logout.onPlusClientSignOut();
            Intent ToLogIn = new Intent();
            ToLogIn.setClass(getApplicationContext(), MainActivity.class);
            startActivity(ToLogIn);//login();
        }
        // Run the runnable object defined every 100ms
      // handler.postDelayed(runnable, 100);
    }
    public void onResume() {
        super.onResume();
        filter1 = new IntentFilter("android.bluetooth.BluetoothDevice.ACTION_ACL_CONNECTED");
        registerReceiver(myReceiver, filter1);
        //registerReceiver(myReceiver, filter1);
    }

    // Get the userId from the cached currentUser object
    private void startWithCurrentUser() {
        sUserId = ParseUser.getCurrentUser().getUsername();
        setupMessagePosting();
    }

    // Create an anonymous user using ParseAnonymousUtils and set sUserId
    /*private void login() {
        ParseAnonymousUtils.logIn(new LogInCallback() {
            @Override
            public void done(ParseUser user, ParseException e) {
                if (e != null) {
                    Log.d(TAG, "Anonymous login failed: " + e.toString());
                } else {
                    startWithCurrentUser();
                }
            }
        });
    }*/

    // Setup button event handler which posts the entered message to Parse
    private void setupMessagePosting() {
        // Find the text field and button
        etMessage = (EditText) findViewById(R.id.etMessage);
        btSend = (Button) findViewById(R.id.btSend);
        lvChat = (ListView) findViewById(R.id.lvChat);
        btPicAdd = (ImageButton) findViewById(R.id.btPicAdd);
        mMessages = new ArrayList<Message>();
        mAdapter = new ChatListAdapter(ChatActivity.this, sUserId, mMessages);
        lvChat.setAdapter(mAdapter);
        btSend.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String body = etMessage.getText().toString();
                // Use Message model to create new messages now
                message.setUserId(sUserId);
                message.setBody(body);
                message.setReceiver(profiles.displayProfile.getUserName());
                Intent localIntent = new Intent("android.bluetooth.BluetoothDevice.ACTION_ACL_CONNECTED");
                sendBroadcast(localIntent);
                /*ParsePush push = new ParsePush();
                //push.subscribeInBackground("Message");
                //push.setChannel("Message");

                push.setMessage("this is my message");*/

                //push.sendInBackground();
                message.saveInBackground(new SaveCallback() {
                    @Override
                    public void done(ParseException e) {
                        //receiveMessage();
                        ParsePush push = new ParsePush();
                //push.subscribeInBackground("Message");
                //push.setChannel("Message");
                       // receiveMessage();
                        push.setMessage("this is my message");

                        push.sendInBackground();
                    }
                });
                message = new Message();
                etMessage.setText("");
            }
        });
        btPicAdd.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent imageSelect = new Intent();
                imageSelect.setType("image/*");
                imageSelect.setAction(MediaStore.ACTION_IMAGE_CAPTURE); //Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(imageSelect, "Select Picture"), SELECT_PICTURE);
            }
        });
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == SELECT_PICTURE) {
                Uri selectedImageUri = data.getData();
                try {
                    message.setPic(MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImageUri));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_chat, menu);
        return true;
    }

    /*@Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }*/
    // Query messages from Parse so we can load them into the chat adapter
    private void receiveMessage() {
        // Construct query to execute
        ParseQuery<Message> query = ParseQuery.getQuery(Message.class);
        // Configure limit and sort order
        query.setLimit(MAX_CHAT_MESSAGES_TO_SHOW);
        Log.d("me", ParseUser.getCurrentUser().getUsername().toString());
        String[] names = {ParseUser.getCurrentUser().getString("username"), profiles.displayProfile.getUserName()};
        query.whereContainedIn("userId", Arrays.asList(names));
        query.whereContainedIn("Receiver", Arrays.asList(names));
        query.orderByAscending("createdAt");
        // Execute query to fetch all messages from Parse asynchronously
        // This is equivalent to a SELECT query with SQL
        query.findInBackground(new FindCallback<Message>() {
            public void done(List<Message> messages, ParseException e) {
                if (e == null) {
                    if (mMessages != null) {
                        mMessages.clear();
                        mMessages.addAll(messages);
                        mAdapter.notifyDataSetChanged(); // update adapter
                        lvChat.invalidate(); // redraw listview
                    }
                } else {
                    Log.d("message", "Error: " + e.getMessage());
                }
            }
        });
    }
    private final BroadcastReceiver myReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            // TODO Auto-generated method stub
            Log.d("in br", "yesdgfffffffffffffffffffffffffffffff");
            if(intent.getAction().equalsIgnoreCase("android.bluetooth.BluetoothDevice.ACTION_ACL_CONNECTED"))
            {
                Log.d("DEFINITELY HERE", "YES");
                receiveMessage();
            }

        }
    };
    /*public final class Receiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent){
            receiveMessage();

        }
    }*/
    @Override
    public void onPause() {
        super.onPause();
        unregisterReceiver(myReceiver);

    }

    @Override
    public void onDestroy(){
        //So that profile pics update if the chat is left and
        //re-entered
        profiles.profilePics.clear();
    }
}
