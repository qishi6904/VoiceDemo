package com.svw.dealerapp.ui.mine.presenter;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;

import com.svw.dealerapp.DealerApp;
import com.svw.dealerapp.entity.ResEntity;
import com.svw.dealerapp.entity.mine.LogoutEntity;
import com.svw.dealerapp.entity.mine.MineHomeEntity;
import com.svw.dealerapp.entity.mine.UploadEntity;
import com.svw.dealerapp.mvpframe.BasePresenter;
import com.svw.dealerapp.mvpframe.base.BaseObserver;
import com.svw.dealerapp.ui.mine.contract.MineContract;
import com.svw.dealerapp.util.dbtools.DBHelper;
import com.svw.dealerapp.util.NetworkUtils;

import java.io.File;
import java.lang.ref.WeakReference;
import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by qinshi on 6/7/2017.
 */

public class MinePresenter extends MineContract.Presenter {

    public static final int REQUEST_BY_INIT = 1001;
    public static final int REQUEST_BY_PULL_DOWN = 1002;

    private static final int getMineDataTimeOutAction = 2001;
    private static final int networkErrorAction = 2002;
    private static final int timeout = 10000;

    private Subscription getDataSubscription;
    private Subscription updateHeaderSubscription;
    private Subscription logoutSubscription;

    private MinePresenterHandler handler;

    public MinePresenter(MineContract.Model model, MineContract.View view){
        this.mView = view;
        this.mModel = model;
        handler = new MinePresenterHandler(this);
    }


    @Override
    public void updateResetHeader(File file) {
        RequestBody requestBody = RequestBody.create(MultipartBody.FORM,file);
        MultipartBody.Part body = MultipartBody.Part.createFormData("file",file.getName(),requestBody);
        updateHeaderSubscription = mModel.updateResetHeader(body)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<ResEntity<UploadEntity>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        mView.updateHeaderFail();
                    }

                    @Override
                    public void onNext(ResEntity<UploadEntity> entity) {
                        if(null != entity && !TextUtils.isEmpty(entity.getRetCode()) && "200".equals(entity.getRetCode())){
                            mView.updateHeaderSuccess(entity.getRetData().getUrl());
                        }
                    }
                });
        mRxManager.add(updateHeaderSubscription);
    }

    @Override
    public void getMineHomeData(Context context, Map<String, Object> options, final int requestType) {

        if(requestType == REQUEST_BY_PULL_DOWN ){
            if(!NetworkUtils.isNetworkAvailable(context)) {
                mView.showNetWorkErrorToast();
                handler.sendEmptyMessageDelayed(networkErrorAction, 500);
                return;
            }
            handler.sendEmptyMessageDelayed(getMineDataTimeOutAction, timeout);
        }

        getDataSubscription = mModel.getMineHomeData(options)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<ResEntity<MineHomeEntity>>(){

                    @Override
                    public void onCompleted() {
                        handler.removeMessages(getMineDataTimeOutAction);
                        if(requestType == REQUEST_BY_PULL_DOWN){
                            mView.setHeaderRefreshing(false);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        handler.removeMessages(getMineDataTimeOutAction);
                        if(requestType == REQUEST_BY_PULL_DOWN){
                            mView.showServerErrorToast();
                            mView.setHeaderRefreshing(false);
                        }
                    }

                    @Override
                    public void onNext(ResEntity<MineHomeEntity> entity) {
                        handler.removeMessages(getMineDataTimeOutAction);
                        if(null != entity && !TextUtils.isEmpty(entity.getRetCode()) && "200".equals(entity.getRetCode())){
                            if(null != entity.getRetData()) {
                                mView.getMineDataSuccess(entity.getRetData());
                            }else {
                                if(requestType == REQUEST_BY_PULL_DOWN) {
                                    mView.getMineDataFail();
                                }
                            }
                        }else {
                            if(requestType == REQUEST_BY_PULL_DOWN) {
                                mView.getMineDataFail();
                            }
                        }
                        mView.setHeaderRefreshing(false);
                    }
                });
        mRxManager.add(getDataSubscription);
    }

    @Override
    public void logout() {
        logoutSubscription = mModel.logout()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<ResEntity<LogoutEntity>>(){

                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        mView.logoutFail();
                    }

                    @Override
                    public void onNext(ResEntity<LogoutEntity> entity) {
                        if(null != entity && !TextUtils.isEmpty(entity.getRetCode()) && "200".equals(entity.getRetCode())){
                            cleanUserInfo();
                            mView.logoutSuccess();
                        }
                    }
                });
        mRxManager.add(logoutSubscription);
    }

    private void cleanUserInfo(){
        DBHelper dbHelper = DealerApp.dbHelper;
        dbHelper.delete("User",null,null);
    }

    protected class MinePresenterHandler extends Handler {

        WeakReference<BasePresenter> weakReference;

        MinePresenterHandler(BasePresenter weakReference) {
            this.weakReference = new WeakReference<>(weakReference);
        }

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case getMineDataTimeOutAction:
                    mView.setHeaderRefreshing(false);
                    mView.showTimeOutToast();
                    break;
                case networkErrorAction:
                    mView.setHeaderRefreshing(false);
                    break;
            }
        }
    }

}
