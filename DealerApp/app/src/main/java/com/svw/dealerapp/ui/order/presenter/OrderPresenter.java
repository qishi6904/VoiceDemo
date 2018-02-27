package com.svw.dealerapp.ui.order.presenter;

import com.svw.dealerapp.entity.ResEntity;
import com.svw.dealerapp.entity.order.CreateOrderEntity;
import com.svw.dealerapp.entity.order.QueryOrderDetailResponseEntity;
import com.svw.dealerapp.mvpframe.base.BaseObserver;
import com.svw.dealerapp.ui.order.contract.OrderContract;
import com.svw.dealerapp.util.JLog;

import java.util.Map;

import rx.Observer;

/**
 * Created by xupan on 28/08/2017.
 */

public class OrderPresenter extends OrderContract.Presenter {

    private static final String TAG = "OrderPresenter";

    public OrderPresenter(OrderContract.Model model, OrderContract.View view) {
        setVM(view, model);
    }

    private BaseObserver<ResEntity<Object>> mCreateOrderResultObserver = new BaseObserver<ResEntity<Object>>() {
        @Override
        public void onCompleted() {
            JLog.d(TAG, "onCompleted");
            mView.onRequestEnd();
        }

        @Override
        public void onError(Throwable e) {
            super.onError(e);
            JLog.d(TAG, "onError");
            mView.onRequestEnd();
            e.printStackTrace();
        }

        @Override
        public void onNext(ResEntity<Object> objectResEntity) {
            if ("200".equals(objectResEntity.getRetCode())) {
                mView.onCreateOrderSuccess();
            } else {
                mView.onCreateOrderFailure(objectResEntity.getRetMessage());
            }
        }
    };

    private BaseObserver<ResEntity<Object>> mCancelOrderResultObserver = new BaseObserver<ResEntity<Object>>() {
        @Override
        public void onCompleted() {
            JLog.d(TAG, "onCompleted");
            mView.onRequestEnd();
        }

        @Override
        public void onError(Throwable e) {
            super.onError(e);
            JLog.d(TAG, "onError");
            mView.onRequestEnd();
            e.printStackTrace();
        }

        @Override
        public void onNext(ResEntity<Object> objectResEntity) {
            if ("200".equals(objectResEntity.getRetCode())) {
                mView.onCancelOrderSuccess();
            } else {
                mView.onCancelOrderFailure(objectResEntity.getRetMessage());
            }
        }
    };

    private BaseObserver<ResEntity<QueryOrderDetailResponseEntity>> mQueryOrderDetailResultObserver = new BaseObserver<ResEntity<QueryOrderDetailResponseEntity>>() {
        @Override
        public void onCompleted() {
            mView.onRequestEnd();
        }

        @Override
        public void onError(Throwable e) {
            super.onError(e);
            mView.onRequestError("");
            e.printStackTrace();
        }

        @Override
        public void onNext(ResEntity<QueryOrderDetailResponseEntity> objectResEntity) {
            if ("200".equals(objectResEntity.getRetCode())) {
                mView.onQueryOrderDetailSuccess(objectResEntity.getRetData());
            } else {
                mView.onCreateOrderFailure(objectResEntity.getRetMessage());
            }
        }
    };

    private BaseObserver<ResEntity<Object>> mReportOtdOrderObserver = new BaseObserver<ResEntity<Object>>() {
        @Override
        public void onCompleted() {
            mView.onRequestEnd();
        }

        @Override
        public void onError(Throwable e) {
            super.onError(e);
            mView.onRequestError("");
            e.printStackTrace();
        }

        @Override
        public void onNext(ResEntity<Object> objectResEntity) {
            if ("200".equals(objectResEntity.getRetCode())) {
                mView.onReportOtdOrderSuccess();
            } else {
                mView.onReportOtdOrderFailure(objectResEntity.getRetMessage());
            }
        }
    };

    private BaseObserver<ResEntity<Object>> mEditOrderObserver = new BaseObserver<ResEntity<Object>>() {
        @Override
        public void onCompleted() {
            mView.onRequestEnd();
        }

        @Override
        public void onError(Throwable e) {
            super.onError(e);
            mView.onRequestError("");
            e.printStackTrace();
        }

        @Override
        public void onNext(ResEntity<Object> objectResEntity) {
            if ("200".equals(objectResEntity.getRetCode())) {
                mView.onEditOrderSuccess();
            } else {
                mView.onEditOrderFailure(objectResEntity.getRetMessage());
            }
        }
    };

    @Override
    public void createOrder(CreateOrderEntity entity) {
        mView.onRequestStart();
        mRxManager.add(mModel.createOrder(entity).subscribe(mCreateOrderResultObserver));
    }

    @Override
    public void editOrder(CreateOrderEntity entity) {
        mView.onRequestStart();
        mRxManager.add(mModel.editOrder(entity).subscribe(mEditOrderObserver));
    }

    @Override
    public void queryOrderDetail(Map<String, Object> options) {
        mView.onRequestStart();
        mRxManager.add(mModel.queryOrderDetail(options).subscribe(mQueryOrderDetailResultObserver));
    }

    @Override
    public void cancelOrder(String orderId) {
        mView.onRequestStart();
        mRxManager.add(mModel.cancelOrder(orderId).subscribe(mCancelOrderResultObserver));
    }

    @Override
    public void reportOtdOrder(String orderId) {
        mView.onRequestStart();
        mRxManager.add(mModel.reportOtdOrder(orderId).subscribe(mReportOtdOrderObserver));
    }
}
