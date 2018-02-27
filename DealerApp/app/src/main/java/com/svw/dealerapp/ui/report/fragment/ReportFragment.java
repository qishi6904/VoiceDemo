package com.svw.dealerapp.ui.report.fragment;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.svw.dealerapp.DealerApp;
import com.svw.dealerapp.R;
import com.svw.dealerapp.global.Constants;
import com.svw.dealerapp.mvpframe.listfragment.BaseListFragment;
import com.svw.dealerapp.ui.adapter.BaseRecyclerViewAdapter;
import com.svw.dealerapp.ui.login.LoginActivity;
import com.svw.dealerapp.ui.report.activity.SubReportActivity;
import com.svw.dealerapp.ui.report.adapter.ReportItemAdapter;
import com.svw.dealerapp.ui.report.contract.ReportHomeContract;
import com.svw.dealerapp.entity.report.ReportHomeEntity;
import com.svw.dealerapp.ui.report.model.ReportHomeModel;
import com.svw.dealerapp.ui.report.presenter.ReportHomePresenter;
import com.svw.dealerapp.util.JLog;
import com.svw.dealerapp.util.ScreenUtils;
import com.svw.dealerapp.util.TalkingDataUtils;
import com.svw.dealerapp.util.ToastUtils;


import java.util.List;

/**
 * Created by qinshi on 8/29/2017.
 */

public class ReportFragment extends BaseListFragment<ReportHomeEntity, ReportHomeEntity.ReportHomeItemEntity>
    implements ReportHomeContract.View{

    private View headerView;
    private LinearLayout llTabContainer;
    private LinearLayout llNoItemDataLayout;
    private TextView tvTabEmpty;

    private ReportHomeContract.Presenter reportPresenter;
    private ReportItemAdapter reportItemAdapter;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new ReportHomePresenter(this, new ReportHomeModel());
    }

    @Override
    public BaseRecyclerViewAdapter getAdapter() {

        if(presenter instanceof ReportHomeContract.Presenter){
            reportPresenter = (ReportHomeContract.Presenter) presenter;
        }

        rvRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));

        initHeader();

        reportItemAdapter = new ReportItemAdapter(getActivity(), presenter.getDataList());

        reportItemAdapter.setOnItemClickListener(new ReportItemAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, ReportHomeEntity.ReportHomeItemEntity entity, int position) {
                goToSubReport(entity.getUrl());
                TalkingDataUtils.onEvent(getActivity(),entity.getName(),"报告首页");
            }
        });

        return reportItemAdapter;
    }

    private void initHeader(){
        headerView = View.inflate(getActivity(), R.layout.ui_report_home_header, null);
        llTabContainer = (LinearLayout) headerView.findViewById(R.id.ll_tab_container);
        llNoItemDataLayout = (LinearLayout) headerView.findViewById(R.id.ll_error_layout);
        tvTabEmpty = (TextView) headerView.findViewById(R.id.tv_tab_empty);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);

        rvRecyclerView.addHeaderView(headerView);

    }

    @Override
    public void showNoDataLayout() {
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(headerView.getLayoutParams());
        params.height = rvRecyclerView.getHeight();
        headerView.setLayoutParams(params);
        llNoItemDataLayout.setVisibility(View.VISIBLE);
    }

    @Override
    public void refresh() {
        super.refresh();
        headerView.setVisibility(View.VISIBLE);
        if(presenter.getDataList().size() > 0) {
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(headerView.getLayoutParams());
            params.height = LinearLayout.LayoutParams.WRAP_CONTENT;
            headerView.setLayoutParams(params);
            llNoItemDataLayout.setVisibility(View.GONE);
        }

        hideFooter();

    }

    @Override
    protected void setShowErrorLayout() {
        super.setShowErrorLayout();
        headerView.setVisibility(View.GONE);
    }

    @Override
    public void getTabListSuccess(final List<ReportHomeEntity.ReportHomeItemEntity> tabList) {
        int tabWidth = ScreenUtils.getScreenWidth(getActivity()) / tabList.size();
        if(tabList.size() > 5) {
            tabWidth = ScreenUtils.getScreenWidth(getActivity()) / 5;
        }
        for(int i = 0; i < tabList.size(); i++){
            View childView = null;
            if(llTabContainer.getChildCount() > i){
                childView = llTabContainer.getChildAt(i);
            }else {
                childView = View.inflate(getActivity(), R.layout.item_report_home_header_tab, null);
                llTabContainer.addView(childView);
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(childView.getLayoutParams());
                layoutParams.width = tabWidth;
                childView.setLayoutParams(layoutParams);
            }

            TextView tvName = (TextView) childView.findViewById(R.id.tv_tab_name);
            SimpleDraweeView sdvIcon = (SimpleDraweeView) childView.findViewById(R.id.sdv_tab_icon);

            tvName.setText(tabList.get(i).getName());
            Uri uri = null;
            if(TextUtils.isEmpty(tabList.get(i).getIconUrl())){
                uri = Uri.parse("res://" + getActivity().getPackageName() + "/" + tabList.get(i).getIconId());
            }else {
                //设置来源图标
                uri = Uri.parse(tabList.get(i).getIconUrl());
            }
            if(null != uri) {
                DraweeController draweeController =
                        Fresco.newDraweeControllerBuilder()
                                .setUri(uri)
                                .setAutoPlayAnimations(false)
                                .build();
                sdvIcon.setController(draweeController);
            }

            childView.setTag(i);

            childView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = (int) v.getTag();
                    goToSubReport(tabList.get(position).getUrl());
                    TalkingDataUtils.onEvent(getActivity(),tabList.get(position).getName(),"报告首页");
                }
            });
        }
        tvTabEmpty.setVisibility(View.GONE);
    }

    @Override
    public void getTabListEmpty() {
        tvTabEmpty.setVisibility(View.VISIBLE);
    }

    private void goToSubReport(String url){
        if(!TextUtils.isEmpty(url)){
            //check current env
            url = Constants.API_BASE_URL_REPORT_SERVICE+url;
            Intent intent = new Intent(getActivity(), SubReportActivity.class);
            intent.putExtra("URL", url);
            startActivity(intent);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onShow() {
        super.onShow();
        TalkingDataUtils.onPageStart(getActivity(), "报告");
        JLog.i("talkingDataFlag-show", "报告");
    }

    @Override
    public void onHide() {
        super.onHide();
        TalkingDataUtils.onPageEnd(getActivity(), "报告");
        JLog.i("talkingDataFlag-hide", "报告");
    }
}
