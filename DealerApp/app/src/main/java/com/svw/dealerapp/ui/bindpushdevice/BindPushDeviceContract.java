package com.svw.dealerapp.ui.bindpushdevice;

import com.svw.dealerapp.entity.ResEntity;
import com.svw.dealerapp.mvpframe.BasePresenter;

import java.util.Map;

import rx.Observable;

/**
 * Created by lijinkui on 2017/7/7.
 */

public interface BindPushDeviceContract {

    interface Model {
        Observable<ResEntity<Object>> aliPushRegisterDevice(Map<String, Object> options);

        Observable<ResEntity<Object>> aliPushUnregisterDevice(Map<String, Object> options);
    }

    interface View {
        void onBindPushDeviceSuccess();

        void onBindPushDeviceFail();
    }

    abstract class Presenter extends BasePresenter<Model, View> {
        public abstract void aliPushRegisterDevice();

        public abstract void aliPushUnregisterDevice();
    }

}
