package com.svw.dealerapp.ui.resource.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.svw.dealerapp.common.BaseFragment;
import com.svw.dealerapp.mvpframe.listfragment.BaseListFragment;

import java.util.List;

/**
 * Created by qinshi on 5/4/2017.
 */

public class ResourceViewPagerAdapter extends FragmentStatePagerAdapter {

    private List<BaseFragment> fragmentList;

    public ResourceViewPagerAdapter(FragmentManager fm, List<BaseFragment> fragmentList) {
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
