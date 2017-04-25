package com.oujian.graduation.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.oujian.graduation.R;
import com.oujian.graduation.base.BaseActivity;

import butterknife.Bind;

public class ChangePasswordActivity extends BaseActivity {

    @Bind(R.id.change_password_toolBar)
    Toolbar mToolbar;
    @Override
    protected View getContentView() {
        View view =getLayoutInflater().inflate(R.layout.activity_change_password,null);
        return view;
    }

    @Override
    protected void initViews() {
        mToolbar.setTitle("");
        setSupportActionBar(mToolbar);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
    @Override
    protected void initListeners() {

    }

    @Override
    protected void initData() {

    }
}
