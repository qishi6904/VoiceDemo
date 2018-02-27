package com.svw.dealerapp.ui.newcustomer.fragment;

import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.bigkoo.pickerview.OptionsPickerView;
import com.svw.dealerapp.DealerApp;
import com.svw.dealerapp.R;
import com.svw.dealerapp.entity.newcustomer.OpportunityDetailEntity;
import com.svw.dealerapp.entity.newcustomer.OpportunitySubmitReqEntity;
import com.svw.dealerapp.global.NewCustomerConstants;
import com.svw.dealerapp.ui.newcustomer.activity.NewCustomerStepOneUnitedActivity;
import com.svw.dealerapp.ui.widget.CustomItemViewForEditText;
import com.svw.dealerapp.ui.widget.CustomItemViewForOptionsPicker;
import com.svw.dealerapp.ui.widget.InterceptTouchCoverView;
import com.svw.dealerapp.util.dbtools.DBHelper;
import com.svw.dealerapp.util.StringUtils;
import com.svw.dealerapp.util.ToastUtils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

//import butterknife.ButterKnife;
//import butterknife.OnTextChanged;

/**
 * 黄卡基本信息
 * Created by xupan on 08/06/2017.
 */

public class NewCustomerBasicInfoFragment extends BaseCustomerFragment implements View.OnClickListener {

    private static final String TAG = "NewCustomerBasicInfoFragment";
    private TableRow mAgeTr, mSourceTr, mMediaTr;
    private TextView mAgeTv, mSourceTv, mMediaTv;
    private EditText mNameEt, mMobileEt;
    private RadioGroup mGenderGr;
    private CheckBox mRecommendedCb;
    private TableRow mRecommendedNameTr, mRecommendedMobileTr;
    private EditText mRecommendedNameEt, mRecommendedMobileEt;
    private View mRecommendedLine, mRecommendedMobileLine, mMediaLine;
    private RadioButton femaleRadioBtn;
    private RadioButton maleRadioBtn;
    private RelativeLayout rlRootView;
    private InterceptTouchCoverView itvCoverView;

    private OptionsPickerView mAgePicker, mSourcePicker, mMediaPicker;
    private CustomItemViewForOptionsPicker mActivityPicker;//外拓

    private List<String> mAgeCodeList, mSourceCodeList, mMediaCodeList;
    private List<String> mAgeValueList, mSourceValueList, mMediaValueList;

    private String mCurrentAge, mCurrentGender, mCurrentSource, mCurrentMedia;
    private CustomItemViewForEditText mCustomerCodeView;

    private boolean hasCheckCoverView = false;

    public static NewCustomerBasicInfoFragment newInstance(OpportunityDetailEntity entity) {
        NewCustomerBasicInfoFragment fragment = new NewCustomerBasicInfoFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("entity", entity);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
//        ButterKnife.unbind(this);
    }

    //    @OnTextChanged({R.id.new_yellow_card_info_name_et, R.id.new_customer_step_1_info_phone_et,
//            R.id.new_customer_jipan_name_et, R.id.new_customer_jipan_customer_mobile_et})
    public void onDataChanged() {
        if (getActivity() instanceof NewCustomerStepOneUnitedActivity) {
            ((NewCustomerStepOneUnitedActivity) getActivity()).checkIfNeedToEnableSubmit();
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.layout_new_customer_basic_info;
    }

    protected void initViews(final View view) {
//        ButterKnife.bind(this, view);
        mGenderGr = (RadioGroup) view.findViewById(R.id.new_customer_step_1_gender_rg);
        mAgeTr = (TableRow) view.findViewById(R.id.new_yellow_card_info_age_tr);
        mSourceTr = (TableRow) view.findViewById(R.id.new_yellow_card_info_source_tr);
        mMediaTr = (TableRow) view.findViewById(R.id.new_yellow_card_info_media_tr);
        rlRootView = (RelativeLayout) view.findViewById(R.id.rl_root_view);
        itvCoverView = (InterceptTouchCoverView) view.findViewById(R.id.itv_cover_view);

        mAgeTv = (TextView) view.findViewById(R.id.new_yellow_card_info_age_tv);
        mSourceTv = (TextView) view.findViewById(R.id.new_yellow_card_info_source_tv);
        mMediaTv = (TextView) view.findViewById(R.id.new_yellow_card_info_media_tv);
        mMediaLine = view.findViewById(R.id.new_yellow_card_info_media_line);
        mActivityPicker = (CustomItemViewForOptionsPicker) view.findViewById(R.id.new_customer_step_1_activity_picker);
        mActivityPicker.setTitleText("外拓");
        mActivityPicker.setHintTextForContentView("请选择外拓");
        mNameEt = (EditText) view.findViewById(R.id.new_yellow_card_info_name_et);
        mMobileEt = (EditText) view.findViewById(R.id.new_customer_step_1_info_phone_et);

        mRecommendedCb = (CheckBox) view.findViewById(R.id.new_customer_step_1_recommend_by_friend_cb);
        mRecommendedNameTr = (TableRow) view.findViewById(R.id.new_customer_recommended_name_tr);
        mRecommendedMobileTr = (TableRow) view.findViewById(R.id.new_customer_recommended_mobile_tr);
        mRecommendedNameEt = (EditText) view.findViewById(R.id.new_customer_jipan_name_et);
        mRecommendedMobileEt = (EditText) view.findViewById(R.id.new_customer_jipan_customer_mobile_et);
        mRecommendedLine = view.findViewById(R.id.new_customer_recommended_name_line);
        mRecommendedMobileLine = view.findViewById(R.id.new_customer_recommended_mobile_line);
        maleRadioBtn = (RadioButton) view.findViewById(R.id.new_customer_step_1_gender_male_rb);
        femaleRadioBtn = (RadioButton) view.findViewById(R.id.new_customer_step_1_gender_female_rb);

        mCustomerCodeView = (CustomItemViewForEditText) view.findViewById(R.id.new_customer_code_view);
        mCustomerCodeView.setTitleText("潜客编号");
        mCustomerCodeView.setEnabled(false);

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
        Iterator<Map.Entry<String, String>> iterator = NewCustomerConstants.ageMap.entrySet().iterator();
        mAgeCodeList = new ArrayList<>();
        mAgeValueList = new ArrayList<>();
        while (iterator.hasNext()) {
            Map.Entry<String, String> entry = iterator.next();
            mAgeCodeList.add(entry.getKey());
            mAgeValueList.add(entry.getValue());
        }

        iterator = NewCustomerConstants.sourceMap.entrySet().iterator();
        mSourceCodeList = new ArrayList<>();
        mSourceValueList = new ArrayList<>();
        while (iterator.hasNext()) {
            Map.Entry<String, String> entry = iterator.next();
            mSourceCodeList.add(entry.getKey());
            mSourceValueList.add(entry.getValue());
        }

        mMediaCodeList = new ArrayList<>();
        mMediaValueList = new ArrayList<>();
    }

    protected void initPickerViews() {
        mAgePicker = new OptionsPickerView.Builder(getContext(), new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                mCurrentAge = mAgeCodeList.get(options1);
                mAgeTv.setText(mAgeValueList.get(options1));
            }
        }).build();
        mAgePicker.setNPicker(mAgeValueList, null, null);

        mSourcePicker = new OptionsPickerView.Builder(getContext(), new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                mCurrentSource = mSourceCodeList.get(options1);
                mSourceTv.setText(mSourceValueList.get(options1));
                setMediaPicker(mCurrentSource);
            }
        }).build();
        mSourcePicker.setNPicker(mSourceValueList, null, null);
    }

    private void setMediaPicker(String sourceCode) {
        if (TextUtils.isEmpty(sourceCode)) {
            return;
        }
        mMediaCodeList.clear();
        mMediaValueList.clear();
        mCurrentMedia = "";
        mMediaTv.setText("");
        DBHelper dbHelper = DealerApp.dbHelper;
//        Cursor cursor = dbHelper.rawQuery("select * from DictionaryRel where dictId=? and relaTypeId=?", new String[]{sourceCode, "999"});
        Cursor cursor = dbHelper.rawQuery("select * from DictionaryRel where dictId=?", new String[]{sourceCode});
        if (cursor != null) {
            while (cursor.moveToNext()) {
                mMediaCodeList.add(cursor.getString(5));
                mMediaValueList.add(cursor.getString(6));
                NewCustomerConstants.mediaMap.put(cursor.getString(5), cursor.getString(6));
            }
            cursor.close();
        }
        mMediaPicker = new OptionsPickerView.Builder(getContext(), new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                if (mMediaCodeList == null || mMediaCodeList.isEmpty()) {
                    return;
                }
                mCurrentMedia = mMediaCodeList.get(options1);
                mMediaTv.setText(mMediaValueList.get(options1));
            }
        }).build();
        mMediaPicker.setNPicker(mMediaValueList, null, null);
    }

    protected void setListeners() {
        mAgeTr.setOnClickListener(this);
//        mSourceTr.setOnClickListener(this);
//        mMediaTr.setOnClickListener(this);
        mGenderGr.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                if (checkedId == R.id.new_customer_step_1_gender_male_rb) {
                    mCurrentGender = "0";
                } else if (checkedId == R.id.new_customer_step_1_gender_female_rb) {
                    mCurrentGender = "1";
                }
                onDataChanged();
            }
        });
        mRecommendedCb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                mRecommendedNameTr.setVisibility(isChecked ? View.VISIBLE : View.GONE);
                mRecommendedMobileTr.setVisibility(isChecked ? View.VISIBLE : View.GONE);
                mRecommendedLine.setVisibility(isChecked ? View.VISIBLE : View.GONE);
                mRecommendedMobileLine.setVisibility(isChecked ? View.VISIBLE : View.GONE);
                onDataChanged();
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.new_yellow_card_info_age_tr:
                mAgePicker.show();
                break;
            case R.id.new_yellow_card_info_source_tr:
                mSourcePicker.show();
                break;
            case R.id.new_yellow_card_info_media_tr:
                if (mMediaPicker != null) {
                    mMediaPicker.show();
                }
                break;
        }
    }

    public OpportunitySubmitReqEntity getParameters() {
        OpportunitySubmitReqEntity entity = new OpportunitySubmitReqEntity();
        entity.setCustGender(mCurrentGender);//性别
        entity.setCustName(mNameEt.getText().toString().trim());//姓名
        entity.setCustMobile(mMobileEt.getText().toString().trim());//电话
        entity.setCustAge(mCurrentAge);//年龄
        entity.setSrcTypeId(mCurrentSource);//客户来源
        if (!TextUtils.isEmpty(mCurrentMedia)) {
            entity.setChannelId(mCurrentMedia);//媒体
        }
        if (!TextUtils.isEmpty(mActivityPicker.getInputData())) {
            entity.setActivityId(mActivityPicker.getInputData());//外拓
        }
        entity.setRecomName(mRecommendedCb.isChecked() ? mRecommendedNameEt.getText().toString().trim() : "");
        entity.setRecomMobile(mRecommendedCb.isChecked() ? mRecommendedMobileEt.getText().toString().trim() : "");
        return entity;
    }

    public boolean checkDataValidation() {
        if (TextUtils.isEmpty(mNameEt.getText().toString().trim())) {
            return false;
        }
        if (TextUtils.isEmpty(mMobileEt.getText().toString().trim())) {
            return false;
        }
        if (TextUtils.isEmpty(mCurrentGender)) {
            return false;
        }
        if (mRecommendedCb.isChecked()) {
            if (TextUtils.isEmpty(mRecommendedNameEt.getText().toString().trim())) {
                return false;
            }
            if (TextUtils.isEmpty(mRecommendedMobileEt.getText().toString().trim())) {
                return false;
            }
        }
        return true;
    }

    /**
     * 是否为合法的手机号码
     *
     * @return true合法，反之为false
     */
    public boolean checkMobile() {
        if (!StringUtils.isValidMobile(mMobileEt.getText().toString().trim())) {
            ToastUtils.showToast(getString(R.string.new_customer_wrong_mobile_format));
            return false;
        }
        if (mRecommendedCb.isChecked() &&
                !StringUtils.isValidMobile(mRecommendedMobileEt.getText().toString().trim())) {
            ToastUtils.showToast(getString(R.string.new_customer_jipan_wrong_mobile_format));
            return false;
        }
        return true;
    }

    @Override
    public void renderView() {
        if (mEntity == null) {
            return;
        }
        OpportunityDetailEntity entity = (OpportunityDetailEntity) mEntity;
        mNameEt.setText(entity.getCustName());
        String gender = entity.getCustGender();
        if (!entity.isYellowCardOwner() || entity.isSleepStatus() || entity.isFailedStatus()) {   // 当前用户不是该黄卡的owner或该黄卡处在休眠/战败状态
            Drawable checkedDrawable = getResources().getDrawable(R.mipmap.public_radio_button_no_owner_checked);
            checkedDrawable.setBounds(0, 0, checkedDrawable.getMinimumWidth(), checkedDrawable.getMinimumHeight());
            Drawable uncheckedDrawable = getResources().getDrawable(R.mipmap.public_radio_button_no_owner_unchecked);
            uncheckedDrawable.setBounds(0, 0, uncheckedDrawable.getMinimumWidth(), uncheckedDrawable.getMinimumHeight());
            if ("0".equals(gender)) {
                maleRadioBtn.setCompoundDrawables(checkedDrawable, null, null, null);
                femaleRadioBtn.setCompoundDrawables(uncheckedDrawable, null, null, null);
            } else if ("1".equals(gender)) {
                maleRadioBtn.setCompoundDrawables(uncheckedDrawable, null, null, null);
                femaleRadioBtn.setCompoundDrawables(checkedDrawable, null, null, null);
            }
        } else {     // 当前用户是该黄卡的owner
            if (!TextUtils.isEmpty(gender)) {
                if (gender.equals("0")) {
                    mGenderGr.check(R.id.new_customer_step_1_gender_male_rb);
                } else if (gender.equals("1")) {
                    mGenderGr.check(R.id.new_customer_step_1_gender_female_rb);
                }
            }
        }
        mMobileEt.setText(entity.getCustMobile());
        String currentAgeCode = entity.getCustAge();
        if (!TextUtils.isEmpty(currentAgeCode)) {
            mAgeTv.setText(NewCustomerConstants.ageMap.get(currentAgeCode));
            mAgePicker.setSelectOptions(mAgeCodeList.indexOf(currentAgeCode));
            mCurrentAge = currentAgeCode;//由上个界面带入的年龄值不仅要显示相应的UI，同时要写到传参的值里
        }
        String srcTypeId = entity.getSrcTypeId();
        if (!TextUtils.isEmpty(srcTypeId)) {
            mSourceTv.setText(NewCustomerConstants.sourceMap.get(srcTypeId));
            mSourcePicker.setSelectOptions(mSourceCodeList.indexOf(srcTypeId));
            mCurrentSource = srcTypeId;//由上个界面带入的客户来源不仅要显示相应的UI，同时要写到传参的值里
            setMediaPicker(srcTypeId);
            //非到店和外拓时，才显示媒体一栏
            if (!srcTypeId.equals("11010") && !srcTypeId.equals("11050")) {
                mMediaTr.setVisibility(View.VISIBLE);
                mMediaLine.setVisibility(View.VISIBLE);
            } else if (srcTypeId.equals("11050")) {
                //外拓
                mActivityPicker.setVisibility(View.VISIBLE);
                mActivityPicker.setStaticData(entity.getActivityId(), entity.getActivitySubject());
            } else {
                mMediaTr.setVisibility(View.GONE);
                mMediaLine.setVisibility(View.GONE);
            }
            String channelId = entity.getChannelId();
            if (!TextUtils.isEmpty(channelId)) {
                int index = mMediaCodeList.indexOf(channelId);
                if (index != -1) {
                    mMediaTv.setText(mMediaValueList.get(index));
                    mMediaPicker.setSelectOptions(index);
                    mCurrentMedia = channelId;//由上个界面带入的媒体值不仅要显示相应的UI，同时要写到传参的值里
                }
            }
        }
        String recommName = entity.getRecomName();
        String recomMobile = entity.getRecomMobile();
        if (TextUtils.isEmpty(recommName) || TextUtils.isEmpty(recomMobile)) {
            mRecommendedCb.setChecked(false);
        } else {
            mRecommendedCb.setChecked(true);
            mRecommendedNameEt.setText(recommName);
            mRecommendedMobileEt.setText(recomMobile);
        }
        if (!TextUtils.isEmpty(entity.getCustomerCode())) {
            mCustomerCodeView.setData(entity.getCustomerCode());
        }
    }

}