package com.oujian.graduation.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.github.jdsjlzx.interfaces.OnLoadMoreListener;
import com.github.jdsjlzx.interfaces.OnRefreshListener;
import com.github.jdsjlzx.recyclerview.LRecyclerView;
import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter;
import com.github.jdsjlzx.recyclerview.ProgressStyle;
import com.github.jdsjlzx.util.RecyclerViewStateUtils;
import com.github.jdsjlzx.view.LoadingFooter;
import com.oujian.graduation.R;
import com.oujian.graduation.activity.PublishActivity;
import com.oujian.graduation.adpater.FriendAdapter;
import com.oujian.graduation.adpater.HomePagerAdapter;
import com.oujian.graduation.base.BaseFragment;

import butterknife.Bind;


/**
 * A simple {@link Fragment} subclass.
 */
public class FriendFragment extends BaseFragment {
    private View mRootView ;
    @Bind(R.id.publish_add)
    ImageView mAdd;
    @Bind(R.id.friend_recyclerView)
    LRecyclerView mLRecyclerView;
    private HomePagerAdapter mPagerAdapter;
    private LinearLayoutManager mLinearLayoutManager;
    private FriendAdapter mAdapter;
    private LRecyclerViewAdapter mLRecyclerViewAdapter;

    public FriendFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if(mRootView == null){
            mRootView = inflater.inflate(R.layout.fragment_friend, container, false);
        }
        return mRootView;
    }

    @Override
    protected void initViews(View rootView) {
        //设置下拉样式
        mLRecyclerView.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader);
        mLinearLayoutManager = new LinearLayoutManager(getActivity());
        mLinearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mLRecyclerView.setLayoutManager(mLinearLayoutManager);
        mAdapter = new FriendAdapter(getActivity());
        mLRecyclerViewAdapter = new LRecyclerViewAdapter(mAdapter);
        mLRecyclerView.setAdapter(mLRecyclerViewAdapter);
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        if(isVisibleToUser){

        }
    }

    @Override
    protected void initListeners() {
      mAdd.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              //跳转到发布界面
            startActivity(new Intent(getActivity(), PublishActivity.class));
          }
      });

        mLRecyclerView.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
                //设置foot为正常样式
                RecyclerViewStateUtils.setFooterViewState(mLRecyclerView, LoadingFooter.State.Normal);
                mAdapter.clear();
                mLRecyclerViewAdapter.notifyDataSetChanged();
                loadData();
            }
        });
//        mLRecyclerView.setOnLoadMoreListener(new OnLoadMoreListener() {
//            @Override
//            public void onLoadMore() {
//                LoadingFooter.State state = RecyclerViewStateUtils.getFooterViewState(mLRecyclerView);
//                if(state == LoadingFooter.State.Loading) {//如果已经正在加载就取消
//                    return;
//                }
//                if(mHasNext ==true){
//                    //有下一页开始加载数据，将foot改为loading
//                    RecyclerViewStateUtils.setFooterViewState(NoticeListActivity.this, mLRecyclerView, REQUEST_COUNT, LoadingFooter.State.Loading, null);
//                    loadData();
//                }else {
//                    //没有下一页就是加载结束
//                    RecyclerViewStateUtils.setFooterViewState(NoticeListActivity.this, mLRecyclerView, REQUEST_COUNT, LoadingFooter.State.TheEnd, null);
//                }
//            }
//        });
        mLRecyclerView.setRefreshing(true);//初始化刷新一下,获取数据
    }

    /**
     * 加载数据
     */
    private void loadData() {
        //  获得数据
        mLRecyclerView.refreshComplete();
//        if(data != null){
//            if(data.noticeList == null||data.noticeList.size() == 0 ){
//                mNoMessageBg.setVisibility(View.VISIBLE);
//            }else {
//                mNoMessageBg.setVisibility(View.GONE);
//                mAdapter.setDataList(data.noticeList);
//                mAdapter.notifyDataSetChanged();
//                RecyclerViewStateUtils.setFooterViewState(mLRecyclerView, LoadingFooter.State.Normal);//将foot恢复正常
//                mLRecyclerViewAdapter.notifyDataSetChanged();
//                mHasNext = data.isNext;
//            }
//        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

    }

    @Override
    protected void initData() {

    }
}
