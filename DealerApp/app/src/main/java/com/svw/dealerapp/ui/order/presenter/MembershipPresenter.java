package com.svw.dealerapp.ui.order.presenter;

import com.svw.dealerapp.entity.ResEntity;
import com.svw.dealerapp.entity.order.CheckMembershipReqEntity;
import com.svw.dealerapp.entity.order.CheckMembershipResEntity;
import com.svw.dealerapp.mvpframe.base.BaseObserver;
import com.svw.dealerapp.ui.order.contract.MembershipContract;
import com.svw.dealerapp.util.JLog;

import rx.Observer;

/**
 * Created by xupan on 19/09/2017.
 */

public class MembershipPresenter extends MembershipContract.Presenter {
    private static final String TAG = "MembershipPresenter";

    public MembershipPresenter(MembershipContract.Model model, MembershipContract.View view) {
        setVM(view, model);
    }

    private BaseObserver<ResEntity<CheckMembershipResEntity>> observer = new BaseObserver<ResEntity<CheckMembershipResEntity>>() {
        @Override
        public void onCompleted() {
            mView.onRequestEnd();
        }

        @Override
        public void onError(Throwable e) {
            super.onError(e);
            mView.onRequestEnd();
        }

        @Override
        public void onNext(ResEntity<CheckMembershipResEntity> checkMembershipResEntityResEntity) {
            JLog.d(TAG, "onNext");
            if (checkMembershipResEntityResEntity.getRetCode().equals("200")) {
                mView.onCheckMembershipSuccess(checkMembershipResEntityResEntity.getRetData());
            } else {
                mView.onRequestError(checkMembershipResEntityResEntity.getRetMessage());
            }
        }
    };

    @Override
    public void checkMembership(CheckMembershipReqEntity option) {
        mView.onRequestStart();
        mRxManager.add(mModel.checkMembership(option).subscribe(observer));
    }
}
