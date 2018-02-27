package com.svw.dealerapp.ui.newcustomer.fragment;

import android.os.Bundle;
import android.view.View;

import com.svw.dealerapp.R;
import com.svw.dealerapp.entity.newcustomer.FollowupCreateReqEntity;
import com.svw.dealerapp.entity.newcustomer.OpportunitySubmitReqEntityV2;
import com.svw.dealerapp.ui.newcustomer.activity.NewCustomerUnitedActivity;
import com.svw.dealerapp.ui.widget.CustomItemViewBase;
import com.svw.dealerapp.ui.widget.CustomItemViewForCheckbox;
import com.svw.dealerapp.util.ToastUtils;

import java.io.Serializable;

/**
 * 合卡页的下次跟进计划Fragment（包括跟进计划和新建预约）
 * Created by xupan on 26/09/2017.
 */

public class NewCustomerUnitedNextFollowupPlanFragment extends BaseCustomerFragment {

    private CustomItemViewForCheckbox mAppointmentAdderView;
//    private NewCustomerFollowUpPlanFragment mFollowupPlanFragment;
    private NewCustomerFollowUpPlanFragment2 mFollowupPlanFragment2;
    private NewCustomerNewAppointmentFragment mAppointmentFragment;

    public static NewCustomerUnitedNextFollowupPlanFragment newInstance(Serializable entity) {
        NewCustomerUnitedNextFollowupPlanFragment fragment = new NewCustomerUnitedNextFollowupPlanFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("entity", entity);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_new_customer_next_followup_plan_united;
    }

    @Override
    protected void initViews(View view) {
        mAppointmentAdderView = (CustomItemViewForCheckbox) view
                .findViewById(R.id.new_customer_next_followup_plan_appointment_checkbox);
        mAppointmentAdderView.setTitleText("是否添加预约");
//        mFollowupPlanFragment = NewCustomerFollowUpPlanFragment.newInstance(null);
        mFollowupPlanFragment2 = NewCustomerFollowUpPlanFragment2.newInstance(null);
        mAppointmentFragment = NewCustomerNewAppointmentFragment.newInstance(null);
        getChildFragmentManager().beginTransaction()
//                .add(R.id.new_customer_next_followup_plan_followup_container, mFollowupPlanFragment)
                .add(R.id.new_customer_next_followup_plan_followup_container, mFollowupPlanFragment2)
                .add(R.id.new_customer_next_followup_plan_appointment_container, mAppointmentFragment)
                .hide(mAppointmentFragment)
                .commit();
    }

    @Override
    protected void setListeners() {
        mAppointmentAdderView.setOnDataChangedListener(new CustomItemViewBase.OnDataChangedListener() {
            @Override
            public void onDateChanged(Object object) {
                boolean checked = (Boolean) object;
                if (checked) {
                    getChildFragmentManager().beginTransaction()
                            .show(mAppointmentFragment)
                            .commit();
                } else {
                    getChildFragmentManager().beginTransaction().hide(mAppointmentFragment).commit();
                    mAppointmentFragment.clearSelection();
                }
                askActivityToCheckValidation();
            }
        });
    }

    @Override
    protected void renderView() {

    }

    @Override
    public OpportunitySubmitReqEntityV2 getParameters() {
        OpportunitySubmitReqEntityV2 entityV2 = new OpportunitySubmitReqEntityV2();
//        FollowupCreateReqEntity entityFromFollowupPlan = mFollowupPlanFragment.getParameters();
        FollowupCreateReqEntity entityFromFollowupPlan = mFollowupPlanFragment2.getParameters();
        entityV2.setScheduleDateStr(entityFromFollowupPlan.getScheduleDateStr());
        entityV2.setScheduleDesc(entityFromFollowupPlan.getScheduleDesc());
        entityV2.setNextModeId(entityFromFollowupPlan.getNextModeId());
        if (mAppointmentAdderView.getInputData()) {
            FollowupCreateReqEntity entityFromAppointment = mAppointmentFragment.getParameters();
            entityV2.setAppmDateStr(entityFromAppointment.getAppmDateStr());
            entityV2.setAppmTypeId(entityFromAppointment.getAppmTypeId());
        }
        return entityV2;
    }

    @Override
    public boolean checkDataValidation() {
        if (mAppointmentAdderView.getInputData()) {
            return mFollowupPlanFragment2.checkDataValidation() && mAppointmentFragment.checkDataValidation();
        } else {
            return mFollowupPlanFragment2.checkDataValidation();
        }
    }

    private void askActivityToCheckValidation() {
        if (getActivity() instanceof NewCustomerUnitedActivity) {
            ((NewCustomerUnitedActivity) getActivity()).checkIfNeedToEnableSubmit();
        }
    }

//    public void changeScheduleDate(String result) {
//        mFollowupPlanFragment.setScheduleDateString(result);
//    }

    public void setFollowupCalendars(int delayOfDefault, int delayOfStart, int delayOfEnd) {
        mFollowupPlanFragment2.setCalendarDelay(delayOfDefault, delayOfStart, delayOfEnd);
    }

    public boolean validateDate() {
//        if (!mFollowupPlanFragment.validateDate()) {
//            ToastUtils.showToast(getString(R.string.customer_detail_wrong_date));
//            return false;
//        }
        if (!mAppointmentFragment.validateDate()) {
            ToastUtils.showToast(getString(R.string.add_followup_record_invalid_appointment_time));
            return false;
        }
        return true;
    }
}
