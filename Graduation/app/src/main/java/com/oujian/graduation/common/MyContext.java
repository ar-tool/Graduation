package com.oujian.graduation.common;

import android.content.Context;

import com.oujian.graduation.manager.UserInfo;

/**
 * Created by yi on 2017-04-20.
 */

public class MyContext {
    private static MyContext ourInstance = null;
    private Context mContext;
    private UserInfo mUserInfo;
    private MyContext(){
        mUserInfo = UserInfo.getInstance();
    }
    public static MyContext getInstance() {
        if(ourInstance == null){
            ourInstance = new MyContext();
        }
        return ourInstance;
    }
    public void init(Context context) {
        mContext = context;
    }

    public Context getContext() {
        return mContext;
    }
    public String getUserId() {
        return mUserInfo.getUserId();
    }
    public void resetUserInfo() {
        mUserInfo = new UserInfo();
    }
    public UserInfo getUserInfo() {
        return mUserInfo;
    }
}
