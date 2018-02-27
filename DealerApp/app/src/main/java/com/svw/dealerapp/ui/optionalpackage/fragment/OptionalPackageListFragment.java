package com.svw.dealerapp.ui.optionalpackage.fragment;

import android.graphics.Color;
import android.os.Bundle;

import com.svw.dealerapp.entity.optionalpackage.OptionalPackageEntity;
import com.svw.dealerapp.mvpframe.listfragment.BaseListFragment;
import com.svw.dealerapp.ui.adapter.BaseRecyclerViewAdapter;
import com.svw.dealerapp.ui.optionalpackage.activity.OptionalPackageListActivity;
import com.svw.dealerapp.ui.optionalpackage.adapter.OptionalPackageListAdapter;
import com.svw.dealerapp.ui.optionalpackage.model.OptionalPackageListModel;
import com.svw.dealerapp.ui.optionalpackage.presenter.OptionalPackageListPresenter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by qinshi on 11/30/2017.
 */

public class OptionalPackageListFragment  extends BaseListFragment<OptionalPackageEntity, OptionalPackageEntity.OptionListBean> {

    private OptionalPackageListAdapter adapter;
    private OptionalPackageListActivity activity;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = (OptionalPackageListActivity) getActivity();
        presenter = new OptionalPackageListPresenter(this, new OptionalPackageListModel());
    }

    @Override
    public BaseRecyclerViewAdapter getAdapter() {
        rvRecyclerView.setBackgroundColor(Color.WHITE);
        adapter = new OptionalPackageListAdapter(getActivity(), presenter.getDataList());

        if(null != activity.getSelectData()){
            adapter.addDefaultSelectData(activity.getSelectData());
        }

        return adapter;
    }

    @Override
    public void initView() {
        super.initView();
    }

    @Override
    public void refresh() {
        super.refresh();
        this.hideLoadMoreFooterView();
    }

    @Override
    public String[] getMoreParams() {
        return ((OptionalPackageListActivity)getActivity()).getRequestParams();
    }

    /**
     * 获取选中的数据
     * @return
     */
    public ArrayList<OptionalPackageEntity.OptionListBean> getSelectData(){
        return adapter.getSelectData();
    }

    /**
     * 获取上次选中的数据
     * @return
     */
    public List<OptionalPackageEntity.OptionListBean> getDefaultSelectData(){
        return activity.getSelectData();
    }
}
