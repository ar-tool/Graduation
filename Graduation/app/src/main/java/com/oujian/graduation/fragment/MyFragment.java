package com.oujian.graduation.fragment;


import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.oujian.graduation.R;
import com.oujian.graduation.activity.LoginActivity;
import com.oujian.graduation.base.BaseFragment;
import com.oujian.graduation.common.MyContext;
import com.oujian.graduation.utils.PreferencesUtils;
import com.oujian.graduation.view.ExitDialog;

import butterknife.Bind;

/**
 * A simple {@link Fragment} subclass.
 */
public class MyFragment extends BaseFragment {

    private View mRootView;
    @Bind(R.id.my_rl_exit)
    RelativeLayout mExit;
    public MyFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if(mRootView == null){
            mRootView = inflater.inflate(R.layout.fragment_my, container, false);
        }
        return mRootView;
    }

    @Override
    protected void initViews(View rootView) {

    }

    @Override
    protected void initListeners() {
        mExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getExitDialog();
            }
        });
    }

    @Override
    protected void initData() {

    }

    private void getExitDialog(){
        ExitDialog.Builder builder = new ExitDialog.Builder(getActivity());
        builder.setPositiveButton(new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                PreferencesUtils.removeSharedPreferences(getActivity(), LoginActivity.KEY_SHARE_PASSWORD);
                PreferencesUtils.removeSharedPreferences(getActivity(), LoginActivity.KEY_SHARE_SAVE_PASSWORD);
                //设置你的操作事项
                MyContext.getInstance().resetUserInfo();//重置个人信息
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                startActivity(intent);
            }
        });
        builder.setNegativeButton(new android.content.DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        builder.create().show();
    }
}
