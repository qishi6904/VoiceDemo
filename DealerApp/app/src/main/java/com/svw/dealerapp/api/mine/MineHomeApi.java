package com.svw.dealerapp.api.mine;

import com.svw.dealerapp.entity.ResEntity;
import com.svw.dealerapp.entity.mine.LogoutEntity;
import com.svw.dealerapp.entity.mine.MineHomeEntity;

import java.util.Map;

import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.QueryMap;
import rx.Observable;

/**
 * Created by lijinkui on 2017/6/7.
 */

public interface MineHomeApi {
    @GET("api/my/home")
    Observable<ResEntity<MineHomeEntity>> getMineHomeData(@QueryMap Map<String, Object> options);
}
