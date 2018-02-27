package com.svw.dealerapp.entity.resource;

import java.util.List;

/**
 * Created by qinshi on 8/14/2017.
 */

public class DealCustomerEntity {

    private PageBean page;
    private List<DealCustomerInfoEntity> orderList;

    public PageBean getPage() {
        return page;
    }

    public void setPage(PageBean page) {
        this.page = page;
    }

    public List<DealCustomerInfoEntity> getOrderList() {
        return orderList;
    }

    public void setOrderList(List<DealCustomerInfoEntity> orderList) {
        this.orderList = orderList;
    }

    public class DealCustomerInfoEntity{

        private String custName;                // 车主姓名
        private String seriesName;              // 车系名称
        private String srcTypeName;             // 来源类型名称
        private String srcTypeId;               // 来源类型ID
        private String deliveryDate;            // 预计提车日期
        private String updateTime;              // 更新时间
        private String createTime;              // 创建时间
        private String salesConsultant;         // 销售顾问id
        private String salesConsultantName;     // 销售顾问名字

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        private String isKeyuser;               // 是否重点客户
        private String custTelephone;           // 固定电话
        private String custMobile;              // 手机号
        private String leadsId;                 // 线索ID
        private String oppId;                   // 潜客ID
        private String realDeliveryDate;        // 实际交车时间

        public String getCustName() {
            return custName;
        }

        public void setCustName(String custName) {
            this.custName = custName;
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

        public String getSrcTypeId() {
            return srcTypeId;
        }

        public void setSrcTypeId(String srcTypeId) {
            this.srcTypeId = srcTypeId;
        }

        public String getDeliveryDate() {
            return deliveryDate;
        }

        public void setDeliveryDate(String deliveryDate) {
            this.deliveryDate = deliveryDate;
        }

        public String getUpdateTime() {
            return updateTime;
        }

        public void setUpdateTime(String updateTime) {
            this.updateTime = updateTime;
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

        public String getRealDeliveryDate() {
            return realDeliveryDate;
        }

        public void setRealDeliveryDate(String realDeliveryDate) {
            this.realDeliveryDate = realDeliveryDate;
        }

        public String getIsKeyuser() {
            return isKeyuser;
        }

        public void setIsKeyuser(String isKeyuser) {
            this.isKeyuser = isKeyuser;
        }

        public String getCustTelephone() {
            return custTelephone;
        }

        public void setCustTelephone(String custTelephone) {
            this.custTelephone = custTelephone;
        }

        public String getCustMobile() {
            return custMobile;
        }

        public void setCustMobile(String custMobile) {
            this.custMobile = custMobile;
        }

        public String getLeadsId() {
            return leadsId;
        }

        public void setLeadsId(String leadsId) {
            this.leadsId = leadsId;
        }

        public String getOppId() {
            return oppId;
        }

        public void setOppId(String oppId) {
            this.oppId = oppId;
        }
    }
}
