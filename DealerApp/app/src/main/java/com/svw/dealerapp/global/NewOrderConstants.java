package com.svw.dealerapp.global;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 新建订单相关常量
 * Created by xupan on 08/08/2017.
 */

public class NewOrderConstants {

    public static Map<String, String> idTypeMap = new LinkedHashMap<>();//证件类型
    public static Map<String, String> customerTypeMap = new LinkedHashMap<>();//客户类别
    public static Map<String, String> industryMap = new LinkedHashMap<>();//从事行业
    public static Map<String, String> occupationMap = new LinkedHashMap<>();//职务
    public static Map<String, String> companyPropertyMap = new LinkedHashMap<>();//公司性质


//    static {
//        idTypeMap.put("0", "身份证");
//        idTypeMap.put("1", "军官证");
//        idTypeMap.put("2", "护照");
//        idTypeMap.put("3", "其他");
//    }
//
//    static {
//        customerTypeMap.put("0", "个人");
//        customerTypeMap.put("1", "公司");
//        customerTypeMap.put("2", "经销商");
//    }
}
