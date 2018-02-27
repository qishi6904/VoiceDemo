package com.svw.dealerapp.entity.resource;

import java.util.List;

/**
 * Created by qinshi on 7/28/2017.
 */

public class OrderCustomerEntity {

    private PageBean page;
    private List<OrderCustomerInfoEntity> orderList;

    public PageBean getPage() {
        return page;
    }

    public void setPage(PageBean page) {
        this.page = page;
    }

    public List<OrderCustomerInfoEntity> getOrderList() {
        return orderList;
    }

    public void setOrderList(List<OrderCustomerInfoEntity> orderList) {
        this.orderList = orderList;
    }

    public class OrderCustomerInfoEntity{

        private String modelId;                 // 来源渠道ID
        private String channelId;               // 车型ID
        private String createTime;              // 订单创建时间 （默认排序字段）
        private String custMobile;              // 手机号
        private String custName;                // 车主姓名
        private String custTelephone;           // 固定电话
        private String oppId;                   // 潜客ID
        private String salesConsultant;         // 销售顾问id
        private String salesConsultantName;     // 销售顾问名字
        private String seriesName;              // 车系名称
        private String srcTypeName;             // 来源类型名称
        private String deliveryDate;            // 预计提车日期
        private String imageUrl;                // 来源图标地址
        private String orderStatus;             // 订单状态0：NEW，1：取消， 2：OTD上报中，3：OTD取消中，4：绑车中（开票中），5：已绑车（已开票），6：已付全款（已交车）
        private String srcTypeId;               // 来源类型ID
        private String orderId;                 // 订单ID
        private String seriesId;                // 车系ID
        private String orgId;                   // 组织ID
        private String updateTime;              // 更新时间
        private String isKeyuser;               // 是否重点客户
        private String oppStatusId;             // 潜客状态ID
        private String oppLevel;                // 潜客级别
        private String leadsId;                 // 线索ID
        private String isOtdOrder;              // 是否是OTD订单

        public String getModelId() {
            return modelId;
        }

        public String getIsOtdOrder() {
            return isOtdOrder;
        }

        public void setIsOtdOrder(String isOtdOrder) {
            this.isOtdOrder = isOtdOrder;
        }

        public void setModelId(String modelId) {
            this.modelId = modelId;
        }

        public String getChannelId() {
            return channelId;
        }

        public void setChannelId(String channelId) {
            this.channelId = channelId;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
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

        public String getOppId() {
            return oppId;
        }

        public void setOppId(String oppId) {
            this.oppId = oppId;
        }

        public String getSalesConsultant() {
            return salesConsultant;
        }

        public void setSalesConsultant(String salesConsultant) {
            this.salesConsultant = salesConsultant;
        }

        public String getSalesConsultantName() {
            return salesConsultantName;
        }

        public void setSalesConsultantName(String salesConsultantName) {
            this.salesConsultantName = salesConsultantName;
        }

        public String getSeriesName() {
            return seriesName;
        }

        public void setSeriesName(String seriesName) {
            this.seriesName = seriesName;
        }

        public String getSrcTypeName() {
            return srcTypeName;
        }

        public void setSrcTypeName(String srcTypeName) {
            this.srcTypeName = srcTypeName;
        }

        public String getDeliveryDate() {
            return deliveryDate;
        }

        public void setDeliveryDate(String deliveryDate) {
            this.deliveryDate = deliveryDate;
        }

        public String getImageUrl() {
            return imageUrl;
        }

        public void setImageUrl(String imageUrl) {
            this.imageUrl = imageUrl;
        }

        public String getOrderStatus() {
            return orderStatus;
        }

        public void setOrderStatus(String orderStatus) {
            this.orderStatus = orderStatus;
        }

        public String getSrcTypeId() {
            return srcTypeId;
        }

        public void setSrcTypeId(String srcTypeId) {
            this.srcTypeId = srcTypeId;
        }

        public String getOrderId() {
            return orderId;
        }

        public void setOrderId(String orderId) {
            this.orderId = orderId;
        }

        public String getSeriesId() {
            return seriesId;
        }

        public void setSeriesId(String seriesId) {
            this.seriesId = seriesId;
        }

        public String getOrgId() {
            return orgId;
        }

        public void setOrgId(String orgId) {
            this.orgId = orgId;
        }

        public String getUpdateTime() {
            return updateTime;
        }

        public void setUpdateTime(String updateTime) {
            this.updateTime = updateTime;
        }

        public String getIsKeyuser() {
            return isKeyuser;
        }

        public void setIsKeyuser(String isKeyuser) {
            this.isKeyuser = isKeyuser;
        }

        public String getOppStatusId() {
            return oppStatusId;
        }

        public void setOppStatusId(String oppStatusId) {
            this.oppStatusId = oppStatusId;
        }

        public String getOppLevel() {
            return oppLevel;
        }

        public void setOppLevel(String oppLevel) {
            this.oppLevel = oppLevel;
        }

        public String getLeadsId() {
            return leadsId;
        }

        public void setLeadsId(String leadsId) {
            this.leadsId = leadsId;
        }
    }
}
