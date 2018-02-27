package com.svw.dealerapp.ui.newcustomer;

import com.google.gson.Gson;
import com.svw.dealerapp.entity.ResEntity;
import com.svw.dealerapp.entity.newcustomer.CarTypesEntity;
import com.svw.dealerapp.entity.newcustomer.FollowupCreateReqEntity;
import com.svw.dealerapp.entity.newcustomer.OpportunityDetailEntity;
import com.svw.dealerapp.entity.newcustomer.OpportunityEntity;
import com.svw.dealerapp.entity.newcustomer.OpportunityRelationsBean;
import com.svw.dealerapp.entity.newcustomer.OpportunitySubmitReqEntity;
import com.svw.dealerapp.entity.newcustomer.OpportunityUpdateReqEntity;
import com.svw.dealerapp.ui.newcustomer.model.NewCustomerModel;
import com.svw.dealerapp.utils.RxSchedulersOverrideRule;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import rx.functions.Action1;

/**
 * Created by lijinkui on 2017/5/15.
 */
public class NewCustomerModelTest {

    private NewCustomerModel mModel;

    @Rule
    public final RxSchedulersOverrideRule mOverrideSchedulersRule = new RxSchedulersOverrideRule();

    @Before
    public void setUp() throws Exception {
        mModel = new NewCustomerModel();
    }

    @Test
    public void testForSubmitOpportunities() throws Exception {
        mModel.submitOpportunities(optionsForOpportunities()).doOnNext(new Action1<ResEntity<OpportunityEntity>>() {
            @Override
            public void call(ResEntity<OpportunityEntity> opportunityEntityResEntity) {
                System.out.println(new Gson().toJson(opportunityEntityResEntity));
            }
        }).toBlocking().single();
    }

    @Test
    public void testForSubmitOpportunities2() throws Exception {
        mModel.submitOpportunities(optionsForSubmit()).doOnNext(new Action1<ResEntity<OpportunityEntity>>() {
            @Override
            public void call(ResEntity<OpportunityEntity> opportunityEntityResEntity) {
                System.out.println(new Gson().toJson(opportunityEntityResEntity));
            }
        }).toBlocking().single();
    }

    @Test
    public void testForUpdateOpportunities() throws Exception {
        mModel.updateOpportunities(optionsForOpportunities()).doOnNext(new Action1<ResEntity<OpportunityEntity>>() {
            @Override
            public void call(ResEntity<OpportunityEntity> opportunityEntityResEntity) {
                System.out.println(new Gson().toJson(opportunityEntityResEntity));
            }
        }).toBlocking().single();
    }

    @Test
    public void testForUpdateOpportunities2() throws Exception {
        mModel.updateOpportunities(optionsForUpdate()).doOnNext(new Action1<ResEntity<OpportunityEntity>>() {
            @Override
            public void call(ResEntity<OpportunityEntity> opportunityEntityResEntity) {
                System.out.println(new Gson().toJson(opportunityEntityResEntity));
            }
        }).toBlocking().single();
    }

    @Test
    public void testSearchByCarType() throws Exception {
        mModel.searchByCarType("3E92FZ").doOnNext(new Action1<ResEntity<CarTypesEntity>>() {
            @Override
            public void call(ResEntity<CarTypesEntity> carTypesEntityResEntity) {
                System.out.println(new Gson().toJson(carTypesEntityResEntity));
            }
        }).toBlocking().single();
    }

    @Test
    public void testSubmitFollowupInfo() throws Exception {
        mModel.submitFollowupInfo(optionsForFollowupInfo2()).doOnNext(new Action1<ResEntity<Object>>() {
            @Override
            public void call(ResEntity<Object> objectResEntity) {
                System.out.println(new Gson().toJson(objectResEntity));
            }
        }).toBlocking().single();
    }

    @Test
    public void testForShowOpportunitiesDetail() throws Exception {
        mModel.showOpportunitiesDetail("145fad0a-5ae8-4ebb-9c83-38430cc0685d").doOnNext(new Action1<ResEntity<OpportunityDetailEntity>>() {
            @Override
            public void call(ResEntity<OpportunityDetailEntity> opportunityDetailEntityResEntity) {
                System.out.println(new Gson().toJson(opportunityDetailEntityResEntity));
            }
        }).toBlocking().single();
    }

    private Map<String, String> optionsForOpportunities() {

        Map<String, String> options = new HashMap<String, String>();

        options.put("leadsId", "724a20a1-5b15-4c7d-9efa-0d3cb8301bba");//流量id
//        options.put("oppId", "0ceb5280-30a8-4e7d-ab1a-2a46a825c548");//潜客id,只有更新使用

        options.put("custName", "张三");//潜客名称
        options.put("custGender", "0");//潜客性别
        options.put("custAge", "10020");//潜客年龄段
        options.put("custDesc", "");//潜客描述
        options.put("oppStatusId", "");//潜客状态ID
        options.put("custMobile", "18640982936");//手机号
        options.put("custTelephone", "");//固定电话
        options.put("isWechat", "1");//是否已加客户微信
        options.put("custWechat", "");//微信号
        options.put("custEmail", "");//电子邮箱
        options.put("channelId", "");//来源渠道ID
        options.put("srcTypeId", "11030");//来源类型ID
        options.put("oppOwner", "");//潜客负责人
        options.put("oppLevel", "12010");//潜客级别
        options.put("isKeyuser", "0");//是否重点客户
        options.put("purchaseId", "13010");//购买区分ID
        options.put("propertyId", "13510");//购买性质ID
        options.put("currentModel", "BMW");//现用车型
        options.put("modelYear", "2015");//年份
        options.put("currentMileage", "50000");//里程数
        options.put("budgetMin", "100000");//购车预算（最小值）
        options.put("budgetMax", "200000");//购车预算（最大值）
        options.put("paymentMode", "12510");//付款方式
        options.put("carModelId", "");//意向车型ID
        options.put("outsideColorId", "");//外观颜色ID
        options.put("insideColorId", "");//内饰颜色ID
//        options.put("demandId", "14030");//其它需求类型ID
//        options.put("demandDesc", "上牌子啊");//其它需求描述
//        options.put("infoId", "14540");//信息途径ID
//        options.put("infoDesc", "移动互联网啊");//其它信息途径描述
        options.put("remark", "remarkremark");//备注
        options.put("createUser", "1234");//创建人ID
        options.put("provinceId", "");//所在省份
        options.put("cityId", "");//所在城市
        options.put("orgId", "");//经销商ID
        options.put("scheduleDateStr", "2017-05-25T10:01:06.006+0800");//跟进日期
        options.put("followupDesc", "23232323232323232323");//跟进理由描述
//        options.put("followupId", "af647b27-7343-43d1-bdb0-fecb42bbb46e");//跟进id,只有更新使用
        options.put("opportunityRelations", new Gson().toJson(getOpportunityRelations()).toString());//

        return options;
    }

    private List<Map<String, String>> getOpportunityRelations(){
        List<Map<String, String>> result = new ArrayList<>();
        Map<String, String> option1 = new HashMap<>();
        option1.put("relaId","10012");
        option1.put("relaDesc","lflsfldsfjdsl");
        option1.put("relaFlag","0");
        option1.put("remark","sdfdsfsdfdsfsdfdsf");
        result.add(option1);
        Map<String, String> option2 = new HashMap<>();
        option2.put("relaId","10012");
        option2.put("relaDesc","lflsfldsfjdsl");
        option2.put("relaFlag","0");
        option2.put("remark","sdfdsfsdfdsfsdfdsf");
        result.add(option2);

        return result;
    }

    private OpportunitySubmitReqEntity optionsForSubmit() {
        OpportunitySubmitReqEntity submitReqEntity = new OpportunitySubmitReqEntity();

        submitReqEntity.setCustName("张三");
        submitReqEntity.setCustGender("0");
        submitReqEntity.setCustAge("10020");
        submitReqEntity.setCustDesc("");
        submitReqEntity.setOppStatusId("");
        submitReqEntity.setCustMobile("18640982936");
        submitReqEntity.setCustTelephone("");
        submitReqEntity.setIsWechat("1");
        submitReqEntity.setCustWechat("");
        submitReqEntity.setCustEmail("");
        submitReqEntity.setChannelId("");
        submitReqEntity.setSrcTypeId("11030");
        submitReqEntity.setOppOwner("");
        submitReqEntity.setOppLevel("12010");
        submitReqEntity.setIsKeyuser("0");
        submitReqEntity.setPurchaseId("13010");
        submitReqEntity.setPropertyId("13510");
        submitReqEntity.setCurrentModel("BMW");
        submitReqEntity.setModelYear("2015");
        submitReqEntity.setCurrentMileage("50000");
        submitReqEntity.setBudgetMin("100000");
        submitReqEntity.setBudgetMax("200000");
        submitReqEntity.setPaymentMode("12510");
        submitReqEntity.setCarModelId("");
        submitReqEntity.setOutsideColorId("");
        submitReqEntity.setInsideColorId("");
        submitReqEntity.setRemark("remarkremark");
        submitReqEntity.setCreateUser("1234");
        submitReqEntity.setLeadsId("724a20a1-5b15-4c7d-9efa-0d3cb8301bba");
        submitReqEntity.setProvinceId("");
        submitReqEntity.setCityId("");
        submitReqEntity.setOrgId("");
        submitReqEntity.setScheduleDateStr("2017-05-25T10:01:06.006+0800");
        submitReqEntity.setFollowupDesc("23232323232323232323");

        List<OpportunityRelationsBean> opportunityRelations = new ArrayList<>();
        OpportunityRelationsBean bean1 = new OpportunityRelationsBean();
        bean1.setRelaId("10012");
        bean1.setRelaDesc("lflsfldsfjdsl");
        bean1.setRelaFlag("0");
        bean1.setRemark("sdfdsfsdfdsfsdfdsf");
        opportunityRelations.add(bean1);

        OpportunityRelationsBean bean2 = new OpportunityRelationsBean();
        bean2.setRelaId("10013");
        bean2.setRelaDesc("lflsfldsfjdsl3");
        bean2.setRelaFlag("0");
        bean2.setRemark("sdfdsfsdfdsfsdfdsf3");
        opportunityRelations.add(bean2);

        submitReqEntity.setOpportunityRelations(opportunityRelations);

        return submitReqEntity;
    }

    private OpportunityUpdateReqEntity optionsForUpdate() {
        OpportunityUpdateReqEntity updateReqEntity = new OpportunityUpdateReqEntity();

        updateReqEntity.setCustName("张三");
        updateReqEntity.setCustGender("0");
        updateReqEntity.setCustAge("10020");
        updateReqEntity.setCustDesc("");
        updateReqEntity.setOppStatusId("");
        updateReqEntity.setCustMobile("18640982936");
        updateReqEntity.setCustTelephone("");
        updateReqEntity.setIsWechat("1");
        updateReqEntity.setCustWechat("");
        updateReqEntity.setCustEmail("");
        updateReqEntity.setChannelId("");
        updateReqEntity.setSrcTypeId("11030");
        updateReqEntity.setOppOwner("");
        updateReqEntity.setOppLevel("12010");
        updateReqEntity.setIsKeyuser("0");
        updateReqEntity.setPurchaseId("13010");
        updateReqEntity.setPropertyId("13510");
        updateReqEntity.setCurrentModel("BMW");
        updateReqEntity.setModelYear("2015");
        updateReqEntity.setCurrentMileage("50000");
        updateReqEntity.setBudgetMin("100000");
        updateReqEntity.setBudgetMax("200000");
        updateReqEntity.setPaymentMode("12510");
        updateReqEntity.setCarModelId("");
        updateReqEntity.setOutsideColorId("");
        updateReqEntity.setInsideColorId("");
        updateReqEntity.setRemark("remarkremark");
        updateReqEntity.setOppId("26abde60-9f52-486a-aaa0-2c3be0df0320");
        updateReqEntity.setLeadsId("724a20a1-5b15-4c7d-9efa-0d3cb8301bba");
        updateReqEntity.setProvinceId("");
        updateReqEntity.setCityId("");
        updateReqEntity.setOrgId("");
        updateReqEntity.setScheduleDateStr("2017-05-25T10:01:06.006+0800");
        updateReqEntity.setFollowupId("1da1b298-4399-4cf8-bd52-0e5081deeb50");
        updateReqEntity.setFollowupDesc("23232323232323232323");

        List<OpportunityRelationsBean> opportunityRelations = new ArrayList<>();
        OpportunityRelationsBean bean1 = new OpportunityRelationsBean();
        bean1.setRelaId("10012");
        bean1.setRelaDesc("lflsfldsfjdsl");
        bean1.setRelaFlag("0");
        bean1.setRemark("sdfdsfsdfdsfsdfdsf");
        opportunityRelations.add(bean1);

        OpportunityRelationsBean bean2 = new OpportunityRelationsBean();
        bean2.setRelaId("10013");
        bean2.setRelaDesc("lflsfldsfjdsl3");
        bean2.setRelaFlag("0");
        bean2.setRemark("sdfdsfsdfdsfsdfdsf3");
        opportunityRelations.add(bean2);

        updateReqEntity.setOpportunityRelations(opportunityRelations);

        return updateReqEntity;
    }

    private Map<String, String> optionsForFollowupInfo() {

        Map<String, String> options = new HashMap<String, String>();

        options.put("followupId", "8ebda962-3246-4829-8c74-0d3e71324d25");//跟进ID
        options.put("oppId", "1d81a782-289f-4dfb-bbe8-d0c3db6326e1");//潜客ID
        options.put("scheduleDateStr", "2017-05-27T10:01:06.006+0800");//预定跟进日期,第三页传空，后台以此作为第三四页区分
        options.put("scheduleDesc", "电话跟进看看");//预定跟进计划，第三页传空，后台以此作为第三四页区分
        options.put("modeId", "15020");//跟进方式ID
        options.put("opp_level", "");//潜客级别
        options.put("remark", "备注备注备注");//备注
        options.put("dictId", "15590");//跟进结果ID
        options.put("resultDesc", "看得不爽");//跟进结果描述
        options.put("appmDateStr", "2017-05-27T11:01:06.006+0800");//预约日期
        options.put("appmTypeId", "17010");//预约类型id
        options.put("isFirst", "");//是否首次到店
        options.put("isReminder", "");//是否提醒预约
        options.put("reminderInterval", "");//提醒的间隔时间

        return options;
    }

    private FollowupCreateReqEntity optionsForFollowupInfo2() {
        FollowupCreateReqEntity followupCreateReqEntity = new FollowupCreateReqEntity();

        followupCreateReqEntity.setFollowupId("67b690c6-85b1-4c07-8300-e8979197b203");
        followupCreateReqEntity.setOppId("22413235-bde9-49c1-a709-074d0d76cb44");
        followupCreateReqEntity.setScheduleDateStr("2017-05-27T10:01:06.006+0800");//预定跟进日期,第三页传空，后台以此作为第三四页区分
        followupCreateReqEntity.setScheduleDesc("电话跟进看看");//预定跟进计划，第三页传空，后台以此作为第三四页区分
        followupCreateReqEntity.setModeId("15020");
        followupCreateReqEntity.setOppLevel("");
        followupCreateReqEntity.setRemark("ccccc");
        followupCreateReqEntity.setAppmDateStr("2017-05-27T11:01:06.006+0800");
        followupCreateReqEntity.setAppmTypeId("17010");
        followupCreateReqEntity.setIsFirst("");
        followupCreateReqEntity.setIsReminder("");
        followupCreateReqEntity.setReminderInterval("");

        List<FollowupCreateReqEntity.ResultsBean> ResultsBeanList = new ArrayList<>();
        FollowupCreateReqEntity.ResultsBean resultsBean1 = new FollowupCreateReqEntity.ResultsBean();
        resultsBean1.setDictId("00500");
        resultsBean1.setResultDesc("xxxxxxx");
        ResultsBeanList.add(resultsBean1);

        FollowupCreateReqEntity.ResultsBean resultsBean2 = new FollowupCreateReqEntity.ResultsBean();
        resultsBean2.setDictId("00501");
        resultsBean2.setResultDesc("sdfsfsfsf");
        ResultsBeanList.add(resultsBean2);

        followupCreateReqEntity.setResults(ResultsBeanList);

        return followupCreateReqEntity;
    }

}