package com.svw.dealerapp.ui.mine.presenter;

import com.svw.dealerapp.entity.ResEntity;
import com.svw.dealerapp.entity.mine.ApproveCompleteEntity;
import com.svw.dealerapp.entity.mine.ApproveCompleteEntity.ApproveCompleteInfoEntity;
import com.svw.dealerapp.mvpframe.listfragment.ListFragmentContract;
import com.svw.dealerapp.mvpframe.listfragment.ListFragmentPresenter;

import java.util.List;

/**
 * Created by qinshi on 6/6/2017.
 */

public class ApproveCompletePresenter extends ListFragmentPresenter<ApproveCompleteEntity, ApproveCompleteInfoEntity> {

    public ApproveCompletePresenter(ListFragmentContract.View<ApproveCompleteEntity, ApproveCompleteInfoEntity> view, ListFragmentContract.Model<ResEntity<ApproveCompleteEntity>> model) {
        super(view, model);
    }

    @Override
    public void loadMore(ApproveCompleteEntity approveCompleteEntity) {
        List<ApproveCompleteInfoEntity> approveList = approveCompleteEntity.getApprovals();
        if(null != approveList && approveList.size() > 0){
            this.getDataList().addAll(approveList);
            mView.loadMore();
        }else {
            mView.showLoadMoreNoData();
        }
    }

    @Override
    public void refresh(ApproveCompleteEntity approveCompleteEntity) {
        List<ApproveCompleteInfoEntity> approveList = approveCompleteEntity.getApprovals();
        if(null != approveList && approveList.size() > 0){
            this.getDataList().clear();
            this.getDataList().addAll(approveList);
            mView.refresh();
        }else {
            switch (requestType){
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
