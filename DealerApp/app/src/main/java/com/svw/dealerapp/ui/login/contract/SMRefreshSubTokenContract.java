package com.svw.dealerapp.ui.login.contract;

import com.svw.dealerapp.entity.SMResEntity;
import com.svw.dealerapp.entity.login.SMRefreshSubTokenEntity;
import com.svw.dealerapp.mvpframe.BasePresenter;

import java.util.Map;

import rx.Observable;

/**
 * Created by lijinkui on 2018/1/25.
 */

public interface SMRefreshSubTokenContract {
    interface Model {
        Observable<SMResEntity<SMRefreshSubTokenEntity>> smRefreshSubToken(Map<String, Object> options);
    }

    interface View {
        void getWebTokenSuccess(String result);
        void getWebTokenFail(String result);
    }

    abstract class Presenter extends BasePresenter<Model, View> {
        public abstract void smRefreshSubToken(Map<String, Object> options);
    }
}
