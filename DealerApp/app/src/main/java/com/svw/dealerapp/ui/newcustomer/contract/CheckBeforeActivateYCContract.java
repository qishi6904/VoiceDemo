package com.svw.dealerapp.ui.newcustomer.contract;

import com.svw.dealerapp.entity.ResEntity;
import com.svw.dealerapp.mvpframe.BasePresenter;

import java.util.Map;

import rx.Observable;

/**
 * Created by lijinkui on 2017/12/22.
 */

public interface CheckBeforeActivateYCContract {
    interface View {
        void checkBeforeActivateYCSuccess(String returnCode, String msg);

        void checkBeforeActivateYCFail(String msg);
    }

    interface Model {
        Observable<ResEntity<Object>> checkBeforeActivateYC(Map<String, Object> options);
    }

    abstract class Presenter extends BasePresenter<CheckBeforeActivateYCContract.Model, CheckBeforeActivateYCContract.View> {
        public abstract void checkBeforeActivateYC(Map<String, Object> options);
    }
}
