package com.svw.dealerapp.ui.main;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.svw.dealerapp.R;
import com.svw.dealerapp.common.BaseActivity;
import com.svw.dealerapp.common.BaseFragment;
import com.svw.dealerapp.entity.update.AppUpdateEntity;
import com.svw.dealerapp.mvpframe.listfragment.BaseListFragment;
import com.svw.dealerapp.ui.entity.NavBarParamsEntity;
import com.svw.dealerapp.ui.home.activity.CreateTrafficActivity;
import com.svw.dealerapp.ui.home.fragment.HomeFragment;
import com.svw.dealerapp.ui.main.adapter.MainViewPagerAdapter;
import com.svw.dealerapp.ui.mine.fragment.MineFragment;
import com.svw.dealerapp.ui.report.fragment.ReportFragment;
import com.svw.dealerapp.ui.resource.fragment.ResourceFragment;
import com.svw.dealerapp.ui.update.UpdateActivity;
import com.svw.dealerapp.ui.update.contract.AppUpdateContract;
import com.svw.dealerapp.ui.update.model.AppUpdateModel;
import com.svw.dealerapp.ui.update.presenter.AppUpdatePresenter;
import com.svw.dealerapp.ui.widget.NavBar;
import com.svw.dealerapp.ui.widget.NavTab;
import com.svw.dealerapp.util.dbtools.DBUtils;
import com.svw.dealerapp.util.DensityUtil;
import com.svw.dealerapp.util.TalkingDataUtils;
import com.svw.dealerapp.util.ToastUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by qinshi on 5/9/2017.
 */

@Deprecated
public class MainActivity extends BaseActivity implements AppUpdateContract.View {

    private RelativeLayout rlTitle;
    private TextView tvTitle;
    private ImageView ivFilter;
    private ImageView ivSearch;
    private ViewPager nvpMainViewPager;
    private NavBar nbMainNavBar;
    private View vShadowUp;

    private MainViewPagerAdapter adapter;
    private List<BaseFragment> fragmentList = new ArrayList<>();
    private ResourceFragment resourceFragment;
    private HomeFragment homeFragment;
    private String[] titles;
    private int[] unSelectTabIconIds;
    private int[] selectTabIconIds;

    private AppUpdateContract.Presenter updatePresenter;
    private boolean hasCheckVersion = false;    //是否已经检查过版本信息

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //updatefun升级方式-备用
        //initUpdate();

        //dev环境不提示自动升级
        String currentEnv = DBUtils.getCurrentEnvLabel();
        if (!"Env0".equals(currentEnv)) {
            //检查版本方法
            checkVersion();
        }

        setContentView(R.layout.activity_main);
        assignViews();
        setExit(true);
        processExtraData();
        dealIntentData(getIntent());

        // 调用当前可见fragment的show，执行TalkingData的onPageStart
        fragmentList.get(nvpMainViewPager.getCurrentItem()).show();
    }

//updatefun升级方式-备用
//    private void initUpdate(){
//        UpdateKey.API_TOKEN = ManifestUtils.getMetaDataValue("check_update_token");
//        UpdateKey.APP_ID = this.getPackageName();
//        //下载方式:
//        UpdateKey.DialogOrNotification=UpdateKey.WITH_DIALOG;
//        //UpdateKey.DialogOrNotification=UpdateKey.WITH_NOTIFITION;通过通知栏来进行下载(默认)
//        UpdateFunGO.init(this);
//    }


    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        dealIntentData(intent);

        if (null != intent) {
            int firstNavPosition = intent.getIntExtra("firstNavPosition", -1);
            int secondNavPosition = intent.getIntExtra("secondNavPosition", -1);
            if (firstNavPosition == 1 && secondNavPosition >= 0 && secondNavPosition <= 1) {
                BaseListFragment fragment = resourceFragment.getFragment(secondNavPosition);
                if (null != fragment) {
                    fragment.requestDataFromServer();
                }
            }
        }
    }

    private void dealIntentData(Intent intent) {
        if (null != intent) {
            int firstNavPosition = intent.getIntExtra("firstNavPosition", -1);
            int secondNavPosition = intent.getIntExtra("secondNavPosition", -1);
            setViewPagerPosition(firstNavPosition, secondNavPosition);
        }
    }

    /**
     * 设置一\二级导航的位置
     *
     * @param firstNavPosition
     * @param secondNavPosition
     */
    public void setViewPagerPosition(int firstNavPosition, int secondNavPosition) {
        if (firstNavPosition >= 0) {
            nvpMainViewPager.setCurrentItem(firstNavPosition);
        }
        if (firstNavPosition == 1 && secondNavPosition >= 0) {
            resourceFragment.setCurrentItem(secondNavPosition);
        }
    }

    private void initFragment() {
        homeFragment = new HomeFragment();
        fragmentList.add(homeFragment);

        resourceFragment = new ResourceFragment();
        fragmentList.add(resourceFragment);

//        FirstReportFragment firstReportFragment = new FirstReportFragment();
//        Bundle bundle = new Bundle();
//        //check current env
//        int num = 0;
//        Cursor cursor = DealerApp.dbHelper.rawQuery("select * from Env where status=?" ,new String[]{"1"});
//        if(cursor !=null) {
//            if (cursor.moveToNext()) {
//                String envName = cursor.getString(cursor.getColumnIndex("name")).toString();
//                num = Integer.parseInt(envName.substring(3,4));
//            }
//            cursor.close();
//        }
//        bundle.putString("URL", Constants.PRESET_REPORT_BASE_URL[num]+"/#/charts");
//        firstReportFragment.setArguments(bundle);
//        fragmentList.add(firstReportFragment);
        fragmentList.add(new ReportFragment());

        fragmentList.add(new MineFragment());
    }

    private void assignViews() {
        rlTitle = (RelativeLayout) findViewById(R.id.rl_title);
        tvTitle = (TextView) findViewById(R.id.tv_title);
        ivFilter = (ImageView) findViewById(R.id.iv_filter);
        ivSearch = (ImageView) findViewById(R.id.iv_search);
        nvpMainViewPager = (ViewPager) findViewById(R.id.nvp_main_view_pager);
        nbMainNavBar = (NavBar) findViewById(R.id.nb_main_nav_bar);
        vShadowUp = findViewById(R.id.v_shadow_up);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.iv_filter:    // 筛选点击事件
                if (nvpMainViewPager.getCurrentItem() == 1) {
                    resourceFragment.jumpToFilterActivity();
                }
                if (nvpMainViewPager.getCurrentItem() == 0) {
                    TalkingDataUtils.onEvent(this, "点击添加客流", "首页");
                    Intent intent = new Intent(this, CreateTrafficActivity.class);
                    startActivity(intent);
                }
                break;
            case R.id.iv_search:    // 搜索点击事件
                if (nvpMainViewPager.getCurrentItem() == 0 || nvpMainViewPager.getCurrentItem() == 1) {
                    TalkingDataUtils.onEvent(this, "进入搜索", "首页");
                    resourceFragment.jumpToSearchActivity();
                }
                break;
        }
    }

    private void processExtraData() {
        initFragment();
        adapter = new MainViewPagerAdapter(getSupportFragmentManager(), fragmentList);
        nvpMainViewPager.setAdapter(adapter);
        nvpMainViewPager.setOffscreenPageLimit(3);

        titles = new String[]{
                getResources().getString(R.string.main_index_page),
                getResources().getString(R.string.main_resource),
                getResources().getString(R.string.main_kpi),
                getResources().getString(R.string.main_mine)};
        unSelectTabIconIds = new int[]{
                R.mipmap.ic_home_tab,
                R.mipmap.ic_resource_tab,
                R.mipmap.ic_kpi_tab,
                R.mipmap.ic_mine_tab};
        selectTabIconIds = new int[]{
                R.mipmap.ic_home_tab_select,
                R.mipmap.ic_resource_tab_select,
                R.mipmap.ic_kpi_tab_select,
                R.mipmap.ic_mine_tab_select};
        int tipOffset = DensityUtil.dp2px(this, 10);
        int[] tipOffsetArr = new int[]{tipOffset, tipOffset, tipOffset, tipOffset};
        nbMainNavBar.bindViewPager(nvpMainViewPager, new NavBarParamsEntity(
                titles.length,
                4,
                titles,
                null,
                null,
                unSelectTabIconIds,
                0,
                MainActivity.this.getResources().getDimensionPixelSize(R.dimen.activity_title_bar_height),
                0,
                0,
                DensityUtil.dp2px(this, 22),
                DensityUtil.dp2px(this, 22),
                10,
                MainActivity.this.getResources().getColor(R.color.resource_assist_text),
                0,
                0,
                DensityUtil.dp2px(this, 4),
                tipOffsetArr,
                0,
                true,
                false,
                false
        ), fragmentList);

        NavTab firstTab = nbMainNavBar.getNavTab(0);
        if (null != firstTab) {
            firstTab.setTitleTextColor(Color.WHITE);
            firstTab.loadDefaultIcon(R.mipmap.ic_home_tab_select);
        }

        nbMainNavBar.setOnTabChangeListener(new NavBar.OnTabChangeListener() {
            @Override
            public void onSelect(NavTab selectTab, int selectPosition) {
                selectTab.loadDefaultIcon(selectTabIconIds[selectPosition]);
                selectTab.setTitleTextColor(Color.WHITE);
                tvTitle.setText(titles[selectPosition]);
                //如果切换到资源页和报告页，标题栏下的阴影消失，其他显示
//                if(selectPosition == 1 ||  selectPosition == 2){
                if (selectPosition == 1) {
                    vShadowUp.setVisibility(View.INVISIBLE);
                } else {
                    vShadowUp.setVisibility(View.VISIBLE);
                }
                //如果切换到 我的/报告 页，搜索、筛选隐藏，其他显示
                if (selectPosition == 3 || selectPosition == 2) {
                    ivFilter.setVisibility(View.INVISIBLE);
                    ivSearch.setVisibility(View.INVISIBLE);
                } else {
                    ivFilter.setVisibility(View.VISIBLE);
                    ivSearch.setVisibility(View.VISIBLE);
                }

                if (selectPosition == 0) {
                    ivFilter.setImageResource(R.mipmap.ic_home_add);
                    homeFragment.startAutoPlay();
                } else {
                    ivFilter.setImageResource(R.mipmap.ic_filter);
                    homeFragment.stopAutoPlay();
                }

                // 如果切换到报告页，隐藏标题，其他则显示
//                if(selectPosition == 2){
//                    rlTitle.setVisibility(View.GONE);
//                }else {
//                    rlTitle.setVisibility(View.VISIBLE);
//                }
            }

            @Override
            public void onCancel(NavTab cancelTab, int cancelPosition) {
                cancelTab.loadDefaultIcon(unSelectTabIconIds[cancelPosition]);
                cancelTab.setTitleTextColor(getResources().getColor(R.color.resource_assist_text));
            }
        });

        ivFilter.setOnClickListener(this);
        ivSearch.setOnClickListener(this);
    }

    public boolean getKPIFragmentIsVisibility() {
        if (null != fragmentList && null != fragmentList.get(2)) {
            return fragmentList.get(2).getUserVisibleHint();
        }
        return false;
    }

    /**
     * 检查应用版本，在homefragment中第一次获取数据成功后调用
     * 为防止token失效时，显示升级的对话框后又跳到登录页
     */
    public void checkVersion() {
        if (!hasCheckVersion) {
            hasCheckVersion = true;
            if (null == updatePresenter) {
                updatePresenter = new AppUpdatePresenter(this, new AppUpdateModel());
            }
            updatePresenter.checkAppUpdate(MainActivity.this, false);
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
            ivFilter.setImageResource(R.mipmap.ic_filter_blue);
        } else {
            ivFilter.setImageResource(R.mipmap.ic_filter);
        }
    }

    //首页检查升级为后台执行，无需实现下列方法----start
    @Override
    public void showLoadingDialog() {

    }

    @Override
    public void hideLoadingDialog() {

    }

    @Override
    public void showServerErrorToast() {

    }

    @Override
    public void showTimeOutToast() {

    }

    @Override
    public void showNetWorkErrorToast() {

    }

    @Override
    public void checkUpdateFail() {

    }
    //首页检查升级为后台执行，无需实现上面方法----end

    @Override
    public void hasNewVersion(AppUpdateEntity updateEntity) {
        Intent intent = new Intent(this, UpdateActivity.class);
        intent.putExtra("downloadUrl", updateEntity.getInstall_url());
        intent.putExtra("apkFileSize", updateEntity.getBinary().getFsize());
        intent.putExtra("isShowLoading", false);
        startActivity(intent);
        overridePendingTransition(R.anim.activity_traffic_filter_in, R.anim.activity_traffic_filter_out);
    }


    @Override
    public void isLatestVersion() {
        ToastUtils.showToast(getResources().getString(R.string.update_find_is_new));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ToastUtils.destroy();
    }

    //updatefun升级方式-备用
//    @Override
//    protected void onResume() {
//        super.onResume();
//        UpdateFunGO.onResume(this);
//    }
//
//    @Override
//    protected void onStop() {
//        super.onStop();
//        UpdateFunGO.onStop(this);
//    }
    public ResourceFragment getResourceFragment() {
        return resourceFragment;
    }
}
