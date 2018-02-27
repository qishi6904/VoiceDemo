package com.svw.dealerapp.ui.resource.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.svw.dealerapp.R;
import com.svw.dealerapp.common.BaseActivity;
import com.svw.dealerapp.entity.resource.SMYCTransferSalesEntity;
import com.svw.dealerapp.entity.resource.YCTransferSalesEntity;
import com.svw.dealerapp.ui.resource.fragment.SalesFilterListFragment;
import com.svw.dealerapp.util.ToastUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by qinshi on 11/21/2017.
 */

public class SalesFilterActivity  extends BaseActivity implements View.OnClickListener {

    private TextView tvTitle;
    private TextView tvCancel;
    private TextView tvReset;
    private TextView tvConfirm;
    private FrameLayout flSalesFilter;
    private SalesFilterListFragment fragment;
    private List<String> defaultUserIdList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sales_filter);
        assignViews();
        tvTitle.setText(getResources().getString(R.string.resource_sales_filter_title));

        if(null != getIntent()){
            String userId = getIntent().getStringExtra("userId");
            if(!TextUtils.isEmpty(userId)) {
                String[] userIdArr = userId.split(",");
                defaultUserIdList = Arrays.asList(userIdArr);
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
        flSalesFilter = (FrameLayout) findViewById(R.id.fl_sales_filter);

        fragment = new SalesFilterListFragment();
        getSupportFragmentManager().beginTransaction().replace(
                R.id.fl_sales_filter, fragment).commit();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_cancel:
                finish();
                overridePendingTransition(R.anim.activity_traffic_filter_in, R.anim.activity_traffic_filter_out);
                break;
            case R.id.tv_confirm:
                List<SMYCTransferSalesEntity> selectList = fragment.getSelectSales();
                Intent intent = new Intent();
                StringBuilder userIdBuilder = new StringBuilder();
                StringBuilder userNameBuilder = new StringBuilder();
                if(selectList.size() == 0 || fragment.isSelectAll()) {
                    userIdBuilder.append("");
                    userNameBuilder.append(getResources().getString(R.string.resource_sales_filter_all));
                }else {
                    for (int i = 0; i < selectList.size(); i++){
                        String userId = selectList.get(i).getPhone();
                        if(!TextUtils.isEmpty(userId)) {
                            userIdBuilder.append(userId);
                            userNameBuilder.append(selectList.get(i).getDisplayName());
                            if(i != selectList.size() - 1){
                                userIdBuilder.append(",");
                                userNameBuilder.append("、");
                            }
                        }
                    }
                }
                intent.putExtra("userId", userIdBuilder.toString());
                intent.putExtra("userName", userNameBuilder.toString());
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
