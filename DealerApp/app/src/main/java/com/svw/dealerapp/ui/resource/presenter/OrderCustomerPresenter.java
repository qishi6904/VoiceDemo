package com.svw.dealerapp.ui.resource.presenter;

import android.content.Context;
import android.text.TextUtils;

import com.svw.dealerapp.entity.ResEntity;
import com.svw.dealerapp.entity.resource.OrderCustomerEntity;
import com.svw.dealerapp.mvpframe.listfragment.ListFragmentContract;
import com.svw.dealerapp.mvpframe.listfragment.ListFragmentPresenter;
import com.svw.dealerapp.mvpframe.listfragment.ToastObserver;
import com.svw.dealerapp.ui.resource.contract.OrderCustomerContract;
import com.svw.dealerapp.util.DateUtils;

import java.util.List;
import java.util.Map;

import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by qinshi on 7/28/2017.
 */

public class OrderCustomerPresenter extends ListFragmentPresenter<OrderCustomerEntity, OrderCustomerEntity.OrderCustomerInfoEntity>
        implements OrderCustomerContract.Presenter {

    private OrderCustomerContract.View mFragmentView;
    private Subscription postCancelSubscription;

    public OrderCustomerPresenter(ListFragmentContract.View<OrderCustomerEntity, OrderCustomerEntity.OrderCustomerInfoEntity> view, ListFragmentContract.Model<ResEntity<OrderCustomerEntity>> model) {
        super(view, model);
        mFragmentView = (OrderCustomerContract.View) view;
    }

    @Override
    public void postCancelOrder(Context context, Map<String, Object> options, final int dealPosition) {
        if(mModel instanceof OrderCustomerContract.Model) {
            OrderCustomerContract.Model orderModel = (OrderCustomerContract.Model) mModel;

            Observable observable = orderModel.postCancelOrder(options)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread());

            ToastObserver observer = new ToastObserver<ResEntity<Object>>(mView, handler, handlerAction1) {

                @Override
                public void dealResult(ResEntity<Object> entity) {
                    if(null != entity && !TextUtils.isEmpty(entity.getRetCode()) && "200".equals(entity.getRetCode())){
//                        dataList.get(dealPosition).setOrderStatus("1");
                        dataList.remove(dealPosition);
                        mFragmentView.cancelSuccess(dealPosition);
                    }else {
                        mFragmentView.cancelFail();
                    }
                }
            };

            postCancelSubscription = requestByToast(context, observable, observer, mView, handlerAction1);
        }
    }

    @Override
    protected void doHandlerAction1() {
        if(null != postCancelSubscription){
            postCancelSubscription.unsubscribe();
            mView.showTimeOutToast();
        }
        mView.hideLoadingDialog();
    }

    @Override
    public void loadMore(OrderCustomerEntity orderCustomerEntity) {
        List<OrderCustomerEntity.OrderCustomerInfoEntity> dataList = orderCustomerEntity.getOrderList();
        if(null != dataList && dataList.size() > 0){
            this.getDataList().addAll(dataList);

            dealMonthDataByLoadMore();

            mView.loadMore();
        }else {
            mView.showLoadMoreNoData();
        }
    }

    @Override
    public void refresh(OrderCustomerEntity orderCustomerEntity) {
        List<OrderCustomerEntity.OrderCustomerInfoEntity> dataList = orderCustomerEntity.getOrderList();
        if(null != dataList && dataList.size() > 0){
            this.getDataList().clear();
            this.getDataList().addAll(dataList);

            dealMonthDataByRefresh();

            mFragmentView.setTabTipNumber(String.valueOf(orderCustomerEntity.getPage().getTotalCount()));
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
            mFragmentView.setTabTipNumber("0");
        }
    }

    @Override
    protected String getShowMonth(OrderCustomerEntity.OrderCustomerInfoEntity orderCustomerInfoEntity) {
        return DateUtils.dateFormat("MM月", "yyyy-MM-dd'T'HH:mm:ss.SSSZ", orderCustomerInfoEntity.getUpdateTime());
    }
}
