package com.svw.dealerapp.ui.resource.model;

import com.google.gson.Gson;
import com.svw.dealerapp.entity.ResEntity;
import com.svw.dealerapp.entity.resource.TrafficEntity;
import com.svw.dealerapp.ui.resource.model.TrafficModel;
import com.svw.dealerapp.utils.RxSchedulersOverrideRule;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import rx.functions.Action1;

/**
 * Created by lijinkui on 2017/5/10.
 */
public class TrafficModelTest {

    private TrafficModel mTrafficModel;

    @Rule
    public final RxSchedulersOverrideRule mOverrideSchedulersRule = new RxSchedulersOverrideRule();

    @Before
    public void setUp() throws Exception {
        mTrafficModel = new TrafficModel();
    }

    @Test
    public void testGetDataFromServer() throws Exception {
        mTrafficModel.getDataFromServer("1","10","1","CreateTime:<=1;LeadsStatus:Created,UnProcessed").doOnNext(new Action1<ResEntity<TrafficEntity>>() {
            @Override
            public void call(ResEntity<TrafficEntity> leadsEntityResEntity) {
                System.out.println(new Gson().toJson(leadsEntityResEntity));
            }
        }).toBlocking().single();
    }

    @Test
    public void testPostInvalidTrafficStatus(){
        mTrafficModel.postInvalidTrafficStatus("3001ba31-95ae-4e8d-ac52-32eba3980095", "InValid", "任性，不想建卡")
                .doOnNext(new Action1<ResEntity<Object>>() {
                    @Override
                    public void call(ResEntity<Object> entity) {
                        System.out.println(new Gson().toJson(entity));
                    }
                }).toBlocking().single();
    }

    @Test
    public void testCheckYCExisted() throws Exception {
//        mTrafficModel.checkYCExisted("111").doOnNext(new Action1<ResEntity<Object>>() {
//            @Override
//            public void call(ResEntity<Object> objectResEntity) {
//                System.out.println(new Gson().toJson(objectResEntity));
//            }
//        }).toBlocking().single();
    }

}