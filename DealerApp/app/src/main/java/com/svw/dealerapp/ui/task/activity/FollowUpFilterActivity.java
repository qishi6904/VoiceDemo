package com.svw.dealerapp.ui.task.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.svw.dealerapp.R;
import com.svw.dealerapp.common.BaseActivity;
import com.svw.dealerapp.ui.task.entity.FollowUpFilterEntity;
import com.svw.dealerapp.ui.task.entity.FollowUpFilterIntentEntity;
import com.svw.dealerapp.ui.widget.DatePeriodPickerView;
import com.svw.dealerapp.util.DateUtils;
import com.svw.dealerapp.util.GsonUtils;
import com.svw.dealerapp.util.UserInfoUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Date;

/**
 * Created by qinshi on 1/2/2018.
 */

public class FollowUpFilterActivity extends BaseActivity {

    private TextView tvTitle;
    private TextView tvCancel;
    private TextView tvReset;
    private TextView tvConfirm;
    private DatePeriodPickerView followUpPicker;
    private boolean canReset;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_follow_up_filter);

        tvTitle = (TextView) findViewById(R.id.tv_title);
        tvCancel = (TextView) findViewById(R.id.tv_cancel);
        tvReset = (TextView) findViewById(R.id.tv_reset);
        tvConfirm = (TextView) findViewById(R.id.tv_confirm);
        followUpPicker = (DatePeriodPickerView) findViewById(R.id.dppv_follow_up_time);

        tvTitle.setText(getResources().getString(R.string.task_follow_up_filter_title));

        Intent intent = getIntent();
        if(null != intent){
            FollowUpFilterIntentEntity filterIntentEntity = intent.getParcelableExtra("filterIntentEntity");
            initSelectItem(filterIntentEntity);
        }

        tvCancel.setOnClickListener(this);
        tvReset.setOnClickListener(this);
        tvConfirm.setOnClickListener(this);

        followUpPicker.setOnAllTimeClickListener(new DatePeriodPickerView.OnAllTimeClickListener() {
            @Override
            public void onAllTimeClick(boolean isSelect) {
                if(isSelect) {
                    checkAndSetCanReset();
                }
            }
        });

        followUpPicker.setOnPickerSelectedListener(new DatePeriodPickerView.OnPickerSelectedListener() {
            @Override
            public void OnPickerSelected(Date date) {
                setCanReset(true);
            }
        });
    }

    /**
     * 初始化选中的item
     * @param filterIntentEntity
     */
    private void initSelectItem(FollowUpFilterIntentEntity filterIntentEntity){
        if(null == filterIntentEntity ||
                (TextUtils.isEmpty(filterIntentEntity.getFollowupDateFrom()) && TextUtils.isEmpty(filterIntentEntity.getFollowupDateTo())) ||
                filterIntentEntity.isSelectAllTime()) {
            followUpPicker.setSelectAllTime(true);
            setCanReset(false);
        }else {
            if(!TextUtils.isEmpty(filterIntentEntity.getFollowupDateFrom())){
                followUpPicker.setStartTimeStr(filterIntentEntity.getFollowupDateFrom());
            }

            if(null != filterIntentEntity.getFollowupDateTo()){
                followUpPicker.setEndTime(filterIntentEntity.getFollowupDateTo());
            }
            followUpPicker.setSelectAllTime(false);
            setCanReset(true);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_cancel:
                finish();
                break;
            case R.id.tv_reset:
                if(canReset){
                    followUpPicker.reset();
                    setCanReset(false);
                }
                break;
            case R.id.tv_confirm:
                Intent intent = new Intent();
                intent.putExtra("filterEntity", getFilterEntity());
                FollowUpFilterIntentEntity intentEntity = new FollowUpFilterIntentEntity(followUpPicker.getStartTime(),
                        followUpPicker.getEndTime(), followUpPicker.isSelectAllTime());
                intent.putExtra("filterIntentEntity", intentEntity);
                setResult(Activity.RESULT_OK, intent);
                finish();
                overridePendingTransition(R.anim.activity_traffic_filter_in, R.anim.activity_traffic_filter_out);
                break;
        }
    }


    private FollowUpFilterEntity getFilterEntity(){
        FollowUpFilterEntity filterEntity = new FollowUpFilterEntity();
        if(!followUpPicker.isSelectAllTime()){
            if(!TextUtils.isEmpty(followUpPicker.getStartTime())){
                String startFormatTime = DateUtils.dateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ", "yyyy年MM月dd日",
                        followUpPicker.getStartTime());
                if(!TextUtils.isEmpty(startFormatTime)) {
                    filterEntity.setFollowupDateFrom(startFormatTime);
                }
            }

            if(null != followUpPicker.getEndTimeDate()) {
                String endTime = DateUtils.addDay("yyyy-MM-dd'T'HH:mm:ss.SSSZ",
                        followUpPicker.getEndTimeDate(), 0);
                if(!TextUtils.isEmpty(endTime)){
                    filterEntity.setFollowupDateTo(endTime);
                }
            }

        }
        return filterEntity;
    }

    private void setCanReset(boolean canReset) {
        this.canReset = canReset;
        if(canReset) {
            tvReset.setTextColor(getResources().getColor(R.color.resource_main_text));
        }else {
            tvReset.setTextColor(getResources().getColor(R.color.gray_999));
        }
    }

    private void checkAndSetCanReset(){
        if(followUpPicker.isSelectAllTime()){
            setCanReset(false);
        }else {
            setCanReset(true);
        }
    }
}
