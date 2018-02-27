package com.svw.dealerapp.ui.mine.model;

import com.google.gson.Gson;
import com.svw.dealerapp.entity.ResEntity;
import com.svw.dealerapp.entity.mine.ScheduleWaitEntity;
import com.svw.dealerapp.utils.RxSchedulersOverrideRule;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import rx.functions.Action1;

import static org.junit.Assert.*;

/**
 * Created by lijinkui on 2017/6/6.
 */
public class ScheduleWaitModelTest {

    ScheduleWaitModel mModel;

    @Rule
    public final RxSchedulersOverrideRule mOverrideSchedulersRule = new RxSchedulersOverrideRule();

    @Before
    public void setUp() throws Exception {
        mModel = new ScheduleWaitModel();
    }

    @Test
    public void testCancelSchedule() throws Exception {
        Map<String,Object> options = new HashMap<>();
        options.put("appmId","ff765804-7513-4202-aa69-c72ed7a9c7dd");
        mModel.cancelSchedule(options).doOnNext(new Action1<ResEntity<Object>>() {
            @Override
            public void call(ResEntity<Object> objectResEntity) {
                System.out.println(new Gson().toJson(objectResEntity));
            }
        }).toBlocking().single();
    }

    @Test
    public void testGetDataFromServer() throws Exception {
        mModel.getDataFromServer("1","10","1","%7B\"appmStatusId\":\"0\"%7D").doOnNext(new Action1<ResEntity<ScheduleWaitEntity>>() {
            @Override
            public void call(ResEntity<ScheduleWaitEntity> scheduleWaitEntityResEntity) {
                System.out.println(new Gson().toJson(scheduleWaitEntityResEntity));
            }
        }).toBlocking().single();
    }

}