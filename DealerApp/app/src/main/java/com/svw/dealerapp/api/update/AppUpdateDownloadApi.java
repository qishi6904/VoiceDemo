package com.svw.dealerapp.api.update;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Streaming;
import retrofit2.http.Url;

/**
 * Created by qinshi on 7/4/2017.
 */

public interface AppUpdateDownloadApi {
    @Streaming //大文件时要加不然会OOM
    @GET
    Call<ResponseBody> downloadApp(@Url String downloadUrl);
}
