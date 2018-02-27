package com.svw.dealerapp.ui.order.fragment;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import com.svw.dealerapp.R;
import com.svw.dealerapp.entity.newcustomer.OpportunityDetailEntity;
import com.svw.dealerapp.entity.order.CreateOrderEntity;
import com.svw.dealerapp.entity.order.QueryOrderDetailResponseEntity;
import com.svw.dealerapp.global.NewCustomerConstants;
import com.svw.dealerapp.global.NewOrderConstants;
import com.svw.dealerapp.ui.newcustomer.fragment.BaseCustomerFragment;
import com.svw.dealerapp.ui.order.activity.OrderActivity;
import com.svw.dealerapp.ui.widget.CustomItemViewBase;
import com.svw.dealerapp.ui.widget.CustomItemViewForEditText;
import com.svw.dealerapp.ui.widget.CustomItemViewForOptionsPicker;
import com.svw.dealerapp.ui.widget.CustomItemViewForRadioButton;
import com.svw.dealerapp.util.CommonUtils;
import com.svw.dealerapp.util.StringUtils;
import com.svw.dealerapp.util.ToastUtils;

import java.io.Serializable;

/**
 * 订单-车主信息
 * Created by xupan on 31/07/2017.
 */
@Deprecated
public class OrderBuyerInfoFragment extends BaseCustomerFragment {
    private CustomItemViewForEditText mNameView, mMobileView, mAddressView, mEmailView, mIdView;
    private CustomItemViewForOptionsPicker mIdTypeView;
    private CustomItemViewForOptionsPicker mBuyerCategoryView;
    private CustomItemViewForRadioButton mGenderView;
    private CustomItemViewForOptionsPicker mIndustryView;
    private CustomItemViewForOptionsPicker mOccupationView;
    private CustomItemViewForOptionsPicker mCompanyPropertyView;

    public static OrderBuyerInfoFragment newInstance(Serializable entity) {
        OrderBuyerInfoFragment fragment = new OrderBuyerInfoFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("entity", entity);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_order_buyer_info;
    }

    @Override
    protected void initViews(View view) {
        mNameView = (CustomItemViewForEditText) view.findViewById(R.id.new_order_buyer_name_view);
        mNameView.setMandatory(true);
        mNameView.setTitleText("姓名");
        mNameView.setHintTextForContentView("请输入姓名");
        mGenderView = (CustomItemViewForRadioButton) view.findViewById(R.id.new_order_buyer_gender_view);
        mGenderView.setTitleText("性别");
        mMobileView = (CustomItemViewForEditText) view.findViewById(R.id.new_order_buyer_mobile_view);
        mMobileView.setMandatory(true);
        mMobileView.setTitleText("联系电话");
        mMobileView.setHintTextForContentView("请输入联系电话");
        mMobileView.limitContentToNumber();
        mMobileView.setMaxLengthForContentView(11);
        mAddressView = (CustomItemViewForEditText) view.findViewById(R.id.new_order_buyer_address_view);
        mAddressView.setFlag(true);
        mAddressView.setMandatory(true);
        mAddressView.setTitleText("联系地址");
        mAddressView.setHintTextForContentView("请输入详细地址");
        mEmailView = (CustomItemViewForEditText) view.findViewById(R.id.new_order_buyer_email_view);
        mEmailView.setMandatory(true);
        mEmailView.setTitleText("电子邮件");
        mEmailView.setHintTextForContentView("请输入电子邮件地址");
        mIdTypeView = (CustomItemViewForOptionsPicker) view.findViewById(R.id.new_order_buyer_id_type_view);
        mIdTypeView.setMandatory(true);
        mIdTypeView.setTitleText("证件类型");
        mIdTypeView.setHintTextForContentView("请选择证件类型");
        mIdView = (CustomItemViewForEditText) view.findViewById(R.id.new_order_buyer_id_view);
        mIdView.setTitleText("证件号码");
        mIdView.setHintTextForContentView("请输入证件号码");
        mIdView.setMaxLengthForContentView(18);
        mIdView.setMandatory(true);
        mBuyerCategoryView = (CustomItemViewForOptionsPicker) view.findViewById(R.id.new_order_buyer_category_view);
        mBuyerCategoryView.setTitleText("客户类别");
        mBuyerCategoryView.setHintTextForContentView("请选择客户类别");
        mIndustryView = (CustomItemViewForOptionsPicker) view.findViewById(R.id.new_order_buyer_industry_view);
        mIndustryView.setTitleText("从事行业");
        mIndustryView.setHintTextForContentView("请选择从事行业");
        mOccupationView = (CustomItemViewForOptionsPicker) view.findViewById(R.id.new_order_buyer_occupation_view);
        mOccupationView.setTitleText("职务");
        mOccupationView.setHintTextForContentView("请选择职务");
        mCompanyPropertyView = (CustomItemViewForOptionsPicker) view.findViewById(R.id.new_order_buyer_company_property_view);
        mCompanyPropertyView.setTitleText("公司性质");
        mCompanyPropertyView.setHintTextForContentView("请选择公司性质");
        mCompanyPropertyView.setShowBottomLine(false);
    }

    protected void initBaseViews() {
        mAllBaseView = new CustomItemViewBase[]{mNameView, mMobileView, mAddressView, mEmailView, mIdView,
                mIdTypeView, mBuyerCategoryView, mGenderView, mIndustryView, mOccupationView, mCompanyPropertyView};
    }

    @Override
    protected void onDataChanged(Object object) {
        super.onDataChanged(object);
        ((OrderActivity) getActivity()).checkFragmentsValidation();
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
            mIdTypeView.setData("11010");
        } else if (mEntity instanceof QueryOrderDetailResponseEntity) {
            QueryOrderDetailResponseEntity entity = (QueryOrderDetailResponseEntity) mEntity;
            enableBaseViews(entity.isBuyerInfoEditable());
            mNameView.setData(entity.getCustName());
            mGenderView.setData(entity.getCustGender());
            mMobileView.setData(entity.getCustMobile());
            mAddressView.setData(entity.getCustAddress());
            mEmailView.setData(entity.getCustEmail());
            mIdTypeView.setData(entity.getCertType());
            mIdView.setData(entity.getCertNum());
            mBuyerCategoryView.setData(entity.getCustType());
            mIndustryView.setData(entity.getCustIndustry());
            mOccupationView.setData(entity.getCustDuty());
            mCompanyPropertyView.setData(entity.getCorpNature());
        }
    }

    @Override
    protected void initOptions() {
        super.initOptions();
        CommonUtils.initOptionsView(NewCustomerConstants.genderMap, mGenderView);
        CommonUtils.initOptionsView(NewOrderConstants.idTypeMap, mIdTypeView);
        CommonUtils.initOptionsView(NewOrderConstants.customerTypeMap, mBuyerCategoryView);
        CommonUtils.initOptionsView(NewOrderConstants.industryMap, mIndustryView);
        CommonUtils.initOptionsView(NewOrderConstants.occupationMap, mOccupationView);
        CommonUtils.initOptionsView(NewOrderConstants.companyPropertyMap, mCompanyPropertyView);
    }

    @Override
    public CreateOrderEntity getParameters() {
        CreateOrderEntity entity = new CreateOrderEntity();
        entity.setCustName(mNameView.getInputData());
        entity.setCustGender(mGenderView.getInputData());
        entity.setCustMobile(mMobileView.getInputData());
        entity.setCustAddress(mAddressView.getInputData());
        entity.setCustEmail(mEmailView.getInputData());
        entity.setCertType(mIdTypeView.getInputData());
        entity.setCertNum(mIdView.getInputData());
        entity.setCustType(mBuyerCategoryView.getInputData());
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
        String idType = mIdTypeView.getInputData();
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
