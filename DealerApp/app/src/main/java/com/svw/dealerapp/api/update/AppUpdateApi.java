package com.svw.dealerapp.api.update;

import com.svw.dealerapp.entity.update.AppUpdateEntity;

import java.util.Map;

import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.QueryMap;
import rx.Observable;

/**
 * Created by qinshi on 7/3/2017.
 */

public interface AppUpdateApi {

    @GET("latest/{packageName}")
    Observable<AppUpdateEntity> checkAppUpdate(@Path("packageName") String packageName, @QueryMap Map<String, String> options);
}
