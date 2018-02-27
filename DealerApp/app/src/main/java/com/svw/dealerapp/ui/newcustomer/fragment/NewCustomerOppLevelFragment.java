package com.svw.dealerapp.ui.newcustomer.fragment;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TableRow;
import android.widget.TextView;

import com.bigkoo.pickerview.OptionsPickerView;
import com.svw.dealerapp.R;
import com.svw.dealerapp.entity.newcustomer.OpportunityDetailEntity;
import com.svw.dealerapp.entity.newcustomer.OpportunitySubmitReqEntity;
import com.svw.dealerapp.global.NewCustomerConstants;
import com.svw.dealerapp.ui.newcustomer.activity.AddFollowupRecordActivity;
import com.svw.dealerapp.ui.newcustomer.activity.NewCustomerStepOneActivity;
import com.svw.dealerapp.ui.newcustomer.activity.NewCustomerStepOneUnitedActivity;
import com.svw.dealerapp.ui.order.activity.OrderCancelActivity2;
import com.svw.dealerapp.ui.widget.EditTextWithMicLayout;
import com.svw.dealerapp.util.PermissionUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import static com.svw.dealerapp.global.NewCustomerConstants.levelMap;

/**
 * 潜客等级Fragment(潜客等级、重点客户、继续跟进理由)
 * Created by xupan on 08/06/2017.
 */

public class NewCustomerOppLevelFragment extends BaseCustomerFragment implements View.OnClickListener {
    private static final String TAG = "NewCustomerOppLevelFragment";
    private int mFlag;//标记是否为仅显示跟进理由，还是仅显示潜客级别
    private TableRow mLevelTr, mKeyCustomerTr, mReasonTitleTr, mReasonContentTr;
    private TextView mLevelTitleTv, mLevelTv;
    private OptionsPickerView mLevelPicker;

    private List<String> mLevelCodeList;
    private List<String> mLevelValueList;
    private String mCurrentLevel;

    private CheckBox mKeyCustomerCb;
    private View mKeyCustomerTrLine, mOppLevelLine;
    private EditTextWithMicLayout editTextWithMicLayout;

    public static NewCustomerOppLevelFragment newInstance(OpportunityDetailEntity entity) {
        NewCustomerOppLevelFragment fragment = new NewCustomerOppLevelFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("entity", entity);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.layout_new_customer_customer_level;
    }

    @Override
    protected void onDataChanged(Object object) {
        super.onDataChanged(object);
        if (getActivity() instanceof NewCustomerStepOneUnitedActivity) {
            ((NewCustomerStepOneUnitedActivity) getActivity()).checkIfNeedToEnableSubmit();
        } else if (getActivity() instanceof AddFollowupRecordActivity) {
            ((AddFollowupRecordActivity) getActivity()).checkIfNeedToEnableSubmit();
        } else if (getActivity() instanceof OrderCancelActivity2) {
            ((OrderCancelActivity2) getActivity()).checkIfNeedToEnableSubmit();
        }
    }

    public void initViews(View view) {
        mLevelTr = (TableRow) view.findViewById(R.id.new_yellow_card_level_level_tr);
        mLevelTv = (TextView) view.findViewById(R.id.new_yellow_card_level_level_tv);
        mLevelTitleTv = (TextView) view.findViewById(R.id.new_yellow_card_level_title_tv);
        mOppLevelLine = view.findViewById(R.id.new_yellow_card_level_level_line);

        mKeyCustomerTr = (TableRow) view.findViewById(R.id.new_customer_step_1_level_key_customer_tr);
        mKeyCustomerCb = (CheckBox) view.findViewById(R.id.new_customer_step_1_level_key_customer_cb);
        mKeyCustomerTrLine = view.findViewById(R.id.new_customer_step_1_level_key_customer_tr_line);
        mReasonTitleTr = (TableRow) view.findViewById(R.id.new_customer_step_1_level_reason_title_tr);
        mReasonContentTr = (TableRow) view.findViewById(R.id.new_customer_step_1_level_reason_content_tr);
        editTextWithMicLayout = (EditTextWithMicLayout) view.findViewById(R.id.et_with_mic);
        editTextWithMicLayout.initEditWithMic(getActivity(), NewCustomerOppLevelFragment.this);
        editTextWithMicLayout.setMaxTextNum(50);
        editTextWithMicLayout.setEnabled(true);
        editTextWithMicLayout.setHint(getResources().getString(R.string.new_customer_opp_level_followup_reason_hint));
        editTextWithMicLayout.setOnTextChangeListener(new EditTextWithMicLayout.OnTextChangeListener() {
            @Override
            public void onAfterTextChange(CharSequence s) {
                onDataChanged(s);
            }
        });
    }

    private void changeStyleForFollowupReason() {
        switch (mFlag) {
            case NewCustomerConstants.FLAG_FOLLOWUP_REASON:
//                mHeaderTv.setText(R.string.new_customer_opp_level_header_as_followup);
                mLevelTr.setVisibility(View.GONE);
                mOppLevelLine.setVisibility(View.GONE);
                mKeyCustomerTr.setVisibility(View.GONE);
                mKeyCustomerTrLine.setVisibility(View.GONE);
                break;
            case NewCustomerConstants.FLAG_OPP_LEVEL:
                mLevelTitleTv.setText(R.string.new_customer_opp_level_title_as_change_level);
                mReasonTitleTr.setVisibility(View.GONE);
                mReasonContentTr.setVisibility(View.GONE);
                mKeyCustomerTr.setVisibility(View.GONE);
                mKeyCustomerTrLine.setVisibility(View.GONE);
                break;
            default:
                break;
        }
    }

    public void initOptions() {
        Iterator<Map.Entry<String, String>> iterator = levelMap.entrySet().iterator();
        mLevelCodeList = new ArrayList<>();
        mLevelValueList = new ArrayList<>();
        while (iterator.hasNext()) {
            Map.Entry<String, String> entry = iterator.next();
            mLevelCodeList.add(entry.getKey());
            mLevelValueList.add(entry.getValue());
        }
    }

    protected void initPickerViews() {
        mLevelPicker = new OptionsPickerView.Builder(getContext(), new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                onLevelChanged(mLevelCodeList.get(options1));
            }
        }).build();
        mLevelPicker.setNPicker(mLevelValueList, null, null);
    }

    private void onLevelChanged(String levelCode) {
        if (TextUtils.isEmpty(levelCode)) {
            return;
        }
        String levelName = levelMap.get(levelCode);
        if (TextUtils.isEmpty(levelName)) {
            return;
        }
        mCurrentLevel = levelCode;
        informFollowupResultChanged();
        mLevelTv.setText(levelName);
        getScheduleDate(levelName);
        onDataChanged(levelCode);
    }

    public String getOppLevel() {
        return mCurrentLevel;
    }

    private void informFollowupResultChanged() {
        if (getActivity() instanceof AddFollowupRecordActivity) {
            ((AddFollowupRecordActivity) getActivity()).checkToResetFollowupCalendars();
        }
    }

    /**
     * 根据选择的潜客级别生成计划跟进日期
     *
     * @param level 潜客级别
     */
    private void getScheduleDate(String level) {
        long DAY = 24 * 60 * 60 * 1000;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ", Locale.CHINA);
        Calendar now = Calendar.getInstance();
        long resultDateInMillis = 0;
        Date date;
        String result;
        switch (mFlag) {
            case 0://在新建黄卡第一页
                //黄卡创建时，跟进日期默认为当前日期+1天
                resultDateInMillis = now.getTimeInMillis() + DAY;
                break;
            case NewCustomerConstants.FLAG_OPP_LEVEL://在添加跟进记录页
                if ("H".equals(level)) {
                    resultDateInMillis = now.getTimeInMillis() + DAY * 3;
                    setDateLimitationFlag(true);
                } else if ("A".equals(level)) {
                    resultDateInMillis = now.getTimeInMillis() + DAY * 7;
                    setDateLimitationFlag(true);
                } else if ("B".equals(level)) {
                    resultDateInMillis = now.getTimeInMillis() + DAY * 15;
                    setDateLimitationFlag(true);
                } else if ("C".equals(level)) {
                    resultDateInMillis = now.getTimeInMillis() + DAY * 30;
                    setDateLimitationFlag(true);
                } else if ("N".equals(level)) {
                    resultDateInMillis = now.getTimeInMillis() + DAY * 30;
                    setDateLimitationFlag(false);
                }
                break;
            case NewCustomerConstants.FLAG_FOLLOWUP_REASON://只显示跟进理由时，没有潜客级别，无需处理
                break;
        }
        date = new Date(resultDateInMillis);
        result = sdf.format(date);
        changeScheduleDate(result);
    }

    private void setDateLimitationFlag(boolean hasLimitation) {
        if (getActivity() instanceof AddFollowupRecordActivity) {
            ((AddFollowupRecordActivity) getActivity()).setDateLimitationFlag(hasLimitation);
        }
    }

    /**
     * 改变UI上的计划跟进日期
     *
     * @param scheduleDateStr
     */
    private void changeScheduleDate(String scheduleDateStr) {
        FragmentActivity hostActivity = getActivity();
        if (hostActivity instanceof NewCustomerStepOneActivity) {
            ((NewCustomerStepOneActivity) hostActivity).changeScheduleDate(scheduleDateStr);
        } else if (hostActivity instanceof AddFollowupRecordActivity) {
            ((AddFollowupRecordActivity) hostActivity).changeScheduleDate(scheduleDateStr);
        } else if (hostActivity instanceof NewCustomerStepOneUnitedActivity) {
            ((NewCustomerStepOneUnitedActivity) hostActivity).changeScheduleDate(scheduleDateStr);
            NewCustomerConstants.setFollowupDateStr(scheduleDateStr);
        }
    }

    public void setListeners() {
        mLevelTr.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.new_yellow_card_level_level_tr:
                mLevelPicker.show();
                break;
        }
    }

    public OpportunitySubmitReqEntity getParameters() {
        OpportunitySubmitReqEntity entity = new OpportunitySubmitReqEntity();
        entity.setOppLevel(mCurrentLevel);//潜客级别
        entity.setIsKeyuser(mKeyCustomerCb.isChecked() ? "0" : "1");//是否重点客户
        entity.setFollowupDesc(editTextWithMicLayout.getTextContent());//继续跟进理由
        return entity;
    }

    //对不同的场景判断不同的必填项
    public boolean checkDataValidation() {
        //显示在建卡第一页时，判断潜客级别和跟进理由是否为空
        if (mFlag == 0) {
            return !TextUtils.isEmpty(mCurrentLevel) && !TextUtils.isEmpty(editTextWithMicLayout.getTextContent());
        }
        //显示为潜客级别时，只需判断级别是否为空
        if (mFlag == NewCustomerConstants.FLAG_OPP_LEVEL && TextUtils.isEmpty(mCurrentLevel)) {
            return false;
        }
        //显示为跟进理由时，只需判断跟进理由是否为空
        if (mFlag == NewCustomerConstants.FLAG_FOLLOWUP_REASON && TextUtils.isEmpty(editTextWithMicLayout.getTextContent())) {
            return false;
        }
        return true;
    }

    @Override
    protected void renderView() {
        changeStyleForFollowupReason();
        if (mEntity == null) {
            return;
        }
        OpportunityDetailEntity entity = (OpportunityDetailEntity) mEntity;
        String oppLevelCode = entity.getOppLevel();
        if (!TextUtils.isEmpty(oppLevelCode)) {
            mLevelPicker.setSelectOptions(mLevelCodeList.indexOf(oppLevelCode));
            onLevelChanged(oppLevelCode);
        }
        //当前用户不是该黄卡的owner
        if (!entity.isYellowCardOwner() || entity.isSleepStatus() || entity.isFailedStatus()) {  // 当前用户不是该黄卡的owner或该黄卡处在休眠/战败状态
            editTextWithMicLayout.setText(entity.getFollowupDesc());
//            mFollowUpReasonTv.setVisibility(View.GONE);
            editTextWithMicLayout.setEnabled(false);
        } else {
//            mFollowUpReasonTv.setText(entity.getFollowupDesc());
            editTextWithMicLayout.setText(entity.getFollowupDesc());
//            mFollowUpReasonTv.setVisibility(View.GONE);
            editTextWithMicLayout.setEnabled(true);
        }

    }

    public void setFlag(int flag) {
        mFlag = flag;
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
