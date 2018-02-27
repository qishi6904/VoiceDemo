package com.svw.dealerapp.api.login;

import com.svw.dealerapp.entity.ResEntity;
import com.svw.dealerapp.entity.login.LoginEntity;

import java.util.Map;

import retrofit2.http.Body;
import retrofit2.http.POST;
import rx.Observable;
/**
 * Created by lijinkui on 2017/5/9.
 */

@Deprecated
public interface LoginApi {
    @POST("dealer/login")
    Observable<ResEntity<LoginEntity>> userLogin(@Body Map<String, String> options);
}
