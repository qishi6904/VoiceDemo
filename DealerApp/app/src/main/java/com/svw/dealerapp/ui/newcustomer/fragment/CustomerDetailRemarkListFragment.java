package com.svw.dealerapp.ui.newcustomer.fragment;

import android.os.Bundle;

import com.svw.dealerapp.R;
import com.svw.dealerapp.entity.newcustomer.OpportunityDetailEntity;
import com.svw.dealerapp.entity.newcustomer.RemarkEntity;
import com.svw.dealerapp.mvpframe.listfragment.BaseListFragment;
import com.svw.dealerapp.ui.adapter.BaseRecyclerViewAdapter;
import com.svw.dealerapp.ui.newcustomer.adapter.RemarkListAdapter;
import com.svw.dealerapp.ui.newcustomer.model.RemarkListModel;
import com.svw.dealerapp.ui.newcustomer.presenter.RemarkListPresenter;

/**
 * Created by qinshi on 7/14/2017.
 */

public class CustomerDetailRemarkListFragment extends BaseListFragment<RemarkEntity, RemarkEntity.RemarkEntityInfo>{

    private OpportunityDetailEntity entity;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        presenter = new RemarkListPresenter(this, new RemarkListModel());
    }

    @Override
    public void initView() {
        super.initView();
        rvRecyclerView.setBackgroundColor(getResources().getColor(R.color.page_bg));
    }

    @Override
    public BaseRecyclerViewAdapter getAdapter() {
        RemarkListAdapter adapter = new RemarkListAdapter(getActivity(), presenter.getDataList());

        return adapter;
    }

    @Override
    public void refresh() {
        super.refresh();
        hideFooter();
    }

    public void setEntity(OpportunityDetailEntity entity){
        this.entity = entity;
    }

    @Override
    public String[] getMoreParams() {
        if(null != entity){
            return new String[]{entity.getOppId()};
        }
        return super.getMoreParams();
    }
}
