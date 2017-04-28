package com.oujian.graduation.entity;

/**
 * Created by yi on 2016/9/18.
 */
public class PingLun {
    //评论Id
    private String pingLunId;

    //评论的用户，
    private String pUserId;
    /**
     * 评论的用户昵称
     */
    private String pUserName;
    //对谁的评论，Null为评论帖子
    private String towho;
    /**
     * 被评论人昵称
     */
    private String toWhoName;
    //评论时间
    private String pingLunTime;
    //评论内容
    private String value;


    public String getpUserId() {
        return pUserId;
    }

    public void setpUserId(String pUserId) {
        this.pUserId = pUserId;
    }

    public String getpUserName() {
        return pUserName;
    }

    public void setpUserName(String pUserName) {
        this.pUserName = pUserName;
    }

    public String getTowho() {
        return towho;
    }

    public void setTowho(String towho) {
        this.towho = towho;
    }

    public String getToWhoName() {
        return toWhoName;
    }

    public void setToWhoName(String toWhoName) {
        this.toWhoName = toWhoName;
    }

    public String getPingLunId() {
        return pingLunId;
    }

    public void setPingLunId(String pingLunId) {
        this.pingLunId = pingLunId;
    }

    public String getPingLunTime() {
        return pingLunTime;
    }

    public void setPingLunTime(String pingLunTime) {
        this.pingLunTime = pingLunTime;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
