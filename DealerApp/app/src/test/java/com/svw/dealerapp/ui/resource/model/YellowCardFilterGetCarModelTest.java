package com.svw.dealerapp.ui.resource.model;

import com.google.gson.Gson;
import com.svw.dealerapp.entity.ResEntity;
import com.svw.dealerapp.entity.newcustomer.CarTypesEntity;
import com.svw.dealerapp.utils.RxSchedulersOverrideRule;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import rx.functions.Action1;

import static org.junit.Assert.*;

/**
 * Created by lijinkui on 2017/5/22.
 */
public class YellowCardFilterGetCarModelTest {

    private FilterGetCarNameModel mModel;

    @Rule
    public final RxSchedulersOverrideRule mOverrideSchedulersRule = new RxSchedulersOverrideRule();

    @Before
    public void setUp() throws Exception {
        mModel = new FilterGetCarNameModel();
    }

    @Test
    public void testGetCarTypeByCode() throws Exception {
        mModel.getCarTypeByCode("3E92FZ").doOnNext(new Action1<ResEntity<CarTypesEntity>>() {
            @Override
            public void call(ResEntity<CarTypesEntity> carTypesEntityResEntity) {
                System.out.println(new Gson().toJson(carTypesEntityResEntity));
            }
        }).toBlocking().single();
    }

}