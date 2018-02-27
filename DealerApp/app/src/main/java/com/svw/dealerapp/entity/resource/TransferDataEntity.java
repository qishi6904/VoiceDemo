package com.svw.dealerapp.entity.resource;

import java.util.List;

/**
 * Created by qinshi on 1/30/2018.
 */

public class TransferDataEntity {
    private List<SMYCTransferSalesEntity> data;
    private boolean empty;
    private String total;

    public List<SMYCTransferSalesEntity> getData() {
        return data;
    }

    public void setData(List<SMYCTransferSalesEntity> data) {
        this.data = data;
    }

    public boolean getEmpty() {
        return empty;
    }

    public void setEmpty(boolean empty) {
        this.empty = empty;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }
}
