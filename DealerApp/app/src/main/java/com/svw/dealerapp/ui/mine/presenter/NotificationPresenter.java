package com.svw.dealerapp.ui.mine.presenter;

import android.content.Context;
import android.util.Log;

import com.svw.dealerapp.entity.ResEntity;
import com.svw.dealerapp.entity.mine.NotificationEntity;
import com.svw.dealerapp.entity.mine.NotificationEntity.NotificationInfoEntity;
import com.svw.dealerapp.mvpframe.base.BaseObserver;
import com.svw.dealerapp.mvpframe.listfragment.ListFragmentContract;
import com.svw.dealerapp.mvpframe.listfragment.ListFragmentPresenter;
import com.svw.dealerapp.mvpframe.listfragment.ToastObserver;
import com.svw.dealerapp.ui.mine.contract.NotificationContract;

import java.util.List;
import java.util.Map;

import rx.Observable;
import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by qinshi on 6/6/2017.
 */

public class NotificationPresenter extends ListFragmentPresenter<NotificationEntity, NotificationInfoEntity>
        implements NotificationContract.Presenter {

    private NotificationContract.View fragmentView;
    private Subscription setReadSubscription;
    private Subscription deleteSubscription;

    public NotificationPresenter(ListFragmentContract.View<NotificationEntity, NotificationInfoEntity> view, ListFragmentContract.Model<ResEntity<NotificationEntity>> model) {
        super(view, model);
        if(view instanceof NotificationContract.View){
            fragmentView = (NotificationContract.View) view;
        }
    }

    @Override
    public void loadMore(NotificationEntity notificationEntity) {
        List<NotificationInfoEntity> infoEntityList = notificationEntity.getNoticeList();
        if(null != infoEntityList && infoEntityList.size() > 0){
            this.getDataList().addAll(infoEntityList);
            mView.loadMore();
        }else {
            mView.showLoadMoreNoData();
        }
    }

    @Override
    public void refresh(NotificationEntity notificationEntity) {
        List<NotificationInfoEntity> infoEntityList = notificationEntity.getNoticeList();
        if(null != infoEntityList && infoEntityList.size() > 0){
            this.getDataList().clear();
            this.getDataList().addAll(infoEntityList);
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

    @Override
    public void setNotificationRead(Context context, Map<String, Object> options, boolean isShowLoading) {
        if(mModel instanceof NotificationContract.Model){
            NotificationContract.Model notificationModel = (NotificationContract.Model) mModel;
            if(!isShowLoading) {
                setReadSubscription = notificationModel.setNotificationRead(options)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new BaseObserver<ResEntity<Object>>() {
                            @Override
                            public void onCompleted() {

                            }

                            @Override
                            public void onError(Throwable e) {
                                super.onError(e);
                            }

                            @Override
                            public void onNext(ResEntity<Object> entity) {

                            }
                        });
                mRxManager.add(setReadSubscription);
            }else {
                Observable observable = notificationModel.setNotificationRead(options)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread());

                ToastObserver observer = new ToastObserver<ResEntity<Object>>(mView, handler, handlerAction1) {
                    @Override
                    public void dealResult(ResEntity<Object> entity) {
                        if (null != entity && "200".equals(entity.getRetCode())) {
                            fragmentView.setNotificationReadSuccess();
                        } else {
                            fragmentView.setNotificationReadFail();
                        }
                    }
                };

                setReadSubscription = requestByToast(context, observable, observer, mView, handlerAction1);
            }
        }
    }

    @Override
    public void postDeleteNotification(Context context, Map<String, Object> options) {
        if(mModel instanceof NotificationContract.Model){
            NotificationContract.Model notificationModel = (NotificationContract.Model) mModel;
            Observable observable = notificationModel.postDeleteNotification(options)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread());

            ToastObserver observer = new ToastObserver<ResEntity<Object>>(mView, handler, handlerAction2) {
                @Override
                public void dealResult(ResEntity<Object> entity) {
                    if(null != entity && "200".equals(entity.getRetCode())) {
                        fragmentView.deleteNotificationSuccess();
                    }else {
                        fragmentView.deleteNotificationFail();
                    }
                }
            };

            deleteSubscription = requestByToast(context, observable, observer, mView, handlerAction2);
        }
    }

    @Override
    protected void doHandlerAction1() {
        if(null != setReadSubscription){
            setReadSubscription.unsubscribe();
            mView.showTimeOutToast();
        }
        mView.hideLoadingDialog();
    }

    @Override
    protected void doHandlerAction2() {
        if(null != deleteSubscription){
            deleteSubscription.unsubscribe();
            mView.showTimeOutToast();
        }
        mView.hideLoadingDialog();
    }
}
