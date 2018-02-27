package com.svw.dealerapp.ui.newcustomer.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.bigkoo.pickerview.TimePickerView;
import com.svw.dealerapp.R;
import com.svw.dealerapp.common.BaseFragment;
import com.svw.dealerapp.entity.newcustomer.OpportunitySubmitReqEntity;
import com.svw.dealerapp.global.NewCustomerConstants;
import com.svw.dealerapp.util.JLog;
import com.svw.dealerapp.util.ToastUtils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by xupan on 08/06/2017.
 */
@Deprecated
public class NewCustomerStepOneFollowUpPlanFragment extends BaseFragment implements View.OnClickListener {

    private static final String TAG = "NewCustomerStepOneFollowUpPlanFragment";
    private TableRow mDateTr;
    private TextView mDateTv;
    private TimePickerView mDatePicker;

    private ImageView mDateCloseIv;
    private TableLayout mDateLayout;

    private String mScheduleDateString;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_new_customer_step_1_follow_up_plan, container, false);
        initViews(view);
        initPickerViews();
        setListeners();
        return view;
    }

    private void initViews(View view) {
        mDateTr = (TableRow) view.findViewById(R.id.new_yellow_card_plan_date_tr);
        mDateTv = (TextView) view.findViewById(R.id.new_yellow_card_plan_date_tv);
        mDateCloseIv = (ImageView) view.findViewById(R.id.new_yellow_card_plan_collapse_iv);
        mDateLayout = (TableLayout) view.findViewById(R.id.new_yellow_card_plan_table_layout);
    }

    private void initPickerViews() {
        mDatePicker = new TimePickerView.Builder(getContext(), new TimePickerView.OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {
                mNewDate = date;
                SimpleDateFormat sdfShow = new SimpleDateFormat("yyyy.MM.dd");
                mDateTv.setText(sdfShow.format(date));
                SimpleDateFormat sdfPara = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
                mScheduleDateString = sdfPara.format(date);
                NewCustomerConstants.setFollowupDateStr(mScheduleDateString);
            }
        }).setType(new boolean[]{true, true, true, false, false, false}).build();
    }

    private void setListeners() {
        mDateTr.setOnClickListener(this);
        mDateCloseIv.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.new_yellow_card_plan_date_tr:
                mDatePicker.show();
                break;
            case R.id.new_yellow_card_plan_collapse_iv:
                if (mDateLayout.getVisibility() == View.VISIBLE) {
                    mDateLayout.setVisibility(View.GONE);
                    mDateCloseIv.setImageResource(R.mipmap.new_yellow_card_expand);
                } else {
                    mDateLayout.setVisibility(View.VISIBLE);
                    mDateCloseIv.setImageResource(R.mipmap.new_yellow_card_collapse);
                }
                break;
            default:
                break;
        }
    }

    public OpportunitySubmitReqEntity getParameters() {
        OpportunitySubmitReqEntity entity = new OpportunitySubmitReqEntity();
        entity.setScheduleDateStr(mScheduleDateString);
        return entity;
    }

    public boolean checkDataValidation() {
        if (TextUtils.isEmpty(mScheduleDateString)) {
            return false;
        }
        return true;
    }

    private String mBroughtScheduleDate;
    private Date mNewDate;

    /**
     * 根据潜客级别修改跟进日期
     *
     * @param scheduleDateString
     */
    public void setScheduleDateString(String scheduleDateString) {
        if (TextUtils.isEmpty(scheduleDateString) || !scheduleDateString.contains("T")) {
            return;
        }
        mBroughtScheduleDate = scheduleDateString;
        mDateTv.setText(scheduleDateString.split("T")[0].replace("-", "."));
        mScheduleDateString = scheduleDateString;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ", Locale.CHINA);
        Date date = null;
        try {
            date = sdf.parse(scheduleDateString);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        mDatePicker.setDate(calendar);
    }

    /**
     * 验证新输入的跟进日期是否合法
     *
     * @return true:合法
     */
    public boolean validateDate() {
        if (TextUtils.isEmpty(mBroughtScheduleDate) || mNewDate == null) {
            return true;
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);
        Date date = null;
        try {
            date = sdf.parse(mBroughtScheduleDate);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Calendar broughtCalendar = Calendar.getInstance();
        broughtCalendar.setTime(date);
        JLog.d(TAG, "brought: " + broughtCalendar);
        Calendar newCalender = Calendar.getInstance();
        newCalender.setTime(mNewDate);
        JLog.d(TAG, "new: " + newCalender);
        Calendar nowCalender = Calendar.getInstance();
        nowCalender.set(Calendar.HOUR_OF_DAY, 0);
        nowCalender.set(Calendar.MINUTE, 0);
        nowCalender.set(Calendar.SECOND, 0);
        nowCalender.set(Calendar.MILLISECOND, 0);
        JLog.d(TAG, "now: " + nowCalender);
        if (newCalender.before(nowCalender) || newCalender.after(broughtCalendar)) {
            ToastUtils.showToast(getString(R.string.customer_detail_wrong_date));
            return false;
        }
        return true;

    }
}
