package com.oujian.graduation.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.oujian.graduation.R;
import com.oujian.graduation.base.BaseActivity;

public class LoginActivity extends BaseActivity {
    private final String TAG = this.getClass().getSimpleName();


    @Override
    protected View getContentView() {
        View view =getLayoutInflater().inflate(R.layout.activity_login,null);
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

    }
}
