package com.oujian.graduation.entity;

/**
 * Created by yi on 2016/9/18.
 * 点赞
 */
public class Click {
    /**
     * 点赞用户
     */
    private String friendId;
    /**
     * 用户昵称
     */
    private String friendName;
    /**
     * 点赞时间
     */
    private String zanTime;


    public String getFriendId() {
        return friendId;
    }

    public void setFriendId(String friendId) {
        this.friendId = friendId;
    }

    public String getFriendName() {
        return friendName;
    }

    public void setFriendName(String friendName) {
        this.friendName = friendName;
    }

    public String getZanTime() {
        return zanTime;
    }

    public void setZanTime(String zanTime) {
        this.zanTime = zanTime;
    }
}
