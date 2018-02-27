package com.svw.dealerapp.ui.order.fragment;

import android.os.Bundle;
import android.view.View;

import com.svw.dealerapp.R;
import com.svw.dealerapp.entity.newcustomer.OpportunityDetailEntity;
import com.svw.dealerapp.entity.order.QueryOrderDetailResponseEntity;
import com.svw.dealerapp.ui.newcustomer.fragment.BaseCustomerFragment;
import com.svw.dealerapp.ui.widget.CustomItemViewForEditText;

import java.io.Serializable;

/**
 * 订单-潜客信息
 * Created by xupan on 28/07/2017.
 */
@Deprecated
public class OrderOppInfoFragment extends BaseCustomerFragment {
    private CustomItemViewForEditText mOppNameView, mMobileView;

    public static OrderOppInfoFragment getInstance(Serializable entity) {
        OrderOppInfoFragment fragment = new OrderOppInfoFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("entity", entity);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_order_opp_info;
    }

    @Override
    protected void initViews(View view) {
        mOppNameView = (CustomItemViewForEditText) view.findViewById(R.id.new_order_opp_name_view);
        mOppNameView.setMandatory(true);
        mOppNameView.setTitleText("姓名");
        mOppNameView.setHintTextForContentView("请输入姓名");
        mMobileView = (CustomItemViewForEditText) view.findViewById(R.id.new_order_mobile_view);
        mMobileView.setMandatory(true);
        mMobileView.setTitleText("联系电话");
        mMobileView.setHintTextForContentView("请输入联系电话");
        mMobileView.setShowBottomLine(false);
    }

    @Override
    protected void setListeners() {

    }

    @Override
    protected void renderView() {
        if (mEntity == null) {
            return;
        }
        mOppNameView.setEnabled(false);
        mMobileView.setEnabled(false);
        if (mEntity instanceof OpportunityDetailEntity) {
            OpportunityDetailEntity entity = (OpportunityDetailEntity) mEntity;
            mOppNameView.setData(entity.getCustName());
            mMobileView.setData(entity.getCustMobile());
        } else if (mEntity instanceof QueryOrderDetailResponseEntity) {
            QueryOrderDetailResponseEntity entity = (QueryOrderDetailResponseEntity) mEntity;
            mOppNameView.setData(entity.getCustName());
            mMobileView.setData(entity.getCustMobile());
        }
    }

//    @Override
//    public Object getParameters() {
//        mOppNameView.getInputData();
//        mMobileView.getInputData();
//        return super.getParameters();
//    }
}
