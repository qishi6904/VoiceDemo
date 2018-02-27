package com.svw.dealerapp.ui.resource.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import com.svw.dealerapp.R;
import com.svw.dealerapp.entity.resource.DealCustomerEntity;
import com.svw.dealerapp.mvpframe.listfragment.BaseListFragment;
import com.svw.dealerapp.mvpframe.listfragment.ListFragmentPresenter;
import com.svw.dealerapp.ui.adapter.BaseRecyclerViewAdapter;
import com.svw.dealerapp.ui.main.activity.RdMainActivity;
import com.svw.dealerapp.ui.newcustomer.activity.CustomerDetailActivity;
import com.svw.dealerapp.ui.resource.adapter.DealCustomerAdapter;
import com.svw.dealerapp.ui.resource.adapter.RdDealAdapter;
import com.svw.dealerapp.ui.resource.adapter.RdOrderAdapter;
import com.svw.dealerapp.ui.resource.contract.DealCustomerContract;
import com.svw.dealerapp.ui.resource.model.DealCustomerModel;
import com.svw.dealerapp.ui.resource.presenter.DealCustomerPresenter;
import com.svw.dealerapp.util.JLog;
import com.svw.dealerapp.util.TalkingDataUtils;
import com.svw.dealerapp.util.ToastUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * Created by qinshi on 8/14/2017.
 */

public class DealCustomerFragment extends BaseListFragment<DealCustomerEntity, DealCustomerEntity.DealCustomerInfoEntity>
        implements DealCustomerContract.View{

    public final static String FRESH_FILTER_STRING = "com.svw.dealerapp.deal.customer.fresh";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        presenter = new DealCustomerPresenter(this, new DealCustomerModel());

        registerFreshReceiver(FRESH_FILTER_STRING);
    }

    @Override
    public BaseRecyclerViewAdapter getAdapter() {
        RdDealAdapter adapter = new RdDealAdapter(getActivity(), presenter.getDataList(),
                presenter.getShowMonthEntities());

        setShowHorizontalTag(true);

        return adapter;
    }

    /**
     * 从筛选Activity返回调用
     * @param filter
     */
    public void requestDateAfterTakeFilter(String filter){
        filterString = filter;
        showLoadingLayout();
        presenter.getDataFromServer(getActivity(), "1", pageSize, "1", filter, ListFragmentPresenter.REQUEST_BY_INIT);
    }

//    @Override
//    public String getFilter() {
//        String filter = "{\"followupResult\":\"15550\"}";
//        try {
//            return URLEncoder.encode(filter, "utf-8");
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//        }
//        return "";
//    }

    @Override
    public void onShow() {
        super.onShow();
        TalkingDataUtils.onPageStart(getActivity(), "资源-成交客户");
        JLog.i("talkingDataFlag-show", "资源-成交客户");
    }

    @Override
    public void onHide() {
        super.onHide();
        TalkingDataUtils.onPageEnd(getActivity(), "资源-成交客户");
        JLog.i("talkingDataFlag-hide", "资源-成交客户");
    }

    @Override
    public void setTabTipNumber(String number) {
        RdMainActivity mainActivity = (RdMainActivity)getActivity();
        (mainActivity.getResourceFragment()).setTabTipNumber(2, number);
    }
}
