package com.svw.dealerapp.ui.login.contract;

import com.svw.dealerapp.entity.SMResEntity;
import com.svw.dealerapp.entity.login.SMLoginEntity;
import com.svw.dealerapp.mvpframe.BasePresenter;

import java.util.Map;

import rx.Observable;

/**
 * Created by lijinkui on 2018/1/25.
 */

public interface SMLoginContract {
    interface Model {
        Observable<SMResEntity<SMLoginEntity>> smLogin(Map<String, Object> options);
    }

    interface View {
        void onLoginSuccess(boolean isNewUser, SMResEntity<SMLoginEntity> smLoginEntitySMResEntity);

        void onLoginError(String msg);
    }

    abstract class Presenter extends BasePresenter<Model, View> {
        public abstract void smLogin(Map<String, Object> options);
    }
}
