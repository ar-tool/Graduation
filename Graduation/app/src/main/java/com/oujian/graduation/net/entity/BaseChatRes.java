package com.oujian.graduation.net.entity;

/**
 * Created by DIY on 2017/4/24.
 */

public class BaseChatRes {
    private String reason;
    private ChatEntity result;
    private int error_code;

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public ChatEntity getResult() {
        return result;
    }

    public void setResult(ChatEntity result) {
        this.result = result;
    }

    public int getError_code() {
        return error_code;
    }

    public void setError_code(int error_code) {
        this.error_code = error_code;
    }
}
