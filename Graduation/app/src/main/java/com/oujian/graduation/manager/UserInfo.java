package com.oujian.graduation.manager;

import android.text.TextUtils;

/**
 * Created by yi on 2017-03-20.
 */

public class UserInfo {
    private String mAccount = "";
    private String mPassword = "";
    private String mUserId = "";
    private static UserInfo mUserInfo = null;
    public static UserInfo getInstance(){
        if(mUserInfo == null){
            mUserInfo = new UserInfo();
        }
        return mUserInfo;
    }

    public String getAccount() {
        return mAccount;
    }

    public void setAccount(String mAccount) {
        this.mAccount = mAccount;
    }

    public String getPassword() {
        return mPassword;
    }

    public void setPassword(String mPassword) {
        this.mPassword = mPassword;
    }

    public String getUserId() {
        return mUserId;
    }

    public void setUserId(String mUserId) {
        this.mUserId = mUserId;
    }
    //判断是否已经登录的关键
    public boolean isLogin() {
        return !TextUtils.isEmpty(mUserId);
    }
}
