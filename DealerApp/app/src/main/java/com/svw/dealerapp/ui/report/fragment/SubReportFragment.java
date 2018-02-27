package com.svw.dealerapp.ui.report.fragment;

import android.content.ContentValues;
import android.content.res.Configuration;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;

import com.svw.dealerapp.DealerApp;
import com.svw.dealerapp.common.BaseWebViewFragment;
import com.svw.dealerapp.ui.login.contract.SMRefreshTokenContract;
import com.svw.dealerapp.ui.login.model.SMRefreshTokenModel;
import com.svw.dealerapp.ui.login.presenter.SMRefreshTokenPresenter;
import com.svw.dealerapp.util.CommonUtils;
import com.svw.dealerapp.util.ScreenOrientationHelper;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by lijinkui on 2017/8/25.
 */

public class SubReportFragment extends BaseWebViewFragment implements SMRefreshTokenContract.View {

    ScreenOrientationHelper screenOrientationHelper;
    private SMRefreshTokenPresenter smRefreshTokenPresenter;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        smRefreshTokenPresenter = new SMRefreshTokenPresenter(new SMRefreshTokenModel(), this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        screenOrientationHelper = new ScreenOrientationHelper(getActivity());
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onStart() {
        super.onStart();
        screenOrientationHelper.enableSensorOrientation();
    }

    @Override
    public void onStop() {
        super.onStop();
        screenOrientationHelper.disableSensorOrientation();
    }


    @Override
    public void setJSInterface(WebView webView) {
        webView.addJavascriptInterface(new JSInterfaceForSR(), "Android");
    }

    public void onBackPressed() {
        if (getActivity().getResources().getConfiguration().orientation != Configuration.ORIENTATION_PORTRAIT) {
            screenOrientationHelper.enableSensorOrientation();
            screenOrientationHelper.portrait();
        } else {
            getActivity().finish();
        }
    }

    private class JSInterfaceForSR extends JSInterface {

        @JavascriptInterface
        public void updateWebToken() {
            smRefreshTokenPresenter.smRefreshToken(getAccessTokenFromDB());
        }

        @JavascriptInterface
        public void finishSubReport() {
            if (getActivity().getResources().getConfiguration().orientation != Configuration.ORIENTATION_PORTRAIT) {
                screenOrientationHelper.enableSensorOrientation();
                screenOrientationHelper.portrait();
            } else {
                getActivity().finish();
            }
        }

        @JavascriptInterface
        public void showLandscapeView() {
//            if(getActivity().getRequestedOrientation()!=ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE){
//                getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE);
//            }
            screenOrientationHelper.disableSensorOrientation();
            screenOrientationHelper.landscape();
        }
    }

    @Override
    public void getWebTokenSuccess(String result) {
        saveWebTokenFromDB(result);
        CommonUtils.syncCookie(getUri(), "webToken=" + result);
//        Map<String, String> extraHeaders;
//        extraHeaders = new HashMap<>();
//        extraHeaders.put("Cache-Control", "max-age=3600");
//        getWebView().loadUrl(getUri(),extraHeaders);
        getWebView().loadUrl(getUri());
    }

    @Override
    public void getWebTokenFail(String result) {

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (null != smRefreshTokenPresenter) {
            smRefreshTokenPresenter.onDestroy();
        }
    }

    private void saveWebTokenFromDB(String webToken) {
        Cursor cursor = DealerApp.dbHelper.rawQuery("select * from SMToken", null);
        if (cursor != null) {
            while (cursor.moveToNext()) {
                ContentValues values = new ContentValues();
                values.put("accessSubToken", webToken);
                DealerApp.dbHelper.update("SMToken", values, null, null);
            }
            cursor.close();
        }
    }

    private Map<String, Object> getAccessTokenFromDB() {
        Map<String, Object> result = new HashMap<>();
        Cursor cursor = DealerApp.dbHelper.rawQuery("select * from SMToken", null);
        if (cursor != null) {
            if (cursor.moveToNext()) {
                result.put("grant_type", "refresh_token");
                result.put("refresh_token", cursor.getString(cursor.getColumnIndex("refreshToken")));
            }
            cursor.close();
        }
        return result;
    }
}
