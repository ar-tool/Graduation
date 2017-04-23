package com.oujian.graduation.activity;

import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.oujian.graduation.R;
import com.oujian.graduation.base.BaseActivity;

import butterknife.Bind;
import butterknife.OnClick;

public class LoginActivity extends BaseActivity {
    private final String TAG = this.getClass().getSimpleName();

    @Bind(R.id.login_toolBar)
    Toolbar mToolbar;

    @Override
    protected View getContentView() {
        View view =getLayoutInflater().inflate(R.layout.activity_login,null);
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
    @OnClick(R.id.login_regist_tv)
    public void regist(){
        startActivity(new Intent(LoginActivity.this,RegistActivity.class));
    }
    @Override
    protected void initListeners() {

    }

    @Override
    protected void initData() {

    }
}
