package com.oujian.graduation.manager;

import android.app.Activity;
import android.content.Intent;

import com.oujian.graduation.activity.LoginActivity;
import com.oujian.graduation.common.MyContext;
import com.oujian.graduation.utils.PreferencesUtils;


/**
 * 管理登录状态
 * Created by yi on 17/03/24.
 */
public class LoginManager {

    private static class SingleHolder{
       private static LoginManager mInstance = new LoginManager();
    }
    public static LoginManager getInstance() {

        return SingleHolder.mInstance;
    }

    private LoginManager(){
    }

    /**
     * 是否进入登录界面,没有userId就重新登录
     * @param activity
     * @return
     */
    public boolean gotoLogin(Activity activity) {
        if (activity == null) {
            return false;
        }
        if (MyContext.getInstance().getUserInfo().isLogin()) {
            return false;
        } else {
            MyContext.getInstance().resetUserInfo();
            Intent intent = new Intent(activity, LoginActivity.class);
            activity.startActivity(intent);
            return true;
        }
    }

}
