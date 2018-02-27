package com.svw.dealerapp.ui.task.presenter;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;

import com.svw.dealerapp.entity.ResEntity;
import com.svw.dealerapp.entity.task.BenefitEntity;
import com.svw.dealerapp.entity.task.TaskECommerceEntity;
import com.svw.dealerapp.entity.task.TaskECommerceEntity.TaskECommerceInfoEntity;
import com.svw.dealerapp.mvpframe.listfragment.ListFragmentContract;
import com.svw.dealerapp.mvpframe.listfragment.ListFragmentPresenter;
import com.svw.dealerapp.mvpframe.listfragment.ToastObserver;
import com.svw.dealerapp.ui.task.contract.ECommerceContract;

import java.util.List;
import java.util.Map;

import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by qinshi on 5/15/2017.
 */

public class TaskECommercePresenter extends ListFragmentPresenter<TaskECommerceEntity, TaskECommerceInfoEntity>
        implements ECommerceContract.Presenter {

    private ECommerceContract.View fragmentView;

    private Subscription postVipCustomerSubscription;
    private Subscription getBenefitDataSubscription;

    public TaskECommercePresenter(ListFragmentContract.View<TaskECommerceEntity, TaskECommerceInfoEntity> view, ListFragmentContract.Model<ResEntity<TaskECommerceEntity>> model) {
        super(view, model);
        if(view instanceof ECommerceContract.View){
            fragmentView = (ECommerceContract.View) view;
        }
    }

    @Override
    public void loadMore(TaskECommerceEntity yellowCardEntity) {
        List<TaskECommerceInfoEntity> eCommerceInfoList = yellowCardEntity.getData();
        if(null != eCommerceInfoList && eCommerceInfoList.size() > 0){
            this.getDataList().addAll(eCommerceInfoList);
            mView.loadMore();
        }else {
            mView.showLoadMoreNoData();
        }
    }

    @Override
    public void refresh(TaskECommerceEntity yellowCardEntity) {
        List<TaskECommerceInfoEntity> eCommerceInfoList = yellowCardEntity.getData();
        if(null != eCommerceInfoList && eCommerceInfoList.size() > 0){
            this.getDataList().clear();
            this.getDataList().addAll(eCommerceInfoList);
            mView.refresh();
            fragmentView.setTabTipNumber(String.valueOf(yellowCardEntity.getPage().getTotalCount()));
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
    public void postVipCustomer(Context context, String oppId, final String isKeyuser, final int dealPosition) {
        if(mModel instanceof ECommerceContract.Model) {
            ECommerceContract.Model eCommerceUpModel = (ECommerceContract.Model) mModel;
            Observable observable = eCommerceUpModel.postVipCustomer(oppId, isKeyuser)
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
    public void getBenefitDate(Context context, Map<String, Object> options) {
        if(mModel instanceof ECommerceContract.Model) {
            ECommerceContract.Model eCommerceUpModel = (ECommerceContract.Model) mModel;
            Observable observable = eCommerceUpModel.getBenefitDate(options)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread());

            ToastObserver observer = new ToastObserver<ResEntity<BenefitEntity>>(mView, handler, handlerAction2){

                @Override
                public void dealResult(ResEntity<BenefitEntity> entity) {
                    if(null != entity && !TextUtils.isEmpty(entity.getRetCode())){
                        if("200".equals(entity.getRetCode())) {
                            if (!TextUtils.isEmpty(entity.getRetData().getRight())) {
                                fragmentView.getBenefitDataSuccess(entity.getRetData().getRight());
                            } else {
                                fragmentView.getBenefitDataEmpty();
                            }
                        }else if("019002".equals(entity.getRetCode())){
                            fragmentView.getBenefitDataEmpty();
                        }else {
                            fragmentView.getBenefitDataFail();
                        }
                    }else {
                        fragmentView.getBenefitDataFail();
                    }
                }
            };

            getBenefitDataSubscription = requestByToast(context, observable, observer, mView, handlerAction2);
        }
    }

    @Override
    protected void doHandlerAction1() {     //设置重点客户请求超时后执行
        if(null != postVipCustomerSubscription){
            postVipCustomerSubscription.unsubscribe();
            mView.showTimeOutToast();
        }
        mView.hideLoadingDialog();
    }

    @Override
    protected void doHandlerAction2() {     //设置重点客户请求超时后执行
        if(null != getBenefitDataSubscription){
            getBenefitDataSubscription.unsubscribe();
            mView.showTimeOutToast();
        }
        mView.hideLoadingDialog();
    }
}
