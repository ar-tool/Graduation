package com.oujian.graduation.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.View;

import butterknife.ButterKnife;

/**
 * Created by yi on 2017-03-20.
 */

public abstract class BaseFragment extends Fragment {
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init(view);
    }
    protected void init(View rootView) {
        ButterKnife.bind(this, rootView);
        initViews(rootView);
        initData();
        initListeners();
    }
    protected abstract void initViews(View rootView);

    protected abstract void initListeners();

    protected abstract void initData();
}
