package com.svw.dealerapp.ui.newcustomer.fragment;

import android.os.Bundle;
import android.view.View;

import com.svw.dealerapp.R;
import com.svw.dealerapp.global.NewCustomerConstants;
import com.svw.dealerapp.ui.adapter.RdRecyclerViewAdapter;
import com.svw.dealerapp.ui.widget.RdCustomItemViewForButton;
import com.svw.dealerapp.ui.widget.RdCustomItemViewForCheckbox;
import com.svw.dealerapp.ui.widget.RdCustomItemViewForOptionsPicker;
import com.svw.dealerapp.ui.widget.RdCustomItemViewForRecycleView;

import java.io.Serializable;

/**
 * 建卡-潜客信息补充
 * Created by xupan on 26/01/2018.
 */

public class RdNewCustomerSupplementInfoFragment extends RdBaseCustomerFragment {

    private RdCustomItemViewForButton mCheckView;
    private RdCustomItemViewForCheckbox mWeChatAddedView;
    private RdCustomItemViewForRecycleView mPropertyView;
    private RdCustomItemViewForOptionsPicker mEvaluator;

    public static RdNewCustomerSupplementInfoFragment newInstance(Serializable entity) {
        RdNewCustomerSupplementInfoFragment fragment = new RdNewCustomerSupplementInfoFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("entity", entity);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.rd_fragment_new_customer_supplement_info;
    }

    @Override
    protected void initViews(View view) {
        mCheckView = (RdCustomItemViewForButton) view.findViewById(R.id.rd_supplement_info_check_view);
        mCheckView.setTitleText("会员电话验证");
        mWeChatAddedView = (RdCustomItemViewForCheckbox) view.findViewById(R.id.rd_supplement_info_wechat_added_view);
        mWeChatAddedView.setTitleText(R.string.new_customer_intention_other_wechat);
        mPropertyView = (RdCustomItemViewForRecycleView) view.findViewById(R.id.rd_supplement_info_purchase_property_view);
        mPropertyView.setTitleText(R.string.new_customer_purpose);
        mPropertyView.setHintTextForContentView(R.string.new_customer_purpose_hint);
        mPropertyView.initAdapter(NewCustomerConstants.propertyMap, RdRecyclerViewAdapter.MODE_SINGLE);
        mEvaluator.setTitleText("评估师");
        mEvaluator.setHintTextForContentView("请选择评估师");
    }

    @Override
    protected void renderView() {

    }
}
