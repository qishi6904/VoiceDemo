package com.svw.dealerapp.api.order;

import com.svw.dealerapp.entity.ResEntity;
import com.svw.dealerapp.entity.order.CheckMembershipReqEntity;
import com.svw.dealerapp.entity.order.CheckMembershipResEntity;

import retrofit2.http.Body;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by xupan on 19/09/2017.
 */

public interface MembershipApi {
    /**
     * 检查是否为会员
     */
    @POST("membership/points")
    Observable<ResEntity<CheckMembershipResEntity>> checkMembership(@Body CheckMembershipReqEntity entity);
}
