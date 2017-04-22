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

    public String getmAccount() {
        return mAccount;
    }

    public void setmAccount(String mAccount) {
        this.mAccount = mAccount;
    }

    public String getmPassword() {
        return mPassword;
    }

    public void setmPassword(String mPassword) {
        this.mPassword = mPassword;
    }

    public String getmUserId() {
        return mUserId;
    }

    public void setmUserId(String mUserId) {
        this.mUserId = mUserId;
    }
    //判断是否已经登录的关键
    public boolean isLogin() {
        return !TextUtils.isEmpty(mUserId);
    }
}
