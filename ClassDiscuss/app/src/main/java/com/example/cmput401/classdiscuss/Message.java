package com.example.cmput401.classdiscuss;

import com.parse.ParseClassName;
import com.parse.ParseObject;

/**
 * Created by Valerie on 2015-03-06.
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
}