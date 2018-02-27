package com.svw.dealerapp.ui.order.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.svw.dealerapp.R;
import com.svw.dealerapp.common.BaseActivity;
import com.svw.dealerapp.entity.order.AppraiserEntity;
import com.svw.dealerapp.ui.order.fragment.AppraiserListFragment;
import com.svw.dealerapp.util.ToastUtils;

/**
 * Created by qinshi on 11/22/2017.
 */

public class AppraiserListActivity extends BaseActivity implements View.OnClickListener {

    private TextView tvTitle;
    private TextView tvCancel;
    private TextView tvConfirm;
    private FrameLayout flAppraiserList;
    private AppraiserListFragment fragment;

    private AppraiserEntity.AppraiserInfoEntity defaultAppraiserEntity;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appraiser_list);
        assignViews();
        tvTitle.setText(getResources().getString(R.string.appraiser_list_title));

        if(null != getIntent()) {
            defaultAppraiserEntity = getIntent().getParcelableExtra("defaultAppraiserInfoEntity");
        }

        tvCancel.setOnClickListener(this);
        tvConfirm.setOnClickListener(this);
    }

    private void assignViews() {
        tvTitle = (TextView) findViewById(R.id.tv_title);
        tvCancel = (TextView) findViewById(R.id.tv_cancel);
        tvConfirm = (TextView) findViewById(R.id.tv_confirm);
        flAppraiserList = (FrameLayout) findViewById(R.id.fl_appraise);

        fragment = new AppraiserListFragment();
        getSupportFragmentManager().beginTransaction().replace(
                R.id.fl_appraise, fragment).commit();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_cancel:
                finish();
                overridePendingTransition(R.anim.activity_traffic_filter_in, R.anim.activity_traffic_filter_out);
                break;
            case R.id.tv_confirm:
                AppraiserEntity.AppraiserInfoEntity appraiserInfoEntity = fragment.getAppraiser();
                if(null == appraiserInfoEntity) {
                    ToastUtils.showToast(getResources().getString(R.string.appraiser_select_appraiser_tip));
                }else {
                    Intent intent = new Intent();
                    intent.putExtra("defaultAppraiserInfoEntity", appraiserInfoEntity);
                    setResult(RESULT_OK, intent);
                    finish();
                    overridePendingTransition(R.anim.activity_traffic_filter_in, R.anim.activity_traffic_filter_out);
                }
                break;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.activity_traffic_filter_in, R.anim.activity_traffic_filter_out);
    }

    public String getDefaultAppraiserId(){
        String appraiserId = null;
        if(null != defaultAppraiserEntity){
            appraiserId = defaultAppraiserEntity.getAppraiserId();
        }
        return appraiserId;
    }
}
