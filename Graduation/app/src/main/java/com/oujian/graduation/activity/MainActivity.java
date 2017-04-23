package com.oujian.graduation.activity;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentTabHost;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.oujian.graduation.R;
import com.oujian.graduation.base.BaseActivity;
import com.oujian.graduation.common.MyContext;
import com.oujian.graduation.entity.TabInfo;
import com.oujian.graduation.fragment.FriendFragment;
import com.oujian.graduation.fragment.HomeFragment;
import com.oujian.graduation.fragment.MainFragment;
import com.oujian.graduation.fragment.MyFragment;
import com.oujian.graduation.fragment.ServiceFragment;
import com.oujian.graduation.utils.ToastUtils;
import com.oujian.graduation.view.SimplePopuWindow;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

public class MainActivity extends BaseActivity {
    private final String TAG = this.getClass().getSimpleName();
    @Bind(R.id.tabhost_main)
    FragmentTabHost mTabHost;

    private static final int EXIT_APP = 1;
    private static final int TWO_SECOND = 2000;
    private static final int HOME_TAG = 0;
    private static final int FRIEND_TAG = HOME_TAG + 1;
    private static final int MAIN_TAG = FRIEND_TAG + 1;
    private static final int SERVICE_TAG = MAIN_TAG + 1;
    private static final int MY_TAG = SERVICE_TAG + 1;
    private static final String TAG_HOME = "Home";
    private static final String TAG_FRIEND = "Friend";
    private static final String TAG_MY = "My";
    private static final String TAG_MAIN = "Main";
    private static final String TAG_SERVICE = "Service";

    //第一次点击退出时系统时间
    private long mExitTime;

    @Override
    protected View getContentView() {
        View view =getLayoutInflater().inflate(R.layout.activity_main,null);
        return view;
    }
    //两次点击退出应用
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if ((System.currentTimeMillis() - mExitTime) > TWO_SECOND) {
                mHandler.sendEmptyMessage(EXIT_APP);
                mExitTime = System.currentTimeMillis();
            } else {
                finish();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
    /**
     * 由于使用了singleTask模式,需要在这里处理intent传值
     * @param intent
     */
    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        //getIntent().putExtras(intent);这是更新activity中fragment的getIntent
    }
    @Override
    protected void initViews() {

        mHandler = new MyHandler(MainActivity.this);
        mTabHost.setup(MainActivity.this, getSupportFragmentManager(), R.id.main_content);

        mTabHost.clearAllTabs();
        List<TabInfo> tabInfos = new ArrayList<>();
        TabInfo info1 = new TabInfo();
        info1.clss = HomeFragment.class;
        info1.drawableId =R.drawable.tab_publicity ;
        info1.label = "6";
        info1.tag = TAG_HOME;
        TabInfo info2 = new TabInfo();
        info2.clss = FriendFragment.class;
        info2.drawableId = R.drawable.tab_society ;
        info2.label = "5";
        info2.tag = TAG_FRIEND;
        TabInfo info3 = new TabInfo();
        info3.clss = MainFragment.class;
        info3.drawableId = R.drawable.tab_main ;
        info3.label = "4";
        info3.tag = TAG_MAIN;
        TabInfo info4 = new TabInfo();
        info4.clss = ServiceFragment.class;
        info4.drawableId = R.drawable.tab_service ;
        info4.label = "3";
        info4.tag = TAG_SERVICE;
        TabInfo info5 = new TabInfo();
        info5.clss = MyFragment.class;
        info5.drawableId =R.drawable.tab_my  ;
        info5.label = "1";
        info5.tag = TAG_MY;
        tabInfos.add(info1);
        tabInfos.add(info2);
        tabInfos.add(info3);
        tabInfos.add(info4);
        tabInfos.add(info5);
        for (int i =0;i<5;i++) {
            LinearLayout linearLayout = (LinearLayout) getLayoutInflater().inflate(R.layout.main_tab,null);
            TabInfo tabInfo = tabInfos.get(i);
            ImageView tabImg = (ImageView) linearLayout.findViewById(R.id.main_img);
            tabImg.setImageResource(tabInfo.drawableId);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            layoutParams.gravity = Gravity.BOTTOM;
            layoutParams.weight = 1.0f;
            linearLayout.setLayoutParams(layoutParams);
            //关键语句，添加tabhost的内容
            mTabHost.addTab(mTabHost.newTabSpec(tabInfo.tag).setIndicator(linearLayout), tabInfo.clss, null);
        }
    }

    @Override
    protected void initListeners() {
        //mTabHost.getTabWidget().setDividerDrawable(null);//去掉分割线
        //第三个设置为默认显示
        mTabHost.setCurrentTabByTag(TAG_MAIN);
        mTabHost.getTabWidget().getChildTabViewAt(SERVICE_TAG).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               showPopuWindow(mTabHost.getTabWidget().getChildTabViewAt(SERVICE_TAG));
            }
        });
    }

    @Override
    protected void initData() {

    }

    /**
     * 弹出popu
     * @param view
     */
    private void showPopuWindow(View view){
        SimplePopuWindow popuWindow = new SimplePopuWindow(this);
        popuWindow.showAsDropDown(view,20,0);
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        mHandler.removeCallbacksAndMessages(null);
    }

    private MyHandler mHandler ;
    static class MyHandler extends Handler {
        private final WeakReference<MainActivity> mActivity;

        public MyHandler(MainActivity activity) {
            mActivity = new WeakReference<MainActivity>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            if (mActivity.get() == null) {
                return;
            }
            switch (msg.what) {
                case EXIT_APP:
                    MyContext.getInstance().resetUserInfo();//重置个人信息
                    ToastUtils.showToast(mActivity.get(), mActivity.get().getString(R.string.exit_app));
                    break;
            }
        }
    }
}
