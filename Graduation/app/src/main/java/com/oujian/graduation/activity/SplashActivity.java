package com.oujian.graduation.activity;

import android.content.Intent;
import android.view.View;

import com.oujian.graduation.R;
import com.oujian.graduation.base.BaseActivity;
import com.oujian.graduation.utils.RxHelper;
import com.oujian.graduation.view.SimpleButton;

import butterknife.Bind;
import butterknife.OnClick;
import rx.Subscriber;

/**
 * Splash页面
 */
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
        //如果已经登录过，这里利用保存的账号和密码，进行隐蔽登录获取个人信息

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
