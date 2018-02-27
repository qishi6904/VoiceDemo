package com.svw.dealerapp.ui.resource.presenter;

import com.svw.dealerapp.entity.SMResEntity;
import com.svw.dealerapp.entity.resource.SMYCTransferSalesEntity;
import com.svw.dealerapp.entity.resource.TransferDataEntity;
import com.svw.dealerapp.mvpframe.listfragment.ListFragmentContract;
import com.svw.dealerapp.mvpframe.listfragment.SMListFragmentPresenter;

import java.util.List;

/**
 * Created by qinshi on 11/21/2017.
 */

public class SalesFilterListPresenter extends SMListFragmentPresenter<TransferDataEntity, SMYCTransferSalesEntity> {

    public SalesFilterListPresenter(ListFragmentContract.View<TransferDataEntity, SMYCTransferSalesEntity> view, ListFragmentContract.Model<SMResEntity<TransferDataEntity>> model) {
        super(view, model);
    }

    @Override
    public void loadMore(TransferDataEntity ycTransferSalesEntities) {

    }

    @Override
    public void refresh(TransferDataEntity entity) {
        if(null != entity && null != entity.getData() && entity.getData().size() > 0){
            this.getDataList().clear();
            this.getDataList().addAll(entity.getData());
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
