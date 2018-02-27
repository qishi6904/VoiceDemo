package com.svw.dealerapp.ui.order.presenter;

import com.svw.dealerapp.entity.ResEntity;
import com.svw.dealerapp.entity.order.AppraiserEntity;
import com.svw.dealerapp.mvpframe.listfragment.ListFragmentContract;
import com.svw.dealerapp.mvpframe.listfragment.ListFragmentPresenter;

import java.util.List;

/**
 * Created by qinshi on 11/22/2017.
 */

public class AppraiserPresenter  extends ListFragmentPresenter<AppraiserEntity, AppraiserEntity.AppraiserInfoEntity> {
    public AppraiserPresenter(ListFragmentContract.View<AppraiserEntity, AppraiserEntity.AppraiserInfoEntity> view, ListFragmentContract.Model<ResEntity<AppraiserEntity>> model) {
        super(view, model);
    }

    @Override
    public void loadMore(AppraiserEntity appraiserEntity) {

    }

    @Override
    public void refresh(AppraiserEntity appraiserEntity) {
        List<AppraiserEntity.AppraiserInfoEntity> dataList = appraiserEntity.getData();
        if(null != dataList && dataList.size() > 0){
            this.getDataList().clear();
            this.getDataList().addAll(dataList);
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
