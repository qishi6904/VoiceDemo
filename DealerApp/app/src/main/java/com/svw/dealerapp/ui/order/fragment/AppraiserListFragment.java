package com.svw.dealerapp.ui.order.fragment;

import android.os.Bundle;
import android.text.TextUtils;

import com.svw.dealerapp.entity.order.AppraiserEntity;
import com.svw.dealerapp.mvpframe.listfragment.BaseListFragment;
import com.svw.dealerapp.ui.adapter.BaseRecyclerViewAdapter;
import com.svw.dealerapp.ui.order.activity.AppraiserListActivity;
import com.svw.dealerapp.ui.order.adapter.AppraiserListAdapter;
import com.svw.dealerapp.ui.order.model.AppraiserModel;
import com.svw.dealerapp.ui.order.presenter.AppraiserPresenter;

/**
 * Created by qinshi on 11/22/2017.
 */

public class AppraiserListFragment extends BaseListFragment<AppraiserEntity, AppraiserEntity.AppraiserInfoEntity> {

    private AppraiserListAdapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new AppraiserPresenter(this, new AppraiserModel());
    }

    @Override
    public BaseRecyclerViewAdapter getAdapter() {
        adapter = new AppraiserListAdapter(getContext(), presenter.getDataList());
        return adapter;
    }

    @Override
    public void refresh() {
        String defaultAppraiserId = ((AppraiserListActivity) getActivity()).getDefaultAppraiserId();
        if(!TextUtils.isEmpty(defaultAppraiserId)){
            for(int i = 0; i < presenter.getDataList().size(); i++) {
                if(defaultAppraiserId.equals(presenter.getDataList().get(i).getAppraiserId())){
                    presenter.getDataList().get(i).setSelect(true);
                    break;
                }
            }
        }
        super.refresh();
        this.hideLoadMoreFooterView();
    }

    public AppraiserEntity.AppraiserInfoEntity getAppraiser(){
        return adapter.getAppraiser();
    }
}
