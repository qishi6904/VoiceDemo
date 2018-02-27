package com.svw.dealerapp.ui.resource.fragment;

import android.os.Bundle;

import com.svw.dealerapp.R;
import com.svw.dealerapp.entity.resource.SMYCTransferSalesEntity;
import com.svw.dealerapp.entity.resource.TransferDataEntity;
import com.svw.dealerapp.mvpframe.listfragment.BaseListFragment;
import com.svw.dealerapp.ui.adapter.BaseRecyclerViewAdapter;
import com.svw.dealerapp.ui.resource.activity.SalesFilterActivity;
import com.svw.dealerapp.ui.resource.adapter.SalesFilterListAdapter;
import com.svw.dealerapp.ui.resource.model.SalesFilterListModel;
import com.svw.dealerapp.ui.resource.presenter.SalesFilterListPresenter;
import com.svw.dealerapp.util.UserInfoUtils;

import java.util.List;

/**
 * Created by qinshi on 11/21/2017.
 */

public class SalesFilterListFragment extends BaseListFragment<TransferDataEntity, SMYCTransferSalesEntity> {

    private SalesFilterListAdapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        smPresenter = new SalesFilterListPresenter(this, new SalesFilterListModel());
    }

    @Override
    public BaseRecyclerViewAdapter getAdapter() {
        adapter = new SalesFilterListAdapter(getActivity(), smPresenter.getDataList());
        return adapter;
    }

    @Override
    public void initView() {
        super.initView();
        rvRecyclerView.setLoadMoreEnabled(false);
    }

    @Override
    public void refresh() {
        SMYCTransferSalesEntity allEntity = new SMYCTransferSalesEntity();
        allEntity.setAll(true);
        allEntity.setName(getResources().getString(R.string.resource_sales_filter_all));
        allEntity.setDisplayName(getResources().getString(R.string.resource_sales_filter_all));

        List<String> defaultUserIdList = ((SalesFilterActivity)getActivity()).getDefaultUserIdList();
        if(null != defaultUserIdList && defaultUserIdList.size() > 0) {
            for (int i = 0; i < smPresenter.getDataList().size(); i++) {
                if(defaultUserIdList.contains(smPresenter.getDataList().get(i).getPhone())) {
                    smPresenter.getDataList().get(i).setSelect(true);
                    adapter.addSelectSales(smPresenter.getDataList().get(i));
                }
            }
            if(adapter.getSelectSales().size() == smPresenter.getDataList().size()){
                allEntity.setSelect(true);
            }
        }
        adapter.setAllEntity(allEntity);
        smPresenter.getDataList().add(0, allEntity);
        super.refresh();
        this.hideLoadMoreFooterView();
    }

    @Override
    public boolean isUseSMToGetListData() {
        return true;
    }

    @Override
    public String[] getMoreParams() {
        return new String[]{UserInfoUtils.getOrgId()};
    }

    public List<SMYCTransferSalesEntity> getSelectSales(){
        return adapter.getSelectSales();
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
