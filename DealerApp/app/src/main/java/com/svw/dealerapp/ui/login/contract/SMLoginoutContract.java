package com.svw.dealerapp.ui.login.contract;

import com.svw.dealerapp.entity.SMResEntity;
import com.svw.dealerapp.entity.login.SMLoginoutEntity;
import com.svw.dealerapp.mvpframe.BasePresenter;

import java.util.Map;

import rx.Observable;

/**
 * Created by lijinkui on 2018/1/25.
 */

public interface SMLoginoutContract {

    interface Model {
        Observable<SMResEntity<Object>> smLoginout(Map<String, Object> options);
    }

    interface View {
        void onLogoutSuccess();

        void onLogoutFail();
    }

    abstract class Presenter extends BasePresenter<Model, View> {
        public abstract void smLoginout(Map<String, Object> options);
    }

}
