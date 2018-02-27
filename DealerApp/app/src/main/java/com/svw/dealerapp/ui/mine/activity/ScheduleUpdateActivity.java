package com.svw.dealerapp.ui.mine.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.bigkoo.pickerview.OptionsPickerView;
import com.bigkoo.pickerview.TimePickerView;
import com.svw.dealerapp.R;
import com.svw.dealerapp.common.BaseActivity;
import com.svw.dealerapp.global.NewCustomerConstants;
import com.svw.dealerapp.ui.mine.adapter.ScheduleUpdateAdapter;
import com.svw.dealerapp.ui.mine.contract.ScheduleUpdateContract;
import com.svw.dealerapp.ui.entity.KeyValueListItemEntity;
import com.svw.dealerapp.ui.mine.model.ScheduleUpdateModel;
import com.svw.dealerapp.ui.mine.presenter.ScheduleUpdatePresenter;
import com.svw.dealerapp.ui.widget.CustomTimePickerView;
import com.svw.dealerapp.ui.widget.LoadingDialog;
import com.svw.dealerapp.util.DateUtils;
import com.svw.dealerapp.util.ToastUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by qinshi on 6/2/2017.
 */

public class ScheduleUpdateActivity extends BaseActivity implements ScheduleUpdateContract.View{

    private ImageView ivBack;
    private RecyclerView recyclerView;
    private CustomTimePickerView datePicker;
    private CustomTimePickerView timePicker;
    private OptionsPickerView scheduleTypePicker;
    private Button btnSubmit;

    private ScheduleUpdateAdapter adapter;
    private List<KeyValueListItemEntity> dataList = new ArrayList<>();
    private List<String> scheduleTypeValueList = new ArrayList<>();
    private List<String> scheduleTypeCodeList = new ArrayList<>();

    private String scheduleId;
    private int dealPosition;
    private String updateDate;
    private String updateTime;
    private String dataTimeStr;
    private String updateCode;
    private String appTypeName;
//    private String currentScheduleDate;
    private String currentScheduleTime;
//    private String currentScheduleDateTime;

    private LoadingDialog loadingDialog;
    private ScheduleUpdatePresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule_update);

        presenter = new ScheduleUpdatePresenter(this, new ScheduleUpdateModel());

        assignViews();

        Intent intent = getIntent();
        if(null != intent){
            scheduleId = intent.getStringExtra("scheduleId");
            dealPosition = intent.getIntExtra("position", -1);
            dataTimeStr = intent.getStringExtra("dateTimeStr");
            updateDate = DateUtils.dateFormat("yyyy.MM.dd", "yyyy-MM-dd'T'HH:mm:ss.SSSZ", dataTimeStr);
            updateTime = DateUtils.dateFormat("HH:mm", "yyyy-MM-dd'T'HH:mm:ss.SSSZ", dataTimeStr);
            updateCode = intent.getStringExtra("appTypeId");
            appTypeName = NewCustomerConstants.appointmentTypeMap.get(updateCode);

//            currentScheduleDate = updateDate;
            currentScheduleTime = updateTime;
//            currentScheduleDateTime = dataTimeStr;
        }

        initListData();

        adapter = new ScheduleUpdateAdapter(this, dataList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener(new ScheduleUpdateAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(KeyValueListItemEntity entity, int position) {
                switch (position){
                    case 0:
                        datePicker.show();
                        break;
                    case 1:
                        timePicker.show();
                        break;
                    case 2:
                        scheduleTypePicker.show();
                        break;
                }
            }
        });

        initPicker();

        ivBack.setOnClickListener(this);
        btnSubmit.setOnClickListener(this);
    }

    private void assignViews() {
        ivBack = (ImageView) findViewById(R.id.iv_back);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        btnSubmit = (Button) findViewById(R.id.btn_submit);
    }

    private void initPicker(){
        datePicker = new CustomTimePickerView.Builder(this, new CustomTimePickerView.OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy.MM.dd");
                String nowDate = simpleDateFormat.format(new Date());
                if(DateUtils.getDateMill("yyyy.MM.dd", simpleDateFormat.format(date)) <
                        DateUtils.getDateMill("yyyy.MM.dd", nowDate)){
                    ToastUtils.showToast(getResources().getString(R.string.mine_schedule_update_select_date_error));
                    Calendar calendar = DateUtils.getCalendar("yyyy.MM.dd", nowDate);
                    datePicker.setDate(calendar);
                }else {
                    updateDate = simpleDateFormat.format(date);
                    dataList.get(0).setValue(updateDate);
                    adapter.notifyDataSetChanged();
                    datePicker.dismiss();
                    if(updateDate.equals(nowDate)){
                        simpleDateFormat = new SimpleDateFormat("HH:mm");
                        if(DateUtils.getDateMill("HH:mm", updateTime) <
                                DateUtils.getDateMill("HH:mm", simpleDateFormat.format(new Date()))){
                            Calendar calendar = DateUtils.getCalendar("yyyy-MM-dd'T'HH:mm:ss.SSSZ", dataTimeStr);
                            timePicker.setDate(calendar);
                            dataList.get(1).setValue(currentScheduleTime);
                            adapter.notifyDataSetChanged();
                            updateTime = currentScheduleTime;
                        }
                    }
                }
            }
        }).setType(new boolean[]{true, true, true, false, false, false}).build();

        timePicker = new CustomTimePickerView.Builder(this, new CustomTimePickerView.OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {
                SimpleDateFormat simpleDateFormatDate = new SimpleDateFormat("yyyy.MM.dd");
                String nowDate = simpleDateFormatDate.format(new Date());
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");
                String nowTime = simpleDateFormat.format(new Date());
                if(updateDate.equals(nowDate)){
                    if(DateUtils.getDateMill("HH:mm", simpleDateFormat.format(date)) <
                            DateUtils.getDateMill("HH:mm", nowTime)){
                        ToastUtils.showToast(getResources().getString(R.string.mine_schedule_update_select_time_error));
                        Calendar calendar = DateUtils.getCalendar("HH:mm", nowTime);
                        timePicker.setDate(calendar);
                    }else {
                        updateTime = simpleDateFormat.format(date);
                        dataList.get(1).setValue(updateTime);
                        adapter.notifyDataSetChanged();
                        timePicker.dismiss();
                    }
                }else {
                    updateTime = simpleDateFormat.format(date);
                    dataList.get(1).setValue(updateTime);
                    adapter.notifyDataSetChanged();
                    timePicker.dismiss();
                }
            }
        }).setType(new boolean[]{false, false, false, true, true, false}).build();

        if(!TextUtils.isEmpty(dataTimeStr)) {
            Calendar calendar = DateUtils.getCalendar("yyyy-MM-dd'T'HH:mm:ss.SSSZ", dataTimeStr);
            datePicker.setDate(calendar);
            timePicker.setDate(calendar);
        }

        scheduleTypePicker = new OptionsPickerView.Builder(this, new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                updateCode = scheduleTypeCodeList.get(options1);
                dataList.get(2).setValue(scheduleTypeValueList.get(options1));
                adapter.notifyDataSetChanged();
            }
        }).build();
        scheduleTypePicker.setNPicker(scheduleTypeValueList, null, null);
        if(!TextUtils.isEmpty(updateCode)) {
            int scheduleTypePosition = 0;
            for (int i = 0; i < scheduleTypeCodeList.size(); i++) {
                if (updateCode.equals(scheduleTypeCodeList.get(i))){
                    scheduleTypePosition = i;
                    break;
                }
            }
            scheduleTypePicker.setSelectOptions(scheduleTypePosition);
        }
    }

    private void initListData(){
        dataList.add(new KeyValueListItemEntity(
                getResources().getString(R.string.mine_schedule_update_date), updateDate));
        dataList.add(new KeyValueListItemEntity(
                getResources().getString(R.string.mine_schedule_update_time), updateTime));
        dataList.add(new KeyValueListItemEntity(
                getResources().getString(R.string.mine_schedule_update_item), appTypeName));

        Iterator<Map.Entry<String, String>> iterator = NewCustomerConstants.appointmentTypeMap.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, String> entry = iterator.next();
            scheduleTypeCodeList.add(entry.getKey());
            scheduleTypeValueList.add(entry.getValue());
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_back:
                finish();
                break;
            case R.id.btn_submit:
                Map<String, Object> options = new HashMap<>();
                if(TextUtils.isEmpty(scheduleId)){
                    ToastUtils.showToast(getResources().getString(R.string.mine_schedule_update_schedule_id_empty));
                }
                if(TextUtils.isEmpty(updateDate)){
                    ToastUtils.showToast(getResources().getString(R.string.mine_schedule_update_select_date));
                }
                if(TextUtils.isEmpty(updateTime)){
                    ToastUtils.showToast(getResources().getString(R.string.mine_schedule_update_select_time));
                }
                if(TextUtils.isEmpty(updateCode)){
                    ToastUtils.showToast(getResources().getString(R.string.mine_schedule_update_select_item));
                }
                options.put("appmId", scheduleId);
                dataTimeStr = DateUtils.dateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ", "yyyy.MM.dd HH:mm", updateDate + " " + updateTime);
                options.put("appmDateStr", dataTimeStr);
                options.put("appmTypeId", updateCode);
                presenter.updateSchedule(this,options, dealPosition);
                break;
        }
    }

    @Override
    public void updateScheduleSuccess(int dealPosition) {
        Intent intent = new Intent();
        intent.putExtra("dealPosition", dealPosition);
        intent.putExtra("appmDateStr", dataTimeStr);
        intent.putExtra("appmTypeId", updateCode);
        setResult(0, intent);
        finish();
        ToastUtils.showToast(getResources().getString(R.string.mine_schedule_update_success));
    }

    @Override
    public void showServerErrorToast() {
        ToastUtils.showToast(getResources().getString(R.string.mine_schedule_update_fail));
    }

    @Override
    public void showTimeOutToast() {
        ToastUtils.showToast(getResources().getString(R.string.mine_schedule_update_timeout));
    }

    @Override
    public void showNetWorkErrorToast() {
        ToastUtils.showToast(getResources().getString(R.string.network_error));
    }

    @Override
    public void showLoadingDialog() {
        if(null == loadingDialog){
            loadingDialog = new LoadingDialog(this);
        }
        loadingDialog.show();
    }

    @Override
    public void hideLoadingDialog() {
        if(null != loadingDialog){
            loadingDialog.dismiss();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(null != datePicker){
            datePicker.dismiss();
        }
        if(null != timePicker){
            timePicker.dismiss();
        }
        if(null != scheduleTypePicker){
            scheduleTypePicker.dismiss();
        }
        if(null != loadingDialog){
            loadingDialog.dismiss();
        }
        if(null != presenter) {
            presenter.onDestroy();
        }
    }
}
