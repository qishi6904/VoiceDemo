package com.svw.dealerapp.ui.mine.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.svw.dealerapp.R;
import com.svw.dealerapp.common.BaseActivity;
import com.svw.dealerapp.util.PackageUtils;
import com.svw.dealerapp.util.TalkingDataUtils;

/**
 * Created by qinshi on 6/2/2017.
 */

public class AboutActivity extends BaseActivity {

    private TextView tvVersion;
    private ImageView ivBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        tvVersion = (TextView) findViewById(R.id.tv_version);
        ivBack = (ImageView) findViewById(R.id.iv_back);

        String versionName = PackageUtils.getAppVersionName(this);
        int versionCode = PackageUtils.getAppVersionNumber(this);
        tvVersion.setText(getResources().getString(R.string.mine_about_version) + " " + versionName + " (" + versionCode + ")");

        ivBack.setOnClickListener(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        TalkingDataUtils.onPageStart(this,"我的-关于");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_back:
                finish();
                break;
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        TalkingDataUtils.onPageEnd(this,"我的-关于");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
