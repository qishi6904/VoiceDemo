package com.svw.dealerapp.ui.widget;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.svw.dealerapp.R;
import com.svw.dealerapp.util.DateUtils;
import com.svw.dealerapp.util.ToastUtils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by qinshi on 12/20/2017.
 */

public class DatePeriodPickerView extends LinearLayout implements View.OnClickListener {

    private Context context;

    private TextView tvTimeAll;
    private TextView tvStartTime;
    private TextView tvEndTime;

    private CustomTimePickerView datePicker;
    private boolean isSelectAllTime = true;
    private boolean isSelectStartTime = true;
    private String startTime;
    private Date endTimeDate;
    private String endTime;
    private OnAllTimeClickListener onAllTimeClickListener;
    private OnPickerSelectedListener onPickerSelectedListener;

    public DatePeriodPickerView(Context context) {
        super(context);
        this.context = context;
        initView();
    }

    public DatePeriodPickerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        initView();
    }

    private void initView(){
        View view = View.inflate(context, R.layout.ui_date_period_picker_view, this);
        assignViews(view);
    }

    private void assignViews(View view) {
        tvTimeAll = (TextView) view.findViewById(R.id.tv_time_all);
        tvStartTime = (TextView) view.findViewById(R.id.tv_start_time);
        tvEndTime = (TextView) view.findViewById(R.id.tv_end_time);

        tvEndTime.setOnClickListener(this);
        tvStartTime.setOnClickListener(this);
        tvTimeAll.setOnClickListener(this);

        initPicker();

    }

    private void initPicker(){
        datePicker = new CustomTimePickerView.Builder(context, new CustomTimePickerView.OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {
                isSelectAllTime = false;
                tvTimeAll.setBackgroundDrawable(getResources().getDrawable(R.drawable.shape_resource_filter_item_unselect_bg));
                tvTimeAll.setTextColor(getResources().getColor(R.color.resource_main_text));

                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy年MM月dd日");
                String chosenDate = simpleDateFormat.format(date);
                if(isSelectStartTime) {
                    tvStartTime.setText(chosenDate);
                    startTime = chosenDate;
                }else {
                    if(!TextUtils.isEmpty(startTime)){
                        if(DateUtils.compareDate("yyyy年MM月dd日", startTime, "yyyy年MM月dd日", chosenDate) > 0){
                            ToastUtils.showToast(getResources()
                                    .getString(R.string.resource_yellow_filter_time_error));
                            return;
                        }
                    }
                    tvEndTime.setText(chosenDate);
                    endTimeDate = date;
                    endTime = chosenDate;
                }
                if(null != onPickerSelectedListener) {
                    onPickerSelectedListener.OnPickerSelected(date);
                }
                datePicker.dismiss();
            }
        }).setType(new boolean[]{true, true, true, false, false, false}).build();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_time_all:
                if(isSelectAllTime){
                    return;
//                    tvTimeAll.setBackgroundDrawable(getResources().getDrawable(R.drawable.shape_resource_filter_item_unselect_bg));
//                    tvTimeAll.setTextColor(getResources().getColor(R.color.resource_main_text));
                }else {
                    tvTimeAll.setBackgroundDrawable(getResources().getDrawable(R.drawable.shape_resource_filter_item_select_bg));
                    tvTimeAll.setTextColor(Color.WHITE);
                    tvStartTime.setText(getResources().getString(R.string.resource_yellow_filter_time_pick_start));
                    tvEndTime.setText(getResources().getString(R.string.resource_yellow_filter_time_pick_end));
                    startTime = null;
                    endTimeDate = null;
                }
                isSelectAllTime = !isSelectAllTime;
                if(null != onAllTimeClickListener) {
                    onAllTimeClickListener.onAllTimeClick(isSelectAllTime);
                }
                break;
            case R.id.tv_start_time:
                isSelectStartTime = true;
                datePicker.show();
                break;
            case R.id.tv_end_time:
                isSelectStartTime = false;
                datePicker.show();
                break;
        }
    }

    /**
     * 开始时间设置字符串
     * @param startTimeStr
     */
    public void setStartTimeStr(String startTimeStr) {
        this.startTime = startTimeStr;
        if(!TextUtils.isEmpty(startTimeStr)){
            tvStartTime.setText(startTimeStr);
        }
    }

    /**
     * 结束时间设置字符串
     * @param endTimeStr
     */
    public void setEndTimeStr(String endTimeStr) {
        if(!TextUtils.isEmpty(endTimeStr)){
            tvEndTime.setText(endTimeStr);
        }
    }

    public void setEndTimeDate(Date endTimeDate){
        this.endTimeDate = endTimeDate;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy年MM月dd日");
        String endStr = simpleDateFormat.format(endTimeDate);
        tvEndTime.setText(endStr);
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
        if(!TextUtils.isEmpty(endTime)){
            tvEndTime.setText(endTime);
        }
    }

    /**
     * 获取开始时间的字符串
     * @return
     */
    public String getStartTime(){
        return startTime;
    }

    /**
     * 获取结束时间Date
     * @return
     */
    public Date getEndTimeDate(){
        return endTimeDate;
    }

    public String getEndTime() {
        return endTime;
    }

    /**
     * 获取是否选的是全部
     * @return
     */
    public boolean isSelectAllTime(){
        return isSelectAllTime;
    }

    /**
     * 设置是否全部选
     * @param isSelectAllTime
     */
    public void setSelectAllTime(boolean isSelectAllTime) {
        this.isSelectAllTime = isSelectAllTime;
        if(isSelectAllTime){
            tvTimeAll.setBackgroundDrawable(getResources().getDrawable(R.drawable.shape_resource_filter_item_select_bg));
            tvTimeAll.setTextColor(Color.WHITE);
        }else {
            tvTimeAll.setBackgroundDrawable(getResources().getDrawable(R.drawable.shape_resource_filter_item_unselect_bg));
            tvTimeAll.setTextColor(context.getResources().getColor(R.color.resource_main_text));
        }
    }

    /**
     * 关闭picker
     */
    public void dismissPicker(){
        if(null != datePicker){
            datePicker.dismiss();
        }
    }

    public void reset(){
        setSelectAllTime(true);
        tvStartTime.setText(context.getResources().getString(R.string.resource_yellow_filter_time_pick_start));
        tvEndTime.setText(context.getResources().getString(R.string.resource_yellow_filter_time_pick_end));
        startTime = null;
        endTimeDate = null;
        endTime = null;
    }

    public interface OnAllTimeClickListener{
        void onAllTimeClick(boolean isSelect);
    }

    public void setOnAllTimeClickListener(OnAllTimeClickListener listener) {
        this.onAllTimeClickListener = listener;
    }

    public interface OnPickerSelectedListener{
        void OnPickerSelected(Date date);
    }

    public void setOnPickerSelectedListener(OnPickerSelectedListener listener) {
        this.onPickerSelectedListener = listener;
    }
}
