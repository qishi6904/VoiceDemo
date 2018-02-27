package com.svw.dealerapp.api;

import com.svw.dealerapp.api.dictionary.DictionaryApi;
import com.svw.dealerapp.api.home.ActivityListApi;
import com.svw.dealerapp.api.home.CheckRepeatApi;
import com.svw.dealerapp.api.home.CreateTrafficApi;
import com.svw.dealerapp.api.home.HomeApi;
import com.svw.dealerapp.api.login.LoginApi;
import com.svw.dealerapp.api.login.SMLoginApi;
import com.svw.dealerapp.api.login.SMLoginoutApi;
import com.svw.dealerapp.api.login.SMRefreshSubTokenApi;
import com.svw.dealerapp.api.login.SMRefreshTokenApi;
import com.svw.dealerapp.api.login.SMResetPassApi;
import com.svw.dealerapp.api.login.SMUploadImageApi;
import com.svw.dealerapp.api.login.SMUserInfoApi;
import com.svw.dealerapp.api.login.SMUserPrivilegeApi;
import com.svw.dealerapp.api.mine.AliPushRegisterDeviceApi;
import com.svw.dealerapp.api.mine.AliPushUnregisterDeviceApi;
import com.svw.dealerapp.api.mine.CompleteApproveDataApi;
import com.svw.dealerapp.api.mine.LogoutApi;
import com.svw.dealerapp.api.mine.MineHomeApi;
import com.svw.dealerapp.api.mine.NotificationDataApi;
import com.svw.dealerapp.api.mine.PawApi;
import com.svw.dealerapp.api.mine.PostApproveApi;
import com.svw.dealerapp.api.mine.PostDeleteNotificationApi;
import com.svw.dealerapp.api.mine.PostNotificationReadApi;
import com.svw.dealerapp.api.mine.RejectApproveApi;
import com.svw.dealerapp.api.mine.ScheduleCompleteApi;
import com.svw.dealerapp.api.mine.ScheduleCreateApi;
import com.svw.dealerapp.api.mine.ScheduleDeleteApi;
import com.svw.dealerapp.api.mine.ScheduleUpdateApi;
import com.svw.dealerapp.api.mine.ScheduleWaitApi;
import com.svw.dealerapp.api.mine.WaitApproveDataApi;
import com.svw.dealerapp.api.newcustomer.ActivateYellowCardApi;
import com.svw.dealerapp.api.newcustomer.CarTypesApi;
import com.svw.dealerapp.api.newcustomer.CheckBeforeActivateYCApi;
import com.svw.dealerapp.api.newcustomer.RemarkListDataListApi;
import com.svw.dealerapp.api.newcustomer.ShowOpportunitiesDetailApi;
import com.svw.dealerapp.api.newcustomer.SubmitFollowupInfoApi;
import com.svw.dealerapp.api.newcustomer.SubmitOpportunitiesV2Api;
import com.svw.dealerapp.api.newcustomer.SubmitRemarkApi;
import com.svw.dealerapp.api.newcustomer.UpdateOpportunitiesApi;
import com.svw.dealerapp.api.optionalpackage.OptionalPackageApi;
import com.svw.dealerapp.api.order.AppraiserApi;
import com.svw.dealerapp.api.order.CancelOrderFollowupApi;
import com.svw.dealerapp.api.order.CreateOrderApi;
import com.svw.dealerapp.api.order.EditOrderApi;
import com.svw.dealerapp.api.order.MembershipApi;
import com.svw.dealerapp.api.order.PostCancelOrderApi;
import com.svw.dealerapp.api.order.QueryOrderDetailApi;
import com.svw.dealerapp.api.order.ReportOtdOrderApi;
import com.svw.dealerapp.api.report.ReportApi;
import com.svw.dealerapp.api.report.ReportHomeApi;
import com.svw.dealerapp.api.resource.DealCustomerApi;
import com.svw.dealerapp.api.resource.FailedCustomerApi;
import com.svw.dealerapp.api.resource.LeadRelateValidApi;
import com.svw.dealerapp.api.resource.ManagerSalesApi;
import com.svw.dealerapp.api.resource.MyYellowCardListApi;
import com.svw.dealerapp.api.resource.OrderCustomerApi;
import com.svw.dealerapp.api.resource.PostActiveYellowCardApi;
import com.svw.dealerapp.api.resource.PostInvalidTrafficStatusApi;
import com.svw.dealerapp.api.resource.PostTransferYellowCardApi;
import com.svw.dealerapp.api.resource.PostVipCustomerApi;
import com.svw.dealerapp.api.resource.SleepCustomerApi;
import com.svw.dealerapp.api.resource.TransferSalesApi;
import com.svw.dealerapp.api.resource.YellowCardListApi;
import com.svw.dealerapp.api.task.BenefitApi;
import com.svw.dealerapp.api.task.TaskECommerceApi;
import com.svw.dealerapp.api.task.TaskFollowUpApi;
import com.svw.dealerapp.api.task.TaskLeadApi;
import com.svw.dealerapp.api.task.TaskTrafficApi;
import com.svw.dealerapp.api.upload.UploadApi;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by qinshi on 1/18/2018.
 */

public class ApiManager {

    //按照微服务方式拆分API

    private static List<Class> carConfigApiList;
    private static List<Class> dealerAppList;
    private static List<Class> dictionaryApiList;
    private static List<Class> KIPApiList;
    private static List<Class> leadsApiList;
    private static List<Class> memberShipApiList;
    private static List<Class> noticeApiList;
    private static List<Class> opportunityApiList;
    private static List<Class> orderApiList;
    private static List<Class> uploadApiList;
    private static List<Class> userApiList;
    private static List<Class> suaaApiList;
    private static List<Class> suserApiList;

    public static void init() {
        generateCarConfigApiList();
        generateDealerAppList();
        generateDictionaryApiList();
        generateKIPApiList();
        generateLeadsApiList();
        generateMemberShipApiList();
        generateNoticeApiList();
        generateOrderApiList();
        generateUploadApiList();
        generateUserApiList();
        generateOpportunityApiList();
        generateSuaaApiList();
        generateSuserApiList();
    }

    public static List<Class> generateCarConfigApiList() {
        carConfigApiList = new ArrayList<>();
        carConfigApiList.add(OptionalPackageApi.class);
        carConfigApiList.add(CarTypesApi.class);

        return carConfigApiList;
    }

    public static List<Class> generateDealerAppList() {
        dealerAppList = new ArrayList<>();

        return dealerAppList;
    }

    public static List<Class> generateDictionaryApiList() {
        dictionaryApiList = new ArrayList<>();
        dictionaryApiList.add(DictionaryApi.class);

        return dictionaryApiList;
    }

    public static List<Class> generateKIPApiList() {
        KIPApiList = new ArrayList<>();
        KIPApiList.add(ReportHomeApi.class);

        return KIPApiList;
    }

    public static List<Class> generateLeadsApiList() {
        leadsApiList = new ArrayList<>();
        leadsApiList.add(ActivityListApi.class);
        leadsApiList.add(CreateTrafficApi.class);
        leadsApiList.add(BenefitApi.class);
        leadsApiList.add(PostInvalidTrafficStatusApi.class);
        leadsApiList.add(TaskLeadApi.class);
        leadsApiList.add(TaskTrafficApi.class);
        leadsApiList.add(CheckRepeatApi.class);

        return leadsApiList;
    }

    public static List<Class> generateMemberShipApiList() {
        memberShipApiList = new ArrayList<>();
        memberShipApiList.add(MembershipApi.class);

        return memberShipApiList;
    }

    public static List<Class> generateNoticeApiList() {
        noticeApiList = new ArrayList<>();
        noticeApiList.add(AliPushRegisterDeviceApi.class);
        noticeApiList.add(AliPushUnregisterDeviceApi.class);
        noticeApiList.add(NotificationDataApi.class);
        noticeApiList.add(PostDeleteNotificationApi.class);
        noticeApiList.add(PostNotificationReadApi.class);

        return noticeApiList;
    }

    public static List<Class> generateOpportunityApiList() {
        opportunityApiList = new ArrayList<>();
        opportunityApiList.add(CompleteApproveDataApi.class);
        opportunityApiList.add(WaitApproveDataApi.class);
        opportunityApiList.add(PostApproveApi.class);
        opportunityApiList.add(RejectApproveApi.class);
        opportunityApiList.add(AppraiserApi.class);
        opportunityApiList.add(ScheduleCompleteApi.class);
        opportunityApiList.add(ScheduleWaitApi.class);
        opportunityApiList.add(ScheduleCreateApi.class);
        opportunityApiList.add(ScheduleDeleteApi.class);
        opportunityApiList.add(ScheduleUpdateApi.class);
        opportunityApiList.add(FailedCustomerApi.class);
        opportunityApiList.add(SleepCustomerApi.class);
        opportunityApiList.add(LeadRelateValidApi.class);
        opportunityApiList.add(MyYellowCardListApi.class);
        opportunityApiList.add(PostActiveYellowCardApi.class);
        opportunityApiList.add(ActivateYellowCardApi.class);
        opportunityApiList.add(PostTransferYellowCardApi.class);
        opportunityApiList.add(PostVipCustomerApi.class);
        opportunityApiList.add(UpdateOpportunitiesApi.class);
        opportunityApiList.add(ShowOpportunitiesDetailApi.class);
        opportunityApiList.add(YellowCardListApi.class);
        opportunityApiList.add(TaskECommerceApi.class);
        opportunityApiList.add(TaskFollowUpApi.class);
        opportunityApiList.add(CheckBeforeActivateYCApi.class);
        opportunityApiList.add(RemarkListDataListApi.class);
        opportunityApiList.add(SubmitRemarkApi.class);
        opportunityApiList.add(SubmitFollowupInfoApi.class);
        opportunityApiList.add(SubmitOpportunitiesV2Api.class);
        opportunityApiList.add(HomeApi.class);
        opportunityApiList.add(MineHomeApi.class);

        return opportunityApiList;
    }

    public static List<Class> generateOrderApiList() {
        orderApiList = new ArrayList<>();
        orderApiList.add(CancelOrderFollowupApi.class);
        orderApiList.add(CreateOrderApi.class);
        orderApiList.add(EditOrderApi.class);
        orderApiList.add(PostCancelOrderApi.class);
        orderApiList.add(QueryOrderDetailApi.class);
        orderApiList.add(ReportOtdOrderApi.class);
        orderApiList.add(DealCustomerApi.class);
        orderApiList.add(OrderCustomerApi.class);

        return orderApiList;
    }

    public static List<Class> generateUploadApiList() {
        uploadApiList = new ArrayList<>();
        uploadApiList.add(UploadApi.class);

        return uploadApiList;
    }

    public static List<Class> generateUserApiList() {
        userApiList = new ArrayList<>();
        userApiList.add(LoginApi.class);
        userApiList.add(LogoutApi.class);
        userApiList.add(PawApi.class);
        userApiList.add(ReportApi.class);

        return userApiList;
    }

    public static List<Class> generateSuaaApiList() {
        suaaApiList = new ArrayList<>();
        suaaApiList.add(SMLoginApi.class);
        suaaApiList.add(SMLoginoutApi.class);
        suaaApiList.add(SMRefreshTokenApi.class);
        suaaApiList.add(SMRefreshSubTokenApi.class);


        return suaaApiList;
    }

    public static List<Class> generateSuserApiList() {
        suserApiList = new ArrayList<>();
        suserApiList.add(SMUserInfoApi.class);
        suserApiList.add(SMResetPassApi.class);
        suserApiList.add(SMUserPrivilegeApi.class);
        suserApiList.add(SMUploadImageApi.class);
        suserApiList.add(TransferSalesApi.class);
        suserApiList.add(ManagerSalesApi.class);

        return suserApiList;
    }

    private static <T> boolean isContainApi(List<Class> list, Class<T> clazz) {
        if (list.contains(clazz)) {
            return true;
        }
        return false;
    }

    public static <T> boolean isUserApi(Class<T> clazz) {
        return isContainApi(userApiList, clazz);
    }

    public static <T> boolean isUploadApi(Class<T> clazz) {
        return isContainApi(uploadApiList, clazz);
    }

    public static <T> boolean isOrderApi(Class<T> clazz) {
        return isContainApi(orderApiList, clazz);
    }

    public static <T> boolean isOpportunityApi(Class<T> clazz) {
        return isContainApi(opportunityApiList, clazz);
    }

    public static <T> boolean isNoticeApi(Class<T> clazz) {
        return isContainApi(noticeApiList, clazz);
    }

    public static <T> boolean isMemberShipApi(Class<T> clazz) {
        return isContainApi(memberShipApiList, clazz);
    }

    public static <T> boolean isLeadsApi(Class<T> clazz) {
        return isContainApi(leadsApiList, clazz);
    }

    public static <T> boolean isKIPApi(Class<T> clazz) {
        return isContainApi(KIPApiList, clazz);
    }

    public static <T> boolean isDictionaryApi(Class<T> clazz) {
        return isContainApi(dictionaryApiList, clazz);
    }

    public static <T> boolean isDealerApi(Class<T> clazz) {
        return isContainApi(dealerAppList, clazz);
    }

    public static <T> boolean isCarConfigApi(Class<T> clazz) {
        return isContainApi(carConfigApiList, clazz);
    }

    public static <T> boolean isSuaaApi(Class<T> clazz) {
        return isContainApi(suaaApiList, clazz);
    }

    public static <T> boolean isSuserApi(Class<T> clazz) {
        return isContainApi(suserApiList, clazz);
    }
}
