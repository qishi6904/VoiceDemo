package com.svw.dealerapp.ui.splash;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.Button;

import com.svw.dealerapp.DealerApp;
import com.svw.dealerapp.R;
import com.svw.dealerapp.common.BaseActivity;
import com.svw.dealerapp.ui.dictionary.DictionaryContract;
import com.svw.dealerapp.ui.dictionary.DictionaryModel;
import com.svw.dealerapp.ui.dictionary.DictionaryPresenter;
import com.svw.dealerapp.ui.login.LoginActivity;
import com.svw.dealerapp.ui.main.activity.RdMainActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lijinkui on 2017/7/5.
 */

public class SplashActivity extends BaseActivity implements DictionaryContract.View{

    private ViewPager viewPager;
    private Button btStart;
    private Button btSkip;
    private List<SplashSingleFragment> fragmentList;
    private SharedPreferences firstInstallSP;

    private DictionaryPresenter dictionaryPresenter;

    public  String[] SPLASH_LOCAL_URL = new String[5];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        dictionaryPresenter = new DictionaryPresenter(new DictionaryModel(),this);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(null != dictionaryPresenter){
            dictionaryPresenter.onDestroy();
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_splash;
    }

    @Override
    public void initData() {
        super.initData();
        fragmentList = new ArrayList<>();

        firstInstallSP= getSharedPreferences("appSettingSP", Activity.MODE_PRIVATE);
        String isFirstInstall = firstInstallSP.getString("isFirst", "");
        if("".equals(isFirstInstall)){
            //first install
            SPLASH_LOCAL_URL[0] = "android.resource://" + getPackageName() + "/" + R.mipmap.welcome_page1;
            SPLASH_LOCAL_URL[1] = "android.resource://" + getPackageName() + "/" + R.mipmap.welcome_page2;
            SPLASH_LOCAL_URL[2] = "android.resource://" + getPackageName() + "/" + R.mipmap.welcome_page3;
            SPLASH_LOCAL_URL[3] = "android.resource://" + getPackageName() + "/" + R.mipmap.welcome_page4;
            SPLASH_LOCAL_URL[4] = "android.resource://" + getPackageName() + "/" + R.mipmap.welcome_page5;

            //支持动态加载欢迎界面
//            String url1 = "http://pic1.win4000.com/mobile/5/53901e85ecc3b.jpg";
//            String url2 = "http://pic1.win4000.com/mobile/5/53901e8bcf736.jpg";
//            String url3 = "http://pic1.win4000.com/mobile/5/53901e8f6b233.jpg";

            for(int i = 0; i < SPLASH_LOCAL_URL.length; i++){
                SplashSingleFragment singleFragment = new SplashSingleFragment();
                Bundle bundle = new Bundle();
                bundle.putString("url", SPLASH_LOCAL_URL[i]);
                singleFragment.setArguments(bundle);
                fragmentList.add(singleFragment);
            }
        }
    }

    @Override
    public void initView() {
        super.initView();
        viewPager = (ViewPager) findViewById(R.id.vp_guide);
        btStart = (Button) findViewById(R.id.bt_start);
        btSkip = (Button) findViewById(R.id.bt_skip);
        viewPager.setOffscreenPageLimit(3);
        viewPager.setAdapter(new SplashVPAdapter(getSupportFragmentManager()));
        viewPager.addOnPageChangeListener(new SplashVPChangeListener());
        btStart.setOnClickListener(this);
        btSkip.setOnClickListener(this);

        String isFirstInstall = firstInstallSP.getString("isFirst", "");
        if("n".equals(isFirstInstall)){
//            Handler handler = new Handler();
//            handler.postDelayed(new Runnable() {
//                @Override
//                public void run() {
//                    /**
//                     *要执行的操作
//                     */
//                    //下载字典数据
//                    downloadDictionary();
//
//                    Intent newIntent = new Intent(DealerApp.getContext(), MainActivity.class);
//                    startActivity(newIntent);
//
//                }
//            }, 1000);//1秒后执行Runnable中的run方法

            btSkip.setVisibility(View.INVISIBLE);
            //下载字典数据
            downloadDictionary();
        }else if("".equals(isFirstInstall)){
            btSkip.setVisibility(View.VISIBLE);
        }

    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.bt_start:
                clickStartButton();
                break;
            case R.id.bt_skip:
                clickStartButton();
                break;
        }
    }

    private void clickStartButton(){
        //下载字典数据
        downloadDictionary();

        //实例化SharedPreferences.Editor对象
        SharedPreferences.Editor editor = firstInstallSP.edit();
        editor.putString("isFirst", "n");
        editor.commit();
    }

    private void downloadDictionary(){
        Cursor cursor = DealerApp.dbHelper.rawQuery("select * from Dictionary",null);
        String dictVersion = "0";
        if(cursor !=null){
            if(cursor.moveToNext()){
                dictVersion = cursor.getString(cursor.getColumnIndex("version"));
            }
            cursor.close();
        }
        dictionaryPresenter.getDictionary(dictVersion);
    }

    @Override
    public void getDictSuccess() {
        Intent newIntent = new Intent(this, RdMainActivity.class);
        startActivity(newIntent);
    }

    @Override
    public void getDictFail() {
        Intent newIntent = new Intent(this, LoginActivity.class);
        startActivity(newIntent);
    }

    /**
     * ViewPager适配器
     */
    private class SplashVPAdapter extends FragmentPagerAdapter {


        public SplashVPAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return fragmentList.get(position);
        }

        @Override
        public int getCount() {
            return fragmentList.size();
        }
    }

    private class SplashVPChangeListener implements ViewPager.OnPageChangeListener {

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            btStart.setVisibility(View.GONE);
            if(position == SPLASH_LOCAL_URL.length-1){
                btStart.setVisibility(View.VISIBLE);
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    }


}
