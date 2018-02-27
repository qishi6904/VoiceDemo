package com.svw.dealerapp.ui.mine.model;

import com.google.gson.Gson;
import com.svw.dealerapp.entity.ResEntity;
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
public class ScheduleUpdateModelTest {

    ScheduleUpdateModel mModel;

    @Rule
    public final RxSchedulersOverrideRule mOverrideSchedulersRule = new RxSchedulersOverrideRule();

    @Before
    public void setUp() throws Exception {
        mModel = new ScheduleUpdateModel();
    }

    @Test
    public void testUpdateSchedule() throws Exception {
        Map<String,Object> options = new HashMap<>();
        options.put("oppId","");
        options.put("appmDateStr","2017-06-11T10:01:06.006+0800");
        options.put("appmTypeId","17010");
        options.put("appmId","02eefae5-4a61-4bef-83e6-3f105af3d32f");
        options.put("isFirst","");
        options.put("isReminder","");
        options.put("reminderInterval","");
        options.put("remark","");
        mModel.updateSchedule(options).doOnNext(new Action1<ResEntity<Object>>() {
            @Override
            public void call(ResEntity<Object> objectResEntity) {
                System.out.println(new Gson().toJson(objectResEntity));
            }
        }).toBlocking().single();
    }

}