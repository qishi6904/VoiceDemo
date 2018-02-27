package com.svw.dealerapp.ui.resource.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.svw.dealerapp.R;
import com.svw.dealerapp.common.BaseActivity;
import com.svw.dealerapp.ui.resource.entity.YellowCardFilterEntity;
import com.svw.dealerapp.ui.resource.fragment.RdYellowCardSearchFragment;
import com.svw.dealerapp.util.GsonUtils;
import com.svw.dealerapp.util.SoftInputUtils;
import com.svw.dealerapp.util.TalkingDataUtils;
import com.svw.dealerapp.util.ToastUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;

/**
 * Created by qinshi on 5/18/2017.
 */

public class YellowCardSearchActivity extends BaseActivity {

    private TextView tvTitle;
    private EditText etSearch;
    private ImageView ivClear;
    private TextView tvCancel;
    private TextView tvSearchResult;
    private FrameLayout flYellowCardSearch;
    private ImageView ivBack;
    private String leadId, leadMobile;

    private RdYellowCardSearchFragment fragment;
    private RefreshYCSearchReceiver refreshYCSearchReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_yellow_card_search);

        Intent intent = getIntent();
        leadId = intent.getStringExtra("leadId");
        leadMobile = intent.getStringExtra("leadMobile");

        assignViews();

        ivClear.setOnClickListener(this);
        tvCancel.setOnClickListener(this);
        ivBack.setOnClickListener(this);

        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!TextUtils.isEmpty(s.toString())) {
                    ivClear.setVisibility(View.VISIBLE);
                } else {
                    ivClear.setVisibility(View.INVISIBLE);
                }
            }
        });

        etSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEND || (event != null && event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) {
                    SoftInputUtils.hideSoftInput(YellowCardSearchActivity.this, etSearch);
                    searchYellowCard();
                }
                return false;
            }
        });

        if (!TextUtils.isEmpty(leadId)) {
            etSearch.setText(leadMobile);
            searchYellowCard();
        }

        //注册广播
        refreshYCSearchReceiver = new RefreshYCSearchReceiver();
        IntentFilter filter = new IntentFilter("com.svw.dealerapp.search.yellowcard.refresh");
        this.registerReceiver(refreshYCSearchReceiver, filter);
    }

    private void assignViews() {
        tvTitle = (TextView) findViewById(R.id.tv_title);
        etSearch = (EditText) findViewById(R.id.et_search);
        ivClear = (ImageView) findViewById(R.id.iv_clear);
        tvCancel = (TextView) findViewById(R.id.tv_cancel);
        tvSearchResult = (TextView) findViewById(R.id.tv_search_result);
        flYellowCardSearch = (FrameLayout) findViewById(R.id.fl_yellow_card_search);
        ivBack = (ImageView) findViewById(R.id.iv_back);

        HashMap<String, String> param1 = new HashMap<>();
        param1.put("leadId", leadId);
        fragment = new RdYellowCardSearchFragment().newInstance(param1);
        getSupportFragmentManager().beginTransaction().replace(R.id.fl_yellow_card_search, fragment).commit();
    }

    /**
     * 设置搜索到的总条数
     *
     * @param total
     */
    public void setSearchTotal(String total) {
        tvSearchResult.setVisibility(View.VISIBLE);
        tvSearchResult.setText(getResources().getString(R.string.resource_yellow_search_result_one)
                + total + getResources().getString(R.string.resource_yellow_search_result_two));
    }

    /**
     * 隐藏搜索到的总条数的TextView，搜索无结果或失败时调用
     */
    public void hideSearchTotalTextView() {
        tvSearchResult.setVisibility(View.GONE);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_clear:
                etSearch.setText("");
                break;
            case R.id.tv_cancel:
                finish();
                break;
            case R.id.iv_back:
                finish();
                break;
        }
    }

    private void searchYellowCard() {
        if (!TextUtils.isEmpty(etSearch.getText())) {
            final YellowCardFilterEntity filterEntity = new YellowCardFilterEntity();
            filterEntity.setCustMobile(etSearch.getText().toString().trim());
            if (!TextUtils.isEmpty(leadId)) {
                filterEntity.setNeedFollowup("1");
            }
            try {
                final String filterString = URLEncoder.encode(GsonUtils.changeEntityToJsonStr(filterEntity), "utf-8");
                etSearch.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        TalkingDataUtils.onEvent(YellowCardSearchActivity.this, "提交搜索条件", "搜索潜客");
                        fragment.searchYellowCard(filterString, filterEntity);
//                        setSearchTotal("0");
                    }
                }, 500);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        } else {
            ToastUtils.showToast(getResources().getString(R.string.resource_yellow_search_content_empty));
        }
    }

    /**
     * 通知刷新查询列表
     */
    private class RefreshYCSearchReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            searchYellowCard();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(null != refreshYCSearchReceiver) {
            unregisterReceiver(refreshYCSearchReceiver);
        }
    }
}
