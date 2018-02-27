package com.svw.dealerapp.api.login;

import com.svw.dealerapp.entity.SMResEntity;
import com.svw.dealerapp.entity.login.SMUserPrivilegeByAppIdEntity;

import java.util.List;
import java.util.Map;

import retrofit2.http.POST;
import retrofit2.http.QueryMap;
import rx.Observable;

/**
 * Created by lijinkui on 2018/1/25.
 */

public interface SMUserPrivilegeApi {
    @POST("api/v1/user/application/privilege/menu/getCheckedMenuTreeByAppId")
    Observable<SMResEntity<List<SMUserPrivilegeByAppIdEntity>>> smUserPrivilege(@QueryMap Map<String, Object> options);
}
