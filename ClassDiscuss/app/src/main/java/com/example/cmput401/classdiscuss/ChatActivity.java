package com.example.cmput401.classdiscuss;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseInstallation;
import com.parse.ParsePush;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
/*
 * copyright 2015 Nhu Bui, Nancy Pham-Nguyen, Valerie Sawyer, Cole Fudge, Kelsey Wicentowich
 */
public class ChatActivity extends sideBarMenuActivity {
    private static final String TAG = ChatActivity.class.getName();
    private static String sUserId;
    private Uri outputFileUri;


    private EditText etMessage;
    private ImageButton btSend;
    private ImageButton btPicAdd;

    private static final int SELECT_PICTURE = 1;

    private ImageView postPicView;
    private ListView lvChat;
    private ArrayList<Message> mMessages;
    private ChatListAdapter mAdapter;
    IntentFilter filter1;
    private static final int MAX_CHAT_MESSAGES_TO_SHOW = 70;
    private Message message = new Message();

    Profiles profiles = Profiles.getInstance();
    public String body;
    // Create a handler which can run code periodically
    private Handler handler = new Handler();
    Notice notice = Notice.getInstance();
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
        postPicView = (ImageView) findViewById(R.id.postPicView);
        postPicView.setVisibility(View.INVISIBLE);
        //topbar color
        android.support.v7.app.ActionBar actionBar =  getSupportActionBar();
        ColorDrawable colorDraw = new ColorDrawable(Color.parseColor("#9FBF8C"));
        actionBar.setBackgroundDrawable(colorDraw);

        // User login
        if (ParseUser.getCurrentUser() != null) { // start with existing user
            refreshMessages();
            //filter1 = new IntentFilter("android.bluetooth.BluetoothDevice.ACTION_ACL_CONNECTED");
// Associate the device with a user
            ParseInstallation installation = ParseInstallation.getCurrentInstallation();
            installation.put("user",ParseUser.getCurrentUser());
            installation.saveInBackground();
            //registerReceiver(myReceiver, filter1);
            startWithCurrentUser();
        } else {
            Intent ToLogIn = new Intent();
            ToLogIn.setClass(getApplicationContext(), MainActivity.class);
            startActivity(ToLogIn);//login();
        }
        // Run the runnable object defined every 100ms
      // handler.postDelayed(runnable, 100);
    }
    public void onResume() {
        super.onResume();
        receiveMessage();
        if(notice.getUsername().equals(profiles.displayProfile.getUserName()));
            notice.iconDisappear();
       // filter1 = new IntentFilter("android.bluetooth.BluetoothDevice.ACTION_ACL_CONNECTED");
        //registerReceiver(myReceiver, filter1);
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
        btSend = (ImageButton) findViewById(R.id.btSend);
        lvChat = (ListView) findViewById(R.id.lvChat);
        btPicAdd = (ImageButton) findViewById(R.id.btPicAdd);
        mMessages = new ArrayList<Message>();
        mAdapter = new ChatListAdapter(ChatActivity.this, sUserId, mMessages);
        lvChat.setAdapter(mAdapter);
        btSend.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                postPicView.setImageBitmap(null);
                postPicView.setVisibility(View.INVISIBLE);
                body = etMessage.getText().toString();
                if (!body.isEmpty()) {
                    // Use Message model to create new messages now
                    message.setUserId(sUserId);
                    message.setBody(body);
                    message.setReceiver(profiles.displayProfile.getUserName());
                    message.saveInBackground(new SaveCallback() {
                        @Override
                        public void done(ParseException e) {
                            /*ParsePush push = new ParsePush();
                            push.setChannel(profiles.displayProfile.getUserName().toString());
                            push.setMessage("New Message!");
                            push.sendInBackground();*/
                            // Find users near a given location
                            ParseQuery userQuery = ParseUser.getQuery();
                            userQuery.whereEqualTo("username", profiles.displayProfile.getUserName());
                            if (userQuery == null)
                                Log.d("user query is", "null");

                            ParseQuery pushQuery = ParseInstallation.getQuery();
                            pushQuery.whereMatchesQuery("user", userQuery);
                            JSONObject obj =new JSONObject();
                            try{
                                obj.put("username", ParseUser.getCurrentUser().getUsername());
                                obj.put("message", body);
                            } catch (JSONException error) {
                                // TODO Auto-generated catch block
                                error.printStackTrace();
                            }
                            // Send push notification to query
                            ParsePush push = new ParsePush();
                            push.setQuery(pushQuery); // Set our Installation query
                            //push.setMessage("New Message!");
                            push.setData(obj);
                            push.sendInBackground();
                            receiveMessage();
                        }
                    });
                    message = new Message();
                    etMessage.setText("");
                }
            }
        });
        btPicAdd.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //following the code here: http://stackoverflow.com/questions/4455558/allow-user-to-select-camera-or-gallery-for-image

                // Determine Uri of camera image to save.
                final File root = new File(Environment.getExternalStorageDirectory() + File.separator + "MyDir" + File.separator);
                root.mkdirs();
                final String fname = "nimPic";
                final File sdImageMainDirectory = new File(root, fname);
                outputFileUri = Uri.fromFile(sdImageMainDirectory);

                final List<Intent> cameraIntents = new ArrayList<Intent>();
                final Intent getPic = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                final PackageManager packageManager = getPackageManager();
                final List<ResolveInfo> listCam = packageManager.queryIntentActivities(getPic, 0);
                for(ResolveInfo res: listCam){
                    final String packageName = res.activityInfo.packageName;
                    final Intent intent = new Intent(getPic);
                    intent.setComponent(new ComponentName(packageName, res.activityInfo.name));
                    intent.setPackage(packageName);
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, outputFileUri);
                    cameraIntents.add(intent);
                }

                final Intent galleryIntent = new Intent();
                galleryIntent.setType("image/*");
                galleryIntent.setAction(Intent.ACTION_GET_CONTENT);

                final Intent chooserIntent = Intent.createChooser(galleryIntent, "Select Source");

                chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, cameraIntents.toArray(new Parcelable[cameraIntents.size()]));

                startActivityForResult(chooserIntent, SELECT_PICTURE);
            }
        });
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == SELECT_PICTURE) {
                //following the code here: http://stackoverflow.com/questions/4455558/allow-user-to-select-camera-or-gallery-for-image
                final boolean isCamera;
                if(data == null){
                    //if the camera was used, the image was saved to storage instead, so no data.
                    isCamera = true;
                }
                else{
                    final String action = data.getAction();
                    if (action == null){
                        //if the camera intent was picked, then the action is
                        //MediaStore.ACTION_IMAGE_CAPTURE, and thus cannot be null
                        isCamera = false;
                    }
                    else{
                        isCamera = action.equals(MediaStore.ACTION_IMAGE_CAPTURE);
                    }
                }
                Uri selectedImageUri;
                if(isCamera){
                    selectedImageUri = outputFileUri;
                }else{
                    selectedImageUri = data.getData();
                }
                try {
                    message.setPic(MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImageUri));
                   postPicView.post(new Runnable() {
                       public void run() {
                           postPicView.setImageBitmap(message.getSmallPostPic());
                           postPicView.setVisibility(View.VISIBLE);
                       }
                   });
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
        receiveMessage();
       // unregisterReceiver(myReceiver);

    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        //So that profile pics update if the chat is left and
        //re-entered
        profiles.profilePics.clear();
    }
}
