package com.svw.dealerapp.ui.newcustomer.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.bigkoo.pickerview.OptionsPickerView;
import com.svw.dealerapp.R;
import com.svw.dealerapp.entity.newcustomer.CarTypesEntity;
import com.svw.dealerapp.entity.newcustomer.OpportunityDetailEntity;
import com.svw.dealerapp.entity.newcustomer.OpportunitySubmitReqEntity;
import com.svw.dealerapp.entity.order.AppraiserEntity;
import com.svw.dealerapp.global.NewCustomerConstants;
import com.svw.dealerapp.ui.newcustomer.activity.CustomerDetailItemActivity;
import com.svw.dealerapp.ui.newcustomer.activity.NewCustomerStepOneActivity;
import com.svw.dealerapp.ui.newcustomer.activity.NewCustomerStepOneUnitedActivity;
import com.svw.dealerapp.ui.order.activity.AppraiserListActivity;
import com.svw.dealerapp.ui.widget.CustomItemViewForOptionsPicker;
import com.svw.dealerapp.ui.widget.InterceptTouchCoverView;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import static android.app.Activity.RESULT_OK;

/**
 * 产品需求描述 (购买区分、购买性质、意向车型)
 * Created by xupan on 08/06/2017.
 */

public class NewCustomerRequirementsFragment extends BaseCustomerFragment implements View.OnClickListener {

    private static final String TAG = "NewCustomerRequirementsFragment";
    private boolean mHideCarTypeRow;
    private TableRow mCarTypeTr;
    private TableRow mPurposeTr, mPropertyTr;
    private TextView mPurposeTv, mPropertyTv;
    private OptionsPickerView mPurposePicker, mPropertyPicker;

    private List<String> mPurposeCodeList, mPropertyCodeList;
    private List<String> mPurposeValueList, mPropertyValueList;

    private String mCurrentPurpose, mCurrentProperty;
    private EditText mCarTypeEt;
    private TextView mCarTypeTv;
    private View mCarTypeLine;
    private RelativeLayout rlRootView;
    private InterceptTouchCoverView itvCoverView;
    private boolean hasCheckCoverView = false;

    private CustomItemViewForOptionsPicker mEvaluator;
    private static final int CODE_EVALUATOR = 1000;

    public static NewCustomerRequirementsFragment newInstance(OpportunityDetailEntity entity) {
        NewCustomerRequirementsFragment fragment = new NewCustomerRequirementsFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("entity", entity);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.layout_new_customer_requirement_description;
    }

    @Override
    protected void onDataChanged(Object object) {
        super.onDataChanged(object);
        if (getActivity() instanceof NewCustomerStepOneUnitedActivity) {
            ((NewCustomerStepOneUnitedActivity) getActivity()).checkIfNeedToEnableSubmit();
        }
    }

    protected void initViews(View view) {
        mCarTypeTr = (TableRow) view.findViewById(R.id.new_yellow_card_requirement_car_type_tr);
        mCarTypeLine = view.findViewById(R.id.new_yellow_card_requirement_car_type_line);
        mPurposeTr = (TableRow) view.findViewById(R.id.new_yellow_card_requirement_purpose_tr);
        mPropertyTr = (TableRow) view.findViewById(R.id.new_yellow_card_requirement_property_tr);
        mPurposeTv = (TextView) view.findViewById(R.id.new_yellow_card_requirement_purpose_tv);
        mPropertyTv = (TextView) view.findViewById(R.id.new_yellow_card_requirement_property_tv);
        mCarTypeEt = (EditText) view.findViewById(R.id.new_customer_step_1_car_type_et);
        mCarTypeTv = (TextView) view.findViewById(R.id.new_customer_step_1_car_type_tv);
        mEvaluator = (CustomItemViewForOptionsPicker) view.findViewById(R.id.new_yellow_card_requirement_evaluator);
        mEvaluator.setMandatory(true);
        mEvaluator.setTitleText("评估师");
        mEvaluator.setHintTextForContentView("请选择评估师");
        rlRootView = (RelativeLayout) view.findViewById(R.id.rl_root_view);
        itvCoverView = (InterceptTouchCoverView) view.findViewById(R.id.itv_cover_view);
        changeStyle();
        mCarTypeEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
//                onDataChanged(s);
                String result = s.toString().trim();
                if (result.length() == 6) {
                    if (getActivity() instanceof NewCustomerStepOneActivity) {
                        ((NewCustomerStepOneActivity) getActivity()).queryCarType(result);
                    } else if (getActivity() instanceof CustomerDetailItemActivity) {
//                        ((CustomerDetailItemActivity) getActivity()).queryCarType(result);
                    }

                } else if (result.length() < 6) {
                    mCarTypeTv.setVisibility(View.GONE);
                    if (getActivity() instanceof NewCustomerStepOneActivity) {
                        ((NewCustomerStepOneActivity) getActivity()).invalidateCarType();
                    } else if (getActivity() instanceof CustomerDetailItemActivity) {
//                        ((CustomerDetailItemActivity) getActivity()).invalidateCarType();
                    }
                }
            }
        });

        // 设置根视图的布局监听，如果当前用户是不该黄卡的owner，获取根视图的高设置给遮盖层，显示遮盖层，并拦截消费掉事件
        rlRootView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {

                if (null == mEntity) {
                    return;
                }

                if (!hasCheckCoverView) {
                    hasCheckCoverView = true;
                    OpportunityDetailEntity entity = (OpportunityDetailEntity) mEntity;
                    if (!entity.isYellowCardOwner() || entity.isSleepStatus() || entity.isFailedStatus()) {   // 当前用户不是该黄卡的owner或该黄卡处在休眠/战败状态
                        if (View.VISIBLE != itvCoverView.getVisibility()) {
                            itvCoverView.setVisibility(View.VISIBLE);
                            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(itvCoverView.getLayoutParams());
                            params.height = rlRootView.getHeight();
                            itvCoverView.setLayoutParams(params);
                        }
                    }
                }
            }
        });
    }

    private void changeStyle() {
        if (mHideCarTypeRow) {
            mCarTypeTr.setVisibility(View.GONE);
            mCarTypeLine.setVisibility(View.GONE);
        }
    }

    protected void initOptions() {
        Iterator<Map.Entry<String, String>> iterator = NewCustomerConstants.purposeMap.entrySet().iterator();
        mPurposeCodeList = new ArrayList<>();
        mPurposeValueList = new ArrayList<>();
        while (iterator.hasNext()) {
            Map.Entry<String, String> entry = iterator.next();
            mPurposeCodeList.add(entry.getKey());
            mPurposeValueList.add(entry.getValue());
        }

        iterator = NewCustomerConstants.propertyMap.entrySet().iterator();
        mPropertyCodeList = new ArrayList<>();
        mPropertyValueList = new ArrayList<>();
        while (iterator.hasNext()) {
            Map.Entry<String, String> entry = iterator.next();
            mPropertyCodeList.add(entry.getKey());
            mPropertyValueList.add(entry.getValue());
        }
    }

    protected void initPickerViews() {
        mPurposePicker = new OptionsPickerView.Builder(getContext(), new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                mCurrentPurpose = mPurposeCodeList.get(options1);
                mPurposeTv.setText(mPurposeValueList.get(options1));
                onDataChanged(mCurrentPurpose);
            }
        }).build();
        mPurposePicker.setNPicker(mPurposeValueList, null, null);

        mPropertyPicker = new OptionsPickerView.Builder(getContext(), new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                mCurrentProperty = mPropertyCodeList.get(options1);
                mPropertyTv.setText(mPropertyValueList.get(options1));
                onPropertySelected();
                onDataChanged(mCurrentProperty);
            }
        }).build();
        mPropertyPicker.setNPicker(mPropertyValueList, null, null);
    }

    private void onPropertySelected() {
        if (mCurrentProperty.equals("13520") || mCurrentProperty.equals("13530")) {
            if (getActivity() instanceof NewCustomerStepOneUnitedActivity) {
                ((NewCustomerStepOneUnitedActivity) getActivity()).showCurrentCarFragment(true);
            } else if (getActivity() instanceof CustomerDetailItemActivity) {
                ((CustomerDetailItemActivity) getActivity()).showCurrentCarFragment(true);
            }
        } else {
            if (getActivity() instanceof NewCustomerStepOneUnitedActivity) {
                ((NewCustomerStepOneUnitedActivity) getActivity()).showCurrentCarFragment(false);
            } else if (getActivity() instanceof CustomerDetailItemActivity) {
                ((CustomerDetailItemActivity) getActivity()).showCurrentCarFragment(false);
            }
        }
        if ("13530".equals(mCurrentProperty)) {
            mEvaluator.setVisibility(View.VISIBLE);
        } else {
            mEvaluator.setVisibility(View.GONE);
            mEvaluator.clearData();
        }
    }

    protected void setListeners() {
        mPurposeTr.setOnClickListener(this);
        mPropertyTr.setOnClickListener(this);
        mEvaluator.setOnBaseViewClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getContext(), AppraiserListActivity.class);
                AppraiserEntity.AppraiserInfoEntity entity = new AppraiserEntity.AppraiserInfoEntity();
                entity.setAppraiserId(mEvaluator.getInputData());
                i.putExtra("defaultAppraiserInfoEntity", entity);
                startActivityForResult(i, CODE_EVALUATOR);
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CODE_EVALUATOR && resultCode == RESULT_OK) {
            AppraiserEntity.AppraiserInfoEntity appraiserInfoEntity = data.getParcelableExtra("defaultAppraiserInfoEntity");
            mEvaluator.setStaticData(appraiserInfoEntity.getAppraiserId(), appraiserInfoEntity.getAppraiserName(), true);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.new_yellow_card_requirement_purpose_tr:
                mPurposePicker.show();
                break;
            case R.id.new_yellow_card_requirement_property_tr:
                mPropertyPicker.show();
                break;
        }
    }

    public OpportunitySubmitReqEntity getParameters() {
        OpportunitySubmitReqEntity entity = new OpportunitySubmitReqEntity();
        entity.setPurchaseId(mCurrentPurpose);//购买区分
        entity.setPropertyId(mCurrentProperty);//购买性质
        entity.setCarModelId(mCarTypeEt.getText().toString().trim());//意向车型
        entity.setAppraiserId(mEvaluator.getInputData());//评估师 有值传值，无值清空
        return entity;
    }

    public boolean checkDataValidation() {
        if (TextUtils.isEmpty(mCurrentProperty)) {
            return false;
        }
        if (TextUtils.isEmpty(mCurrentPurpose)) {
            return false;
        }
        if (mEvaluator.isVisible() && TextUtils.isEmpty(mEvaluator.getInputData())) {
            return false;
        }
        //意向车型一栏显示，且未输入
        if (!mHideCarTypeRow && TextUtils.isEmpty(mCarTypeEt.getText().toString().trim())) {
            return false;
        }
        return true;
    }

    public void onQueryCarTypeSuccess(CarTypesEntity entity) {
        if (entity != null) {
            mCarTypeTv.setVisibility(View.VISIBLE);
            mCarTypeTv.setText(entity.getModelDescCn());
        }
    }

    @Override
    public void renderView() {
        if (mEntity == null) {
            return;
        }
        OpportunityDetailEntity entity = (OpportunityDetailEntity) mEntity;
        String purposeId = entity.getPurchaseId();//购买区分
        if (!TextUtils.isEmpty(purposeId)) {
            mCurrentPurpose = purposeId;
            mPurposeTv.setText(NewCustomerConstants.purposeMap.get(purposeId));
            mPurposePicker.setSelectOptions(mPurposeCodeList.indexOf(purposeId));
        }
        String propertyId = entity.getPropertyId();//购买性质
        if (!TextUtils.isEmpty(propertyId)) {
            mCurrentProperty = propertyId;
            mPropertyTv.setText(NewCustomerConstants.propertyMap.get(propertyId));
            mPropertyPicker.setSelectOptions(mPropertyCodeList.indexOf(propertyId));
            onPropertySelected();
        }
        if ("13530".equals(mCurrentProperty)) {
            mEvaluator.setVisibility(View.VISIBLE);
            mEvaluator.setStaticData(entity.getAppraiserId(), entity.getAppraiserName(), true);
        } else {
            mEvaluator.setVisibility(View.GONE);
        }
        mCarTypeEt.setText(entity.getCarModelId());
        if (!TextUtils.isEmpty(entity.getCarModelName())) {
            mCarTypeTv.setText(entity.getCarModelName());
            mCarTypeTv.setVisibility(View.VISIBLE);
        }
    }

    public void hideCarTypeRow() {
        mHideCarTypeRow = true;
    }
}
