package com.svw.dealerapp.ui.order.fragment;

import android.os.Bundle;
import android.view.View;

import com.svw.dealerapp.R;
import com.svw.dealerapp.entity.newcustomer.OpportunityDetailEntity;
import com.svw.dealerapp.entity.order.CreateOrderEntity;
import com.svw.dealerapp.entity.order.QueryOrderDetailResponseEntity;
import com.svw.dealerapp.global.NewCustomerConstants;
import com.svw.dealerapp.global.NewOrderConstants;
import com.svw.dealerapp.ui.adapter.RdRecyclerViewAdapter;
import com.svw.dealerapp.ui.newcustomer.fragment.RdBaseCustomerFragment;
import com.svw.dealerapp.ui.order.activity.RdOrderActivity;
import com.svw.dealerapp.ui.widget.RdCustomItemViewBase;
import com.svw.dealerapp.ui.widget.RdCustomItemViewForEditText;
import com.svw.dealerapp.ui.widget.RdCustomItemViewForOptionsPicker;
import com.svw.dealerapp.ui.widget.RdCustomItemViewForRadioButton;
import com.svw.dealerapp.ui.widget.RdCustomItemViewForRecycleView;
import com.svw.dealerapp.util.CommonUtils;
import com.svw.dealerapp.util.StringUtils;
import com.svw.dealerapp.util.ToastUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 订单-车主信息
 * Created by xupan on 31/07/2017.
 */

public class RdOrderBuyerInfoFragment extends RdBaseCustomerFragment {
    private RdCustomItemViewForEditText mNameView, mMobileView, mAddressView, mEmailView, mIdView;
    private RdCustomItemViewForRecycleView mIdTypeView;
    private RdCustomItemViewForRecycleView mBuyerCategoryView;
    private RdCustomItemViewForRadioButton mGenderView;
    private RdCustomItemViewForOptionsPicker mIndustryView;
    private RdCustomItemViewForOptionsPicker mOccupationView;
    private RdCustomItemViewForOptionsPicker mCompanyPropertyView;

    public static RdOrderBuyerInfoFragment newInstance(Serializable entity) {
        RdOrderBuyerInfoFragment fragment = new RdOrderBuyerInfoFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("entity", entity);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.rd_fragment_order_buyer_info;
    }

    @Override
    protected void initViews(View view) {
        mNameView = (RdCustomItemViewForEditText) view.findViewById(R.id.new_order_buyer_name_view);
        mNameView.setMandatory(true);
        mNameView.setTitleText(getResources().getString(R.string.order_create_owner_name));
        mNameView.setHintTextForContentView(getResources().getString(R.string.order_create_owner_name_hint));

        mMobileView = (RdCustomItemViewForEditText) view.findViewById(R.id.new_order_buyer_mobile_view);
        mMobileView.setMandatory(true);
        mMobileView.setTitleText(getResources().getString(R.string.order_create_owner_mobile));
        mMobileView.setHintTextForContentView(getResources().getString(R.string.order_create_owner_mobile_hint));
        mMobileView.limitContentToNumber();
        mMobileView.setMaxLengthForContentView(11);

        mAddressView = (RdCustomItemViewForEditText) view.findViewById(R.id.new_order_buyer_address_view);
        mAddressView.setFlag(true);
        mAddressView.setMandatory(true);
        mAddressView.setTitleText(getResources().getString(R.string.order_create_owner_address));
        mAddressView.setHintTextForContentView(getResources().getString(R.string.order_create_owner_address_hint));

        mEmailView = (RdCustomItemViewForEditText) view.findViewById(R.id.new_order_buyer_email_view);
        mEmailView.setMandatory(true);
        mEmailView.setTitleText(getResources().getString(R.string.order_create_owner_email));
        mEmailView.setHintTextForContentView(getResources().getString(R.string.order_create_owner_email_hint));

        mIdTypeView = (RdCustomItemViewForRecycleView) view.findViewById(R.id.new_order_buyer_id_type_view);
        mIdTypeView.initAdapter(NewOrderConstants.idTypeMap, RdRecyclerViewAdapter.MODE_SINGLE);
        mIdTypeView.setMandatory(true);
        mIdTypeView.setTitleText(getResources().getString(R.string.order_create_owner_certificate_type));
        mIdTypeView.setHintTextForContentView(getResources().getString(R.string.order_create_owner_certificate_type_hint));
        List<String> idTypeDefaultSelect = new ArrayList<>();
        idTypeDefaultSelect.add("11010");
        mIdTypeView.setData(idTypeDefaultSelect);

        mIdView = (RdCustomItemViewForEditText) view.findViewById(R.id.new_order_buyer_id_view);
        mIdView.setTitleText(getResources().getString(R.string.order_create_owner_certificate_id));
        mIdView.setHintTextForContentView(getResources().getString(R.string.order_create_owner_certificate_id_hint));
        mIdView.setMaxLengthForContentView(18);
        mIdView.setMandatory(true);

        mGenderView = (RdCustomItemViewForRadioButton) view.findViewById(R.id.new_order_buyer_gender_view);
        mGenderView.setTitleText(getResources().getString(R.string.order_create_owner_gender));

        mBuyerCategoryView = (RdCustomItemViewForRecycleView) view.findViewById(R.id.new_order_buyer_category_view);
        mBuyerCategoryView.initAdapter(NewOrderConstants.customerTypeMap, RdRecyclerViewAdapter.MODE_SINGLE);
        mBuyerCategoryView.setTitleText(getResources().getString(R.string.order_create_owner_customer_type));
        mBuyerCategoryView.setHintTextForContentView(getResources().getString(R.string.order_create_owner_customer_type_hint));
        List<String> buyerCategoryDefaultSelect = new ArrayList<>();
        buyerCategoryDefaultSelect.add("10510");
        mBuyerCategoryView.setData(buyerCategoryDefaultSelect);

        mIndustryView = (RdCustomItemViewForOptionsPicker) view.findViewById(R.id.new_order_buyer_industry_view);
        mIndustryView.setTitleText(getResources().getString(R.string.order_create_owner_industry));
        mIndustryView.setHintTextForContentView(getResources().getString(R.string.order_create_owner_industry_hint));

        mOccupationView = (RdCustomItemViewForOptionsPicker) view.findViewById(R.id.new_order_buyer_occupation_view);
        mOccupationView.setTitleText(getResources().getString(R.string.order_create_owner_jop));
        mOccupationView.setHintTextForContentView(getResources().getString(R.string.order_create_owner_jop_hint));

        mCompanyPropertyView = (RdCustomItemViewForOptionsPicker) view.findViewById(R.id.new_order_buyer_company_property_view);
        mCompanyPropertyView.setTitleText(getResources().getString(R.string.order_create_owner_company_type));
        mCompanyPropertyView.setHintTextForContentView(getResources().getString(R.string.order_create_owner_company_type_hint));
        mCompanyPropertyView.setShowBottomLine(false);
    }

    protected void initBaseViews() {
        mAllBaseView = new RdCustomItemViewBase[]{mNameView, mMobileView, mAddressView, mEmailView, mIdView,
                mIdTypeView, mBuyerCategoryView, mGenderView, mIndustryView, mOccupationView, mCompanyPropertyView};
    }

    @Override
    protected void onDataChanged(Object object) {
        super.onDataChanged(object);
        ((RdOrderActivity) getActivity()).checkFragmentsValidation();
    }

    @Override
    protected void renderView() {
        if (mEntity == null) {
            return;
        }
        if (mEntity instanceof OpportunityDetailEntity) {
            OpportunityDetailEntity entity = (OpportunityDetailEntity) mEntity;
            mNameView.setData(entity.getCustName());
            mGenderView.setData(entity.getCustGender());
            mMobileView.setData(entity.getCustMobile());
        } else if (mEntity instanceof QueryOrderDetailResponseEntity) {
            QueryOrderDetailResponseEntity entity = (QueryOrderDetailResponseEntity) mEntity;
            enableBaseViews(entity.isBuyerInfoEditable());
            mNameView.setData(entity.getCustName());
            mGenderView.setData(entity.getCustGender());
            mMobileView.setData(entity.getCustMobile());
            mAddressView.setData(entity.getCustAddress());
            mEmailView.setData(entity.getCustEmail());
            mIdView.setData(entity.getCertNum());
            mIndustryView.setData(entity.getCustIndustry());
            mOccupationView.setData(entity.getCustDuty());
            mCompanyPropertyView.setData(entity.getCorpNature());
        }
    }

    @Override
    protected void initOptions() {
        super.initOptions();
        CommonUtils.rdInitOptionsView(NewCustomerConstants.genderMap, mGenderView);
        CommonUtils.rdInitOptionsView(NewOrderConstants.industryMap, mIndustryView);
        CommonUtils.rdInitOptionsView(NewOrderConstants.occupationMap, mOccupationView);
        CommonUtils.rdInitOptionsView(NewOrderConstants.companyPropertyMap, mCompanyPropertyView);
    }

    @Override
    public CreateOrderEntity getParameters() {
        CreateOrderEntity entity = new CreateOrderEntity();
        entity.setCustName(mNameView.getInputData());
        entity.setCustGender(mGenderView.getInputData());
        entity.setCustMobile(mMobileView.getInputData());
        entity.setCustAddress(mAddressView.getInputData());
        entity.setCustEmail(mEmailView.getInputData());
        entity.setCertType(mIdTypeView.getInputData().get(0));
        entity.setCertNum(mIdView.getInputData());
        entity.setCustIndustry(mIndustryView.getInputData());
        entity.setCustDuty(mOccupationView.getInputData());
        entity.setCorpNature(mCompanyPropertyView.getInputData());
        return entity;
    }

    @Override
    public boolean checkDataCorrectness() {
        String mobile = mMobileView.getInputData();
        if (!StringUtils.isValidMobile(mobile)) {
            ToastUtils.showToast("车主信息的联系电话格式有误");
            return false;
        }
        String email = mEmailView.getInputData();
        if (!StringUtils.isValidEmail(email)) {
            ToastUtils.showToast("车主信息的电子邮件格式有误");
            return false;
        }
        String idType = mIdTypeView.getInputData().get(0);
        if ("11010".equals(idType)) {
            String id = mIdView.getInputData();
            if (!StringUtils.isValidID(id)) {
                ToastUtils.showToast("车主信息的身份证号码格式有误");
                return false;
            }
        }
        return true;
    }
}
