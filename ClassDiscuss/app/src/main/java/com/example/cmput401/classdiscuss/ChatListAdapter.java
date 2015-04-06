package com.example.cmput401.classdiscuss;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/*
 * copyright 2015 Nhu Bui, Nancy Pham-Nguyen, Valerie Sawyer, Cole Fudge, Kelsey Wicentowich
 */
public class ChatListAdapter extends ArrayAdapter<Message> {
    private String mUserId;
    private Calendar cal;

    public ChatListAdapter(Context context, String userId, List<Message> messages) {
        super(context, 0, messages);
        this.mUserId = userId;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).
                    inflate(R.layout.chat_item, parent, false);
            final ViewHolder holder = new ViewHolder();
            holder.image = (ImageView)convertView.findViewById(R.id.chatPic);
            holder.body = (TextView)convertView.findViewById(R.id.tvBody);
            holder.postStats = (TextView)convertView.findViewById(R.id.postStats);
            holder.selfProPic = (ImageView)convertView.findViewById(R.id.chatProfilePicSelf);
            holder.othersProPic = (ImageView)convertView.findViewById(R.id.chatProfilePicOther);
            convertView.setTag(holder);
        }
        final Message message = (Message)getItem(position);

        final ViewHolder holder = (ViewHolder)convertView.getTag();

        final ImageView proPic;
        final ImageView picView = holder.image;

        if(!Profiles.getInstance().loginEmail.replace("@ualberta.ca", "").equals(message.getUserId())){
            //If the user that posted is not the current user, use the left side for profile pic and
            //use pink, left-facing bubble
            ((ViewGroup) convertView).getChildAt(1).setBackgroundResource(R.drawable.speech_bubble_pink);
            proPic = holder.othersProPic;
            ((ImageView) convertView.findViewById(R.id.chatProfilePicSelf)).setVisibility(View.INVISIBLE);
            ((ImageView) convertView.findViewById(R.id.chatProfilePicSelf)).setImageBitmap(null);
            ((ImageView) convertView.findViewById(R.id.chatProfilePicOther)).setVisibility(View.VISIBLE);
        }
        else{
            //If the current user posted this message, use the right side for profile pic and use blue,
            //right-facing bubble.
            ((ViewGroup) convertView).getChildAt(1).setBackgroundResource(R.drawable.speech_bubble_blue);
            proPic = holder.selfProPic;
            ((ImageView) convertView.findViewById(R.id.chatProfilePicOther)).setVisibility(View.INVISIBLE);
            ((ImageView) convertView.findViewById(R.id.chatProfilePicOther)).setImageBitmap(null);
            ((ImageView) convertView.findViewById(R.id.chatProfilePicSelf)).setVisibility(View.VISIBLE);
        }

        //get pics on separate threads:
        new Thread(new Runnable() {
            public void run() {
                final Bitmap mProPic = message.getPosterPic();
                if (mProPic == null) {
                    proPic.post(new Runnable() {
                        public void run() {
                            //can't set imageview outside UI thread:
                            proPic.setImageBitmap(null);
                        }
                    });
                } else {
                    proPic.post(new Runnable() {
                        public void run() {
                            //can't set imageview outside UI thread:
                            proPic.setImageBitmap(mProPic);
                        }
                    });
                }
            }
        }).start();

        new Thread(new Runnable() {
            public void run() {
                final Bitmap messagePic = message.getPic();
                if (messagePic == null) {
                    picView.post(new Runnable() {
                        public void run() {
                            //can't set imageview outside UI thread:
                            picView.setImageBitmap(null);
                            //Picasso.with(getContext()).load(getProfileUrl(message.getUserId())).into(picView);                        }
                        }
                    });
                } else {
                    picView.post(new Runnable() {
                        public void run() {
                            //can't set imageview outside UI thread:
                            picView.setImageBitmap(messagePic);
                        }
                    });
                }

            }
        }).start();

        holder.body.setText(message.getBody());

        cal = Calendar.getInstance();
        Date postTime = message.getPostTime();

        //date difference in milliseconds:
        long dateDiffMS = cal.getTime().getTime() - postTime.getTime();
        float dateDiffSecs = dateDiffMS/1000;
        float dateDiffMins = dateDiffSecs/60;
        float dateDiffHrs = dateDiffMins/60;
        float dateDiffDays = dateDiffHrs/24;
        int dayDiff = Math.abs(cal.getTime().getDay() - postTime.getDay());
        String postTimeString = postTime.toString();


        if(dateDiffSecs < 60){
            holder.postStats.setText("A few seconds ago.");
        }
        else if(dateDiffMins < 60){
            if(((int)dateDiffMins) == 1){
                holder.postStats.setText("1 minute ago.");
            }
            else {
                holder.postStats.setText(String.valueOf((int) dateDiffMins) + " minutes ago.");
            }
        }
        else if((dateDiffHrs < 24) && (dayDiff == 0)){
            if(((int)dateDiffHrs) == 1){
                holder.postStats.setText("1 hour ago.");
            }
            else {
                holder.postStats.setText(String.valueOf((int) dateDiffHrs) + " hours ago.");
            }
        }
        else if((dateDiffHrs < 48) && (dayDiff < 2)){
            holder.postStats.setText("Yesterday.");
        }
        else{
            holder.postStats.setText(postTimeString.substring(0, postTimeString.length() - 9));
        }
        return convertView;
    }

    // Create a gravatar image based on the hash value obtained from userId
    private static String getProfileUrl(final String userId) {
        String hex = "";
        try {
            final MessageDigest digest = MessageDigest.getInstance("MD5");
            final byte[] hash = digest.digest(userId.getBytes());
            final BigInteger bigInt = new BigInteger(hash);
            hex = bigInt.abs().toString(16);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "http://www.gravatar.com/avatar/" + hex + "?d=identicon";
    }

    final class ViewHolder {
        public ImageView image;
        public ImageView selfProPic;
        public ImageView othersProPic;
        public TextView body;
        public TextView postStats;
    }

}
