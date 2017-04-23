package com.oujian.graduation.utils;
import android.app.Activity;
import android.content.Context;
import android.util.DisplayMetrics;
import android.view.View;

public class ScreenUtil {
    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }


    public static int getScreenWidth(Activity activity){
        DisplayMetrics dm = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
        int screenWidth = dm.widthPixels;
        return screenWidth;
    }

    public static int getScreenHeigth(Activity activity){
        DisplayMetrics dm = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
        int screenHeigh = dm.heightPixels;
        return screenHeigh;
    }


    public static int measureHeight(View view){

        int w = View.MeasureSpec.makeMeasureSpec(0,View.MeasureSpec.UNSPECIFIED);
        int h = View.MeasureSpec.makeMeasureSpec(0,View.MeasureSpec.UNSPECIFIED);
        view.measure(w, h);
        int height =view.getMeasuredHeight();
        return height;
    }

    public static int measureWidth(View view){
        int w = View.MeasureSpec.makeMeasureSpec(0,View.MeasureSpec.UNSPECIFIED);
        int h = View.MeasureSpec.makeMeasureSpec(0,View.MeasureSpec.UNSPECIFIED);
        view.measure(w, h);
        int width =view.getMeasuredWidth();
        return width;
    }


    /**
     * 根据比例计算控件宽度
     * @param owidth    设计宽度
     * @return          适配宽度
     *                  标准宽度    720
     */
    public int getNewWidth(int owidth ,Activity activity ){
        int width = getScreenWidth(activity);
        int wprop = owidth / 720 ; //宽度基于标准的比例
        return width * wprop;
    }

    /**
     * 根据比例计算控件高度
     * @param owidth    设计宽度
     * @param oheight   设计高度
     * @return          适配高度
     */
    public int getNewHeight(int owidth,int oheight,Activity activity){
        int width = getScreenWidth(activity);
        int height = getScreenHeigth(activity);

        int wprop = owidth / 720 ; //宽度基于标准的比例
        int hprop = oheight/owidth; //高度基于宽度的比例
        int nwidth = width * wprop;
        return nwidth*hprop;
    }
    /**
     * 根据比例计算控件距离父控件边距
     * @param opadding 规范边距
     * @param owidth   规范宽度
     * @param width    屏幕宽度
     * @return         适配边距
     */
    public int getPadding(int opadding,int owidth,int width){
        //     30/720
//			float prop = opadding/720;
        return width*(opadding/owidth);
    }


}
