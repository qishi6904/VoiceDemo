package com.svw.dealerapp.ui.newcustomer.fragment;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import com.svw.dealerapp.R;
import com.svw.dealerapp.entity.newcustomer.FollowupCreateReqEntity;
import com.svw.dealerapp.global.NewCustomerConstants;
import com.svw.dealerapp.ui.adapter.RdRecyclerViewAdapter;
import com.svw.dealerapp.ui.newcustomer.activity.ActivateYellowCardActivity;
import com.svw.dealerapp.ui.newcustomer.activity.AddFollowupRecordActivity;
import com.svw.dealerapp.ui.newcustomer.activity.NewCustomerStepFourActivity;
import com.svw.dealerapp.ui.newcustomer.activity.NewCustomerUnitedActivity;
import com.svw.dealerapp.ui.order.activity.OrderCancelActivity2;
import com.svw.dealerapp.ui.widget.RdCustomItemViewBase;
import com.svw.dealerapp.ui.widget.RdCustomItemViewForDatePicker;
import com.svw.dealerapp.ui.widget.RdCustomItemViewForEditTextWithMic;
import com.svw.dealerapp.ui.widget.RdCustomItemViewForRecycleView;
import com.svw.dealerapp.util.DateUtils;

import java.io.Serializable;
import java.util.Calendar;

/**
 * 跟进计划(建卡和跟进页)
 * Created by xupan on 2017/11/09.
 */

public class RdNewCustomerFollowUpPlanFragment2 extends RdBaseCustomerFragment {

    private RdCustomItemViewForDatePicker mDateView;
    private RdCustomItemViewForEditTextWithMic mPlanView;
    //    private RdCustomItemViewForOptionsPicker mMethodView;
    private RdCustomItemViewForRecycleView mMethodView;

    private boolean[] mPickerType = new boolean[]{true, true, true, false, false, false};

    public static RdNewCustomerFollowUpPlanFragment2 newInstance(Serializable entity) {
        RdNewCustomerFollowUpPlanFragment2 fragment = new RdNewCustomerFollowUpPlanFragment2();
        Bundle bundle = new Bundle();
        bundle.putSerializable("entity", entity);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.rd_fragment_new_customer_followup_plan;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mPlanView.stopNLS();
    }

    @Override
    protected void initViews(View view) {
        mDateView = (RdCustomItemViewForDatePicker) view.findViewById(R.id.new_customer_followup_plan_date_picker);
        mDateView.setMandatory(true);
        mDateView.setTitleText(R.string.new_customer_next_followup_date);
        mDateView.setHintTextForContentView(R.string.new_customer_next_followup_date_hint);
        mDateView.setDateFormatStr("yyyy-MM-dd");
        mDateView.setDateFormatStrForParameter("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
        mDateView.initPicker(new boolean[]{true, true, true, false, false, false}, Calendar.getInstance(), null);
        mPlanView = (RdCustomItemViewForEditTextWithMic) view.findViewById(R.id.new_customer_followup_plan_plan_view);
        mPlanView.setMandatory(true);
        mPlanView.setTitleText(R.string.new_customer_next_followup_contact_plan);
        mPlanView.initMicEditText(getActivity(), this);
        mPlanView.setMaxTextNum(50);
        mPlanView.setEnabled(true);
        mPlanView.setHint(R.string.new_customer_next_followup_contact_plan_hint);
        mMethodView = (RdCustomItemViewForRecycleView) view.findViewById(R.id.new_customer_followup_plan_method_view);
        mMethodView.setMandatory(true);
        mMethodView.setTitleText(R.string.new_customer_next_followup_contact_method);
        mMethodView.initAdapter(NewCustomerConstants.contactMethodMap, RdRecyclerViewAdapter.MODE_SINGLE);
    }

    @Override
    protected void initBaseViews() {
        super.initBaseViews();
        mAllBaseView = new RdCustomItemViewBase[]{mDateView, mPlanView, mMethodView};
    }

    @Override
    protected void renderView() {

    }

    public FollowupCreateReqEntity getParameters() {
        FollowupCreateReqEntity entity = new FollowupCreateReqEntity();
        entity.setScheduleDateStr(mDateView.getInputData());
        entity.setScheduleDesc(mPlanView.getInputData());
        entity.setNextModeId(mMethodView.getInputData().get(0));
        return entity;
    }

    @Override
    protected void onDataChanged(Object object) {
        super.onDataChanged(object);
        checkIfCanEnableRightText();
        checkIfAllSet();
    }

    private void checkIfAllSet() {
        if (getActivity() instanceof NewCustomerStepFourActivity) {
            ((NewCustomerStepFourActivity) getActivity()).checkIfNeedToEnableSubmit();
        } else if (getActivity() instanceof NewCustomerUnitedActivity) {
            ((NewCustomerUnitedActivity) getActivity()).checkIfNeedToEnableSubmit();
        } else if (getActivity() instanceof AddFollowupRecordActivity) {
            ((AddFollowupRecordActivity) getActivity()).checkIfNeedToEnableSubmit();
        } else if (getActivity() instanceof ActivateYellowCardActivity) {
            ((ActivateYellowCardActivity) getActivity()).checkIfNeedToEnableSubmit();
        } else if (getActivity() instanceof OrderCancelActivity2) {
            ((OrderCancelActivity2) getActivity()).checkIfNeedToEnableSubmit();
        }
    }

    private void checkIfCanEnableRightText() {
        boolean enabled = !TextUtils.isEmpty(mDateView.getInputData())
                || !TextUtils.isEmpty(mPlanView.getInputData())
                || !mMethodView.getInputData().isEmpty();
        if (getActivity() instanceof AddFollowupRecordActivity) {
            ((AddFollowupRecordActivity) getActivity()).enableFollowupPlanRightText(enabled);
        }
    }

    private boolean checkIfAllEmpty() {
        return TextUtils.isEmpty(mDateView.getInputData())
                && TextUtils.isEmpty(mPlanView.getInputData())
                && mMethodView.getInputData().isEmpty();
    }

    @Override
    public boolean checkDataValidation() {
        if ((getActivity() instanceof AddFollowupRecordActivity) &&
                ((AddFollowupRecordActivity) getActivity()).checkIfFollowupPlanOptional()) {
            return super.checkDataValidation() || checkIfAllEmpty();
        } else {
            return super.checkDataValidation();
        }
    }

    /**
     * 设置需要显示的日期与当前日期的天数间隔
     *
     * @param delayOfDefault 默认显示日期与当前日期的天数间隔
     * @param delayOfStart   开始日期与当前日期的天数间隔
     * @param delayOfEnd     结束日期与当前日期的天数间隔
     */
    public void setCalendarDelay(int delayOfDefault, int delayOfStart, int delayOfEnd) {
        Calendar now = Calendar.getInstance();
        Calendar defaultCal = DateUtils.addDay(now, delayOfDefault);
        Calendar startCal = DateUtils.addDay(now, delayOfStart);
        Calendar endCal = DateUtils.addDay(now, delayOfEnd);
        mDateView.initPicker(mPickerType, startCal, endCal, defaultCal);
    }

    public void clearSelection() {
        mDateView.clearSelection();
        mPlanView.clearSelection();
        mMethodView.clearSelection();
        if (mPlanView.hasFocus()) {
            mPlanView.clearFocus();
        }
    }

    public void clearFollowupDateSelection() {
        mDateView.clearSelection();
    }

}
