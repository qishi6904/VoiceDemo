package com.svw.dealerapp.api.optionalpackage;

import com.svw.dealerapp.entity.ResEntity;
import com.svw.dealerapp.entity.optionalpackage.OptionalPackageEntity;

import java.util.Map;

import retrofit2.http.GET;
import retrofit2.http.QueryMap;
import rx.Observable;

/**
 * Created by lijinkui on 2017/11/30.
 */

public interface OptionalPackageApi {
    @GET("api/selectByEcCarOption/selectByEcCarOptions")
    Observable<ResEntity<OptionalPackageEntity>> getOptionalPackageData(@QueryMap Map<String, String> options);
}
