package com.svw.dealerapp.ui.login.contract;

import com.svw.dealerapp.entity.SMResEntity;
import com.svw.dealerapp.entity.login.SMUserInfoEntity;
import com.svw.dealerapp.mvpframe.BasePresenter;

import java.util.Map;

import rx.Observable;

/**
 * Created by lijinkui on 2018/1/25.
 */

public interface SMUserInfoContract {
    interface Model {
        Observable<SMResEntity<SMUserInfoEntity>> smUserInfo(Map<String, Object> options);
    }

    interface View {
        void onUserInfoSuccess(SMUserInfoEntity smUserInfoEntity);

        void onUserInfoError(String msg);
    }

    abstract class Presenter extends BasePresenter<Model, View> {
        public abstract void smUserInfo(Map<String, Object> options);
    }
}
