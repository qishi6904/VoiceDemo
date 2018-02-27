package com.svw.dealerapp.ui.resource.presenter;

import com.svw.dealerapp.global.NewCustomerConstants;
import com.svw.dealerapp.ui.resource.entity.SeriesFilterEntity;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by qinshi on 11/21/2017.
 */

public class SeriesFilterListPresenter {

    public List<SeriesFilterEntity> getSeriesDataForDB()  {
        Map<String, String> dataMap= NewCustomerConstants.carSeriesMap;
        Set<Map.Entry<String, String>> set = dataMap.entrySet();
        Iterator<Map.Entry<String, String>> iterator = set.iterator();
        List<SeriesFilterEntity> dataList = new ArrayList<>();
        while (iterator.hasNext()) {
            Map.Entry<String, String> entry = iterator.next();
            dataList.add(new SeriesFilterEntity(entry.getKey(), entry.getValue()));
        }
        return dataList;
    }

}
