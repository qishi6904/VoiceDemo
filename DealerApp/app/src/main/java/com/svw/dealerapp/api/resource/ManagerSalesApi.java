package com.svw.dealerapp.api.resource;

import com.svw.dealerapp.entity.ResEntity;
import com.svw.dealerapp.entity.SMResEntity;
import com.svw.dealerapp.entity.resource.TransferDataEntity;
import com.svw.dealerapp.entity.resource.YCTransferSalesEntity;

import java.util.List;
import java.util.Map;

import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.QueryMap;
import rx.Observable;

/**
 * Created by lijinkui on 2018/1/18.
 */

public interface ManagerSalesApi {
    //获取某个经理下的销售顾问
    @POST("api/v1/organization/employee/getEmployeeByOrganizationId")
    Observable<SMResEntity<TransferDataEntity>> getManagerSales(@QueryMap Map<String, Object> options);
}
