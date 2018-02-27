package com.svw.dealerapp.entity.task;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;

import java.util.List;

/**
 * Created by qinshi on 6/1/2017.
 */

public class TaskTrafficEntity {

    private PageBean page;
    private List<TaskTrafficInfoEntity> data;

    public PageBean getPage() {
        return page;
    }

    public void setPage(PageBean page) {
        this.page = page;
    }

    public List<TaskTrafficInfoEntity> getData() {
        return data;
    }

    public void setData(List<TaskTrafficInfoEntity> data) {
        this.data = data;
    }

    public static class TaskTrafficInfoEntity implements Parcelable {

        /**
         * businessFlag : 0
         * businessId : 0a0b77ed-a459-4ddb-9d9f-4a2a9a837d1d
         * carModelDescCn : 辉昂380TSI行政旗舰版
         * carModelId : 3E95FZ
         * channelId : 7b66b108-4010-453a-aae9-faad87a3629b
         * channelName :
         * cityId : 041001
         * cityName :
         * custAge : 10020
         * custAgeName : 25-35
         * custDescription : Beauty
         * custEmail : xc4jq_wpyu@126.com
         * custGender : 0
         * custMobile : 12674994963
         * custName : ND 琴
         * custTelephone : 08183171863
         * custWechat : enyabgl6ryl974q
         * isDuplication :
         * isFlow : 1
         * leadsId : 0a0b77ed-a459-4ddb-9d9f-4a2a9a837d1d
         * leadsStatusId : 10530
         * leadsStatusName :
         * leaveDate :
         * oppOwnerName : 3
         * orgId : 167dbe79-8f56-4333-ae68-c64f1ec87a84
         * orgName : 安亭经销商
         * provinceId : 041
         * provinceName :
         * salesConsultant : e2914449-546f-46d9-9df9-52edc665a7ee
         * srcTypeId : 11050
         * srcTypeName : 外拓
         * statusReason : 客户未留下有效联络信息，无法建卡
         * taskBeginTime : 2017-05-20 08:00:00.0
         * createTime : 2017-05-20 08:00:00.0
         * taskDesc : 11
         * taskId : 7f88419f-4160-478c-8ebf-d7a90044629d
         * taskOwner : 18964561062
         * taskProcessTime :
         * taskResultDesc : other
         * taskScheduleTime : 2017-07-01 00:00:00.0
         * taskStatusId : 112
         * taskTypeId : 12
         * taskUserId : 12131
         * walkinDate :
         */

        private String businessFlag;
        private String businessId;
        private String carModelDescCn;
        private String carModelId;
        private String channelId;
        private String channelName;
        private String cityId;
        private String cityName;
        private String custAge;
        private String custAgeName;
        private String custDescription;
        private String custEmail;
        private String custGender;
        private String custMobile;
        private String custName;
        private String custTelephone;
        private String custWechat;
        private String isDuplication;
        private String isFlow;
        private String leadsId;
        private String leadsStatusId;
        private String leadsStatusName;
        private String leaveDate;
        private String oppOwnerName;
        private String orgId;
        private String orgName;
        private String provinceId;
        private String provinceName;
        private String salesConsultant;
        private String srcTypeId;
        private String srcTypeName;
        private String statusReason;
        private String taskBeginTime;
        private String createTime;
        private String taskDesc;
        private String taskId;
        private String taskOwner;
        private String taskProcessTime;
        private String taskResultDesc;
        private String taskScheduleTime;
        private String taskStatusId;
        private String taskTypeId;
        private String taskUserId;
        private String walkinDate;
        private String oppId;

        private String[] custDescriptions;

        public String getBusinessFlag() {
            return businessFlag;
        }

        public void setBusinessFlag(String businessFlag) {
            this.businessFlag = businessFlag;
        }

        public String getBusinessId() {
            return businessId;
        }

        public void setBusinessId(String businessId) {
            this.businessId = businessId;
        }

        public String getCarModelDescCn() {
            return carModelDescCn;
        }

        public void setCarModelDescCn(String carModelDescCn) {
            this.carModelDescCn = carModelDescCn;
        }

        public String getCarModelId() {
            return carModelId;
        }

        public void setCarModelId(String carModelId) {
            this.carModelId = carModelId;
        }

        public String getChannelId() {
            return channelId;
        }

        public void setChannelId(String channelId) {
            this.channelId = channelId;
        }

        public String getChannelName() {
            return channelName;
        }

        public void setChannelName(String channelName) {
            this.channelName = channelName;
        }

        public String getCityId() {
            return cityId;
        }

        public void setCityId(String cityId) {
            this.cityId = cityId;
        }

        public String getCityName() {
            return cityName;
        }

        public void setCityName(String cityName) {
            this.cityName = cityName;
        }

        public String getCustAge() {
            return custAge;
        }

        public void setCustAge(String custAge) {
            this.custAge = custAge;
        }

        public String getCustAgeName() {
            return custAgeName;
        }

        public void setCustAgeName(String custAgeName) {
            this.custAgeName = custAgeName;
        }

        public String getCustDescription() {
            return custDescription;
        }

        public void setCustDescription(String custDescription) {
            this.custDescription = custDescription;
        }

        public String getCustEmail() {
            return custEmail;
        }

        public void setCustEmail(String custEmail) {
            this.custEmail = custEmail;
        }

        public String getCustGender() {
            return custGender;
        }

        public void setCustGender(String custGender) {
            this.custGender = custGender;
        }

        public String getCustMobile() {
            return custMobile;
        }

        public void setCustMobile(String custMobile) {
            this.custMobile = custMobile;
        }

        public String getCustName() {
            return custName;
        }

        public void setCustName(String custName) {
            this.custName = custName;
        }

        public String getCustTelephone() {
            return custTelephone;
        }

        public void setCustTelephone(String custTelephone) {
            this.custTelephone = custTelephone;
        }

        public String getCustWechat() {
            return custWechat;
        }

        public void setCustWechat(String custWechat) {
            this.custWechat = custWechat;
        }

        public String getIsDuplication() {
            return isDuplication;
        }

        public void setIsDuplication(String isDuplication) {
            this.isDuplication = isDuplication;
        }

        public String getIsFlow() {
            return isFlow;
        }

        public void setIsFlow(String isFlow) {
            this.isFlow = isFlow;
        }

        public String getLeadsId() {
            return leadsId;
        }

        public void setLeadsId(String leadsId) {
            this.leadsId = leadsId;
        }

        public String getLeadsStatusId() {
            return leadsStatusId;
        }

        public void setLeadsStatusId(String leadsStatusId) {
            this.leadsStatusId = leadsStatusId;
        }

        public String getLeadsStatusName() {
            return leadsStatusName;
        }

        public void setLeadsStatusName(String leadsStatusName) {
            this.leadsStatusName = leadsStatusName;
        }

        public String getLeaveDate() {
            return leaveDate;
        }

        public void setLeaveDate(String leaveDate) {
            this.leaveDate = leaveDate;
        }

        public String getOppOwnerName() {
            return oppOwnerName;
        }

        public void setOppOwnerName(String oppOwnerName) {
            this.oppOwnerName = oppOwnerName;
        }

        public String getOrgId() {
            return orgId;
        }

        public void setOrgId(String orgId) {
            this.orgId = orgId;
        }

        public String getOrgName() {
            return orgName;
        }

        public void setOrgName(String orgName) {
            this.orgName = orgName;
        }

        public String getProvinceId() {
            return provinceId;
        }

        public void setProvinceId(String provinceId) {
            this.provinceId = provinceId;
        }

        public String getProvinceName() {
            return provinceName;
        }

        public void setProvinceName(String provinceName) {
            this.provinceName = provinceName;
        }

        public String getSalesConsultant() {
            return salesConsultant;
        }

        public void setSalesConsultant(String salesConsultant) {
            this.salesConsultant = salesConsultant;
        }

        public String getSrcTypeId() {
            return srcTypeId;
        }

        public void setSrcTypeId(String srcTypeId) {
            this.srcTypeId = srcTypeId;
        }

        public String getSrcTypeName() {
            return srcTypeName;
        }

        public void setSrcTypeName(String srcTypeName) {
            this.srcTypeName = srcTypeName;
        }

        public String getStatusReason() {
            return statusReason;
        }

        public void setStatusReason(String statusReason) {
            this.statusReason = statusReason;
        }

        public String getTaskBeginTime() {
            return taskBeginTime;
        }

        public void setTaskBeginTime(String taskBeginTime) {
            this.taskBeginTime = taskBeginTime;
        }

        public String getTaskDesc() {
            return taskDesc;
        }

        public void setTaskDesc(String taskDesc) {
            this.taskDesc = taskDesc;
        }

        public String getTaskId() {
            return taskId;
        }

        public void setTaskId(String taskId) {
            this.taskId = taskId;
        }

        public String getTaskOwner() {
            return taskOwner;
        }

        public void setTaskOwner(String taskOwner) {
            this.taskOwner = taskOwner;
        }

        public String getTaskProcessTime() {
            return taskProcessTime;
        }

        public void setTaskProcessTime(String taskProcessTime) {
            this.taskProcessTime = taskProcessTime;
        }

        public String getTaskResultDesc() {
            return taskResultDesc;
        }

        public void setTaskResultDesc(String taskResultDesc) {
            this.taskResultDesc = taskResultDesc;
        }

        public String getTaskScheduleTime() {
            return taskScheduleTime;
        }

        public void setTaskScheduleTime(String taskScheduleTime) {
            this.taskScheduleTime = taskScheduleTime;
        }

        public String getTaskStatusId() {
            return taskStatusId;
        }

        public void setTaskStatusId(String taskStatusId) {
            this.taskStatusId = taskStatusId;
        }

        public String getTaskTypeId() {
            return taskTypeId;
        }

        public void setTaskTypeId(String taskTypeId) {
            this.taskTypeId = taskTypeId;
        }

        public String getTaskUserId() {
            return taskUserId;
        }

        public void setTaskUserId(String taskUserId) {
            this.taskUserId = taskUserId;
        }

        public String getWalkinDate() {
            return walkinDate;
        }

        public void setWalkinDate(String walkinDate) {
            this.walkinDate = walkinDate;
        }

        public String[] getCustDescriptions(){
            return custDescriptions;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public String getOppId() {
            return oppId;
        }

        public void setOppId(String oppId) {
            this.oppId = oppId;
        }

        /**
         * 折分描述
         */
        public void splitDescriptions(){
            if(!TextUtils.isEmpty(custDescription)){
                custDescription = custDescription.replaceAll(" +", " ");
                if(custDescription.contains(" ")){
                    custDescriptions = custDescription.split(" ");
                }else {
                    custDescriptions = new String[]{custDescription};
                }
            }
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.businessFlag);
            dest.writeString(this.businessId);
            dest.writeString(this.carModelDescCn);
            dest.writeString(this.carModelId);
            dest.writeString(this.channelId);
            dest.writeString(this.channelName);
            dest.writeString(this.cityId);
            dest.writeString(this.cityName);
            dest.writeString(this.custAge);
            dest.writeString(this.custAgeName);
            dest.writeString(this.custDescription);
            dest.writeString(this.custEmail);
            dest.writeString(this.custGender);
            dest.writeString(this.custMobile);
            dest.writeString(this.custName);
            dest.writeString(this.custTelephone);
            dest.writeString(this.custWechat);
            dest.writeString(this.isDuplication);
            dest.writeString(this.isFlow);
            dest.writeString(this.leadsId);
            dest.writeString(this.leadsStatusId);
            dest.writeString(this.leadsStatusName);
            dest.writeString(this.leaveDate);
            dest.writeString(this.oppOwnerName);
            dest.writeString(this.orgId);
            dest.writeString(this.orgName);
            dest.writeString(this.provinceId);
            dest.writeString(this.provinceName);
            dest.writeString(this.salesConsultant);
            dest.writeString(this.srcTypeId);
            dest.writeString(this.srcTypeName);
            dest.writeString(this.statusReason);
            dest.writeString(this.taskBeginTime);
            dest.writeString(this.createTime);
            dest.writeString(this.taskDesc);
            dest.writeString(this.taskId);
            dest.writeString(this.taskOwner);
            dest.writeString(this.taskProcessTime);
            dest.writeString(this.taskResultDesc);
            dest.writeString(this.taskScheduleTime);
            dest.writeString(this.taskStatusId);
            dest.writeString(this.taskTypeId);
            dest.writeString(this.taskUserId);
            dest.writeString(this.walkinDate);
            dest.writeString(this.oppId);
            dest.writeStringArray(this.custDescriptions);
        }

        public TaskTrafficInfoEntity() {
        }

        protected TaskTrafficInfoEntity(Parcel in) {
            this.businessFlag = in.readString();
            this.businessId = in.readString();
            this.carModelDescCn = in.readString();
            this.carModelId = in.readString();
            this.channelId = in.readString();
            this.channelName = in.readString();
            this.cityId = in.readString();
            this.cityName = in.readString();
            this.custAge = in.readString();
            this.custAgeName = in.readString();
            this.custDescription = in.readString();
            this.custEmail = in.readString();
            this.custGender = in.readString();
            this.custMobile = in.readString();
            this.custName = in.readString();
            this.custTelephone = in.readString();
            this.custWechat = in.readString();
            this.isDuplication = in.readString();
            this.isFlow = in.readString();
            this.leadsId = in.readString();
            this.leadsStatusId = in.readString();
            this.leadsStatusName = in.readString();
            this.leaveDate = in.readString();
            this.oppOwnerName = in.readString();
            this.orgId = in.readString();
            this.orgName = in.readString();
            this.provinceId = in.readString();
            this.provinceName = in.readString();
            this.salesConsultant = in.readString();
            this.srcTypeId = in.readString();
            this.srcTypeName = in.readString();
            this.statusReason = in.readString();
            this.taskBeginTime = in.readString();
            this.createTime = in.readString();
            this.taskDesc = in.readString();
            this.taskId = in.readString();
            this.taskOwner = in.readString();
            this.taskProcessTime = in.readString();
            this.taskResultDesc = in.readString();
            this.taskScheduleTime = in.readString();
            this.taskStatusId = in.readString();
            this.taskTypeId = in.readString();
            this.taskUserId = in.readString();
            this.walkinDate = in.readString();
            this.oppId = in.readString();
            this.custDescriptions = in.createStringArray();
        }

        public static final Parcelable.Creator<TaskTrafficInfoEntity> CREATOR = new Parcelable.Creator<TaskTrafficInfoEntity>() {
            @Override
            public TaskTrafficInfoEntity createFromParcel(Parcel source) {
                return new TaskTrafficInfoEntity(source);
            }

            @Override
            public TaskTrafficInfoEntity[] newArray(int size) {
                return new TaskTrafficInfoEntity[size];
            }
        };
    }
}
