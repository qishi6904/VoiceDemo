package com.svw.dealerapp.ui.resource.model;

import com.google.gson.Gson;
import com.svw.dealerapp.entity.ResEntity;
import com.svw.dealerapp.entity.SMResEntity;
import com.svw.dealerapp.entity.resource.TransferDataEntity;
import com.svw.dealerapp.utils.RxSchedulersOverrideRule;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import rx.functions.Action1;

import static org.junit.Assert.*;

/**
 * Created by lijinkui on 2017/5/22.
 */
public class YellowCardTransferModelTest {

    private YellowCardTransferModel mModel;

    @Rule
    public final RxSchedulersOverrideRule mOverrideSchedulersRule = new RxSchedulersOverrideRule();


    @Before
    public void setUp() throws Exception {
        mModel = new YellowCardTransferModel();
    }

    @Test
    public void testPostTransferYellowCard(){
        mModel.postTransferYellowCard("504d7e27-a097-4c09-bedb-c153450a8777", "User001").doOnNext(new Action1<ResEntity<Object>>() {
            @Override
            public void call(ResEntity<Object> objectResEntity) {
                System.out.println(new Gson().toJson(objectResEntity));
            }
        }).toBlocking().single();
    }

    @Test
    public void testGetDataFromServer(){
        mModel.getDataFromServer("1", "10", "1", "", "167dbe79-8f56-4333-ae68-c64f1ec87a84").doOnNext(new Action1<SMResEntity<TransferDataEntity>>() {
            @Override
            public void call(SMResEntity<TransferDataEntity> objectResEntity) {
                System.out.println(new Gson().toJson(objectResEntity));
            }
        }).toBlocking().single();
    }

}