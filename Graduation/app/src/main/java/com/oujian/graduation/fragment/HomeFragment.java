package com.oujian.graduation.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.oujian.graduation.R;
import com.oujian.graduation.adpater.HomeAdapter;
import com.oujian.graduation.base.BaseFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends BaseFragment {
    private View mRootView ;
    @Bind(R.id.home_toolBar)
    Toolbar mToolbar;
    @Bind(R.id.home_recyclerView)
    RecyclerView mRecyclerView;
    @Bind(R.id.refrsh_layout)
    SwipeRefreshLayout mSwipeRefreshLayout;
    private HomeAdapter mAdapter;

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if(mRootView == null){
            mRootView = inflater.inflate(R.layout.fragment_home, container, false);
        }
        return mRootView;
    }

    @Override
    protected void initViews(View rootView) {
        mAdapter = new HomeAdapter(getActivity());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.setAdapter(mAdapter);

        mSwipeRefreshLayout.setProgressBackgroundColorSchemeResource(android.R.color.white);
        // 设置下拉进度的主题颜色
        mSwipeRefreshLayout.setColorSchemeResources(R.color.colorAccent, R.color.colorPrimary, R.color.colorPrimaryDark);
    }

    @Override
    protected void initListeners() {
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                //去请求更新
                //更新完停止刷新动画
                mSwipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    @Override
    protected void initData() {
        List<String> messages = new ArrayList<>();
        messages.add("消息1");
        messages.add("消息2");
        messages.add("消息3");
        messages.add("消息4");
        mAdapter.setDataList(messages);
    }
}
