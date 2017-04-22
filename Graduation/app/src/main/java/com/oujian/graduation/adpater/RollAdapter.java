package com.oujian.graduation.adpater;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.jude.rollviewpager.RollPagerView;
import com.jude.rollviewpager.adapter.LoopPagerAdapter;
import com.oujian.graduation.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yi on 2017-03-21.
 */

public class RollAdapter extends LoopPagerAdapter {
    private int[] imgs = {R.drawable.resource_bg, R.drawable.resource_bg, R.drawable.resource_bg, R.drawable.resource_bg, R.drawable.resource_bg,};  // 本地图片
    private List<String> imgUrls = new ArrayList<>();

    public void setData(List<String> urls){
        imgUrls.clear();
        imgUrls.addAll(urls);
    }
    public RollAdapter(RollPagerView viewPager) {
        super(viewPager);
    }

    @Override
    public View getView(ViewGroup container, int position) {
        ImageView view = new ImageView(container.getContext());
        view.setImageResource(imgs[position]);
        view.setScaleType(ImageView.ScaleType.CENTER_CROP);
        view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        return view;
    }

    @Override
    protected int getRealCount() {
        return imgs.length;
    }
}
