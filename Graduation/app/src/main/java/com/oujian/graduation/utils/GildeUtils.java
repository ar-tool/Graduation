package com.oujian.graduation.utils;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.oujian.graduation.R;


/**
 * Created by yi on 2016/11/25.
 */

public class GildeUtils {
    /**
     * 加载网络图片
     * @param activity
     * @param img
     * @param url
     */
    public static void imageLoader(Activity activity, ImageView img, String url){
        Glide.with(activity)//这里会使glide与activity生命周期一致
                .load(url)//加载图片地址  网址或file文件地址
                .error(R.mipmap.ic_launcher)//图片加载错误显示的图片
                .thumbnail(0.3f)//防止图片过大加载时间过长先加载缩略图
                .centerCrop()//图片格式
                .diskCacheStrategy(DiskCacheStrategy.ALL) //缓存全尺寸的
                .into(img);//图片显示的ImageView

    }

    /**加载图片
     * @param fragment
     * @param img
     * @param url
     */
    public static void imageLoader(Fragment fragment, ImageView img, String url){
        Glide.with(fragment)//这里会使glide与fragment生命周期一致
                .load(url)//加载图片地址  网址或file文件地址
                .error(R.mipmap.ic_launcher)//图片加载错误显示的图片
                .thumbnail(0.3f)//防止图片过大加载时间过长先加载缩略图
                .centerCrop()//图片格式
                .diskCacheStrategy(DiskCacheStrategy.ALL) //缓存全尺寸的
                .into(img);//图片显示的ImageView

    }

    /**
     * 清理缓存
     * @param activity
     */
  public static void clear(final Activity activity){
      //清理内存缓存
      Glide.get(activity).clearMemory();
      //清理SD卡缓存
      new Thread(){
          @Override
          public void run() {
              super.run();
              Glide.get(activity).clearDiskCache();
          }
      }.start();
  }

}
