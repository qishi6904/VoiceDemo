
package com.svw.dealerapp.common;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

/**
 * Created by ibm on 2017/4/20.
 */
public class BaseFragment extends Fragment implements BaseFuncIml {

    private View mContentView;
    private ViewGroup container;

    private boolean isShowForUser = false;
    private boolean isFirstStart = true;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        initData();
        initView();
        initListener();
        initLoad();
        this.container = container;
        return mContentView;
    }

    public void setContentView(int viewId) {
        this.mContentView = getActivity().getLayoutInflater().inflate(viewId, container, false);
    }

    public View getContentView() {
        return mContentView;
    }

    protected void showShortToast(String pMsg) {
        Toast.makeText(getActivity(), pMsg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void initData() {

    }

    @Override
    public void initView() {

    }

    @Override
    public void initListener() {

    }

    @Override
    public void initLoad() {

    }

    protected void openActivity(Class<? extends BaseActivity> toActivity) {
        openActivity(toActivity, null);
    }

    protected void openActivity(Class<? extends BaseActivity> toActivity, Bundle parameter) {
        Intent intent = new Intent(getActivity(), toActivity);
        if (parameter != null) {
            intent.putExtras(parameter);
        }
        startActivity(intent);

    }

    public void show(){
        onShow();
        isShowForUser = true;
    }

    public void hide(){
        onHide();
        isShowForUser = false;
    }

    public void onShow(){

    }

    public void onHide(){

    }

    @Override
    public void onStop() {
        super.onStop();
        if(isShowForUser) {
            onHide();
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        if(isFirstStart){
            isFirstStart = false;
        }else {
            if (isShowForUser) {
                onShow();
            }
        }
    }
}
