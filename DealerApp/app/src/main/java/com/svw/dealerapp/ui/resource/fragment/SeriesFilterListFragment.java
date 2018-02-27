package com.svw.dealerapp.ui.resource.fragment;

import android.os.Bundle;

import com.svw.dealerapp.R;
import com.svw.dealerapp.mvpframe.listfragment.BaseListFragment;
import com.svw.dealerapp.ui.adapter.BaseRecyclerViewAdapter;
import com.svw.dealerapp.ui.resource.activity.SeriesFilterActivity;
import com.svw.dealerapp.ui.resource.adapter.SeriesFilterListAdapter;
import com.svw.dealerapp.ui.resource.entity.SeriesFilterEntity;
import com.svw.dealerapp.ui.resource.presenter.SeriesFilterListPresenter;
import com.svw.dealerapp.util.UserInfoUtils;

import java.util.List;

/**
 * Created by qinshi on 11/21/2017.
 */

public class SeriesFilterListFragment extends BaseListFragment<List<SeriesFilterEntity>, SeriesFilterEntity> {

    private SeriesFilterListAdapter adapter;
    private SeriesFilterListPresenter seriesFilterListPresenter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public BaseRecyclerViewAdapter getAdapter() {
        seriesFilterListPresenter = new SeriesFilterListPresenter();
        List<SeriesFilterEntity> dataList = seriesFilterListPresenter.getSeriesDataForDB();
        adapter = new SeriesFilterListAdapter(getActivity(), dataList);
        if(dataList.size() > 0){
            SeriesFilterEntity allEntity = new SeriesFilterEntity();
            allEntity.setAll(true);
            allEntity.setSeriesName(getResources().getString(R.string.resource_series_filter_all));

            List<String> defaultUserIdList = ((SeriesFilterActivity)getActivity()).getDefaultUserIdList();
            if(null != defaultUserIdList && defaultUserIdList.size() > 0) {
                for (int i = 0; i < dataList.size(); i++) {
                    if(defaultUserIdList.contains(dataList.get(i).getSeriesCode())) {
                        dataList.get(i).setSelect(true);
                        adapter.addSelectSeries(dataList.get(i));
                    }
                }
                if(adapter.getSelectSeries().size() == dataList.size()){
                    allEntity.setSelect(true);
                }
            }
            adapter.setAllEntity(allEntity);
            dataList.add(0, allEntity);
        }
        return adapter;
    }

    @Override
    public void initView() {
        super.initView();
        this.hideLoadMoreFooterView();
    }

    @Override
    public String[] getMoreParams() {
        return new String[]{UserInfoUtils.getOrgId()};
    }

    public List<SeriesFilterEntity> getSelectSales(){
        return adapter.getSelectSeries();
    }

    public boolean isSelectAll(){
        return adapter.isSelectAll();
    }

    public void clearSelectSeries(){
        adapter.clearSelectSeries();
    }


    public void refreshList(){
        adapter.notifyDataSetChanged();
    }
}
