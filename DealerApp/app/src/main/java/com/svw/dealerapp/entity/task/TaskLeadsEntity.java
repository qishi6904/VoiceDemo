package com.svw.dealerapp.entity.task;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;

import java.util.List;

/**
 * Created by qinshi on 8/27/2017.
 */

public class TaskLeadsEntity {

    private List<TaskLeadsInfoEntity> data;
    private PageBean page;

    public PageBean getPage() {
        return page;
    }

    public void setPage(PageBean page) {
        this.page = page;
    }

    public List<TaskLeadsInfoEntity> getData() {
        return data;
    }

    public void setData(List<TaskLeadsInfoEntity> data) {
        this.data = data;
    }

    public static class TaskLeadsInfoEntity implements Parcelable {

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
        private String imageUrl;
        private String modelId;
        private String delayDays;
        private String recomName;
        private String recomMobile;
        private String seriesId;
        private String seriesName;
        private String activityId;
        private String activitySubject;

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

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
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

        public String getOppId() {
            return oppId;
        }

        public void setOppId(String oppId) {
            this.oppId = oppId;
        }

        public String getImageUrl() {
            return imageUrl;
        }

        public void setImageUrl(String imageUrl) {
            this.imageUrl = imageUrl;
        }

        public String getModelId() {
            return modelId;
        }

        public void setModelId(String modelId) {
            this.modelId = modelId;
        }

        public String getRecomName() {
            return recomName;
        }

        public void setRecomName(String recomName) {
            this.recomName = recomName;
        }

        public String getRecomMobile() {
            return recomMobile;
        }

        public void setRecomMobile(String recomMobile) {
            this.recomMobile = recomMobile;
        }

        public String[] getCustDescriptions() {
            return custDescriptions;
        }

        public void setCustDescriptions(String[] custDescriptions) {
            this.custDescriptions = custDescriptions;
        }

        public String getDelayDays() {
            return delayDays;
        }

        public void setDelayDays(String delayDays) {
            this.delayDays = delayDays;
        }

        public String getSeriesId() {
            return seriesId;
        }

        public void setSeriesId(String seriesId) {
            this.seriesId = seriesId;
        }

        public String getSeriesName() {
            return seriesName;
        }

        public void setSeriesName(String seriesName) {
            this.seriesName = seriesName;
        }

        public String getActivityId() {
            return activityId;
        }

        public void setActivityId(String activityId) {
            this.activityId = activityId;
        }

        public String getActivitySubject() {
            return activitySubject;
        }

        public void setActivitySubject(String activitySubject) {
            this.activitySubject = activitySubject;
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
            dest.writeString(this.imageUrl);
            dest.writeString(this.modelId);
            dest.writeString(this.delayDays);
            dest.writeString(this.recomName);
            dest.writeString(this.recomMobile);
            dest.writeString(this.seriesId);
            dest.writeString(this.seriesName);
            dest.writeString(this.activityId);
            dest.writeString(this.activitySubject);

            dest.writeStringArray(this.custDescriptions);
        }

        public TaskLeadsInfoEntity() {
        }

        protected TaskLeadsInfoEntity(Parcel in) {
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
            this.imageUrl = in.readString();
            this.modelId = in.readString();
            this.delayDays = in.readString();
            this.recomName = in.readString();
            this.recomMobile = in.readString();
            this.seriesId = in.readString();
            this.seriesName = in.readString();
            this.activityId = in.readString();
            this.activitySubject = in.readString();
            this.custDescriptions = in.createStringArray();
        }

        public static final Parcelable.Creator<TaskLeadsInfoEntity> CREATOR = new Parcelable.Creator<TaskLeadsInfoEntity>() {
            @Override
            public TaskLeadsInfoEntity createFromParcel(Parcel source) {
                return new TaskLeadsInfoEntity(source);
            }

            @Override
            public TaskLeadsInfoEntity[] newArray(int size) {
                return new TaskLeadsInfoEntity[size];
            }
        };
    }
}
