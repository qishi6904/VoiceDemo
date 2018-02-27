package com.svw.dealerapp.api.report;

import com.svw.dealerapp.entity.ResEntity;
import com.svw.dealerapp.entity.report.ReportHomeEntity;

import java.util.Map;

import retrofit2.http.GET;
import retrofit2.http.QueryMap;
import rx.Observable;

/**
 * Created by lijinkui on 2017/8/29.
 */

public interface ReportHomeApi {
    @GET("report/home/summary")
    Observable<ResEntity<ReportHomeEntity>> getReportHomeData(@QueryMap Map<String, Object> options);
}
