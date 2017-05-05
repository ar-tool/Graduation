package com.oujian.graduation.view;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.oujian.graduation.R;
import com.oujian.graduation.activity.LoginActivity;
import com.oujian.graduation.activity.RobotChatActivity;

import static com.oujian.graduation.common.Constant.QQ_COMMUNICATE;
import static com.oujian.graduation.common.Constant.QQ_SERVICE;

/**
 * 主界面点击服务弹出框
 * Created by yi on 2017/4/22.
 */

public class SimplePopuWindow extends PopupWindow implements View.OnClickListener{
    private Activity mContext;
    private View window;
    private TextView first,second,third;
    public SimplePopuWindow(Activity context){
        super(context);
        mContext = context;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        window = inflater.inflate(R.layout.popu_simple, null);
        first = (TextView)window.findViewById(R.id.first);
        second = (TextView)window.findViewById(R.id.second);
        third = (TextView)window.findViewById(R.id.third);
        first.setOnClickListener(this);
        second.setOnClickListener(this);
        third.setOnClickListener(this);
        //设置SelectPicPopupWindow的View
        this.setContentView(window);
        //设置SelectPicPopupWindow弹出窗体的宽
        this.setWidth(dip2Px(60));
        //设置SelectPicPopupWindow弹出窗体的高
        this.setHeight(dip2Px(60));
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
                mContext.startActivity(new Intent(mContext, RobotChatActivity.class));
                break;
            case R.id.second:
                joinQQGroup(QQ_COMMUNICATE);
                break;
            case R.id.third:
                joinQQGroup(QQ_SERVICE);
                break;
        }
    }
    /*
    * converts dip to px
    */
    private int dip2Px(float dip) {
        return (int) (dip * mContext.getResources().getDisplayMetrics().density + 0.5f);
    }

    /****************
     *
     * 发起添加群流程。群号：测试群(635431728) 的 key 为： jy3tXhIxtjRLJYDee6O5-aToVuonPBMP
     * 调用 joinQQGroup(jy3tXhIxtjRLJYDee6O5-aToVuonPBMP) 即可发起手Q客户端申请加群 测试群(635431728)
     *
     * @param key 由官网生成的key
     * @return 返回true表示呼起手Q成功，返回fals表示呼起失败
     ******************/
    public boolean joinQQGroup(String key) {
        Intent intent = new Intent();
        intent.setData(Uri.parse("mqqopensdkapi://bizAgent/qm/qr?url=http%3A%2F%2Fqm.qq.com%2Fcgi-bin%2Fqm%2Fqr%3Ffrom%3Dapp%26p%3Dandroid%26k%3D" + key));
        // 此Flag可根据具体产品需要自定义，如设置，则在加群界面按返回，返回手Q主界面，不设置，按返回会返回到呼起产品界面    //intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        try {
            mContext.startActivity(intent);
            return true;
        } catch (Exception e) {
            // 未安装手Q或安装的版本不支持
            return false;
        }
    }

}
