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
import java.util.List;

/*
 * copyright 2015 Nhu Bui, Nancy Pham-Nguyen, Valerie Sawyer, Cole Fudge, Kelsey Wicentowich
 */
public class ChatListAdapter extends ArrayAdapter<Message> {
    private String mUserId;

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
        Bitmap messagePic = message.getPic();

        if(!Profiles.getInstance().loginEmail.replace("@ualberta.ca", "").equals(message.getUserId())){
            ((ViewGroup) convertView).getChildAt(1).setBackgroundResource(R.drawable.speech_bubble_pink);
            proPic = holder.othersProPic;
            ((ImageView) convertView.findViewById(R.id.chatProfilePicSelf)).setVisibility(View.INVISIBLE);
            ((ImageView) convertView.findViewById(R.id.chatProfilePicSelf)).setImageBitmap(null);
            ((ImageView) convertView.findViewById(R.id.chatProfilePicOther)).setVisibility(View.VISIBLE);
        }
        else{
            ((ViewGroup) convertView).getChildAt(1).setBackgroundResource(R.drawable.speech_bubble_blue);
            proPic = holder.selfProPic;
            ((ImageView) convertView.findViewById(R.id.chatProfilePicOther)).setVisibility(View.INVISIBLE);
            ((ImageView) convertView.findViewById(R.id.chatProfilePicOther)).setImageBitmap(null);
            ((ImageView) convertView.findViewById(R.id.chatProfilePicSelf)).setVisibility(View.VISIBLE);
        }


        Bitmap mProPic = message.getPosterPic();
        if(mProPic == null){
            proPic.setImageBitmap(null);
        }
        else{
            proPic.setImageBitmap(mProPic);
        }

        if(messagePic == null) {
            picView.setImageBitmap(null);
            //Picasso.with(getContext()).load(getProfileUrl(message.getUserId())).into(picView);
        }
        else{
            picView.setImageBitmap(messagePic);
        }
        holder.body.setText(message.getBody());
        holder.postStats.setText(message.getPostTime());
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
