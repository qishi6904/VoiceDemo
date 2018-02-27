package com.svw.dealerapp.ui.login.contract;

import com.svw.dealerapp.entity.SMResEntity;
import com.svw.dealerapp.entity.login.SMRefreshTokenEntity;
import com.svw.dealerapp.mvpframe.BasePresenter;

import java.util.Map;

import rx.Observable;

/**
 * Created by lijinkui on 2018/1/25.
 */

public interface SMRefreshTokenContract {
    interface Model {
        Observable<SMResEntity<SMRefreshTokenEntity>> smRefreshToken(Map<String, Object> options);
    }

    interface View {
        void getWebTokenSuccess(String result);
        void getWebTokenFail(String result);
    }

    abstract class Presenter extends BasePresenter<Model, View> {
        public abstract void smRefreshToken(Map<String, Object> options);
    }
}
