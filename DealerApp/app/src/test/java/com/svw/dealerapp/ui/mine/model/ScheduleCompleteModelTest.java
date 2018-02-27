package com.svw.dealerapp.ui.mine.model;

import com.google.gson.Gson;
import com.svw.dealerapp.entity.ResEntity;
import com.svw.dealerapp.entity.mine.ScheduleCompleteEntity;
import com.svw.dealerapp.utils.RxSchedulersOverrideRule;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import rx.functions.Action1;

import static org.junit.Assert.*;

/**
 * Created by lijinkui on 2017/6/6.
 */
public class ScheduleCompleteModelTest {

    ScheduleCompleteModel mModel;

    @Rule
    public final RxSchedulersOverrideRule mOverrideSchedulersRule = new RxSchedulersOverrideRule();

    @Before
    public void setUp() throws Exception {
        mModel = new ScheduleCompleteModel();
    }

    @Test
    public void testGetDataFromServer() throws Exception {
        mModel.getDataFromServer("1","10","1","%7B\"appmStatusId\":\"1\"%7D").doOnNext(new Action1<ResEntity<ScheduleCompleteEntity>>() {
            @Override
            public void call(ResEntity<ScheduleCompleteEntity> scheduleCompleteEntityResEntity) {
                System.out.println(new Gson().toJson(scheduleCompleteEntityResEntity));
            }
        }).toBlocking().single();
    }

}