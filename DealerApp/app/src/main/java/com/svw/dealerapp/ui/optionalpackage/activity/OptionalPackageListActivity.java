package com.svw.dealerapp.ui.optionalpackage.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.alibaba.sdk.android.push.common.util.JSONUtils;
import com.svw.dealerapp.R;
import com.svw.dealerapp.common.BaseActivity;
import com.svw.dealerapp.entity.optionalpackage.OptionalPackageEntity;
import com.svw.dealerapp.ui.optionalpackage.fragment.OptionalPackageListFragment;
import com.svw.dealerapp.util.GsonUtils;
import com.svw.dealerapp.util.ToastUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by qinshi on 11/30/2017.
 */

public class OptionalPackageListActivity extends BaseActivity implements View.OnClickListener {

    private OptionalPackageListFragment fragment;
    private String[] requestParams;
    private ArrayList<OptionalPackageEntity.OptionListBean> selectData;

    private TextView tvTitle;
    private TextView tvCancel;
    private TextView tvConfirm;
    private FrameLayout flOptionalPackage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_optional_package_list);
        assignViews();

        if(null != getIntent()){
            String serId = getIntent().getStringExtra("serId");
            String modelId = getIntent().getStringExtra("modelId");
            String color = getIntent().getStringExtra("color");
            selectData = getIntent().getParcelableArrayListExtra("selectData");
            requestParams = new String[]{serId, modelId, color};
        }

    }

    private void assignViews() {
        tvTitle = (TextView) findViewById(R.id.tv_title);
        tvCancel = (TextView) findViewById(R.id.tv_cancel);
        tvConfirm = (TextView) findViewById(R.id.tv_confirm);
        flOptionalPackage = (FrameLayout) findViewById(R.id.fl_optional_package);

        tvTitle.setText(getResources().getString(R.string.optional_package_select_title));

        fragment = new OptionalPackageListFragment();
        getSupportFragmentManager().beginTransaction().replace(
                R.id.fl_optional_package, fragment).commit();

        tvCancel.setOnClickListener(this);
        tvConfirm.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_cancel:
                finish();
                overridePendingTransition(R.anim.activity_traffic_filter_in, R.anim.activity_traffic_filter_out);
                break;
            case R.id.tv_confirm:
                selectData = fragment.getSelectData();
//                if(null == selectData || selectData.size() == 0){
//                    ToastUtils.showToast(getResources().getString(R.string.optional_package_select_empty));
//                    return;
//                }
                Intent intent = new Intent();
                intent.putParcelableArrayListExtra("selectData", selectData);
                setResult(RESULT_OK, intent);
                finish();
                break;
        }
    }

    /**
     * 获取请求参数
     * @return
     */
    public String[] getRequestParams(){
        return requestParams;
    }

    /**
     * 获取上次选中的数据
     * @return
     */
    public List<OptionalPackageEntity.OptionListBean> getSelectData(){
        return selectData;
    }

}
