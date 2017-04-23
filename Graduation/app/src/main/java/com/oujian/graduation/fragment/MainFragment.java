package com.oujian.graduation.fragment;


import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.jude.rollviewpager.RollPagerView;
import com.jude.rollviewpager.adapter.LoopPagerAdapter;
import com.jude.rollviewpager.hintview.ColorPointHintView;
import com.oujian.graduation.R;
import com.oujian.graduation.adpater.RollAdapter;
import com.oujian.graduation.base.BaseFragment;
import com.oujian.graduation.utils.ToastUtils;

import butterknife.Bind;

/**
 * A simple {@link Fragment} subclass.
 */
public class MainFragment extends BaseFragment {
    private View mRootView ;
    @Bind(R.id.roll_view)
    RollPagerView mRollPagerView;
    @Bind(R.id.ll_new)
    RelativeLayout rl_new;
    public MainFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if(mRootView == null){
            mRootView = inflater.inflate(R.layout.fragment_main, container, false);
        }
        return mRootView;
    }

    @Override
    protected void initViews(View rootView) {
        //设置播放时间间隔,为0则不播放
        mRollPagerView.setPlayDelay(2000);
        //图片滑动过程时间
        mRollPagerView.setAnimationDurtion(500);
        //设置适配器
        mRollPagerView.setAdapter(new RollAdapter(mRollPagerView));
        //设置指示器,这里使用颜色指示器
        mRollPagerView.setHintView(new ColorPointHintView(getActivity(), Color.RED, Color.WHITE));
    }

    @Override
    protected void initListeners() {
//        rl_new.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                ToastUtils.showToast(getActivity(),"点到了，该变色");
//            }
//        });
    }

    @Override
    public void onResume() {
        super.onResume();
        mRollPagerView.resume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mRollPagerView.pause();
    }

    @Override
    protected void initData() {

    }
}
