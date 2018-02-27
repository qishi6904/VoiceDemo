package com.svw.dealerapp.api.newcustomer;

import com.svw.dealerapp.entity.ResEntity;
import com.svw.dealerapp.entity.newcustomer.CarTypesEntity;

import java.util.Map;

import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.QueryMap;
import rx.Observable;

/**
 * Created by lijinkui on 2017/5/15.
 */

public interface CarTypesApi {
    @GET("api/carTypes")
    Observable<ResEntity<CarTypesEntity>> searchByCarType(@QueryMap Map<String, String> options);
}
