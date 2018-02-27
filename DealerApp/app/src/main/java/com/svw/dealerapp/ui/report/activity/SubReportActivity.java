package com.svw.dealerapp.ui.report.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.widget.LinearLayout;

import com.svw.dealerapp.R;
import com.svw.dealerapp.common.BaseActivity;
import com.svw.dealerapp.ui.report.fragment.SubReportFragment;

/**
 * Created by lijinkui on 2017/8/4.
 */

public class SubReportActivity extends BaseActivity {

    private LinearLayout subWebView;
    private SubReportFragment subReportFragment;
    private String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subreport_webview);
        subWebView = (LinearLayout) findViewById(R.id.sub_webview);
        url = getIntent().getStringExtra("URL");
        addWebFragment(url);
    }

    private void addWebFragment(String url) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        subReportFragment = new SubReportFragment();
        Bundle bundle = new Bundle();
        bundle.putString("URL", url);
        subReportFragment.setArguments(bundle);

        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        transaction.add(R.id.sub_webview, subReportFragment);
        transaction.commit();
    }

//    private void removeWebFragment(Fragment fragment) {
//        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
//        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
//        transaction.remove(fragment);
//        transaction.commit();
//    }
//
    @Override
    public void onBackPressed() {
//        super.onBackPressed();
        subReportFragment.onBackPressed();
    }
//
//    @Override
//    public void onConfigurationChanged(Configuration newConfig) {
//        removeWebFragment(baseWebViewFragment);
//        addWebFragment(url);
//        super.onConfigurationChanged(newConfig);
//    }
}
