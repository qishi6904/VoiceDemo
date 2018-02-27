package com.svw.dealerapp.api.report;

import com.svw.dealerapp.entity.ResEntity;
import com.svw.dealerapp.entity.report.WebTokenEntity;

import java.util.Map;

import retrofit2.http.POST;
import retrofit2.http.QueryMap;
import rx.Observable;

/**
 * Created by lijinkui on 2017/8/1.
 */

@Deprecated
public interface ReportApi {
    @POST("dealer/token/refresh")
    Observable<ResEntity<WebTokenEntity>> updateWebToken(@QueryMap Map<String, Object> options);
}
