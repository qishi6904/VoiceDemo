package com.svw.dealerapp.common.drawer;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.svw.dealerapp.R;
import com.svw.dealerapp.common.BaseActivity;
import com.svw.dealerapp.util.ScreenUtils;

/**
 * Created by qinshi on 1/11/2018.
 */

public abstract class RdDrawerActivity extends BaseActivity {

    private DrawerLayout drawerLayout;
    private FrameLayout flMainContent;
    private FrameLayout flLeftDrawer;
    private FrameLayout flRightDrawer;

    DrawerLayout.LayoutParams leftDrawerLayoutParams;
    DrawerLayout.LayoutParams rightDrawerLayoutParams;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rd_activity_drawer);

        assignViews();
    }

    private void assignViews() {
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        flMainContent = (FrameLayout) findViewById(R.id.fl_main_content);
        flLeftDrawer = (FrameLayout) findViewById(R.id.fl_left_drawer);
        flRightDrawer = (FrameLayout) findViewById(R.id.fl_right_drawer);

        Fragment mainContentFragment = getMainContentFragment();
        if(null != mainContentFragment) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fl_main_content, mainContentFragment).commit();
        }

        //设置左侧边栏的宽度为屏幕宽度的百分比
        int screenWidth = ScreenUtils.getScreenWidth(this);
        int leftDrawerWidth = (int)(screenWidth * 0.8);
        leftDrawerLayoutParams = (DrawerLayout.LayoutParams) flLeftDrawer.getLayoutParams();
        leftDrawerLayoutParams.width = leftDrawerWidth;
        flLeftDrawer.setLayoutParams(leftDrawerLayoutParams);

        Fragment leftDrawerFragment = getLeftDrawerFragment();
        if(null != leftDrawerFragment) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fl_left_drawer, leftDrawerFragment).commit();
        }else {
            disableLeftDrawer();
        }

        //设置右侧边栏的宽度为屏幕宽度的百分比
        rightDrawerLayoutParams = (DrawerLayout.LayoutParams) flRightDrawer.getLayoutParams();
        int rightDrawerWidth = (int)(screenWidth * 0.8);
        rightDrawerLayoutParams.width = rightDrawerWidth;
        flRightDrawer.setLayoutParams(rightDrawerLayoutParams);

        Fragment rightDrawerFragment = getRightDrawerFragment();
        if(null != rightDrawerFragment) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fl_right_drawer, rightDrawerFragment).commit();
        }else {
            disableRightDrawer();
        }
    }

    protected void disableLeftDrawer(){
        drawerLayout.removeView(flLeftDrawer);
    }

    protected void enableLeftDrawer(){
        ViewGroup parent = (ViewGroup) flLeftDrawer.getParent();
        if(null == parent) {
            drawerLayout.addView(flLeftDrawer);
        }
    }

    protected void disableRightDrawer(){
        drawerLayout.removeView(flRightDrawer);
    }

    protected void enableRightDrawer(){
        ViewGroup parent = (ViewGroup) flRightDrawer.getParent();
        if(null == parent) {
            drawerLayout.addView(flRightDrawer);
        }
    }

    public void toggleLeftDrawer(boolean isToggle) {
        if(isToggle){
            drawerLayout.openDrawer(Gravity.START);
        }else {
            drawerLayout.closeDrawer(Gravity.START);
        }
    }

    public boolean isLeftDrawerOpen(){
        return drawerLayout.isDrawerOpen(Gravity.START);
    }

    public void toggleRightDrawer(boolean isToggle) {
        if(isToggle){
            drawerLayout.openDrawer(Gravity.END);
        }else {
            drawerLayout.closeDrawer(Gravity.END);
        }
    }

    public boolean isRightDrawerOpen(){
        return drawerLayout.isDrawerOpen(Gravity.END);
    }

    protected Fragment getRightDrawerFragment() {
        return null;
    }

    protected Fragment getLeftDrawerFragment() {
        return null;
    }

    protected abstract Fragment getMainContentFragment();

}
