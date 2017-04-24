package com.oujian.graduation.fragment;


import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.oujian.graduation.R;
import com.oujian.graduation.adpater.FriendPagerAdapter;
import com.oujian.graduation.base.BaseFragment;

import butterknife.Bind;

import static com.oujian.graduation.R.drawable.one;
import static com.oujian.graduation.R.drawable.two;

/**
 * A simple {@link Fragment} subclass.
 */
public class FriendFragment extends BaseFragment {
    private View mRootView ;
    @Bind(R.id.friend_tabLayout)
    TabLayout mTabLayout;
    @Bind(R.id.friend_viewPager)
    ViewPager mViewPager;
    private FriendPagerAdapter mPagerAdapter;

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

        //使用适配器将ViewPager与Fragment绑定在一起
        mPagerAdapter = new FriendPagerAdapter(getChildFragmentManager());
        mViewPager.setAdapter(mPagerAdapter);
        //将TabLayout与ViewPager绑定在一起
        mTabLayout.setupWithViewPager(mViewPager);
        //设置分割线
        LinearLayout linearLayout = (LinearLayout) mTabLayout.getChildAt(0);
        linearLayout.setShowDividers(LinearLayout.SHOW_DIVIDER_MIDDLE);
        linearLayout.setDividerDrawable(ContextCompat.getDrawable(getActivity(),
                R.drawable.divider_vertical));

    }

    @Override
    protected void initListeners() {

    }

    @Override
    protected void initData() {

    }
}
