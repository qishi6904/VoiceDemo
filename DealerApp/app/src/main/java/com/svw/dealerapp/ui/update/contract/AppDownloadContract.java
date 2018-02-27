package com.svw.dealerapp.ui.update.contract;

import android.app.Activity;

import com.svw.dealerapp.entity.update.AppUpdateEntity;

import okhttp3.ResponseBody;
import retrofit2.Call;
import rx.Observable;

/**
 * Created by qinshi on 7/4/2017.
 */

public class AppDownloadContract {

    public interface View{
        /**
         * 下载异常
         */
        void downloadFail();

        /**
         * 刷新下载相关的UI
         * @param currentSize
         */
        void reFreshDownloadUI(long currentSize);

        /**
         * 跳转到系统应用安装页
         * @param downloadPath
         */
        void jumpToInstall(String downloadPath);
    }

    public interface Model{
        /**
         * 下载新版本应用
         * @param baseUrl
         * @param downloadUrl
         * @param rangeStart    断点下载的开始点
         * @param rangeEnd      断点下载的结束点
         * @return
         */
        Call<ResponseBody> downloadApp(String baseUrl, String downloadUrl, final int rangeStart, final int rangeEnd);
    }

    public interface Presenter{

        /**
         * 下载新版本应用
         * @param downloadUrl
         * @param downloadAppDir    下载目录
         * @param downloadAppName   下载文件名
         * @param rangeStart        断点下载的开始点
         * @param rangeEnd          断点下载的结束点
         */
        void downloadApp(String downloadUrl, final String downloadAppDir, final String downloadAppName, final int rangeStart, final int rangeEnd);

        /**
         * 取消下载
         */
        void cancel();

        /**
         * 当前状态是否是取消下载
         * @return
         */
        boolean isCancel();

        boolean isExecuted();
    }
}
