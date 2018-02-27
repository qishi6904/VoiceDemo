package com.svw.dealerapp.ui.mine.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;

import com.svw.dealerapp.entity.mine.ApproveCompleteEntity;
import com.svw.dealerapp.entity.mine.ApproveCompleteEntity.ApproveCompleteInfoEntity;
import com.svw.dealerapp.mvpframe.listfragment.BaseListFragment;
import com.svw.dealerapp.mvpframe.listfragment.ListFragmentPresenter;
import com.svw.dealerapp.ui.adapter.BaseRecyclerViewAdapter;
import com.svw.dealerapp.ui.mine.adapter.ApproveCompleteAdapter;
import com.svw.dealerapp.ui.mine.model.ApproveCompleteModel;
import com.svw.dealerapp.ui.mine.presenter.ApproveCompletePresenter;
import com.svw.dealerapp.ui.newcustomer.activity.CustomerDetailActivity;
import com.svw.dealerapp.util.JLog;
import com.svw.dealerapp.util.TalkingDataUtils;

/**
 * Created by qinshi on 6/1/2017.
 */

public class ApproveCompleteFragment extends BaseListFragment<ApproveCompleteEntity, ApproveCompleteInfoEntity> {

    public final static String FRESH_FILTER_STRING = "com.svw.dealerapp.approve.complete.fresh";
    private ApproveCompleteAdapter adapter;
    private FreshReceiver freshReceiver;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new ApproveCompletePresenter(this, new ApproveCompleteModel());

        IntentFilter filter = new IntentFilter(FRESH_FILTER_STRING);
        freshReceiver = new FreshReceiver();
        getActivity().registerReceiver(freshReceiver, filter);
    }

    @Override
    public BaseRecyclerViewAdapter getAdapter() {
        adapter = new ApproveCompleteAdapter(getActivity(), presenter.getDataList());
        adapter.setOnIconClickListener(new ApproveCompleteAdapter.OnIconClickListener() {
            @Override
            public void onItemClick(View view, ApproveCompleteInfoEntity entity, int position) {
                Intent intent = new Intent(getActivity(), CustomerDetailActivity.class);
                intent.putExtra("oppId", entity.getOppId());
                startActivity(intent);
            }
        });
        return adapter;
    }

    @Override
    public String getFilter() {
        return "1";
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(null != freshReceiver) {
            getActivity().unregisterReceiver(freshReceiver);
        }
    }

    @Override
    public void onShow() {
        super.onShow();
        TalkingDataUtils.onPageStart(getActivity(), "我的-审批-已审批");
        JLog.i("talkingDataFlag-show", "我的-审批-已审批");
    }

    @Override
    public void onHide() {
        super.onHide();
        TalkingDataUtils.onPageEnd(getActivity(), "我的-审批-已审批");
        JLog.i("talkingDataFlag-hide", "我的-审批-已审批");
    }

    private class FreshReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            presenter.getDataFromServer(getActivity(), "1", pageSize, "1", filterString, ListFragmentPresenter.REQUEST_BY_INIT, getMoreParams());
        }
    }
}
