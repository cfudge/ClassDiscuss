package com.example.cmput401.classdiscuss;

/**
 * Created by Valerie on 2015-04-04.
 */
public class Notice {
    private static final Notice NoticeInstance = new Notice();
    boolean newNotice;
    boolean awake;
    String username;
    boolean live;
    public Notice() {
        this.newNotice = true;
        this.awake = false;
        this.username = "";
        this.live = false;
    }

    public static Notice getInstance(){
        return NoticeInstance;
    }

    public boolean unreadNotice() {
        return this.awake;
    }
    public void iconAppear() {
        this.awake = true;
    }
    public void iconDisappear() {
        this.awake = false;
    }

    public void setUsername(String name) {this.username = name;}
    public String getUsername() {return this.username;}

    //public void setNotice(boolean read){this.newNotice = read;}

    public void setLive(boolean live){this.live = live;}
    public boolean getLive(){return this.live;}
}
