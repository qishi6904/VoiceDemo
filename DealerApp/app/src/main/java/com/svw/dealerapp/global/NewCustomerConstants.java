package com.svw.dealerapp.global;

import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

/**
 * 新建黄卡所用到的字段
 * Created by xupan on 15/05/2017.
 */

public class NewCustomerConstants {
    public static final String SELECTION_MAP = "selectionMap";
    public static final String CAR_TYPE_ENTITY = "carTypeEntity";
    public static final int FLAG_FOLLOWUP_REASON = 1;//传给OppLevelFragment的flag，表示只显示跟进理由
    public static final int FLAG_OPP_LEVEL = 2;//传给OppLevelFragment的flag，表示只显示潜客等级
    public static final String FRAGMENT_TAG_INFO = "info";//黄卡详情页点击基本信息后跳转的tag,以下类似
    public static final String FRAGMENT_TAG_REQUIREMENTS = "requirements";
    public static final String FRAGMENT_TAG_FOLLOWUP = "followup";
    public static final String FRAGMENT_TAG_REMARK = "remark";

    public static final String KEY_OPP_ID = "oppId";
    public static final String KEY_FOLLOWUP_ID = "followupId";
    public static final int REQUEST_CODE_PACKAGE = 1000;

    public static Map<String, String> genderMap = new LinkedHashMap<>();//性别
    public static Map<String, String> ageMap = new LinkedHashMap<>();//年龄
    public static Map<String, String> sourceMap = new LinkedHashMap<>();//客户来源
    public static Map<String, String> mediaMap = new LinkedHashMap<>();//媒体
    public static Map<String, String> purposeMap = new LinkedHashMap<>();//购买区分（私人、公司等）
    public static Map<String, String> propertyMap = new LinkedHashMap<>();//购买性质（首次、增购等）
    public static Map<String, String> levelMap = new LinkedHashMap<>();//潜客级别
    public static Map<String, String> requirementsMap = new LinkedHashMap<>();//其他需求
    public static Map<String, String> methodsMap = new LinkedHashMap<>();///客户了解信息途径
    public static Map<String, String> ycStatusMap = new LinkedHashMap<>(); //潜客状态

    public static Map<String, String> payMethodMap = new LinkedHashMap<>();//付款方式（意向）
    public static Map<String, String> wechatAddedMap = new LinkedHashMap<>();//是否添加微信

    public static Map<String, String> contactMethodMap = new LinkedHashMap<>();//联络方式(本次跟进记录)
    public static Map<String, String> followupMethodMap = new LinkedHashMap<>();//跟进方式(下次跟进计划)
    public static Map<String, String> contactResultSingleChoiceMap = new LinkedHashMap<>();//电话短信微信接触结果（单选）
    public static Map<String, String> contactResultMultiChoicesMap = new LinkedHashMap<>();//展厅接触结果（部分多选）
    public static Map<String, String> failureReasonsMap = new LinkedHashMap<>();//战败原因
    public static Map<String, String> asleepReasonsMap = new LinkedHashMap<>();//休眠原因

    public static Map<String, String> appointmentTypeMap = new LinkedHashMap<>();//预约事项
    public static Map<String, String> approvalStatusMap = new LinkedHashMap<>();//审核状态
    public static Set<String> contactMethodSingleChoiceCodeSet = new HashSet<>();//接触方式中对应单选项的选项code，如电话短信
    public static Set<String> contactMethodMultiChoicesCodeSet = new HashSet<>();//接触方式中对应多选项的选项code，如展厅

    public static Map<String, String> trafficStatusMap = new LinkedHashMap<>();//客流状态
    public static Map<String, String> activityMap = new LinkedHashMap<>();  //外拓
    public static Map<String, String> carSeriesMap = new LinkedHashMap<>();  //车系
    public static Map<String, String> carModelMap = new LinkedHashMap<>();  //车型

    public static Map<String, String> srcTypeMap = new LinkedHashMap<>();  //新建客源-客户来源
    public static Map<String, String> phoneMediaMap = new LinkedHashMap<>();  //来电－媒体

    static {
        genderMap.put("0", "男");
        genderMap.put("1", "女");
    }

    //时间规则------start
    public static final int SLEEP_FIRST_DEFAULT = 90;           //休眠 首次跟进 默认
    public static final int SLEEP_FIRST_MAX = 365;              //休眠 首次跟进 最晚
    public static final int SLEEP_NO_FIRST_DEFAULT = 90;        //休眠 非首次跟进 默认
    public static final int SLEEP_NO_FIRST_MAX = 365;           //休眠 非首次跟进 最晚
    public static final int CANCEL_ORDER_NO_FIRST_DEFAULT = 3;  //取消订单　非首次跟进　默认
    public static final int CANCEL_ORDER_NO_FIRST_MAX = 365;    //取消订单　非首次跟进　最晚
    //时间规则------end

//    static {
//        ageMap.put("2", "25岁及以下");
//        ageMap.put("3", "26-35岁");
//        ageMap.put("4", "36-45岁");
//        ageMap.put("5", "46-55岁");
//        ageMap.put("6", "55岁以上");
//    }
//
//    static {
//        sourceMap.put("7", "随机进入");
//        sourceMap.put("8", "电商");
//        sourceMap.put("9", "线索");
//        sourceMap.put("10", "来电");
//        sourceMap.put("11", "外拓");
//    }
//
//    static {
//        purposeMap.put("12", "私人");
//        purposeMap.put("13", "政府");
//        purposeMap.put("14", "公司");
//        purposeMap.put("15", "其他");
//    }
//
//    static {
//        propertyMap.put("16", "首次购车");
//        propertyMap.put("17", "增购");
//        propertyMap.put("18", "置换");
//    }
//
//    static {
//        levelMap.put("19", "H级");
//        levelMap.put("20", "A级");
//        levelMap.put("21", "B级");
//        levelMap.put("22", "C级");
//        levelMap.put("23", "N级");
//    }
//
//    static {
//        requirementsMap.put("100", "保险");
//        requirementsMap.put("101", "延保");
//        requirementsMap.put("102", "附件");
//        requirementsMap.put("103", "上牌");
//        requirementsMap.put("104", "其他");
//    }
//
//    static {
//        methodsMap.put("200", "报纸杂志");
//        methodsMap.put("201", "广播电视");
//        methodsMap.put("202", "户外广告");
//        methodsMap.put("203", "移动互联");
//        methodsMap.put("204", "朋友推荐");
//        methodsMap.put("205", "外展车展");
//        methodsMap.put("206", "其他");
//    }

//    static {
//        payMethodMap.put("221", "全款");
//        payMethodMap.put("222", "分期");
//    }

    static {
        wechatAddedMap.put("0", "是");
        wechatAddedMap.put("1", "否");
    }

//    static {
//        contactMethodMap.put("241", "电话");
//        contactMethodMap.put("242", "短信");
//        contactMethodMap.put("243", "微信");
//        contactMethodMap.put("244", "展厅");
//        contactMethodMap.put("245", "其他");
//    }

//    static {
//        contactResultSingleChoiceMap.put("251", "继续跟进");
//        contactResultSingleChoiceMap.put("252", "战败");
//        contactResultSingleChoiceMap.put("253", "休眠");
//    }

//    static {
//        contactResultMultiChoicesMap.put("261", "看车");
//        contactResultMultiChoicesMap.put("262", "试乘试驾");
//        contactResultMultiChoicesMap.put("263", "生成订单");
//        contactResultMultiChoicesMap.put("264", "成功");
//        contactResultMultiChoicesMap.put("265", "交车");
//        contactResultMultiChoicesMap.put("266", "战败");
//        contactResultMultiChoicesMap.put("267", "休眠");
//        contactResultMultiChoicesMap.put("268", "其他");
//    }

    static {
        failureReasonsMap.put("同品", "同品");
        failureReasonsMap.put("竞品", "竞品");
    }

    static {
        asleepReasonsMap.put("近期无购车意向", "近期无购车意向");
        asleepReasonsMap.put("需拍车牌", "需拍车牌");
        asleepReasonsMap.put("其他", "其他");
    }

    static {
        contactMethodMultiChoicesCodeSet.add("15010");
    }

    static {
        contactMethodSingleChoiceCodeSet.add("15020");
        contactMethodSingleChoiceCodeSet.add("15030");
        contactMethodSingleChoiceCodeSet.add("15040");
    }

    static {
//        srcTypeMap.put("11010", "到店");
        srcTypeMap.put("11040", "来电");
        srcTypeMap.put("11050", "外拓");
    }


    private static String followupDateStr = "";

    public static String getFollowupDateStr() {
        return followupDateStr;
    }

    public static void setFollowupDateStr(String followupDateStr) {
        NewCustomerConstants.followupDateStr = followupDateStr;
    }
}
