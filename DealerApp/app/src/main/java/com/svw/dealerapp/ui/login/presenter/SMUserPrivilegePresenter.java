package com.svw.dealerapp.ui.login.presenter;

import android.text.TextUtils;

import com.svw.dealerapp.entity.SMResEntity;
import com.svw.dealerapp.entity.login.SMUserPrivilegeByAppIdEntity;
import com.svw.dealerapp.mvpframe.listfragment.ToastObserver;
import com.svw.dealerapp.ui.login.contract.SMUserPrivilegeContract;

import java.util.List;
import java.util.Map;

import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by lijinkui on 2018/1/25.
 */

public class SMUserPrivilegePresenter extends SMUserPrivilegeContract.Presenter {

    private Subscription subscription;

    public SMUserPrivilegePresenter(SMUserPrivilegeContract.View view, SMUserPrivilegeContract.Model model) {
        super(view, model);
    }

    @Override
    public void getSmUserPrivilege(Map<String, Object> options) {

        Observable observable = mModel.smUserPrivilege(options)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());

        ToastObserver observer = new ToastObserver<SMResEntity<List<SMUserPrivilegeByAppIdEntity>>>(mView, handler, handlerAction1) {
            @Override
            public void dealResult(SMResEntity<List<SMUserPrivilegeByAppIdEntity>> entity) {
                if(null != entity && "200".equals(entity.getStatus())) {
                    final List<SMUserPrivilegeByAppIdEntity> dataList = entity.getData();
                    if(null != dataList && dataList.size() > 0) {
                        mView.onUserPrivilegeSuccess(dataList);
                    }else {
                        mView.onUserPrivilegeError("权限为空");
                    }
                }else {
                    if(null != entity && !TextUtils.isEmpty(entity.getMessage())) {
                        mView.onUserPrivilegeError(entity.getMessage());
                    }
                }
            }
        };

        subscription = requestByToast(context, observable, observer, mView, handlerAction1);
    }

    @Override
    protected void doHandlerAction1() {
        if(null != subscription){
            subscription.unsubscribe();
        }
        mView.showTimeOutToast();
    }
}
