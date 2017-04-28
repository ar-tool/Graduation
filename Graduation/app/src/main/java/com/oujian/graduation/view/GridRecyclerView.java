package com.oujian.graduation.view;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import com.oujian.graduation.R;

/**
 * Created by yi on 2016/9/13.
 * 九宫格的recyclerView,需要重新recyclerView
 */
public class GridRecyclerView extends FrameLayout {

    private RecyclerView mRecyclerView;
    private GridLayoutManager mLayoutManager;
    private int mNumColums = 3;//默认4列

    public GridRecyclerView(Context context) {
        this(context,null);
    }

    public GridRecyclerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initViews();
    }

    public GridRecyclerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initViews();
    }

    private void initViews() {
        mRecyclerView = new RecyclerView(getContext());
        mRecyclerView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        int spacingInPixels = getResources().getDimensionPixelOffset(R.dimen.dimen_5dp);
        //设置间距
        mRecyclerView.addItemDecoration(new GridSpaceItemDecoration(spacingInPixels));
        setLayManager(mNumColums);
        addView(mRecyclerView);
    }
    public void setLayManager(int num){
        this.mNumColums = num;
        mLayoutManager = new GridLayoutManager(getContext(),mNumColums);
        mRecyclerView.setLayoutManager(mLayoutManager);
    }
    public void setAdapter(RecyclerView.Adapter adapter){
        mRecyclerView.setAdapter(adapter);
    }
}
