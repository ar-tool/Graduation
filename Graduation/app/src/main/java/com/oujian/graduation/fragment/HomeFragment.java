package com.oujian.graduation.fragment;


import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.oujian.graduation.R;
import com.oujian.graduation.adpater.HomePagerAdapter;
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
    @Bind(R.id.home_tabLayout)
    TabLayout mTabLayout;
    @Bind(R.id.home_viewPager)
    ViewPager mViewPager;
    private HomePagerAdapter mPagerAdapter;

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

        //使用适配器将ViewPager与Fragment绑定在一起
        mPagerAdapter = new HomePagerAdapter(getChildFragmentManager());
        mViewPager.setAdapter(mPagerAdapter);
        //将TabLayout与ViewPager绑定在一起
        mTabLayout.setupWithViewPager(mViewPager);
        //设置分割线
        LinearLayout linearLayout = (LinearLayout) mTabLayout.getChildAt(0);
        linearLayout.setShowDividers(LinearLayout.SHOW_DIVIDER_MIDDLE);
        linearLayout.setDividerDrawable(ContextCompat.getDrawable(getActivity(),
                R.drawable.divider_vertical));
        mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                Log.i("选中的tab",tab.getPosition()+"");
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    @Override
    protected void initListeners() {

    }

    @Override
    protected void initData() {

    }
}
