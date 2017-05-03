package com.oujian.graduation.activity;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;
import com.oujian.graduation.R;
import com.oujian.graduation.base.BaseActivity;
import com.oujian.graduation.common.MyContext;
import com.oujian.graduation.net.RetrofitClient;
import com.oujian.graduation.net.base.BaseSubscriber;
import com.oujian.graduation.net.base.ExceptionHandle;
import com.oujian.graduation.net.entity.LoginEntity;
import com.oujian.graduation.net.req.ModifyInfoReq;
import com.oujian.graduation.net.res.BaseResponse;
import com.oujian.graduation.net.res.BaseResult;
import com.oujian.graduation.utils.GildeUtils;
import com.oujian.graduation.utils.ToastUtils;
import com.oujian.graduation.view.CircleImageView;
import com.oujian.graduation.view.ClearEditText;

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
    @Bind(R.id.modify_phone_tv)
    TextView mPhone;
    @Bind(R.id.modified_info_nick_et)
    ClearEditText mNickNameEt;

    private String mNickName;
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
        //ModelUtils.setDebugModel(true);
        if(MyContext.getInstance().getUserId() != null){
            mPhone.setText(MyContext.getInstance().getUserInfo().getAccount());
            mNickNameEt.setText(MyContext.getInstance().getUserInfo().getNickName());
        }
    }
    @Override
    protected void initListeners() {

    }
    @OnClick(R.id.modify_photo_ll)
    public void modifyPhoto(){
        ToastUtils.showToast(ModifiedInfoActivity.this,"暂时不支持更改头像");
//        RxGalleryFinalApi.openRadioSelectImage(ModifiedInfoActivity.this, new RxBusResultSubscriber<ImageRadioResultEvent>() {
//            @Override
//            protected void onEvent(ImageRadioResultEvent imageRadioResultEvent) throws Exception {
//                Logger.i("剪裁OK，图片路径:"+imageRadioResultEvent.getResult().getCropPath());
//                GildeUtils.imageLoader(ModifiedInfoActivity.this,mPhoto,imageRadioResultEvent.getResult().getCropPath());
//                //同时如果需要上传在此发送请求
//            }
//        });
    }
    @OnClick(R.id.modify_sure_btn)
    public void modify(){
        //修改昵称
        mNickName = mNickNameEt.getText().toString();
        if(TextUtils.isEmpty(mNickName)){
            ToastUtils.showToast(ModifiedInfoActivity.this,"昵称不能为空");
            return;
        }
        //开始请求修改
        ModifyInfoReq req = new ModifyInfoReq();
        req.setAccount(MyContext.getInstance().getUserInfo().getAccount());
        req.setPassword(MyContext.getInstance().getUserInfo().getPassword());
        req.setNickName(mNickName);
        String json = new Gson().toJson(req);
        RetrofitClient.getInstance(ModifiedInfoActivity.this).createBaseApi().changeInfo(json, new BaseSubscriber<BaseResult>(ModifiedInfoActivity.this) {
            @Override
            public void onError(ExceptionHandle.ResponeThrowable e) {
                ToastUtils.showToast(ModifiedInfoActivity.this,"修改出错了，请重新修改");
            }

            @Override
            public void onNext(BaseResult baseResponse) {
                if(baseResponse.getRetCode() ==0){
                    //更改本地昵称
                    MyContext.getInstance().getUserInfo().setNickName(mNickName);
                    ToastUtils.showToast(ModifiedInfoActivity.this,"修改成功");
                    ModifiedInfoActivity.this.finish();
                }
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
