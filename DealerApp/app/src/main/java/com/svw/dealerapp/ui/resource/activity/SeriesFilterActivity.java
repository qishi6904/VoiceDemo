package com.svw.dealerapp.ui.resource.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.svw.dealerapp.R;
import com.svw.dealerapp.common.BaseActivity;
import com.svw.dealerapp.ui.resource.entity.SeriesFilterEntity;
import com.svw.dealerapp.ui.resource.fragment.SeriesFilterListFragment;

import java.util.Arrays;
import java.util.List;

/**
 * Created by qinshi on 12/6/2017.
 */

public class SeriesFilterActivity extends BaseActivity implements View.OnClickListener{

    private TextView tvTitle;
    private TextView tvCancel;
    private TextView tvReset;
    private TextView tvConfirm;
    private FrameLayout flSeriesFilter;
    private SeriesFilterListFragment fragment;
    private List<String> defaultUserIdList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seires_filter);

        assignViews();

        tvTitle.setText(getResources().getString(R.string.resource_series_filter_title));

        if(null != getIntent()){
            String seriesCode = getIntent().getStringExtra("seriesCode");
            if(!TextUtils.isEmpty(seriesCode)) {
                String[] seriesCodeArr = seriesCode.split(",");
                defaultUserIdList = Arrays.asList(seriesCodeArr);
            }
        }

        tvCancel.setOnClickListener(this);
        tvConfirm.setOnClickListener(this);
        tvReset.setOnClickListener(this);
    }

    private void assignViews() {
        tvTitle = (TextView) findViewById(R.id.tv_title);
        tvCancel = (TextView) findViewById(R.id.tv_cancel);
        tvReset = (TextView) findViewById(R.id.tv_reset);
        tvConfirm = (TextView) findViewById(R.id.tv_confirm);
        flSeriesFilter = (FrameLayout) findViewById(R.id.fl_series_filter);

        fragment = new SeriesFilterListFragment();
        getSupportFragmentManager().beginTransaction().replace(
                R.id.fl_series_filter, fragment).commit();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_cancel:
                finish();
                overridePendingTransition(R.anim.activity_traffic_filter_in, R.anim.activity_traffic_filter_out);
                break;
            case R.id.tv_confirm:
                List<SeriesFilterEntity> selectList = fragment.getSelectSales();
                Intent intent = new Intent();
                StringBuilder seriesCodeBuilder = new StringBuilder();
                StringBuilder seriesNameBuilder = new StringBuilder();
                if(selectList.size() == 0 || fragment.isSelectAll()) {
                    seriesCodeBuilder.append("");
                    seriesNameBuilder.append(getResources().getString(R.string.resource_sales_filter_all));
                }else {
                    for (int i = 0; i < selectList.size(); i++){
                        String seriesCode = selectList.get(i).getSeriesCode();
                        if(!TextUtils.isEmpty(seriesCode)) {
                            seriesCodeBuilder.append(seriesCode);
                            seriesNameBuilder.append(selectList.get(i).getSeriesName());
                            if(i != selectList.size() - 1){
                                seriesCodeBuilder.append(",");
                                seriesNameBuilder.append("、");
                            }
                        }
                    }
                }
                intent.putExtra("seriesCode", seriesCodeBuilder.toString());
                intent.putExtra("seriesName", seriesNameBuilder.toString());
                setResult(0, intent);
                finish();
                overridePendingTransition(R.anim.activity_traffic_filter_in, R.anim.activity_traffic_filter_out);
                break;
            case R.id.tv_reset:
                fragment.clearSelectSeries();
                if(null != defaultUserIdList) {
                    defaultUserIdList = null;
                }
                fragment.refreshList();
                break;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.activity_traffic_filter_in, R.anim.activity_traffic_filter_out);
    }

    public List<String> getDefaultUserIdList(){
        return defaultUserIdList;
    }

}
