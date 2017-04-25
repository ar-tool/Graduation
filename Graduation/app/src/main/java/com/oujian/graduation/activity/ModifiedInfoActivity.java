package com.oujian.graduation.activity;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;

import com.oujian.graduation.R;
import com.oujian.graduation.base.BaseActivity;
import com.oujian.graduation.utils.GildeUtils;
import com.oujian.graduation.utils.ToastUtils;
import com.oujian.graduation.view.CircleImageView;

import butterknife.Bind;
import butterknife.OnClick;
import cn.finalteam.rxgalleryfinal.RxGalleryFinalApi;
import cn.finalteam.rxgalleryfinal.rxbus.RxBusResultSubscriber;
import cn.finalteam.rxgalleryfinal.rxbus.event.ImageRadioResultEvent;
import cn.finalteam.rxgalleryfinal.utils.Logger;
import cn.finalteam.rxgalleryfinal.utils.MediaScanner;
import cn.finalteam.rxgalleryfinal.utils.ModelUtils;

public class ModifiedInfoActivity extends BaseActivity {

    @Bind(R.id.modified_info_toolBar)
    Toolbar mToolbar;
    @Bind(R.id.photo_my)
    CircleImageView mPhoto;
    @Override
    protected View getContentView() {
        View view =getLayoutInflater().inflate(R.layout.activity_modified_info,null);
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
        //手动打开日志
        ModelUtils.setDebugModel(true);
    }
    @Override
    protected void initListeners() {

    }
    @OnClick(R.id.modify_photo_ll)
    public void modifyPhoto(){
        RxGalleryFinalApi.openRadioSelectImage(ModifiedInfoActivity.this, new RxBusResultSubscriber<ImageRadioResultEvent>() {
            @Override
            protected void onEvent(ImageRadioResultEvent imageRadioResultEvent) throws Exception {
                Logger.i("剪裁OK，图片路径:"+imageRadioResultEvent.getResult().getCropPath());
                GildeUtils.imageLoader(ModifiedInfoActivity.this,mPhoto,imageRadioResultEvent.getResult().getCropPath());
                //同时如果需要上传在此发送请求
            }
        });
    }
    @Override
    protected void initData() {

    }
    //拍照回调
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RxGalleryFinalApi.TAKE_IMAGE_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            //刷新相册数据库
            RxGalleryFinalApi.openZKCameraForResult(ModifiedInfoActivity.this, new MediaScanner.ScanCallback() {
                @Override
                public void onScanCompleted(String[] strings) {
                }
            });
        }
    }
}
