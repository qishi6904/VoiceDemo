package com.svw.dealerapp.ui.login.contract;

import com.svw.dealerapp.entity.SMResEntity;
import com.svw.dealerapp.entity.login.SMUploadImageEntity;
import com.svw.dealerapp.mvpframe.BasePresenter;

import java.io.File;

import okhttp3.MultipartBody;
import rx.Observable;

/**
 * Created by lijinkui on 2018/1/25.
 */

public interface SMUploadImageContract {
    interface View {
        /**
         * 更改头像成功
         */
        void updateHeaderSuccess(String url);

        /**
         * 更改头像失败
         */
        void updateHeaderFail(String msg);
    }

    interface Model{
        /**
         * 更新头像
         * @param body
         * @return
         */
        Observable<SMResEntity<String>> smUploadImage(MultipartBody.Part body);
    }

    abstract class Presenter extends BasePresenter<Model, View> {
        /**
         * 更新头像
         * @param file
         */
        public abstract void smUploadImage(File file);
    }
}
