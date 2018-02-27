package com.svw.dealerapp.api.resource;

import com.svw.dealerapp.entity.ResEntity;
import com.svw.dealerapp.entity.resource.YellowCardEntity;

import java.util.Map;

import retrofit2.http.GET;
import retrofit2.http.QueryMap;
import rx.Observable;

/**
 * Created by lijinkui on 2018/1/18.
 */

public interface YellowCardListApi {
    @GET("api/opportunities/screen")
    Observable<ResEntity<YellowCardEntity>> getYellowCardList(@QueryMap Map<String, String> options);
}
