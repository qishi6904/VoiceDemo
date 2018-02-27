package com.svw.dealerapp.ui.usedcar;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.svw.dealerapp.R;
import com.svw.dealerapp.common.BaseActivity;

/**
 * Created by lijinkui on 2018/1/4.
 */

public class UsedCarActivity extends BaseActivity {

    private UsedCarFragment usedCarFragment;
    private String url;
    private TextView title;
    private ImageView backKey;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usedcar);
        title = (TextView) findViewById(R.id.usedcar_title_bar_title_textview);
        title.setText("二手车");
        backKey = (ImageView) findViewById(R.id.usedcar_title_bar_left_iv);
        backKey.setOnClickListener(this);
        url = getIntent().getStringExtra("URL");
        addWebFragment(url);
    }

    private void addWebFragment(String url) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        usedCarFragment = new UsedCarFragment();
        Bundle bundle = new Bundle();
        bundle.putString("URL", url);
        usedCarFragment.setArguments(bundle);

        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        transaction.add(R.id.sub_webview, usedCarFragment);
        transaction.commit();
    }

    @Override
    public void onBackPressed() {
        usedCarFragment.onBackPressed();
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.usedcar_title_bar_left_iv:
                if(usedCarFragment.getWebView().canGoBack()){
                    usedCarFragment.getWebView().goBack();
                }else {
                    usedCarFragment.onBackPressed();
                }
                break;
            default:
                break;
        }
    }

}
