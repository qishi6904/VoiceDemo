package com.svw.dealerapp.ui.mine.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;

import com.svw.dealerapp.R;
import com.svw.dealerapp.common.BaseActivity;
import com.svw.dealerapp.common.BaseFragment;
import com.svw.dealerapp.ui.entity.NavBarParamsEntity;
import com.svw.dealerapp.ui.main.adapter.MainViewPagerAdapter;
import com.svw.dealerapp.ui.mine.fragment.ApproveCompleteFragment;
import com.svw.dealerapp.ui.mine.fragment.ApproveWaitFragment;
import com.svw.dealerapp.ui.widget.NavBar;
import com.svw.dealerapp.ui.widget.NavTab;
import com.svw.dealerapp.util.DensityUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by qinshi on 6/1/2017.
 */

public class ApproveActivity extends BaseActivity{

    private ImageView ivBack;
    private NavBar nbNavBar;
    private ViewPager vpViewPager;
    private List<BaseFragment> fragmentList = new ArrayList<>();
    private ApproveWaitFragment waitFragment;
    private ApproveCompleteFragment completeFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule);
        assignViews();

        initFragments();

        MainViewPagerAdapter adapter = new MainViewPagerAdapter(getSupportFragmentManager(), fragmentList);
        vpViewPager.setAdapter(adapter);

        initNavBarParams();

        ivBack.setOnClickListener(this);

        nbNavBar.setOnTabChangeListener(new NavBar.OnTabChangeListener() {
            @Override
            public void onSelect(NavTab selectTab, int selectPosition) {
                selectTab.setTitleTextColor(getResources().getColor(R.color.resource_blue));
                selectTab.setTipTextColor(getResources().getColor(R.color.resource_blue));
                selectTab.setTipBackgroundDrawable(R.drawable.shape_second_tab_tip_round_select_bg);
            }

            @Override
            public void onCancel(NavTab cancelTab, int cancelPosition) {
                cancelTab.setTitleTextColor(getResources().getColor(R.color.resource_main_text));
                cancelTab.setTipTextColor(getResources().getColor(R.color.resource_main_text));
                cancelTab.setTipBackgroundDrawable(R.drawable.shape_second_tab_tip_round_bg);
            }
        });

        dealIntentData(getIntent());

        // 调用当前可见fragment的show，执行TalkingData的onPageStart
        fragmentList.get(vpViewPager.getCurrentItem()).show();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        dealIntentData(intent);

        if(null != intent) {
            boolean isFromNotice = intent.getBooleanExtra("isFromNotice", false);
            int firstNavPosition = intent.getIntExtra("firstNavPosition", -1);
            if (0 == firstNavPosition) {
                waitFragment.requestDataFromServer();
            } else if (firstNavPosition == 1) {
                if(isFromNotice) {
                    completeFragment.requestDataFromServer();
                }
            }
        }
    }

    private void dealIntentData(Intent intent){
        if(null != intent){
            int firstNavPosition = intent.getIntExtra("firstNavPosition", -1);
            if(firstNavPosition >= 0){
                vpViewPager.setCurrentItem(firstNavPosition);
            }
        }
    }

    private void assignViews() {
        ivBack = (ImageView) findViewById(R.id.iv_back);
        nbNavBar = (NavBar) findViewById(R.id.nb_nav_bar);
        vpViewPager = (ViewPager) findViewById(R.id.vp_view_pager);
    }

    private void initNavBarParams(){
        String[] tabTitles = new String[]{
                getResources().getString(R.string.mine_approve_tab_wait),
                getResources().getString(R.string.mine_approve_tab_complete)
        };

        nbNavBar.bindViewPager(vpViewPager, new NavBarParamsEntity(
                tabTitles.length,
                3,
                tabTitles,
                null,
                null,
                null,
                0,
                getResources().getDimensionPixelSize(R.dimen.activity_second_nav_bar_height),
                0,
                DensityUtil.dp2px(this, 2),
                DensityUtil.dp2px(this, 22),
                DensityUtil.dp2px(this, 22),
                16,
                getResources().getColor(R.color.resource_main_text),
                10,
                getResources().getColor(R.color.resource_main_text),
                DensityUtil.dp2px(this, 9),
                null,
                getResources().getColor(R.color.resource_blue),
                false,
                false,
                true
        ), fragmentList);

        nbNavBar.getNavTab(0).setTitleTextColor(getResources().getColor(R.color.resource_blue));
        nbNavBar.getNavTab(0).setTipTextColor(getResources().getColor(R.color.resource_blue));
        nbNavBar.getNavTab(0).setTipBackgroundDrawable(R.drawable.shape_second_tab_tip_round_select_bg);
    }

    private void initFragments(){
        waitFragment = new ApproveWaitFragment();
        completeFragment = new ApproveCompleteFragment();
        fragmentList.add(waitFragment);
        fragmentList.add(completeFragment);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_back:
                finish();
                break;
        }
    }
}
