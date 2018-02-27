package com.svw.dealerapp.ui.newcustomer.fragment;

import android.os.Bundle;
import android.view.View;

import com.svw.dealerapp.R;
import com.svw.dealerapp.global.NewCustomerConstants;
import com.svw.dealerapp.ui.adapter.RdRecyclerViewAdapter;
import com.svw.dealerapp.ui.widget.RdCustomItemViewForEditText;
import com.svw.dealerapp.ui.widget.RdCustomItemViewForEditTextWithMic;
import com.svw.dealerapp.ui.widget.RdCustomItemViewForRadioButton;
import com.svw.dealerapp.ui.widget.RdCustomItemViewForRecycleView;
import com.svw.dealerapp.util.CommonUtils;

import java.io.Serializable;

/**
 * 基本信息
 * Created by xupan on 23/01/2018.
 */

public class RdNewCustomerBasicInfoFragment extends RdBaseCustomerFragment {

    private RdCustomItemViewForEditText mNameView, mMobileView;
    private RdCustomItemViewForRadioButton mGenderView;
    private RdCustomItemViewForRecycleView mOppLevelView;
    private RdCustomItemViewForEditTextWithMic mReasonView;

    public static RdNewCustomerBasicInfoFragment newInstance(Serializable entity) {
        RdNewCustomerBasicInfoFragment fragment = new RdNewCustomerBasicInfoFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("entity", entity);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.rd_fragment_new_customer_basic_info;
    }

    @Override
    protected void initViews(View view) {
        mNameView = (RdCustomItemViewForEditText) view.findViewById(R.id.rd_basic_info_name_view);
        mNameView.setMandatory(true);
        mNameView.setTitleText(R.string.new_customer_name);
        mNameView.setHintTextForContentView(R.string.new_customer_name_hint);
        mGenderView = (RdCustomItemViewForRadioButton) view.findViewById(R.id.rd_basic_info_gender_view);
        mGenderView.setTitleText(R.string.new_customer_gender);
        mMobileView = (RdCustomItemViewForEditText) view.findViewById(R.id.rd_basic_info_mobile_view);
        mMobileView.setMandatory(true);
        mMobileView.setTitleText(R.string.new_customer_mobile);
        mMobileView.setHintTextForContentView(R.string.new_customer_mobile_hint);
        mMobileView.limitContentToNumber();
        mMobileView.setMaxLengthForContentView(11);
        mMobileView.setShowBottomLine(false);
        mOppLevelView = (RdCustomItemViewForRecycleView) view.findViewById(R.id.rd_basic_info_opp_level_view);
        mOppLevelView.setMandatory(true);
        mOppLevelView.setTitleText(R.string.new_customer_opp_level_header);
        mOppLevelView.setHintTextForContentView(R.string.new_customer_opp_level_hint);
        mOppLevelView.initAdapter(NewCustomerConstants.levelMap, RdRecyclerViewAdapter.MODE_SINGLE);
        mReasonView = (RdCustomItemViewForEditTextWithMic) view.findViewById(R.id.rd_basic_info_followup_reason);
        mReasonView.setTitleText(R.string.new_customer_opp_level_followup_reason_title);
        mReasonView.setHint(R.string.new_customer_opp_level_followup_reason_hint);
        mReasonView.setMaxTextNum(100);
        mReasonView.initMicEditText(getActivity(), this);
    }

    @Override
    protected void initOptions() {
        super.initOptions();
        CommonUtils.rdInitOptionsView(NewCustomerConstants.genderMap, mGenderView);
    }

    @Override
    protected void renderView() {

    }
}
