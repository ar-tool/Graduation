package com.oujian.graduation.base;

import android.app.Application;
import android.content.Context;

import com.oujian.graduation.common.MyContext;
import com.oujian.graduation.lis.GlidePauseOnScrollListener;
import com.oujian.graduation.utils.GlideImageLoader;

import cn.finalteam.galleryfinal.CoreConfig;
import cn.finalteam.galleryfinal.FunctionConfig;
import cn.finalteam.galleryfinal.GalleryFinal;
import cn.finalteam.galleryfinal.ImageLoader;
import cn.finalteam.galleryfinal.ThemeConfig;

/**
 * Created by yi on 2017-03-20.
 */

public class BaseApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        //这样可以获取context上下文
        MyContext.getInstance().init(getApplicationContext());

        //初始化galleryFinal
        initGalleryFinal(getApplicationContext());
    }

    private void initGalleryFinal(Context context) {
        //设置主题
        //ThemeConfig.CYAN
        ThemeConfig theme = new ThemeConfig.Builder()
                .build();
        //配置功能
        FunctionConfig functionConfig = new FunctionConfig.Builder()
                //配置相机
                .setEnableCamera(true)
                //开启编辑
                .setEnableEdit(true)
                //开启剪裁
                .setEnableCrop(true)
                //开启旋转
                .setEnableRotate(true)
                //裁剪正方形
                .setCropSquare(true)
                //开启预览
                .setEnablePreview(true)
                .build();

        //配置imageloader
        ImageLoader imageloader = new GlideImageLoader();
        CoreConfig coreConfig = new CoreConfig.Builder(context, imageloader, theme)
                //debug开关
//                .setDebug(BuildConfig.DEBUG)
                .setFunctionConfig(functionConfig)
                .setPauseOnScrollListener(new GlidePauseOnScrollListener(false,true))
                .build();
        GalleryFinal.init(coreConfig);
    }
}
