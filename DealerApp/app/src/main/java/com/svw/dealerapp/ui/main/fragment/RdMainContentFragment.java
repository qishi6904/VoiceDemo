package com.svw.dealerapp.ui.main.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.svw.dealerapp.R;
import com.svw.dealerapp.common.BaseFragment;
import com.svw.dealerapp.ui.entity.NavBarParamsEntity;
import com.svw.dealerapp.ui.home.activity.CreateTrafficActivity;
import com.svw.dealerapp.ui.home.fragment.HomeFragment;
import com.svw.dealerapp.ui.home.fragment.RdHomeFragment;
import com.svw.dealerapp.ui.main.activity.RdMainActivity;
import com.svw.dealerapp.ui.main.adapter.MainViewPagerAdapter;
import com.svw.dealerapp.ui.mine.fragment.MineFragment;
import com.svw.dealerapp.ui.report.fragment.ReportFragment;
import com.svw.dealerapp.ui.resource.fragment.ResourceFragment;
import com.svw.dealerapp.ui.widget.NavBar;
import com.svw.dealerapp.ui.widget.NavTab;
import com.svw.dealerapp.util.DensityUtil;
import com.svw.dealerapp.util.TalkingDataUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by qinshi on 1/11/2018.
 */

public class RdMainContentFragment extends BaseFragment implements View.OnClickListener {

    private RelativeLayout rlTitle;
    private TextView tvTitle;
    private ImageView ivMenu;
    private ImageView ivFilter;
    private ImageView ivSearch;
    private ViewPager nvpMainViewPager;
    private NavBar nbMainNavBar;

    private List<BaseFragment> fragmentList = new ArrayList<>();
    private RdHomeFragment homeFragment;
    private ResourceFragment resourceFragment;
    private ReportFragment reportFragment;
    private MineFragment mineFragment;
    private RdMainActivity mainActivity;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainActivity = (RdMainActivity) getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.rd_fragment_main_content, null);

        assignViews(view);

        processExtraData();

        return view;
    }

    private void assignViews(View view) {
        rlTitle = (RelativeLayout) view.findViewById(R.id.rl_title);
        tvTitle = (TextView) view.findViewById(R.id.tv_title);
        ivMenu = (ImageView) view.findViewById(R.id.iv_menu);
        ivFilter = (ImageView) view.findViewById(R.id.iv_filter);
        ivSearch = (ImageView) view.findViewById(R.id.iv_search);
        nvpMainViewPager = (ViewPager) view.findViewById(R.id.nvp_main_view_pager);
        nbMainNavBar = (NavBar) view.findViewById(R.id.nb_main_nav_bar);
    }

    private void initFragment() {
        homeFragment = new RdHomeFragment();
        fragmentList.add(homeFragment);
        resourceFragment = new ResourceFragment();
        fragmentList.add(resourceFragment);
        reportFragment = new ReportFragment();
        fragmentList.add(reportFragment);
        mineFragment = new MineFragment();
        fragmentList.add(mineFragment);
    }

    private void processExtraData() {
        initFragment();
        MainViewPagerAdapter adapter = new MainViewPagerAdapter(getActivity().getSupportFragmentManager(), fragmentList);
        nvpMainViewPager.setAdapter(adapter);
        nvpMainViewPager.setOffscreenPageLimit(3);

        final String[] titles = new String[]{
                getResources().getString(R.string.main_index_page),
                getResources().getString(R.string.main_resource),
                getResources().getString(R.string.main_kpi),
                getResources().getString(R.string.main_mine)};
        final int[] unSelectTabIconIds = new int[]{
                R.mipmap.rd_ic_nav_home_unselect,
                R.mipmap.rd_ic_nav_resource_unselect,
                R.mipmap.rd_ic_nav_report_unselect,
                R.mipmap.rd_ic_nav_mine_unselect};
        final int[] selectTabIconIds = new int[]{
                R.mipmap.rd_ic_nav_home_select,
                R.mipmap.rd_ic_nav_resource_select,
                R.mipmap.rd_ic_nav_report_select,
                R.mipmap.rd_ic_nav_mine_select};
        int tipOffset = DensityUtil.dp2px(getActivity(), 10);
        int[] tipOffsetArr = new int[]{tipOffset, tipOffset, tipOffset, tipOffset};
        nbMainNavBar.bindViewPager(nvpMainViewPager, new NavBarParamsEntity(
                titles.length,
                4,
                titles,
                null,
                null,
                unSelectTabIconIds,
                0,
                getResources().getDimensionPixelSize(R.dimen.activity_title_bar_height),
                0,
                0,
                DensityUtil.dp2px(getActivity(), 22),
                DensityUtil.dp2px(getActivity(), 22),
                10,
                getResources().getColor(R.color.resource_assist_text),
                0,
                0,
                DensityUtil.dp2px(getActivity(), 4),
                tipOffsetArr,
                0,
                true,
                false,
                false
        ), fragmentList);

        NavTab firstTab = nbMainNavBar.getNavTab(0);
        if (null != firstTab) {
            firstTab.setTitleTextColor(getResources().getColor(R.color.light_blue));
            firstTab.loadDefaultIcon(R.mipmap.rd_ic_nav_home_select);
        }

        nbMainNavBar.setOnTabChangeListener(new NavBar.OnTabChangeListener() {
            @Override
            public void onSelect(NavTab selectTab, int selectPosition) {
                selectTab.loadDefaultIcon(selectTabIconIds[selectPosition]);
                selectTab.setTitleTextColor(getResources().getColor(R.color.light_blue));
                tvTitle.setText(titles[selectPosition]);
                //如果切换到 我的/报告 页，'搜索、筛选' 隐藏，其他显示
                if (selectPosition == 3 || selectPosition == 2) {
                    ivFilter.setVisibility(View.INVISIBLE);
                    ivSearch.setVisibility(View.INVISIBLE);
                } else {
                    ivFilter.setVisibility(View.VISIBLE);
                    ivSearch.setVisibility(View.VISIBLE);
                }

                if (selectPosition == 0) {
                    ivFilter.setImageResource(R.mipmap.rd_ic_home_create_lead);
                    homeFragment.startAutoPlay();
                } else {
                    ivFilter.setImageResource(R.mipmap.rd_ic_home_fitler);
                    homeFragment.stopAutoPlay();
                }
            }

            @Override
            public void onCancel(NavTab cancelTab, int cancelPosition) {
                cancelTab.loadDefaultIcon(unSelectTabIconIds[cancelPosition]);
                cancelTab.setTitleTextColor(getResources().getColor(R.color.resource_assist_text));
            }
        });

        ivFilter.setOnClickListener(this);
        ivSearch.setOnClickListener(this);
        ivMenu.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_filter:    // 筛选点击事件
                if (nvpMainViewPager.getCurrentItem() == 1) {
                    resourceFragment.jumpToFilterActivity();
                }
                if (nvpMainViewPager.getCurrentItem() == 0) {
                    TalkingDataUtils.onEvent(getActivity(), "点击添加客流", "首页");
                    Intent intent = new Intent(getActivity(), CreateTrafficActivity.class);
                    startActivity(intent);
                }
                break;
            case R.id.iv_search:    // 搜索点击事件
                if (nvpMainViewPager.getCurrentItem() == 0 || nvpMainViewPager.getCurrentItem() == 1) {
                    TalkingDataUtils.onEvent(getActivity(), "进入搜索", "首页");
                    resourceFragment.jumpToSearchActivity();
                }
                break;
            case R.id.iv_menu:
                if(mainActivity.isLeftDrawerOpen()){
                    mainActivity.toggleLeftDrawer(false);
                }else {
                    mainActivity.toggleLeftDrawer(true);
                }
                break;
        }
    }

    /**
     * 是否显示 我的 右上角的提示点
     *
     * @param isShow
     */
    public void isShowMeRedTip(boolean isShow) {
        NavTab meTab = nbMainNavBar.getNavTab(3);
        if (null != meTab) {
            if (isShow) {
                meTab.setTvTabTipSolid(true);
                meTab.showTipTv();
            } else {
                meTab.hideTipTv();
            }
        }
    }

    /**
     * 根据当前fragment是否带过滤条件显示过滤按钮的颜色
     *
     * @param hasFilter
     */
    public void setIvFilterStatus(boolean hasFilter) {
        if (hasFilter) {
            ivFilter.setImageResource(R.mipmap.rd_ic_home_fitler);
        } else {
            ivFilter.setImageResource(R.mipmap.rd_ic_home_fitler);
        }
    }

    /**
     * 获取资源Fragment
     * @return
     */
    public ResourceFragment getResourceFragment() {
        return resourceFragment;
    }

    /**
     * 获取viewpager
     * @return
     */
    public ViewPager getNvpMainViewPager(){
        return nvpMainViewPager;
    }

    /**
     * 通过位置切换Fragment
     * @param position
     */
    public void changeFragmentByPosition(int position) {
        nvpMainViewPager.setCurrentItem(position, false);
    }
}
