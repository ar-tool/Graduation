package com.oujian.graduation.fragment;


import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.jude.rollviewpager.RollPagerView;
import com.jude.rollviewpager.adapter.LoopPagerAdapter;
import com.jude.rollviewpager.hintview.ColorPointHintView;
import com.oujian.graduation.R;
import com.oujian.graduation.activity.RobotChatActivity;
import com.oujian.graduation.adpater.MainNewsAdapter;
import com.oujian.graduation.adpater.MessageAdapter;
import com.oujian.graduation.adpater.RollAdapter;
import com.oujian.graduation.base.BaseFragment;
import com.oujian.graduation.utils.ToastUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * A simple {@link Fragment} subclass.
 */
public class MainFragment extends BaseFragment {
    private View mRootView ;
    @Bind(R.id.roll_view)
    RollPagerView mRollPagerView;
    @Bind(R.id.main_recyclerView)
    RecyclerView mRecylerView;
    @Bind(R.id.main_toolBar)
    Toolbar mToolbar;
    private MainNewsAdapter mAdapter;
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

        mAdapter = new MainNewsAdapter(getActivity());
        LinearLayoutManager layoutManager =new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecylerView.setLayoutManager(layoutManager);
        mRecylerView.setAdapter(mAdapter);
    }

    @Override
    protected void initListeners() {
        mAdapter.setItemCick(new MainNewsAdapter.OnItemClick() {
            @Override
            public void onClick(View view) {
                ToastUtils.showToast(getActivity(),"点点");
            }
        });
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
        List<String> strings = new ArrayList<>();
        strings.add("显示学校最新发布的新闻公告");
        strings.add("显示学校最新发布的新闻公告");
        strings.add("显示学校最新发布的新闻公告");
        strings.add("显示学校最新发布的新闻公告");
        mAdapter.setDataList(strings);
    }
}
