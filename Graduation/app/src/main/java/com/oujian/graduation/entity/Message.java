package com.oujian.graduation.entity;

/**
 * Created by Administrator on 2017-04-23.
 */

public class Message {
    public static final int TYPE_MY = 0;
    public static final int TYPE_SERVICE = 1;
    private String message;
    private int type; //0-my 1-service

    public Message(String message, int type) {
        this.message = message;
        this.type = type;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
