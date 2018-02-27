package com.svw.dealerapp.ui.resource.presenter;

import android.content.Context;
import android.text.TextUtils;

import com.svw.dealerapp.entity.ResEntity;
import com.svw.dealerapp.entity.resource.DealCustomerEntity;
import com.svw.dealerapp.entity.resource.PageBean;
import com.svw.dealerapp.entity.resource.YellowCardEntity;
import com.svw.dealerapp.entity.resource.YellowCardEntity.YellowCardInfoEntity;
import com.svw.dealerapp.mvpframe.listfragment.ListFragmentContract;
import com.svw.dealerapp.mvpframe.listfragment.ListFragmentPresenter;
import com.svw.dealerapp.mvpframe.listfragment.ToastObserver;
import com.svw.dealerapp.ui.resource.contract.YellowCardContract;
import com.svw.dealerapp.ui.resource.contract.YellowCardSearchContract;
import com.svw.dealerapp.util.DateUtils;

import java.util.List;
import java.util.Map;

import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by qinshi on 5/15/2017.
 */

public class YellowSearchFragmentPresenter extends ListFragmentPresenter<YellowCardEntity, YellowCardInfoEntity>
        implements YellowCardContract.Presenter {

    private YellowCardContract.VipView fragmentView;

    private Subscription postVipCustomerSubscription;
    private Subscription postActiveSubscription;

    public YellowSearchFragmentPresenter(ListFragmentContract.View<YellowCardEntity, YellowCardInfoEntity> view, ListFragmentContract.Model<ResEntity<YellowCardEntity>> model) {
        super(view, model);
        if(view instanceof YellowCardContract.VipView){
            fragmentView = (YellowCardContract.VipView) view;
        }
    }

    @Override
    public void loadMore(YellowCardEntity yellowCardEntity) {
        List<YellowCardInfoEntity> yellowInfoList = yellowCardEntity.getData();
        if(null != yellowInfoList && yellowInfoList.size() > 0){
            this.getDataList().addAll(yellowInfoList);

            dealMonthDataByLoadMore();

            mView.loadMore();
        }else {
            mView.showLoadMoreNoData();
        }
    }

    @Override
    public void refresh(YellowCardEntity yellowCardEntity) {
        List<YellowCardInfoEntity> yellowInfoList = yellowCardEntity.getData();
        if(null != yellowInfoList && yellowInfoList.size() > 0){
            this.getDataList().clear();
            this.getDataList().addAll(yellowInfoList);

            dealMonthDataByRefresh();

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
        if(null != yellowCardEntity && null != yellowCardEntity.getPage()) {
            PageBean pageBean = yellowCardEntity.getPage();
            YellowCardSearchContract.View view = (YellowCardSearchContract.View) mView;
            view.setSearchTotal(String.valueOf(pageBean.getTotalCount()));
        }
    }

    @Override
    public void postVipCustomer(Context context, String oppId, final String isKeyuser, final int dealPosition) {
        if(mModel instanceof YellowCardContract.Model) {
            YellowCardContract.Model yellowCardModel = (YellowCardContract.Model) mModel;
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
    public void postActiveYellow(Map<String, Object> options, final int dealPosition) {
        if(mModel instanceof YellowCardContract.Model) {
            YellowCardContract.Model yellowCardModel = (YellowCardContract.Model) mModel;
            Observable observable = yellowCardModel.postActiveYellow(options)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread());

            ToastObserver observer = new ToastObserver<ResEntity<Object>>(mView, handler, handlerAction2) {

                @Override
                public void dealResult(ResEntity<Object> entity) {
                    if(null != entity && !TextUtils.isEmpty(entity.getRetCode()) && "200".equals(entity.getRetCode())){
                        dataList.get(dealPosition).setOppStatusId("11510");
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
    protected void doHandlerAction1() {    //设置重点客户请求超时后执行
        if(null != postVipCustomerSubscription){
            postVipCustomerSubscription.unsubscribe();
            mView.showTimeOutToast();
        }
        mView.hideLoadingDialog();
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
    protected String getShowMonth(YellowCardInfoEntity yellowCardInfoEntity) {
        return DateUtils.dateFormat("MM月", "yyyy-MM-dd'T'HH:mm:ss.SSSZ", yellowCardInfoEntity.getUpdateTime());
    }
}
