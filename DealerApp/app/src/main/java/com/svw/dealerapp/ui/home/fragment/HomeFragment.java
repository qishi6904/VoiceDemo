package com.svw.dealerapp.ui.home.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.svw.dealerapp.R;
import com.svw.dealerapp.entity.home.HomeEntity;
import com.svw.dealerapp.entity.home.HomeEntity.HomeInfoEntity;
import com.svw.dealerapp.entity.report.ReportHomeEntity;
import com.svw.dealerapp.mvpframe.listfragment.BaseListFragment;
import com.svw.dealerapp.mvpframe.listfragment.ListFragmentPresenter;
import com.svw.dealerapp.ui.adapter.BaseRecyclerViewAdapter;
import com.svw.dealerapp.ui.adapter.StringCustomDialogAdapter;
import com.svw.dealerapp.ui.home.adapter.HomeAdapter;
import com.svw.dealerapp.ui.home.contract.HomeContract;
import com.svw.dealerapp.ui.home.entity.AutoPlayEntity;
import com.svw.dealerapp.ui.home.model.HomeModel;
import com.svw.dealerapp.ui.home.presenter.HomePresenter;
import com.svw.dealerapp.ui.mine.activity.ApproveActivity;
import com.svw.dealerapp.ui.mine.activity.ScheduleUpdateActivity;
import com.svw.dealerapp.ui.newcustomer.activity.CustomerDetailActivity;
import com.svw.dealerapp.ui.task.activity.TaskActivity;
import com.svw.dealerapp.ui.widget.AutoPlayLayout;
import com.svw.dealerapp.ui.widget.CustomDialog;
import com.svw.dealerapp.ui.widget.HomeHeaderLayout;
import com.svw.dealerapp.util.DensityUtil;
import com.svw.dealerapp.util.JLog;
import com.svw.dealerapp.util.TalkingDataUtils;
import com.svw.dealerapp.util.ToastUtils;
import com.svw.dealerapp.util.UserInfoUtils;
import com.svw.dealerapp.util.dbtools.PrivilegeDBUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static com.svw.dealerapp.global.Constants.E_HOME_APPROVAL_SUBVIEW;
import static com.svw.dealerapp.global.Constants.E_HOME_SCROLL_SUBVIEW;
import static com.svw.dealerapp.global.Constants.P_SA_APP_HOME;

/**
 * Created by lijinkui on 2017/5/2.
 */

@Deprecated
public class HomeFragment extends BaseListFragment<HomeEntity, HomeInfoEntity> implements HomeContract.View, View.OnClickListener {

    private static final int updateScheduleRequestCode = 1001;

    private HomeAdapter adapter;
    private CustomDialog cancelDialog;
    private HomeHeaderLayout hhlTraffic;
    private HomeHeaderLayout hhlApprove;
    private HomeHeaderLayout hhlECommerce;
    private HomeHeaderLayout hhlFollowUp;
    private TextView tvAutoPlayError;
    private LinearLayout llAutoPlayError;
    private AutoPlayLayout aplHome;
    private LinearLayout llNoDataLayout;
    private LinearLayout llAutoPlayOuter;
    private View headerView;
    private RelativeLayout rlTopTip;
    private TextView tvTopTipNum;
    private boolean isFirstStart = true;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new HomePresenter(this, new HomeModel());
    }

    @Override
    public BaseRecyclerViewAdapter getAdapter() {
        adapter = new HomeAdapter(getActivity(), presenter.getDataList());

        initHeader();

        rvRecyclerView.setBackgroundColor(getResources().getColor(R.color.page_bg));

        adapter.setOnBackViewClickListener(new HomeAdapter.OnBackViewClickListener() {
            @Override
            public void onUpdateClick(HomeEntity.HomeInfoEntity entity, int position) {
                TalkingDataUtils.onEvent(getActivity(), "更改预约", "首页");
                Intent intent = new Intent(getActivity(), ScheduleUpdateActivity.class);
                intent.putExtra("scheduleId", entity.getAppmId());
                intent.putExtra("dateTimeStr", entity.getAppmDateStr());
                intent.putExtra("appTypeId", entity.getAppmTypeId());
                intent.putExtra("position", position);
                startActivityForResult(intent, updateScheduleRequestCode);
            }

            @Override
            public void onCancelClick(HomeEntity.HomeInfoEntity entity, int position) {
                TalkingDataUtils.onEvent(getActivity(), "取消预约", "首页");
                showCancelDialog(entity, position);
            }

            @Override
            public void onCheckYCClick(HomeInfoEntity entity, int position) {
                TalkingDataUtils.onEvent(getActivity(), "查看黄卡", "首页");
                Intent intent = new Intent(getActivity(), CustomerDetailActivity.class);
                intent.putExtra("oppId", entity.getOppId());
                startActivity(intent);
            }
        });

        rlTopTip = (RelativeLayout) View.inflate(getActivity(), R.layout.ui_home_delay_top_view, null);
        tvTopTipNum = (TextView) rlTopTip.findViewById(R.id.tv_top_tip_num);
        ImageView topDelayBar = (ImageView) rlTopTip.findViewById(R.id.iv_delay_top_bar_close);

        addView(rlTopTip, 0);

        topDelayBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TalkingDataUtils.onEvent(getActivity(), "删除逾期记录提示", "首页");
                hideTopDelayTip();
            }
        });

        hhlApprove.setOnClickListener(this);
        hhlTraffic.setOnClickListener(this);
        hhlECommerce.setOnClickListener(this);
        hhlFollowUp.setOnClickListener(this);

        setFooterBgColor(getResources().getColor(R.color.page_bg));
        //check待审批是否有权限显示
        if( PrivilegeDBUtils.isCheck(P_SA_APP_HOME, E_HOME_APPROVAL_SUBVIEW)){
            hhlApprove.setVisibility(View.VISIBLE);
        } else {
            hhlApprove.setVisibility(View.GONE);
        }
        //checkKPI轮播是否有权限显示
        if( PrivilegeDBUtils.isCheck(P_SA_APP_HOME, E_HOME_SCROLL_SUBVIEW)){
            llAutoPlayOuter.setVisibility(View.VISIBLE);
            ((HomePresenter)presenter).getAutoPlayData(getActivity(), null);
        } else {
            llAutoPlayOuter.setVisibility(View.GONE);
        }

        return adapter;
    }

    private void initHeader(){
        headerView = View.inflate(getActivity(), R.layout.ui_home_header, null);
        hhlApprove = (HomeHeaderLayout) headerView.findViewById(R.id.hhl_approve);
        hhlTraffic = (HomeHeaderLayout) headerView.findViewById(R.id.hhl_traffic);
        hhlECommerce = (HomeHeaderLayout) headerView.findViewById(R.id.hhl_e_commerce);
        hhlFollowUp = (HomeHeaderLayout) headerView.findViewById(R.id.hhl_follow_up);
        aplHome = (AutoPlayLayout) headerView.findViewById(R.id.apl_home);
        tvAutoPlayError = (TextView) headerView.findViewById(R.id.tv_auto_play_error);
        llAutoPlayError = (LinearLayout) headerView.findViewById(R.id.ll_auto_play_error_outer);
        llNoDataLayout = (LinearLayout) headerView.findViewById(R.id.ll_error_layout);
        llAutoPlayOuter = (LinearLayout) headerView.findViewById(R.id.ll_auto_play_outer);

        hhlApprove.setIvHeaderIcon(R.mipmap.ic_home_approve);
        hhlApprove.setTvHeaderText(getResources().getString(R.string.home_header_approve));

        hhlTraffic.setIvHeaderIcon(R.mipmap.ic_home_traffic);
        hhlTraffic.setTvHeaderText(getResources().getString(R.string.home_header_traffic));

        hhlECommerce.setIvHeaderIcon(R.mipmap.ic_home_e_commerce_car);
        hhlECommerce.setTvHeaderText(getResources().getString(R.string.home_header_e_commerce));

        hhlFollowUp.setIvHeaderIcon(R.mipmap.ic_home_follow_up);
        hhlFollowUp.setTvHeaderText(getResources().getString(R.string.home_header_follow_up));

        rvRecyclerView.addHeaderView(headerView);
    }

    /**
     * 隐藏逾期任务的topbar
     */
    private void hideTopDelayTip(){
        rlTopTip.setVisibility(View.GONE);
    }

    /**
     * 设置显示逾期任务的topbar
     * @param topTip
     */
    private void setTopDelayTip(String topTip){
        rlTopTip.setVisibility(View.VISIBLE);
        tvTopTipNum.setText(topTip);
    }

    @Override
    public void showNoDataToast() {
        rvRecyclerView.setRefreshing(false);
    }

    private void showCancelDialog(final HomeEntity.HomeInfoEntity entity, final int position){
        if(null == cancelDialog) {
            StringCustomDialogAdapter dialogAdapter = new StringCustomDialogAdapter();
            cancelDialog = new CustomDialog(getActivity(), dialogAdapter);
            dialogAdapter.setContent(getResources().getString(R.string.mine_schedule_cancel_tip));
        }
        cancelDialog.setOnBtnClickListener(new CustomDialog.OnBtnClickListener() {
            @Override
            public void onCancelBtnClick() {
                cancelDialog.dismiss();
            }

            @Override
            public void onConfirmBtnClick() {
                HomePresenter homePresenter = (HomePresenter) presenter;
                Map<String, Object> options = new HashMap<>();
                options.put("appmId", entity.getAppmId());
                homePresenter.cancelSchedule(getActivity(), options, position);
                cancelDialog.dismiss();
            }
        });
        cancelDialog.show();
    }

    @Override
    public void showNoDataLayout() {
        hideLoadMoreFooterView();
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(headerView.getLayoutParams());
        params.height = rvRecyclerView.getHeight();
        if(llAutoPlayOuter.getVisibility() == View.VISIBLE){
            params.height = params.height + getResources().getDimensionPixelOffset(R.dimen.home_auto_play_item_height);
        }
        headerView.setLayoutParams(params);
        llNoDataLayout.setVisibility(View.VISIBLE);
        hideLoadMoreFooterView();

    }

    @Override
    public void refresh() {
        super.refresh();
        if(presenter.getDataList().size() == 0) {
            hideLoadMoreFooterView();
        }else {
            showFooter();
        }
        headerView.setVisibility(View.VISIBLE);
        if(presenter.getDataList().size() > 0) {
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(headerView.getLayoutParams());
            params.height = LinearLayout.LayoutParams.WRAP_CONTENT;
            headerView.setLayoutParams(params);
            llNoDataLayout.setVisibility(View.GONE);
        }
    }

    @Override
    protected void setShowErrorLayout() {
        super.setShowErrorLayout();
        headerView.setVisibility(View.GONE);
    }

    @Override
    public void cancelScheduleSuccess(int position) {
        ToastUtils.showToast(getResources().getString(R.string.mine_schedule_cancel_success));
        adapter.notifyDataSetChanged();
    }

    @Override
    public void cancelScheduleFailed() {
        ToastUtils.showToast(getResources().getString(R.string.mine_schedule_cancel_fail));
    }

    @Override
    public void setIconCountText(String trafficCount, String eCommerceCount, String followUpCount, String unapprovedCount) {
        hhlTraffic.setTvHeaderNumber(trafficCount);
        hhlECommerce.setTvHeaderNumber(eCommerceCount);
        hhlFollowUp.setTvHeaderNumber(followUpCount);
        hhlApprove.setTvHeaderNumber(unapprovedCount);
    }

    @Override
    public void setDelayTip(String trafficDelayNum, String eCommerceDelayNum, String followDelayNum) {

        int totalDelayNum = 0;

        if(!TextUtils.isEmpty(trafficDelayNum) && !"0".equals(trafficDelayNum)){
            hhlTraffic.setCircleTip(trafficDelayNum);
            try{
                totalDelayNum = totalDelayNum + Integer.parseInt(trafficDelayNum);
            }catch (Exception e){
                e.printStackTrace();
            }
        }else {
            hhlTraffic.hideCircleTip();
        }

        if(!TextUtils.isEmpty(eCommerceDelayNum) && !"0".equals(eCommerceDelayNum)){
            hhlECommerce.setCircleTip(eCommerceDelayNum);
            try{
                totalDelayNum = totalDelayNum + Integer.parseInt(eCommerceDelayNum);
            }catch (Exception e){
                e.printStackTrace();
            }
        }else {
            hhlECommerce.hideCircleTip();
        }

        if(!TextUtils.isEmpty(followDelayNum) && !"0".equals(followDelayNum)){
            hhlFollowUp.setCircleTip(followDelayNum);
            try{
                totalDelayNum = totalDelayNum + Integer.parseInt(followDelayNum);
            }catch (Exception e){
                e.printStackTrace();
            }
        }else {
            hhlFollowUp.hideCircleTip();
        }

        if(totalDelayNum > 0){
            setTopDelayTip(getResources().getString(R.string.home_delay_tip_one) +
                    String.valueOf(totalDelayNum) + getResources().getString(R.string.home_delay_tip_two));
        }else {
            rlTopTip.setVisibility(View.GONE);
        }

        // TODO: 8/25/2017
//        hhlApprove.setCircleTip("2");
    }

    @Override
    public void getAutoPlayDataSuccess(List<ReportHomeEntity.ReportHomeItemEntity> data) {
        aplHome.setData(data);
    }

    @Override
    public void getAutoPlayDataServerError() {
        llAutoPlayError.setVisibility(View.VISIBLE);
        aplHome.setVisibility(View.GONE);
        tvAutoPlayError.setText(getResources().getString(R.string.server_error));
    }

    @Override
    public void getAutoPlayDataServerTimeout() {
        llAutoPlayError.setVisibility(View.VISIBLE);
        aplHome.setVisibility(View.GONE);
        tvAutoPlayError.setText(getResources().getString(R.string.timeout_error));
    }

    @Override
    public void getAutoPlayDataServerEmpty() {
        llAutoPlayError.setVisibility(View.VISIBLE);
        aplHome.setVisibility(View.GONE);
        tvAutoPlayError.setText(getResources().getString(R.string.empty_data));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.hhl_traffic:
                jumpToTaskActivity(0);
                TalkingDataUtils.onEvent(getActivity(), "点击客源task", "首页");
                break;
            case R.id.hhl_e_commerce:
                jumpToTaskActivity(1);
                TalkingDataUtils.onEvent(getActivity(), "点击电商task", "首页");
                break;
            case R.id.hhl_follow_up:
                jumpToTaskActivity(2);
                TalkingDataUtils.onEvent(getActivity(), "点击跟进task", "首页");
                break;
            case R.id.hhl_approve:
                Intent intent = new Intent(getActivity(), ApproveActivity.class);
                startActivity(intent);
                break;

        }
    }

    private void jumpToTaskActivity(int position){
        Intent intent = new Intent(getActivity(), TaskActivity.class);
        intent.putExtra("position", position);
        intent.putExtra("fromFlag", TaskActivity.FROM_HOME);
        startActivity(intent);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case updateScheduleRequestCode:
                if(null != data){
                    int dealPosition = data.getIntExtra("dealPosition", -1);
                    if(dealPosition >= 0) {
                        String appmDateStr = data.getStringExtra("appmDateStr");
                        String appmTypeId = data.getStringExtra("appmTypeId");
                        HomePresenter homePresenter = (HomePresenter) presenter;
                        homePresenter.updateSchedule(dealPosition, appmDateStr, appmTypeId);
                        adapter.notifyDataSetChanged();
                    }
                }
                break;
        }
    }

    @Override
    public void onStart() {
        super.onStart();
//        if(!isFirstStart) {
//            rvRecyclerView.postDelayed(new Runnable() {
//                @Override
//                public void run() {
//                    presenter.getDataFromServer(getActivity(), "1", pageSize, "1", filterString, ListFragmentPresenter.REQUEST_BY_INIT, getMoreParams());
//                }
//            }, 1000);
//        }
//        isFirstStart = false;
    }

    @Override
    public void onPause() {
        super.onPause();
        stopAutoPlay();
    }

    @Override
    public void onResume() {
        super.onResume();
        startAutoPlay();
    }

    public void stopAutoPlay(){
        if(null != aplHome){
            aplHome.stopAutoPlay();
        }
    }

    public void startAutoPlay(){
        if(null != aplHome){
            aplHome.startAutoPlay();
        }
    }

    @Override
    public void onShow() {
        super.onShow();
        TalkingDataUtils.onPageStart(getActivity(), "首页");
        JLog.i("talkingDataFlag-show", "首页");
    }

    @Override
    public void onHide() {
        super.onHide();
        TalkingDataUtils.onPageEnd(getActivity(), "首页");
        JLog.i("talkingDataFlag-hide", "首页");
    }
}
