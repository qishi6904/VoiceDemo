package com.svw.dealerapp.ui.mine.model;

import com.google.gson.Gson;
import com.svw.dealerapp.entity.ResEntity;
import com.svw.dealerapp.entity.mine.ScheduleCreateResEntity;
import com.svw.dealerapp.utils.RxSchedulersOverrideRule;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import rx.functions.Action1;

import static org.junit.Assert.*;

/**
 * Created by lijinkui on 2017/6/3.
 */
public class ScheduleModelTest {

    private ScheduleModel mModel;

    @Rule
    public final RxSchedulersOverrideRule mOverrideSchedulersRule = new RxSchedulersOverrideRule();

    @Before
    public void setUp() throws Exception {
        mModel = new ScheduleModel();
    }

    @Test
    public void testScheduleCreate() throws Exception {
        mModel.scheduleCreate(optionsForScheduleCreate()).doOnNext(new Action1<ResEntity<ScheduleCreateResEntity>>() {
            @Override
            public void call(ResEntity<ScheduleCreateResEntity> scheduleCreateResEntityResEntity) {
                System.out.println(new Gson().toJson(scheduleCreateResEntityResEntity));
            }
        }).toBlocking().single();
    }

    private Map<String,Object> optionsForScheduleCreate(){
        Map<String,Object> param = new HashMap<>();
        param.put("","");
        return param;
    }

}