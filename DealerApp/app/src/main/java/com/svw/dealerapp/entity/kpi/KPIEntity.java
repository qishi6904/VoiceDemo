package com.svw.dealerapp.entity.kpi;

import java.util.List;

/**
 * Created by qinshi on 6/13/2017.
 */

public class KPIEntity {

    private String itemId;
    private String kpiType;
    private String customerNum;
    private String displayType;
    private List<KPIInfoEntity> itemInfo;

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public String getKpiType() {
        return kpiType;
    }

    public void setKpiType(String kpiType) {
        this.kpiType = kpiType;
    }

    public String getCustomerNum() {
        return customerNum;
    }

    public void setCustomerNum(String customerNum) {
        this.customerNum = customerNum;
    }

    public String getDisplayType() {
        return displayType;
    }

    public void setDisplayType(String displayType) {
        this.displayType = displayType;
    }

    public List<KPIInfoEntity> getItemInfo() {
        return itemInfo;
    }

    public void setItemInfo(List<KPIInfoEntity> itemInfo) {
        this.itemInfo = itemInfo;
    }

    public class KPIInfoEntity{

        /**
         * itemInfoId : 1000000000001
         * itemInfoName : sales1
         * itemInfoNum : 20
         * itemInfoIcon : http://confluence.svwsx.tech/display/BAC/Interface+Document
         */

        private String itemInfoId;
        private String itemInfoName;
        private String itemInfoNum;
        private String itemInfoIcon;

        public String getItemInfoId() {
            return itemInfoId;
        }

        public void setItemInfoId(String itemInfoId) {
            this.itemInfoId = itemInfoId;
        }

        public String getItemInfoName() {
            return itemInfoName;
        }

        public void setItemInfoName(String itemInfoName) {
            this.itemInfoName = itemInfoName;
        }

        public String getItemInfoNum() {
            return itemInfoNum;
        }

        public void setItemInfoNum(String itemInfoNum) {
            this.itemInfoNum = itemInfoNum;
        }

        public String getItemInfoIcon() {
            return itemInfoIcon;
        }

        public void setItemInfoIcon(String itemInfoIcon) {
            this.itemInfoIcon = itemInfoIcon;
        }
    }
}
