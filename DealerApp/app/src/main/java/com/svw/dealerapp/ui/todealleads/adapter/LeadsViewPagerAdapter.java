package com.svw.dealerapp.ui.todealleads.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.svw.dealerapp.mvpframe.listfragment.BaseListFragment;

import java.util.List;

/**
 * Created by qinshi on 4/28/2017.
 */
@Deprecated
public class LeadsViewPagerAdapter extends FragmentStatePagerAdapter {

    private List<BaseListFragment> fragmentList;

    public LeadsViewPagerAdapter(FragmentManager fm, List<BaseListFragment> fragmentList) {
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
