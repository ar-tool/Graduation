package com.oujian.graduation.view;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.AppCompatButton;
import android.util.AttributeSet;
import android.widget.Button;


import com.oujian.graduation.common.Constant;

import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by yi on 2016/4/29.
 * 带倒计时功能的按钮
 */
public class TimeCountButton extends AppCompatButton {
    private long time;
    private long length = 60 * 1000;//60秒
    private final static int TIMER_TASK = 1;
    private String textafter = "秒后重新获取";
    private String textbefore = "获取验证码";
    private final String TIME = "time";
    private final String CTIME = "ctime";
    //时间类
    private Timer timer;
    //任务类
    private TimerTask timerTask;

    public TimeCountButton(Context context) {
        super(context);
    }

    public TimeCountButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public TimeCountButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /**
     * 开启倒计时的方法，外部调用
     */
    public void startCountDown() {
        initTimer();
        this.setText(time / 1000 + textafter);
        this.setTextSize(13);
        //无法点击
        this.setEnabled(false);
        //设置0延迟操作,每个周期为1秒
        timer.schedule(timerTask, 0, 1000);
    }

    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case TIMER_TASK: {
                    TimeCountButton.this.setText(time / 1000 + textafter);
                    time -= 1000;
                    if (time < 0) {
                        //倒计时结束可以点击
                        TimeCountButton.this.setEnabled(true);
                        TimeCountButton.this.setText(textbefore);
                        //清空
                        clearTimer();
                    }
                }
                break;
            }

        }
    };

    private void clearTimer() {
        if (timerTask != null) {
            timerTask.cancel();
            timerTask = null;
        }
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
    }

    public void initTimer() {
        time = length;
        timer = new Timer();
        timerTask = new TimerTask() {
            @Override
            public void run() {
                mHandler.sendEmptyMessage(TIMER_TASK);
            }
        };
    }

    /**
     * 设置点击之前文本
     */
    public TimeCountButton setTextBefore(String before) {
        this.textbefore = before;
        this.setText(textbefore);
        return this;
    }

    /**
     * 设置点击之后的文本
     */
    public TimeCountButton setTextAfter(String after) {
        this.textafter = after;
        this.setText(textbefore);
        return this;
    }

    /**
     * 设置倒计时时间长度
     */
    public TimeCountButton setTimeLength(long timeLength) {
        this.length = timeLength;
        return this;
    }


    /**
     * 与Activity同步
     */
    public void onCreate() {
        if (Constant.map == null) {
            return;
        }
        //表明上一次未完成的计时
        if (Constant.map.size() <= 0) {
            return;
        }
        long time = System.currentTimeMillis() - Constant.map.get(CTIME)
                - Constant.map.get(TIME);
        Constant.map.clear();
        if (time > 0) {
            return;
        } else {
            initTimer();
            this.time = Math.abs(time);
            timer.schedule(timerTask, 0, 1000);
            this.setText(time + textafter);
            this.setEnabled(false);
        }
    }

    public void onDestory() {
        if (Constant.map == null) {
            Constant.map = new HashMap<String, Long>();
            Constant.map.put(TIME, time);
            Constant.map.put(CTIME, System.currentTimeMillis());
            clearTimer();
        }
        mHandler.removeCallbacksAndMessages(null);
    }
}
