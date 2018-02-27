package com.svw.dealerapp.mvpframe.listfragment;

import android.content.Context;

import java.util.List;

import rx.Observable;

/**
 * Created by qinshi on 4/28/2017.
 */

public class ListFragmentContract {

    /**
     * 视图接口
     * @param <T> 用于控制服务返回数据的retData的数据类型
     * @param <M> 用于控制服务返回数据的retData中列表中的数据类型
     */
    public interface View<T, M> extends ShowToastView {
        /**
         * 显示加载中的页面
         */
        void showLoadingLayout();

        /**
         * 显示加载中的转圈视图
         * 用于在切换页面时切换到的页面显示
         */
        void showLoadingView();

        /**
         * 隐藏加载中的转圈视图
         * 用于在切换页面时被切换的页面隐藏加载圈
         */
        void hideLoadingView();

        /**
         * 显示加载超时的页面
         */
        void showTimeoutLayout();

        /**
         * 显示服务异常的页面
         */
        void showServerErrorLayout();

        /**
         * 显示无网络的页面
         */
        void showNetWorkErrorLayout();

        /**
         * 显示无数据的页面
         */
        void showNoDataLayout();

        /**
         * 刷新页面，只显示第一页
         *
         */
        void refresh();

        /**
         * 加载更多后刷新页面，显示到当前的页数
         *
         */
        void loadMore();

        /**
         * 无数据吐丝
         */
        void showNoDataToast();

        /**
         * 显示加载更多无数据视图
         */
        void showLoadMoreNoData();

        /**
         * 显示加载更多超时视图
         */
        void showLoadMoreTimeout();

        /**
         * 显示加载更多服务异常视图
         */
        void showLoadMoreServerError();

        /**
         * 显示加载更多网络异常视图
         */
        void showLoadMoreNetWorkError();

        /**
         * 隐藏下拉刷新的loading
         */
        void hidePullDownLoading();

        /**
         * 设置加载状态
         * @param refreshing
         */
        void setRefreshing(boolean refreshing);

        /**
         * 显示token失效
         */
        void showTokenInvalidLayout();
    }

    /**
     * 数据模型接口
     * @param <T> 用于控制服务返回数据的retData的数据类型
     */
    public interface Model<T>{
        /**
         * 获取数据
         * @param pageIndex
         * @param pageSize
         * @param orderType
         */
        Observable<T> getDataFromServer(String pageIndex, String pageSize, String orderType, String filter, String... moreParams);
    }

    /**
     * Presenter接口
     * @param <T> 用于控制服务返回数据的retData的数据类型
     * @param <M> 用于控制服务返回数据的retData中列表中的数据类型
     */
    public interface Presenter<T, M>{
        /**
         * 获取数据
         * @param context
         * @param pageIndex
         * @param pageSize
         * @param orderType
         * @param requestType 请求方式，下拉/上拉/初始化
         */
        void getDataFromServer(Context context, String pageIndex, String pageSize, String orderType, String filter, int requestType, String... moreParams);
    }
}
