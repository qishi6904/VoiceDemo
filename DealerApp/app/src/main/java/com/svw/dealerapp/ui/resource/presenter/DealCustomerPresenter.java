package com.svw.dealerapp.ui.resource.presenter;

import com.svw.dealerapp.entity.ResEntity;
import com.svw.dealerapp.entity.resource.DealCustomerEntity;
import com.svw.dealerapp.entity.resource.OrderCustomerEntity;
import com.svw.dealerapp.mvpframe.listfragment.ListFragmentContract;
import com.svw.dealerapp.mvpframe.listfragment.ListFragmentPresenter;
import com.svw.dealerapp.ui.resource.contract.DealCustomerContract;
import com.svw.dealerapp.util.DateUtils;

import java.util.List;

/**
 * Created by qinshi on 7/28/2017.
 */

public class DealCustomerPresenter extends ListFragmentPresenter<DealCustomerEntity, DealCustomerEntity.DealCustomerInfoEntity> {

    private DealCustomerContract.View fragmentView;

    public DealCustomerPresenter(ListFragmentContract.View<DealCustomerEntity, DealCustomerEntity.DealCustomerInfoEntity> view, ListFragmentContract.Model<ResEntity<DealCustomerEntity>> model) {
        super(view, model);
        if(view instanceof DealCustomerContract.View){
            fragmentView = (DealCustomerContract.View) view;
        }
    }

    @Override
    public void loadMore(DealCustomerEntity dealCustomerEntity) {
        List<DealCustomerEntity.DealCustomerInfoEntity> dataList = dealCustomerEntity.getOrderList();
        if(null != dataList && dataList.size() > 0){
            this.getDataList().addAll(dataList);

            dealMonthDataByLoadMore();

            mView.loadMore();
        }else {
            mView.showLoadMoreNoData();
        }
    }

    @Override
    public void refresh(DealCustomerEntity dealCustomerEntity) {
        List<DealCustomerEntity.DealCustomerInfoEntity> dataList = dealCustomerEntity.getOrderList();
        if(null != dataList && dataList.size() > 0){
            this.getDataList().clear();
            this.getDataList().addAll(dataList);

            dealMonthDataByRefresh();

            fragmentView.setTabTipNumber(String.valueOf(dealCustomerEntity.getPage().getTotalCount()));
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
            fragmentView.setTabTipNumber("0");
        }
    }

    @Override
    protected String getShowMonth(DealCustomerEntity.DealCustomerInfoEntity dealCustomerInfoEntity) {
        return DateUtils.dateFormat("MM月", "yyyy-MM-dd'T'HH:mm:ss.SSSZ", dealCustomerInfoEntity.getUpdateTime());
    }
}
