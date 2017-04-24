package com.oujian.graduation.adpater;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.oujian.graduation.fragment.PublicityFragment;
import com.oujian.graduation.fragment.PublishFragment;

/**
 * Created by DIY on 2017/4/24.
 */

public class FriendPagerAdapter extends FragmentPagerAdapter {

    private String[] mTitles = new String[]{"关注", "话题", "推荐","发布"};

    public FriendPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 1) {
            return new PublicityFragment();
        } else if (position == 2) {
            return new PublicityFragment();
        }else if (position==3){
            return new PublishFragment();
        }
        return new PublicityFragment();
    }

    @Override
    public int getCount() {
        return mTitles.length;
    }

    //ViewPager与TabLayout绑定后，这里获取到PageTitle就是Tab的Text
    @Override
    public CharSequence getPageTitle(int position) {
        return mTitles[position];
    }
}
