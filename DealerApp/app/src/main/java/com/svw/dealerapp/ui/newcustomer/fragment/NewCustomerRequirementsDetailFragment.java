package com.svw.dealerapp.ui.newcustomer.fragment;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.bigkoo.pickerview.OptionsPickerView;
import com.jaygoo.widget.RangeSeekBar;
import com.svw.dealerapp.R;
import com.svw.dealerapp.entity.newcustomer.CarTypesEntity;
import com.svw.dealerapp.entity.newcustomer.OpportunityDetailEntity;
import com.svw.dealerapp.entity.newcustomer.OpportunityRelationsBean;
import com.svw.dealerapp.entity.newcustomer.OpportunitySubmitReqEntity;
import com.svw.dealerapp.entity.newcustomer.OpportunityUpdateReqEntity;
import com.svw.dealerapp.global.NewCustomerConstants;
import com.svw.dealerapp.ui.newcustomer.GridSpacingItemDecoration;
import com.svw.dealerapp.ui.newcustomer.activity.CustomerDetailItemActivity;
import com.svw.dealerapp.ui.newcustomer.activity.NewCustomerStepOneUnitedActivity;
import com.svw.dealerapp.ui.newcustomer.activity.NewCustomerStepTwoActivity;
import com.svw.dealerapp.ui.newcustomer.activity.NewCustomerUnitedActivity;
import com.svw.dealerapp.ui.widget.CustomItemViewForOptionalPackage;
import com.svw.dealerapp.ui.widget.EditTextWithMicLayout;
import com.svw.dealerapp.ui.widget.InterceptTouchCoverView;
import com.svw.dealerapp.util.CommonUtils;
import com.svw.dealerapp.util.JLog;
import com.svw.dealerapp.util.PermissionUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 需求详情（付款意向、购车预算、外观内饰颜色、其他需求）
 * Created by xupan on 09/06/2017.
 */

public class NewCustomerRequirementsDetailFragment extends BaseCustomerFragment implements View.OnClickListener {

    private static final String TAG = "NewCustomerRequirementsDetailFragment";
    private boolean mChangeStyleFlag;
    private TableRow mWantedCarTypeTr;
    private TableRow mPayMethodTr, mOuterColorTr, mInnerColorTr;
    private TextView mPayMethodTv, mOuterColorTv, mInnerColorTv;
    private RangeSeekBar mBudgetSeekbar;
    private RangeSeekBar mBudgetGraySeekbar;
    private RecyclerView mRequirementsRv;
    private OptionsPickerView mPayMethodPicker, mInnerColorPicker, mOuterColorPicker;
    private EditText mCarTypeEt, mOptionalPackage;
    private TextView mCarTypeTv;
    private RelativeLayout rlRootView;
    private InterceptTouchCoverView itvCoverView;
    private EditTextWithMicLayout editTextWithMicLayout;
    private CarTypeFragment mCarTypeFragment;

    private List<String> mPayMethodCodeList, mInnerColorCodeList, mOuterColorCodeList, mRequirementCodeList;
    private List<String> mPayMethodValueList, mInnerColorValueList, mOuterColorValueList, mRequirementValueList;

    private String mCurrentPayMethod, mCurrentInnerColor, mCurrentOuterColor;
    private int mBudgetMin, mBudgetMax;
    private CarTypesEntity mCarTypeEntity;
    private GridAdapter mRequirementAdapter;
    private boolean mFirstEnter = true;

    private boolean hasCheckCoverView = false;
    private CustomItemViewForOptionalPackage mPackageView;

    public static NewCustomerRequirementsDetailFragment newInstance(Serializable entity) {
        NewCustomerRequirementsDetailFragment fragment = new NewCustomerRequirementsDetailFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("entity", entity);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.layout_new_customer_step_2_intention;
    }

    public void onDataChanged() {
        if (getActivity() instanceof NewCustomerStepOneUnitedActivity) {
            ((NewCustomerStepOneUnitedActivity) getActivity()).checkIfNeedToEnableSubmit();
        }
    }

    public void setChangeStyleTag() {
        mChangeStyleFlag = true;
    }

    private void changeStyle() {
        if (mChangeStyleFlag) {
            mWantedCarTypeTr.setVisibility(View.GONE);
        } else {
            addCarTypeFragment();
        }
    }

    private void addCarTypeFragment() {
        mCarTypeFragment = CarTypeFragment.newInstance(mEntity);
        mCarTypeFragment.setViewMandatory(true, false, false);
        getChildFragmentManager().beginTransaction()
                .add(R.id.new_customer_step_2_intention_car_type_container, mCarTypeFragment).commit();
    }

    protected void initViews(View view) {
        mWantedCarTypeTr = (TableRow) view.findViewById(R.id.new_customer_step_2_wanted_car_type_tr);
        changeStyle();
        mPayMethodTr = (TableRow) view.findViewById(R.id.new_customer_step_2_intention_pay_method_tr);
        mPayMethodTv = (TextView) view.findViewById(R.id.new_customer_step_2_intention_pay_method_tv);
        mRequirementsRv = (RecyclerView) view.findViewById(R.id.new_customer_step_2_intention_require_rv);
        rlRootView = (RelativeLayout) view.findViewById(R.id.rl_root_view);
        itvCoverView = (InterceptTouchCoverView) view.findViewById(R.id.itv_cover_view);

        mBudgetSeekbar = (RangeSeekBar) view.findViewById(R.id.new_customer_step_2_intention_budget_seekbar);
        mBudgetSeekbar.setOnRangeChangedListener(new RangeSeekBar.OnRangeChangedListener() {
            @Override
            public void onRangeChanged(RangeSeekBar view, float min, float max, boolean isFromUser) {
//                JLog.d(TAG, "min: " + min + "| max: " + max);
                mBudgetMin = (int) min * 10000;
                mBudgetMax = (int) max * 10000;
            }
        });
        mBudgetGraySeekbar = (RangeSeekBar) view.findViewById(R.id.new_customer_step_2_intention_budget_gray_seekbar);

        mOuterColorTr = (TableRow) view.findViewById(R.id.new_customer_step_2_intention_outer_color_tr);
        mOuterColorTv = (TextView) view.findViewById(R.id.new_customer_step_2_intention_outer_color_tv);
        mInnerColorTr = (TableRow) view.findViewById(R.id.new_customer_step_2_intention_inner_color_tr);
        mInnerColorTv = (TextView) view.findViewById(R.id.new_customer_step_2_intention_inner_color_tv);

        mCarTypeEt = (EditText) view.findViewById(R.id.new_customer_step_2_intention_car_type_et);
        mCarTypeTv = (TextView) view.findViewById(R.id.new_customer_step_2_intention_car_type_tv);
        mOptionalPackage = (EditText) view.findViewById(R.id.new_customer_step_2_option_package_et);
        editTextWithMicLayout = (EditTextWithMicLayout) view.findViewById(R.id.et_with_mic);
        editTextWithMicLayout.initEditWithMic(getActivity(), NewCustomerRequirementsDetailFragment.this);
        editTextWithMicLayout.setMaxTextNum(50);
        editTextWithMicLayout.setEnabled(false);
        editTextWithMicLayout.setHint(getResources().getString(R.string.new_customer_intention_other_requirement_hint));
        mPackageView = (CustomItemViewForOptionalPackage) view.findViewById(R.id.new_customer_step_2_intention_package);
        mPackageView.setTitleText("选装包");
        mPackageView.setHintTextForContentView("请选择选装包");

        initRequirementRecyclerView();
        mCarTypeEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                onDataChanged();
                String result = s.toString().trim();
                if (result.length() == 6) {
                    if (getActivity() instanceof NewCustomerStepTwoActivity) {
                        ((NewCustomerStepTwoActivity) getActivity()).searchCarType(result);
                    } else if (getActivity() instanceof NewCustomerStepOneUnitedActivity) {
                        ((NewCustomerStepOneUnitedActivity) getActivity()).queryCarType(result);
                    }
                } else if (result.length() < 6) {
                    mCarTypeTv.setVisibility(View.GONE);
                    if (getActivity() instanceof NewCustomerStepOneUnitedActivity) {
                        ((NewCustomerStepOneUnitedActivity) getActivity()).invalidateCarType();
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

    protected void initOptions() {
        Iterator<Map.Entry<String, String>> iterator = NewCustomerConstants.payMethodMap.entrySet().iterator();
        mPayMethodCodeList = new ArrayList<>();
        mPayMethodValueList = new ArrayList<>();
        while (iterator.hasNext()) {
            Map.Entry<String, String> entry = iterator.next();
            mPayMethodCodeList.add(entry.getKey());
            mPayMethodValueList.add(entry.getValue());
        }
        initCarTypeColorOptions();
    }

    private void initCarTypeColorOptions() {
        if (mCarTypeEntity == null) {
            return;
        }
        List<CarTypesEntity.ColorNListBean> colorNList = mCarTypeEntity.getColorNList();
        mInnerColorCodeList = new ArrayList<>();
        mInnerColorValueList = new ArrayList<>();
        for (CarTypesEntity.ColorNListBean innerColorBean : colorNList) {
            mInnerColorCodeList.add(innerColorBean.getColorId());
            mInnerColorValueList.add(innerColorBean.getColorNameCn());
        }

        List<CarTypesEntity.ColorWListBean> colorWList = mCarTypeEntity.getColorWList();
        mOuterColorCodeList = new ArrayList<>();
        mOuterColorValueList = new ArrayList<>();
        for (CarTypesEntity.ColorWListBean outerColorBean : colorWList) {
            mOuterColorCodeList.add(outerColorBean.getColorId());
            mOuterColorValueList.add(outerColorBean.getColorNameCn());
        }
    }

    protected void initPickerViews() {
        mPayMethodPicker = new OptionsPickerView.Builder(getContext(), new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                mCurrentPayMethod = mPayMethodCodeList.get(options1);
                mPayMethodTv.setText(mPayMethodValueList.get(options1));
            }
        }).build();
        mPayMethodPicker.setNPicker(mPayMethodValueList, null, null);
        initCarTypePickers();
    }

    private void initCarTypePickers() {
        if (mCarTypeEntity == null) {
            return;
        }
        mInnerColorPicker = new OptionsPickerView.Builder(getContext(), new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                if (null != mCurrentInnerColor && !mCurrentInnerColor.equals(mInnerColorCodeList.get(options1))) {
                    askActivityToResetOptionalPackage();
                }
                mCurrentInnerColor = mInnerColorCodeList.get(options1);
                mInnerColorTv.setText(mInnerColorValueList.get(options1));
            }
        }).build();
        mInnerColorPicker.setNPicker(mInnerColorValueList, null, null);

        mOuterColorPicker = new OptionsPickerView.Builder(getContext(), new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                mCurrentOuterColor = mOuterColorCodeList.get(options1);
                mOuterColorTv.setText(mOuterColorValueList.get(options1));
            }
        }).build();
        mOuterColorPicker.setNPicker(mOuterColorValueList, null, null);
    }

    private void renderPickerSelection() {
        if (mEntity == null || !(mEntity instanceof OpportunityDetailEntity)) {
            return;
        }
        OpportunityDetailEntity entity = (OpportunityDetailEntity) mEntity;
        String insideId = entity.getInsideColorId();
        if (!TextUtils.isEmpty(insideId)) {
            mInnerColorPicker.setSelectOptions(mInnerColorCodeList.indexOf(insideId));
        }
        String outsideId = entity.getOutsideColorId();
        if (!TextUtils.isEmpty(outsideId)) {
            mOuterColorPicker.setSelectOptions(mOuterColorCodeList.indexOf(outsideId));
        }
    }

    private void initRequirementRecyclerView() {
        Iterator<Map.Entry<String, String>> iterator = NewCustomerConstants.requirementsMap.entrySet().iterator();
        mRequirementCodeList = new ArrayList<>();
        mRequirementValueList = new ArrayList<>();
        while (iterator.hasNext()) {
            Map.Entry<String, String> entry = iterator.next();
            mRequirementCodeList.add(entry.getKey());
            mRequirementValueList.add(entry.getValue());
        }
        mRequirementsRv.setLayoutManager(new GridLayoutManager(getContext(), 4, LinearLayoutManager.VERTICAL, false));
        mRequirementsRv.addItemDecoration(new GridSpacingItemDecoration(4, getResources().getDimensionPixelSize(R.dimen.new_customer_grid_recycler_spacing), true));
        mRequirementAdapter = new GridAdapter(mRequirementCodeList, mRequirementValueList, 0);
        mRequirementsRv.setAdapter(mRequirementAdapter);
    }

    private void renderCarTypeViewData() {
        if (mEntity == null) {
            return;
        }
        CarTypesEntity entity = (CarTypesEntity) mEntity;
        mCarTypeEt.setText(entity.getModelId());
        mCarTypeTv.setText(entity.getModelDescCn());
        mCarTypeTv.setVisibility(View.VISIBLE);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.new_customer_step_2_intention_pay_method_tr:
                mPayMethodPicker.show();
                break;
            case R.id.new_customer_step_2_intention_outer_color_tr:
                if (mOuterColorPicker != null) {
                    mOuterColorPicker.show();
                }
                break;
            case R.id.new_customer_step_2_intention_inner_color_tr:
                if (mInnerColorPicker != null) {
                    mInnerColorPicker.show();
                }
        }
    }

    protected void setListeners() {
        mPayMethodTr.setOnClickListener(this);
        mOuterColorTr.setOnClickListener(this);
        mInnerColorTr.setOnClickListener(this);
        mPackageView.setOnBaseViewClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                askActivityToQueryOptionalPackage();
            }
        });
    }

    private void askActivityToQueryOptionalPackage() {
        if (getActivity() instanceof NewCustomerUnitedActivity) {
            ((NewCustomerUnitedActivity) getActivity()).queryOptionalPackage();
        } else if (getActivity() instanceof CustomerDetailItemActivity) {
            ((CustomerDetailItemActivity) getActivity()).queryOptionalPackage();
        }
    }

    public OpportunityUpdateReqEntity getParameters() {
        OpportunityUpdateReqEntity entity = new OpportunityUpdateReqEntity();
        entity.setPaymentMode(mCurrentPayMethod);//分期或全款
        entity.setBudgetMin(String.valueOf(mBudgetMin));
        entity.setBudgetMax(String.valueOf(mBudgetMax));
        entity.setOptionPackage(mOptionalPackage.getText().toString().trim());//选装包
        entity.setOutsideColorId(mCurrentOuterColor);
        entity.setInsideColorId(mCurrentInnerColor);
        entity.setOpportunityRelations(getOpportunityRelationsBeansList());
        if (!mChangeStyleFlag) {
            OpportunitySubmitReqEntity entityFromCarTypeFragment = mCarTypeFragment.getParameters();
            entity.setSeriesId(entityFromCarTypeFragment.getSeriesId());//意向车系
            entity.setCarModelId(entityFromCarTypeFragment.getCarModelId());//意向车型
        }
        return entity;
    }

    /**
     * 生成多选项结果List
     *
     * @return
     */
    private List<OpportunityRelationsBean> getOpportunityRelationsBeansList() {
        List<OpportunityRelationsBean> list = new ArrayList<>();
        //其他需求
        Set<Integer> requirementSet = mRequirementAdapter.getCheckedIndexSet();
        String flag = "0";
        for (Integer index : requirementSet) {
            OpportunityRelationsBean requirementRelationsBean = new OpportunityRelationsBean();
            requirementRelationsBean.setRelaId(mRequirementCodeList.get(index));
            requirementRelationsBean.setRelaDesc(mRequirementValueList.get(index));
            requirementRelationsBean.setRelaFlag(flag);
            //用户选择了“其它”后，才传入输入框内容
            if (mRequirementValueList.get(index).contains("其它")) {
                requirementRelationsBean.setRemark(editTextWithMicLayout.getTextContent());
            }
            JLog.d(TAG, "bean: " + requirementRelationsBean);
            list.add(requirementRelationsBean);
        }
        return list;
    }

    public boolean checkDataValidation() {
        if (!mChangeStyleFlag) {
            return mCarTypeFragment.checkDataValidation();
        }
//        if (TextUtils.isEmpty(mCarTypeEt.getText().toString().trim())) {
//            return false;
//        }
        return true;
    }

    public void onQueryCarTypeSuccess(CarTypesEntity entity) {
        mCarTypeEntity = entity;
        mCarTypeTv.setVisibility(View.VISIBLE);
        mCarTypeTv.setText(entity.getModelDescCn());
        initCarTypeColorOptions();
        initCarTypePickers();
        renderPickerSelection();
        //第一次进入时会查询车型以获取颜色列表，但此时不能清空当前颜色选项，否则提交时外观和内饰颜色会传入空字符串
        if (mFirstEnter) {
            mFirstEnter = false;
            return;
        }
        mCurrentOuterColor = "";
        mCurrentInnerColor = "";
        mInnerColorTv.setText("");
        mOuterColorTv.setText("");
    }

    /**
     * 重置当前内外颜色和旧的车型码对应的所有内外颜色选项
     */
    public void resetCarTypeColor() {
        mCurrentOuterColor = "";
        mCurrentInnerColor = "";
        mInnerColorTv.setText("");
        mOuterColorTv.setText("");
        mOuterColorPicker = null;
        mInnerColorPicker = null;
        if (mOuterColorCodeList != null) mOuterColorCodeList.clear();
        if (mInnerColorCodeList != null) mInnerColorCodeList.clear();
    }

    public void resetOptionalPackage() {
        mPackageView.setData(new ArrayList<String>());
    }

    private void askActivityToResetOptionalPackage() {
        if (getActivity() instanceof CustomerDetailItemActivity) {
            ((CustomerDetailItemActivity) getActivity()).resetOptionalPackage();
        } else if (getActivity() instanceof NewCustomerUnitedActivity) {
            ((NewCustomerUnitedActivity) getActivity()).resetOptionalPackage();
        }
    }

    public void displayOptionalPackage(List<String> list) {
        mPackageView.setData(list);
    }

    @Override
    protected void renderView() {
        if (mEntity == null) {
            return;
        }
        if (mEntity instanceof OpportunityDetailEntity) {
            OpportunityDetailEntity entity = (OpportunityDetailEntity) mEntity;
            String payMethod = entity.getPaymentMode();
            if (!TextUtils.isEmpty(payMethod)) {
                mCurrentPayMethod = payMethod;
                mPayMethodTv.setText(NewCustomerConstants.payMethodMap.get(entity.getPaymentMode()));
                mPayMethodPicker.setSelectOptions(mPayMethodCodeList.indexOf(payMethod));
            }
            // 如果当前用户不是该黄卡的owner
            if (!entity.isYellowCardOwner() || entity.isSleepStatus() || entity.isFailedStatus()) {    // 当前用户不是该黄卡的owner或该黄卡处在休眠/战败状态
                try {
                    JLog.d(TAG, "budget min: " + entity.getBudgetMin() + "| budget max:" + entity.getBudgetMax());
                    mBudgetGraySeekbar.setVisibility(View.VISIBLE);
                    mBudgetSeekbar.setVisibility(View.GONE);
                    if (!entity.getBudgetMin().equals(entity.getBudgetMax())) {
                        mBudgetGraySeekbar.setValue((Float.valueOf(entity.getBudgetMin()) / 10000),
                                (Float.valueOf(entity.getBudgetMax()) / 10000));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                try {
                    JLog.d(TAG, "budget min: " + entity.getBudgetMin() + "| budget max:" + entity.getBudgetMax());
                    String min = entity.getBudgetMin();
                    String max = entity.getBudgetMax();
                    if (min == null || max == null) {
                        JLog.d(TAG, "null pointer");
                    } else if (!entity.getBudgetMin().equals(entity.getBudgetMax())) {
                        mBudgetSeekbar.setValue((Float.valueOf(entity.getBudgetMin()) / 10000),
                                (Float.valueOf(entity.getBudgetMax()) / 10000));
                        mBudgetMin = Integer.valueOf(entity.getBudgetMin());
                        mBudgetMax = Integer.valueOf(entity.getBudgetMax());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if (!TextUtils.isEmpty(entity.getCarModelId())) {
                mCarTypeEt.setText(entity.getCarModelId());
            }
            if (!TextUtils.isEmpty(entity.getOptionPackage())) {
                mOptionalPackage.setText(entity.getOptionPackage());
            }
            if (!TextUtils.isEmpty(entity.getInsideColorId())) {
                mInnerColorTv.setText(entity.getInsideColorName());
                mCurrentInnerColor = entity.getInsideColorId();
            }
            if (!TextUtils.isEmpty(entity.getOutsideColorId())) {
                mOuterColorTv.setText(entity.getOutsideColorName());
                mCurrentOuterColor = entity.getOutsideColorId();
            }
            List<String> resultList = CommonUtils.getOptionalPackage(entity.getEcCarOptions(), true);
            mPackageView.setData(resultList);
            List<OpportunityDetailEntity.RelationsBean> list = entity.getOpportunityRelations();
            if (list == null || list.isEmpty()) {
                return;
            }
            Set<String> set = new HashSet<>();
            for (OpportunityDetailEntity.RelationsBean bean : list) {
                //relaFlag为0表示其他需求，为1表示了解信息途径
                if ("0".equals(bean.getRelaFlag())) {
                    set.add(bean.getRelaId());
                    if (!TextUtils.isEmpty(bean.getRemark())) {
                        editTextWithMicLayout.setEnabled(true);
                        editTextWithMicLayout.setText(bean.getRemark());
                    }
                }
            }
            mRequirementAdapter.setCheckedCodeSet(set);
        } else if (mEntity instanceof CarTypesEntity) {
            renderCarTypeViewData();
        }

    }

    private class GridAdapter extends RecyclerView.Adapter<GridAdapter.ViewHolder> {

        private List<String> mValueList, mCodeList;
        private Set<Integer> mCheckedIndexSet = new HashSet<>();
        private Set<String> mCheckedCodeSet;

        private GridAdapter(List<String> codeList, List<String> valueList, int flag) {
            mCodeList = codeList;
            mValueList = valueList;
        }

        public Set<Integer> getCheckedIndexSet() {
            return mCheckedIndexSet;
        }

        public void setCheckedCodeSet(Set<String> set) {
            mCheckedCodeSet = set;
        }

        @Override
        public GridAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_new_customer_grid, parent, false);
            return new GridAdapter.ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final GridAdapter.ViewHolder holder, final int position) {
            final int adapterPosition = holder.getAdapterPosition();
            holder.checkBox.setText(mValueList.get(adapterPosition));
            holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
                        mCheckedIndexSet.add(adapterPosition);
                        if (mValueList.get(adapterPosition).contains("其它")) {
                            editTextWithMicLayout.setEnabled(true);
                        }
                    } else {
                        mCheckedIndexSet.remove(adapterPosition);
                        if (mValueList.get(adapterPosition).contains("其它")) {
                            editTextWithMicLayout.setEnabled(false);
                            editTextWithMicLayout.setText("");
                        }
                    }
                }
            });
            if (mCheckedCodeSet != null && mCheckedCodeSet.contains(mCodeList.get(adapterPosition))) {
                holder.checkBox.setChecked(true);
                // 如果当前用户不是该黄卡的owner
                if (mEntity instanceof OpportunityDetailEntity) {
                    OpportunityDetailEntity entity = (OpportunityDetailEntity) mEntity;
                    if (!entity.isYellowCardOwner() || entity.isSleepStatus() || entity.isFailedStatus()) {    // 当前用户不是该黄卡的owner或该黄卡处在休眠/战败状态
                        holder.checkBox.setBackground(getResources().getDrawable(R.drawable.new_customer_checkbox_no_owner_checked));
                        holder.checkBox.setTextColor(getResources().getColor(R.color.white));
                    }
                }
            }
        }

        @Override
        public int getItemCount() {
            return mValueList.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            private CheckBox checkBox;

            private ViewHolder(View itemView) {
                super(itemView);
                checkBox = (CheckBox) itemView.findViewById(R.id.item_new_customer_grid_checkbox);
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        editTextWithMicLayout.stopNLS();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case PermissionUtils.REQUEST_AUDIO_RECORD_PERMISSION:
                if (Manifest.permission.RECORD_AUDIO.equals(permissions[0]) && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    editTextWithMicLayout.clickRecordButton();
                }
                break;
        }
    }

}
