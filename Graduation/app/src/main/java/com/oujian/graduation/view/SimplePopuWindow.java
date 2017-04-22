package com.oujian.graduation.view;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.PopupWindow;

import com.oujian.graduation.R;
import com.oujian.graduation.activity.LoginActivity;

/**
 * Created by DIY on 2017/4/22.
 */

public class SimplePopuWindow extends PopupWindow implements View.OnClickListener{
    private Activity mContext;
    private View window;
    private Button first,second,third;
    public SimplePopuWindow(Activity context){
        super(context);
        mContext = context;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        window = inflater.inflate(R.layout.popu_simple, null);
        first = (Button)window.findViewById(R.id.first);
        second = (Button)window.findViewById(R.id.second);
        third = (Button)window.findViewById(R.id.third);
        first.setOnClickListener(this);
        second.setOnClickListener(this);
        third.setOnClickListener(this);
        //设置SelectPicPopupWindow的View
        this.setContentView(window);
        //设置SelectPicPopupWindow弹出窗体的宽
        this.setWidth(dip2Px(80));
        //设置SelectPicPopupWindow弹出窗体的高
        this.setHeight(dip2Px(150));
        //设置SelectPicPopupWindow弹出窗体可点击
        this.setFocusable(true);
        //设置SelectPicPopupWindow弹出窗体动画效果
//        this.setAnimationStyle(R.style.AnimBottom);
        //实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(0x00000000);
        //设置SelectPicPopupWindow弹出窗体的背景
        this.setBackgroundDrawable(dw);
        this.setOutsideTouchable(true);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.first:
                mContext.startActivity(new Intent(mContext, LoginActivity.class));
                break;
            case R.id.second:
                mContext.startActivity(new Intent(mContext, LoginActivity.class));
                break;
            case R.id.third:
                mContext.startActivity(new Intent(mContext, LoginActivity.class));
                break;
        }
    }
    /*
    * converts dip to px
    */
    private int dip2Px(float dip) {
        return (int) (dip * mContext.getResources().getDisplayMetrics().density + 0.5f);
    }
}
