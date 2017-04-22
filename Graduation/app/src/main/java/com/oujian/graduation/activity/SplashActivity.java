package com.oujian.graduation.activity;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.TextView;

import com.oujian.graduation.R;
import com.oujian.graduation.base.BaseActivity;

import java.lang.ref.WeakReference;

import butterknife.Bind;

public class SplashActivity extends BaseActivity {
    private final String TAG = this.getClass().getSimpleName();
    //倒计时
    @Bind(R.id.splash_time_tv)
    TextView mTimeCount;
    private int count =3;
    @Override
    protected View getContentView() {
        View view =getLayoutInflater().inflate(R.layout.activity_splash,null);
        return view;
    }

    @Override
    protected void initViews() {
    }

    @Override
    protected void initListeners() {
    }

    @Override
    protected void initData() {
        //暂时无数据需求
        //延迟2秒跳转到首页
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashActivity.this,MainActivity.class);
                startActivity(intent);
            }
        },2000);
    }

}
