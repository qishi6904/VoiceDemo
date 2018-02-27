package com.svw.dealerapp.ui.report.presenter;

import android.text.TextUtils;

import com.svw.dealerapp.R;
import com.svw.dealerapp.entity.ResEntity;
import com.svw.dealerapp.mvpframe.listfragment.ListFragmentContract;
import com.svw.dealerapp.mvpframe.listfragment.ListFragmentPresenter;
import com.svw.dealerapp.ui.report.contract.ReportHomeContract;
import com.svw.dealerapp.entity.report.ReportHomeEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by qinshi on 8/29/2017.
 */

public class ReportHomePresenter extends ListFragmentPresenter<ReportHomeEntity, ReportHomeEntity.ReportHomeItemEntity> implements ReportHomeContract.Presenter {

    private List<ReportHomeEntity.ReportHomeItemEntity> tabList = new ArrayList<>();
    private ReportHomeContract.View fragmentView;

    public ReportHomePresenter(ListFragmentContract.View<ReportHomeEntity, ReportHomeEntity.ReportHomeItemEntity> view, ListFragmentContract.Model<ResEntity<ReportHomeEntity>> model) {
        super(view, model);
        if(view instanceof ReportHomeContract.View){
            fragmentView = (ReportHomeContract.View) view;
        }
    }

    @Override
    public List<ReportHomeEntity.ReportHomeItemEntity> getTabList() {
        return tabList;
    }

    @Override
    public void loadMore(ReportHomeEntity reportHomeEntity) {

    }

    @Override
    public void refresh(ReportHomeEntity reportHomeEntity) {
        List<ReportHomeEntity.ReportHomeItemEntity> itemList = reportHomeEntity.getItemList();

        if(null != reportHomeEntity.getTabList() && reportHomeEntity.getTabList().size() > 0){
            tabList.clear();
            tabList.addAll(reportHomeEntity.getTabList());
            for(int i = 0; i < tabList.size(); i++){
                if("1001".equals(tabList.get(i).getTabId())){
                    if(!TextUtils.isEmpty(tabList.get(i).getUrl())){
                        tabList.get(i).setIconId(R.mipmap.ic_report_home_tab_ch);
                    }else {
                        tabList.get(i).setIconId(R.mipmap.ic_report_home_tab_ch_invalid);
                    }
                }else if("1002".equals(tabList.get(i).getTabId())){
                    if(!TextUtils.isEmpty(tabList.get(i).getUrl())){
                        tabList.get(i).setIconId(R.mipmap.ic_report_home_tab_xh);
                    }else {
                        tabList.get(i).setIconId(R.mipmap.ic_report_home_tab_xh_invalid);
                    }
                }else if("1003".equals(tabList.get(i).getTabId())){
                    if(!TextUtils.isEmpty(tabList.get(i).getUrl())){
                        tabList.get(i).setIconId(R.mipmap.ic_report_home_tab_benyue);
                    }else {
                        tabList.get(i).setIconId(R.mipmap.ic_report_home_tab_benyue_invalid);
                    }
                }else if("1004".equals(tabList.get(i).getTabId())){
                    if(!TextUtils.isEmpty(tabList.get(i).getUrl())){
                        tabList.get(i).setIconId(R.mipmap.ic_report_home_tab_ic);
                    }else {
                        tabList.get(i).setIconId(R.mipmap.ic_report_home_tab_ic_invalid);
                    }
                }else if("1005".equals(tabList.get(i).getTabId())){
                    if(!TextUtils.isEmpty(tabList.get(i).getUrl())){
                        tabList.get(i).setIconId(R.mipmap.ic_report_home_tab_dcc);
                    }else {
                        tabList.get(i).setIconId(R.mipmap.ic_report_home_tab_dcc_invalid);
                    }
                }
            }
            fragmentView.getTabListSuccess(tabList);
        }else {
            switch (requestType){
                case REQUEST_BY_PULL_DOWN:
                    fragmentView.getTabListEmpty();
                    break;
                case REQUEST_BY_INIT:
                    mView.showNoDataLayout();
                    break;
            }
        }

        if(null != itemList && itemList.size() > 0){
            this.getDataList().clear();
            this.getDataList().addAll(itemList);
            mView.refresh();
        }else {
            switch (requestType){
                case REQUEST_BY_PULL_DOWN:
                    mView.showNoDataToast();
                    break;
                case REQUEST_BY_INIT:
                    mView.showNoDataLayout();
                    break;
            }
        }
    }


}
