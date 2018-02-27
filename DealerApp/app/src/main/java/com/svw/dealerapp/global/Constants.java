
package com.svw.dealerapp.global;

/**
 * Created by ibm on 2017/4/20.
 */
public class Constants {

    public static final int CREATE_YELLOW_CAR_FROM_RESOURCE_TRAFFIC = 1001;
    public static final int CREATE_YELLOW_CAR_FROM_TASK_TRAFFIC = 1002;

    public static Boolean IS_FIRST_START = true;
    public static String CLIENT_ID = "";
    public static String APP_ID = "";

    //sale Env set
    public static String BASE_URL = "";
    public static String API_BASE_URL_LEADS_SERVICE = "";
    public static String API_BASE_URL_OPPORTUNITY_SERVICE = "";
    public static String API_BASE_URL_DIC_SERVICE = "";
    public static String API_BASE_URL_NOTICE_SERVICE = "";
    public static String API_BASE_URL_KPI_SERVICE = "";
    public static String API_BASE_URL_ORDER_SERVICE = "";
    public static String API_BASE_URL_MEMBERSHIP_SERVICE = "";
    public static String API_BASE_URL_UPLOAD_SERVICE = "";
    public static String API_BASE_URL_USER_SERVICE = "";
    public static String API_BASE_URL_CAR_CONFIG_SERVICE = "";
    public static String API_BASE_URL_DEALER_APP_SERVICE = "";

    //3th part Env set
    public static String API_BASE_URL_REPORT_SERVICE = "";
    public static String API_BASE_URL_USED_CAR_SERVICE = "";
    public static String API_BASE_URL_SM_UAA_SERVICE = "";
    public static String API_BASE_URL_SM_USER_SERVICE = "";

    //权限ID
    public static final String P_SA_APP_HOME = "sa.app_home";
    public static final String E_HOME_APPROVAL_SUBVIEW = "sa.app_home.approval_subview";
    public static final String E_HOME_SCROLL_SUBVIEW = "sa.app_home.scroll_subview";
    public static final String P_SA_APP_RESOURCE_FILTER = "sa.app_resources_filter";
    public static final String E_RESOURCE_FILTER_SHOW_SELF = "sa.app_resources_filter.only_show_self";
    public static final String E_RESOURCE_FILTER_SALES_CONSULTANT = "sa.app_resources_filter.by_sales_consultant";
    public static final String P_SA_APP_APPROVAL_LIST = "sa.app_approval_list";
    public static final String E_APPROVAL_LIST_REJECT = "sa.app_approval_list.reject";
    public static final String E_APPROVAL_LIST_APPROVAL = "sa.app_approval_list.approve";
    public static final String E_APPROVAL_LIST_OWNER_NAME = "sa.app_approval_list.owner_name";
    public static final String P_SA_APP_CUSTOMER_LIST = "sa.app_customer_list";
    public static final String E_CUSTOMER_LIST_OWNER_NAME = "sa.app_customer_list.owner_name";

    public static void setApiBaseUrlService(int num) {
        String apiBaseUrl = PRESET_BASE_URL[num];
        API_BASE_URL_LEADS_SERVICE = apiBaseUrl + "/sa-leads-service/";
        API_BASE_URL_OPPORTUNITY_SERVICE = apiBaseUrl + "/sa-opportunity-service/";
        API_BASE_URL_DIC_SERVICE = apiBaseUrl + "/sa-dictionary-service/";
        API_BASE_URL_NOTICE_SERVICE = apiBaseUrl + "/common-notice-center-service/";
        API_BASE_URL_KPI_SERVICE = apiBaseUrl + "/sa-reports-kpi-service/";
        API_BASE_URL_ORDER_SERVICE = apiBaseUrl + "/sa-order-service/";
        API_BASE_URL_MEMBERSHIP_SERVICE = apiBaseUrl + "/sa-membercenter-service/";
        API_BASE_URL_UPLOAD_SERVICE = apiBaseUrl + "/zuul/svw-usermanagement-service/";
        API_BASE_URL_USER_SERVICE = apiBaseUrl + "/svw-usermanagement-service/";
        API_BASE_URL_CAR_CONFIG_SERVICE = apiBaseUrl + "/common-car-config-service/";
        API_BASE_URL_DEALER_APP_SERVICE = apiBaseUrl + "/sa-dealer-app-service/";
    }

    //point to point debug
    public static void setApiBaseUrlServiceTest() {
        API_BASE_URL_LEADS_SERVICE = "http://9.200.46.73:9090/";
        API_BASE_URL_OPPORTUNITY_SERVICE = "http://9.200.46.73:7778/";
        API_BASE_URL_DIC_SERVICE = "http://9.200.46.73:7779/";
        API_BASE_URL_NOTICE_SERVICE = "http://9.200.46.73:8889/";
        API_BASE_URL_KPI_SERVICE = "http://9.200.46.73:8098/";
        API_BASE_URL_ORDER_SERVICE = "";// TODO
        API_BASE_URL_MEMBERSHIP_SERVICE = "";// TODO
        API_BASE_URL_CAR_CONFIG_SERVICE = "";// TODO
        API_BASE_URL_DEALER_APP_SERVICE = "";// TODO
    }

    public static void set3thApiBaseUrlService(int num) {
        API_BASE_URL_REPORT_SERVICE = PRESET_REPORT_BASE_URL[num];
        API_BASE_URL_USED_CAR_SERVICE = PRESET_USED_CAR_BASE_URL[num];
        API_BASE_URL_SM_UAA_SERVICE = PRESET_SM_SUAA_BASE_URL[num];
        API_BASE_URL_SM_USER_SERVICE = PRESET_SM_SUSER_BASE_URL[num];
    }

    public static void setSMCommonParam(int num) {
        CLIENT_ID = SM_CURRENT_CLIENT_ID[num];
        APP_ID = SM_CURRENT_APP_ID[num];
    }

    //0:DEV 1:Pre-Production 2:Production 3:QA 4:INT
    public static final String[] PRESET_BASE_URL = {
            "http://106.14.166.160:8765",
            "https://pre.apigateway.svwsx.cn",
            "https://api.mysvw.com",
            "http://test.apigateway.svwsx.cn",
            "http://uat.apigateway.svwsx.cn"};

    //0:DEV 1:Pre-Production 2:Production 3:QA 4:INT
    public static final String[] PRESET_REPORT_BASE_URL = {
            "http://106.14.166.160:7062",
            "https://pre.h5.svwsx.cn:442",
            "https://daadmin.mysvw.com",
            "http://test.dcop.svwsx.cn",
            "http://uat.dcop.svwsx.cn"};

    //0:DEV 1:Pre-Production 2:Production 3:QA 4:INT
    public static final String[] PRESET_USED_CAR_BASE_URL = {
            "http://121.40.207.195:5000",
            "http://121.40.207.195:5000",
            "http://121.40.207.195:5000",
            "http://121.40.207.195:5000",
            "http://121.40.207.195:5000"};

    //0:DEV 1:Pre-Production 2:Production 3:QA 4:INT
    public static final String[] PRESET_SM_SUAA_BASE_URL = {
            "http://dev.suaa.svwsx.cn",
            "https://pre.suaa.svwsx.cn:668",
            "https://passport-org.mysvw.com",
            "http://test.suaa.svwsx.cn",
            "http://dev.suaa.svwsx.cn"};

    //0:DEV 1:Pre-Production 2:Production 3:QA 4:INT
    public static final String[] PRESET_SM_SUSER_BASE_URL = {
            "http://dev.suser.svwsx.cn",
            "https://pre.suser.svwsx.cn:667",
            "https://iam-org.mysvw.com",
            "http://test.suser.svwsx.cn",
            "http://dev.suser.svwsx.cn"};

    //0:DEV 1:Pre-Production 2:Production 3:QA 4:INT
    public static final String[] SM_CURRENT_CLIENT_ID = {
            "b814a337360f40edaeaf6cfe401011e8",
            "b814a337360f40edaeaf6cfe401011e8",
            "b814a337360f40edaeaf6cfe401011e8",
            "b814a337360f40edaeaf6cfe401011e8",
            "b814a337360f40edaeaf6cfe401011e8"};

    //0:DEV 1:Pre-Production 2:Production 3:QA 4:INT
    public static final String[] SM_CURRENT_APP_ID = {
            "",
            "4",
            "4",
            "4",
            ""};

}
