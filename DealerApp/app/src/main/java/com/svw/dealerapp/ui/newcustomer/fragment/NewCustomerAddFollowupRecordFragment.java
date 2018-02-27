package com.svw.dealerapp.ui.newcustomer.fragment;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TableRow;
import android.widget.TextView;

import com.bigkoo.pickerview.OptionsPickerView;
import com.svw.dealerapp.DealerApp;
import com.svw.dealerapp.R;
import com.svw.dealerapp.common.BaseFragment;
import com.svw.dealerapp.entity.newcustomer.FollowupCreateReqEntity;
import com.svw.dealerapp.global.NewCustomerConstants;
import com.svw.dealerapp.ui.newcustomer.GridSpacingItemDecoration;
import com.svw.dealerapp.ui.newcustomer.activity.ActivateYellowCardActivity;
import com.svw.dealerapp.ui.newcustomer.activity.AddFollowupRecordActivity;
import com.svw.dealerapp.ui.newcustomer.activity.NewCustomerStepThreeActivity;
import com.svw.dealerapp.ui.newcustomer.activity.NewCustomerUnitedActivity;
import com.svw.dealerapp.ui.order.activity.OrderCancelActivity2;
import com.svw.dealerapp.ui.resource.fragment.DealCustomerFragment;
import com.svw.dealerapp.ui.resource.fragment.FailedCustomerFragment;
import com.svw.dealerapp.ui.resource.fragment.OrderCustomerFragment;
import com.svw.dealerapp.ui.resource.fragment.SleepCustomerFragment;
import com.svw.dealerapp.ui.resource.fragment.YellowCardFragment;
import com.svw.dealerapp.ui.widget.CustomItemViewBase;
import com.svw.dealerapp.ui.widget.CustomItemViewForCheckbox;
import com.svw.dealerapp.ui.widget.CustomItemViewForDatePicker;
import com.svw.dealerapp.ui.widget.CustomItemViewForEditTextWithMic;
import com.svw.dealerapp.ui.widget.CustomItemViewForOptionsPicker;
import com.svw.dealerapp.ui.widget.CustomTimePickerView;
import com.svw.dealerapp.ui.widget.EditTextWithMicLayout;
import com.svw.dealerapp.util.CommonUtils;
import com.svw.dealerapp.util.dbtools.DBHelper;
import com.svw.dealerapp.util.dbtools.DBUtils;
import com.svw.dealerapp.util.DateUtils;
import com.svw.dealerapp.util.JLog;
import com.svw.dealerapp.util.PermissionUtils;
import com.svw.dealerapp.util.ToastUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;
import static com.svw.dealerapp.global.NewCustomerConstants.contactResultMultiChoicesMap;
import static com.svw.dealerapp.global.NewCustomerConstants.contactResultSingleChoiceMap;

/**
 * 添加跟进记录Fragment
 * Created by xupan on 05/06/2017.
 */

public class NewCustomerAddFollowupRecordFragment extends BaseFragment implements View.OnClickListener {

    private static final String TAG = "NewCustomerAddFollowupRecordFragment";
    private Map<String, String> mParaMap;

    private TableRow mContactMethodTr, mFailureReasonTr, mAsleepReasonTr, mContactResultTr, mContactResultMultiTr;
    private TextView mContactMethodTv, mFailureReasonTv, mAsleepReasonTv, mContactResultTv;

    private List<String> mContactMethodCodeList, mFailureReasonCodeList, mAsleepReasonCodeList, mContactResultCodeList;
    private List<String> mContactMethodValueList, mFailureReasonValueList, mAsleepReasonValueList, mContactResultValueList;

    private OptionsPickerView mContactMethodPicker, mSingleContactResultPicker, mFailureReasonPicker, mAsleepReasonPicker;
    private CustomTimePickerView mSleepFinishTimePicker;
    private String mCurrentContactMethodCode, mCurrentContactMethodValue, mSingleContactResultCode,
            mSingleContactResultValue, mCurrentFailureReason, mCurrentAsleepReason, mSleepFinishTime;

    private RecyclerView mContactResultRv;
    private TableRow mReasonTr;
    private TableRow mSleepFinishTimeTr;
    private TextView mSleepFinishTimeTv;
    private View mContactResultTrLine, mResultRecyclerViewLine, mFailureAsleepLine, mFinishTimeLine;
    private GridAdapter mGridAdapter;

//    private String mOrderStatus;

    private EditTextWithMicLayout editTextWithMicLayoutReason;
    private EditTextWithMicLayout editTextWithMicLayoutRemark;

    private String mOppStatus = "11500";//默认潜客状态为“新”
    private CustomItemViewForOptionsPicker mContactMethodView;
    private CustomItemViewForOptionsPicker mContactResultView;
    private CustomItemViewForOptionsPicker mFailureReasonView;
    private CustomItemViewForOptionsPicker mFailureCompetitorsSeriesView;//竞品车系Picker
    private CustomItemViewForEditTextWithMic mFailureCompetitorsSeriesOther;//竞品车系选择其他后出现的输入框
    private CustomItemViewForOptionsPicker mAsleepReasonView;
    private CustomItemViewForDatePicker mAsleepTimeView;
    private CustomItemViewForCheckbox mTrialView;
    private Map<String, String> mContactResultsMap;
    private boolean mFirstFollowUp;//是否首次跟进
    private String mCurrentFollowupResult;//当前选择的跟进结果
    private String mSeriesId;
    private String mIsFrom;//0:取消订单
    private boolean mEnabled = true;//设置picker是否可以点击

    public static NewCustomerAddFollowupRecordFragment newInstance(HashMap<String, String> map) {
        Bundle bundle = new Bundle();
        bundle.putSerializable("key", map);
        NewCustomerAddFollowupRecordFragment fragment = new NewCustomerAddFollowupRecordFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (getActivity() instanceof NewCustomerUnitedActivity) {
            mFirstFollowUp = true;
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            mParaMap = (HashMap<String, String>) bundle.get("key");
            JLog.d(TAG, "" + mParaMap);
            if (mParaMap != null) {
//                if (!TextUtils.isEmpty(mParaMap.get("orderStatus"))) {
//                    mOrderStatus = mParaMap.get("orderStatus");
//                }
                if (!TextUtils.isEmpty(mParaMap.get("oppStatus"))) {
                    mOppStatus = mParaMap.get("oppStatus");
                }
                if (!TextUtils.isEmpty(mParaMap.get("seriesId"))) {
                    mSeriesId = mParaMap.get("seriesId");
                }
                if (!TextUtils.isEmpty(mParaMap.get("isFrom"))) {
                    mIsFrom = mParaMap.get("isFrom");
                }
            }
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_new_customer_step_3_follow_up, container, false);
        initViews(view);
        initOptions();
        initPickerViews();
        setListeners();
//        onContactMethodOptionsSelected(0);//联络方式默认选择展厅
        onContactMethodOptionsSelected("15010");//联络方式默认选择展厅
        mContactMethodView.setData("15010");
        return view;
    }

    private void initViews(View view) {
        mContactMethodTr = (TableRow) view.findViewById(R.id.new_customer_step_3_contact_method_tr);
        mContactMethodTv = (TextView) view.findViewById(R.id.new_customer_step_3_contact_method_tv);
        mContactResultMultiTr = (TableRow) view.findViewById(R.id.new_customer_step_3_contact_result_multi_tr);
        mContactResultRv = (RecyclerView) view.findViewById(R.id.new_customer_step_3_contact_result_rv);
        mContactResultTr = (TableRow) view.findViewById(R.id.new_customer_step_3_contact_result_tr);
        mContactResultTv = (TextView) view.findViewById(R.id.new_customer_step_3_contact_result_tv);
        mSleepFinishTimeTr = (TableRow) view.findViewById(R.id.new_customer_step_3_failure_time_tr);
        mFinishTimeLine = view.findViewById(R.id.new_customer_step_3_time_asleep_line);
        mSleepFinishTimeTv = (TextView) view.findViewById(R.id.new_customer_step_3_failure_finish_time);
        mReasonTr = (TableRow) view.findViewById(R.id.new_customer_step_3_reason_tr);

        mFailureReasonTr = (TableRow) view.findViewById(R.id.new_customer_step_3_failure_reason_tr);
        mFailureReasonTv = (TextView) view.findViewById(R.id.new_customer_step_3_failure_reason_tv);
        mAsleepReasonTr = (TableRow) view.findViewById(R.id.new_customer_step_3_asleep_reason_tr);
        mAsleepReasonTv = (TextView) view.findViewById(R.id.new_customer_step_3_asleep_reason_tv);

        mContactResultTrLine = view.findViewById(R.id.new_customer_step_3_contact_result_tr_line);
        mResultRecyclerViewLine = view.findViewById(R.id.new_customer_step_3_contact_result_rv_line);
        mFailureAsleepLine = view.findViewById(R.id.new_customer_step_3_failure_asleep_line);

        editTextWithMicLayoutReason = (EditTextWithMicLayout) view.findViewById(R.id.et_with_mic1);
        editTextWithMicLayoutReason.initEditWithMic(getActivity(), NewCustomerAddFollowupRecordFragment.this);
        editTextWithMicLayoutReason.setMaxTextNum(50);
        editTextWithMicLayoutReason.setEnabled(true);
        editTextWithMicLayoutReason.setHint(getResources().getString(R.string.new_customer_other_reason));

        editTextWithMicLayoutRemark = (EditTextWithMicLayout) view.findViewById(R.id.et_with_mic2);
        editTextWithMicLayoutRemark.initEditWithMic(getActivity(), NewCustomerAddFollowupRecordFragment.this);
        editTextWithMicLayoutRemark.setMaxTextNum(50);
        editTextWithMicLayoutRemark.setEnabled(true);
        editTextWithMicLayoutRemark.setHint(getResources().getString(R.string.new_customer_remark_detail_hint));

        mContactMethodView = (CustomItemViewForOptionsPicker) view.findViewById(R.id.new_customer_step_3_contact_method_view);
        mContactMethodView.setMandatory(true);
        mContactMethodView.setTitleText(R.string.new_customer_contact_method);
        mContactMethodView.setHintTextForContentView(R.string.new_customer_contact_method_hint);
        mContactMethodView.setEnabled(mEnabled);

        mContactResultView = (CustomItemViewForOptionsPicker) view.findViewById(R.id.new_customer_step_3_contact_result_view);
        mContactResultView.setMandatory(true);
        mContactResultView.setTitleText(R.string.new_customer_contact_result);
        mContactResultView.setHintTextForContentView(R.string.new_customer_contact_result_hint);

        mFailureReasonView = (CustomItemViewForOptionsPicker) view.findViewById(R.id.new_customer_step_3_failure_reason_view);
        mFailureReasonView.setMandatory(true);
        mFailureReasonView.setTitleText(R.string.new_customer_failure_result_title);
        mFailureReasonView.setHintTextForContentView(R.string.new_customer_failure_result_hint);

        mAsleepReasonView = (CustomItemViewForOptionsPicker) view.findViewById(R.id.new_customer_step_3_asleep_reason_view);
        mAsleepReasonView.setMandatory(true);
        mAsleepReasonView.setTitleText(R.string.new_customer_asleep_result_title);
        mAsleepReasonView.setHintTextForContentView(R.string.new_customer_asleep_result_hint);

        mAsleepTimeView = (CustomItemViewForDatePicker) view.findViewById(R.id.new_customer_step_3_asleep_time_view);
        mAsleepTimeView.setMandatory(true);
        mAsleepTimeView.setTitleText(R.string.new_customer_asleep_finish_time);
        mAsleepTimeView.setHintTextForContentView(R.string.new_customer_asleep_finish_time_hint);
        mAsleepTimeView.setDateFormatStr("yyyy.MM.dd");
        mAsleepTimeView.setDateFormatStrForParameter("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
        Calendar now = Calendar.getInstance();
        Calendar end = DateUtils.addDay(now, 365);
        Calendar def = DateUtils.addDay(now, 90);
        mAsleepTimeView.initPicker(new boolean[]{true, true, true, false, false, false}, now, end, def);

        mFailureCompetitorsSeriesView = (CustomItemViewForOptionsPicker) view.findViewById(R.id.new_customer_step_3_competitors_series_picker);
        mFailureCompetitorsSeriesView.setMandatory(true);
        mFailureCompetitorsSeriesView.setTitleText("竞品车系");
        mFailureCompetitorsSeriesView.setHintTextForContentView("请选择车系");

        mFailureCompetitorsSeriesOther = (CustomItemViewForEditTextWithMic) view.findViewById(R.id.new_customer_step_3_competitors_series_other);
        mFailureCompetitorsSeriesOther.initMicEditText(getActivity(), this);
        mFailureCompetitorsSeriesOther.setTitleText("其他车系");
        mFailureCompetitorsSeriesOther.setMaxTextNum(20);
        mFailureCompetitorsSeriesOther.setEnabled(true);
        mFailureCompetitorsSeriesOther.setHint(R.string.new_customer_failure_competitor_series_other_hint);

        mTrialView = (CustomItemViewForCheckbox) view.findViewById(R.id.new_customer_step_3_trial_view);
        mTrialView.setTitleText("是否试乘试驾");
    }

    private void initOptions() {
        Iterator<Map.Entry<String, String>> iterator = NewCustomerConstants.contactMethodMap.entrySet().iterator();
        mContactMethodCodeList = new ArrayList<>();
        mContactMethodValueList = new ArrayList<>();
        while (iterator.hasNext()) {
            Map.Entry<String, String> entry = iterator.next();
            mContactMethodCodeList.add(entry.getKey());
            mContactMethodValueList.add(entry.getValue());
        }

        iterator = NewCustomerConstants.failureReasonsMap.entrySet().iterator();
        mFailureReasonCodeList = new ArrayList<>();
        mFailureReasonValueList = new ArrayList<>();
        while (iterator.hasNext()) {
            Map.Entry<String, String> entry = iterator.next();
            mFailureReasonCodeList.add(entry.getKey());
            mFailureReasonValueList.add(entry.getValue());
        }

        iterator = NewCustomerConstants.asleepReasonsMap.entrySet().iterator();
        mAsleepReasonCodeList = new ArrayList<>();
        mAsleepReasonValueList = new ArrayList<>();
        while (iterator.hasNext()) {
            Map.Entry<String, String> entry = iterator.next();
            mAsleepReasonCodeList.add(entry.getKey());
            mAsleepReasonValueList.add(entry.getValue());
        }

        CommonUtils.initOptionsView(NewCustomerConstants.contactMethodMap, mContactMethodView);
        CommonUtils.initOptionsView(NewCustomerConstants.failureReasonsMap, mFailureReasonView);
        CommonUtils.initOptionsView(NewCustomerConstants.asleepReasonsMap, mAsleepReasonView);
    }

    private void initPickerViews() {
        mContactMethodPicker = new OptionsPickerView.Builder(getContext(), new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                onContactMethodOptionsSelected(options1);
            }
        }).build();
        mContactMethodPicker.setNPicker(mContactMethodValueList, null, null);

        mFailureReasonPicker = new OptionsPickerView.Builder(getContext(), new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                mFailureReasonTv.setText(mFailureReasonValueList.get(options1));
                mCurrentFailureReason = mFailureReasonCodeList.get(options1);
                checkIfAllSet();
            }
        }).build();
        mFailureReasonPicker.setNPicker(mFailureReasonValueList, null, null);

        mAsleepReasonPicker = new OptionsPickerView.Builder(getContext(), new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                mAsleepReasonTv.setText(mAsleepReasonValueList.get(options1));
                mCurrentAsleepReason = mAsleepReasonCodeList.get(options1);
                checkIfAllSet();
            }
        }).build();
        mAsleepReasonPicker.setNPicker(mAsleepReasonValueList, null, null);

        final String afterThreeMonth = DateUtils.addMonth("yyyy.MM.dd", new Date(), 3);
        mSleepFinishTimeTv.setText(afterThreeMonth);
        Calendar calendar = DateUtils.getCalendar("yyyy.MM.dd", afterThreeMonth);
        mSleepFinishTime = afterThreeMonth;
        mSleepFinishTimePicker = new CustomTimePickerView.Builder(getActivity(), new CustomTimePickerView.OnTimeSelectListener() {

            @Override
            public void onTimeSelect(Date date, View v) {
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy.MM.dd");
                String nowDate = simpleDateFormat.format(new Date());
                String selectDate = simpleDateFormat.format(date);

                if (DateUtils.getDateMill("yyyy.MM.dd", selectDate) <
                        DateUtils.getDateMill("yyyy.MM.dd", nowDate)) {
                    ToastUtils.showToast(getResources().getString(R.string.new_customer_asleep_time_error_tip));
                } else if (DateUtils.getDateMill("yyyy.MM.dd", selectDate) >
                        DateUtils.getDateMill("yyyy.MM.dd", afterThreeMonth)) {
                    ToastUtils.showToast(getResources().getString(R.string.new_customer_asleep_time_error_tip2));
                } else {
                    mSleepFinishTimeTv.setText(selectDate);
                    mSleepFinishTime = selectDate;
                    mSleepFinishTimePicker.dismiss();
                }
            }
        }).setType(new boolean[]{true, true, true, false, false, false}).build();

        mSleepFinishTimePicker.setDate(calendar);
    }

    /**
     * 联络方式选项选择后的操作
     */
    private void onContactMethodOptionsSelected(int optionIndex) {
        //若用户新选择的选项仍然是当前选项，则直接返回
        if (mContactMethodCodeList.isEmpty()
                || mContactMethodCodeList.get(optionIndex).equals(mCurrentContactMethodCode)) {
            return;
        }
        mCurrentContactMethodCode = mContactMethodCodeList.get(optionIndex);
        mCurrentContactMethodValue = mContactMethodValueList.get(optionIndex);
        mContactMethodTv.setText(mCurrentContactMethodValue);
        queryRelatedOptions(mCurrentContactMethodCode);
        checkIfAllSet();
    }

    /**
     * 根据联络方式的选项查询相关联的接触结果选项
     *
     * @param code 联络方式的选项code
     */
    private void queryRelatedOptions(String code) {
        DBHelper dbHelper = DealerApp.dbHelper;
        Cursor cursor = dbHelper.rawQuery("SELECT * FROM DictionaryRel Where dictId=? and relaTypeId=?", new String[]{code, "155"});
        if (NewCustomerConstants.contactMethodMultiChoicesCodeSet.contains(code)) {
            contactResultMultiChoicesMap.clear();
            while (cursor != null && cursor.moveToNext()) {
                contactResultMultiChoicesMap.put(cursor.getString(5), cursor.getString(6));
            }
            initRecyclerView();
        } else if (NewCustomerConstants.contactMethodSingleChoiceCodeSet.contains(code)) {
            contactResultSingleChoiceMap.clear();
            while (cursor != null && cursor.moveToNext()) {
                contactResultSingleChoiceMap.put(cursor.getString(5), cursor.getString(6));
            }
            initRelatedPicker();
        }
        if (cursor != null) {
            cursor.close();
        }
    }

    /**
     * 初始化多选相关的控件及相关的数据
     */
    private void initRecyclerView() {
        Iterator<Map.Entry<String, String>> iterator = mContactResultsMap.entrySet().iterator();
        if (mContactResultCodeList == null) {
            mContactResultCodeList = new ArrayList<>();
        } else {
            mContactResultCodeList.clear();
        }
        if (mContactResultValueList == null) {
            mContactResultValueList = new ArrayList<>();
        } else {
            mContactResultValueList.clear();
        }
        while (iterator.hasNext()) {
            Map.Entry<String, String> entry = iterator.next();
            mContactResultCodeList.add(entry.getKey());
            mContactResultValueList.add(entry.getValue());
        }
        //RecyclerView只初始化一次，否则选项UI可能会有点小问题
        if (mGridAdapter == null) {
            mContactResultRv.setLayoutManager(new GridLayoutManager(getContext(), 4, LinearLayoutManager.VERTICAL, false));
            mContactResultRv.addItemDecoration(new GridSpacingItemDecoration(4, getResources().getDimensionPixelSize(R.dimen.new_customer_grid_recycler_spacing), true));
            mGridAdapter = new GridAdapter(mContactResultCodeList, mContactResultValueList);
            mContactResultRv.setAdapter(mGridAdapter);
        }
        mGridAdapter.clearSelection();
        mGridAdapter.notifyDataSetChanged();
        mContactResultMultiTr.setVisibility(VISIBLE);
        mContactResultRv.setVisibility(VISIBLE);
        mResultRecyclerViewLine.setVisibility(VISIBLE);
        mTrialView.setVisibility(VISIBLE);
        mContactResultView.setVisibility(GONE);
        resetContactResultsView();
    }

    private void resetContactResultsView() {
        editTextWithMicLayoutReason.setVisibility(GONE);
        mFailureReasonView.setVisibility(GONE);
        mFailureReasonView.clearSelection();
        mAsleepReasonView.setVisibility(GONE);
        mAsleepReasonView.clearSelection();
        mAsleepTimeView.setVisibility(GONE);
        mFailureCompetitorsSeriesView.setVisibility(GONE);
        mFailureCompetitorsSeriesView.clearSelection();
    }

    /**
     * 初始化单选相关的控件及相应的数据
     */
    private void initRelatedPicker() {
        Iterator<Map.Entry<String, String>> iterator = contactResultSingleChoiceMap.entrySet().iterator();
        final List<String> codeList = new ArrayList<>();
        final List<String> valueList = new ArrayList<>();
        while (iterator.hasNext()) {
            Map.Entry<String, String> entry = iterator.next();
            codeList.add(entry.getKey());
            valueList.add(entry.getValue());
        }

        mSingleContactResultPicker = new OptionsPickerView.Builder(getContext(), new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                mSingleContactResultCode = codeList.get(options1);
                mSingleContactResultValue = valueList.get(options1);
                String text = mSingleContactResultValue;
                mContactResultTv.setText(text);
                if (text.contains("战败")) {
                    onAsleepChecked(false);
                    onFailureChecked(true);
                } else if (text.contains("休眠")) {
                    onFailureChecked(false);
                    onAsleepChecked(true);
                } else {
                    onFailureChecked(false);
                    onAsleepChecked(false);
                }
                checkIfAllSet();
            }
        }).build();
        mSingleContactResultPicker.setNPicker(valueList, null, null);
        mContactResultTr.setVisibility(VISIBLE);
        mContactResultTrLine.setVisibility(VISIBLE);
        mContactResultMultiTr.setVisibility(GONE);
        mContactResultRv.setVisibility(GONE);
        mResultRecyclerViewLine.setVisibility(GONE);
        resetContactResultsView();
    }

    private void initRelatedPicker2() {
        CommonUtils.initOptionsView(mContactResultsMap, mContactResultView);
        mContactResultView.setVisibility(VISIBLE);
        mContactResultMultiTr.setVisibility(GONE);
        mContactResultRv.setVisibility(GONE);
        mResultRecyclerViewLine.setVisibility(GONE);
        mTrialView.setVisibility(GONE);
        resetContactResultsView();
    }

    private void setListeners() {
        mContactMethodTr.setOnClickListener(this);
        mFailureReasonTr.setOnClickListener(this);
        mContactResultTr.setOnClickListener(this);
        mAsleepReasonTr.setOnClickListener(this);
        mSleepFinishTimeTr.setOnClickListener(this);

        mContactMethodView.setOnDataChangedListener(new CustomItemViewBase.OnDataChangedListener() {
            @Override
            public void onDateChanged(Object object) {
                String code = (String) object;
                if (!TextUtils.isEmpty(mCurrentContactMethodCode) && mCurrentContactMethodCode.equals(code)) {
                    return;//值没变
                }
                onContactMethodOptionsSelected(code);
                showNextFollowupPlanAndAppointment();//重新选择展厅电话等则需要重新将之前选战败隐藏掉的下次跟进计划显示出来
                checkIfAllSet();
            }
        });

        mContactResultView.setOnDataChangedListener(new CustomItemViewBase.OnDataChangedListener() {
            @Override
            public void onDateChanged(Object object) {
                String code = (String) object;
                mCurrentFollowupResult = code;
                informFollowupResultChanged();
                String value = mContactResultsMap.get(code);
                mSingleContactResultValue = value;
                if ("战败".equals(value)) {
                    onAsleepChecked(false);
                    onFailureChecked(true);
                } else if ("休眠".equals(value)) {
                    onFailureChecked(false);
                    onAsleepChecked(true);
                } else {
                    onFailureChecked(false);
                    onAsleepChecked(false);
                }
                checkIfAllSet();
            }
        });

        mFailureReasonView.setOnDataChangedListener(new CustomItemViewBase.OnDataChangedListener() {
            @Override
            public void onDateChanged(Object object) {
                String result = (String) object;
                if (TextUtils.isEmpty(result)) {
                    return;
                }
                if ("竞品".equals(result)) {
                    mFailureCompetitorsSeriesView.setVisibility(VISIBLE);
                    CommonUtils.initOptionsView(CommonUtils.generateFinalCompetitorSeriesMap(mSeriesId), mFailureCompetitorsSeriesView);
                } else {
                    mFailureCompetitorsSeriesView.setVisibility(GONE);
                    mFailureCompetitorsSeriesOther.setVisibility(GONE);
                    mFailureCompetitorsSeriesOther.setData("");
                }
                checkIfAllSet();
            }
        });

        mFailureCompetitorsSeriesView.setOnDataChangedListener(new CustomItemViewBase.OnDataChangedListener() {
            @Override
            public void onDateChanged(Object object) {
                String result = (String) object;
                if (TextUtils.isEmpty(result)) {
                    return;
                }
                mFailureCompetitorsSeriesOther.setVisibility("其他车系".equals(result) ? VISIBLE : GONE);
                checkIfAllSet();
            }
        });

        CustomItemViewBase.OnDataChangedListener listener = new CustomItemViewBase.OnDataChangedListener() {
            @Override
            public void onDateChanged(Object object) {
                checkIfAllSet();
            }
        };
        mAsleepReasonView.setOnDataChangedListener(listener);
    }

    private void onContactMethodOptionsSelected(String code) {
//        if (!TextUtils.isEmpty(mCurrentContactMethodCode) && mCurrentContactMethodCode.equals(code)) {
//            return;//值没变
//        }
        mCurrentContactMethodCode = code;
        if (!TextUtils.isEmpty(mIsFrom) && "0".equals(mIsFrom)) {
            mContactResultsMap = DBUtils.getFollowUpResultMap(code, mOppStatus, -1);
        } else {
            mContactResultsMap = DBUtils.getFollowUpResultMap(code, mOppStatus, 0);
        }
        if (NewCustomerConstants.contactMethodMultiChoicesCodeSet.contains(code)) {
            initRecyclerView();
        } else if ((NewCustomerConstants.contactMethodSingleChoiceCodeSet.contains(code))) {
//            initRelatedPicker2();
            initRecyclerView();
            mTrialView.setVisibility(GONE);
        }
    }

    private void showNextFollowupPlanAndAppointment() {
        if (getActivity() instanceof NewCustomerUnitedActivity) {
            ((NewCustomerUnitedActivity) getActivity()).showNextFollowupAndAppointment(true);
        } else if (getActivity() instanceof AddFollowupRecordActivity) {
            ((AddFollowupRecordActivity) getActivity()).setPlanAndScheduleVisibility(VISIBLE);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.new_customer_step_3_contact_method_tr:
                mContactMethodPicker.show();
                break;
            case R.id.new_customer_step_3_contact_result_tr:
                mSingleContactResultPicker.show();
                break;
            case R.id.new_customer_step_3_failure_reason_tr:
                mFailureReasonPicker.show();
                break;
            case R.id.new_customer_step_3_asleep_reason_tr:
                mAsleepReasonPicker.show();
                break;
            case R.id.new_customer_step_3_failure_time_tr:
                mSleepFinishTimePicker.show();
                break;
        }
    }

    /**
     * 选择其他
     *
     * @param checked 是否选中
     */
    private void onOthersChecked(boolean checked) {
        mReasonTr.setVisibility(checked ? VISIBLE : GONE);
        editTextWithMicLayoutReason.setVisibility(checked ? VISIBLE : GONE);
    }

    /**
     * 选择休眠
     */
    private void onAsleepChecked(boolean checked) {
        mAsleepReasonView.setVisibility(checked ? VISIBLE : GONE);
        mAsleepTimeView.setVisibility(checked ? VISIBLE : GONE);
        if (getActivity() instanceof AddFollowupRecordActivity) {
            ((AddFollowupRecordActivity) getActivity()).setPlanAndScheduleVisibility(checked ? GONE : VISIBLE);
        } else if ((getActivity() instanceof NewCustomerUnitedActivity)) {
            ((NewCustomerUnitedActivity) getActivity()).showNextFollowupAndAppointment(!checked);
        } else if (getActivity() instanceof NewCustomerStepThreeActivity) {
            ((NewCustomerStepThreeActivity) getActivity()).markAsFinished(checked);
        } else if (getActivity() instanceof OrderCancelActivity2) {
            ((OrderCancelActivity2) getActivity()).setPlanVisibility(checked ? GONE : VISIBLE);
        }
    }

    /**
     * 选择战败
     *
     * @param checked 是否选中
     */
    private void onFailureChecked(boolean checked) {
        mFailureReasonView.setVisibility(checked ? VISIBLE : GONE);
        if (!checked) {
            mFailureReasonView.clearSelection();
            mFailureCompetitorsSeriesView.setVisibility(GONE);
            mFailureCompetitorsSeriesView.clearSelection();
            mFailureCompetitorsSeriesOther.setVisibility(GONE);
            mFailureCompetitorsSeriesOther.setData("");
        }
        if (getActivity() instanceof AddFollowupRecordActivity) {
            ((AddFollowupRecordActivity) getActivity()).setPlanAndScheduleVisibility(checked ? GONE : VISIBLE);
        } else if ((getActivity() instanceof NewCustomerUnitedActivity)) {
            ((NewCustomerUnitedActivity) getActivity()).showNextFollowupAndAppointment(!checked);
        } else if (getActivity() instanceof NewCustomerStepThreeActivity) {
            ((NewCustomerStepThreeActivity) getActivity()).markAsFinished(checked);
        } else if (getActivity() instanceof OrderCancelActivity2) {
            ((OrderCancelActivity2) getActivity()).setPlanVisibility(checked ? GONE : VISIBLE);
        }
    }

    public String getFollowupResult() {
        return mCurrentFollowupResult;
    }

    public void setSeriesId(String seriesId) {
        if (TextUtils.isEmpty(seriesId)) {
            mFailureCompetitorsSeriesView.clearData();
            return;
        }
        mSeriesId = seriesId;
        if (mFailureCompetitorsSeriesView.isVisible()) {
            mFailureCompetitorsSeriesView.clearData();
            CommonUtils.initOptionsView(CommonUtils.generateFinalCompetitorSeriesMap(mSeriesId), mFailureCompetitorsSeriesView);
            mFailureCompetitorsSeriesOther.setVisibility(GONE);
            mFailureCompetitorsSeriesOther.setData("");
        }
    }

    private void informFollowupResultChanged() {
        //只有首次跟进时，跟进结果才会影响下次跟进日期
        if (!mFirstFollowUp) {
            return;
        }
        if (getActivity() instanceof NewCustomerUnitedActivity) {
            ((NewCustomerUnitedActivity) getActivity()).checkToResetFollowupCalendars();
        }
    }

    class GridAdapter extends RecyclerView.Adapter<GridAdapter.ViewHolder> {

        private List<String> mValueList, mCodeList;
        private Set<Integer> mCheckedIndexSet = new HashSet<>();
        private Set<String> mCheckedValueSet = new HashSet<>();


        public GridAdapter(List<String> codeList, List<String> valueList) {
            mValueList = valueList;
            mCodeList = codeList;
        }

        public Set<Integer> getCheckedIndexSet() {
            return mCheckedIndexSet;
        }

        public void clearSelection() {
            mCheckedIndexSet.clear();
            mCheckedValueSet.clear();
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_new_customer_grid, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, final int position) {
            final int adapterPosition = holder.getAdapterPosition();
            final String text = mValueList.get(adapterPosition);
            holder.checkBox.setText(text);
            holder.checkBox.setChecked(mCheckedIndexSet.contains(adapterPosition));
            holder.checkBox.setEnabled(true);
            if (!mCheckedValueSet.isEmpty() && !mCheckedValueSet.contains(text)) {
                holder.checkBox.setEnabled(false);
            }
//            if (mCheckedValueSet.contains("战败")) {//选择战败时，其余全部按钮禁用
//                if (!text.contains("战败")) {
//                    holder.checkBox.setEnabled(false);
//                }
//            } else if (mCheckedValueSet.contains("休眠")) {//选择休眠时，其余全部按钮禁用
//                if (!text.contains("休眠")) {
//                    holder.checkBox.setEnabled(false);
//                }
//            } else if (!mCheckedValueSet.isEmpty()) {//选择任意战败休眠以外的按钮时，战败休眠按钮禁用
//                if (text.contains("战败") || text.contains("休眠")) {
//                    holder.checkBox.setEnabled(false);
//                }
//            }
            holder.checkBox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (holder.checkBox.isChecked()) {
                        mCurrentFollowupResult = mCodeList.get(adapterPosition);
                        informFollowupResultChanged();
                        mCheckedIndexSet.add(adapterPosition);
                        mCheckedValueSet.add(text);
                        if (text.contains("休眠")) {
                            onAsleepChecked(true);
                        } else if (text.contains("其他")) {
//                            onOthersChecked(true);//需求整改为点击其他时不需要显示输入框相关内容
                        } else if (text.contains("战败")) {
                            onFailureChecked(true);
                        }
                    } else {
                        mCheckedIndexSet.remove(adapterPosition);
                        mCheckedValueSet.remove(text);
                        if (text.contains("休眠")) {
                            onAsleepChecked(false);
                        } else if (text.contains("其他")) {
//                            onOthersChecked(false);
                        } else if (text.contains("战败")) {
                            onFailureChecked(false);
                        }
                    }
                    notifyDataSetChanged();
                    checkIfAllSet();
                }
            });
        }

        @Override
        public int getItemCount() {
            return mValueList.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            CheckBox checkBox;

            private ViewHolder(View itemView) {
                super(itemView);
                checkBox = (CheckBox) itemView.findViewById(R.id.item_new_customer_grid_checkbox);
            }
        }

    }

    public FollowupCreateReqEntity getParameters() {
        FollowupCreateReqEntity entity = new FollowupCreateReqEntity();
        entity.setModeId(mCurrentContactMethodCode);
        //多选场景(展厅电话微信短信全走这里)
        if ("15010".equals(mCurrentContactMethodCode) || "15020".equals(mCurrentContactMethodCode)
                || "15030".equals(mCurrentContactMethodCode) || "15040".equals(mCurrentContactMethodCode)) {
            Set<Integer> checkedIndexSet = mGridAdapter.getCheckedIndexSet();
            List<FollowupCreateReqEntity.ResultsBean> list = new ArrayList<>();
            for (Integer index : checkedIndexSet) {
                FollowupCreateReqEntity.ResultsBean resultsBean = new FollowupCreateReqEntity.ResultsBean();
                resultsBean.setDictId(mContactResultCodeList.get(index));
                if (mContactResultValueList.get(index).contains("战败")) {
                    resultsBean.setResultDesc(mFailureReasonView.getInputData());
                    if ("竞品".equals(mFailureReasonView.getInputData())) {
                        if (mFailureCompetitorsSeriesOther.isVisible()) {
                            entity.setCompetitor(mFailureCompetitorsSeriesOther.getInputData());
                        } else {
                            entity.setCompetitor(mFailureCompetitorsSeriesView.getInputData());
                        }
                    }
                } else if (mContactResultValueList.get(index).contains("休眠")) {
                    resultsBean.setResultDesc(mAsleepReasonView.getInputData());
                    resultsBean.setSuspendDate(mAsleepTimeView.getInputData());
                } else if (mContactResultValueList.get(index).contains("其他")) {
                    resultsBean.setResultDesc(editTextWithMicLayoutReason.getTextContent());
                } else {
                    resultsBean.setResultDesc(mContactResultValueList.get(index));
                }
                list.add(resultsBean);
            }
            entity.setResults(list);
        } else {
            //单选场景(不会再执行到此)
            List<FollowupCreateReqEntity.ResultsBean> list = new ArrayList<>();
            FollowupCreateReqEntity.ResultsBean resultsBean = new FollowupCreateReqEntity.ResultsBean();
            resultsBean.setDictId(mContactResultView.getInputData());
            String value = mContactResultsMap.get(mContactResultView.getInputData());
            if (value.contains("战败")) {
                resultsBean.setResultDesc(mFailureReasonView.getInputData());
                if ("竞品".equals(mFailureReasonView.getInputData())) {
                    if (mFailureCompetitorsSeriesOther.isVisible()) {
                        entity.setCompetitor(mFailureCompetitorsSeriesOther.getInputData());
                    } else {
                        entity.setCompetitor(mFailureCompetitorsSeriesView.getInputData());
                    }
                }
            } else if (value.contains("休眠")) {
                resultsBean.setResultDesc(mAsleepReasonView.getInputData());
                resultsBean.setSuspendDate(mAsleepTimeView.getInputData());
            }
            list.add(resultsBean);//单选时只有一个bean，对应唯一的选项
            entity.setResults(list);
        }
        entity.setRemark(editTextWithMicLayoutRemark.getTextContent());
        if (mTrialView.isVisible()) {
            entity.setIsTestdrive(mTrialView.getInputData() ? "0" : "1");
        }
        return entity;
    }

    public void onSubmitSuccess() {
        if (TextUtils.isEmpty(mCurrentContactMethodCode)) {
            return;
        }
        checkToRefreshListInMultipleMode();//都是通过RecyclerView来选择跟进结果了
//        if (mCurrentContactMethodCode.equals("15010")) {
//            //多选场景
//            checkToRefreshListInMultipleMode();
//        } else {
//            checkToRefreshListInSingleMode();
//        }
    }

    private void checkToRefreshListInSingleMode() {
        String value = mContactResultsMap.get(mContactResultView.getInputData());
        if (value.contains("战败")) {
            Intent i = new Intent(FailedCustomerFragment.FRESH_FILTER_STRING);
            getContext().sendBroadcast(i);
        } else if (value.contains("休眠")) {
            Intent i = new Intent(SleepCustomerFragment.FRESH_FILTER_STRING);
            getContext().sendBroadcast(i);
        }
    }

    private void checkToRefreshListInMultipleMode() {
        boolean order = false;
        boolean invoice = false;
        boolean delivery = false;
        boolean failure = false;
        boolean asleep = false;
        boolean visit = false;
        Set<Integer> set = mGridAdapter.getCheckedIndexSet();
        for (Integer index : set) {
            if (index >= mContactResultCodeList.size()) {
                return;
            }
            String code = mContactResultCodeList.get(index);
            if ("15540".equals(code)) {
                //订单
                order = true;
            } else if ("15550".equals(code)) {
                //开票
                invoice = true;
            } else if ("15560".equals(code)) {
                //交车
                delivery = true;
            } else if ("15570".equals(code)) {
                //战败
                failure = true;
            } else if ("15590".equals(code)) {
                //休眠
                asleep = true;
            } else if ("15595".equals(code)) {
                //售后回访
                visit = true;
            }
        }
        if (order) {
            Intent i = new Intent(OrderCustomerFragment.REFRESH_FILTER_STRING);
            Intent i2 = new Intent(YellowCardFragment.FRESH_FILTER_STRING);
            getContext().sendBroadcast(i);
            getContext().sendBroadcast(i2);
        }
        if (delivery) {
            Intent i = new Intent(DealCustomerFragment.FRESH_FILTER_STRING);
            Intent i2 = new Intent(YellowCardFragment.FRESH_FILTER_STRING);
            getContext().sendBroadcast(i);
            getContext().sendBroadcast(i2);
        }
        if (invoice || visit) {
            Intent i = new Intent(DealCustomerFragment.FRESH_FILTER_STRING);
            getContext().sendBroadcast(i);
        }
        if (failure) {
            Intent i = new Intent(FailedCustomerFragment.FRESH_FILTER_STRING);
            getContext().sendBroadcast(i);
        }
        if (asleep) {
            Intent i = new Intent(SleepCustomerFragment.FRESH_FILTER_STRING);
            getContext().sendBroadcast(i);
        }
        //订单---订单客户列表
        //开票、交车、售后回访-------成交客户列表
        //战败------战败列表
        //休眠------休眠列表
    }

    /**
     * 刷新资源页5个列表
     */
    public void refreshAllLists() {
        Intent i = new Intent(OrderCustomerFragment.REFRESH_FILTER_STRING);
        Intent i2 = new Intent(YellowCardFragment.FRESH_FILTER_STRING);
        Intent i3 = new Intent(DealCustomerFragment.FRESH_FILTER_STRING);
        Intent i4 = new Intent(SleepCustomerFragment.FRESH_FILTER_STRING);
        Intent i5 = new Intent(FailedCustomerFragment.FRESH_FILTER_STRING);
        getContext().sendBroadcast(i);
        getContext().sendBroadcast(i2);
        getContext().sendBroadcast(i3);
        getContext().sendBroadcast(i4);
        getContext().sendBroadcast(i5);
    }

    /**
     * 检查数据输入完整性
     *
     * @return
     */
    public boolean checkDataValidation() {
        if (TextUtils.isEmpty(mCurrentContactMethodCode)) {
            return false;
        }
        //展厅电话微信短信
        if ("15010".equals(mCurrentContactMethodCode) || "15020".equals(mCurrentContactMethodCode)
                || "15030".equals(mCurrentContactMethodCode) || "15040".equals(mCurrentContactMethodCode)) {
            Set<Integer> checkedIndexSet = mGridAdapter.getCheckedIndexSet();
            if (mGridAdapter.getCheckedIndexSet().isEmpty()) {
                return false;
            }
            for (Integer index : checkedIndexSet) {
                String value = mContactResultValueList.get(index);
                if (value.contains("战败")) {
                    if (TextUtils.isEmpty(mFailureReasonView.getInputData())) {
                        return false;
                    }
                    if ("竞品".equals(mFailureReasonView.getInputData())
                            && TextUtils.isEmpty(mFailureCompetitorsSeriesView.getInputData())) {
                        return false;
                    }
                } else if (value.contains("休眠")) {
                    if (TextUtils.isEmpty(mAsleepReasonView.getInputData()) || TextUtils.isEmpty(mAsleepTimeView.getInputData())) {
                        return false;
                    }
                }/*else if (value.contains("其他")) {
                    if (TextUtils.isEmpty(mReasonContentEt.getText().toString().trim())) {
                        return false;
                    }
                }*/
            }
        } else {
            //单选场景
            String value = mContactResultsMap.get(mContactResultView.getInputData());
            if (TextUtils.isEmpty(value)) {
                return false;
            }
            if (value.contains("战败")) {
                if (TextUtils.isEmpty(mFailureReasonView.getInputData())) {
                    return false;
                }
                if ("竞品".equals(mFailureReasonView.getInputData())
                        && TextUtils.isEmpty(mFailureCompetitorsSeriesView.getInputData())) {
                    return false;
                }
            } else if (value.contains("休眠") || TextUtils.isEmpty(mAsleepTimeView.getInputData())) {
                if (TextUtils.isEmpty(mAsleepReasonView.getInputData())) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        editTextWithMicLayoutReason.stopNLS();
        editTextWithMicLayoutRemark.stopNLS();
        mFailureCompetitorsSeriesOther.stopNLS();
        if (null != mSleepFinishTimePicker) {
            mSleepFinishTimePicker.dismiss();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case PermissionUtils.REQUEST_AUDIO_RECORD_PERMISSION:
                if (Manifest.permission.RECORD_AUDIO.equals(permissions[0]) && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                }
                break;
        }
    }

    private void checkIfAllSet() {
        if (getActivity() instanceof NewCustomerStepThreeActivity) {
            ((NewCustomerStepThreeActivity) getActivity()).checkIfNeedToEnableSubmit();
        } else if (getActivity() instanceof AddFollowupRecordActivity) {
            ((AddFollowupRecordActivity) getActivity()).checkIfNeedToEnableSubmit();
        } else if (getActivity() instanceof NewCustomerUnitedActivity) {
            ((NewCustomerUnitedActivity) getActivity()).checkIfNeedToEnableSubmit();
        } else if (getActivity() instanceof ActivateYellowCardActivity) {
            ((ActivateYellowCardActivity) getActivity()).checkIfNeedToEnableSubmit();
        } else if (getActivity() instanceof OrderCancelActivity2) {
            ((OrderCancelActivity2) getActivity()).checkIfNeedToEnableSubmit();
        }
    }

    public void setEnabled(boolean enabled) {
        mEnabled = enabled;
    }

}
