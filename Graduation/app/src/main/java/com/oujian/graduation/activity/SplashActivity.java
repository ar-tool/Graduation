package com.oujian.graduation.activity;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.TextView;

import com.oujian.graduation.R;
import com.oujian.graduation.base.BaseActivity;
import com.oujian.graduation.utils.RxHelper;
import com.oujian.graduation.view.SimpleButton;

import java.lang.ref.WeakReference;

import butterknife.Bind;
import butterknife.OnClick;
import rx.Subscriber;

public class SplashActivity extends BaseActivity {
    private final String TAG = this.getClass().getSimpleName();
    //倒计时
    @Bind(R.id.splash_skip)
    SimpleButton mSkipButton;
    private boolean mIsSkip = false;
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
        RxHelper.countdown(3)
                .subscribe(new Subscriber<Integer>() {
                    @Override
                    public void onCompleted() {
                        _doSkip();
                    }

                    @Override
                    public void onError(Throwable e) {
                        _doSkip();
                    }

                    @Override
                    public void onNext(Integer integer) {
                        mSkipButton.setText("跳过 " + integer+"S");
                    }
                });
    }
    private void _doSkip() {
        if (!mIsSkip) {
            mIsSkip = true;
            finish();
            startActivity(new Intent(SplashActivity.this, MainActivity.class));
            overridePendingTransition(R.anim.hold, R.anim.zoom_in_exit);
        }
    }
    @Override
    public void onBackPressed() {
        // 不响应后退键
        return;
    }

    @OnClick(R.id.splash_skip)
    public void onClick() {
        _doSkip();
    }
}
