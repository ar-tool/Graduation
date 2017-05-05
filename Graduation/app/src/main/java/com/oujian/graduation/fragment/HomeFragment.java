package com.oujian.graduation.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.oujian.graduation.R;
import com.oujian.graduation.activity.LoginActivity;
import com.oujian.graduation.activity.NewsActivity;
import com.oujian.graduation.adpater.HomeAdapter;
import com.oujian.graduation.base.BaseFragment;
import com.oujian.graduation.net.RetrofitClient;
import com.oujian.graduation.net.base.BaseSubscriber;
import com.oujian.graduation.net.base.ExceptionHandle;
import com.oujian.graduation.net.entity.NewsEntity;
import com.oujian.graduation.net.req.GetTitleReq;
import com.oujian.graduation.net.res.BaseResponse;
import com.oujian.graduation.utils.ToastUtils;

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
    RecyclerView mRecylerView;
    @Bind(R.id.home_swipe_layout)
    SwipeRefreshLayout mSwipeRefreshLayout;
    @Bind(R.id.search_img)
    ImageView mSearchImg;
    @Bind(R.id.home_et)
    EditText mHomeEt;

    private HomeAdapter mAdapter;
    private String mTitleInput;
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
        mAdapter.setItemCick(new HomeAdapter.OnItemClick() {
            @Override
            public void onClick(NewsEntity newsEntity) {
                if (newsEntity == null) {
                    return;
                }
                Intent intent = new Intent(getActivity(), NewsActivity.class);
                intent.putExtra(NewsActivity.KEY_NEWS, newsEntity);
                startActivity(intent);
            }
        });
        LinearLayoutManager layoutManager =new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecylerView.setLayoutManager(layoutManager);
        mRecylerView.setAdapter(mAdapter);
    }

    @Override
    protected void initListeners() {
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
                loadData("");
            }
        });
        mSearchImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mTitleInput = mHomeEt.getText().toString();
                if (TextUtils.isEmpty(mTitleInput)) {
                    ToastUtils.showToast(getActivity(), mHomeEt.getHint().toString());
                    return;
                }
                loadData(mTitleInput);
            }
        });
    }

    private void loadData(String title){
        GetTitleReq req = new GetTitleReq();
        req.setTitle(title);
        String json = new Gson().toJson(req);
        //请求数据
        RetrofitClient.getInstance(getActivity()).createBaseApi().getNews(json,new BaseSubscriber<BaseResponse<List<NewsEntity>>>(getActivity()) {
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
    protected void initData() {
        //进入页面自动刷新
        mSwipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                mSwipeRefreshLayout.setRefreshing(true);
            }
        });
        loadData("");
    }
}
