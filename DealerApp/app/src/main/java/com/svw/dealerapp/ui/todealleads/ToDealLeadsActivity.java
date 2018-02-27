package com.svw.dealerapp.ui.todealleads;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;

import com.svw.dealerapp.R;
import com.svw.dealerapp.common.BaseActivity;
import com.svw.dealerapp.ui.todealleads.adapter.LeadsViewPagerAdapter;
import com.svw.dealerapp.ui.entity.NavBarParamsEntity;
import com.svw.dealerapp.ui.todealleads.fragment.ArriveShopFragment;
import com.svw.dealerapp.ui.todealleads.fragment.ECommerceOrderFragment;
import com.svw.dealerapp.ui.todealleads.fragment.NetPhoneLeadsFragment;
import com.svw.dealerapp.mvpframe.listfragment.BaseListFragment;
import com.svw.dealerapp.ui.widget.NoScrollViewPager;
import com.svw.dealerapp.ui.widget.NavBar;
import com.svw.dealerapp.ui.widget.NavTab;
import com.svw.dealerapp.util.DensityUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by qinshi on 4/27/2017.
 * 待处理线索Activity
 */
@Deprecated
public class ToDealLeadsActivity extends BaseActivity {

    private NavBar snbTabBar;
    private NoScrollViewPager vpLeadsViewPager;
    private ImageView ivBackView;
    private List<BaseListFragment> fragmentList = new ArrayList<>();

    private int firstShowFragmentIndex = 1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_to_deal_leads);
        assignViews();
    }

    private void assignViews() {
        snbTabBar = (NavBar) findViewById(R.id.snb_tab_bar);
        vpLeadsViewPager = (NoScrollViewPager) findViewById(R.id.vp_leads_view_pager);
        ivBackView = (ImageView) findViewById(R.id.iv_back_view);
    }

    @Override
    public void initView() {
        String[] titles = new String[]{"电商订单", "到店", "网电线索"};
        String[] tips = new String[]{"8", "3", "16"};
        String[] urls = new String[]{"", "", ""};
        int[] ids = new int[]{R.mipmap.ic_leads_order_tab, R.mipmap.ic_leads_arrive_shop_tab, R.mipmap.ic_leads_net_phone_tab};
        snbTabBar.bindViewPager(vpLeadsViewPager, new NavBarParamsEntity(
                titles.length,
                3,
                titles,
                tips,
                urls,
                ids,
                0,
                DensityUtil.dp2px(this, 56),
                0,
                DensityUtil.dp2px(this, 2),
                DensityUtil.dp2px(this, 20),
                DensityUtil.dp2px(this, 20),
                12,
                Color.BLACK,
                10,
                Color.WHITE,
                DensityUtil.dp2px(this, 10),
                null,
                Color.BLACK,
                true,
                true,
                true
        ), fragmentList);

        fragmentList.add(new ECommerceOrderFragment());
        fragmentList.add(new ArriveShopFragment());
        fragmentList.add(new NetPhoneLeadsFragment());
        LeadsViewPagerAdapter viewPagerAdapter = new LeadsViewPagerAdapter(getSupportFragmentManager(), fragmentList);
        vpLeadsViewPager.setOffscreenPageLimit(3);
        if (firstShowFragmentIndex < fragmentList.size()) {
            fragmentList.get(firstShowFragmentIndex).setIsFirstShowFragment(true);
        }
        vpLeadsViewPager.setAdapter(viewPagerAdapter);
        vpLeadsViewPager.setCurrentItem(1);

        snbTabBar.setOnTabChangeListener(new NavBar.OnTabChangeListener() {

            @Override
            public void onSelect(NavTab selectTab, int selectPosition) {
                if (selectPosition < fragmentList.size()) {
                    BaseListFragment selectFragment = fragmentList.get(selectPosition);
                    selectFragment.showLoadingView();
                }
            }

            @Override
            public void onCancel(NavTab cancelTab, int cancelPosition) {
                if (cancelPosition < fragmentList.size()) {
                    BaseListFragment cancelFragment = fragmentList.get(cancelPosition);
                    cancelFragment.hideLoadingView();
                }
            }
        });

        ivBackView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_back_view:
                finish();
                break;
        }
    }
}
