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
import com.svw.dealerapp.ui.newcustomer.activity.AddFollowupRecordActivity;
import com.svw.dealerapp.ui.newcustomer.activity.NewCustomerStepFourActivity;
import com.svw.dealerapp.ui.newcustomer.activity.NewCustomerUnitedActivity;
import com.svw.dealerapp.util.CommonUtils;
import com.svw.dealerapp.util.JLog;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * 新建预约Fragment
 * Created by xupan on 26/05/2017.
 */

public class NewCustomerNewAppointmentFragment extends BaseFragment implements View.OnClickListener {

    private static final String TAG = "NewCustomerNewAppointmentFragment";
    private TableRow mDateTr, mTimeTr, mTypeTr;
    private TextView mDateTv, mTimeTv, mTypeTv;
    private OptionsPickerView mTypePicker;
    private TimePickerView mDatePicker, mTimePicker;
    private List<String> mAppointmentCodeList;
    private List<String> mAppointmentValueList;
    private String mCurrentAppointment;
    private Map<String, String> mParaMap;
    private Date mYearMonthDayDate, mHourMinuteDate;

    public static NewCustomerNewAppointmentFragment newInstance(HashMap<String, String> map) {
        Bundle bundle = new Bundle();
        bundle.putSerializable("key", map);
        NewCustomerNewAppointmentFragment fragment = new NewCustomerNewAppointmentFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            mParaMap = (HashMap<String, String>) bundle.getSerializable("key");
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_new_customer_step_4_appointment, container, false);
        initOptions();
        initViews(view);
        setListeners();
        return view;
    }

    private void initOptions() {
        Iterator<Map.Entry<String, String>> iterator = NewCustomerConstants.appointmentTypeMap.entrySet().iterator();
        mAppointmentCodeList = new ArrayList<>();
        mAppointmentValueList = new ArrayList<>();
        while (iterator.hasNext()) {
            Map.Entry<String, String> entry = iterator.next();
            mAppointmentCodeList.add(entry.getKey());
            mAppointmentValueList.add(entry.getValue());
        }
    }

    private void initViews(View view) {
        mDateTr = (TableRow) view.findViewById(R.id.new_customer_step_4_appointment_date_tr);
        mDateTv = (TextView) view.findViewById(R.id.new_customer_step_4_appointment_date_tv);
        mTimeTr = (TableRow) view.findViewById(R.id.new_customer_step_4_appointment_time_tr);
        mTimeTv = (TextView) view.findViewById(R.id.new_customer_step_4_appointment_time_tv);
        mTypeTr = (TableRow) view.findViewById(R.id.new_customer_step_4_appointment_type_tr);
        mTypeTv = (TextView) view.findViewById(R.id.new_customer_step_4_appointment_type_tv);
        initPickerView();
    }

    private void initPickerView() {
        mTypePicker = new OptionsPickerView.Builder(getContext(), new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                mTypeTv.setText(mAppointmentValueList.get(options1));
                mCurrentAppointment = mAppointmentCodeList.get(options1);
                checkIfAllSet();
                checkIfCanEnableRightText();
            }
        }).build();
        mTypePicker.setNPicker(mAppointmentValueList, null, null);

        Calendar now = Calendar.getInstance();
        mDatePicker = new TimePickerView.Builder(getContext(), new TimePickerView.OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {
                mYearMonthDayDate = date;
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                String chosenDate = simpleDateFormat.format(date);
                mDateTv.setText(chosenDate);
                checkIfAllSet();
                checkIfCanEnableRightText();
            }
        }).setType(new boolean[]{true, true, true, false, false, false}).setRangDate(now, null).build();

        mTimePicker = new TimePickerView.Builder(getContext(), new TimePickerView.OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {
                mHourMinuteDate = date;
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");
                String chosenDate = simpleDateFormat.format(date);
                mTimeTv.setText(chosenDate);
                checkIfAllSet();
                checkIfCanEnableRightText();
            }
        }).setType(new boolean[]{false, false, false, true, true, false}).build();
    }

    private void setListeners() {
        mDateTr.setOnClickListener(this);
        mTimeTr.setOnClickListener(this);
        mTypeTr.setOnClickListener(this);
        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.setEnabled(false);
                clearSelection();
                checkIfAllSet();
            }
        };
        if (getActivity() instanceof NewCustomerStepFourActivity) {
            ((NewCustomerStepFourActivity) getActivity()).setCustomSectionRightTextListener(listener);
        } else if (getActivity() instanceof AddFollowupRecordActivity) {
            ((AddFollowupRecordActivity) getActivity()).setCustomSectionRightTextListener(listener);
        }
    }

    public void clearSelection() {
        mTypeTv.setText("");
        mDateTv.setText("");
        mTimeTv.setText("");
        mDatePicker.setDate(Calendar.getInstance());
        mTimePicker.setDate(Calendar.getInstance());
        mTypePicker.setSelectOptions(0);
        mYearMonthDayDate = null;
        mHourMinuteDate = null;
        mCurrentAppointment = "";
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.new_customer_step_4_appointment_date_tr:
                mDatePicker.show();
                break;
            case R.id.new_customer_step_4_appointment_time_tr:
                mTimePicker.show();
                break;
            case R.id.new_customer_step_4_appointment_type_tr:
                mTypePicker.show();
                break;
        }
    }

    private String generateDateStr() {
        if (mYearMonthDayDate == null || mHourMinuteDate == null) {
            return "";
        }
        mYearMonthDayDate.setHours(mHourMinuteDate.getHours());
        mYearMonthDayDate.setMinutes(mHourMinuteDate.getMinutes());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
        return sdf.format(mYearMonthDayDate);
    }

    //新建预约要么全填，要么全不填
    public boolean checkDataValidation() {
        //合并后的建卡页，必须全填
        if (getActivity() instanceof NewCustomerUnitedActivity) {
            return mYearMonthDayDate != null && mHourMinuteDate != null && !TextUtils.isEmpty(mCurrentAppointment);
        }
        if (mYearMonthDayDate == null && mHourMinuteDate == null && TextUtils.isEmpty(mCurrentAppointment)) {
            return true;
        }
        if (mYearMonthDayDate != null && mHourMinuteDate != null && !TextUtils.isEmpty(mCurrentAppointment)) {
            return true;
        }
        return false;
    }

    private void checkIfCanEnableRightText() {
        boolean result = mYearMonthDayDate != null || mHourMinuteDate != null
                || !TextUtils.isEmpty(mCurrentAppointment);
        if (getActivity() instanceof NewCustomerStepFourActivity) {
            ((NewCustomerStepFourActivity) getActivity()).enableCustomSectionRightText(result);
        } else if (getActivity() instanceof AddFollowupRecordActivity) {
            ((AddFollowupRecordActivity) getActivity()).enableCustomSectionRightText(result);
        }
    }

    /**
     * 验证预约时间是否合法
     *
     * @return true:合法
     */
    public boolean validateDate() {
        String dateStr = generateDateStr();
        //为空则说明用户没有输入日期和时间，则不需要进行下一步的合法性验证
        if (TextUtils.isEmpty(dateStr)) {
            return true;
        }
        return !CommonUtils.isBeforeNow(generateDateStr());
    }

    public FollowupCreateReqEntity getParameters() {
        FollowupCreateReqEntity entity = new FollowupCreateReqEntity();
        entity.setAppmDateStr(generateDateStr());
        entity.setAppmTypeId(mCurrentAppointment);
        return entity;
    }

    private void checkIfAllSet() {
        if (getActivity() instanceof NewCustomerStepFourActivity) {
            ((NewCustomerStepFourActivity) getActivity()).checkIfNeedToEnableSubmit();
        } else if (getActivity() instanceof AddFollowupRecordActivity) {
            ((AddFollowupRecordActivity) getActivity()).checkIfNeedToEnableSubmit();
        } else if (getActivity() instanceof NewCustomerUnitedActivity) {
            ((NewCustomerUnitedActivity) getActivity()).checkIfNeedToEnableSubmit();
        }
    }
}
