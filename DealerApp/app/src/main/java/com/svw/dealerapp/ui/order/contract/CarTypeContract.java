package com.svw.dealerapp.ui.order.contract;

import com.svw.dealerapp.entity.ResEntity;
import com.svw.dealerapp.entity.newcustomer.CarTypesEntity;
import com.svw.dealerapp.mvpframe.BasePresenter;

import rx.Observable;

/**
 * Created by xupan on 09/08/2017.
 */

public interface CarTypeContract {

    interface Model {
        Observable<ResEntity<CarTypesEntity>> searchByCarType(String ecModelId);
    }

    interface View {
        void onQueryCarTypeSuccess(CarTypesEntity entity);

        void onQueryCarTypeFailure(String msg);
    }

    abstract class Presenter extends BasePresenter<Model, View> {
        public abstract void queryCarType(String carTypeId);
    }
}
