package com.oujian.graduation.base;

import android.app.Application;
import android.content.Context;

import com.oujian.graduation.common.MyContext;

/**
 * Created by yi on 2017-03-20.
 */

public class BaseApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        //这样可以获取context上下文
        MyContext.getInstance().init(getApplicationContext());
    }
}
