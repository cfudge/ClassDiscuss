package com.example.cmput401.classdiscuss;

/**
 * Created by Valerie on 2015-04-04.
 */
public class Notice {
    private static final Notice NoticeInstance = new Notice();
    boolean newNotice;
    boolean awake;
    public Notice() {
        this.newNotice = false;
        this.awake = false;
    }

    public static Notice getInstance(){
        return NoticeInstance;
    }

    public boolean isIconThere() {
        return this.awake;
    }
    public boolean isNew() {
        return newNotice;
    }
    public void iconAppear() {
        this.awake = true;
    }
    public void iconDisappear() {
        this.awake = false;
    }
    public void unreadNotice() {
        newNotice = true;
    }
    public void readNotice() {
        newNotice = false;
    }
}
