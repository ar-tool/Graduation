package com.oujian.graduation.activity;

import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.oujian.graduation.R;
import com.oujian.graduation.base.BaseActivity;
import com.oujian.graduation.utils.ToastUtils;
import com.oujian.graduation.view.TimeCountButton;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by DIY on 2017/4/22.
 */

public class RegistActivity extends BaseActivity {

    @Bind(R.id.regist_toolBar)
    Toolbar mToolbar;
    @Bind(R.id.regist_account_et)
    EditText mAccountEt;
    @Bind(R.id.val_code_timeBtn)
    TimeCountButton mCountButton;
    @Override
    protected View getContentView() {
        View view =getLayoutInflater().inflate(R.layout.activity_regist,null);
        return view;
    }

    @Override
    protected void initViews() {
        mCountButton.onCreate();
        mToolbar.setTitle("");
        setSupportActionBar(mToolbar);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
    @OnClick(R.id.val_code_timeBtn)
    public void onGetValClick(){
        String mobile = mAccountEt.getText().toString();
        if (TextUtils.isEmpty(mobile)) {
            ToastUtils.showShort(RegistActivity.this, mAccountEt.getHint().toString());
            return;
        }
        mCountButton.startCountDown();
    }
    @Override
    protected void initListeners() {

    }

    @Override
    protected void initData() {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mCountButton.onDestory();
    }
}
