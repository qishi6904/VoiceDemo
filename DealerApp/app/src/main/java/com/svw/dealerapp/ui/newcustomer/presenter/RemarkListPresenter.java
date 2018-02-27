package com.svw.dealerapp.ui.newcustomer.presenter;


import com.svw.dealerapp.entity.ResEntity;
import com.svw.dealerapp.entity.newcustomer.RemarkEntity;
import com.svw.dealerapp.mvpframe.listfragment.ListFragmentContract;
import com.svw.dealerapp.mvpframe.listfragment.ListFragmentPresenter;

import java.util.List;


/**
 * Created by qinshi on 7/14/2017.
 */

public class RemarkListPresenter  extends ListFragmentPresenter<RemarkEntity, RemarkEntity.RemarkEntityInfo> {

    public RemarkListPresenter(ListFragmentContract.View<RemarkEntity, RemarkEntity.RemarkEntityInfo> view, ListFragmentContract.Model<ResEntity<RemarkEntity>> model) {
        super(view, model);
    }

    @Override
    public void loadMore(RemarkEntity remarkEntity) {

    }

    @Override
    public void refresh(RemarkEntity remarkEntity) {
        List<RemarkEntity.RemarkEntityInfo> remarkList = remarkEntity.getList();
        if(null != remarkList && remarkList.size() > 0){
            this.dataList.clear();
            this.dataList.addAll(remarkList);
            mView.refresh();
        }else {
            switch (requestType) {
                case REQUEST_BY_PULL_DOWN:
                    dealNoDataByPullDown();
                    break;
                case REQUEST_BY_INIT:
                    mView.showNoDataLayout();
                    break;
            }
        }
    }
}
