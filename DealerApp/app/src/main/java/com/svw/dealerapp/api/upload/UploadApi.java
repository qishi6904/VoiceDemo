package com.svw.dealerapp.api.upload;

import com.svw.dealerapp.entity.ResEntity;
import com.svw.dealerapp.entity.mine.UploadEntity;

import java.util.Map;

import okhttp3.MultipartBody;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.QueryMap;
import rx.Observable;

/**
 * Created by lijinkui on 2017/6/7.
 */

@Deprecated
public interface UploadApi {
    @Multipart
    @POST("dealer/user/image")
    Observable<ResEntity<UploadEntity>> uploadPic(@Part MultipartBody.Part body, @QueryMap Map<String,Object>options);
}
