package com.svw.dealerapp.ui.main.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.svw.dealerapp.common.BaseFragment;

import java.util.List;

/**
 * Created by qinshi on 5/9/2017.
 */

public class MainViewPagerAdapter extends FragmentStatePagerAdapter {

    private List<BaseFragment> fragmentList;

    public MainViewPagerAdapter(FragmentManager fm, List<BaseFragment> fragmentList) {
        super(fm);
        this.fragmentList = fragmentList;
    }

    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position);
    }

    @Override
    public int getCount() {
        return null == fragmentList ? 0 : fragmentList.size();
    }
}
