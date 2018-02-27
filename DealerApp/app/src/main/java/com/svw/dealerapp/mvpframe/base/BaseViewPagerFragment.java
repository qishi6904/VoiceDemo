package com.svw.dealerapp.mvpframe.base;

import android.support.v4.view.ViewPager;

import com.svw.dealerapp.common.BaseFragment;
import com.svw.dealerapp.mvpframe.listfragment.BaseListFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by qinshi on 9/12/2017.
 */

public class BaseViewPagerFragment extends BaseFragment {

    protected ViewPager viewPager;
    protected List<BaseFragment> fragmentList = new ArrayList<>();

    public BaseFragment getCurrentFragment(){
        if(null != viewPager) {
            int currentItem = viewPager.getCurrentItem();
            if(currentItem < fragmentList.size()){
                BaseFragment baseFragment =  fragmentList.get(currentItem);
                if(baseFragment instanceof BaseViewPagerFragment) {
                    BaseViewPagerFragment viewPagerFragment = (BaseViewPagerFragment) baseFragment;
                    viewPagerFragment.getCurrentFragment();
                }else {
                    return baseFragment;
                }
            }
        }
        return null;
    }
}
