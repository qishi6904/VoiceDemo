package com.svw.dealerapp.ui.update.model;

import com.svw.dealerapp.api.UpdateNetwork;
import com.svw.dealerapp.ui.update.contract.AppDownloadContract;

import okhttp3.ResponseBody;
import retrofit2.Call;

/**
 * Created by qinshi on 7/4/2017.
 */

public class AppDownloadModel implements AppDownloadContract.Model{

    @Override
    public Call<ResponseBody> downloadApp(String baseUrl, String downloadUrl, final int rangeStart, final int rangeEnd) {
        return UpdateNetwork.getInstance()
                .getAppUpdateDownloadApi(baseUrl, rangeStart, rangeEnd)
                .downloadApp(downloadUrl);
    }
}
