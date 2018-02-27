package com.svw.dealerapp.ui.newcustomer.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableRow;
import android.widget.TextView;

import com.bigkoo.pickerview.OptionsPickerView;
import com.bigkoo.pickerview.TimePickerView;
import com.svw.dealerapp.R;
import com.svw.dealerapp.common.BaseFragment;
import com.svw.dealerapp.entity.newcustomer.FollowupCreateReqEntity;
import com.svw.dealerapp.global.NewCustomerConstants;
import com.svw.dealerapp.ui.newcustomer.activity.ActivateYellowCardActivity;
import com.svw.dealerapp.ui.newcustomer.activity.AddFollowupRecordActivity;
import com.svw.dealerapp.ui.newcustomer.activity.NewCustomerStepFourActivity;
import com.svw.dealerapp.ui.newcustomer.activity.NewCustomerUnitedActivity;
import com.svw.dealerapp.ui.widget.EditTextWithMicLayout;
import com.svw.dealerapp.util.JLog;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * 新潜客/黄卡详情页-下次跟进计划Fragment
 * Created by xupan on 25/05/2017.
 */

@Deprecated
public class NewCustomerFollowUpPlanFragment extends BaseFragment implements View.OnClickListener {

    private static final String TAG = "NewCustomerFollowUpPlanFragment";
    private TableRow mDateTr, mTypeTr;
    private TextView mDateTv, mTypeTv;

    private List<String> mContactMethodCodeList, mContactMethodValueList;
    private OptionsPickerView mContactMethodPicker;
    private TimePickerView mDatePicker;
    private Map<String, String> mParaMap;
    private String mScheduleDateString, mCurrentFollowupMethod;

    //    private EditText mFollowupPlanEt;
    private Date mNewDate;

    private EditTextWithMicLayout etwmlFollowupPlan;

    public static NewCustomerFollowUpPlanFragment newInstance(HashMap<String, String> map) {
        Bundle bundle = new Bundle();
        bundle.putSerializable("key", map);
        NewCustomerFollowUpPlanFragment fragment = new NewCustomerFollowUpPlanFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            mParaMap = (HashMap<String, String>) bundle.get("key");
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_new_customer_step_4_follow_up_plan, container, false);
        initOptions();
        initViews(view);
        initFollowupDate();
        setListeners();
        return view;
    }

    private void initViews(View view) {
        mDateTr = (TableRow) view.findViewById(R.id.new_customer_step_4_followup_date_tr);
        mTypeTr = (TableRow) view.findViewById(R.id.new_customer_step_4_followup_type_tr);
        mDateTv = (TextView) view.findViewById(R.id.new_customer_step_4_followup_date_tv);
        mTypeTv = (TextView) view.findViewById(R.id.new_customer_step_4_followup_type_tv);
//        mFollowupPlanEt = (EditText) view.findViewById(R.id.new_customer_step_4_followup_plan_et);
        initPickerView();

        etwmlFollowupPlan = (EditTextWithMicLayout) view.findViewById(R.id.et_with_mic1);
        etwmlFollowupPlan.initEditWithMic(getActivity(), NewCustomerFollowUpPlanFragment.this);
        etwmlFollowupPlan.setMaxTextNum(50);
        etwmlFollowupPlan.setEnabled(true);
        etwmlFollowupPlan.setHint(getResources().getString(R.string.new_customer_next_followup_contact_plan_hint));
    }

    private void initPickerView() {
        mContactMethodPicker = new OptionsPickerView.Builder(getContext(), new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                mCurrentFollowupMethod = mContactMethodCodeList.get(options1);
                mTypeTv.setText(mContactMethodValueList.get(options1));
                checkIfAllSet();
            }
        }).build();
        mContactMethodPicker.setNPicker(mContactMethodValueList, null, null);

        mDatePicker = new TimePickerView.Builder(getContext(), new TimePickerView.OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                String chosenDate = simpleDateFormat.format(date);
                mNewDate = date;
                mDateTv.setText(chosenDate);
                SimpleDateFormat sdfPara = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
                mScheduleDateString = sdfPara.format(date);
                checkIfAllSet();
            }
        }).setType(new boolean[]{true, true, true, false, false, false}).build();
    }

    private void initOptions() {
//        Iterator<Map.Entry<String, String>> iterator = NewCustomerConstants.followupMethodMap.entrySet().iterator();
        Iterator<Map.Entry<String, String>> iterator = NewCustomerConstants.contactMethodMap.entrySet().iterator();
        mContactMethodCodeList = new ArrayList<>();
        mContactMethodValueList = new ArrayList<>();
        while (iterator.hasNext()) {
            Map.Entry<String, String> entry = iterator.next();
            mContactMethodCodeList.add(entry.getKey());
            mContactMethodValueList.add(entry.getValue());
        }
    }

    private void initFollowupDate() {
        //只在新潜客第四页才显示第一页预设的值
        if (getActivity() instanceof NewCustomerStepFourActivity) {
            String followupDateStr = NewCustomerConstants.getFollowupDateStr();
            setScheduleDateString(followupDateStr);
        }
    }

    private void setListeners() {
        mDateTr.setOnClickListener(this);
        mTypeTr.setOnClickListener(this);
        etwmlFollowupPlan.setOnTextChangeListener(new EditTextWithMicLayout.OnTextChangeListener() {
            @Override
            public void onAfterTextChange(CharSequence s) {
                checkIfAllSet();
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.new_customer_step_4_followup_date_tr:
                mDatePicker.show();
                break;
            case R.id.new_customer_step_4_followup_type_tr:
                mContactMethodPicker.show();
                break;
            default:
                break;
        }
    }

    public boolean checkDataValidation() {
        //检查跟进时间是否为空
        if (TextUtils.isEmpty(mScheduleDateString)) {
            return false;
        }
        if (TextUtils.isEmpty(etwmlFollowupPlan.getText())) {
            return false;
        }
        if (TextUtils.isEmpty(mCurrentFollowupMethod)) {
            return false;
        }
        return true;
    }

    public FollowupCreateReqEntity getParameters() {
        FollowupCreateReqEntity entity = new FollowupCreateReqEntity();
        entity.setScheduleDateStr(mScheduleDateString);
//        entity.setScheduleDesc(mFollowupPlanEt.getText().toString().trim());
        entity.setScheduleDesc(etwmlFollowupPlan.getTextContent());
        entity.setNextModeId(mCurrentFollowupMethod);
        return entity;
    }

    private String mBroughtScheduleDate;

    public void setScheduleDateString(String scheduleDateString) {
        if (TextUtils.isEmpty(scheduleDateString) || !scheduleDateString.contains("T")) {
            return;
        }
        mBroughtScheduleDate = scheduleDateString;
        mDateTv.setText(scheduleDateString.split("T")[0]);
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
        newCalender.set(Calendar.HOUR_OF_DAY, 0);
        newCalender.set(Calendar.MINUTE, 0);
        newCalender.set(Calendar.SECOND, 0);
        newCalender.set(Calendar.MILLISECOND, 0);
        JLog.d(TAG, "new: " + newCalender);
        Calendar nowCalender = Calendar.getInstance();
        nowCalender.set(Calendar.HOUR_OF_DAY, 0);
        nowCalender.set(Calendar.MINUTE, 0);
        nowCalender.set(Calendar.SECOND, 0);
        nowCalender.set(Calendar.MILLISECOND, 0);
        JLog.d(TAG, "now: " + nowCalender);
        if (newCalender.before(nowCalender) || newCalender.after(broughtCalendar)) {
            return false;
        }
        return true;
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
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        etwmlFollowupPlan.stopNLS();
    }

}
