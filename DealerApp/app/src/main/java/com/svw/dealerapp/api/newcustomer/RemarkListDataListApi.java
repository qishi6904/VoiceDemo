package com.svw.dealerapp.api.newcustomer;

import com.svw.dealerapp.entity.ResEntity;
import com.svw.dealerapp.entity.newcustomer.RemarkEntity;

import java.util.Map;

import retrofit2.http.GET;
import retrofit2.http.QueryMap;
import rx.Observable;

/**
 * Created by lijinkui on 2018/1/18.
 */

public interface RemarkListDataListApi {
    @GET("api/opportunities/comment")
    Observable<ResEntity<RemarkEntity>> getRemarkListData(@QueryMap Map<String, String> options);
}
