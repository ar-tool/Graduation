package com.oujian.graduation.fragment;


import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jude.rollviewpager.RollPagerView;
import com.jude.rollviewpager.hintview.ColorPointHintView;
import com.oujian.graduation.R;
import com.oujian.graduation.adpater.MainNewsAdapter;
import com.oujian.graduation.adpater.RollAdapter;
import com.oujian.graduation.base.BaseFragment;
import com.oujian.graduation.net.RetrofitClient;
import com.oujian.graduation.net.base.BaseSubscriber;
import com.oujian.graduation.net.base.ExceptionHandle;
import com.oujian.graduation.net.entity.NewsEntity;
import com.oujian.graduation.net.res.BaseResponse;
import com.oujian.graduation.utils.ToastUtils;

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
    @Bind(R.id.swipe_layout)
    SwipeRefreshLayout mSwipeRefreshLayout;

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
//        mAdapter.setItemCick(new MainNewsAdapter.OnItemClick() {
//            @Override
//            public void onClick(View view) {
//                ToastUtils.showToast(getActivity(),"点点");
//            }
//        });
        mSwipeRefreshLayout.setColorSchemeResources(R.color.colorAccent, R.color.colorPrimary, R.color.colorPrimaryDark);
        mRecylerView.addOnScrollListener(new RecyclerView.OnScrollListener(){
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                int topRowVerticalPosition =
                        (recyclerView == null || recyclerView.getChildCount() == 0) ? 0 : recyclerView.getChildAt(0).getTop();
                mSwipeRefreshLayout.setEnabled(topRowVerticalPosition >= 0);

            }
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }
        });
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
              loadData();
            }
        });
    }
    private void loadData(){
        //请求数据
        RetrofitClient.getInstance(getActivity()).createBaseApi().getNews(null,new BaseSubscriber<BaseResponse<List<NewsEntity>>>(getActivity()) {
            @Override
            public void onError(ExceptionHandle.ResponeThrowable e) {
                ToastUtils.showToast(getActivity(),"获取数据失败");
                mSwipeRefreshLayout.setRefreshing(false);
            }

            @Override
            public void onNext(BaseResponse<List<NewsEntity>> listBaseResponse) {
                if(listBaseResponse.getRetCode() == 0){
                    mAdapter.setDataList(listBaseResponse.getRetBody());
                }else {
                    ToastUtils.showToast(getActivity(),listBaseResponse.getRetMsg());
                }
                mSwipeRefreshLayout.setRefreshing(false);
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
        //进入页面自动刷新
        mSwipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                mSwipeRefreshLayout.setRefreshing(true);
            }
        });
        loadData();
    }
}
