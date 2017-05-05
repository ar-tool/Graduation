package com.oujian.graduation.activity;

import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.oujian.graduation.R;
import com.oujian.graduation.base.BaseActivity;
import com.oujian.graduation.common.MyContext;
import com.oujian.graduation.net.RetrofitClient;
import com.oujian.graduation.net.base.BaseSubscriber;
import com.oujian.graduation.net.base.ExceptionHandle;
import com.oujian.graduation.net.req.PublishReq;
import com.oujian.graduation.net.res.BaseResult;
import com.oujian.graduation.utils.ToastUtils;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * 发布帖子
 */
public class PublishActivity extends BaseActivity {

    @Bind(R.id.publish_toolBar)
    Toolbar mToolbar;
    @Bind(R.id.input_content)
    EditText mContentEt;
    @Bind(R.id.publish_btn)
    Button mPublishBtn;
    private String mContent;
    @Override
    protected View getContentView() {
        View view = getLayoutInflater().inflate(R.layout.activity_publish, null);
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
    @OnClick(R.id.publish_btn)
    public void publish(){
        mContent = mContentEt.getText().toString();
        if (TextUtils.isEmpty(mContent)) {
            Toast.makeText(PublishActivity.this, R.string.publish_empty, Toast.LENGTH_SHORT).show();
            return;
        }
        //发帖请求
        PublishReq req = new PublishReq();
        req.setUserId(MyContext.getInstance().getUserId());
        req.setContent(mContent);
        String json = new Gson().toJson(req);
        RetrofitClient.getInstance(PublishActivity.this).createBaseApi().pushNote(json, new BaseSubscriber<BaseResult>(PublishActivity.this) {
            @Override
            public void onError(ExceptionHandle.ResponeThrowable e) {
                ToastUtils.showToast(PublishActivity.this,"请重新输入内容");
                mContentEt.setText("");
            }

            @Override
            public void onNext(BaseResult baseResponse) {
                if(baseResponse.getRetCode() == 0){
                    ToastUtils.showToast(PublishActivity.this,"发帖成功");
                    mContentEt.setText("");
                }
            }
        });
    }
    @Override
    protected void initData() {

    }
}