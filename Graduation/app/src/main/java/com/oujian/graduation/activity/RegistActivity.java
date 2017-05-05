package com.oujian.graduation.activity;

import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;

import com.google.gson.Gson;
import com.oujian.graduation.R;
import com.oujian.graduation.base.BaseActivity;
import com.oujian.graduation.net.RetrofitClient;
import com.oujian.graduation.net.base.BaseSubscriber;
import com.oujian.graduation.net.base.ExceptionHandle;
import com.oujian.graduation.net.req.RegistReq;
import com.oujian.graduation.net.res.BaseResult;
import com.oujian.graduation.utils.MD5Utils;
import com.oujian.graduation.utils.ToastUtils;
import com.oujian.graduation.view.ClearEditText;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * 注册
 * Created by yi on 2017/4/22.
 */

public class RegistActivity extends BaseActivity {

    @Bind(R.id.regist_toolBar)
    Toolbar mToolbar;
    @Bind(R.id.regist_account_et)
    ClearEditText mAccountEt;
    @Bind(R.id.regist_againpwd_et)
    ClearEditText mPasswordAgainEt;
    @Bind(R.id.regist_password_et)
    ClearEditText mPasswordEt;

    private String mAccount;
    private String mPassword;
    private String mAgainPassword;
    @Override
    protected View getContentView() {
        View view =getLayoutInflater().inflate(R.layout.activity_regist,null);
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
    @OnClick(R.id.regist_btn)
    public void regist(){
        mAccount = mAccountEt.getText().toString();
        mPassword = mPasswordEt.getText().toString();
        mAgainPassword = mPasswordAgainEt.getText().toString();
        if(TextUtils.isEmpty(mAccount)||TextUtils.isEmpty(mPassword)||TextUtils.isEmpty(mAgainPassword)){
            ToastUtils.showToast(RegistActivity.this,getString(R.string.regist_tips) );
            return;
        }
        if(mPassword.equals(mAgainPassword) == false){
            ToastUtils.showToast(RegistActivity.this,getString(R.string.regist_pwd) );
            mPasswordEt.setText("");
            mPasswordAgainEt.setText("");
            return;
        }
        //开始注册
        RegistReq req = new RegistReq();
        req.setAccount(mAccount);
        req.setPassword(MD5Utils.encrypt(mPassword));
        String json = new Gson().toJson(req);
        RetrofitClient.getInstance(RegistActivity.this).createBaseApi().regist(json, new BaseSubscriber<BaseResult>(RegistActivity.this) {
            @Override
            public void onError(ExceptionHandle.ResponeThrowable e) {
                ToastUtils.showToast(RegistActivity.this,getString(R.string.regist_fail));
            }

            @Override
            public void onNext(BaseResult baseResult) {
               if(null !=baseResult &&baseResult.getRetCode() == 0){
                  RegistActivity.this.finish();
               }else {
                   ToastUtils.showToast(RegistActivity.this,baseResult.getRetMsg());
               }
            }
        });
    }
    @Override
    protected void initData() {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
