package com.svw.dealerapp.ui.widget;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;

import com.svw.dealerapp.R;
import com.svw.dealerapp.common.BaseFragment;
import com.svw.dealerapp.mvpframe.base.BaseViewPagerFragment;
import com.svw.dealerapp.ui.entity.NavBarParamsEntity;
import com.svw.dealerapp.util.ScreenUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by qinshi on 4/27/2017.
 */

public class NavBar extends ScrollView {

    private RelativeLayout rlNavBar;
    private LinearLayout llTabContainer;
    private LinearLayout llTabUnderLine;
    private View vScrollLine;

    private Context mContext;
    private List<NavTab> tabList = new ArrayList<>();
    private int selectTabPosition = 0;
    private OnTabChangeListener onTabChangeListener;

    public NavBar(Context context) {
        super(context);
        initView(context);
    }

    public NavBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    private void initView(Context context) {
        mContext = context;
        View.inflate(context, R.layout.widget_second_nav_bar, this);
        assignViews();
    }

    private void assignViews() {
        rlNavBar = (RelativeLayout) findViewById(R.id.rl_nav_bar);
        llTabContainer = (LinearLayout) findViewById(R.id.ll_tab_container);
        llTabUnderLine = (LinearLayout) findViewById(R.id.ll_tab_under_line);
        vScrollLine = findViewById(R.id.v_scroll_line);
    }

    /**
     * 绑定ViewPager，设置TabBar的相关参数
     *
     * @param viewPager
     * @param paramsEntity
     */
    public void bindViewPager(final ViewPager viewPager, NavBarParamsEntity paramsEntity, final List fragmentList) {
        if (null == viewPager) {
            return;
        }
        LinearLayout.LayoutParams tabParams;
        final int tabWidth;
        if (paramsEntity.getTabs() <= 0) {
            return;
        } else if (paramsEntity.getTabs() <= paramsEntity.getShowTabsOnePage()) {   //如果tab的数量小于等于4，每个tab平均分配屏幕的宽度
            tabWidth = (ScreenUtils.getScreenWidth(mContext) - paramsEntity.getLeftRightMargin() * 2) / paramsEntity.getTabs();
            tabParams = new LinearLayout.LayoutParams(tabWidth, ViewGroup.LayoutParams.MATCH_PARENT);
        } else {    //如果tab的数量大于4，每个tab的宽度为屏幕宽度的1/4，左右可以滑动
            tabWidth = (ScreenUtils.getScreenWidth(mContext) - paramsEntity.getLeftRightMargin() * 2) / paramsEntity.getShowTabsOnePage();
            tabParams = new LinearLayout.LayoutParams(tabWidth, ViewGroup.LayoutParams.MATCH_PARENT);
        }
        //向tabBar中添加tab，设置tab的相关参数
        for (int i = 0; i < paramsEntity.getTabs(); i++) {
            final NavTab secondNavTab = new NavTab(mContext);
            secondNavTab.setIconSize(paramsEntity.getIconWidth(), paramsEntity.getIconHeight());
            secondNavTab.setTipTextSize(paramsEntity.getTipTextSize());
            secondNavTab.setTitleTextSize(paramsEntity.getTitleTextSize());
            secondNavTab.setTvTabTipRadius(paramsEntity.getTipRadius());
            secondNavTab.setTipTextColor(paramsEntity.getTipTextColor());
            secondNavTab.setTitleTextColor(paramsEntity.getTitleTextColor());
            if(0 != paramsEntity.getNavPaddingBottom()) {
                secondNavTab.setPadding(0, 0, 0, paramsEntity.getNavPaddingBottom());
            }
            secondNavTab.setPosition(i);
            if (null != paramsEntity.getTabIconUrls() && paramsEntity.getTabIconUrls().length > i && !TextUtils.isEmpty(paramsEntity.getTabIconUrls()[i])) {
                secondNavTab.loadIcon(paramsEntity.getTabIconUrls()[i]);
            }else if(null != paramsEntity.getDefaultIconIds() && paramsEntity.getDefaultIconIds().length > i){
                secondNavTab.loadDefaultIcon(paramsEntity.getDefaultIconIds()[i]);
            }
            if (null != paramsEntity.getTabTitles() && paramsEntity.getTabTitles().length > i) {
                secondNavTab.setTabTitleText(paramsEntity.getTabTitles()[i]);
            }
            if (null != paramsEntity.getTipTexts() && paramsEntity.getTipTexts().length > i) {
                secondNavTab.setTvTabTipText(paramsEntity.getTipTexts()[i]);
            }
            if (!paramsEntity.isShowIcon()) {
                secondNavTab.hideIcon();
            }
            if (!paramsEntity.isShowTip()) {
                secondNavTab.hideTipTv();
            }
            if (!paramsEntity.isShowScrollLine()) {
                llTabUnderLine.setVisibility(View.GONE);
            }
            llTabContainer.addView(secondNavTab);
            secondNavTab.setLayoutParams(tabParams);
            if(null != paramsEntity.getTipOffsetArr() &&
                    i < paramsEntity.getTipOffsetArr().length) {
                secondNavTab.setTabLayoutParams(tabWidth, paramsEntity.getTipOffsetArr()[i]);
            }

            //tab的点击事件
            secondNavTab.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (v instanceof NavTab) {
                        NavTab tab = (NavTab) v;
                        if (selectTabPosition != tab.getPosition()) {
                            viewPager.setCurrentItem(tab.getPosition());
                        }
                    }
                }
            });

            tabList.add(secondNavTab);
        }

        //设置缩进
        if(paramsEntity.getLeftRightMargin() > 0) {
            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(llTabUnderLine.getLayoutParams());
            params.leftMargin = paramsEntity.getLeftRightMargin();
            params.rightMargin = paramsEntity.getLeftRightMargin();
            params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE);
            llTabUnderLine.setLayoutParams(params);
            llTabContainer.setPadding(paramsEntity.getLeftRightMargin(), 0, paramsEntity.getLeftRightMargin(), 0);
            LinearLayout.LayoutParams scrollBlockParams = new LinearLayout.LayoutParams(vScrollLine.getLayoutParams());
            scrollBlockParams.width = (ScreenUtils.getScreenWidth(mContext) - paramsEntity.getLeftRightMargin() * 2)/paramsEntity.getTabs();
            vScrollLine.setLayoutParams(scrollBlockParams);
        }

        //设置viewpager切换监听事件
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                LinearLayout.LayoutParams scrollLineParams = new LinearLayout.LayoutParams(vScrollLine.getLayoutParams());
                scrollLineParams.leftMargin = position * tabWidth;
                vScrollLine.setLayoutParams(scrollLineParams);

                if (null != onTabChangeListener) {
                    onTabChangeListener.onSelect(tabList.get(position), position);
                    onTabChangeListener.onCancel(tabList.get(selectTabPosition), selectTabPosition);
                }

                if(fragmentList.get(position) instanceof BaseFragment) {
                    BaseFragment selectFragment = (BaseFragment) fragmentList.get(position);
                    if (selectFragment instanceof BaseViewPagerFragment) {
                        selectFragment = ((BaseViewPagerFragment) selectFragment).getCurrentFragment();
                    }
                    selectFragment.show();
                }
                if(fragmentList.get(position) instanceof BaseFragment) {
                    BaseFragment unSelectFragment = (BaseFragment) fragmentList.get(selectTabPosition);
                    if (unSelectFragment instanceof BaseViewPagerFragment) {
                        unSelectFragment = ((BaseViewPagerFragment) unSelectFragment).getCurrentFragment();
                    }
                    unSelectFragment.hide();
                }

                selectTabPosition = position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        //设置tabBar的高度
        LayoutParams navBarParams = new LayoutParams(rlNavBar.getLayoutParams());
        navBarParams.height = paramsEntity.getNavBarHeight();
        rlNavBar.setLayoutParams(navBarParams);

        //设置滑动下划线的宽高
        vScrollLine.setBackgroundColor(paramsEntity.getScrollLineColor());
        LinearLayout.LayoutParams scrollLineParams = new LinearLayout.LayoutParams(vScrollLine.getLayoutParams());
        scrollLineParams.width = tabWidth;
        scrollLineParams.height = paramsEntity.getScrollLineHeight();
        vScrollLine.setLayoutParams(scrollLineParams);
    }

    /**
     * 获取Tab
     * @param position
     * @return
     */
    public NavTab getNavTab(int position){
        return tabList.get(position);
    }

    /**
     * 定义切换Tab的接口
     */
    public interface OnTabChangeListener {
        void onSelect(NavTab selectTab, int selectPosition);

        void onCancel(NavTab cancelTab, int cancelPosition);
    }

    /**
     * 设置切换Tab的接口
     *
     * @param onTabChangeListener
     */
    public void setOnTabChangeListener(OnTabChangeListener onTabChangeListener) {
        this.onTabChangeListener = onTabChangeListener;
    }

    /**
     * 获取tab
     * @param index
     * @return
     */
    public NavTab getSecondNavTabByIndex(int index){
        if(tabList.size() > index){
            return tabList.get(index);
        }
        return null;
    }

    /**
     * 获取Tab的集合
     * @return
     */
    public List<NavTab> getTabList(){
        return tabList;
    }
}
