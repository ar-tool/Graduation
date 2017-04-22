package com.oujian.graduation.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.oujian.graduation.R;
import com.oujian.graduation.base.BaseFragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends BaseFragment {
    private View mRootView ;

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

    }

    @Override
    protected void initListeners() {

    }

    @Override
    protected void initData() {

    }
}
