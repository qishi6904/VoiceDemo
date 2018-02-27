package com.svw.dealerapp.ui.mine.contract;

import android.content.Context;

import com.svw.dealerapp.entity.ResEntity;
import com.svw.dealerapp.entity.mine.LogoutEntity;
import com.svw.dealerapp.entity.mine.MineHomeEntity;
import com.svw.dealerapp.entity.mine.UploadEntity;
import com.svw.dealerapp.mvpframe.BasePresenter;

import java.io.File;
import java.util.Map;

import okhttp3.MultipartBody;
import rx.Observable;

/**
 * Created by qinshi on 6/6/2017.
 */

public interface MineContract {

    interface View {
        /**
         * 更改头像成功
         */
        void updateHeaderSuccess(String url);

        /**
         * 更改头像失败
         */
        void updateHeaderFail();

        /**
         * 获取 我的 页面数据成功
         */
        void getMineDataSuccess(MineHomeEntity entity);


        /**
         * 获取 我的 页面数据成功
         */
        void getMineDataFail();

        /**
         * 服务异常据吐丝
         */
        void showServerErrorToast();

        /**
         * 请求超时吐丝
         */
        void showTimeOutToast();

        /**
         * 网络异常吐丝
         */
        void showNetWorkErrorToast();

        /**
         * 设置显示藏下拉
         * @param isRefreshing
         */
        void setHeaderRefreshing(boolean isRefreshing);

        void logoutSuccess();
        void logoutFail();
    }

    interface Model{
        /**
         * 更新头像
         * @param body
         * @return
         */
        Observable<ResEntity<UploadEntity>> updateResetHeader(MultipartBody.Part body);

        /**
         * 获取 我的 页面的数据
         * @param options
         * @return
         */
        Observable<ResEntity<MineHomeEntity>> getMineHomeData(Map<String, Object> options);

        Observable<ResEntity<LogoutEntity>> logout();

    }

    abstract class Presenter extends BasePresenter<Model,View> {
        /**
         * 更新头像
         * @param file
         */
        public abstract void updateResetHeader(File file);

        /**
         * 获取 我的 页面的数据
         * @param context
         * @param options
         */
        public abstract void getMineHomeData(Context context, Map<String, Object> options, int requestType);
        /**
         * 注销
         */
        public abstract void logout();
    }
}
