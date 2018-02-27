package com.svw.dealerapp.ui.login.contract;

import com.svw.dealerapp.entity.SMResEntity;
import com.svw.dealerapp.mvpframe.BasePresenter;

import java.util.Map;

import rx.Observable;

/**
 * Created by lijinkui on 2018/1/25.
 */

public interface SMResetPassContract {
    interface Model {
        Observable<SMResEntity<Object>> smResetPass(Map<String, Object> options);
    }

    interface View {
        /**
         * 重设密码成功刷新页面
         */
        void showResetPawSuccess();

        /**
         * 原始密码错误提示
         */
        void showOldPawErrorToast();
    }

    abstract class Presenter extends BasePresenter<Model, View> {
        public abstract void smResetPass(Map<String, Object> options);
    }
}
