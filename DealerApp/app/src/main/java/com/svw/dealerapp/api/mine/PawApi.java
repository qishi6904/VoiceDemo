package com.svw.dealerapp.api.mine;

import com.svw.dealerapp.entity.ResEntity;
import com.svw.dealerapp.entity.login.LoginEntity;
import com.svw.dealerapp.entity.mine.ResetPassEntity;

import java.util.Map;

import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by qinshi on 6/1/2017.
 */

@Deprecated
public interface PawApi {
    @POST("dealer/password/reset")
    Observable<ResEntity<ResetPassEntity>> postResetPaw(@Body Map<String, String> options);
}
