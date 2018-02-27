package com.svw.dealerapp.ui.update.presenter;

import com.svw.dealerapp.api.DownloadCallback;
import com.svw.dealerapp.mvpframe.listfragment.BaseHandlerPresenter;
import com.svw.dealerapp.ui.update.contract.AppDownloadContract;
import com.svw.dealerapp.util.StringUtils;

import okhttp3.ResponseBody;
import retrofit2.Call;

/**
 * Created by qinshi on 7/4/2017.
 */

public class AppDownloadPresenter extends BaseHandlerPresenter<AppDownloadContract.Model, AppDownloadContract.View>
        implements AppDownloadContract.Presenter {

    private Call<ResponseBody> downloadCall;

    public AppDownloadPresenter(AppDownloadContract.View view, AppDownloadContract.Model model) {
        super(view, model);
    }

    @Override
    public void downloadApp(String downloadUrl, final String downloadAppDir, final String downloadAppName,
                            final int rangeStart, final int rangeEnd) {

        //处理下载url，拆分为baseurl和endurl，retrofit必须要baseurl
        int spiltPosition = StringUtils.getCharPosition(downloadUrl, "/", 3);
        String baseUrl = downloadUrl.substring(0, spiltPosition + 1);
        String endUrl = downloadUrl.substring(spiltPosition + 1);

        downloadCall = mModel.downloadApp(baseUrl, endUrl, rangeStart, rangeEnd);
        downloadCall.enqueue(new DownloadCallback(downloadAppDir, downloadAppName, rangeStart) {
            @Override
            public void refreshDownloadUI(long currentSize) {   //刷新下载相关的UI
                mView.reFreshDownloadUI(currentSize);
            }

            @Override
            public void downloadFinish() {      //下载完成
                mView.jumpToInstall(downloadAppDir + "/" +downloadAppName);
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {   //下载失败
                    mView.downloadFail();
            }
        });
    }

    @Override
    public void cancel() {
        if(null != downloadCall){
            downloadCall.cancel();
        }
    }

    @Override
    public boolean isCancel() {
        if(null != downloadCall){
            return downloadCall.isCanceled();
        }
        return false;
    }

    @Override
    public boolean isExecuted() {
        if(null != downloadCall){
            return downloadCall.isExecuted();
        }
        return false;
    }

}
