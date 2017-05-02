package com.oujian.graduation.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.oujian.graduation.R;
import com.oujian.graduation.base.BaseActivity;
import com.oujian.graduation.utils.ToastUtils;
import com.oujian.graduation.view.ClearEditText;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.Bind;
import butterknife.OnClick;

public class ChangePasswordActivity extends BaseActivity {

    @Bind(R.id.change_password_toolBar)
    Toolbar mToolbar;
    @Bind(R.id.change_current_pwd_et)
    ClearEditText mCurrentPwdEt;
    @Bind(R.id.change_new_pwd_et)
    ClearEditText mNewPasswordEt;
    @Bind(R.id.change_again_pwd_et)
    ClearEditText mAgainPaswordEt;
    @Bind(R.id.change_pwd_btn)
    Button mChangeBtn;

    private String mCurrentPassword;
    private String mNewPassword;
    private String mAgainPassword;
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
    @OnClick(R.id.change_pwd_btn)
    public void changePassword(){
        mCurrentPassword = mCurrentPwdEt.getText().toString();
        if (TextUtils.isEmpty(mCurrentPassword)) {
            Toast.makeText(ChangePasswordActivity.this, R.string.tips4, Toast.LENGTH_SHORT).show();
            return;
        }
        mNewPassword = mNewPasswordEt.getText().toString();
        if (TextUtils.isEmpty(mNewPassword)) {
            Toast.makeText(ChangePasswordActivity.this, R.string.tips3, Toast.LENGTH_SHORT).show();
            return;
        }
        mAgainPassword = mAgainPaswordEt.getText().toString();
        if (TextUtils.isEmpty(mNewPassword)) {
            Toast.makeText(ChangePasswordActivity.this, R.string.tips2, Toast.LENGTH_SHORT).show();
            return;
        }
        if(mNewPassword.equals(mAgainPassword) == false){
            ToastUtils.showToast(ChangePasswordActivity.this,getString(R.string.regist_pwd) );
            mNewPasswordEt.setText("");
            mAgainPaswordEt.setText("");
            return;
        }
        Pattern p = Pattern.compile("^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,18}$");
        Matcher m = p.matcher(mNewPassword);
        if(false == m.matches()){
            Toast.makeText(ChangePasswordActivity.this, R.string.tips1, Toast.LENGTH_SHORT).show();
            return;
        }
        //开始发送请求
    }
    @Override
    protected void initData() {

    }
}
