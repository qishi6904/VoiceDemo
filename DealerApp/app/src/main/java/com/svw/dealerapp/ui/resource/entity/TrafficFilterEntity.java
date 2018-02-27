package com.svw.dealerapp.ui.resource.entity;

/**
 * Created by qinshi on 6/21/2017.
 */

public class TrafficFilterEntity {

    private String channelId;
    private String leadsStatusId;
    private String srcTypeId = "11010";
    private String salesConsultant;
    private String CreateTime;

    public String getChannelId() {
        return channelId;
    }

    public void setChannelId(String channelId) {
        this.channelId = channelId;
    }

    public String getLeadsStatusId() {
        return leadsStatusId;
    }

    public void setLeadsStatusId(String leadsStatusId) {
        this.leadsStatusId = leadsStatusId;
    }

    public String getSrcTypeId() {
        return srcTypeId;
    }

    public void setSrcTypeId(String srcTypeId) {
        this.srcTypeId = srcTypeId;
    }

    public String getSalesConsultant() {
        return salesConsultant;
    }

    public void setSalesConsultant(String salesConsultant) {
        this.salesConsultant = salesConsultant;
    }

    public String getCreateTime() {
        return CreateTime;
    }

    public void setCreateTime(String createTime) {
        CreateTime = createTime;
    }
}
