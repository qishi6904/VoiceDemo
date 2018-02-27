package com.svw.dealerapp.ui.resource.fragment;

import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.svw.dealerapp.R;
import com.svw.dealerapp.mvpframe.base.BaseViewPagerFragment;
import com.svw.dealerapp.mvpframe.listfragment.BaseListFragment;
import com.svw.dealerapp.ui.entity.NavBarParamsEntity;
import com.svw.dealerapp.ui.main.activity.RdMainActivity;
import com.svw.dealerapp.ui.resource.activity.DealCustomerFilterActivity;
import com.svw.dealerapp.ui.resource.activity.FailedCustomerFilterActivity;
import com.svw.dealerapp.ui.resource.activity.OrderCustomerFilterActivity;
import com.svw.dealerapp.ui.resource.activity.SleepCustomerFilterActivity;
import com.svw.dealerapp.ui.resource.activity.YellowCardFilterActivity;
import com.svw.dealerapp.ui.resource.activity.YellowCardSearchActivity;
import com.svw.dealerapp.ui.resource.adapter.ResourceViewPagerAdapter;
import com.svw.dealerapp.ui.resource.entity.DealCusFilterIntentEntity;
import com.svw.dealerapp.ui.resource.entity.FailedCusFilterIntentEntity;
import com.svw.dealerapp.ui.resource.entity.SleepCusFilterIntentEntity;
import com.svw.dealerapp.ui.resource.entity.YellowCardFilterIntentEntity;
import com.svw.dealerapp.ui.widget.NavBar;
import com.svw.dealerapp.ui.widget.NavTab;
import com.svw.dealerapp.util.DensityUtil;

/**
 * Created by qinshi on 5/4/2017.
 */

public class ResourceFragment extends BaseViewPagerFragment {

    private static final int requestYellowFilterCode = 1002;
    private static final int requestDealCusFilterCode = 1003;
    private static final int requestOrderCusFilterCode = 1004;
    private static final int requestFailedCusFilterCode = 1005;
    private static final int requestSleepCusFilterCode = 1006;

    private NavBar snbTabBar;
    private ResourceViewPagerAdapter adapter;
    private YellowCardFragment yellowCardFragment;
    private OrderCustomerFragment orderCustomerFragment;
    private DealCustomerFragment dealCustomerFragment;
    private FailedCustomerFragment failedCustomerFragment;
    private SleepCustomerFragment sleepCustomerFragment;

    private YellowCardFilterIntentEntity ycFilterIntentEntity;
    private DealCusFilterIntentEntity dealCusFilterIntentEntity;
    private DealCusFilterIntentEntity orderCusFilterIntentEntity;
    private FailedCusFilterIntentEntity failedCusFilterIntentEntity;
    private SleepCusFilterIntentEntity sleepCusFilterIntentEntity;

    private int initPosition;

    @Override
    public void initView() {
        setContentView(R.layout.fragment_resource);
        View rootView = getContentView();
        assignViews(rootView);

        String[] titles = new String[]{
                getResources().getString(R.string.resource_tab_yellow_card),
                getResources().getString(R.string.order_customer_tab_title),
                getResources().getString(R.string.resource_tab_traffic),
                getResources().getString(R.string.resource_tab_sleep),
                getResources().getString(R.string.resource_tab_failed)
        };

        int twoWordsTipOffset = DensityUtil.dp2px(getActivity(), 18);
        int fourWordsTipOffset = DensityUtil.dp2px(getActivity(), 34);
        int[] tipOffsetArr = new int[]{twoWordsTipOffset, fourWordsTipOffset, fourWordsTipOffset,
                fourWordsTipOffset, fourWordsTipOffset};

        snbTabBar.bindViewPager(viewPager, new NavBarParamsEntity(
                titles.length,
                5,
                titles,
                null,
                null,
                null,
                DensityUtil.dp2px(getActivity(), 15),
                getResources().getDimensionPixelSize(R.dimen.activity_second_nav_bar_height),
                0,
                DensityUtil.dp2px(getActivity(), 2),
                DensityUtil.dp2px(getActivity(), 20),
                DensityUtil.dp2px(getActivity(), 20),
                16,
                getResources().getColor(R.color.resource_main_text),
                10,
                getResources().getColor(R.color.resource_main_text),
                DensityUtil.dp2px(getActivity(), 9),
                null,
                getResources().getColor(R.color.resource_blue),
                false,
                false,
                true
        ), fragmentList);

        dealCustomerFragment = new DealCustomerFragment();
        orderCustomerFragment = new OrderCustomerFragment();
        yellowCardFragment = new YellowCardFragment();
        failedCustomerFragment = new FailedCustomerFragment();
        sleepCustomerFragment = new SleepCustomerFragment();
        fragmentList.add(yellowCardFragment);
        fragmentList.add(orderCustomerFragment);
        fragmentList.add(dealCustomerFragment);
        fragmentList.add(sleepCustomerFragment);
        fragmentList.add(failedCustomerFragment);
        adapter = new ResourceViewPagerAdapter(getActivity().getSupportFragmentManager(), fragmentList);
        viewPager.setOffscreenPageLimit(fragmentList.size() - 1);
        viewPager.setAdapter(adapter);

        if (initPosition >= 0) {
            viewPager.setCurrentItem(initPosition);
        }

        NavTab firstTab = snbTabBar.getSecondNavTabByIndex(0);
        if (null != firstTab) {
            firstTab.setTitleTextColor(getResources().getColor(R.color.resource_blue));
            firstTab.setTipTextColor(getResources().getColor(R.color.resource_blue));
            firstTab.setTipBackgroundDrawable(R.drawable.shape_second_tab_tip_round_select_bg);
        }

        snbTabBar.setOnTabChangeListener(new NavBar.OnTabChangeListener() {
            @Override
            public void onSelect(NavTab selectTab, int selectPosition) {
                selectTab.setTitleTextColor(getResources().getColor(R.color.resource_blue));
                boolean hasFilter = ((BaseListFragment) fragmentList.get(selectPosition)).isHasFilter();
                ((RdMainActivity) getActivity()).setIvFilterStatus(hasFilter);

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
    }

    private void assignViews(View view) {
        snbTabBar = (NavBar) view.findViewById(R.id.snb_tab_bar);
        viewPager = (ViewPager) view.findViewById(R.id.view_pager);
    }

    /**
     * 跳转到过滤页面
     */
    public void jumpToFilterActivity() {
        Intent intent = null;
        switch (viewPager.getCurrentItem()) {
            case 0:
                intent = new Intent(getActivity(), YellowCardFilterActivity.class);
                intent.putExtra("filterIntentEntity", ycFilterIntentEntity);
                this.startActivityForResult(intent, requestYellowFilterCode);
                getActivity().overridePendingTransition(R.anim.activity_traffic_filter_in, R.anim.activity_traffic_filter_out);
                break;
            case 1:
                intent = new Intent(getActivity(), OrderCustomerFilterActivity.class);
                intent.putExtra("filterIntentEntity", orderCusFilterIntentEntity);
                this.startActivityForResult(intent, requestOrderCusFilterCode);
                getActivity().overridePendingTransition(R.anim.activity_traffic_filter_in, R.anim.activity_traffic_filter_out);
                break;
            case 2:
                intent = new Intent(getActivity(), DealCustomerFilterActivity.class);
                intent.putExtra("filterIntentEntity", dealCusFilterIntentEntity);
                this.startActivityForResult(intent, requestDealCusFilterCode);
                getActivity().overridePendingTransition(R.anim.activity_traffic_filter_in, R.anim.activity_traffic_filter_out);
                break;
            case 3:
                intent = new Intent(getActivity(), SleepCustomerFilterActivity.class);
                intent.putExtra("filterIntentEntity", sleepCusFilterIntentEntity);
                this.startActivityForResult(intent, requestSleepCusFilterCode);
                getActivity().overridePendingTransition(R.anim.activity_traffic_filter_in, R.anim.activity_traffic_filter_out);
                break;
            case 4:
                intent = new Intent(getActivity(), FailedCustomerFilterActivity.class);
                intent.putExtra("filterIntentEntity", failedCusFilterIntentEntity);
                this.startActivityForResult(intent, requestFailedCusFilterCode);
                getActivity().overridePendingTransition(R.anim.activity_traffic_filter_in, R.anim.activity_traffic_filter_out);
                break;
        }
    }

    /**
     * 跳转到资源搜索页
     */
    public void jumpToSearchActivity() {
        Intent intent = new Intent(getActivity(), YellowCardSearchActivity.class);
        this.startActivity(intent);
    }

    /**
     * 设置viewpager当前显示的fragment
     *
     * @param position
     */
    public void setCurrentItem(int position) {
        if (null != viewPager) {
            viewPager.setCurrentItem(position);
            initPosition = -1;
        } else {
            initPosition = position;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case requestYellowFilterCode:
                if (null != data) {
                    String filterString = data.getStringExtra("filterString");
                    ycFilterIntentEntity = data.getParcelableExtra("filterIntentEntity");
                    yellowCardFragment.requestDateAfterTakeFilter(filterString);
                    boolean hasFilter = data.getBooleanExtra("hasFilter", false);
                    yellowCardFragment.setHasFilter(hasFilter);
                }
                break;
            case requestDealCusFilterCode:
                if (null != data) {
                    String filterString = data.getStringExtra("filterString");
                    dealCusFilterIntentEntity = data.getParcelableExtra("filterIntentEntity");
                    dealCustomerFragment.requestDateAfterTakeFilter(filterString);
                    boolean hasFilter = data.getBooleanExtra("hasFilter", false);
                    dealCustomerFragment.setHasFilter(hasFilter);
                }
                break;
            case requestOrderCusFilterCode:
                if (null != data) {
                    String filterString = data.getStringExtra("filterString");
                    orderCusFilterIntentEntity = data.getParcelableExtra("filterIntentEntity");
                    orderCustomerFragment.requestDateAfterTakeFilter(filterString);
                    boolean hasFilter = data.getBooleanExtra("hasFilter", false);
                    orderCustomerFragment.setHasFilter(hasFilter);
                }
                break;
            case requestFailedCusFilterCode:
                if (null != data) {
                    String filterString = data.getStringExtra("filterString");
                    failedCusFilterIntentEntity = data.getParcelableExtra("filterIntentEntity");
                    failedCustomerFragment.requestDateAfterTakeFilter(filterString);
                    boolean hasFilter = data.getBooleanExtra("hasFilter", false);
                    failedCustomerFragment.setHasFilter(hasFilter);
                }
                break;
            case requestSleepCusFilterCode:
                if (null != data) {
                    String filterString = data.getStringExtra("filterStr");
                    sleepCusFilterIntentEntity = data.getParcelableExtra("filterEntity");
                    sleepCustomerFragment.requestDateAfterTakeFilter(filterString);
                    boolean hasFilter = data.getBooleanExtra("hasFilter", false);
                    sleepCustomerFragment.setHasFilter(hasFilter);
                }
                break;
        }
        if (null != data) {
            boolean hasFilter = data.getBooleanExtra("hasFilter", false);
            ((RdMainActivity) getActivity()).setIvFilterStatus(hasFilter);
        }
    }

    /**
     * 获取指定位置的fragment
     *
     * @param index
     * @return
     */
    public BaseListFragment getFragment(int index) {
        if (index < fragmentList.size() && fragmentList.get(index) instanceof BaseListFragment) {
            return (BaseListFragment) fragmentList.get(index);
        }
        return null;
    }

    /**
     * 设置Tab上圆圈里的字符
     *
     * @param position
     * @param tipNumber
     */
    public void setTabTipNumber(int position, String tipNumber) {
        if (snbTabBar.getTabList().size() > position) {
            try {
                if (Integer.parseInt(tipNumber) > 99) {
                    snbTabBar.getNavTab(position).setTvTabTipText("...");
                } else {
                    snbTabBar.getNavTab(position).setTvTabTipText(tipNumber);
                }
            } catch (Exception e) {
                snbTabBar.getNavTab(position).setTvTabTipText("...");
                e.printStackTrace();
            }
        }
    }
}
