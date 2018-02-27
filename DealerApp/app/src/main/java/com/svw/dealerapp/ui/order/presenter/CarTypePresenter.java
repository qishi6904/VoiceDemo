package com.svw.dealerapp.ui.order.presenter;

import com.svw.dealerapp.entity.ResEntity;
import com.svw.dealerapp.entity.newcustomer.CarTypesEntity;
import com.svw.dealerapp.mvpframe.base.BaseObserver;
import com.svw.dealerapp.ui.order.contract.CarTypeContract;
import com.svw.dealerapp.util.JLog;

import rx.Observer;

/**
 * Created by xupan on 09/08/2017.
 */

public class CarTypePresenter extends CarTypeContract.Presenter {
    private static final String TAG = "CarTypePresenter";

    public CarTypePresenter(CarTypeContract.Model model, CarTypeContract.View view) {
        setVM(view, model);
    }

    private BaseObserver<ResEntity<CarTypesEntity>> mCarTypeObserver = new BaseObserver<ResEntity<CarTypesEntity>>() {
        @Override
        public void onCompleted() {

        }

        @Override
        public void onError(Throwable e) {
            super.onError(e);
            JLog.d(TAG, "onError");
            e.printStackTrace();
        }

        @Override
        public void onNext(ResEntity<CarTypesEntity> carTypesEntityResEntity) {
            JLog.d(TAG, "onNext");
            JLog.d(TAG, "carTypesEntityResEntity" + carTypesEntityResEntity);
            if (carTypesEntityResEntity.getRetCode().equals("200")) {
                mView.onQueryCarTypeSuccess(carTypesEntityResEntity.getRetData());
            } else {
                mView.onQueryCarTypeFailure(carTypesEntityResEntity.getRetMessage());
            }
        }
    };

    @Override
    public void queryCarType(String carTypeId) {
        mRxManager.add(mModel.searchByCarType(carTypeId).subscribe(mCarTypeObserver));
    }
}
