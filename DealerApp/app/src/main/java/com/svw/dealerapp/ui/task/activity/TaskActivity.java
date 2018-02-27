package com.svw.dealerapp.ui.task.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;

import com.svw.dealerapp.R;
import com.svw.dealerapp.common.BaseActivity;
import com.svw.dealerapp.common.BaseFragment;
import com.svw.dealerapp.mvpframe.listfragment.BaseListFragment;
import com.svw.dealerapp.ui.entity.NavBarParamsEntity;
import com.svw.dealerapp.ui.main.adapter.MainViewPagerAdapter;
import com.svw.dealerapp.ui.resource.entity.FailedCusFilterIntentEntity;
import com.svw.dealerapp.ui.task.entity.FollowUpFilterEntity;
import com.svw.dealerapp.ui.task.entity.FollowUpFilterIntentEntity;
import com.svw.dealerapp.ui.task.fragment.TaskECommerceFragment;
import com.svw.dealerapp.ui.task.fragment.TaskFollowUpFragment;
import com.svw.dealerapp.ui.task.fragment.TaskLeadsFragment;
import com.svw.dealerapp.ui.widget.NavBar;
import com.svw.dealerapp.ui.widget.NavTab;
import com.svw.dealerapp.util.DensityUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by qinshi on 5/25/2017.
 */

public class TaskActivity extends BaseActivity {

    public static final int FROM_HOME = 1001;
    public static final int FROM_MINE = 1002;

    private static final int FOLLOW_UP_FILTER_FOR_RESULT = 2001;

    private ViewPager viewPager;
    private NavBar navBar;
    private ImageView ivBack;
    private ImageView ivFilter;

    private List<BaseFragment> fragmentList = new ArrayList<>();
//    private TaskTrafficFragment taskTrafficFragment;
    private TaskECommerceFragment taskECommerceFragment;
    private TaskFollowUpFragment taskFollowUpFragment;
    private TaskLeadsFragment taskLeadsFragment;

    private int fromFlag;
    private FollowUpFilterIntentEntity intentEntity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task);

        viewPager = (ViewPager) findViewById(R.id.vp_view_pager);
        viewPager.setOffscreenPageLimit(2);
        navBar = (NavBar) findViewById(R.id.nb_nav_bar);
        ivBack = (ImageView) findViewById(R.id.iv_back);
        ivFilter = (ImageView) findViewById(R.id.iv_filter);

        initFragments();

        MainViewPagerAdapter adapter = new MainViewPagerAdapter(getSupportFragmentManager(), fragmentList);
        viewPager.setAdapter(adapter);

        initNavBarParams();

        dealIntent(getIntent());

        navBar.setOnTabChangeListener(new NavBar.OnTabChangeListener() {
            @Override
            public void onSelect(NavTab selectTab, int selectPosition) {
                selectTab.setTitleTextColor(getResources().getColor(R.color.resource_blue));
                selectTab.setTipTextColor(getResources().getColor(R.color.resource_blue));
                selectTab.setTipBackgroundDrawable(R.drawable.shape_second_tab_tip_round_select_bg);

                if(fromFlag == FROM_MINE && selectPosition == 2) {
                    ivFilter.setVisibility(View.VISIBLE);
                }else {
                    ivFilter.setVisibility(View.GONE);
                }
            }

            @Override
            public void onCancel(NavTab cancelTab, int cancelPosition) {
                cancelTab.setTitleTextColor(getResources().getColor(R.color.resource_main_text));
                cancelTab.setTipTextColor(getResources().getColor(R.color.resource_main_text));
                cancelTab.setTipBackgroundDrawable(R.drawable.shape_second_tab_tip_round_bg);
            }
        });

        ivBack.setOnClickListener(this);
        ivFilter.setOnClickListener(this);

        // 调用当前可见fragment的show，执行TalkingData的onPageStart
        fragmentList.get(viewPager.getCurrentItem()).show();
    }

    private int dealIntent(Intent intent){
        if(null != intent){
            int position = intent.getIntExtra("position", 0);
            if(position < fragmentList.size()){
                viewPager.setCurrentItem(position);
                navBar.getNavTab(position).setTitleTextColor(getResources().getColor(R.color.resource_blue));
                navBar.getNavTab(position).setTipTextColor(getResources().getColor(R.color.resource_blue));
                navBar.getNavTab(position).setTipBackgroundDrawable(R.drawable.shape_second_tab_tip_round_select_bg);
            }

            fromFlag = intent.getIntExtra("fromFlag", 0);

            return position;
        }
        return 0;
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        int position = dealIntent(intent);
        boolean isFromNotice = intent.getBooleanExtra("isFromNotice", false);
        if(isFromNotice && fragmentList.get(position) instanceof BaseListFragment) {
            ((BaseListFragment)fragmentList.get(position)).requestDataFromServer();
        }
    }

    private void initNavBarParams(){
        String[] tabTitles = new String[]{
            getResources().getString(R.string.task_tab_leads),
            getResources().getString(R.string.task_tab_e_commerce),
            getResources().getString(R.string.task_tab_follow_up)
        };

        int tipOffset = DensityUtil.dp2px(this, 18);
        int[] tipOffsetArr = new int[]{tipOffset, tipOffset, tipOffset};

        navBar.bindViewPager(viewPager, new NavBarParamsEntity(
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
                tipOffsetArr,
                getResources().getColor(R.color.resource_blue),
                false,
                true,
                true
        ), fragmentList);
    }

    private void initFragments(){
//        taskTrafficFragment = new TaskTrafficFragment();
        taskLeadsFragment = new TaskLeadsFragment();
        taskECommerceFragment = new TaskECommerceFragment();
        taskFollowUpFragment = new TaskFollowUpFragment();
//        fragmentList.add(taskTrafficFragment);
        fragmentList.add(taskLeadsFragment);
        fragmentList.add(taskECommerceFragment);
        fragmentList.add(taskFollowUpFragment);
    }

    /**
     * 设置Tab上圆圈里的字符
     * @param position
     * @param tipNumber
     */
    public void setTabTipNumber(int position, String tipNumber){
        if(navBar.getTabList().size() > position){
            try {
                if (Integer.parseInt(tipNumber) > 99) {
                    navBar.getNavTab(position).setTvTabTipText("...");
                } else {
                    navBar.getNavTab(position).setTvTabTipText(tipNumber);
                }
            }catch (Exception e){
                navBar.getNavTab(position).setTvTabTipText("...");
                e.printStackTrace();
            }
        }
    }

    /**
     * 加或减tipNumber
     * @param position
     * @param num
     */
    public void changeTipNumber(int position, int num){
        try{
            String tipNumber = String.valueOf(Integer.parseInt(navBar.getNavTab(position).getTabTipText()) + num);
            setTabTipNumber(position, tipNumber);
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取从哪个页面跳转过来的标记
     * @return
     */
    public int getFromFlag(){
        return fromFlag;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_back:
                finish();
                break;
            case R.id.iv_filter:
                Intent intent = new Intent(this, FollowUpFilterActivity.class);
                intent.putExtra("filterIntentEntity", intentEntity);
                startActivityForResult(intent, FOLLOW_UP_FILTER_FOR_RESULT);
                overridePendingTransition(R.anim.activity_traffic_filter_in, R.anim.activity_traffic_filter_out);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == FOLLOW_UP_FILTER_FOR_RESULT && null != data){
            FollowUpFilterEntity filterEntity = data.getParcelableExtra("filterEntity");
            if(fromFlag == FROM_HOME) {
                filterEntity.setIsHomePage("0");
            }else {
                filterEntity.setIsHomePage("1");
            }
            intentEntity = data.getParcelableExtra("filterIntentEntity");
            taskFollowUpFragment.requestDateAfterTakeFilter(filterEntity);
        }
    }
}
