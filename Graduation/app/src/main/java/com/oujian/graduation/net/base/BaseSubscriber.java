package com.oujian.graduation.net.base;

import android.content.Context;
import android.content.DialogInterface;
import android.widget.Toast;

import com.oujian.graduation.utils.NetworkUtils;
import com.oujian.graduation.view.HttpDialog;

import java.lang.ref.WeakReference;

import rx.Subscriber;

/**
 * BaseSubscriber
 * Created by yi on 2017/3/21.
 */
public abstract class BaseSubscriber<T> extends Subscriber<T> {

    private boolean isNeedCahe;
    //    弱引用反正内存泄露
    private WeakReference<Context> mActivity;
    //    是否能取消请求，默认为false
    private boolean cancel =false;
    //    加载框可自己定义
    private HttpDialog dialog;

    public BaseSubscriber(Context context) {
        this.mActivity = new WeakReference<>(context);
        initProgressDialog();
    }

    @Override
    public void onError(Throwable e) {

        if(e instanceof ExceptionHandle.ResponeThrowable){
            onError((ExceptionHandle.ResponeThrowable)e);
        } else {
            onError(new ExceptionHandle.ResponeThrowable(e, ExceptionHandle.ERROR.UNKNOWN));
        }
        dismissProgressDialog();
    }


    @Override
    public void onStart() {
        super.onStart();
        Context context = mActivity.get();
        // if  NetworkAvailable no !   must to call onCompleted
        if (!NetworkUtils.isNetworkAvailable(context)) {
            Toast.makeText(context, "请检查网络情况", Toast.LENGTH_SHORT).show();
            onCompleted();
        }
        showProgressDialog();
    }

    @Override
    public void onCompleted() {
        // todo some common as  dismiss loadding
        dismissProgressDialog();
    }


    public abstract void onError(ExceptionHandle.ResponeThrowable e);

    /**
     * 初始化加载框
     */
    private void initProgressDialog() {
        Context context = mActivity.get();
        if (dialog == null && context != null) {
            HttpDialog.Builder builder = new HttpDialog.Builder(context);
            dialog = builder.create();
            dialog.setCancelable(cancel);
            if (cancel) {
                dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialogInterface) {
                        onCancelProgress();
                    }
                });
            }
        }
    }


    /**
     * 显示加载框
     */
    private void showProgressDialog() {
        Context context = mActivity.get();
        if (dialog == null || context == null) return;
        if (!dialog.isShowing()) {
            dialog.show();
        }
    }


    /**
     * 隐藏
     */
    private void dismissProgressDialog() {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
    }

    /**
     * 取消ProgressDialog的时候，取消对observable的订阅，同时也取消了http请求
     */
    public void onCancelProgress() {
        if (!this.isUnsubscribed()) {
            this.unsubscribe();
        }
    }
}
