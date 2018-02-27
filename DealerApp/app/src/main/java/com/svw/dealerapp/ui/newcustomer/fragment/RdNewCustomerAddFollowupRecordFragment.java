package com.svw.dealerapp.ui.newcustomer.fragment;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.svw.dealerapp.R;
import com.svw.dealerapp.common.BaseFragment;
import com.svw.dealerapp.entity.newcustomer.FollowupCreateReqEntity;
import com.svw.dealerapp.global.NewCustomerConstants;
import com.svw.dealerapp.ui.adapter.RdRecyclerViewAdapter;
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
import com.svw.dealerapp.ui.widget.EditTextWithMicLayout;
import com.svw.dealerapp.ui.widget.RdCustomItemViewBase;
import com.svw.dealerapp.ui.widget.RdCustomItemViewForCheckbox;
import com.svw.dealerapp.ui.widget.RdCustomItemViewForDatePicker;
import com.svw.dealerapp.ui.widget.RdCustomItemViewForEditTextWithMic;
import com.svw.dealerapp.ui.widget.RdCustomItemViewForOptionsPicker;
import com.svw.dealerapp.ui.widget.RdCustomItemViewForRecycleView;
import com.svw.dealerapp.util.CommonUtils;
import com.svw.dealerapp.util.dbtools.DBUtils;
import com.svw.dealerapp.util.DateUtils;
import com.svw.dealerapp.util.JLog;
import com.svw.dealerapp.util.PermissionUtils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

/**
 * 添加跟进记录Fragment
 * Created by xupan on 25/01/2018.
 */

public class RdNewCustomerAddFollowupRecordFragment extends BaseFragment {

    private static final String TAG = "NewCustomerAddFollowupRecordFragment";
    private Map<String, String> mParaMap;

    private String mCurrentContactMethodCode;

//    private String mOrderStatus;

    private EditTextWithMicLayout editTextWithMicLayoutReason;
    private EditTextWithMicLayout editTextWithMicLayoutRemark;

    private String mOppStatus = "11500";//默认潜客状态为“新”

    private Map<String, String> mContactResultsMap;
    private boolean mFirstFollowUp;//是否首次跟进
    private String mCurrentFollowupResult;//当前选择的跟进结果
    private String mSeriesId;
    private String mIsFrom;//0:取消订单
    private boolean mEnabled = true;//设置picker是否可以点击

    private RdCustomItemViewForRecycleView mContactMethodView, mContactResultView;
    private RdCustomItemViewForOptionsPicker mFailureReasonView, mFailureCompetitorsSeriesView, mAsleepReasonView;
    private RdCustomItemViewForEditTextWithMic mFailureCompetitorsSeriesOther;//竞品车系选择其他后出现的输入框
    private RdCustomItemViewForDatePicker mAsleepTimeView;
    private RdCustomItemViewForCheckbox mTrialView;
    private RdCustomItemViewBase.OnDataChangedListener mContactResultDataChangedListener;

    public static RdNewCustomerAddFollowupRecordFragment newInstance(HashMap<String, String> map) {
        Bundle bundle = new Bundle();
        bundle.putSerializable("key", map);
        RdNewCustomerAddFollowupRecordFragment fragment = new RdNewCustomerAddFollowupRecordFragment();
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
        View view = inflater.inflate(R.layout.rd_layout_new_customer_step_3_follow_up, container, false);
        initViews(view);
        initOptions();
        setListeners();
        onContactMethodOptionsSelected("15010");//联络方式默认选择展厅
        List<String> defaultContactMethodList = new ArrayList<>();
        defaultContactMethodList.add("15010");
        mContactMethodView.setData(defaultContactMethodList);
        return view;
    }

    private void initViews(View view) {
        editTextWithMicLayoutReason = (EditTextWithMicLayout) view.findViewById(R.id.et_with_mic1);
        editTextWithMicLayoutReason.initEditWithMic(getActivity(), RdNewCustomerAddFollowupRecordFragment.this);
        editTextWithMicLayoutReason.setMaxTextNum(50);
        editTextWithMicLayoutReason.setEnabled(true);
        editTextWithMicLayoutReason.setHint(getResources().getString(R.string.new_customer_other_reason));

        editTextWithMicLayoutRemark = (EditTextWithMicLayout) view.findViewById(R.id.et_with_mic2);
        editTextWithMicLayoutRemark.initEditWithMic(getActivity(), RdNewCustomerAddFollowupRecordFragment.this);
        editTextWithMicLayoutRemark.setMaxTextNum(50);
        editTextWithMicLayoutRemark.setEnabled(true);
        editTextWithMicLayoutRemark.setHint(getResources().getString(R.string.new_customer_remark_detail_hint));

        mContactMethodView = (RdCustomItemViewForRecycleView) view.findViewById(R.id.new_customer_step_3_contact_method_view);
        mContactMethodView.setMandatory(true);
        mContactMethodView.setTitleText(R.string.new_customer_contact_method);
        mContactMethodView.setHintTextForContentView(R.string.new_customer_contact_method_hint);
        mContactMethodView.setEnabled(mEnabled);

        mContactResultView = (RdCustomItemViewForRecycleView) view.findViewById(R.id.new_customer_step_3_contact_result_view);
        mContactResultView.setMandatory(true);
        mContactResultView.setTitleText(R.string.new_customer_contact_result);
        mContactResultView.setHintTextForContentView(R.string.new_customer_contact_result_hint);

        mFailureReasonView = (RdCustomItemViewForOptionsPicker) view.findViewById(R.id.new_customer_step_3_failure_reason_view);
        mFailureReasonView.setMandatory(true);
        mFailureReasonView.setTitleText(R.string.new_customer_failure_result_title);
        mFailureReasonView.setHintTextForContentView(R.string.new_customer_failure_result_hint);

        mAsleepReasonView = (RdCustomItemViewForOptionsPicker) view.findViewById(R.id.new_customer_step_3_asleep_reason_view);
        mAsleepReasonView.setMandatory(true);
        mAsleepReasonView.setTitleText(R.string.new_customer_asleep_result_title);
        mAsleepReasonView.setHintTextForContentView(R.string.new_customer_asleep_result_hint);

        mAsleepTimeView = (RdCustomItemViewForDatePicker) view.findViewById(R.id.new_customer_step_3_asleep_time_view);
        mAsleepTimeView.setMandatory(true);
        mAsleepTimeView.setTitleText(R.string.new_customer_asleep_finish_time);
        mAsleepTimeView.setHintTextForContentView(R.string.new_customer_asleep_finish_time_hint);
        mAsleepTimeView.setDateFormatStr("yyyy.MM.dd");
        mAsleepTimeView.setDateFormatStrForParameter("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
        Calendar now = Calendar.getInstance();
        Calendar end = DateUtils.addDay(now, 365);
        Calendar def = DateUtils.addDay(now, 90);
        mAsleepTimeView.initPicker(new boolean[]{true, true, true, false, false, false}, now, end, def);

        mFailureCompetitorsSeriesView = (RdCustomItemViewForOptionsPicker) view.findViewById(R.id.new_customer_step_3_competitors_series_picker);
        mFailureCompetitorsSeriesView.setMandatory(true);
        mFailureCompetitorsSeriesView.setTitleText("竞品车系");
        mFailureCompetitorsSeriesView.setHintTextForContentView("请选择车系");

        mFailureCompetitorsSeriesOther = (RdCustomItemViewForEditTextWithMic) view.findViewById(R.id.new_customer_step_3_competitors_series_other);
        mFailureCompetitorsSeriesOther.initMicEditText(getActivity(), this);
        mFailureCompetitorsSeriesOther.setTitleText("其他车系");
        mFailureCompetitorsSeriesOther.setMaxTextNum(20);
        mFailureCompetitorsSeriesOther.setEnabled(true);
        mFailureCompetitorsSeriesOther.setHint(R.string.new_customer_failure_competitor_series_other_hint);

        mTrialView = (RdCustomItemViewForCheckbox) view.findViewById(R.id.new_customer_step_3_trial_view);
        mTrialView.setTitleText("是否试乘试驾");
    }

    private void initOptions() {
        mContactMethodView.initAdapter(NewCustomerConstants.contactMethodMap, RdRecyclerViewAdapter.MODE_SINGLE);
        mContactResultView.initAdapter(new HashMap<String, String>(), RdRecyclerViewAdapter.MODE_SINGLE);
        CommonUtils.rdInitOptionsView(NewCustomerConstants.failureReasonsMap, mFailureReasonView);
        CommonUtils.rdInitOptionsView(NewCustomerConstants.asleepReasonsMap, mAsleepReasonView);
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

    private void setListeners() {
        mContactMethodView.setOnDataChangedListener(new RdCustomItemViewBase.OnDataChangedListener() {
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

        mContactResultDataChangedListener = new RdCustomItemViewBase.OnDataChangedListener() {
            @Override
            public void onDateChanged(Object object) {
                String code = (String) object;
                //表示取消选中
                if (mContactResultView.getInputData().isEmpty()) {
                    String value = mContactResultsMap.get(code);
                    if ("战败".equals(value)) {
                        onFailureChecked(false);
                    } else if ("休眠".equals(value)) {
                        onAsleepChecked(false);
                    } else {
                        onFailureChecked(false);
                        onAsleepChecked(false);
                    }
                } else {
                    mCurrentFollowupResult = code;
                    informFollowupResultChanged();
                    String value = mContactResultsMap.get(code);
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
                }
                checkIfAllSet();
            }
        };
        mContactResultView.setOnDataChangedListener(mContactResultDataChangedListener);

        mFailureReasonView.setOnDataChangedListener(new RdCustomItemViewBase.OnDataChangedListener() {
            @Override
            public void onDateChanged(Object object) {
                String result = (String) object;
                if (TextUtils.isEmpty(result)) {
                    return;
                }
                if ("竞品".equals(result)) {
                    mFailureCompetitorsSeriesView.setVisibility(VISIBLE);
                    CommonUtils.rdInitOptionsView(CommonUtils.generateFinalCompetitorSeriesMap(mSeriesId), mFailureCompetitorsSeriesView);
                } else {
                    mFailureCompetitorsSeriesView.setVisibility(GONE);
                    mFailureCompetitorsSeriesOther.setVisibility(GONE);
                    mFailureCompetitorsSeriesOther.setData("");
                }
                checkIfAllSet();
            }
        });

        mFailureCompetitorsSeriesView.setOnDataChangedListener(new RdCustomItemViewBase.OnDataChangedListener() {
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

        RdCustomItemViewBase.OnDataChangedListener listener = new RdCustomItemViewBase.OnDataChangedListener() {
            @Override
            public void onDateChanged(Object object) {
                checkIfAllSet();
            }
        };
        mAsleepReasonView.setOnDataChangedListener(listener);
    }

    private void onContactMethodOptionsSelected(String code) {
        mCurrentContactMethodCode = code;
        if (!TextUtils.isEmpty(mIsFrom) && "0".equals(mIsFrom)) {
            mContactResultsMap = DBUtils.getFollowUpResultMap(code, mOppStatus, -1);
        } else {
            mContactResultsMap = DBUtils.getFollowUpResultMap(code, mOppStatus, 0);
        }
        mContactResultView.initAdapter(mContactResultsMap, RdRecyclerViewAdapter.MODE_SINGLE);
        mContactResultView.setOnDataChangedListener(mContactResultDataChangedListener);
        resetContactResultsView();
    }

    private void showNextFollowupPlanAndAppointment() {
        if (getActivity() instanceof NewCustomerUnitedActivity) {
            ((NewCustomerUnitedActivity) getActivity()).showNextFollowupAndAppointment(true);
        } else if (getActivity() instanceof AddFollowupRecordActivity) {
            ((AddFollowupRecordActivity) getActivity()).setPlanAndScheduleVisibility(VISIBLE);
        }
    }

    /**
     * 选择其他
     *
     * @param checked 是否选中
     */
    private void onOthersChecked(boolean checked) {
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
            CommonUtils.rdInitOptionsView(CommonUtils.generateFinalCompetitorSeriesMap(mSeriesId), mFailureCompetitorsSeriesView);
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

    public FollowupCreateReqEntity getParameters() {
        FollowupCreateReqEntity entity = new FollowupCreateReqEntity();
        entity.setModeId(mCurrentContactMethodCode);
        //多选场景(展厅电话微信短信全走这里)
        if ("15010".equals(mCurrentContactMethodCode) || "15020".equals(mCurrentContactMethodCode)
                || "15030".equals(mCurrentContactMethodCode) || "15040".equals(mCurrentContactMethodCode)) {
//            Set<Integer> checkedIndexSet = mGridAdapter.getCheckedIndexSet();
//            List<FollowupCreateReqEntity.ResultsBean> list = new ArrayList<>();
//            for (Integer index : checkedIndexSet) {
//                FollowupCreateReqEntity.ResultsBean resultsBean = new FollowupCreateReqEntity.ResultsBean();
//                resultsBean.setDictId(mContactResultCodeList.get(index));
//                if (mContactResultValueList.get(index).contains("战败")) {
//                    resultsBean.setResultDesc(mFailureReasonView.getInputData());
//                    if ("竞品".equals(mFailureReasonView.getInputData())) {
//                        if (mFailureCompetitorsSeriesOther.isVisible()) {
//                            entity.setCompetitor(mFailureCompetitorsSeriesOther.getInputData());
//                        } else {
//                            entity.setCompetitor(mFailureCompetitorsSeriesView.getInputData());
//                        }
//                    }
//                } else if (mContactResultValueList.get(index).contains("休眠")) {
//                    resultsBean.setResultDesc(mAsleepReasonView.getInputData());
//                    resultsBean.setSuspendDate(mAsleepTimeView.getInputData());
//                } else if (mContactResultValueList.get(index).contains("其他")) {
//                    resultsBean.setResultDesc(editTextWithMicLayoutReason.getTextContent());
//                } else {
//                    resultsBean.setResultDesc(mContactResultValueList.get(index));
//                }
//                list.add(resultsBean);
//            }
//            entity.setResults(list);
        } else {
            //单选场景(不会再执行到此)
            List<FollowupCreateReqEntity.ResultsBean> list = new ArrayList<>();
            FollowupCreateReqEntity.ResultsBean resultsBean = new FollowupCreateReqEntity.ResultsBean();
            resultsBean.setDictId(mContactResultView.getInputData().get(0));
            String value = mContactResultsMap.get(mContactResultView.getInputData().get(0));
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
    }

    private void checkToRefreshListInSingleMode() {
        String value = mContactResultsMap.get(mContactResultView.getInputData().get(0));
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
//        Set<Integer> set = mGridAdapter.getCheckedIndexSet();
//        for (Integer index : set) {
//            if (index >= mContactResultCodeList.size()) {
//                return;
//            }
//            String code = mContactResultCodeList.get(index);
//            if ("15540".equals(code)) {
//                //订单
//                order = true;
//            } else if ("15550".equals(code)) {
//                //开票
//                invoice = true;
//            } else if ("15560".equals(code)) {
//                //交车
//                delivery = true;
//            } else if ("15570".equals(code)) {
//                //战败
//                failure = true;
//            } else if ("15590".equals(code)) {
//                //休眠
//                asleep = true;
//            } else if ("15595".equals(code)) {
//                //售后回访
//                visit = true;
//            }
//        }
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
//            Set<Integer> checkedIndexSet = mGridAdapter.getCheckedIndexSet();
//            if (mGridAdapter.getCheckedIndexSet().isEmpty()) {
//                return false;
//            }
//            for (Integer index : checkedIndexSet) {
//                String value = mContactResultValueList.get(index);
//                if (value.contains("战败")) {
//                    if (TextUtils.isEmpty(mFailureReasonView.getInputData())) {
//                        return false;
//                    }
//                    if ("竞品".equals(mFailureReasonView.getInputData())
//                            && TextUtils.isEmpty(mFailureCompetitorsSeriesView.getInputData())) {
//                        return false;
//                    }
//                } else if (value.contains("休眠")) {
//                    if (TextUtils.isEmpty(mAsleepReasonView.getInputData()) || TextUtils.isEmpty(mAsleepTimeView.getInputData())) {
//                        return false;
//                    }
//                }/*else if (value.contains("其他")) {
//                    if (TextUtils.isEmpty(mReasonContentEt.getText().toString().trim())) {
//                        return false;
//                    }
//                }*/
//            }
        } else {
            //单选场景
            String value = mContactResultsMap.get(mContactResultView.getInputData().get(0));
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
