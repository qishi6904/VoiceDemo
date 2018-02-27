package com.svw.dealerapp.ui.resource.presenter;

import android.content.Context;
import android.text.TextUtils;

import com.svw.dealerapp.entity.ResEntity;
import com.svw.dealerapp.entity.resource.FailedCustomerEntity;
import com.svw.dealerapp.entity.resource.SleepCustomerEntity;
import com.svw.dealerapp.mvpframe.listfragment.ListFragmentContract;
import com.svw.dealerapp.mvpframe.listfragment.ListFragmentPresenter;
import com.svw.dealerapp.mvpframe.listfragment.ToastObserver;
import com.svw.dealerapp.ui.resource.contract.FailedCustomerContract;
import com.svw.dealerapp.ui.resource.contract.SleepCustomerContract;
import com.svw.dealerapp.ui.resource.contract.YellowCardContract;
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

public class SleepCustomerPresenter extends ListFragmentPresenter<SleepCustomerEntity, SleepCustomerEntity.SleepCustomerInfoEntity>
    implements SleepCustomerContract.Presenter{

    private SleepCustomerContract.View fragmentView;
    private Subscription postActiveSubscription;
    private Subscription postVipCustomerSubscription;

    public SleepCustomerPresenter(ListFragmentContract.View<SleepCustomerEntity, SleepCustomerEntity.SleepCustomerInfoEntity> view, ListFragmentContract.Model<ResEntity<SleepCustomerEntity>> model) {
        super(view, model);
        fragmentView = (SleepCustomerContract.View) view;
    }

    @Override
    public void loadMore(SleepCustomerEntity sleepCustomerEntity) {
        List<SleepCustomerEntity.SleepCustomerInfoEntity> dataList = sleepCustomerEntity.getData();
        if(null != dataList && dataList.size() > 0){
            this.getDataList().addAll(dataList);

            dealMonthDataByRefresh();

            mView.loadMore();
        }else {
            mView.showLoadMoreNoData();
        }
    }

    @Override
    public void refresh(SleepCustomerEntity sleepCustomerEntity) {
        List<SleepCustomerEntity.SleepCustomerInfoEntity> dataList = sleepCustomerEntity.getData();
        if(null != dataList && dataList.size() > 0){
            this.getDataList().clear();
            this.getDataList().addAll(dataList);

            dealMonthDataByRefresh();

            fragmentView.setTabTipNumber(String.valueOf(sleepCustomerEntity.getPage().getTotalCount()));
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
    public void postActiveYellow(Map<String, Object> options, final int dealPosition) {
        if(mModel instanceof SleepCustomerContract.Model) {
            SleepCustomerContract.Model model = (SleepCustomerContract.Model) mModel;
            Observable observable = model.postActiveYellow(options)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread());

            ToastObserver observer = new ToastObserver<ResEntity<Object>>(mView, handler, handlerAction2) {

                @Override
                public void dealResult(ResEntity<Object> entity) {
                    if(null != entity && !TextUtils.isEmpty(entity.getRetCode()) && "200".equals(entity.getRetCode())){
//                        dataList.get(dealPosition).setOppStatusId("11510");
                        dataList.remove(dealPosition);
                        fragmentView.activeYellowCardSuccess(dealPosition);
                    }else {
                        fragmentView.activeYellowCardFail();
                    }
                }
            };

            postActiveSubscription = requestByToast(context, observable, observer, mView, handlerAction2);
        }
    }

    @Override
    public void postVipCustomer(Context context, String oppId, final String isKeyuser, final int dealPosition) {
        if(mModel instanceof SleepCustomerContract.Model) {
            SleepCustomerContract.Model yellowCardModel = (SleepCustomerContract.Model) mModel;
            Observable observable = yellowCardModel.postVipCustomer(oppId, isKeyuser)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread());

            ToastObserver observer = new ToastObserver<ResEntity<Object>>(mView, handler, handlerAction1) {

                @Override
                public void dealResult(ResEntity<Object> entity) {
                    if(null != entity && !TextUtils.isEmpty(entity.getRetCode()) && "200".equals(entity.getRetCode())){
                        dataList.get(dealPosition).setIsKeyuser(isKeyuser);
                        fragmentView.showSetVipSuccessToast(isKeyuser);
                        fragmentView.notifyListView(dealPosition);
                    }else {
                        fragmentView.showSetVipFailedToast(isKeyuser);
                    }
                }
            };

            postVipCustomerSubscription = requestByToast(context, observable, observer, mView, handlerAction1);
        }
    }

    @Override
    protected void doHandlerAction2() {    //激活黄卡请求超时后执行
        if(null != postActiveSubscription){
            postActiveSubscription.unsubscribe();
            mView.showTimeOutToast();
        }
        mView.hideLoadingDialog();
    }

    @Override
    protected void doHandlerAction1() {    //设置重点客户请求超时后执行
        if(null != postVipCustomerSubscription){
            postVipCustomerSubscription.unsubscribe();
            mView.showTimeOutToast();
        }
        mView.hideLoadingDialog();
    }

    @Override
    protected String getShowMonth(SleepCustomerEntity.SleepCustomerInfoEntity sleepCustomerInfoEntity) {
        return DateUtils.dateFormat("MM月", "yyyy-MM-dd'T'HH:mm:ss.SSSZ", sleepCustomerInfoEntity.getUpdateTime());
    }
}
